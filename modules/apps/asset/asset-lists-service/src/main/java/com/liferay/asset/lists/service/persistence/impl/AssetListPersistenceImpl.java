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

package com.liferay.asset.lists.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.lists.exception.NoSuchAssetListException;
import com.liferay.asset.lists.model.AssetList;
import com.liferay.asset.lists.model.impl.AssetListImpl;
import com.liferay.asset.lists.model.impl.AssetListModelImpl;
import com.liferay.asset.lists.service.persistence.AssetListPersistence;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the asset list service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetListPersistence
 * @see com.liferay.asset.lists.service.persistence.AssetListUtil
 * @generated
 */
@ProviderType
public class AssetListPersistenceImpl extends BasePersistenceImpl<AssetList>
	implements AssetListPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetListUtil} to access the asset list persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetListImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			AssetListModelImpl.UUID_COLUMN_BITMASK |
			AssetListModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the asset lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset lists
	 */
	@Override
	public List<AssetList> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<AssetList> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

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
	@Override
	public List<AssetList> findByUuid(String uuid, int start, int end,
		OrderByComparator<AssetList> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

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
	@Override
	public List<AssetList> findByUuid(String uuid, int start, int end,
		OrderByComparator<AssetList> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<AssetList> list = null;

		if (retrieveFromCache) {
			list = (List<AssetList>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetList assetList : list) {
					if (!Objects.equals(uuid, assetList.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETLIST_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetListModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<AssetList>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetList>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list
	 * @throws NoSuchAssetListException if a matching asset list could not be found
	 */
	@Override
	public AssetList findByUuid_First(String uuid,
		OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = fetchByUuid_First(uuid, orderByComparator);

		if (assetList != null) {
			return assetList;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchAssetListException(msg.toString());
	}

	/**
	 * Returns the first asset list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list, or <code>null</code> if a matching asset list could not be found
	 */
	@Override
	public AssetList fetchByUuid_First(String uuid,
		OrderByComparator<AssetList> orderByComparator) {
		List<AssetList> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list
	 * @throws NoSuchAssetListException if a matching asset list could not be found
	 */
	@Override
	public AssetList findByUuid_Last(String uuid,
		OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = fetchByUuid_Last(uuid, orderByComparator);

		if (assetList != null) {
			return assetList;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchAssetListException(msg.toString());
	}

	/**
	 * Returns the last asset list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list, or <code>null</code> if a matching asset list could not be found
	 */
	@Override
	public AssetList fetchByUuid_Last(String uuid,
		OrderByComparator<AssetList> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetList> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset lists before and after the current asset list in the ordered set where uuid = &#63;.
	 *
	 * @param assetListId the primary key of the current asset list
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list
	 * @throws NoSuchAssetListException if a asset list with the primary key could not be found
	 */
	@Override
	public AssetList[] findByUuid_PrevAndNext(long assetListId, String uuid,
		OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = findByPrimaryKey(assetListId);

		Session session = null;

		try {
			session = openSession();

			AssetList[] array = new AssetListImpl[3];

			array[0] = getByUuid_PrevAndNext(session, assetList, uuid,
					orderByComparator, true);

			array[1] = assetList;

			array[2] = getByUuid_PrevAndNext(session, assetList, uuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetList getByUuid_PrevAndNext(Session session,
		AssetList assetList, String uuid,
		OrderByComparator<AssetList> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLIST_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals("")) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetListModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetList);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetList> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset lists where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetList assetList : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetList);
		}
	}

	/**
	 * Returns the number of asset lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset lists
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLIST_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "assetList.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "assetList.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(assetList.uuid IS NULL OR assetList.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			AssetListModelImpl.UUID_COLUMN_BITMASK |
			AssetListModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the asset list where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAssetListException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list
	 * @throws NoSuchAssetListException if a matching asset list could not be found
	 */
	@Override
	public AssetList findByUUID_G(String uuid, long groupId)
		throws NoSuchAssetListException {
		AssetList assetList = fetchByUUID_G(uuid, groupId);

		if (assetList == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAssetListException(msg.toString());
		}

		return assetList;
	}

	/**
	 * Returns the asset list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list, or <code>null</code> if a matching asset list could not be found
	 */
	@Override
	public AssetList fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching asset list, or <code>null</code> if a matching asset list could not be found
	 */
	@Override
	public AssetList fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof AssetList) {
			AssetList assetList = (AssetList)result;

			if (!Objects.equals(uuid, assetList.getUuid()) ||
					(groupId != assetList.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ASSETLIST_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<AssetList> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					AssetList assetList = list.get(0);

					result = assetList;

					cacheResult(assetList);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (AssetList)result;
		}
	}

	/**
	 * Removes the asset list where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list that was removed
	 */
	@Override
	public AssetList removeByUUID_G(String uuid, long groupId)
		throws NoSuchAssetListException {
		AssetList assetList = findByUUID_G(uuid, groupId);

		return remove(assetList);
	}

	/**
	 * Returns the number of asset lists where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset lists
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLIST_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "assetList.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "assetList.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(assetList.uuid IS NULL OR assetList.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "assetList.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			AssetListModelImpl.UUID_COLUMN_BITMASK |
			AssetListModelImpl.COMPANYID_COLUMN_BITMASK |
			AssetListModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the asset lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset lists
	 */
	@Override
	public List<AssetList> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<AssetList> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

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
	@Override
	public List<AssetList> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<AssetList> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

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
	@Override
	public List<AssetList> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<AssetList> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<AssetList> list = null;

		if (retrieveFromCache) {
			list = (List<AssetList>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetList assetList : list) {
					if (!Objects.equals(uuid, assetList.getUuid()) ||
							(companyId != assetList.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETLIST_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetListModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<AssetList>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetList>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list
	 * @throws NoSuchAssetListException if a matching asset list could not be found
	 */
	@Override
	public AssetList findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (assetList != null) {
			return assetList;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAssetListException(msg.toString());
	}

	/**
	 * Returns the first asset list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list, or <code>null</code> if a matching asset list could not be found
	 */
	@Override
	public AssetList fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<AssetList> orderByComparator) {
		List<AssetList> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list
	 * @throws NoSuchAssetListException if a matching asset list could not be found
	 */
	@Override
	public AssetList findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (assetList != null) {
			return assetList;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAssetListException(msg.toString());
	}

	/**
	 * Returns the last asset list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list, or <code>null</code> if a matching asset list could not be found
	 */
	@Override
	public AssetList fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<AssetList> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetList> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

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
	@Override
	public AssetList[] findByUuid_C_PrevAndNext(long assetListId, String uuid,
		long companyId, OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = findByPrimaryKey(assetListId);

		Session session = null;

		try {
			session = openSession();

			AssetList[] array = new AssetListImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, assetList, uuid,
					companyId, orderByComparator, true);

			array[1] = assetList;

			array[2] = getByUuid_C_PrevAndNext(session, assetList, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetList getByUuid_C_PrevAndNext(Session session,
		AssetList assetList, String uuid, long companyId,
		OrderByComparator<AssetList> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ASSETLIST_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals("")) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetListModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetList);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetList> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset lists where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetList assetList : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetList);
		}
	}

	/**
	 * Returns the number of asset lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset lists
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLIST_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "assetList.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "assetList.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(assetList.uuid IS NULL OR assetList.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "assetList.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			AssetListModelImpl.GROUPID_COLUMN_BITMASK |
			AssetListModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the asset lists where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset lists
	 */
	@Override
	public List<AssetList> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<AssetList> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

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
	@Override
	public List<AssetList> findByGroupId(long groupId, int start, int end,
		OrderByComparator<AssetList> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

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
	@Override
	public List<AssetList> findByGroupId(long groupId, int start, int end,
		OrderByComparator<AssetList> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<AssetList> list = null;

		if (retrieveFromCache) {
			list = (List<AssetList>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetList assetList : list) {
					if ((groupId != assetList.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ASSETLIST_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetListModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<AssetList>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetList>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list
	 * @throws NoSuchAssetListException if a matching asset list could not be found
	 */
	@Override
	public AssetList findByGroupId_First(long groupId,
		OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = fetchByGroupId_First(groupId, orderByComparator);

		if (assetList != null) {
			return assetList;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchAssetListException(msg.toString());
	}

	/**
	 * Returns the first asset list in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list, or <code>null</code> if a matching asset list could not be found
	 */
	@Override
	public AssetList fetchByGroupId_First(long groupId,
		OrderByComparator<AssetList> orderByComparator) {
		List<AssetList> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list
	 * @throws NoSuchAssetListException if a matching asset list could not be found
	 */
	@Override
	public AssetList findByGroupId_Last(long groupId,
		OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = fetchByGroupId_Last(groupId, orderByComparator);

		if (assetList != null) {
			return assetList;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchAssetListException(msg.toString());
	}

	/**
	 * Returns the last asset list in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list, or <code>null</code> if a matching asset list could not be found
	 */
	@Override
	public AssetList fetchByGroupId_Last(long groupId,
		OrderByComparator<AssetList> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AssetList> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset lists before and after the current asset list in the ordered set where groupId = &#63;.
	 *
	 * @param assetListId the primary key of the current asset list
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list
	 * @throws NoSuchAssetListException if a asset list with the primary key could not be found
	 */
	@Override
	public AssetList[] findByGroupId_PrevAndNext(long assetListId,
		long groupId, OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = findByPrimaryKey(assetListId);

		Session session = null;

		try {
			session = openSession();

			AssetList[] array = new AssetListImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, assetList, groupId,
					orderByComparator, true);

			array[1] = assetList;

			array[2] = getByGroupId_PrevAndNext(session, assetList, groupId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetList getByGroupId_PrevAndNext(Session session,
		AssetList assetList, long groupId,
		OrderByComparator<AssetList> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETLIST_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetListModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetList);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetList> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset lists that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset lists that the user has permission to view
	 */
	@Override
	public List<AssetList> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<AssetList> filterFindByGroupId(long groupId, int start, int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

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
	@Override
	public List<AssetList> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<AssetList> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetListModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetListModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetList.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetListImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetListImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<AssetList>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset lists before and after the current asset list in the ordered set of asset lists that the user has permission to view where groupId = &#63;.
	 *
	 * @param assetListId the primary key of the current asset list
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list
	 * @throws NoSuchAssetListException if a asset list with the primary key could not be found
	 */
	@Override
	public AssetList[] filterFindByGroupId_PrevAndNext(long assetListId,
		long groupId, OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(assetListId, groupId,
				orderByComparator);
		}

		AssetList assetList = findByPrimaryKey(assetListId);

		Session session = null;

		try {
			session = openSession();

			AssetList[] array = new AssetListImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, assetList,
					groupId, orderByComparator, true);

			array[1] = assetList;

			array[2] = filterGetByGroupId_PrevAndNext(session, assetList,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetList filterGetByGroupId_PrevAndNext(Session session,
		AssetList assetList, long groupId,
		OrderByComparator<AssetList> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetListModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetListModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetList.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetListImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetListImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetList);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetList> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset lists where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AssetList assetList : findByGroupId(groupId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetList);
		}
	}

	/**
	 * Returns the number of asset lists where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset lists
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETLIST_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset lists that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset lists that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_ASSETLIST_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetList.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "assetList.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, AssetListImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			AssetListModelImpl.GROUPID_COLUMN_BITMASK |
			AssetListModelImpl.TYPE_COLUMN_BITMASK |
			AssetListModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the asset lists where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching asset lists
	 */
	@Override
	public List<AssetList> findByG_T(long groupId, int type) {
		return findByG_T(groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

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
	@Override
	public List<AssetList> findByG_T(long groupId, int type, int start, int end) {
		return findByG_T(groupId, type, start, end, null);
	}

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
	@Override
	public List<AssetList> findByG_T(long groupId, int type, int start,
		int end, OrderByComparator<AssetList> orderByComparator) {
		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

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
	@Override
	public List<AssetList> findByG_T(long groupId, int type, int start,
		int end, OrderByComparator<AssetList> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] { groupId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] {
					groupId, type,
					
					start, end, orderByComparator
				};
		}

		List<AssetList> list = null;

		if (retrieveFromCache) {
			list = (List<AssetList>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetList assetList : list) {
					if ((groupId != assetList.getGroupId()) ||
							(type != assetList.getType())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETLIST_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetListModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				if (!pagination) {
					list = (List<AssetList>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetList>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list
	 * @throws NoSuchAssetListException if a matching asset list could not be found
	 */
	@Override
	public AssetList findByG_T_First(long groupId, int type,
		OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = fetchByG_T_First(groupId, type, orderByComparator);

		if (assetList != null) {
			return assetList;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchAssetListException(msg.toString());
	}

	/**
	 * Returns the first asset list in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list, or <code>null</code> if a matching asset list could not be found
	 */
	@Override
	public AssetList fetchByG_T_First(long groupId, int type,
		OrderByComparator<AssetList> orderByComparator) {
		List<AssetList> list = findByG_T(groupId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list
	 * @throws NoSuchAssetListException if a matching asset list could not be found
	 */
	@Override
	public AssetList findByG_T_Last(long groupId, int type,
		OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = fetchByG_T_Last(groupId, type, orderByComparator);

		if (assetList != null) {
			return assetList;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchAssetListException(msg.toString());
	}

	/**
	 * Returns the last asset list in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list, or <code>null</code> if a matching asset list could not be found
	 */
	@Override
	public AssetList fetchByG_T_Last(long groupId, int type,
		OrderByComparator<AssetList> orderByComparator) {
		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<AssetList> list = findByG_T(groupId, type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

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
	@Override
	public AssetList[] findByG_T_PrevAndNext(long assetListId, long groupId,
		int type, OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		AssetList assetList = findByPrimaryKey(assetListId);

		Session session = null;

		try {
			session = openSession();

			AssetList[] array = new AssetListImpl[3];

			array[0] = getByG_T_PrevAndNext(session, assetList, groupId, type,
					orderByComparator, true);

			array[1] = assetList;

			array[2] = getByG_T_PrevAndNext(session, assetList, groupId, type,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetList getByG_T_PrevAndNext(Session session,
		AssetList assetList, long groupId, int type,
		OrderByComparator<AssetList> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ASSETLIST_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetListModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetList);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetList> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset lists that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching asset lists that the user has permission to view
	 */
	@Override
	public List<AssetList> filterFindByG_T(long groupId, int type) {
		return filterFindByG_T(groupId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<AssetList> filterFindByG_T(long groupId, int type, int start,
		int end) {
		return filterFindByG_T(groupId, type, start, end, null);
	}

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
	@Override
	public List<AssetList> filterFindByG_T(long groupId, int type, int start,
		int end, OrderByComparator<AssetList> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T(groupId, type, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetListModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetListModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetList.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, AssetListImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, AssetListImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(type);

			return (List<AssetList>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

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
	@Override
	public AssetList[] filterFindByG_T_PrevAndNext(long assetListId,
		long groupId, int type, OrderByComparator<AssetList> orderByComparator)
		throws NoSuchAssetListException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_PrevAndNext(assetListId, groupId, type,
				orderByComparator);
		}

		AssetList assetList = findByPrimaryKey(assetListId);

		Session session = null;

		try {
			session = openSession();

			AssetList[] array = new AssetListImpl[3];

			array[0] = filterGetByG_T_PrevAndNext(session, assetList, groupId,
					type, orderByComparator, true);

			array[1] = assetList;

			array[2] = filterGetByG_T_PrevAndNext(session, assetList, groupId,
					type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetList filterGetByG_T_PrevAndNext(Session session,
		AssetList assetList, long groupId, int type,
		OrderByComparator<AssetList> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_ASSETLIST_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AssetListModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AssetListModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetList.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AssetListImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AssetListImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetList);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetList> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset lists where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (AssetList assetList : findByG_T(groupId, type, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetList);
		}
	}

	/**
	 * Returns the number of asset lists where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching asset lists
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETLIST_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(type);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset lists that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching asset lists that the user has permission to view
	 */
	@Override
	public int filterCountByG_T(long groupId, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, type);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_ASSETLIST_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				AssetList.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(type);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "assetList.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TYPE_2 = "assetList.type = ?";
	private static final String _FINDER_COLUMN_G_T_TYPE_2_SQL = "assetList.type_ = ?";

	public AssetListPersistenceImpl() {
		setModelClass(AssetList.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the asset list in the entity cache if it is enabled.
	 *
	 * @param assetList the asset list
	 */
	@Override
	public void cacheResult(AssetList assetList) {
		entityCache.putResult(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListImpl.class, assetList.getPrimaryKey(), assetList);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { assetList.getUuid(), assetList.getGroupId() },
			assetList);

		assetList.resetOriginalValues();
	}

	/**
	 * Caches the asset lists in the entity cache if it is enabled.
	 *
	 * @param assetLists the asset lists
	 */
	@Override
	public void cacheResult(List<AssetList> assetLists) {
		for (AssetList assetList : assetLists) {
			if (entityCache.getResult(AssetListModelImpl.ENTITY_CACHE_ENABLED,
						AssetListImpl.class, assetList.getPrimaryKey()) == null) {
				cacheResult(assetList);
			}
			else {
				assetList.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset lists.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetListImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset list.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetList assetList) {
		entityCache.removeResult(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListImpl.class, assetList.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AssetListModelImpl)assetList, true);
	}

	@Override
	public void clearCache(List<AssetList> assetLists) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetList assetList : assetLists) {
			entityCache.removeResult(AssetListModelImpl.ENTITY_CACHE_ENABLED,
				AssetListImpl.class, assetList.getPrimaryKey());

			clearUniqueFindersCache((AssetListModelImpl)assetList, true);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetListModelImpl assetListModelImpl) {
		Object[] args = new Object[] {
				assetListModelImpl.getUuid(), assetListModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			assetListModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetListModelImpl assetListModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					assetListModelImpl.getUuid(),
					assetListModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((assetListModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					assetListModelImpl.getOriginalUuid(),
					assetListModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new asset list with the primary key. Does not add the asset list to the database.
	 *
	 * @param assetListId the primary key for the new asset list
	 * @return the new asset list
	 */
	@Override
	public AssetList create(long assetListId) {
		AssetList assetList = new AssetListImpl();

		assetList.setNew(true);
		assetList.setPrimaryKey(assetListId);

		String uuid = PortalUUIDUtil.generate();

		assetList.setUuid(uuid);

		assetList.setCompanyId(companyProvider.getCompanyId());

		return assetList;
	}

	/**
	 * Removes the asset list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListId the primary key of the asset list
	 * @return the asset list that was removed
	 * @throws NoSuchAssetListException if a asset list with the primary key could not be found
	 */
	@Override
	public AssetList remove(long assetListId) throws NoSuchAssetListException {
		return remove((Serializable)assetListId);
	}

	/**
	 * Removes the asset list with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset list
	 * @return the asset list that was removed
	 * @throws NoSuchAssetListException if a asset list with the primary key could not be found
	 */
	@Override
	public AssetList remove(Serializable primaryKey)
		throws NoSuchAssetListException {
		Session session = null;

		try {
			session = openSession();

			AssetList assetList = (AssetList)session.get(AssetListImpl.class,
					primaryKey);

			if (assetList == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAssetListException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetList);
		}
		catch (NoSuchAssetListException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected AssetList removeImpl(AssetList assetList) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetList)) {
				assetList = (AssetList)session.get(AssetListImpl.class,
						assetList.getPrimaryKeyObj());
			}

			if (assetList != null) {
				session.delete(assetList);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetList != null) {
			clearCache(assetList);
		}

		return assetList;
	}

	@Override
	public AssetList updateImpl(AssetList assetList) {
		boolean isNew = assetList.isNew();

		if (!(assetList instanceof AssetListModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetList.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(assetList);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetList proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetList implementation " +
				assetList.getClass());
		}

		AssetListModelImpl assetListModelImpl = (AssetListModelImpl)assetList;

		if (Validator.isNull(assetList.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetList.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetList.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetList.setCreateDate(now);
			}
			else {
				assetList.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!assetListModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetList.setModifiedDate(now);
			}
			else {
				assetList.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (assetList.isNew()) {
				session.save(assetList);

				assetList.setNew(false);
			}
			else {
				assetList = (AssetList)session.merge(assetList);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetListModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { assetListModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					assetListModelImpl.getUuid(),
					assetListModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { assetListModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					assetListModelImpl.getGroupId(),
					assetListModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((assetListModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetListModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { assetListModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((assetListModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetListModelImpl.getOriginalUuid(),
						assetListModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						assetListModelImpl.getUuid(),
						assetListModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((assetListModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetListModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { assetListModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((assetListModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetListModelImpl.getOriginalGroupId(),
						assetListModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						assetListModelImpl.getGroupId(),
						assetListModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}
		}

		entityCache.putResult(AssetListModelImpl.ENTITY_CACHE_ENABLED,
			AssetListImpl.class, assetList.getPrimaryKey(), assetList, false);

		clearUniqueFindersCache(assetListModelImpl, false);
		cacheUniqueFindersCache(assetListModelImpl);

		assetList.resetOriginalValues();

		return assetList;
	}

	/**
	 * Returns the asset list with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list
	 * @return the asset list
	 * @throws NoSuchAssetListException if a asset list with the primary key could not be found
	 */
	@Override
	public AssetList findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAssetListException {
		AssetList assetList = fetchByPrimaryKey(primaryKey);

		if (assetList == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAssetListException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetList;
	}

	/**
	 * Returns the asset list with the primary key or throws a {@link NoSuchAssetListException} if it could not be found.
	 *
	 * @param assetListId the primary key of the asset list
	 * @return the asset list
	 * @throws NoSuchAssetListException if a asset list with the primary key could not be found
	 */
	@Override
	public AssetList findByPrimaryKey(long assetListId)
		throws NoSuchAssetListException {
		return findByPrimaryKey((Serializable)assetListId);
	}

	/**
	 * Returns the asset list with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list
	 * @return the asset list, or <code>null</code> if a asset list with the primary key could not be found
	 */
	@Override
	public AssetList fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AssetListModelImpl.ENTITY_CACHE_ENABLED,
				AssetListImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetList assetList = (AssetList)serializable;

		if (assetList == null) {
			Session session = null;

			try {
				session = openSession();

				assetList = (AssetList)session.get(AssetListImpl.class,
						primaryKey);

				if (assetList != null) {
					cacheResult(assetList);
				}
				else {
					entityCache.putResult(AssetListModelImpl.ENTITY_CACHE_ENABLED,
						AssetListImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AssetListModelImpl.ENTITY_CACHE_ENABLED,
					AssetListImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetList;
	}

	/**
	 * Returns the asset list with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListId the primary key of the asset list
	 * @return the asset list, or <code>null</code> if a asset list with the primary key could not be found
	 */
	@Override
	public AssetList fetchByPrimaryKey(long assetListId) {
		return fetchByPrimaryKey((Serializable)assetListId);
	}

	@Override
	public Map<Serializable, AssetList> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetList> map = new HashMap<Serializable, AssetList>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetList assetList = fetchByPrimaryKey(primaryKey);

			if (assetList != null) {
				map.put(primaryKey, assetList);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AssetListModelImpl.ENTITY_CACHE_ENABLED,
					AssetListImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AssetList)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ASSETLIST_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (AssetList assetList : (List<AssetList>)q.list()) {
				map.put(assetList.getPrimaryKeyObj(), assetList);

				cacheResult(assetList);

				uncachedPrimaryKeys.remove(assetList.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AssetListModelImpl.ENTITY_CACHE_ENABLED,
					AssetListImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the asset lists.
	 *
	 * @return the asset lists
	 */
	@Override
	public List<AssetList> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<AssetList> findAll(int start, int end) {
		return findAll(start, end, null);
	}

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
	@Override
	public List<AssetList> findAll(int start, int end,
		OrderByComparator<AssetList> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

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
	@Override
	public List<AssetList> findAll(int start, int end,
		OrderByComparator<AssetList> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<AssetList> list = null;

		if (retrieveFromCache) {
			list = (List<AssetList>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETLIST);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETLIST;

				if (pagination) {
					sql = sql.concat(AssetListModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetList>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetList>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset lists from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetList assetList : findAll()) {
			remove(assetList);
		}
	}

	/**
	 * Returns the number of asset lists.
	 *
	 * @return the number of asset lists
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETLIST);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetListModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset list persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AssetListImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_ASSETLIST = "SELECT assetList FROM AssetList assetList";
	private static final String _SQL_SELECT_ASSETLIST_WHERE_PKS_IN = "SELECT assetList FROM AssetList assetList WHERE assetListId IN (";
	private static final String _SQL_SELECT_ASSETLIST_WHERE = "SELECT assetList FROM AssetList assetList WHERE ";
	private static final String _SQL_COUNT_ASSETLIST = "SELECT COUNT(assetList) FROM AssetList assetList";
	private static final String _SQL_COUNT_ASSETLIST_WHERE = "SELECT COUNT(assetList) FROM AssetList assetList WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "assetList.assetListId";
	private static final String _FILTER_SQL_SELECT_ASSETLIST_WHERE = "SELECT DISTINCT {assetList.*} FROM AssetList assetList WHERE ";
	private static final String _FILTER_SQL_SELECT_ASSETLIST_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {AssetList.*} FROM (SELECT DISTINCT assetList.assetListId FROM AssetList assetList WHERE ";
	private static final String _FILTER_SQL_SELECT_ASSETLIST_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN AssetList ON TEMP_TABLE.assetListId = AssetList.assetListId";
	private static final String _FILTER_SQL_COUNT_ASSETLIST_WHERE = "SELECT COUNT(DISTINCT assetList.assetListId) AS COUNT_VALUE FROM AssetList assetList WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "assetList";
	private static final String _FILTER_ENTITY_TABLE = "AssetList";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetList.";
	private static final String _ORDER_BY_ENTITY_TABLE = "AssetList.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetList exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetList exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AssetListPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
}