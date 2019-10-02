/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.util.template.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.template.LayoutConverter;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.layout.util.template.LayoutData;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class LayoutConverterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));
	}

	@After
	public void tearDown() throws Exception {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testConvertOneColumnMultiplePortlets() throws Exception {
		_testConvertOneColumn(
			new String[] {
				"com_liferay_hello_velocity_web_portlet_HelloVelocityPortlet",
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet",
				"hello_soy_portlet"
			});
	}

	@Test
	public void testConvertOneColumnNoPortlets() throws Exception {
		_testConvertOneColumn(new String[0]);
	}

	@Test
	public void testConvertOneColumnSinglePortlet() throws Exception {
		_testConvertOneColumn(
			new String[] {
				"com_liferay_hello_world_web_portlet_HelloWorldPortlet"
			});
	}

	@Test
	public void testIsConvertibleFalseWidgetPageCustomizable()
		throws Exception {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutConstants.CUSTOMIZABLE_LAYOUT, Boolean.TRUE.toString());

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		Assert.assertEquals(false, layoutConverter.isConvertible(layout));
	}

	@Test
	public void testIsConvertibleFalseWidgetPageWithNestedApplicationsWidget()
		throws Exception {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put(
			LayoutTypePortletConstants.NESTED_COLUMN_IDS,
			StringUtil.randomString());

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		Assert.assertEquals(false, layoutConverter.isConvertible(layout));
	}

	@Test
	public void testIsConvertibleTrue() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		Assert.assertEquals(true, layoutConverter.isConvertible(layout));
	}

	private String _getLayoutTemplateId(Layout layout) {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		return layoutTypePortlet.getLayoutTemplateId();
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _testConvertOneColumn(String[] portletIds) throws Exception {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID, "1_column");

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), typeSettingsProperties.toString());

		for (String portletId : portletIds) {
			LayoutTestUtil.addPortletToLayout(
				TestPropsValues.getUserId(), layout,
				PortletIdCodec.encode(portletId), "column-1", new HashMap<>());
		}

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(
				_getLayoutTemplateId(layout));

		LayoutData layoutData = layoutConverter.convert(layout);

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				_group.getGroupId(),
				_portal.getClassNameId(Layout.class.getName()),
				layout.getPlid());

		Set<String> existingPortletIds = new HashSet<>();
		List<String> fragmentEntryLinkIds = new ArrayList<>();

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			fragmentEntryLinkIds.add(
				String.format(
					"\"%s\"", fragmentEntryLink.getFragmentEntryLinkId()));

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				fragmentEntryLink.getEditableValues());

			existingPortletIds.add(jsonObject.getString("portletId"));
		}

		Assert.assertEquals(
			fragmentEntryLinkIds.toString(), portletIds.length,
			fragmentEntryLinkIds.size());

		for (String portletId : portletIds) {
			Assert.assertTrue(existingPortletIds.contains(portletId));
		}

		String fragmentEntryLinkIdsJoined = StringUtil.merge(
			fragmentEntryLinkIds, StringPool.COMMA_AND_SPACE);

		JSONObject layoutDataJSONObject = layoutData.getLayoutDataJSONObject();

		String expectedLayoutData = _read("expected_layout_data_1_column.json");

		expectedLayoutData = StringUtil.replace(
			expectedLayoutData, "[]",
			String.format("[%s]", fragmentEntryLinkIdsJoined));

		JSONObject expectedLayoutDataJSONObject =
			JSONFactoryUtil.createJSONObject(expectedLayoutData);

		Assert.assertEquals(
			expectedLayoutDataJSONObject.toJSONString(),
			layoutDataJSONObject.toJSONString());
	}

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutConverterRegistry _layoutConverterRegistry;

	@Inject
	private Portal _portal;

}