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

package com.liferay.fragment.internal.renderer.servlet;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.internal.constants.FragmentEntryRendererConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.persistence.FragmentEntryLinkPersistence;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.access.control.AccessControlUtil;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.taglib.util.ThemeUtil;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"dynamic.data.mapping.form.builder.servlet=true",
		"osgi.http.whiteboard.context.path=/",
		"osgi.http.whiteboard.servlet.name=com.liferay.fragment.internal.renderer.servlet.FragmentEntryRendererServlet",
		"osgi.http.whiteboard.servlet.pattern=" + FragmentEntryRendererConstants.SERVLET_PATH
	},
	service = Servlet.class
)
public class FragmentEntryRendererServlet extends HttpServlet {

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			AccessControlContext accessControlContext =
				AccessControlUtil.getAccessControlContext();

			AuthVerifierResult authVerifierResult =
				accessControlContext.getAuthVerifierResult();

			long userId = authVerifierResult.getUserId();

			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, httpServletRequest,
				httpServletResponse);

			ServletContext servletContext = ServletContextPool.get(
				StringPool.BLANK);

			httpServletRequest.setAttribute(WebKeys.CTX, servletContext);

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(
					_userLocalService.getUser(userId));

			long groupId = ParamUtil.getLong(httpServletRequest, "groupId");

			_portletResourcePermission.check(
				permissionChecker, groupId,
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkPersistence.create(0);

			String css = ParamUtil.getString(httpServletRequest, "css");
			String html = ParamUtil.getString(httpServletRequest, "html");
			String js = ParamUtil.getString(httpServletRequest, "js");

			fragmentEntryLink.setCss(css);
			fragmentEntryLink.setHtml(html);
			fragmentEntryLink.setJs(js);

			DefaultFragmentRendererContext defaultFragmentRendererContext =
				new DefaultFragmentRendererContext(fragmentEntryLink);

			defaultFragmentRendererContext.setMode(
				FragmentEntryLinkConstants.VIEW);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			LayoutSet layoutSet = themeDisplay.getLayoutSet();

			String portalBody = ThemeUtil.include(
				servletContext, httpServletRequest, httpServletResponse,
				"portal_normal.ftl", layoutSet.getTheme(), false);

			Document document = Jsoup.parse(portalBody);

			Element body = document.body();

			body.html(
				_fragmentRendererController.render(
					defaultFragmentRendererContext, httpServletRequest,
					httpServletResponse));

			ServletResponseUtil.write(httpServletResponse, document.html());
		}
		catch (Exception e) {
			_log.error("Unable to render fragment entry", e);

			throw new ServletException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryRendererServlet.class);

	@Reference
	private FragmentEntryLinkPersistence _fragmentEntryLinkPersistence;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + FragmentConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private UserLocalService _userLocalService;

}