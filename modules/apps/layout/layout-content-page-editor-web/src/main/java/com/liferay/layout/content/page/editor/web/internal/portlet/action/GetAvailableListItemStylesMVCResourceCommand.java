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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.info.list.renderer.InfoListItemStyle;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/get_available_list_item_styles"
	},
	service = MVCResourceCommand.class
)
public class GetAvailableListItemStylesMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String listStyle = ParamUtil.getString(resourceRequest, "listStyle");

		InfoListRenderer infoListRenderer =
			_infoListRendererTracker.getInfoListRenderer(listStyle);

		List<InfoListItemStyle> infoListItemStyles =
			infoListRenderer.getAvailableInfoListItemStyles();

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		for (InfoListItemStyle infoListItemStyle : infoListItemStyles) {
			jsonArray.put(
				JSONUtil.put(
					"label",
					infoListItemStyle.getLabel(themeDisplay.getLocale())
				).put(
					"value", infoListItemStyle.getKey()
				));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	@Reference
	private InfoListRendererTracker _infoListRendererTracker;

}