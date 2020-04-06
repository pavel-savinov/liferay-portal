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

package com.liferay.layout.page.template.headless.delivery.dto.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.headless.delivery.dto.v1_0.ColumnDefinition;
import com.liferay.headless.delivery.dto.v1_0.DropZoneDefinition;
import com.liferay.headless.delivery.dto.v1_0.Fragment;
import com.liferay.headless.delivery.dto.v1_0.FragmentField;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldBackgroundImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldHTML;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentFieldText;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentInstanceDefinition;
import com.liferay.headless.delivery.dto.v1_0.FragmentLink;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.RowDefinition;
import com.liferay.headless.delivery.dto.v1_0.SectionDefinition;
import com.liferay.headless.delivery.dto.v1_0.Settings;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.headless.delivery.dto.v1_0.PageDefinitionConverterUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
public class PageDefinitionConverterUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					TestPropsValues.getUserId(), _group.getGroupId(),
					RandomTestUtil.randomString(), StringPool.BLANK,
					_serviceContext);

		_layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, 0,
				WorkflowConstants.STATUS_DRAFT, _serviceContext);

		_fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);
	}

	@Test
	public void testToPageDefinitionDropZoneAllowedFragments()
		throws Exception {

		_addLayoutPageTemplateStructure(
			"layout_data_drop_zone_allowed_fragments.json", new HashMap<>());

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		PageDefinition pageDefinition =
			PageDefinitionConverterUtil.toPageDefinition(
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererTracker,
				_infoDisplayContributorTracker, layout);

		PageElement rootPageElement = pageDefinition.getPageElement();

		Assert.assertEquals(PageElement.Type.ROOT, rootPageElement.getType());

		PageElement[] pageElements = rootPageElement.getPageElements();

		Assert.assertEquals(
			Arrays.toString(pageElements), 1, pageElements.length);

		PageElement dropZonePageElement = pageElements[0];

		Assert.assertEquals(
			PageElement.Type.DROP_ZONE, dropZonePageElement.getType());

		DropZoneDefinition dropZoneDefinition =
			(DropZoneDefinition)dropZonePageElement.getDefinition();

		Map<String, Fragment[]> fragmentSettingsMap =
			(Map<String, Fragment[]>)dropZoneDefinition.getFragmentSettings();

		Fragment[] allowedFragments = fragmentSettingsMap.get(
			"allowedFragments");

		Assert.assertEquals(
			Arrays.toString(allowedFragments), 3, allowedFragments.length);

		Assert.assertEquals(
			"BASIC_COMPONENT-button", allowedFragments[0].getKey());
		Assert.assertEquals(
			"BASIC_COMPONENT-card", allowedFragments[1].getKey());
		Assert.assertEquals(
			"com.liferay.fragment.internal.renderer." +
				"ContentObjectFragmentRenderer",
			allowedFragments[2].getKey());
	}

	@Test
	public void testToPageDefinitionDropZoneUnallowedFragments()
		throws Exception {

		_addLayoutPageTemplateStructure(
			"layout_data_drop_zone_unallowed_fragments.json", new HashMap<>());

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		PageDefinition pageDefinition =
			PageDefinitionConverterUtil.toPageDefinition(
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererTracker,
				_infoDisplayContributorTracker, layout);

		PageElement rootPageElement = pageDefinition.getPageElement();

		Assert.assertEquals(PageElement.Type.ROOT, rootPageElement.getType());

		PageElement[] pageElements = rootPageElement.getPageElements();

		Assert.assertEquals(
			Arrays.toString(pageElements), 1, pageElements.length);

		PageElement dropZonePageElement = pageElements[0];

		Assert.assertEquals(
			PageElement.Type.DROP_ZONE, dropZonePageElement.getType());

		DropZoneDefinition dropZoneDefinition =
			(DropZoneDefinition)dropZonePageElement.getDefinition();

		Map<String, Fragment[]> fragmentSettingsMap =
			(Map<String, Fragment[]>)dropZoneDefinition.getFragmentSettings();

		Fragment[] unallowedFragments = fragmentSettingsMap.get(
			"unallowedFragments");

		Assert.assertEquals(
			Arrays.toString(unallowedFragments), 3, unallowedFragments.length);

		Assert.assertEquals(
			"BASIC_COMPONENT-button", unallowedFragments[0].getKey());
		Assert.assertEquals(
			"BASIC_COMPONENT-card", unallowedFragments[1].getKey());
		Assert.assertEquals(
			"com.liferay.fragment.internal.renderer." +
				"ContentObjectFragmentRenderer",
			unallowedFragments[2].getKey());
	}

	@Test
	public void testToPageDefinitionFragmentConfig() throws Exception {
		FragmentInstanceDefinition fragmentInstanceDefinition =
			_getFragmentInstanceDefinition(
				_read("fragment_config.json"),
				"editable_values_fragment_config.json", "my-fragment-entry-key",
				RandomTestUtil.randomString(),
				_read("html_fragment_config.ftl"));

		Map<String, Object> fragmentConfigMap =
			fragmentInstanceDefinition.getFragmentConfig();

		Assert.assertEquals(4, fragmentConfigMap.get("level"));
		Assert.assertEquals("center", fragmentConfigMap.get("textAlign"));
		Assert.assertEquals("danger", fragmentConfigMap.get("textColor"));
	}

	@Test
	public void testToPageDefinitionFragmentFieldBackgroundImage()
		throws Exception {

		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_background_image.json",
			"my-background-image",
			"<div data-lfr-background-image-id=\"my-background-image\"></div>");

		FragmentFieldBackgroundImage fragmentFieldBackgroundImage =
			(FragmentFieldBackgroundImage)fragmentField.getValue();

		_validateFragmentBackgroundImage(
			fragmentFieldBackgroundImage.getBackgroundImage());
	}

	@Test
	public void testToPageDefinitionFragmentFieldBackgroundImageTitle()
		throws Exception {

		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_background_image_title.json",
			"my-background-image",
			"<div data-lfr-background-image-id=\"my-background-image\"></div>");

		FragmentFieldBackgroundImage fragmentFieldBackgroundImage =
			(FragmentFieldBackgroundImage)fragmentField.getValue();

		_validateFragmentBackgroundImageWithTitle(
			fragmentFieldBackgroundImage.getBackgroundImage(),
			"My Background Image Title");
	}

	@Test
	public void testToPageDefinitionFragmentFieldHTML() throws Exception {
		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_html.json", "my-html",
			"<lfr-editable id=\"my-html\" type=\"html\"><h1>Example</h1>" +
				"</lfr-editable>");

		FragmentFieldHTML fragmentFieldHTML =
			(FragmentFieldHTML)fragmentField.getValue();

		_validateFragmentFieldHTML(fragmentFieldHTML);
	}

	@Test
	public void testToPageDefinitionFragmentFieldImage() throws Exception {
		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_image.json", "my-image",
			"<lfr-editable id=\"my-image\" type=\"image\"><img/>" +
				"</lfr-editable>");

		FragmentFieldImage fragmentFieldImage =
			(FragmentFieldImage)fragmentField.getValue();

		_validateFragmentImage(fragmentFieldImage.getFragmentImage());
	}

	@Test
	public void testToPageDefinitionFragmentFieldImageTitle() throws Exception {
		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_image_title.json", "my-image",
			"<lfr-editable id=\"my-image\" type=\"image\"><img/>" +
				"</lfr-editable>");

		FragmentFieldImage fragmentFieldImage =
			(FragmentFieldImage)fragmentField.getValue();

		_validateFragmentImageWithTitle(
			fragmentFieldImage.getFragmentImage(), "My Image Title");
	}

	@Test
	public void testToPageDefinitionFragmentFieldLink() throws Exception {
		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_link.json", "my-link",
			"<lfr-editable id=\"my-link\" type=\"link\"><a href=\"\" " +
				"id=\"my-link\">Go here</a></lfr-editable>");

		FragmentFieldText fragmentFieldText =
			(FragmentFieldText)fragmentField.getValue();

		_validateFragmentFieldText(fragmentFieldText);
	}

	@Test
	public void testToPageDefinitionFragmentFieldText() throws Exception {
		FragmentField fragmentField = _getFragmentField(
			"editable_values_fragment_field_text.json", "my-text",
			"<lfr-editable id=\"my-text\" type=\"text\">Example" +
				"</lfr-editable>");

		FragmentFieldText fragmentFieldText =
			(FragmentFieldText)fragmentField.getValue();

		_validateFragmentFieldText(fragmentFieldText);
	}

	@Test
	public void testToPageDefinitionRoot() throws Exception {
		_addLayoutPageTemplateStructure(
			"layout_data_root.json", new HashMap<>());

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		ColorScheme colorScheme = layout.getColorScheme();

		Theme theme = layout.getTheme();

		PageDefinition pageDefinition =
			PageDefinitionConverterUtil.toPageDefinition(
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererTracker,
				_infoDisplayContributorTracker, layout);

		Settings settings = pageDefinition.getSettings();

		Assert.assertEquals(
			colorScheme.getName(), settings.getColorSchemeName());
		Assert.assertNull(settings.getCss());
		Assert.assertNull(settings.getJavascript());
		Assert.assertNull(settings.getMasterPage());
		Assert.assertEquals(theme.getName(), settings.getThemeName());
		Assert.assertNull(settings.getThemeSettings());

		PageElement pageElement = pageDefinition.getPageElement();

		Assert.assertNull(pageElement.getDefinition());
		Assert.assertNull(pageElement.getPageElements());
		Assert.assertEquals(PageElement.Type.ROOT, pageElement.getType());
	}

	@Test
	public void testToPageDefinitionRow() throws Exception {
		_addLayoutPageTemplateStructure(
			"layout_data_row.json", new HashMap<>());

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		PageDefinition pageDefinition =
			PageDefinitionConverterUtil.toPageDefinition(
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererTracker,
				_infoDisplayContributorTracker, layout);

		PageElement rootPageElement = pageDefinition.getPageElement();

		Assert.assertEquals(PageElement.Type.ROOT, rootPageElement.getType());

		PageElement[] pageElements = rootPageElement.getPageElements();

		Assert.assertEquals(
			Arrays.toString(pageElements), 1, pageElements.length);

		PageElement rowPageElement = pageElements[0];

		Assert.assertEquals(PageElement.Type.ROW, rowPageElement.getType());

		RowDefinition rowDefinition =
			(RowDefinition)rowPageElement.getDefinition();

		Assert.assertFalse(rowDefinition.getGutters());

		Assert.assertEquals(
			Integer.valueOf(2), rowDefinition.getNumberOfColumns());

		PageElement[] columnPageElements = rowPageElement.getPageElements();

		Assert.assertEquals(
			Arrays.toString(columnPageElements), 2, columnPageElements.length);

		Assert.assertEquals(
			PageElement.Type.COLUMN, columnPageElements[0].getType());
		Assert.assertNull(columnPageElements[0].getPageElements());

		Assert.assertEquals(
			PageElement.Type.COLUMN, columnPageElements[1].getType());
		Assert.assertNull(columnPageElements[1].getPageElements());

		ColumnDefinition columnDefinition1 =
			(ColumnDefinition)columnPageElements[0].getDefinition();
		ColumnDefinition columnDefinition2 =
			(ColumnDefinition)columnPageElements[1].getDefinition();

		Assert.assertEquals(Integer.valueOf(5), columnDefinition1.getSize());
		Assert.assertEquals(Integer.valueOf(7), columnDefinition2.getSize());
	}

	@Test
	public void testToPageDefinitionSection() throws Exception {
		_addLayoutPageTemplateStructure(
			"layout_data_section.json", new HashMap<>());

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		PageDefinition pageDefinition =
			PageDefinitionConverterUtil.toPageDefinition(
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererTracker,
				_infoDisplayContributorTracker, layout);

		PageElement rootPageElement = pageDefinition.getPageElement();

		Assert.assertEquals(PageElement.Type.ROOT, rootPageElement.getType());

		PageElement[] pageElements = rootPageElement.getPageElements();

		Assert.assertEquals(
			Arrays.toString(pageElements), 2, pageElements.length);

		PageElement sectionPageElement1 = pageElements[0];

		Assert.assertEquals(
			PageElement.Type.SECTION, sectionPageElement1.getType());

		SectionDefinition sectionDefinition1 =
			(SectionDefinition)sectionPageElement1.getDefinition();

		Assert.assertEquals("primary", sectionDefinition1.getBackgroundColor());

		FragmentImage fragmentImage1 = sectionDefinition1.getBackgroundImage();

		FragmentInlineValue titleFragmentInlineValue =
			(FragmentInlineValue)fragmentImage1.getTitle();

		Assert.assertEquals(
			"My Example1 Background Image Title",
			titleFragmentInlineValue.getValue());

		FragmentInlineValue urlFragmentInlineValue1 =
			(FragmentInlineValue)fragmentImage1.getUrl();

		Assert.assertEquals(
			"http://myexample1.com/myexample1.png",
			urlFragmentInlineValue1.getValue());

		com.liferay.headless.delivery.dto.v1_0.Layout sectionLayout =
			sectionDefinition1.getLayout();

		Assert.assertEquals("Fluid", sectionLayout.getContainerTypeAsString());
		Assert.assertEquals(
			Integer.valueOf(8), sectionLayout.getPaddingBottom());
		Assert.assertEquals(
			Integer.valueOf(4), sectionLayout.getPaddingHorizontal());
		Assert.assertEquals(Integer.valueOf(1), sectionLayout.getPaddingTop());

		PageElement sectionPageElement2 = pageElements[1];

		Assert.assertEquals(
			PageElement.Type.SECTION, sectionPageElement2.getType());

		SectionDefinition sectionDefinition2 =
			(SectionDefinition)sectionPageElement2.getDefinition();

		FragmentImage fragmentImage2 = sectionDefinition2.getBackgroundImage();

		Assert.assertNull(fragmentImage2.getTitle());

		FragmentInlineValue urlFragmentInlineValue2 =
			(FragmentInlineValue)fragmentImage2.getUrl();

		Assert.assertEquals(
			"http://myexample2.com/myexample2.png",
			urlFragmentInlineValue2.getValue());
	}

	private void _addLayoutPageTemplateStructure(
			String fileName, Map<String, String> valuesMap)
		throws Exception {

		_layoutPageTemplateStructureLocalService.addLayoutPageTemplateStructure(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class.getName()),
			_layoutPageTemplateEntry.getPlid(),
			StringUtil.replace(_read(fileName), "${", "}", valuesMap),
			_serviceContext);
	}

	private FragmentField _getFragmentField(
			String editableValuesFileName, String fragmentFieldId, String html)
		throws Exception {

		String fragmentName = RandomTestUtil.randomString();

		String fragmentEntryKey = "my-fragment-entry-key";

		FragmentInstanceDefinition fragmentInstanceDefinition =
			_getFragmentInstanceDefinition(
				StringPool.BLANK, editableValuesFileName, fragmentEntryKey,
				fragmentName, html);

		Fragment fragment = fragmentInstanceDefinition.getFragment();

		Assert.assertEquals(
			_fragmentCollection.getName(), fragment.getCollectionName());
		Assert.assertEquals(fragmentEntryKey, fragment.getKey());
		Assert.assertEquals(fragmentName, fragment.getName());

		FragmentField[] fragmentFields =
			fragmentInstanceDefinition.getFragmentFields();

		Assert.assertEquals(
			Arrays.toString(fragmentFields), 1, fragmentFields.length);

		FragmentField fragmentField = fragmentFields[0];

		Assert.assertEquals(fragmentFieldId, fragmentField.getId());

		return fragmentField;
	}

	private FragmentInstanceDefinition _getFragmentInstanceDefinition(
			String configuration, String editableValuesFileName,
			String fragmentEntryKey, String fragmentName, String html)
		throws Exception {

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(), fragmentEntryKey,
				fragmentName, StringPool.BLANK, html, StringPool.BLANK,
				configuration, 0, FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				_portal.getClassNameId(Layout.class), layout.getPlid(),
				StringPool.BLANK, html, StringPool.BLANK, configuration,
				_read(editableValuesFileName), StringPool.BLANK, 0, null,
				_serviceContext);

		_addLayoutPageTemplateStructure(
			"layout_data_fragment.json",
			HashMapBuilder.put(
				"FRAGMENT_ENTRY_LINK_ID",
				String.valueOf(fragmentEntryLink.getFragmentEntryLinkId())
			).build());

		PageDefinition pageDefinition =
			PageDefinitionConverterUtil.toPageDefinition(
				_fragmentCollectionContributorTracker,
				_fragmentEntryConfigurationParser, _fragmentRendererTracker,
				_infoDisplayContributorTracker, layout);

		PageElement rootPageElement = pageDefinition.getPageElement();

		Assert.assertEquals(PageElement.Type.ROOT, rootPageElement.getType());

		PageElement[] pageElements = rootPageElement.getPageElements();

		Assert.assertEquals(
			Arrays.toString(pageElements), 1, pageElements.length);

		PageElement fragmentPageElement = pageElements[0];

		Assert.assertNull(fragmentPageElement.getPageElements());
		Assert.assertEquals(
			PageElement.Type.FRAGMENT, fragmentPageElement.getType());

		return (FragmentInstanceDefinition)fragmentPageElement.getDefinition();
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	private void _validateFragmentBackgroundImage(FragmentImage fragmentImage) {
		Assert.assertNull(fragmentImage.getDescription());
		Assert.assertNull(fragmentImage.getTitle());

		FragmentInlineValue urlFragmentInlineValue =
			(FragmentInlineValue)fragmentImage.getUrl();

		Assert.assertNull(urlFragmentInlineValue.getValue());

		Map<String, String> urlI18nMap = urlFragmentInlineValue.getValue_i18n();

		Assert.assertEquals(
			"http://myexample.com/myexample.png", urlI18nMap.get("en_US"));
		Assert.assertEquals(
			"http://miejemplo.es/miejemplo.png", urlI18nMap.get("es_ES"));
	}

	private void _validateFragmentBackgroundImageWithTitle(
		FragmentImage fragmentImage, String title) {

		Assert.assertNull(fragmentImage.getDescription());

		FragmentInlineValue titleFragmentInlineValue =
			(FragmentInlineValue)fragmentImage.getTitle();

		Assert.assertEquals(title, titleFragmentInlineValue.getValue());

		FragmentInlineValue urlFragmentInlineValue =
			(FragmentInlineValue)fragmentImage.getUrl();

		Assert.assertNull(urlFragmentInlineValue.getValue());

		Map<String, String> urlI18nMap = urlFragmentInlineValue.getValue_i18n();

		Assert.assertEquals(
			"http://myexample.com/myexample.png", urlI18nMap.get("en_US"));
		Assert.assertEquals(
			"http://miejemplo.es/miejemplo.png", urlI18nMap.get("es_ES"));
	}

	private void _validateFragmentFieldHTML(
		FragmentFieldHTML fragmentFieldHTML) {

		FragmentInlineValue fragmentInlineValue =
			(FragmentInlineValue)fragmentFieldHTML.getHtml();

		Assert.assertNull(fragmentInlineValue.getValue());

		Map<String, String> i18nMap = fragmentInlineValue.getValue_i18n();

		Assert.assertEquals("My example", i18nMap.get("en_US"));
		Assert.assertEquals("Mi ejemplo", i18nMap.get("es_ES"));
	}

	private void _validateFragmentFieldText(
		FragmentFieldText fragmentFieldText) {

		FragmentLink fragmentLink = fragmentFieldText.getFragmentLink();

		Assert.assertEquals(
			FragmentLink.Target.BLANK, fragmentLink.getTarget());

		FragmentInlineValue hrefFragmentInlineValue =
			(FragmentInlineValue)fragmentLink.getHref();

		Assert.assertEquals(
			"http://www.myexample.com", hrefFragmentInlineValue.getValue());

		FragmentInlineValue textFragmentInlineValue =
			(FragmentInlineValue)fragmentFieldText.getText();

		Assert.assertNull(textFragmentInlineValue.getValue());

		Map<String, String> i18nMap = textFragmentInlineValue.getValue_i18n();

		Assert.assertEquals("My example", i18nMap.get("en_US"));
		Assert.assertEquals("Mi ejemplo", i18nMap.get("es_ES"));
	}

	private void _validateFragmentImage(FragmentImage fragmentImage) {
		Assert.assertNull(fragmentImage.getTitle());

		FragmentInlineValue descriptionFragmentInlineValue =
			(FragmentInlineValue)fragmentImage.getDescription();

		Assert.assertEquals(
			"My example description",
			descriptionFragmentInlineValue.getValue());

		FragmentInlineValue urlFragmentInlineValue =
			(FragmentInlineValue)fragmentImage.getUrl();

		Assert.assertNull(urlFragmentInlineValue.getValue());

		Map<String, String> urlI18nMap = urlFragmentInlineValue.getValue_i18n();

		Assert.assertEquals(
			"http://myexample.com/myexample.png", urlI18nMap.get("en_US"));
		Assert.assertEquals(
			"http://miejemplo.es/miejemplo.png", urlI18nMap.get("es_ES"));
	}

	private void _validateFragmentImageWithTitle(
		FragmentImage fragmentImage, String title) {

		FragmentInlineValue titleFragmentInlineValue =
			(FragmentInlineValue)fragmentImage.getTitle();

		Assert.assertEquals(title, titleFragmentInlineValue.getValue());

		FragmentInlineValue urlFragmentInlineValue =
			(FragmentInlineValue)fragmentImage.getUrl();

		Assert.assertNull(urlFragmentInlineValue.getValue());

		Map<String, String> urlI18nMap = urlFragmentInlineValue.getValue_i18n();

		Assert.assertEquals(
			"http://myexample.com/myexample.png", urlI18nMap.get("en_US"));
		Assert.assertEquals(
			"http://miejemplo.es/miejemplo.png", urlI18nMap.get("es_ES"));
	}

	private FragmentCollection _fragmentCollection;

	@Inject
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private FragmentRendererTracker _fragmentRendererTracker;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	private LayoutPageTemplateEntry _layoutPageTemplateEntry;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

}