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

package com.liferay.asset.lists.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.lists.model.AssetList;
import com.liferay.asset.lists.service.AssetListServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class AssetListServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddAssetList() throws PortalException {
		Locale defaultLocale = PortalUtil.getSiteDefaultLocale(
			_group.getGroupId());

		AssetList assetList = _addAssetList(defaultLocale);

		Assert.assertNotNull(
			AssetListServiceUtil.fetchAssetList(assetList.getAssetListId()));

		Assert.assertEquals(
			"Asset List Name", assetList.getName(defaultLocale));

		Assert.assertEquals(
			"Asset List Description", assetList.getDescription(defaultLocale));
	}

	@Test
	public void testDeleteAssetList() throws PortalException {
		Locale defaultLocale = PortalUtil.getSiteDefaultLocale(
			_group.getGroupId());

		AssetList assetList = _addAssetList(defaultLocale);

		AssetListServiceUtil.deleteAssetList(assetList.getAssetListId());

		Assert.assertNull(
			AssetListServiceUtil.fetchAssetList(assetList.getAssetListId()));
	}

	@Test
	public void testUpdateAssetList() throws PortalException {
		Locale defaultLocale = PortalUtil.getSiteDefaultLocale(
			_group.getGroupId());

		AssetList assetList = _addAssetList(defaultLocale);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(defaultLocale, "New Asset List Name");

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(defaultLocale, "New Asset List Description");

		assetList = AssetListServiceUtil.updateAssetList(
			assetList.getAssetListId(), nameMap, descriptionMap);

		Assert.assertEquals(
			"New Asset List Name", assetList.getName(defaultLocale));

		Assert.assertEquals(
			"New Asset List Description",
			assetList.getDescription(defaultLocale));
	}

	private AssetList _addAssetList(Locale defaultLocale)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(defaultLocale, "Asset List Name");

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(defaultLocale, "Asset List Description");

		return AssetListServiceUtil.addAssetList(
			TestPropsValues.getUserId(), _group.getGroupId(), nameMap,
			descriptionMap, 0, serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

}