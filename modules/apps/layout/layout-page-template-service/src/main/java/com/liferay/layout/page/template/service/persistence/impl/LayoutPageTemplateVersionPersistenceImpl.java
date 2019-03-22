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

package com.liferay.layout.page.template.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateVersionException;
import com.liferay.layout.page.template.model.LayoutPageTemplateVersion;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateVersionImpl;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateVersionModelImpl;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateVersionPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the layout page template version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutPageTemplateVersionPersistenceImpl
	extends BasePersistenceImpl<LayoutPageTemplateVersion>
	implements LayoutPageTemplateVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutPageTemplateVersionUtil</code> to access the layout page template version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutPageTemplateVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the layout page template versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout page template versions
	 */
	@Override
	public List<LayoutPageTemplateVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid;
			finderArgs = new Object[] {uuid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<LayoutPageTemplateVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateVersion layoutPageTemplateVersion :
						list) {

					if (!uuid.equals(layoutPageTemplateVersion.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutPageTemplateVersionModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutPageTemplateVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout page template version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion findByUuid_First(
			String uuid,
			OrderByComparator<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException {

		LayoutPageTemplateVersion layoutPageTemplateVersion = fetchByUuid_First(
			uuid, orderByComparator);

		if (layoutPageTemplateVersion != null) {
			return layoutPageTemplateVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchPageTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the first layout page template version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion fetchByUuid_First(
		String uuid,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator) {

		List<LayoutPageTemplateVersion> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException {

		LayoutPageTemplateVersion layoutPageTemplateVersion = fetchByUuid_Last(
			uuid, orderByComparator);

		if (layoutPageTemplateVersion != null) {
			return layoutPageTemplateVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchPageTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the last layout page template version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion fetchByUuid_Last(
		String uuid,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateVersion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template versions before and after the current layout page template version in the ordered set where uuid = &#63;.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the current layout page template version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template version
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateVersion[] findByUuid_PrevAndNext(
			long layoutPageTemplateVersionId, String uuid,
			OrderByComparator<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException {

		uuid = Objects.toString(uuid, "");

		LayoutPageTemplateVersion layoutPageTemplateVersion = findByPrimaryKey(
			layoutPageTemplateVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateVersion[] array =
				new LayoutPageTemplateVersionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, layoutPageTemplateVersion, uuid, orderByComparator,
				true);

			array[1] = layoutPageTemplateVersion;

			array[2] = getByUuid_PrevAndNext(
				session, layoutPageTemplateVersion, uuid, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateVersion getByUuid_PrevAndNext(
		Session session, LayoutPageTemplateVersion layoutPageTemplateVersion,
		String uuid,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutPageTemplateVersionModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutPageTemplateVersion layoutPageTemplateVersion :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateVersion);
		}
	}

	/**
	 * Returns the number of layout page template versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout page template versions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"layoutPageTemplateVersion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(layoutPageTemplateVersion.uuid IS NULL OR layoutPageTemplateVersion.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the layout page template version where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPageTemplateVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion findByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateVersionException {

		LayoutPageTemplateVersion layoutPageTemplateVersion = fetchByUUID_G(
			uuid, groupId);

		if (layoutPageTemplateVersion == null) {
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

			throw new NoSuchPageTemplateVersionException(msg.toString());
		}

		return layoutPageTemplateVersion;
	}

	/**
	 * Returns the layout page template version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the layout page template version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion fetchByUUID_G(
		String uuid, long groupId, boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] {uuid, groupId};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof LayoutPageTemplateVersion) {
			LayoutPageTemplateVersion layoutPageTemplateVersion =
				(LayoutPageTemplateVersion)result;

			if (!Objects.equals(uuid, layoutPageTemplateVersion.getUuid()) ||
				(groupId != layoutPageTemplateVersion.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

				List<LayoutPageTemplateVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByUUID_G, finderArgs, list);
				}
				else {
					LayoutPageTemplateVersion layoutPageTemplateVersion =
						list.get(0);

					result = layoutPageTemplateVersion;

					cacheResult(layoutPageTemplateVersion);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByUUID_G, finderArgs);

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
			return (LayoutPageTemplateVersion)result;
		}
	}

	/**
	 * Removes the layout page template version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout page template version that was removed
	 */
	@Override
	public LayoutPageTemplateVersion removeByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateVersionException {

		LayoutPageTemplateVersion layoutPageTemplateVersion = findByUUID_G(
			uuid, groupId);

		return remove(layoutPageTemplateVersion);
	}

	/**
	 * Returns the number of layout page template versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout page template versions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"layoutPageTemplateVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(layoutPageTemplateVersion.uuid IS NULL OR layoutPageTemplateVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"layoutPageTemplateVersion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the layout page template versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout page template versions
	 */
	@Override
	public List<LayoutPageTemplateVersion> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_C;
			finderArgs = new Object[] {uuid, companyId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<LayoutPageTemplateVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateVersion layoutPageTemplateVersion :
						list) {

					if (!uuid.equals(layoutPageTemplateVersion.getUuid()) ||
						(companyId !=
							layoutPageTemplateVersion.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutPageTemplateVersionModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutPageTemplateVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout page template version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException {

		LayoutPageTemplateVersion layoutPageTemplateVersion =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (layoutPageTemplateVersion != null) {
			return layoutPageTemplateVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchPageTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the first layout page template version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator) {

		List<LayoutPageTemplateVersion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException {

		LayoutPageTemplateVersion layoutPageTemplateVersion =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (layoutPageTemplateVersion != null) {
			return layoutPageTemplateVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchPageTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the last layout page template version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateVersion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

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
	@Override
	public LayoutPageTemplateVersion[] findByUuid_C_PrevAndNext(
			long layoutPageTemplateVersionId, String uuid, long companyId,
			OrderByComparator<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException {

		uuid = Objects.toString(uuid, "");

		LayoutPageTemplateVersion layoutPageTemplateVersion = findByPrimaryKey(
			layoutPageTemplateVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateVersion[] array =
				new LayoutPageTemplateVersionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, layoutPageTemplateVersion, uuid, companyId,
				orderByComparator, true);

			array[1] = layoutPageTemplateVersion;

			array[2] = getByUuid_C_PrevAndNext(
				session, layoutPageTemplateVersion, uuid, companyId,
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

	protected LayoutPageTemplateVersion getByUuid_C_PrevAndNext(
		Session session, LayoutPageTemplateVersion layoutPageTemplateVersion,
		String uuid, long companyId,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutPageTemplateVersionModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutPageTemplateVersion layoutPageTemplateVersion :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutPageTemplateVersion);
		}
	}

	/**
	 * Returns the number of layout page template versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout page template versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"layoutPageTemplateVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(layoutPageTemplateVersion.uuid IS NULL OR layoutPageTemplateVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"layoutPageTemplateVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindBylayoutPageTemplateEntryId;
	private FinderPath
		_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId;
	private FinderPath _finderPathCountBylayoutPageTemplateEntryId;

	/**
	 * Returns all the layout page template versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the matching layout page template versions
	 */
	@Override
	public List<LayoutPageTemplateVersion> findBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		return findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end) {

		return findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, null);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator) {

		return findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, orderByComparator, true);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId, int start, int end,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId;
			finderArgs = new Object[] {layoutPageTemplateEntryId};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindBylayoutPageTemplateEntryId;
			finderArgs = new Object[] {
				layoutPageTemplateEntryId, start, end, orderByComparator
			};
		}

		List<LayoutPageTemplateVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateVersion layoutPageTemplateVersion :
						list) {

					if ((layoutPageTemplateEntryId !=
							layoutPageTemplateVersion.
								getLayoutPageTemplateEntryId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutPageTemplateVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutPageTemplateEntryId);

				if (!pagination) {
					list = (List<LayoutPageTemplateVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout page template version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion findBylayoutPageTemplateEntryId_First(
			long layoutPageTemplateEntryId,
			OrderByComparator<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException {

		LayoutPageTemplateVersion layoutPageTemplateVersion =
			fetchBylayoutPageTemplateEntryId_First(
				layoutPageTemplateEntryId, orderByComparator);

		if (layoutPageTemplateVersion != null) {
			return layoutPageTemplateVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchPageTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the first layout page template version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion fetchBylayoutPageTemplateEntryId_First(
		long layoutPageTemplateEntryId,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator) {

		List<LayoutPageTemplateVersion> list = findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version
	 * @throws NoSuchPageTemplateVersionException if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion findBylayoutPageTemplateEntryId_Last(
			long layoutPageTemplateEntryId,
			OrderByComparator<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException {

		LayoutPageTemplateVersion layoutPageTemplateVersion =
			fetchBylayoutPageTemplateEntryId_Last(
				layoutPageTemplateEntryId, orderByComparator);

		if (layoutPageTemplateVersion != null) {
			return layoutPageTemplateVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPageTemplateEntryId=");
		msg.append(layoutPageTemplateEntryId);

		msg.append("}");

		throw new NoSuchPageTemplateVersionException(msg.toString());
	}

	/**
	 * Returns the last layout page template version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template version, or <code>null</code> if a matching layout page template version could not be found
	 */
	@Override
	public LayoutPageTemplateVersion fetchBylayoutPageTemplateEntryId_Last(
		long layoutPageTemplateEntryId,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator) {

		int count = countBylayoutPageTemplateEntryId(layoutPageTemplateEntryId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateVersion> list = findBylayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template versions before and after the current layout page template version in the ordered set where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the current layout page template version
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template version
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateVersion[]
			findBylayoutPageTemplateEntryId_PrevAndNext(
				long layoutPageTemplateVersionId,
				long layoutPageTemplateEntryId,
				OrderByComparator<LayoutPageTemplateVersion> orderByComparator)
		throws NoSuchPageTemplateVersionException {

		LayoutPageTemplateVersion layoutPageTemplateVersion = findByPrimaryKey(
			layoutPageTemplateVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateVersion[] array =
				new LayoutPageTemplateVersionImpl[3];

			array[0] = getBylayoutPageTemplateEntryId_PrevAndNext(
				session, layoutPageTemplateVersion, layoutPageTemplateEntryId,
				orderByComparator, true);

			array[1] = layoutPageTemplateVersion;

			array[2] = getBylayoutPageTemplateEntryId_PrevAndNext(
				session, layoutPageTemplateVersion, layoutPageTemplateEntryId,
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

	protected LayoutPageTemplateVersion
		getBylayoutPageTemplateEntryId_PrevAndNext(
			Session session,
			LayoutPageTemplateVersion layoutPageTemplateVersion,
			long layoutPageTemplateEntryId,
			OrderByComparator<LayoutPageTemplateVersion> orderByComparator,
			boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEVERSION_WHERE);

		query.append(
			_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LayoutPageTemplateVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutPageTemplateEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template versions where layoutPageTemplateEntryId = &#63; from the database.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 */
	@Override
	public void removeBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		for (LayoutPageTemplateVersion layoutPageTemplateVersion :
				findBylayoutPageTemplateEntryId(
					layoutPageTemplateEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateVersion);
		}
	}

	/**
	 * Returns the number of layout page template versions where layoutPageTemplateEntryId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the layout page template entry ID
	 * @return the number of matching layout page template versions
	 */
	@Override
	public int countBylayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		FinderPath finderPath = _finderPathCountBylayoutPageTemplateEntryId;

		Object[] finderArgs = new Object[] {layoutPageTemplateEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTPAGETEMPLATEVERSION_WHERE);

			query.append(
				_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutPageTemplateEntryId);

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

	private static final String
		_FINDER_COLUMN_LAYOUTPAGETEMPLATEENTRYID_LAYOUTPAGETEMPLATEENTRYID_2 =
			"layoutPageTemplateVersion.layoutPageTemplateEntryId = ?";

	public LayoutPageTemplateVersionPersistenceImpl() {
		setModelClass(LayoutPageTemplateVersion.class);

		setModelImplClass(LayoutPageTemplateVersionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the layout page template version in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateVersion the layout page template version
	 */
	@Override
	public void cacheResult(
		LayoutPageTemplateVersion layoutPageTemplateVersion) {

		entityCache.putResult(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionImpl.class,
			layoutPageTemplateVersion.getPrimaryKey(),
			layoutPageTemplateVersion);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				layoutPageTemplateVersion.getUuid(),
				layoutPageTemplateVersion.getGroupId()
			},
			layoutPageTemplateVersion);

		layoutPageTemplateVersion.resetOriginalValues();
	}

	/**
	 * Caches the layout page template versions in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateVersions the layout page template versions
	 */
	@Override
	public void cacheResult(
		List<LayoutPageTemplateVersion> layoutPageTemplateVersions) {

		for (LayoutPageTemplateVersion layoutPageTemplateVersion :
				layoutPageTemplateVersions) {

			if (entityCache.getResult(
					LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
					LayoutPageTemplateVersionImpl.class,
					layoutPageTemplateVersion.getPrimaryKey()) == null) {

				cacheResult(layoutPageTemplateVersion);
			}
			else {
				layoutPageTemplateVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout page template versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutPageTemplateVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout page template version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		LayoutPageTemplateVersion layoutPageTemplateVersion) {

		entityCache.removeResult(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionImpl.class,
			layoutPageTemplateVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(LayoutPageTemplateVersionModelImpl)layoutPageTemplateVersion,
			true);
	}

	@Override
	public void clearCache(
		List<LayoutPageTemplateVersion> layoutPageTemplateVersions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutPageTemplateVersion layoutPageTemplateVersion :
				layoutPageTemplateVersions) {

			entityCache.removeResult(
				LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateVersionImpl.class,
				layoutPageTemplateVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(LayoutPageTemplateVersionModelImpl)layoutPageTemplateVersion,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutPageTemplateVersionModelImpl layoutPageTemplateVersionModelImpl) {

		Object[] args = new Object[] {
			layoutPageTemplateVersionModelImpl.getUuid(),
			layoutPageTemplateVersionModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, layoutPageTemplateVersionModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		LayoutPageTemplateVersionModelImpl layoutPageTemplateVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutPageTemplateVersionModelImpl.getUuid(),
				layoutPageTemplateVersionModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((layoutPageTemplateVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutPageTemplateVersionModelImpl.getOriginalUuid(),
				layoutPageTemplateVersionModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
	}

	/**
	 * Creates a new layout page template version with the primary key. Does not add the layout page template version to the database.
	 *
	 * @param layoutPageTemplateVersionId the primary key for the new layout page template version
	 * @return the new layout page template version
	 */
	@Override
	public LayoutPageTemplateVersion create(long layoutPageTemplateVersionId) {
		LayoutPageTemplateVersion layoutPageTemplateVersion =
			new LayoutPageTemplateVersionImpl();

		layoutPageTemplateVersion.setNew(true);
		layoutPageTemplateVersion.setPrimaryKey(layoutPageTemplateVersionId);

		String uuid = PortalUUIDUtil.generate();

		layoutPageTemplateVersion.setUuid(uuid);

		layoutPageTemplateVersion.setCompanyId(companyProvider.getCompanyId());

		return layoutPageTemplateVersion;
	}

	/**
	 * Removes the layout page template version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the layout page template version
	 * @return the layout page template version that was removed
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateVersion remove(long layoutPageTemplateVersionId)
		throws NoSuchPageTemplateVersionException {

		return remove((Serializable)layoutPageTemplateVersionId);
	}

	/**
	 * Removes the layout page template version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout page template version
	 * @return the layout page template version that was removed
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateVersion remove(Serializable primaryKey)
		throws NoSuchPageTemplateVersionException {

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateVersion layoutPageTemplateVersion =
				(LayoutPageTemplateVersion)session.get(
					LayoutPageTemplateVersionImpl.class, primaryKey);

			if (layoutPageTemplateVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageTemplateVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutPageTemplateVersion);
		}
		catch (NoSuchPageTemplateVersionException nsee) {
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
	protected LayoutPageTemplateVersion removeImpl(
		LayoutPageTemplateVersion layoutPageTemplateVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutPageTemplateVersion)) {
				layoutPageTemplateVersion =
					(LayoutPageTemplateVersion)session.get(
						LayoutPageTemplateVersionImpl.class,
						layoutPageTemplateVersion.getPrimaryKeyObj());
			}

			if (layoutPageTemplateVersion != null) {
				session.delete(layoutPageTemplateVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutPageTemplateVersion != null) {
			clearCache(layoutPageTemplateVersion);
		}

		return layoutPageTemplateVersion;
	}

	@Override
	public LayoutPageTemplateVersion updateImpl(
		LayoutPageTemplateVersion layoutPageTemplateVersion) {

		boolean isNew = layoutPageTemplateVersion.isNew();

		if (!(layoutPageTemplateVersion instanceof
				LayoutPageTemplateVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutPageTemplateVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutPageTemplateVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutPageTemplateVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutPageTemplateVersion implementation " +
					layoutPageTemplateVersion.getClass());
		}

		LayoutPageTemplateVersionModelImpl layoutPageTemplateVersionModelImpl =
			(LayoutPageTemplateVersionModelImpl)layoutPageTemplateVersion;

		if (Validator.isNull(layoutPageTemplateVersion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutPageTemplateVersion.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutPageTemplateVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutPageTemplateVersion.setCreateDate(now);
			}
			else {
				layoutPageTemplateVersion.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!layoutPageTemplateVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutPageTemplateVersion.setModifiedDate(now);
			}
			else {
				layoutPageTemplateVersion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutPageTemplateVersion.isNew()) {
				session.save(layoutPageTemplateVersion);

				layoutPageTemplateVersion.setNew(false);
			}
			else {
				layoutPageTemplateVersion =
					(LayoutPageTemplateVersion)session.merge(
						layoutPageTemplateVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutPageTemplateVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				layoutPageTemplateVersionModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				layoutPageTemplateVersionModelImpl.getUuid(),
				layoutPageTemplateVersionModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				layoutPageTemplateVersionModelImpl.
					getLayoutPageTemplateEntryId()
			};

			finderCache.removeResult(
				_finderPathCountBylayoutPageTemplateEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((layoutPageTemplateVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutPageTemplateVersionModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					layoutPageTemplateVersionModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((layoutPageTemplateVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutPageTemplateVersionModelImpl.getOriginalUuid(),
					layoutPageTemplateVersionModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					layoutPageTemplateVersionModelImpl.getUuid(),
					layoutPageTemplateVersionModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((layoutPageTemplateVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBylayoutPageTemplateEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutPageTemplateVersionModelImpl.
						getOriginalLayoutPageTemplateEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBylayoutPageTemplateEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId,
					args);

				args = new Object[] {
					layoutPageTemplateVersionModelImpl.
						getLayoutPageTemplateEntryId()
				};

				finderCache.removeResult(
					_finderPathCountBylayoutPageTemplateEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId,
					args);
			}
		}

		entityCache.putResult(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionImpl.class,
			layoutPageTemplateVersion.getPrimaryKey(),
			layoutPageTemplateVersion, false);

		clearUniqueFindersCache(layoutPageTemplateVersionModelImpl, false);
		cacheUniqueFindersCache(layoutPageTemplateVersionModelImpl);

		layoutPageTemplateVersion.resetOriginalValues();

		return layoutPageTemplateVersion;
	}

	/**
	 * Returns the layout page template version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template version
	 * @return the layout page template version
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPageTemplateVersionException {

		LayoutPageTemplateVersion layoutPageTemplateVersion = fetchByPrimaryKey(
			primaryKey);

		if (layoutPageTemplateVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageTemplateVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutPageTemplateVersion;
	}

	/**
	 * Returns the layout page template version with the primary key or throws a <code>NoSuchPageTemplateVersionException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the layout page template version
	 * @return the layout page template version
	 * @throws NoSuchPageTemplateVersionException if a layout page template version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateVersion findByPrimaryKey(
			long layoutPageTemplateVersionId)
		throws NoSuchPageTemplateVersionException {

		return findByPrimaryKey((Serializable)layoutPageTemplateVersionId);
	}

	/**
	 * Returns the layout page template version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateVersionId the primary key of the layout page template version
	 * @return the layout page template version, or <code>null</code> if a layout page template version with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateVersion fetchByPrimaryKey(
		long layoutPageTemplateVersionId) {

		return fetchByPrimaryKey((Serializable)layoutPageTemplateVersionId);
	}

	/**
	 * Returns all the layout page template versions.
	 *
	 * @return the layout page template versions
	 */
	@Override
	public List<LayoutPageTemplateVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findAll(
		int start, int end,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

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
	@Override
	public List<LayoutPageTemplateVersion> findAll(
		int start, int end,
		OrderByComparator<LayoutPageTemplateVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<LayoutPageTemplateVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutPageTemplateVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTPAGETEMPLATEVERSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTPAGETEMPLATEVERSION;

				if (pagination) {
					sql = sql.concat(
						LayoutPageTemplateVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutPageTemplateVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutPageTemplateVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Removes all the layout page template versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutPageTemplateVersion layoutPageTemplateVersion : findAll()) {
			remove(layoutPageTemplateVersion);
		}
	}

	/**
	 * Returns the number of layout page template versions.
	 *
	 * @return the number of layout page template versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_LAYOUTPAGETEMPLATEVERSION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "layoutPageTemplateVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTPAGETEMPLATEVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutPageTemplateVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout page template version persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			LayoutPageTemplateVersionModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutPageTemplateVersionModelImpl.UUID_COLUMN_BITMASK |
			LayoutPageTemplateVersionModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutPageTemplateVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutPageTemplateVersionModelImpl.UUID_COLUMN_BITMASK |
			LayoutPageTemplateVersionModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindBylayoutPageTemplateEntryId =
			new FinderPath(
				LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutPageTemplateVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findBylayoutPageTemplateEntryId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindBylayoutPageTemplateEntryId =
			new FinderPath(
				LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutPageTemplateVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findBylayoutPageTemplateEntryId",
				new String[] {Long.class.getName()},
				LayoutPageTemplateVersionModelImpl.
					LAYOUTPAGETEMPLATEENTRYID_COLUMN_BITMASK);

		_finderPathCountBylayoutPageTemplateEntryId = new FinderPath(
			LayoutPageTemplateVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutPageTemplateVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBylayoutPageTemplateEntryId",
			new String[] {Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(LayoutPageTemplateVersionImpl.class.getName());
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

	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEVERSION =
		"SELECT layoutPageTemplateVersion FROM LayoutPageTemplateVersion layoutPageTemplateVersion";

	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEVERSION_WHERE =
		"SELECT layoutPageTemplateVersion FROM LayoutPageTemplateVersion layoutPageTemplateVersion WHERE ";

	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEVERSION =
		"SELECT COUNT(layoutPageTemplateVersion) FROM LayoutPageTemplateVersion layoutPageTemplateVersion";

	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEVERSION_WHERE =
		"SELECT COUNT(layoutPageTemplateVersion) FROM LayoutPageTemplateVersion layoutPageTemplateVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"layoutPageTemplateVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutPageTemplateVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutPageTemplateVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}