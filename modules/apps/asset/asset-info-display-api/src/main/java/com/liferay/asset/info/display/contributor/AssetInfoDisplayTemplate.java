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

package com.liferay.asset.info.display.contributor;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.info.display.contributor.InfoDisplayTemplate;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AssetInfoDisplayTemplate
	implements InfoDisplayTemplate<AssetEntry> {

	public AssetInfoDisplayTemplate(AssetEntry assetEntry) {
		_assetEntry = assetEntry;
	}

	@Override
	public long getClassNameId() {
		return _assetEntry.getClassNameId();
	}

	@Override
	public long getClassPK() {
		return _assetEntry.getClassPK();
	}

	@Override
	public long getClassTypeId() {
		return _assetEntry.getClassTypeId();
	}

	@Override
	public String getDescription(Locale locale) {
		return _assetEntry.getDescription(locale);
	}

	@Override
	public long getGroupId() {
		return _assetEntry.getGroupId();
	}

	@Override
	public AssetEntry getModel() {
		return _assetEntry;
	}

	@Override
	public String getTitle(Locale locale) {
		return _assetEntry.getTitle(locale);
	}

	@Override
	public void prepareTemplate(HttpServletRequest request) {
		Locale locale = PortalUtil.getLocale(request);

		AssetEntry assetEntry = getModel();

		PortalUtil.setPageDescription(
			assetEntry.getDescription(locale), request);

		PortalUtil.setPageKeywords(
			_getAssetKeywords(
				assetEntry.getClassName(), assetEntry.getClassPK()),
			request);

		PortalUtil.setPageTitle(assetEntry.getTitle(locale), request);

		request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);
	}

	private String _getAssetKeywords(String className, long classPK) {
		String[] tagNames = AssetTagLocalServiceUtil.getTagNames(
			className, classPK);
		String[] categoryNames = AssetCategoryLocalServiceUtil.getCategoryNames(
			className, classPK);

		String[] keywords = new String[tagNames.length + categoryNames.length];

		ArrayUtil.combine(tagNames, categoryNames, keywords);

		return StringUtil.merge(keywords);
	}

	private final AssetEntry _assetEntry;

}