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
import com.liferay.info.display.contributor.InfoDisplayTemplate;

import java.util.Locale;

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

	private final AssetEntry _assetEntry;

}