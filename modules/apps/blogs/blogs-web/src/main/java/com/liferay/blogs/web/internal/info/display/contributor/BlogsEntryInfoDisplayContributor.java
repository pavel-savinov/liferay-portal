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

package com.liferay.blogs.web.internal.info.display.contributor;

import com.liferay.asset.info.display.field.AssetEntryClassTypesInfoDisplayFieldsProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.display.field.InfoDisplayFieldProvider;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = InfoDisplayContributor.class)
public class BlogsEntryInfoDisplayContributor
	implements InfoDisplayContributor<BlogsEntry> {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		return _infoDisplayFieldHelper.getContributorInfoDisplayFields(
			locale, AssetEntry.class.getName(), BlogsEntry.class.getName());
	}

	@Override
	public Map<String, Object> getInfoDisplayFieldsValues(
			BlogsEntry blogsEntry, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldValues = new HashMap<>();

		InfoDisplayObjectProvider<BlogsEntry> infoDisplayObjectProvider =
			getInfoDisplayObjectProvider(blogsEntry.getEntryId());

		Map<String, Object> blogsInfoDisplayFieldValues =
			_infoDisplayFieldHelper.getContributorInfoDisplayFieldsValues(
				infoDisplayObjectProvider, locale);

		infoDisplayFieldValues.putAll(blogsInfoDisplayFieldValues);

		Map<String, Object> assetInfoDisplayFieldValues =
			_assetEntryInfoDisplayFieldHelper.
				getAssetEntryInfoDisplayFieldsValues(
					getClassName(), blogsEntry.getEntryId(), locale);

		infoDisplayFieldValues.putAll(assetInfoDisplayFieldValues);

		return infoDisplayFieldValues;
	}

	@Override
	public InfoDisplayObjectProvider<BlogsEntry> getInfoDisplayObjectProvider(
			long classPK)
		throws PortalException {

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(classPK);

		return new BlogsInfoDisplayObjectProvider(blogsEntry);
	}

	@Override
	public InfoDisplayObjectProvider<BlogsEntry> getInfoDisplayObjectProvider(
			long groupId, String urlTitle)
		throws PortalException {

		BlogsEntry blogsEntry = _blogsEntryService.getEntry(groupId, urlTitle);

		return new BlogsInfoDisplayObjectProvider(blogsEntry);
	}

	@Override
	public String getInfoURLSeparator() {
		return "/b/";
	}

	@Reference
	private AssetEntryClassTypesInfoDisplayFieldsProvider
		_assetEntryInfoDisplayFieldHelper;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private InfoDisplayFieldProvider _infoDisplayFieldHelper;

}