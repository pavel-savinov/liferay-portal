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

package com.liferay.asset.lists.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetListService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListService
 * @generated
 */
@ProviderType
public class AssetListServiceWrapper implements AssetListService,
	ServiceWrapper<AssetListService> {
	public AssetListServiceWrapper(AssetListService assetListService) {
		_assetListService = assetListService;
	}

	@Override
	public com.liferay.asset.lists.model.AssetList addAssetList(long userId,
		long groupId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetListService.addAssetList(userId, groupId, nameMap,
			descriptionMap, type, serviceContext);
	}

	@Override
	public com.liferay.asset.lists.model.AssetList deleteAssetList(
		long assetListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetListService.deleteAssetList(assetListId);
	}

	@Override
	public com.liferay.asset.lists.model.AssetList fetchAssetList(
		long assetListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetListService.fetchAssetList(assetListId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetListService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.asset.lists.model.AssetList updateAssetList(
		long assetListId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetListService.updateAssetList(assetListId, nameMap,
			descriptionMap);
	}

	@Override
	public AssetListService getWrappedService() {
		return _assetListService;
	}

	@Override
	public void setWrappedService(AssetListService assetListService) {
		_assetListService = assetListService;
	}

	private AssetListService _assetListService;
}