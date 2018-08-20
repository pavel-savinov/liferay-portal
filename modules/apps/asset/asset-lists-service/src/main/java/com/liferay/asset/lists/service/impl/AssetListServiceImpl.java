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

package com.liferay.asset.lists.service.impl;

import com.liferay.asset.lists.constants.AssetListsActionKeys;
import com.liferay.asset.lists.model.AssetList;
import com.liferay.asset.lists.service.base.AssetListServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Locale;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class AssetListServiceImpl extends AssetListServiceBaseImpl {

	@Override
	public AssetList addAssetList(
			long userId, long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			AssetListsActionKeys.ADD_ASSET_LIST);

		return assetListLocalService.addAssetList(
			userId, groupId, nameMap, descriptionMap, type, serviceContext);
	}

	@Override
	public AssetList deleteAssetList(long assetListId) throws PortalException {
		_assetListModelResourcePermission.check(
			getPermissionChecker(), assetListId, ActionKeys.DELETE);

		return assetListLocalService.deleteAssetList(assetListId);
	}

	@Override
	public AssetList fetchAssetList(long assetListId) throws PortalException {
		AssetList assetList = assetListLocalService.fetchAssetList(assetListId);

		if (assetList != null) {
			_portletResourcePermission.check(
				getPermissionChecker(), assetList.getGroupId(),
				ActionKeys.VIEW);
		}

		return assetList;
	}

	@Override
	public AssetList updateAssetList(
			long assetListId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		_assetListModelResourcePermission.check(
			getPermissionChecker(), assetListId, ActionKeys.UPDATE);

		return assetListLocalService.updateAssetList(
			assetListId, nameMap, descriptionMap);
	}

	private static volatile ModelResourcePermission<AssetList>
		_assetListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				AssetListServiceImpl.class, "_assetListModelResourcePermission",
				AssetList.class);

	@ServiceReference(
		filterString = "(component.name=com.liferay.asset.lists.internal.security.permission.resource.AssetListsPortletResourcePermission)",
		type = PortletResourcePermission.class
	)
	private static volatile PortletResourcePermission
		_portletResourcePermission;

}