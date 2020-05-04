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

package com.liferay.blogs.web.internal.info.display.breadcrumb;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.display.breadcrumb.InfoDisplayBreadcrumbEntriesProvider;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, service = InfoDisplayBreadcrumbEntriesProvider.class
)
public class BlogsEntryInfoDisplayBreadcrumbEntriesProvider
	implements InfoDisplayBreadcrumbEntriesProvider<BlogsEntry> {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public List<BreadcrumbEntry> getInfoDisplayObjectBreadcrumbEntries(
		BlogsEntry blogsEntry, Locale locale) {

		if (blogsEntry == null) {
			return Collections.emptyList();
		}

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(blogsEntry.getTitle());

		breadcrumbEntries.add(breadcrumbEntry);

		return breadcrumbEntries;
	}

}