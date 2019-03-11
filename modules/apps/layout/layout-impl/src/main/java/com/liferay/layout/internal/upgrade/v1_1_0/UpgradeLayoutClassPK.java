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

package com.liferay.layout.internal.upgrade.v1_1_0;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collections;

/**
 * @author Pavel Savinov
 */
public class UpgradeLayoutClassPK extends UpgradeProcess {

	public UpgradeLayoutClassPK(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeLayoutPageTemplateEntry();
		_upgradeLayoutDraft();
	}

	private void _upgradeLayoutDraft() throws Exception {
		String sql = StringBundler.concat(
			"select plid from Layout where plid not in (select classPK from ",
			"Layout where classNameId = ? and system_ = ?) and (type_ = ? or ",
			"type_ = ?) and system_ = ?");

		try (PreparedStatement ps1 = connection.prepareStatement(sql)) {
			ps1.setLong(1, PortalUtil.getClassNameId(Layout.class));
			ps1.setBoolean(2, true);
			ps1.setString(3, LayoutConstants.TYPE_ASSET_DISPLAY);
			ps1.setString(4, LayoutConstants.TYPE_CONTENT);
			ps1.setBoolean(5, false);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAttribute(
				"layout.instanceable.allowed", Boolean.TRUE);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long plid = rs.getLong("plid");

					Layout layout = _layoutLocalService.getLayout(plid);

					_layoutLocalService.addLayout(
						layout.getUserId(), layout.getGroupId(),
						layout.isPrivateLayout(), layout.getParentLayoutId(),
						PortalUtil.getClassNameId(Layout.class), plid,
						layout.getNameMap(), layout.getTitleMap(),
						layout.getDescriptionMap(), layout.getKeywordsMap(),
						layout.getRobotsMap(), layout.getType(),
						layout.getTypeSettings(), true, true,
						Collections.emptyMap(), serviceContext);
				}
			}
		}
	}

	private void _upgradeLayoutPageTemplateEntry() throws Exception {
		String sql =
			"select plid, typeSettings from Layout where classNameId = 0 and " +
				"classPK = 0 and type_ = ? and system_ = ?";

		try (PreparedStatement ps1 = connection.prepareStatement(sql)) {
			ps1.setString(1, LayoutConstants.TYPE_CONTENT);
			ps1.setBoolean(2, false);

			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update Layout set classNameId = ?, classPK = ? where " +
						"plid = ?"));

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					UnicodeProperties properties = new UnicodeProperties();

					properties.load(rs.getString("typeSettings"));

					long layoutPageTemplateEntryId = GetterUtil.getLong(
						properties.getProperty("layoutPageTemplateEntryId"));

					if (layoutPageTemplateEntryId == 0) {
						continue;
					}

					ps2.setLong(
						1,
						PortalUtil.getClassNameId(
							LayoutPageTemplateEntry.class));

					ps2.setLong(2, layoutPageTemplateEntryId);
					ps2.setLong(3, rs.getLong("plid"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private final LayoutLocalService _layoutLocalService;

}