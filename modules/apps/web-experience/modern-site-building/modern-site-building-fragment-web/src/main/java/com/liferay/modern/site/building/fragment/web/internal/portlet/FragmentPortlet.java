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

package com.liferay.modern.site.building.fragment.web.internal.portlet;

import com.liferay.modern.site.building.fragment.constants.FragmentPortletKeys;
import com.liferay.modern.site.building.fragment.service.FragmentCollectionService;
import com.liferay.modern.site.building.fragment.service.FragmentEntryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-fragment-web",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/fragment.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Fragment Web",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class FragmentPortlet extends MVCPortlet {

	public void deleteFragmentCollection(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteFragmentCollectionIds = null;

		long fragmentCollectionId = ParamUtil.getLong(
			actionRequest, "fragmentCollectionId");

		if (fragmentCollectionId > 0) {
			deleteFragmentCollectionIds = new long[] {fragmentCollectionId};
		}
		else {
			deleteFragmentCollectionIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		_fragmentCollectionService.deleteFragmentCollections(
			deleteFragmentCollectionIds);
	}

	public void deleteFragmentEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteFragmentEntryIds = null;

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		if (fragmentEntryId > 0) {
			deleteFragmentEntryIds = new long[] {fragmentEntryId};
		}
		else {
			deleteFragmentEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		_fragmentEntryService.deleteFragmentEntries(deleteFragmentEntryIds);
	}

	public void editFragmentCollection(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentCollectionId = ParamUtil.getLong(
			actionRequest, "fragmentCollectionId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (fragmentCollectionId <= 0) {

			// Add Fragment Collection

			_fragmentCollectionService.addFragmentCollection(
				serviceContext.getScopeGroupId(), name, description,
				serviceContext);
		}
		else {

			// Update Fragment Collection

			_fragmentCollectionService.updateFragmentCollection(
				fragmentCollectionId, name, description);
		}
	}

	public void editFragmentEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentCollectionId = ParamUtil.getLong(
			actionRequest, "fragmentCollectionId");

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		String name = ParamUtil.getString(actionRequest, "name");
		String css = ParamUtil.getString(actionRequest, "cssContent");
		String js = ParamUtil.getString(actionRequest, "jsContent");
		String html = ParamUtil.getString(actionRequest, "htmlContent");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (fragmentEntryId <= 0) {

			// Add Fragment Entry

			_fragmentEntryService.addFragmentEntry(
				serviceContext.getScopeGroupId(), fragmentCollectionId, name,
				css, html, js, serviceContext);
		}
		else {

			// Update Fragment Entry

			_fragmentEntryService.updateFragmentEntry(
				fragmentEntryId, name, css, html, js);
		}
	}

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private FragmentEntryService _fragmentEntryService;

}