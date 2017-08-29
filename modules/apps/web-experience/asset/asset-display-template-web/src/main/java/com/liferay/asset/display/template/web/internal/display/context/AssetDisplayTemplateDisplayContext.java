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

package com.liferay.asset.display.template.web.internal.display.context;

import com.liferay.asset.display.template.constants.AssetDisplayTemplatePortletKeys;
import com.liferay.asset.display.template.model.AssetDisplayTemplate;
import com.liferay.asset.display.template.service.AssetDisplayTemplateLocalServiceUtil;
import com.liferay.asset.display.template.service.permission.AssetDisplayTemplatePermission;
import com.liferay.asset.display.template.util.comparator.AssetDisplayTemplateClassNameIdComparator;
import com.liferay.asset.display.template.util.comparator.AssetDisplayTemplateCreateDateComparator;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class AssetDisplayTemplateDisplayContext {

	public static OrderByComparator<AssetDisplayTemplate>
		getArticleOrderByComparator(String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<AssetDisplayTemplate> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new AssetDisplayTemplateCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("asset-type")) {
			orderByComparator = new AssetDisplayTemplateClassNameIdComparator(
				orderByAsc);
		}

		return orderByComparator;
	}

	public AssetDisplayTemplateDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest request, DDMDisplayRegistry ddmDisplayRegistry,
		DDMTemplateHelper ddmTemplateHelper) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_request = request;

		_ddmDisplayRegistry = ddmDisplayRegistry;
		_ddmTemplateHelper = ddmTemplateHelper;
	}

	public AssetDisplayTemplate getAssetDisplayTemplate()
		throws PortalException {

		if (_assetDisplayTemplate == null) {
			_assetDisplayTemplate =
				AssetDisplayTemplateLocalServiceUtil.fetchAssetDisplayTemplate(
					getAssetDisplayTemplateId());
		}

		return _assetDisplayTemplate;
	}

	public long getAssetDisplayTemplateId() {
		if (_assetDisplayTemplateId == null) {
			_assetDisplayTemplateId = ParamUtil.getLong(
				_request, "assetDisplayTemplateId");
		}

		return _assetDisplayTemplateId;
	}

	public String getAutocompleteJSON(
			HttpServletRequest request, String language)
		throws Exception {

		return _ddmTemplateHelper.getAutocompleteJSON(request, language);
	}

	public long[] getAvailableClassNameIds() {
		if (_availableClassNameIds == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_availableClassNameIds =
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					themeDisplay.getCompanyId(), true);
		}

		return _availableClassNameIds;
	}

	public DDMDisplay getDDMDisplay() {
		return _ddmDisplayRegistry.getDDMDisplay(
			AssetDisplayTemplatePortletKeys.ASSET_DISPLAY_TEMPLATE);
	}

	public DDMTemplate getDDMTemplate() throws Exception {
		if (_ddmTemplate != null) {
			return _ddmTemplate;
		}

		AssetDisplayTemplate assetDisplayTemplate = getAssetDisplayTemplate();

		if (assetDisplayTemplate != null) {
			_ddmTemplate = DDMTemplateLocalServiceUtil.getDDMTemplate(
				assetDisplayTemplate.getDDMTemplateId());
		}

		return _ddmTemplate;
	}

	public long getDefaultClassNameId() {
		return PortalUtil.getClassNameId(AssetDisplayTemplate.class);
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = portalPreferences.getValue(
			AssetDisplayTemplatePortletKeys.ASSET_DISPLAY_TEMPLATE,
			"display-style", "list");

		return _displayStyle;
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords", null);

		return _keywords;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "asc");

		return _orderByType;
	}

	public String getScriptContent() {
		if (_scriptContent == null) {
			_scriptContent = ParamUtil.getString(_request, "scriptContent");
		}

		return _scriptContent;
	}

	public SearchContainer getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer searchContainer = new SearchContainer(
			_renderRequest, _renderResponse.createRenderURL(), null,
			"there-are-no-asset-display-templates");

		String keywords = getKeywords();

		if (Validator.isNull(keywords)) {
			if (isShowAddButton()) {
				searchContainer.setEmptyResultsMessage(
					"there-are-no-asset-display-templates-you-can-add-an-" +
						"asset-display-template-by-clicking-plus-button-on-" +
							"the-bottom-right-corner");
				searchContainer.setEmptyResultsMessageCssClass(
					"taglib-empty-result-message-header-has-plus-btn");
			}
		}
		else {
			searchContainer.setSearch(true);
		}

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String orderByCol = getOrderByCol();

		searchContainer.setOrderByCol(orderByCol);

		String orderByType = getOrderByType();

		searchContainer.setOrderByType(orderByType);

		OrderByComparator<AssetDisplayTemplate> orderByComparator =
			getArticleOrderByComparator(orderByCol, orderByType);

		searchContainer.setOrderByComparator(orderByComparator);

		long scopeGroupId = themeDisplay.getScopeGroupId();

		int tagsCount =
			AssetDisplayTemplateLocalServiceUtil.getAssetDisplayTemplatesCount(
				scopeGroupId);

		searchContainer.setTotal(tagsCount);

		List<AssetDisplayTemplate> assetDisplayTemplates =
			AssetDisplayTemplateLocalServiceUtil.getAssetDisplayTemplates(
				scopeGroupId, searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(assetDisplayTemplates);

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public boolean hasPermission(
			AssetDisplayTemplate assetDisplayTemplate, String actionId)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, themeDisplay.getScopeGroupId(),
			AssetDisplayTemplate.class.getName(),
			assetDisplayTemplate.getAssetDisplayTemplateId(),
			AssetDisplayTemplatePortletKeys.ASSET_DISPLAY_TEMPLATE, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return AssetDisplayTemplatePermission.contains(
			permissionChecker, assetDisplayTemplate, actionId);
	}

	public boolean isAutocompleteEnabled(String language) {
		return _ddmTemplateHelper.isAutocompleteEnabled(language);
	}

	public boolean isDisabledManagementBar() throws PortalException {
		SearchContainer searchContainer = getSearchContainer();

		if (searchContainer.getTotal() <= 0) {
			return true;
		}

		return false;
	}

	public boolean isShowAddButton() {
		return true;
	}

	public boolean isShowSearch() throws PortalException {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		SearchContainer searchContainer = getSearchContainer();

		if (searchContainer.getTotal() > 0) {
			return true;
		}

		return false;
	}

	private AssetDisplayTemplate _assetDisplayTemplate;
	private Long _assetDisplayTemplateId;
	private long[] _availableClassNameIds;
	private final DDMDisplayRegistry _ddmDisplayRegistry;
	private DDMTemplate _ddmTemplate;
	private final DDMTemplateHelper _ddmTemplateHelper;
	private String _displayStyle;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private String _scriptContent;
	private SearchContainer _searchContainer;

}