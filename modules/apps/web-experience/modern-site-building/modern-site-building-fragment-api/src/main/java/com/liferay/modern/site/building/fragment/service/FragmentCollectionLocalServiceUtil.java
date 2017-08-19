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

package com.liferay.modern.site.building.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for FragmentCollection. This utility wraps
 * {@link com.liferay.modern.site.building.fragment.service.impl.FragmentCollectionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCollectionLocalService
 * @see com.liferay.modern.site.building.fragment.service.base.FragmentCollectionLocalServiceBaseImpl
 * @see com.liferay.modern.site.building.fragment.service.impl.FragmentCollectionLocalServiceImpl
 * @generated
 */
@ProviderType
public class FragmentCollectionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.modern.site.building.fragment.service.impl.FragmentCollectionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the fragment collection to the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentCollection the fragment collection
	* @return the fragment collection that was added
	*/
	public static com.liferay.modern.site.building.fragment.model.FragmentCollection addFragmentCollection(
		com.liferay.modern.site.building.fragment.model.FragmentCollection fragmentCollection) {
		return getService().addFragmentCollection(fragmentCollection);
	}

	/**
	* Creates a new fragment collection with the primary key. Does not add the fragment collection to the database.
	*
	* @param fragmentCollectionId the primary key for the new fragment collection
	* @return the new fragment collection
	*/
	public static com.liferay.modern.site.building.fragment.model.FragmentCollection createFragmentCollection(
		long fragmentCollectionId) {
		return getService().createFragmentCollection(fragmentCollectionId);
	}

	/**
	* Deletes the fragment collection from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentCollection the fragment collection
	* @return the fragment collection that was removed
	*/
	public static com.liferay.modern.site.building.fragment.model.FragmentCollection deleteFragmentCollection(
		com.liferay.modern.site.building.fragment.model.FragmentCollection fragmentCollection) {
		return getService().deleteFragmentCollection(fragmentCollection);
	}

	/**
	* Deletes the fragment collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fragmentCollectionId the primary key of the fragment collection
	* @return the fragment collection that was removed
	* @throws PortalException if a fragment collection with the primary key could not be found
	*/
	public static com.liferay.modern.site.building.fragment.model.FragmentCollection deleteFragmentCollection(
		long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteFragmentCollection(fragmentCollectionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.modern.site.building.fragment.model.FragmentCollection fetchFragmentCollection(
		long fragmentCollectionId) {
		return getService().fetchFragmentCollection(fragmentCollectionId);
	}

	/**
	* Returns the fragment collection matching the UUID and group.
	*
	* @param uuid the fragment collection's UUID
	* @param groupId the primary key of the group
	* @return the matching fragment collection, or <code>null</code> if a matching fragment collection could not be found
	*/
	public static com.liferay.modern.site.building.fragment.model.FragmentCollection fetchFragmentCollectionByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchFragmentCollectionByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* Returns the fragment collection with the primary key.
	*
	* @param fragmentCollectionId the primary key of the fragment collection
	* @return the fragment collection
	* @throws PortalException if a fragment collection with the primary key could not be found
	*/
	public static com.liferay.modern.site.building.fragment.model.FragmentCollection getFragmentCollection(
		long fragmentCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFragmentCollection(fragmentCollectionId);
	}

	/**
	* Returns the fragment collection matching the UUID and group.
	*
	* @param uuid the fragment collection's UUID
	* @param groupId the primary key of the group
	* @return the matching fragment collection
	* @throws PortalException if a matching fragment collection could not be found
	*/
	public static com.liferay.modern.site.building.fragment.model.FragmentCollection getFragmentCollectionByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFragmentCollectionByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the fragment collections.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.FragmentCollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @return the range of fragment collections
	*/
	public static java.util.List<com.liferay.modern.site.building.fragment.model.FragmentCollection> getFragmentCollections(
		int start, int end) {
		return getService().getFragmentCollections(start, end);
	}

	/**
	* Returns all the fragment collections matching the UUID and company.
	*
	* @param uuid the UUID of the fragment collections
	* @param companyId the primary key of the company
	* @return the matching fragment collections, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.modern.site.building.fragment.model.FragmentCollection> getFragmentCollectionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getFragmentCollectionsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of fragment collections matching the UUID and company.
	*
	* @param uuid the UUID of the fragment collections
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of fragment collections
	* @param end the upper bound of the range of fragment collections (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching fragment collections, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.modern.site.building.fragment.model.FragmentCollection> getFragmentCollectionsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.FragmentCollection> orderByComparator) {
		return getService()
				   .getFragmentCollectionsByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of fragment collections.
	*
	* @return the number of fragment collections
	*/
	public static int getFragmentCollectionsCount() {
		return getService().getFragmentCollectionsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the fragment collection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param fragmentCollection the fragment collection
	* @return the fragment collection that was updated
	*/
	public static com.liferay.modern.site.building.fragment.model.FragmentCollection updateFragmentCollection(
		com.liferay.modern.site.building.fragment.model.FragmentCollection fragmentCollection) {
		return getService().updateFragmentCollection(fragmentCollection);
	}

	public static FragmentCollectionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FragmentCollectionLocalService, FragmentCollectionLocalService> _serviceTracker =
		ServiceTrackerFactory.open(FragmentCollectionLocalService.class);
}