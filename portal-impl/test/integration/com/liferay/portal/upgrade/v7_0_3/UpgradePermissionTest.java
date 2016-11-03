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

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.model.impl.ResourcePermissionImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.service.permission.AssetCategoriesPermission;
import com.liferay.portlet.asset.service.permission.AssetPermission;

import com.liferay.portlet.asset.service.permission.AssetTagsPermission;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pavel Savinov
 */
@Sync(cleanTransaction = true)
public class UpgradePermissionTest extends UpgradePermission {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		ResourcePermissionLocalServiceUtil.deleteResourcePermissions(
			_user.getCompanyId(), AssetCategoriesPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_INDIVIDUAL,
			AssetCategoriesPermission.RESOURCE_NAME);

		ResourcePermissionLocalServiceUtil.deleteResourcePermissions(
			_user.getCompanyId(), AssetTagsPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_INDIVIDUAL,
			AssetTagsPermission.RESOURCE_NAME);
	}

	@Test
	public void testUpgrade() throws Exception {
		String primKey = AssetPermission.RESOURCE_NAME;
		long actionIds = 30;

		long resourcePermissionId = addResourcePermission(primKey, actionIds);

		int categoriesResourcePermissionCount =
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				_user.getCompanyId(), AssetCategoriesPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				AssetCategoriesPermission.RESOURCE_NAME);

		int tagsResourcePermissionCount =
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				_user.getCompanyId(), AssetTagsPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				AssetTagsPermission.RESOURCE_NAME);

		Assert.assertEquals(0, categoriesResourcePermissionCount);
		Assert.assertEquals(0, tagsResourcePermissionCount);

		upgrade();

		CacheRegistryUtil.clear();

		ResourcePermission resourcePermission =
			ResourcePermissionLocalServiceUtil.fetchResourcePermission(
				resourcePermissionId);

		Assert.assertNull(resourcePermission);

		categoriesResourcePermissionCount =
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				_user.getCompanyId(), AssetCategoriesPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				AssetCategoriesPermission.RESOURCE_NAME);

		tagsResourcePermissionCount =
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				_user.getCompanyId(), AssetTagsPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				AssetTagsPermission.RESOURCE_NAME);

		Assert.assertEquals(1, categoriesResourcePermissionCount);
		Assert.assertEquals(1, tagsResourcePermissionCount);

		ResourcePermission resourcePermissionCategories =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				_user.getCompanyId(), AssetCategoriesPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				AssetCategoriesPermission.RESOURCE_NAME, 0);

		ResourcePermission resourcePermissionTags =
			ResourcePermissionLocalServiceUtil.getResourcePermission(
				_user.getCompanyId(), AssetTagsPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_INDIVIDUAL,
				AssetTagsPermission.RESOURCE_NAME, 0);

		Assert.assertEquals(14, resourcePermissionCategories.getActionIds());
		Assert.assertEquals(6, resourcePermissionTags.getActionIds());
	}

	protected long addResourcePermission(String primKey, long actionIds) {
		ResourcePermission resourcePermission = new ResourcePermissionImpl();

		long resourcePermissionId = CounterLocalServiceUtil.increment(
			ResourcePermission.class.getName());

		resourcePermission.setResourcePermissionId(resourcePermissionId);

		resourcePermission.setCompanyId(_user.getCompanyId());
		resourcePermission.setName(AssetPermission.RESOURCE_NAME);
		resourcePermission.setScope(ResourceConstants.SCOPE_INDIVIDUAL);
		resourcePermission.setPrimKey(primKey);
		resourcePermission.setPrimKeyId(-1);
		resourcePermission.setRoleId(0);
		resourcePermission.setOwnerId(_user.getUserId());
		resourcePermission.setActionIds(actionIds);
		resourcePermission.setViewActionId(actionIds % 2 != 1);

		resourcePermission =
			ResourcePermissionLocalServiceUtil.addResourcePermission(
				resourcePermission);

		return resourcePermission.getResourcePermissionId();
	}

	@DeleteAfterTestRun
	private User _user;

}