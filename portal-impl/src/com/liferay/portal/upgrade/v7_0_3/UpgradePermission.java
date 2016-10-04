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

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portlet.asset.service.permission.AssetPermission;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov
 */
public class UpgradePermission extends UpgradeProcess {

	protected int checkActions(List<Integer> bitwiseValues, int actionsIds) {
		int newActionsIds = 0;

		for (Integer bitwiseValue : bitwiseValues) {
			if ((actionsIds & bitwiseValue.intValue()) != 0) {
				newActionsIds += bitwiseValue.intValue();
			}
		}

		return newActionsIds;
	}

	@Override
	protected void doUpgrade() throws Exception {
		renameResourceActions();
		renameResourcePermission();
	}

	protected List<Integer> getBitwiseValues(String resourceName)
		throws Exception {

		List<Integer> bitwiseValues = new ArrayList<>();

		try (PreparedStatement ps = connection.prepareStatement(
				"select bitwiseValue from ResourceAction where name = ?;")) {

			ps.setString(1, resourceName);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bitwiseValues.add(rs.getInt(1));
			}
		}

		return bitwiseValues;
	}

	protected void renameResourceActions() throws UpgradeException {
		StringBundler selectSb = new StringBundler(2);

		selectSb.append("select count(*) from ResourceAction where name in ");
		selectSb.append("(?,?)");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				selectSb.toString())) {

			ps.setString(1, AssetPermission.RESOURCE_NAME_CATEGORIES);
			ps.setString(2, AssetPermission.RESOURCE_NAME_TAGS);

			ResultSet rs = ps.executeQuery();

			rs.next();

			int count = rs.getInt(1);

			if (count > 0) {
				StringBundler deleteSb = new StringBundler(3);

				deleteSb.append("delete from ResourceAction where name = '");
				deleteSb.append(AssetPermission.RESOURCE_NAME);
				deleteSb.append("';");

				runSQL(deleteSb.toString());

				return;
			};

			StringBundler sb = new StringBundler(6);

			sb.append("update ResourceAction set name = '");
			sb.append(AssetPermission.RESOURCE_NAME_CATEGORIES);
			sb.append("' where name = '");
			sb.append(AssetPermission.RESOURCE_NAME);
			sb.append("' and actionId in ('ADD_CATEGORY', 'ADD_VOCABULARY', ");
			sb.append("'PERMISSIONS');");

			runSQL(sb.toString());

			sb = new StringBundler(3);

			sb.append("update ResourceAction set bitwiseValue = ");
			sb.append("bitwiseValue / 2 where actionId in (");
			sb.append("'ADD_CATEGORY', 'ADD_VOCABULARY');");

			runSQL(sb.toString());

			sb = new StringBundler(5);

			sb.append("update ResourceAction set name = '");
			sb.append(AssetPermission.RESOURCE_NAME_TAGS);
			sb.append("' where name = '");
			sb.append(AssetPermission.RESOURCE_NAME);
			sb.append("' and actionId = 'ADD_TAG';");

			runSQL(sb.toString());

			sb = new StringBundler(6);

			sb.append("insert into ResourceAction (resourceActionId, name, ");
			sb.append("actionId, bitwiseValue) values (?, ?, ?, ?);");

			try (PreparedStatement ps2 = connection.prepareStatement(
				sb.toString())) {

				ps2.setLong(1, increment());
				ps2.setString(2, AssetPermission.RESOURCE_NAME_TAGS);
				ps2.setString(3, "PERMISSIONS");
				ps2.setInt(4, 2);

				ps2.execute();
			}
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void renameResourcePermission() throws UpgradeException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(4);

			sb.append("select actionIds, companyId, ");
			sb.append("ownerId, primKey, primKeyId, resourcePermissionId, ");
			sb.append("roleId, scope, viewActionId from ResourcePermission ");
			sb.append("where name = ?;");

			PreparedStatement ps = connection.prepareStatement(sb.toString());

			ps.setString(1, AssetPermission.RESOURCE_NAME);

			StringBundler insertSb = new StringBundler(6);

			insertSb.append("insert into ResourcePermission (");
			insertSb.append("resourcePermissionId, companyId, ");
			insertSb.append("name, scope, primKey, primKeyId, ");
			insertSb.append("roleId, ownerId, actionIds, ");
			insertSb.append("viewActionId) values (");
			insertSb.append("?,?,?,?,?,?,?,?,?,?);");

			StringBundler deleteSb = new StringBundler(2);

			deleteSb.append("delete from ResourcePermission where ");
			deleteSb.append("resourcePermissionId = ?;");

			List<Integer> categoriesBitwiseValues = getBitwiseValues(
				AssetPermission.RESOURCE_NAME_CATEGORIES);

			List<Integer> tagsBitwiseValues = getBitwiseValues(
				AssetPermission.RESOURCE_NAME_TAGS);

			try (ResultSet rs = ps.executeQuery();
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, deleteSb.toString());
				PreparedStatement ps3 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection, insertSb.toString())) {

				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					int scope = rs.getInt("scope");
					long ownerId = rs.getLong("ownerId");
					long primKeyId = rs.getLong("primKeyId");
					String primKey = rs.getString("primKey");
					long resourcePermissionId = rs.getLong(
						"resourcePermissionId");
					long roleId = rs.getLong("roleId");
					int actionIds = rs.getInt("actionIds");
					boolean viewActionId = rs.getBoolean("viewActionId");

					int categoriesActions = checkActions(
						categoriesBitwiseValues, actionIds);
					int tagsActions = checkActions(
						tagsBitwiseValues, actionIds);

					ps2.setLong(1, resourcePermissionId);

					ps2.addBatch();

					if (categoriesActions != 0) {
						ps3.setLong(1, increment());
						ps3.setLong(2, companyId);
						ps3.setString(
							3, AssetPermission.RESOURCE_NAME_CATEGORIES);
						ps3.setInt(4, scope);

						if (primKey.equals(AssetPermission.RESOURCE_NAME)) {
							ps3.setString(
								5, AssetPermission.RESOURCE_NAME_CATEGORIES);
						}
						else {
							ps3.setString(5, primKey);
						}

						ps3.setLong(6, primKeyId);
						ps3.setLong(7, roleId);
						ps3.setLong(8, ownerId);
						ps3.setInt(9, categoriesActions);
						ps3.setBoolean(10, viewActionId);

						ps3.addBatch();
					}

					if (tagsActions != 0) {
						ps3.setLong(1, increment());
						ps3.setLong(2, companyId);
						ps3.setString(3, AssetPermission.RESOURCE_NAME_TAGS);
						ps3.setInt(4, scope);

						if (primKey.equals(AssetPermission.RESOURCE_NAME)) {
							ps3.setString(
								5, AssetPermission.RESOURCE_NAME_TAGS);
						}
						else {
							ps3.setString(5, primKey);
						}

						ps3.setLong(6, primKeyId);
						ps3.setLong(7, roleId);
						ps3.setLong(8, ownerId);
						ps3.setInt(9, tagsActions);
						ps3.setBoolean(10, viewActionId);

						ps3.addBatch();
					}
				}

				ps2.executeBatch();

				ps3.executeBatch();
			}
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

}