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

package com.liferay.layout.page.template.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for LayoutPageTemplateVersion. This utility wraps
 * <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateVersionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateVersionLocalService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateVersionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateVersionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the layout page template version to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateVersion the layout page template version
	 * @return the layout page template version that was added
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
			addLayoutPageTemplateVersion(
				com.liferay.layout.page.template.model.LayoutPageTemplateVersion
					layoutPageTemplateVersion) {

		return getService().addLayoutPageTemplateVersion(
			layoutPageTemplateVersion);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
				addLayoutPageTemplateVersion(
					long userId, long groupId, long layoutPageTemplateEntryId,
					double version, String name, long classNameId,
					long classTypeId, int type,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addLayoutPageTemplateVersion(
			userId, groupId, layoutPageTemplateEntryId, version, name,
			classNameId, classTypeId, type, serviceContext);
	}

	/**
	 * Creates a new layout page template version with the primary key. Does not add the layout page template version to the database.
	 *
	 * @param layoutPageTemplateVersionId the primary key for the new layout page template version
	 * @return the new layout page template version
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
			createLayoutPageTemplateVersion(long layoutPageTemplateVersionId) {

		return getService().createLayoutPageTemplateVersion(
			layoutPageTemplateVersionId);
	}

	/**
	 * Deletes the layout page template version from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateVersion the layout page template version
	 * @return the layout page template version that was removed
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
			deleteLayoutPageTemplateVersion(
				com.liferay.layout.page.template.model.LayoutPageTemplateVersion
					layoutPageTemplateVersion) {

		return getService().deleteLayoutPageTemplateVersion(
			layoutPageTemplateVersion);
	}

	/**
	 * Deletes the layout page template version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the layout page template version
	 * @return the layout page template version that was removed
	 * @throws PortalException if a layout page template version with the primary key could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
				deleteLayoutPageTemplateVersion(
					long layoutPageTemplateVersionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutPageTemplateVersion(
			layoutPageTemplateVersionId);
	}

	public static void deleteLayoutPageTemplateVersions(
		long layoutPageTemplateEntryId) {

		getService().deleteLayoutPageTemplateVersions(
			layoutPageTemplateEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
			fetchLatestLayoutPageTemplateVersion(
				long layoutPageTemplateEntryId) {

		return getService().fetchLatestLayoutPageTemplateVersion(
			layoutPageTemplateEntryId);
	}

	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
			fetchLayoutPageTemplateVersion(long layoutPageTemplateVersionId) {

		return getService().fetchLayoutPageTemplateVersion(
			layoutPageTemplateVersionId);
	}

	/**
	 * Returns the layout page template version matching the UUID and group.
	 *
	 * @param uuid the layout page template version's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
			fetchLayoutPageTemplateVersionByUuidAndGroupId(
				String uuid, long groupId) {

		return getService().fetchLayoutPageTemplateVersionByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the layout page template version with the primary key.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the layout page template version
	 * @return the layout page template version
	 * @throws PortalException if a layout page template version with the primary key could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
				getLayoutPageTemplateVersion(long layoutPageTemplateVersionId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutPageTemplateVersion(
			layoutPageTemplateVersionId);
	}

	/**
	 * Returns the layout page template version matching the UUID and group.
	 *
	 * @param uuid the layout page template version's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template version
	 * @throws PortalException if a matching layout page template version could not be found
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
				getLayoutPageTemplateVersionByUuidAndGroupId(
					String uuid, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutPageTemplateVersionByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the layout page template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @return the range of layout page template versions
	 */
	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateVersion>
			getLayoutPageTemplateVersions(int start, int end) {

		return getService().getLayoutPageTemplateVersions(start, end);
	}

	/**
	 * Returns all the layout page template versions matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template versions
	 * @param companyId the primary key of the company
	 * @return the matching layout page template versions, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateVersion>
			getLayoutPageTemplateVersionsByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getLayoutPageTemplateVersionsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of layout page template versions matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template versions
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout page template versions, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.page.template.model.LayoutPageTemplateVersion>
			getLayoutPageTemplateVersionsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.page.template.model.
						LayoutPageTemplateVersion> orderByComparator) {

		return getService().getLayoutPageTemplateVersionsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout page template versions.
	 *
	 * @return the number of layout page template versions
	 */
	public static int getLayoutPageTemplateVersionsCount() {
		return getService().getLayoutPageTemplateVersionsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the layout page template version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateVersion the layout page template version
	 * @return the layout page template version that was updated
	 */
	public static
		com.liferay.layout.page.template.model.LayoutPageTemplateVersion
			updateLayoutPageTemplateVersion(
				com.liferay.layout.page.template.model.LayoutPageTemplateVersion
					layoutPageTemplateVersion) {

		return getService().updateLayoutPageTemplateVersion(
			layoutPageTemplateVersion);
	}

	public static LayoutPageTemplateVersionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutPageTemplateVersionLocalService,
		 LayoutPageTemplateVersionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutPageTemplateVersionLocalService.class);

		ServiceTracker
			<LayoutPageTemplateVersionLocalService,
			 LayoutPageTemplateVersionLocalService> serviceTracker =
				new ServiceTracker
					<LayoutPageTemplateVersionLocalService,
					 LayoutPageTemplateVersionLocalService>(
						 bundle.getBundleContext(),
						 LayoutPageTemplateVersionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}