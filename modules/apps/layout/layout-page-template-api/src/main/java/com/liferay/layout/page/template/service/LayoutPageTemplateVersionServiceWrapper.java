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

package com.liferay.layout.page.template.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutPageTemplateVersionService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateVersionService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateVersionServiceWrapper
	implements LayoutPageTemplateVersionService,
			   ServiceWrapper<LayoutPageTemplateVersionService> {

	public LayoutPageTemplateVersionServiceWrapper(
		LayoutPageTemplateVersionService layoutPageTemplateVersionService) {

		_layoutPageTemplateVersionService = layoutPageTemplateVersionService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutPageTemplateVersionService.getOSGiServiceIdentifier();
	}

	@Override
	public LayoutPageTemplateVersionService getWrappedService() {
		return _layoutPageTemplateVersionService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateVersionService layoutPageTemplateVersionService) {

		_layoutPageTemplateVersionService = layoutPageTemplateVersionService;
	}

	private LayoutPageTemplateVersionService _layoutPageTemplateVersionService;

}