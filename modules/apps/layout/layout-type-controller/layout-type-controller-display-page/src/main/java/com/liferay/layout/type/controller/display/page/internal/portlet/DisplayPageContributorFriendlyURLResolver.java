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

package com.liferay.layout.type.controller.display.page.internal.portlet;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.util.AssetHelper;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObject;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.type.controller.display.page.internal.constants.DisplayPageLayoutTypeControllerWebKeys;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = FriendlyURLResolver.class)
public class DisplayPageContributorFriendlyURLResolver
	implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		InfoDisplayContributor infoDisplayContributor =
			_getInfoDisplayContributor(friendlyURL);

		request.setAttribute(
			InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR,
			infoDisplayContributor);

		request.setAttribute(
			InfoDisplayWebKeys.VERSION_CLASS_PK,
			_getVersionClassPK(friendlyURL));

		InfoDisplayObject infoDisplayObject = _getInfoDisplayObject(
			infoDisplayContributor, groupId, friendlyURL);

		Object modelEntry = infoDisplayObject.getModelEntry();

		if (modelEntry instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)modelEntry;

			Locale locale = _portal.getLocale(request);

			_portal.setPageDescription(
				assetEntry.getDescription(locale), request);

			_portal.setPageKeywords(
				_assetHelper.getAssetKeywords(
					assetEntry.getClassName(), assetEntry.getClassPK()),
				request);

			_portal.setPageTitle(assetEntry.getTitle(locale), request);

			request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);
		}

		request.setAttribute(
			DisplayPageLayoutTypeControllerWebKeys.INFO_DISPLAY_OBJECT,
			infoDisplayObject);

		Layout layout = _getInfoDisplayObjectLayout(infoDisplayObject);

		return _portal.getLayoutActualURL(layout, mainPath);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		InfoDisplayContributor infoDisplayContributor =
			_getInfoDisplayContributor(friendlyURL);

		Layout layout = _getInfoDisplayObjectLayout(
			_getInfoDisplayObject(
				infoDisplayContributor, groupId, friendlyURL));

		return new LayoutFriendlyURLComposite(layout, friendlyURL);
	}

	@Override
	public String getURLSeparator() {
		return "/a/";
	}

	@Override
	public String[] getURLSeparators() {
		Set<String> urlSeparators = new HashSet<>();

		List<InfoDisplayContributor> infoDisplayContributors =
			_infoDisplayContributorTracker.getInfoDisplayContributors();

		for (InfoDisplayContributor infoDisplayContributor :
				infoDisplayContributors) {

			urlSeparators.add(infoDisplayContributor.getInfoURLSeparator());
		}

		return ArrayUtil.toStringArray(urlSeparators);
	}

	private InfoDisplayContributor _getInfoDisplayContributor(
			String friendlyURL)
		throws PortalException {

		String infoURLSeparator = _getInfoURLSeparator(friendlyURL);

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.
				getInfoDisplayContributorByURLSeparator(infoURLSeparator);

		if (infoDisplayContributor == null) {
			throw new PortalException(
				"Info display contributor is not available for " +
					infoURLSeparator);
		}

		return infoDisplayContributor;
	}

	private InfoDisplayObject _getInfoDisplayObject(
			InfoDisplayContributor infoDisplayContributor, long groupId,
			String friendlyURL)
		throws PortalException {

		return infoDisplayContributor.getInfoDisplayObject(
			groupId, _getUrlTitle(friendlyURL));
	}

	private Layout _getInfoDisplayObjectLayout(
		InfoDisplayObject infoDisplayObject) {

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				infoDisplayObject.getGroupId(),
				infoDisplayObject.getClassNameId(),
				infoDisplayObject.getClassPK());

		if (assetDisplayPageEntry == null) {
			return null;
		}

		if (assetDisplayPageEntry.getType() !=
				AssetDisplayPageConstants.TYPE_DEFAULT) {

			return _layoutLocalService.fetchLayout(
				assetDisplayPageEntry.getPlid());
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				infoDisplayObject.getGroupId(),
				infoDisplayObject.getClassNameId(),
				infoDisplayObject.getClassTypeId());

		if (layoutPageTemplateEntry != null) {
			return _layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid());
		}

		return null;
	}

	private String _getInfoURLSeparator(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		return CharPool.SLASH + paths.get(0) + CharPool.SLASH;
	}

	private String _getUrlTitle(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		return paths.get(1);
	}

	private long _getVersionClassPK(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		if (paths.size() < 3) {
			return 0;
		}

		return GetterUtil.getLong(paths.get(2));
	}

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}