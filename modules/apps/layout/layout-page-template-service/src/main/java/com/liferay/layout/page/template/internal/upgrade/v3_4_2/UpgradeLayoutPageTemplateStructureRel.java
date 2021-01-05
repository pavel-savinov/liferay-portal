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

package com.liferay.layout.page.template.internal.upgrade.v3_4_2;

import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeLayoutPageTemplateStructureRel extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeLayoutPageTemplateStructureRel();
	}

	private boolean _isEmpty(JSONObject jsonObject) {
		Set<String> keySet = jsonObject.keySet();

		return keySet.isEmpty();
	}

	private void _replaceAlign(JSONObject stylesJSONObject) {
		for (String key : _ALIGN_KEYS) {
			if (!stylesJSONObject.has(key)) {
				continue;
			}

			stylesJSONObject.put("textAlign", stylesJSONObject.remove(key));

			break;
		}
	}

	private void _replaceBorderRadius(JSONObject stylesJSONObject) {
		if (!stylesJSONObject.has("borderRadius")) {
			return;
		}

		stylesJSONObject.put(
			"borderRadius",
			_borderRadiuses.get(stylesJSONObject.remove("borderRadius")));
	}

	private void _replaceBottomSpacing(JSONObject stylesJSONObject) {
		if (!stylesJSONObject.has("bottomSpacing")) {
			return;
		}

		stylesJSONObject.put(
			"marginBottom", stylesJSONObject.remove("bottomSpacing"));
	}

	private void _replaceShadow(JSONObject stylesJSONObject) {
		if (!stylesJSONObject.has("boxShadow")) {
			return;
		}

		String shadowCssClass = GetterUtil.getString(
			stylesJSONObject.remove("boxShadow"));

		if (!_shadows.containsKey(shadowCssClass)) {
			return;
		}

		stylesJSONObject.put("shadow", _shadows.get(shadowCssClass));
	}

	private void _replaceTextColor(JSONObject stylesJSONObject) {
		JSONObject textColorJSONObject = stylesJSONObject.getJSONObject(
			"textColor");

		if (textColorJSONObject == null) {
			return;
		}

		if (Validator.isNotNull(textColorJSONObject.getString("cssClass"))) {
			stylesJSONObject.put(
				"textColor",
				_colors.getOrDefault(
					textColorJSONObject.getString("cssClass"),
					textColorJSONObject.getString("cssClass")));
		}
		else if (Validator.isNotNull(textColorJSONObject.getString("color"))) {
			stylesJSONObject.put(
				"textColor",
				_colors.getOrDefault(
					textColorJSONObject.getString("color"),
					textColorJSONObject.getString("color")));
		}
		else if (Validator.isNotNull(
					textColorJSONObject.getString("rgbValue"))) {

			stylesJSONObject.put(
				"textColor", textColorJSONObject.getString("rgbValue"));
		}
	}

	private String _upgradeLayoutData(String data) {
		LayoutStructure layoutStructure = LayoutStructure.of(data);

		List<LayoutStructureItem> layoutStructureItems =
			layoutStructure.getLayoutStructureItems();

		for (LayoutStructureItem layoutStructureItem : layoutStructureItems) {
			if (layoutStructureItem instanceof ColumnLayoutStructureItem) {
				ColumnLayoutStructureItem columnLayoutStructureItem =
					(ColumnLayoutStructureItem)layoutStructureItem;

				Map<String, JSONObject> viewportConfigurations =
					columnLayoutStructureItem.getViewportConfigurations();

				JSONObject mobileLandscapeJSONObject =
					viewportConfigurations.get(
						ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId());

				JSONObject portraitMobileJSONObject =
					viewportConfigurations.get(
						ViewportSize.PORTRAIT_MOBILE.getViewportSizeId());

				JSONObject tabletJSONObject = viewportConfigurations.get(
					ViewportSize.TABLET.getViewportSizeId());

				if (_isEmpty(mobileLandscapeJSONObject) &&
					_isEmpty(portraitMobileJSONObject) &&
					_isEmpty(tabletJSONObject)) {

					columnLayoutStructureItem.setViewportConfiguration(
						ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId(),
						JSONUtil.put("size", "12"));
				}
			}

			if (layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem) {

				FragmentStyledLayoutStructureItem
					fragmentStyledLayoutStructureItem =
						(FragmentStyledLayoutStructureItem)layoutStructureItem;

				JSONObject itemConfigJSONObject =
					fragmentStyledLayoutStructureItem.getItemConfigJSONObject();

				JSONObject stylesJSONObject =
					itemConfigJSONObject.getJSONObject("styles");

				_replaceAlign(stylesJSONObject);
				_replaceBorderRadius(stylesJSONObject);
				_replaceBottomSpacing(stylesJSONObject);
				_replaceShadow(stylesJSONObject);
				_replaceTextColor(stylesJSONObject);
			}
		}

		JSONObject jsonObject = layoutStructure.toJSONObject();

		return jsonObject.toString();
	}

	private void _upgradeLayoutPageTemplateStructureRel() throws Exception {
		try (Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(
				"select lPageTemplateStructureRelId, segmentsExperienceId, " +
					"data_ from LayoutPageTemplateStructureRel");
			PreparedStatement ps = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update LayoutPageTemplateStructureRel set data_ = ? " +
						"where lPageTemplateStructureRelId = ?"))) {

			while (rs.next()) {
				long layoutPageTemplateStructureRelId = rs.getLong(
					"lPageTemplateStructureRelId");

				String data = rs.getString("data_");

				ps.setString(1, _upgradeLayoutData(data));

				ps.setLong(2, layoutPageTemplateStructureRelId);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	private static final String[] _ALIGN_KEYS = {
		"buttonAlign", "contentAlign", "imageAlign"
	};

	private static final Map<String, String> _borderRadiuses =
		HashMapBuilder.put(
			"lg", "0.375rem"
		).put(
			"none", StringPool.BLANK
		).put(
			"sm", "0.1875rem"
		).build();
	private static final Map<String, String> _colors = HashMapBuilder.put(
		"danger", "#DA1414"
	).put(
		"dark", "#272833"
	).put(
		"gray-dark", "#393A4A"
	).put(
		"info", "#2E5AAC"
	).put(
		"light", "#F1F2F5"
	).put(
		"lighter", "#F7F8F9"
	).put(
		"primary", "#0B5FFF"
	).put(
		"secondary", "#6B6C7E"
	).put(
		"success", "#287D3C"
	).put(
		"warning", "#B95000"
	).put(
		"white", "#FFFFFF"
	).build();
	private static final Map<String, String> _shadows = HashMapBuilder.put(
		"lg", "0 1rem 3rem rgba(0, 0, 0, .175)"
	).put(
		"sm", "0 .125rem .25rem rgba(0, 0, 0, .075)"
	).build();

}