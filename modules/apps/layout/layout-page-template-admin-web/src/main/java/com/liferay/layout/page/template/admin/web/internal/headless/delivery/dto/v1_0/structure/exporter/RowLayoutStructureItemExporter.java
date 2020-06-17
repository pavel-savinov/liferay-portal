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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.exporter;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageRowDefinition;
import com.liferay.headless.delivery.dto.v1_0.ViewportRowConfig;
import com.liferay.headless.delivery.dto.v1_0.ViewportRowConfigDefinition;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporter.class)
public class RowLayoutStructureItemExporter
	implements LayoutStructureItemExporter {

	@Override
	public String getClassName() {
		return RowLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		RowLayoutStructureItem rowLayoutStructureItem =
			(RowLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageRowDefinition() {
					{
						gutters = rowLayoutStructureItem.isGutters();
						modulesPerRow =
							rowLayoutStructureItem.getModulesPerRow();
						numberOfColumns =
							rowLayoutStructureItem.getNumberOfColumns();
						reverseOrder = getReverseOrder();
						verticalAlignment = getVerticalAlignment();

						setViewportRowConfig(
							() -> {
								Map<String, JSONObject> viewportConfigurations =
									rowLayoutStructureItem.
										getViewportSizeConfigurations();

								if (MapUtil.isEmpty(viewportConfigurations)) {
									return null;
								}

								return new ViewportRowConfig() {
									{
										landscapeMobile =
											_toViewportRowConfigDefinition(
												viewportConfigurations,
												ViewportSize.MOBILE_LANDSCAPE);
										portraitMobile =
											_toViewportRowConfigDefinition(
												viewportConfigurations,
												ViewportSize.PORTRAIT_MOBILE);
										tablet = _toViewportRowConfigDefinition(
											viewportConfigurations,
											ViewportSize.TABLET);
									}
								};
							});
					}
				};
				type = PageElement.Type.ROW;
			}
		};
	}

	private ViewportRowConfigDefinition _toViewportRowConfigDefinition(
		Map<String, JSONObject> viewportConfigurations,
		ViewportSize viewportSize) {

		if (!viewportConfigurations.containsKey(
				viewportSize.getViewportSizeId())) {

			return null;
		}

		JSONObject jsonObject = viewportConfigurations.get(
			viewportSize.getViewportSizeId());

		return new ViewportRowConfigDefinition() {
			{
				if (jsonObject.has("modulesPerRow")) {
					modulesPerRow = jsonObject.getInt("modulesPerRow");
				}

				if (jsonObject.has("reverseOrder")) {
					reverseOrder = jsonObject.getBoolean("reverseOrder");
				}

				if (jsonObject.has("verticalAlignment")) {
					verticalAlignment = jsonObject.getString(
						"verticalAlignment");
				}
			}
		};
	}

}