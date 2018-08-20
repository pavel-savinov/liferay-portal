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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for AssetList. This utility wraps
 * {@link com.liferay.asset.lists.service.impl.AssetListServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListService
 * @see com.liferay.asset.lists.service.base.AssetListServiceBaseImpl
 * @see com.liferay.asset.lists.service.impl.AssetListServiceImpl
 * @generated
 */
@ProviderType
public class AssetListServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.asset.lists.service.impl.AssetListServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.asset.lists.model.AssetList addAssetList(
		long userId, long groupId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addAssetList(userId, groupId, nameMap, descriptionMap,
			type, serviceContext);
	}

	public static com.liferay.asset.lists.model.AssetList deleteAssetList(
		long assetListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAssetList(assetListId);
	}

	public static com.liferay.asset.lists.model.AssetList fetchAssetList(
		long assetListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchAssetList(assetListId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.asset.lists.model.AssetList updateAssetList(
		long assetListId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateAssetList(assetListId, nameMap, descriptionMap);
	}

	public static AssetListService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetListService, AssetListService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetListService.class);

		ServiceTracker<AssetListService, AssetListService> serviceTracker = new ServiceTracker<AssetListService, AssetListService>(bundle.getBundleContext(),
				AssetListService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}