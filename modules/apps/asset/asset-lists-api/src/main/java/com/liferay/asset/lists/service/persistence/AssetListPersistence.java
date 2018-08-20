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

package com.liferay.asset.lists.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.lists.exception.NoSuchAssetListException;
import com.liferay.asset.lists.model.AssetList;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the asset list service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.lists.service.persistence.impl.AssetListPersistenceImpl
 * @see AssetListUtil
 * @generated
 */
@ProviderType
public interface AssetListPersistence extends BasePersistence<AssetList> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListUtil} to access the asset list persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching asset lists
	*/
	public java.util.List<AssetList> findByUuid(String uuid);

	/**
	* Returns a range of all the asset lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @return the range of matching asset lists
	*/
	public java.util.List<AssetList> findByUuid(String uuid, int start, int end);

	/**
	* Returns an ordered range of all the asset lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset lists
	*/
	public java.util.List<AssetList> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns an ordered range of all the asset lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset lists
	*/
	public java.util.List<AssetList> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list
	* @throws NoSuchAssetListException if a matching asset list could not be found
	*/
	public AssetList findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Returns the first asset list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public AssetList fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns the last asset list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list
	* @throws NoSuchAssetListException if a matching asset list could not be found
	*/
	public AssetList findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Returns the last asset list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public AssetList fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns the asset lists before and after the current asset list in the ordered set where uuid = &#63;.
	*
	* @param assetListId the primary key of the current asset list
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list
	* @throws NoSuchAssetListException if a asset list with the primary key could not be found
	*/
	public AssetList[] findByUuid_PrevAndNext(long assetListId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Removes all the asset lists where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of asset lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching asset lists
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the asset list where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAssetListException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset list
	* @throws NoSuchAssetListException if a matching asset list could not be found
	*/
	public AssetList findByUUID_G(String uuid, long groupId)
		throws NoSuchAssetListException;

	/**
	* Returns the asset list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public AssetList fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the asset list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public AssetList fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the asset list where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the asset list that was removed
	*/
	public AssetList removeByUUID_G(String uuid, long groupId)
		throws NoSuchAssetListException;

	/**
	* Returns the number of asset lists where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching asset lists
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the asset lists where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching asset lists
	*/
	public java.util.List<AssetList> findByUuid_C(String uuid, long companyId);

	/**
	* Returns a range of all the asset lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @return the range of matching asset lists
	*/
	public java.util.List<AssetList> findByUuid_C(String uuid, long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the asset lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset lists
	*/
	public java.util.List<AssetList> findByUuid_C(String uuid, long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns an ordered range of all the asset lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset lists
	*/
	public java.util.List<AssetList> findByUuid_C(String uuid, long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list
	* @throws NoSuchAssetListException if a matching asset list could not be found
	*/
	public AssetList findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Returns the first asset list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public AssetList fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns the last asset list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list
	* @throws NoSuchAssetListException if a matching asset list could not be found
	*/
	public AssetList findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Returns the last asset list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public AssetList fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns the asset lists before and after the current asset list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param assetListId the primary key of the current asset list
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list
	* @throws NoSuchAssetListException if a asset list with the primary key could not be found
	*/
	public AssetList[] findByUuid_C_PrevAndNext(long assetListId, String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Removes all the asset lists where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of asset lists where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching asset lists
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the asset lists where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset lists
	*/
	public java.util.List<AssetList> findByGroupId(long groupId);

	/**
	* Returns a range of all the asset lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @return the range of matching asset lists
	*/
	public java.util.List<AssetList> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the asset lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset lists
	*/
	public java.util.List<AssetList> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns an ordered range of all the asset lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset lists
	*/
	public java.util.List<AssetList> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list
	* @throws NoSuchAssetListException if a matching asset list could not be found
	*/
	public AssetList findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Returns the first asset list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public AssetList fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns the last asset list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list
	* @throws NoSuchAssetListException if a matching asset list could not be found
	*/
	public AssetList findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Returns the last asset list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public AssetList fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns the asset lists before and after the current asset list in the ordered set where groupId = &#63;.
	*
	* @param assetListId the primary key of the current asset list
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list
	* @throws NoSuchAssetListException if a asset list with the primary key could not be found
	*/
	public AssetList[] findByGroupId_PrevAndNext(long assetListId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Returns all the asset lists that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching asset lists that the user has permission to view
	*/
	public java.util.List<AssetList> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the asset lists that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @return the range of matching asset lists that the user has permission to view
	*/
	public java.util.List<AssetList> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the asset lists that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset lists that the user has permission to view
	*/
	public java.util.List<AssetList> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns the asset lists before and after the current asset list in the ordered set of asset lists that the user has permission to view where groupId = &#63;.
	*
	* @param assetListId the primary key of the current asset list
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list
	* @throws NoSuchAssetListException if a asset list with the primary key could not be found
	*/
	public AssetList[] filterFindByGroupId_PrevAndNext(long assetListId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Removes all the asset lists where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of asset lists where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset lists
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of asset lists that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching asset lists that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the asset lists where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching asset lists
	*/
	public java.util.List<AssetList> findByG_T(long groupId, int type);

	/**
	* Returns a range of all the asset lists where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @return the range of matching asset lists
	*/
	public java.util.List<AssetList> findByG_T(long groupId, int type,
		int start, int end);

	/**
	* Returns an ordered range of all the asset lists where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset lists
	*/
	public java.util.List<AssetList> findByG_T(long groupId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns an ordered range of all the asset lists where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset lists
	*/
	public java.util.List<AssetList> findByG_T(long groupId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset list in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list
	* @throws NoSuchAssetListException if a matching asset list could not be found
	*/
	public AssetList findByG_T_First(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Returns the first asset list in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public AssetList fetchByG_T_First(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns the last asset list in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list
	* @throws NoSuchAssetListException if a matching asset list could not be found
	*/
	public AssetList findByG_T_Last(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Returns the last asset list in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset list, or <code>null</code> if a matching asset list could not be found
	*/
	public AssetList fetchByG_T_Last(long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns the asset lists before and after the current asset list in the ordered set where groupId = &#63; and type = &#63;.
	*
	* @param assetListId the primary key of the current asset list
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list
	* @throws NoSuchAssetListException if a asset list with the primary key could not be found
	*/
	public AssetList[] findByG_T_PrevAndNext(long assetListId, long groupId,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Returns all the asset lists that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the matching asset lists that the user has permission to view
	*/
	public java.util.List<AssetList> filterFindByG_T(long groupId, int type);

	/**
	* Returns a range of all the asset lists that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @return the range of matching asset lists that the user has permission to view
	*/
	public java.util.List<AssetList> filterFindByG_T(long groupId, int type,
		int start, int end);

	/**
	* Returns an ordered range of all the asset lists that the user has permissions to view where groupId = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param type the type
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset lists that the user has permission to view
	*/
	public java.util.List<AssetList> filterFindByG_T(long groupId, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns the asset lists before and after the current asset list in the ordered set of asset lists that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param assetListId the primary key of the current asset list
	* @param groupId the group ID
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset list
	* @throws NoSuchAssetListException if a asset list with the primary key could not be found
	*/
	public AssetList[] filterFindByG_T_PrevAndNext(long assetListId,
		long groupId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException;

	/**
	* Removes all the asset lists where groupId = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param type the type
	*/
	public void removeByG_T(long groupId, int type);

	/**
	* Returns the number of asset lists where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching asset lists
	*/
	public int countByG_T(long groupId, int type);

	/**
	* Returns the number of asset lists that the user has permission to view where groupId = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param type the type
	* @return the number of matching asset lists that the user has permission to view
	*/
	public int filterCountByG_T(long groupId, int type);

	/**
	* Caches the asset list in the entity cache if it is enabled.
	*
	* @param assetList the asset list
	*/
	public void cacheResult(AssetList assetList);

	/**
	* Caches the asset lists in the entity cache if it is enabled.
	*
	* @param assetLists the asset lists
	*/
	public void cacheResult(java.util.List<AssetList> assetLists);

	/**
	* Creates a new asset list with the primary key. Does not add the asset list to the database.
	*
	* @param assetListId the primary key for the new asset list
	* @return the new asset list
	*/
	public AssetList create(long assetListId);

	/**
	* Removes the asset list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param assetListId the primary key of the asset list
	* @return the asset list that was removed
	* @throws NoSuchAssetListException if a asset list with the primary key could not be found
	*/
	public AssetList remove(long assetListId) throws NoSuchAssetListException;

	public AssetList updateImpl(AssetList assetList);

	/**
	* Returns the asset list with the primary key or throws a {@link NoSuchAssetListException} if it could not be found.
	*
	* @param assetListId the primary key of the asset list
	* @return the asset list
	* @throws NoSuchAssetListException if a asset list with the primary key could not be found
	*/
	public AssetList findByPrimaryKey(long assetListId)
		throws NoSuchAssetListException;

	/**
	* Returns the asset list with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param assetListId the primary key of the asset list
	* @return the asset list, or <code>null</code> if a asset list with the primary key could not be found
	*/
	public AssetList fetchByPrimaryKey(long assetListId);

	@Override
	public java.util.Map<java.io.Serializable, AssetList> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset lists.
	*
	* @return the asset lists
	*/
	public java.util.List<AssetList> findAll();

	/**
	* Returns a range of all the asset lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @return the range of asset lists
	*/
	public java.util.List<AssetList> findAll(int start, int end);

	/**
	* Returns an ordered range of all the asset lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset lists
	*/
	public java.util.List<AssetList> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator);

	/**
	* Returns an ordered range of all the asset lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset lists
	* @param end the upper bound of the range of asset lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset lists
	*/
	public java.util.List<AssetList> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset lists from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset lists.
	*
	* @return the number of asset lists
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}