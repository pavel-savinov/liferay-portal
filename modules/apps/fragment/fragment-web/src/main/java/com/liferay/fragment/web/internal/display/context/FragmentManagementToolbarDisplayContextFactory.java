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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.web.internal.constants.FragmentTypeConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class FragmentManagementToolbarDisplayContextFactory {

	public FragmentManagementToolbarDisplayContextFactory(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest httpServletRequest,
		FragmentDisplayContext fragmentDisplayContext) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_httpServletRequest = httpServletRequest;
		_fragmentDisplayContext = fragmentDisplayContext;
	}

	public FragmentManagementToolbarDisplayContext
		getFragmentManagementToolbarDisplayContext(String type) {

		if (Objects.equals(type, FragmentTypeConstants.BASIC_FRAGMENT_TYPE)) {
			return new BasicFragmentManagementToolbarDisplayContext(
				_liferayPortletRequest, _liferayPortletResponse,
				_httpServletRequest, _fragmentDisplayContext);
		}

		if (Objects.equals(type, FragmentTypeConstants.SHARED_FRAGMENT_TYPE)) {
			return new SharedFragmentManagementToolbarDisplayContext(
				_liferayPortletRequest, _liferayPortletResponse,
				_httpServletRequest, _fragmentDisplayContext);
		}

		return null;
	}

	private final FragmentDisplayContext _fragmentDisplayContext;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}