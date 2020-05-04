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

package com.liferay.info.internal.display.breadcrumb;

import com.liferay.info.display.breadcrumb.InfoDisplayBreadcrumbEntriesProvider;
import com.liferay.info.display.breadcrumb.InfoDisplayBreadcrumbEntriesProviderTracker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	service = InfoDisplayBreadcrumbEntriesProviderTracker.class
)
public class InfoDisplayBreadcrumbEntriesProviderTrackerImpl
	implements InfoDisplayBreadcrumbEntriesProviderTracker {

	@Override
	public InfoDisplayBreadcrumbEntriesProvider
		getInfoDisplayBreadcrumbEntriesProvider(String className) {

		return _infoDisplayBreadcrumbEntriesProviders.get(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setInfoDisplayBreadcrumbEntriesProvider(
		InfoDisplayBreadcrumbEntriesProvider
			infoDisplayBreadcrumbEntriesProvider) {

		_infoDisplayBreadcrumbEntriesProviders.put(
			infoDisplayBreadcrumbEntriesProvider.getClassName(),
			infoDisplayBreadcrumbEntriesProvider);
	}

	protected void unsetInfoDisplayBreadcrumbEntriesProvider(
		InfoDisplayBreadcrumbEntriesProvider
			infoDisplayBreadcrumbEntriesProvider) {

		_infoDisplayBreadcrumbEntriesProviders.remove(
			infoDisplayBreadcrumbEntriesProvider.getClassName());
	}

	private final Map<String, InfoDisplayBreadcrumbEntriesProvider>
		_infoDisplayBreadcrumbEntriesProviders = new ConcurrentHashMap<>();

}