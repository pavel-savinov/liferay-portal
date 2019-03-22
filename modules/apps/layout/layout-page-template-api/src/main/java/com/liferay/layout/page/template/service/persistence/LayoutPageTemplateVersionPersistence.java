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

package com.liferay.layout.page.template.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateVersionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateVersion;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the layout page template version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateVersionUtil
 * @generated
 */
@ProviderType
public interface LayoutPageTemplateVersionPersistence
	extends BasePersistence<LayoutPageTemplateVersion> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPageTemplateVersionUtil} to access the layout page template version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the layout page template versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findByUuid(String uuid);

	/**
	 * Returns a range of all the layout page template versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @return the range of matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout page template version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Returns the first layout page template version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator);

	/**
	 * Returns the last layout page template version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Returns the last layout page template version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator);

	/**
	 * Returns the layout page template versions before and after the current layout page template version in the ordered set where uuid = &#63;.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the current layout page template version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template version
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	public LayoutPageTemplateVersion[] findByUuid_PrevAndNext(
			long layoutPageTemplateVersionId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Removes all the layout page template versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of layout page template versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout page template versions
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the layout page template version where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPageTemplateVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion findByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Returns the layout page template version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the layout page template version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache);

	/**
	 * Removes the layout page template version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout page template version that was removed
	 */
	public LayoutPageTemplateVersion removeByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Returns the number of layout page template versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout page template versions
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the layout page template versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the layout page template versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @return the range of matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Returns the first layout page template version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Returns the first layout page template version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator);

	/**
	 * Returns the last layout page template version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Returns the last layout page template version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator);

	/**
	 * Returns the layout page template versions before and after the current layout page template version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the current layout page template version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template version
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	public LayoutPageTemplateVersion[] findByUuid_C_PrevAndNext(
			long layoutPageTemplateVersionId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Removes all the layout page template versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of layout page template versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout page template versions
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the layout page template versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion>
		findBylayoutPageTemplateEntryId(long layoutPageTemplateEntryId);

	/**
	 * Returns a range of all the layout page template versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @return the range of matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion>
		findBylayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId, int start, int end);

	/**
	 * Returns an ordered range of all the layout page template versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion>
		findBylayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion>
		findBylayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateVersion> orderByComparator,
			boolean retrieveFromCache);

	/**
	 * Returns the first layout page template version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion findBylayoutPageTemplateEntryId_First(
			long layoutPageTemplateEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Returns the first layout page template version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion fetchBylayoutPageTemplateEntryId_First(
		long layoutPageTemplateEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator);

	/**
	 * Returns the last layout page template version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion findBylayoutPageTemplateEntryId_Last(
			long layoutPageTemplateEntryId,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Returns the last layout page template version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	public LayoutPageTemplateVersion fetchBylayoutPageTemplateEntryId_Last(
		long layoutPageTemplateEntryId,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator);

	/**
	 * Returns the layout page template versions before and after the current layout page template version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the current layout page template version
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template version
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	public LayoutPageTemplateVersion[]
			findBylayoutPageTemplateEntryId_PrevAndNext(
				long layoutPageTemplateVersionId,
				long layoutPageTemplateEntryId,
				com.liferay.portal.kernel.util.OrderByComparator
					<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Removes all the layout page template versions where layoutPageTemplateEntryId = &#63; from the database.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 */
	public void removeBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId);

	/**
	 * Returns the number of layout page template versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the number of matching layout page template versions
	 */
	public int countBylayoutPageTemplateEntryId(long layoutPageTemplateEntryId);

	/**
	 * Caches the layout page template version in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateVersion the layout page template version
	 */
	public void cacheResult(
		LayoutPageTemplateVersion layoutPageTemplateVersion);

	/**
	 * Caches the layout page template versions in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateVersions the layout page template versions
	 */
	public void cacheResult(
		java.util.List<LayoutPageTemplateVersion> layoutPageTemplateVersions);

	/**
	 * Creates a new layout page template version with the primary key. Does not add the layout page template version to the database.
	 *
	 * @param layoutPageTemplateVersionId the primary key for the new layout page template version
	 * @return the new layout page template version
	 */
	public LayoutPageTemplateVersion create(long layoutPageTemplateVersionId);

	/**
	 * Removes the layout page template version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the layout page template version
	 * @return the layout page template version that was removed
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	public LayoutPageTemplateVersion remove(long layoutPageTemplateVersionId)
		throws NoSuchPageTemplateVersionException;

	public LayoutPageTemplateVersion updateImpl(
		LayoutPageTemplateVersion layoutPageTemplateVersion);

	/**
	 * Returns the layout page template version with the primary key or throws a <code>NoSuchPageTemplateVersionException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the layout page template version
	 * @return the layout page template version
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	public LayoutPageTemplateVersion findByPrimaryKey(
			long layoutPageTemplateVersionId)
		throws NoSuchPageTemplateVersionException;

	/**
	 * Returns the layout page template version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the layout page template version
	 * @return the layout page template version, or <code>null</code> if a layout page template version with the primary key could not be found
	 */
	public LayoutPageTemplateVersion fetchByPrimaryKey(
		long layoutPageTemplateVersionId);

	/**
	 * Returns all the layout page template versions.
	 *
	 * @return the layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findAll();

	/**
	 * Returns a range of all the layout page template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @return the range of layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the layout page template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator);

	/**
	 * Returns an ordered range of all the layout page template versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutPageTemplateVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template versions
	 * @param end the upper bound of the range of layout page template versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout page template versions
	 */
	public java.util.List<LayoutPageTemplateVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<LayoutPageTemplateVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	 * Removes all the layout page template versions from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of layout page template versions.
	 *
	 * @return the number of layout page template versions
	 */
	public int countAll();

}