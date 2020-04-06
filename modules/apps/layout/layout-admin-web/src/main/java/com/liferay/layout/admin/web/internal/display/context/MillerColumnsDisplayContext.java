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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.LayoutTypeControllerTracker;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class MillerColumnsDisplayContext {

	public MillerColumnsDisplayContext(
		LayoutsAdminDisplayContext layoutsAdminDisplayContext,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_layoutsAdminDisplayContext = layoutsAdminDisplayContext;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);
		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getLayoutChildrenURL() {
		PortletURL itemChildrenURL = _liferayPortletResponse.createActionURL();

		itemChildrenURL.setParameter(
			ActionRequest.ACTION_NAME, "/layout/get_layout_children");

		return itemChildrenURL.toString();
	}

	public JSONArray getLayoutColumnsJSONArray() throws Exception {
		JSONArray layoutColumnsJSONArray = JSONUtil.put(
			_getFirstLayoutColumnJSONArray());

		if (_layoutsAdminDisplayContext.isFirstColumn()) {
			return layoutColumnsJSONArray;
		}

		JSONArray layoutSetBranchesJSONArray =
			_layoutsAdminDisplayContext.getLayoutSetBranchesJSONArray();

		if (layoutSetBranchesJSONArray.length() > 0) {
			layoutColumnsJSONArray.put(layoutSetBranchesJSONArray);
		}

		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (selLayout == null) {
			layoutColumnsJSONArray.put(
				getLayoutsJSONArray(
					0, _layoutsAdminDisplayContext.isPrivateLayout()));

			return layoutColumnsJSONArray;
		}

		List<Layout> layouts = ListUtil.copy(selLayout.getAncestors());

		Collections.reverse(layouts);

		layouts.add(selLayout);

		for (Layout layout : layouts) {
			layoutColumnsJSONArray.put(
				getLayoutsJSONArray(
					layout.getParentLayoutId(), selLayout.isPrivateLayout()));
		}

		layoutColumnsJSONArray.put(
			getLayoutsJSONArray(
				selLayout.getLayoutId(), selLayout.isPrivateLayout()));

		return layoutColumnsJSONArray;
	}

	public Map<String, Object> getLayoutData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"context",
			Collections.singletonMap(
				"namespace", _liferayPortletResponse.getNamespace())
		).put(
			"props",
			HashMapBuilder.<String, Object>put(
				"breadcrumbEntries",
				_layoutsAdminDisplayContext.getBreadcrumbEntriesJSONArray()
			).put(
				"getItemChildrenURL", getLayoutChildrenURL()
			).put(
				"layoutColumns", getLayoutColumnsJSONArray()
			).put(
				"moveItemURL",
				_layoutsAdminDisplayContext.getMoveLayoutColumnItemURL()
			).put(
				"searchContainerId", "pages"
			).build()
		).build();
	}

	public JSONArray getLayoutsJSONArray(
			long parentLayoutId, boolean privateLayout)
		throws Exception {

		JSONArray layoutsJSONArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			_layoutsAdminDisplayContext.getSelGroupId(), privateLayout,
			parentLayoutId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (Layout layout : layouts) {
			if (_layoutsAdminDisplayContext.getActiveLayoutSetBranchId() > 0) {
				LayoutRevision layoutRevision =
					LayoutStagingUtil.getLayoutRevision(layout);

				if ((layoutRevision != null) && layoutRevision.isIncomplete()) {
					continue;
				}
			}

			JSONObject layoutJSONObject = JSONUtil.put(
				"actions", _getLayoutActionsJSONArray(layout)
			).put(
				"active", _layoutsAdminDisplayContext.isActive(layout.getPlid())
			).put(
				"bulkActions",
				StringUtil.merge(
					_layoutsAdminDisplayContext.getAvailableActions(layout))
			);

			LayoutTypeController layoutTypeController =
				LayoutTypeControllerTracker.getLayoutTypeController(
					layout.getType());

			ResourceBundle layoutTypeResourceBundle =
				ResourceBundleUtil.getBundle(
					"content.Language", _themeDisplay.getLocale(),
					layoutTypeController.getClass());

			layoutJSONObject.put(
				"description",
				LanguageUtil.get(
					_httpServletRequest, layoutTypeResourceBundle,
					"layout.types." + layout.getType())
			).put(
				"draggable", true
			);

			int childLayoutsCount = LayoutLocalServiceUtil.getLayoutsCount(
				_layoutsAdminDisplayContext.getSelGroup(),
				layout.isPrivateLayout(), layout.getLayoutId());

			layoutJSONObject.put(
				"hasChild", childLayoutsCount > 0
			).put(
				"id", layout.getPlid()
			);

			LayoutType layoutType = layout.getLayoutType();

			layoutJSONObject.put(
				"parentable", layoutType.isParentable()
			).put(
				"selectable", true
			).put(
				"states", _getLayoutStatesJSONArray(layout)
			).put(
				"title", layout.getName(_themeDisplay.getLocale())
			);

			PortletURL portletURL = _layoutsAdminDisplayContext.getPortletURL();

			portletURL.setParameter(
				"selPlid", String.valueOf(layout.getPlid()));
			portletURL.setParameter(
				"layoutSetBranchId",
				String.valueOf(
					_layoutsAdminDisplayContext.getActiveLayoutSetBranchId()));
			portletURL.setParameter(
				"privateLayout", String.valueOf(layout.isPrivateLayout()));

			layoutJSONObject.put(
				"url", portletURL.toString()
			).put(
				"viewUrl", _layoutsAdminDisplayContext.getViewLayoutURL(layout)
			);

			layoutsJSONArray.put(layoutJSONObject);
		}

		return layoutsJSONArray;
	}

	private JSONObject _getFirstLayoutColumn(
			boolean privatePages, boolean active)
		throws PortalException {

		JSONObject pagesJSONObject = JSONUtil.put(
			"actions", _getFirstLayoutColumnActionsJSONArray(privatePages)
		).put(
			"active", active
		).put(
			"hasChild", true
		).put(
			"id", LayoutConstants.DEFAULT_PLID
		).put(
			"title", _layoutsAdminDisplayContext.getTitle(privatePages)
		);

		PortletURL pagesURL = _layoutsAdminDisplayContext.getPortletURL();

		pagesURL.setParameter(
			"selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));
		pagesURL.setParameter("privateLayout", String.valueOf(privatePages));

		pagesJSONObject.put("url", pagesURL.toString());

		return pagesJSONObject;
	}

	private JSONArray _getFirstLayoutColumnActionsJSONArray(
			boolean privatePages)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (_layoutsAdminDisplayContext.isShowAddRootLayoutButton()) {
			jsonArray.put(
				JSONUtil.put(
					"icon", "plus"
				).put(
					"id", "add"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "add")
				).put(
					"quickAction", true
				).put(
					"url",
					_layoutsAdminDisplayContext.
						getSelectLayoutPageTemplateEntryURL(privatePages)
				));
		}

		if (_layoutsAdminDisplayContext.isShowFirstColumnConfigureAction()) {
			jsonArray.put(
				JSONUtil.put(
					"icon", "cog"
				).put(
					"id", "configure"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "configure")
				).put(
					"quickAction", true
				).put(
					"url",
					_layoutsAdminDisplayContext.
						getFirstColumnConfigureLayoutURL(privatePages)
				));
		}

		return jsonArray;
	}

	private JSONArray _getFirstLayoutColumnJSONArray() throws Exception {
		JSONArray firstColumnJSONArray = JSONFactoryUtil.createJSONArray();

		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (LayoutLocalServiceUtil.hasLayouts(
				_layoutsAdminDisplayContext.getSelGroup(), false) &&
			_layoutsAdminDisplayContext.isShowPublicPages()) {

			boolean active = !_layoutsAdminDisplayContext.isPrivateLayout();

			if (selLayout != null) {
				active = selLayout.isPublicLayout();
			}

			if (_layoutsAdminDisplayContext.isFirstColumn()) {
				active = false;
			}

			firstColumnJSONArray.put(_getFirstLayoutColumn(false, active));
		}

		if (LayoutLocalServiceUtil.hasLayouts(
				_layoutsAdminDisplayContext.getSelGroup(), true)) {

			boolean active = _layoutsAdminDisplayContext.isPrivateLayout();

			if (selLayout != null) {
				active = selLayout.isPrivateLayout();
			}

			if (_layoutsAdminDisplayContext.isFirstColumn()) {
				active = false;
			}

			firstColumnJSONArray.put(_getFirstLayoutColumn(true, active));
		}

		return firstColumnJSONArray;
	}

	private JSONArray _getLayoutActionsJSONArray(Layout layout)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (_layoutsAdminDisplayContext.isShowAddChildPageAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"icon", "plus"
				).put(
					"id", "add"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "add")
				).put(
					"quickAction", true
				).put(
					"url",
					_layoutsAdminDisplayContext.
						getSelectLayoutPageTemplateEntryURL(
							_layoutsAdminDisplayContext.
								getFirstLayoutPageTemplateCollectionId(),
							layout.getPlid(), layout.isPrivateLayout())
				));
		}

		if (_layoutsAdminDisplayContext.isShowConfigureAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"icon", "cog"
				).put(
					"id", "configure"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "configure")
				).put(
					"url",
					_layoutsAdminDisplayContext.getConfigureLayoutURL(layout)
				));
		}

		Layout draftLayout = LayoutLocalServiceUtil.fetchLayout(
			PortalUtil.getClassNameId(Layout.class), layout.getPlid());

		if (_layoutsAdminDisplayContext.isShowConvertLayoutAction(layout)) {
			if (draftLayout == null) {
				jsonArray.put(
					JSONUtil.put(
						"id", "layoutConversionPreview"
					).put(
						"label",
						LanguageUtil.get(
							_httpServletRequest, "convert-to-content-page...")
					).put(
						"url",
						_layoutsAdminDisplayContext.
							getLayoutConversionPreviewURL(layout)
					));
			}
			else {
				jsonArray.put(
					JSONUtil.put(
						"id", "deleteLayoutConversionPreview"
					).put(
						"label",
						LanguageUtil.get(
							_httpServletRequest, "discard-conversion-draft")
					).put(
						"url",
						_layoutsAdminDisplayContext.getDeleteLayoutURL(layout)
					));
			}
		}

		if (_layoutsAdminDisplayContext.isShowCopyLayoutAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"id", "copyLayout"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "copy-page")
				).put(
					"url",
					_layoutsAdminDisplayContext.getCopyLayoutRenderURL(layout)
				));
		}
		else {
			jsonArray.put(
				JSONUtil.put(
					"id", "copyLayout"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "copy-page")
				));
		}

		if (_layoutsAdminDisplayContext.isShowDeleteAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"id", "delete"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "delete")
				).put(
					"url",
					_layoutsAdminDisplayContext.getDeleteLayoutURL(layout)
				));
		}

		if (_layoutsAdminDisplayContext.isConversionDraft(layout) &&
			_layoutsAdminDisplayContext.isShowConfigureAction(layout)) {

			jsonArray.put(
				JSONUtil.put(
					"id", "editConversionLayout"
				).put(
					"label",
					LanguageUtil.get(
						_httpServletRequest, "edit-conversion-draft")
				).put(
					"url", _layoutsAdminDisplayContext.getEditLayoutURL(layout)
				));
		}
		else if (_layoutsAdminDisplayContext.isShowConfigureAction(layout)) {
			String editLayoutURL = _layoutsAdminDisplayContext.getEditLayoutURL(
				layout);

			if (Validator.isNotNull(editLayoutURL)) {
				jsonArray.put(
					JSONUtil.put(
						"id", "editLayout"
					).put(
						"label", LanguageUtil.get(_httpServletRequest, "edit")
					).put(
						"url", editLayoutURL
					));
			}
			else if (layout.isTypeContent()) {
				jsonArray.put(
					JSONUtil.put(
						"id", "editLayout"
					).put(
						"label", LanguageUtil.get(_httpServletRequest, "edit")
					));
			}
		}

		if (_layoutsAdminDisplayContext.isShowOrphanPortletsAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"id", "orphanPortlets"
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "orphan-widgets")
				).put(
					"url",
					_layoutsAdminDisplayContext.getOrphanPortletsURL(layout)
				));
		}

		if (_layoutsAdminDisplayContext.isShowPermissionsAction(layout)) {
			jsonArray.put(
				JSONUtil.put(
					"id", "permissions"
				).put(
					"label",
					LanguageUtil.get(_httpServletRequest, "permissions")
				).put(
					"url", _layoutsAdminDisplayContext.getPermissionsURL(layout)
				));
		}

		if (layout.isDenied() || layout.isPending()) {
			jsonArray.put(
				JSONUtil.put(
					"id", "previewLayout"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "preview")
				).put(
					"url", _layoutsAdminDisplayContext.getViewLayoutURL(layout)
				));
		}
		else {
			boolean published = true;

			if (draftLayout != null) {
				published = GetterUtil.getBoolean(
					draftLayout.getTypeSettingsProperty("published"));
			}

			if (!layout.isTypeContent() || published) {
				jsonArray.put(
					JSONUtil.put(
						"id", "viewLayout"
					).put(
						"label", LanguageUtil.get(_httpServletRequest, "view")
					).put(
						"url",
						_layoutsAdminDisplayContext.getViewLayoutURL(layout)
					));
			}
			else {
				jsonArray.put(
					JSONUtil.put(
						"id", "viewLayout"
					).put(
						"label", LanguageUtil.get(_httpServletRequest, "view")
					));
			}
		}

		return jsonArray;
	}

	private JSONArray _getLayoutStatesJSONArray(Layout layout)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Layout draftLayout = LayoutLocalServiceUtil.fetchLayout(
			PortalUtil.getClassNameId(Layout.class), layout.getPlid());

		if (layout.isTypeContent()) {
			boolean published = GetterUtil.getBoolean(
				draftLayout.getTypeSettingsProperty("published"));

			if ((draftLayout.getStatus() == WorkflowConstants.STATUS_DRAFT) ||
				!published) {

				jsonArray.put(
					JSONUtil.put(
						"id", "draft"
					).put(
						"label", LanguageUtil.get(_httpServletRequest, "draft")
					));
			}
		}
		else {
			if (draftLayout != null) {
				jsonArray.put(
					JSONUtil.put(
						"id", "conversionPreview"
					).put(
						"label",
						LanguageUtil.get(
							_httpServletRequest, "conversion-draft")
					));
			}
		}

		if (layout.isDenied() || layout.isPending()) {
			jsonArray.put(
				JSONUtil.put(
					"id", "pending"
				).put(
					"label", LanguageUtil.get(_httpServletRequest, "pending")
				));
		}

		return jsonArray;
	}

	private final HttpServletRequest _httpServletRequest;
	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final ThemeDisplay _themeDisplay;

}