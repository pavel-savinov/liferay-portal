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

package com.liferay.opensocial.shindig.service;

import com.liferay.expando.kernel.exception.NoSuchTableException;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.opensocial.shindig.util.ShindigUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.apache.shindig.auth.SecurityToken;
import org.apache.shindig.common.util.ImmediateFuture;
import org.apache.shindig.protocol.DataCollection;
import org.apache.shindig.protocol.ProtocolException;
import org.apache.shindig.social.opensocial.spi.AppDataService;
import org.apache.shindig.social.opensocial.spi.GroupId;
import org.apache.shindig.social.opensocial.spi.UserId;

/**
 * @author Michael Young
 */
public class LiferayAppDataService implements AppDataService {

	public Future<Void> deletePersonData(
			UserId userId, GroupId groupId, String appId, Set<String> fields,
			SecurityToken securityToken)
		throws ProtocolException {

		try {
			doDeletePersonData(userId, groupId, appId, fields, securityToken);

			return ImmediateFuture.newInstance(null);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			throw new ProtocolException(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				exception.getMessage(), exception);
		}
	}

	public Future<DataCollection> getPersonData(
			Set<UserId> userIds, GroupId groupId, String appId,
			Set<String> fields, SecurityToken securityToken)
		throws ProtocolException {

		try {
			DataCollection dataCollection = doGetPersonData(
				userIds, groupId, appId, fields, securityToken);

			return ImmediateFuture.newInstance(dataCollection);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			throw new ProtocolException(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				exception.getMessage(), exception);
		}
	}

	public Future<Void> updatePersonData(
			UserId userId, GroupId groupId, String appId, Set<String> fields,
			Map<String, String> values, SecurityToken securityToken)
		throws ProtocolException {

		try {
			doUpdatePersonData(
				userId, groupId, appId, fields, values, securityToken);

			return ImmediateFuture.newInstance(null);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			throw new ProtocolException(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				exception.getMessage(), exception);
		}
	}

	protected void doDeletePersonData(
			UserId userId, GroupId groupId, String appId, Set<String> fields,
			SecurityToken securityToken)
		throws Exception {

		long companyId = getCompanyId(securityToken);

		long userIdLong = GetterUtil.getLong(userId.getUserId(securityToken));

		for (String field : fields) {
			ExpandoColumn expandoColumn = getExpandoColumn(
				companyId, getColumnName(appId, field));

			ExpandoValueLocalServiceUtil.deleteValue(
				companyId, User.class.getName(),
				ShindigUtil.getTableOpenSocial(), expandoColumn.getName(),
				userIdLong);
		}
	}

	protected DataCollection doGetPersonData(
			Set<UserId> userIds, GroupId groupId, String appId,
			Set<String> fields, SecurityToken securityToken)
		throws Exception {

		long companyId = getCompanyId(securityToken);

		List<ExpandoColumn> expandoColumns = getExpandoColumns(
			companyId, appId);

		if (expandoColumns == null) {
			return null;
		}

		if (fields.isEmpty()) {
			fields = new LinkedHashSet<>();

			for (ExpandoColumn expandoColumn : expandoColumns) {
				fields.add(expandoColumn.getName());
			}
		}

		Map<String, Map<String, String>> peopleAppData = new HashMap<>();

		for (UserId userId : userIds) {
			String userIdString = userId.getUserId(securityToken);

			long userIdLong = GetterUtil.getLong(userIdString);

			Map<String, String> personAppData = new HashMap<>();

			for (String field : fields) {
				String value = getExpandoValue(
					companyId, appId, userIdLong, getColumnName(appId, field));

				personAppData.put(field, value);
			}

			peopleAppData.put(userIdString, personAppData);
		}

		return new DataCollection(peopleAppData);
	}

	protected void doUpdatePersonData(
			UserId userId, GroupId groupId, String appId, Set<String> fields,
			Map<String, String> values, SecurityToken securityToken)
		throws Exception {

		long companyId = getCompanyId(securityToken);

		long userIdLong = GetterUtil.getLong(userId.getUserId(securityToken));

		for (Map.Entry<String, String> entry : values.entrySet()) {

			// Workaround for a Shindig bug that stores a Long in value instead
			// of the expected String so we cannot use generics here

			String value = String.valueOf(entry.getValue());

			ExpandoColumn expandoColumn = getExpandoColumn(
				companyId, getColumnName(appId, entry.getKey()));

			ExpandoValueLocalServiceUtil.addValue(
				companyId, User.class.getName(),
				ShindigUtil.getTableOpenSocial(), expandoColumn.getName(),
				userIdLong, value);
		}
	}

	protected String getColumnName(String appId, String field) {
		if (Validator.isNotNull(appId)) {
			return appId.concat(field);
		}

		return field;
	}

	protected long getCompanyId(SecurityToken securityToken) throws Exception {
		long userIdLong = GetterUtil.getLong(securityToken.getViewerId());

		User user = UserLocalServiceUtil.getUser(userIdLong);

		return user.getCompanyId();
	}

	protected ExpandoColumn getExpandoColumn(long companyId, String columnName)
		throws Exception {

		ExpandoTable expandoTable = null;

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getTable(
				companyId, User.class.getName(),
				ShindigUtil.getTableOpenSocial());
		}
		catch (NoSuchTableException noSuchTableException) {
			_log.error(noSuchTableException, noSuchTableException);
		}

		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(
			expandoTable.getTableId(), columnName);

		if (expandoColumn == null) {
			expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(
				expandoTable.getTableId(), columnName,
				ExpandoColumnConstants.STRING);
		}

		return expandoColumn;
	}

	protected List<ExpandoColumn> getExpandoColumns(
		long companyId, String appId) {

		try {
			return ExpandoColumnLocalServiceUtil.getColumns(
				companyId, User.class.getName(),
				ShindigUtil.getTableOpenSocial());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return null;
		}
	}

	protected String getExpandoValue(
		long companyId, String appId, long userId, String columnName) {

		try {
			getExpandoColumn(companyId, columnName);

			ExpandoValue expandoValue = ExpandoValueLocalServiceUtil.getValue(
				companyId, User.class.getName(),
				ShindigUtil.getTableOpenSocial(), columnName, userId);

			return expandoValue.getData();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayAppDataService.class);

}