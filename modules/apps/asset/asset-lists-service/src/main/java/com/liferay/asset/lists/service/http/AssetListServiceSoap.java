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

package com.liferay.asset.lists.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.lists.service.AssetListServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link AssetListServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.asset.lists.model.AssetListSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.asset.lists.model.AssetList}, that is translated to a
 * {@link com.liferay.asset.lists.model.AssetListSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListServiceHttp
 * @see com.liferay.asset.lists.model.AssetListSoap
 * @see AssetListServiceUtil
 * @generated
 */
@ProviderType
public class AssetListServiceSoap {
	public static com.liferay.asset.lists.model.AssetListSoap addAssetList(
		long userId, long groupId, String[] nameMapLanguageIds,
		String[] nameMapValues, String[] descriptionMapLanguageIds,
		String[] descriptionMapValues, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.asset.lists.model.AssetList returnValue = AssetListServiceUtil.addAssetList(userId,
					groupId, nameMap, descriptionMap, type, serviceContext);

			return com.liferay.asset.lists.model.AssetListSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.lists.model.AssetListSoap deleteAssetList(
		long assetListId) throws RemoteException {
		try {
			com.liferay.asset.lists.model.AssetList returnValue = AssetListServiceUtil.deleteAssetList(assetListId);

			return com.liferay.asset.lists.model.AssetListSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.lists.model.AssetListSoap fetchAssetList(
		long assetListId) throws RemoteException {
		try {
			com.liferay.asset.lists.model.AssetList returnValue = AssetListServiceUtil.fetchAssetList(assetListId);

			return com.liferay.asset.lists.model.AssetListSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.asset.lists.model.AssetListSoap updateAssetList(
		long assetListId, String[] nameMapLanguageIds, String[] nameMapValues,
		String[] descriptionMapLanguageIds, String[] descriptionMapValues)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.asset.lists.model.AssetList returnValue = AssetListServiceUtil.updateAssetList(assetListId,
					nameMap, descriptionMap);

			return com.liferay.asset.lists.model.AssetListSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetListServiceSoap.class);
}