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

import com.liferay.asset.lists.model.AssetList;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for AssetList. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListServiceUtil
 * @see com.liferay.asset.lists.service.base.AssetListServiceBaseImpl
 * @see com.liferay.asset.lists.service.impl.AssetListServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=fragment", "json.web.service.context.path=AssetList"}, service = AssetListService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AssetListService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListServiceUtil} to access the asset list remote service. Add custom service methods to {@link com.liferay.asset.lists.service.impl.AssetListServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public AssetList addAssetList(long userId, long groupId,
		Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
		int type, ServiceContext serviceContext) throws PortalException;

	public AssetList deleteAssetList(long assetListId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetList fetchAssetList(long assetListId) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public AssetList updateAssetList(long assetListId,
		Map<Locale, String> nameMap, Map<Locale, String> descriptionMap)
		throws PortalException;
}