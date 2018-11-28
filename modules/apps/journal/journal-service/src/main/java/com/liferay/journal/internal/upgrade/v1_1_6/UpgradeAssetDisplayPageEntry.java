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

package com.liferay.journal.internal.upgrade.v1_1_6;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Vendel Toreki
 */
public class UpgradeAssetDisplayPageEntry extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateAssetDisplayPageEntry();
	}

	protected void updateAssetDisplayPageEntry() throws Exception {
		StringBuilder sb = new StringBuilder(9);

		sb.append("select groupId, companyId, userId, userName, createDate, ");
		sb.append("resourcePrimKey from JournalArticle where ");
		sb.append("JournalArticle.layoutUuid is not null and ");
		sb.append("JournalArticle.layoutUuid != '' and not exists ( ");
		sb.append("select 1 from AssetDisplayPageEntry where ");
		sb.append("AssetDisplayPageEntry.groupId = JournalArticle.groupId ");
		sb.append("and AssetDisplayPageEntry.classNameId = ? and ");
		sb.append("AssetDisplayPageEntry.classPK = ");
		sb.append("JournalArticle.resourcePrimKey )");

		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, _createInsertSql());) {

			ps1.setLong(1, journalArticleClassNameId);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");
					long companyId = rs.getLong("companyId");
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					Timestamp createDate = rs.getTimestamp("createDate");
					long resourcePrimKey = rs.getLong("resourcePrimKey");

					ps2.setString(1, PortalUUIDUtil.generate());
					ps2.setLong(2, increment());
					ps2.setLong(3, groupId);
					ps2.setLong(4, companyId);
					ps2.setLong(5, userId);
					ps2.setString(6, userName);
					ps2.setTimestamp(7, createDate);
					ps2.setTimestamp(8, createDate);
					ps2.setLong(9, journalArticleClassNameId);
					ps2.setLong(10, resourcePrimKey);
					ps2.setLong(11, 0);
					ps2.setInt(12, AssetDisplayPageConstants.TYPE_SPECIFIC);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private String _createInsertSql() {
		StringBuilder sb = new StringBuilder(5);

		sb.append("insert into AssetDisplayPageEntry (uuid_, ");
		sb.append("assetDisplayPageEntryId, groupId, companyId, userId, ");
		sb.append("userName, createDate, modifiedDate, classNameId, ");
		sb.append("classPK, layoutPageTemplateEntryId, type_) values ");
		sb.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		return sb.toString();
	}

}