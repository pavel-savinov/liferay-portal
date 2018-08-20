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
 * Provides the local service utility for AssetList. This utility wraps
 * {@link com.liferay.asset.lists.service.impl.AssetListLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListLocalService
 * @see com.liferay.asset.lists.service.base.AssetListLocalServiceBaseImpl
 * @see com.liferay.asset.lists.service.impl.AssetListLocalServiceImpl
 * @generated
 */
@ProviderType
public class AssetListLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.asset.lists.service.impl.AssetListLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the asset list to the database. Also notifies the appropriate model listeners.
	*
	* @param assetList the asset list
	* @return the asset list that was added
	*/
	public static com.liferay.asset.lists.model.AssetList addAssetList(
		com.liferay.asset.lists.model.AssetList assetList) {
		return getService().addAssetList(assetList);
	}

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

	/**
	* Creates a new asset list with the primary key. Does not add the asset list to the database.
	*
	* @param assetListId the primary key for the new asset list
	* @return the new asset list
	*/
	public static com.liferay.asset.lists.model.AssetList createAssetList(
		long assetListId) {
		return getService().createAssetList(assetListId);
	}

	/**
	* Deletes the asset list from the database. Also notifies the appropriate model listeners.
	*
	* @param assetList the asset list
	* @return the asset list that was removed
	*/
	public static com.liferay.asset.lists.model.AssetList deleteAssetList(
		com.liferay.asset.lists.model.AssetList assetList) {
		return getService().deleteAssetList(assetList);
	}

	/**
	* Deletes the asset list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetListId the primary key of the asset list
	* @return the asset list that was removed
	* @throws NoSuchAssetListException
	* @throws PortalException if a asset list with the primary key could not be found
	*/
	public static com.liferay.asset.lists.model.AssetList deleteAssetList(
		long assetListId)
		throws com.liferay.asset.lists.exception.NoSuchAssetListException,
			com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAssetList(assetListId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.lists.model.impl.AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.lists.model.impl.AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.asset.lists.model.AssetList fetchAssetList(
		long assetListId) {
		return getService().fetchAssetList(assetListId);
	}

	/**
	* Returns the asset list matching the UUID and group.
	*
	* @param uuid the asset list's UUID
	* @param groupId the primary key of the group
	* @return the matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public static com.liferay.asset.lists.model.AssetList fetchAssetListByUuidAndGroupId(
		String uuid, long groupId) {
		return getService().fetchAssetListByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the asset list with the primary key.
	*
	* @param assetListId the primary key of the asset list
	* @return the asset list
	* @throws PortalException if a asset list with the primary key could not be found
	*/
	public static com.liferay.asset.lists.model.AssetList getAssetList(
		long assetListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetList(assetListId);
	}

	/**
	* Returns the asset list matching the UUID and group.
	*
	* @param uuid the asset list's UUID
	* @param groupId the primary key of the group
	* @return the matching asset list
	* @throws PortalException if a matching asset list could not be found
	*/
	public static com.liferay.asset.lists.model.AssetList getAssetListByUuidAndGroupId(
		String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetListByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the asset lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.lists.model.impl.AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @return the range of asset lists
	*/
	public static java.util.List<com.liferay.asset.lists.model.AssetList> getAssetLists(
		int start, int end) {
		return getService().getAssetLists(start, end);
	}

	/**
	* Returns all the asset lists matching the UUID and company.
	*
	* @param uuid the UUID of the asset lists
	* @param companyId the primary key of the company
	* @return the matching asset lists, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.asset.lists.model.AssetList> getAssetListsByUuidAndCompanyId(
		String uuid, long companyId) {
		return getService().getAssetListsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of asset lists matching the UUID and company.
	*
	* @param uuid the UUID of the asset lists
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching asset lists, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.asset.lists.model.AssetList> getAssetListsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.lists.model.AssetList> orderByComparator) {
		return getService()
				   .getAssetListsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of asset lists.
	*
	* @return the number of asset lists
	*/
	public static int getAssetListsCount() {
		return getService().getAssetListsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the asset list in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetList the asset list
	* @return the asset list that was updated
	*/
	public static com.liferay.asset.lists.model.AssetList updateAssetList(
		com.liferay.asset.lists.model.AssetList assetList) {
		return getService().updateAssetList(assetList);
	}

	public static com.liferay.asset.lists.model.AssetList updateAssetList(
		long assetListId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateAssetList(assetListId, nameMap, descriptionMap);
	}

	public static AssetListLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetListLocalService, AssetListLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AssetListLocalService.class);

		ServiceTracker<AssetListLocalService, AssetListLocalService> serviceTracker =
			new ServiceTracker<AssetListLocalService, AssetListLocalService>(bundle.getBundleContext(),
				AssetListLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}