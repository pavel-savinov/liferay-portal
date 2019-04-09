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

package com.liferay.layout.type.controller.display.page.internal.display.context;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayTemplate;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.type.controller.display.page.internal.constants.DisplayPageLayoutTypeControllerWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.constants.SegmentsWebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageLayoutTypeControllerDisplayContext {

	public DisplayPageLayoutTypeControllerDisplayContext(
		HttpServletRequest request) {

		_request = request;

		_infoDisplayTemplate = (InfoDisplayTemplate)request.getAttribute(
			DisplayPageLayoutTypeControllerWebKeys.INFO_DISPLAY_TEMPLATE);

		InfoDisplayContributor infoDisplayContributor =
			(InfoDisplayContributor)_request.getAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR);

		if ((infoDisplayContributor == null) &&
			(_infoDisplayTemplate != null)) {

			InfoDisplayContributorTracker infoDisplayContributorTracker =
				(InfoDisplayContributorTracker)request.getAttribute(
					ContentPageEditorWebKeys.ASSET_DISPLAY_CONTRIBUTOR_TRACKER);

			infoDisplayContributor =
				infoDisplayContributorTracker.getInfoDisplayContributor(
					PortalUtil.getClassName(
						_infoDisplayTemplate.getClassNameId()));
		}

		_infoDisplayContributor = infoDisplayContributor;
	}

	public AssetRendererFactory getAssetRendererFactory() {
		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassNameId(
				_infoDisplayTemplate.getClassNameId());
	}

	public Map<String, Object> getInfoDisplayFieldsValues()
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long versionClassPK = GetterUtil.getLong(
			_request.getAttribute(InfoDisplayWebKeys.VERSION_CLASS_PK));

		if (versionClassPK > 0) {
			return _infoDisplayContributor.getVersionInfoDisplayFieldsValues(
				_infoDisplayTemplate.getModel(), versionClassPK,
				themeDisplay.getLocale());
		}

		return _infoDisplayContributor.getInfoDisplayFieldsValues(
			_infoDisplayTemplate.getModel(), themeDisplay.getLocale());
	}

	public InfoDisplayTemplate getInfoDisplayTemplate() {
		return _infoDisplayTemplate;
	}

	public long getLayoutPageTemplateEntryId() {
		AssetDisplayPageEntry assetDisplayPageEntry =
			AssetDisplayPageEntryLocalServiceUtil.fetchAssetDisplayPageEntry(
				_infoDisplayTemplate.getGroupId(),
				_infoDisplayTemplate.getClassNameId(),
				_infoDisplayTemplate.getClassPK());

		if ((assetDisplayPageEntry == null) ||
			(assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_NONE)) {

			return 0;
		}

		if (assetDisplayPageEntry.getType() ==
				AssetDisplayPageConstants.TYPE_SPECIFIC) {

			return assetDisplayPageEntry.getLayoutPageTemplateEntryId();
		}

		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.
				fetchDefaultLayoutPageTemplateEntry(
					_infoDisplayTemplate.getGroupId(),
					_infoDisplayTemplate.getClassNameId(),
					_infoDisplayTemplate.getClassTypeId());

		if (defaultLayoutPageTemplateEntry != null) {
			return defaultLayoutPageTemplateEntry.
				getLayoutPageTemplateEntryId();
		}

		return 0;
	}

	public long[] getSegmentExperienceIds() {
		return GetterUtil.getLongValues(
			_request.getAttribute(SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
			new long[] {SegmentsConstants.SEGMENTS_EXPERIENCE_ID_DEFAULT});
	}

	public JSONArray getStructureJSONArray() throws PortalException {
		InfoDisplayTemplate infoDisplayTemplate = getInfoDisplayTemplate();

		if (infoDisplayTemplate == null) {
			return null;
		}

		long[] segmentsExperienceIds = getSegmentExperienceIds();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.getLayoutPageTemplateEntry(
				getLayoutPageTemplateEntryId());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					infoDisplayTemplate.getGroupId(),
					PortalUtil.getClassNameId(Layout.class.getName()),
					layoutPageTemplateEntry.getPlid(), true);

		String data = layoutPageTemplateStructure.getData(
			segmentsExperienceIds);

		if (Validator.isNull(data)) {
			return null;
		}

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

		return dataJSONObject.getJSONArray("structure");
	}

	private final InfoDisplayContributor _infoDisplayContributor;
	private final InfoDisplayTemplate _infoDisplayTemplate;
	private final HttpServletRequest _request;

}