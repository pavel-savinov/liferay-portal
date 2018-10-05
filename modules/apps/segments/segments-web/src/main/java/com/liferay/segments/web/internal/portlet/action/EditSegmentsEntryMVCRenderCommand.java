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

package com.liferay.segments.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.service.SegmentsEntryRelService;
import com.liferay.segments.service.SegmentsEntryService;
import com.liferay.segments.web.internal.constants.SegmentsWebKeys;
import com.liferay.segments.web.internal.display.context.EditSegmentsEntryDisplayContext;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SegmentsPortletKeys.SEGMENTS,
		"mvc.command.name=editSegmentsEntry"
	},
	service = MVCRenderCommand.class
)
public class EditSegmentsEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		EditSegmentsEntryDisplayContext editSegmentsEntryDisplayContext =
			new EditSegmentsEntryDisplayContext(
				httpServletRequest, renderRequest, renderResponse,
				_segmentsEntryService, _segmentsEntryRelService,
				_userLocalService);

		renderRequest.setAttribute(
			SegmentsWebKeys.EDIT_SEGMENTS_ENTRY_DISPLAY_CONTEXT,
			editSegmentsEntryDisplayContext);

		return "/edit_segments_entry.jsp";
	}

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryRelService _segmentsEntryRelService;

	@Reference
	private SegmentsEntryService _segmentsEntryService;

	@Reference
	private UserLocalService _userLocalService;

}