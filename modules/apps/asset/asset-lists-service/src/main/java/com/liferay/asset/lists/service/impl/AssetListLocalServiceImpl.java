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

import com.liferay.asset.lists.exception.NoSuchAssetListException;
import com.liferay.asset.lists.model.AssetList;
import com.liferay.asset.lists.service.base.AssetListLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class AssetListLocalServiceImpl extends AssetListLocalServiceBaseImpl {

	@Override
	public AssetList addAssetList(
			long userId, long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long assetListId = counterLocalService.increment();

		AssetList assetList = assetListPersistence.create(assetListId);

		assetList.setUuid(serviceContext.getUuid());
		assetList.setGroupId(groupId);
		assetList.setCompanyId(user.getCompanyId());
		assetList.setUserId(user.getUserId());
		assetList.setUserName(user.getFullName());
		assetList.setCreateDate(serviceContext.getCreateDate(new Date()));
		assetList.setModifiedDate(serviceContext.getModifiedDate(new Date()));
		assetList.setNameMap(nameMap);
		assetList.setDescriptionMap(descriptionMap);
		assetList.setType(type);

		assetListPersistence.update(assetList);

		return assetList;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public AssetList deleteAssetList(AssetList assetList) {
		assetListPersistence.remove(assetList);

		return assetList;
	}

	@Override
	public AssetList deleteAssetList(long assetListId)
		throws NoSuchAssetListException {

		AssetList assetList = assetListPersistence.fetchByPrimaryKey(
			assetListId);

		if (assetList == null) {
			throw new NoSuchAssetListException();
		}

		return assetListLocalService.deleteAssetList(assetList);
	}

	@Override
	public AssetList updateAssetList(
			long assetListId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		AssetList assetList = assetListPersistence.findByPrimaryKey(
			assetListId);

		assetList.setModifiedDate(new Date());
		assetList.setNameMap(nameMap);
		assetList.setDescriptionMap(descriptionMap);

		assetListPersistence.update(assetList);

		return assetList;
	}

}