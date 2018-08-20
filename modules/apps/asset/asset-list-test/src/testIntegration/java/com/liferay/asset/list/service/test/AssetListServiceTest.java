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

package com.liferay.asset.list.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.HashMap;
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
	public void testAddAssetEntryList() throws PortalException {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		AssetListEntry assetListEntry = _addAssetListEntry(defaultLanguageId);

		Assert.assertNotNull(
			AssetListEntryServiceUtil.fetchAssetListEntry(
				assetListEntry.getAssetListEntryId()));

		Assert.assertEquals(
			"Asset List Title", assetListEntry.getTitle(defaultLanguageId));

		Assert.assertEquals(
			"Asset List Description",
			assetListEntry.getDescription(defaultLanguageId));
	}

	@Test
	public void testDeleteAssetList() throws PortalException {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		AssetListEntry assetListEntry = _addAssetListEntry(defaultLanguageId);

		AssetListEntryServiceUtil.deleteAssetListEntry(
			assetListEntry.getAssetListEntryId());

		Assert.assertNull(
			AssetListEntryServiceUtil.fetchAssetListEntry(
				assetListEntry.getAssetListEntryId()));
	}

	@Test
	public void testUpdateAssetList() throws PortalException {
		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		AssetListEntry assetListEntry = _addAssetListEntry(defaultLanguageId);

		Map<String, String> titleMap = new HashMap<>();

		titleMap.put(defaultLanguageId, "New Asset List Title");

		Map<String, String> descriptionMap = new HashMap<>();

		descriptionMap.put(defaultLanguageId, "New Asset List Description");

		assetListEntry = AssetListEntryServiceUtil.updateAssetListEntry(
			assetListEntry.getAssetListEntryId(), titleMap, descriptionMap);

		Assert.assertEquals(
			"New Asset List Title", assetListEntry.getTitle(defaultLanguageId));

		Assert.assertEquals(
			"New Asset List Description",
			assetListEntry.getDescription(defaultLanguageId));
	}

	private AssetListEntry _addAssetListEntry(String defaultLanguageId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Map<String, String> titleMap = new HashMap<>();

		titleMap.put(defaultLanguageId, "Asset List Title");

		Map<String, String> descriptionMap = new HashMap<>();

		descriptionMap.put(defaultLanguageId, "Asset List Description");

		return AssetListEntryServiceUtil.addAssetListEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), titleMap,
			descriptionMap, 0, serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

}