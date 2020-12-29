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

package com.liferay.style.book.web.internal.display.context;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditStyleBookEntryDisplayContext {

	public EditStyleBookEntryDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_frontendTokenDefinitionRegistry =
			(FrontendTokenDefinitionRegistry)_renderRequest.getAttribute(
				FrontendTokenDefinitionRegistry.class.getName());
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_setViewAttributes();
	}

	public Map<String, Object> getStyleBookEditorData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"frontendTokenDefinition", _getFrontendTokenDefinitionJSONObject()
		).put(
			"frontendTokensValues",
			() -> {
				StyleBookEntry styleBookEntry = _getStyleBookEntry();

				return JSONFactoryUtil.createJSONObject(
					styleBookEntry.getFrontendTokensValues());
			}
		).put(
			"initialPreviewLayout", _getInitialPreviewLayoutJSONObject()
		).put(
			"layoutsTreeURL",
			() -> {
				ResourceURL resourceURL = _renderResponse.createResourceURL();

				resourceURL.setResourceID("/style_book/layouts_tree");

				return resourceURL.toString();
			}
		).put(
			"namespace", _renderResponse.getNamespace()
		).put(
			"publishURL", _getActionURL("/style_book/publish_style_book_entry")
		).put(
			"redirectURL", _getRedirect()
		).put(
			"saveDraftURL", _getActionURL("/style_book/edit_style_book_entry")
		).put(
			"styleBookEntryId", _getStyleBookEntryId()
		).build();
	}

	private JSONArray _deepMergeJSONArrays(
			JSONArray jsonArray1, JSONArray jsonArray2)
		throws Exception {

		JSONArray jsonArray3 = JSONFactoryUtil.createJSONArray();

		Set<Integer> visited = new HashSet<>();

		_mergeJSONArrays(jsonArray1, jsonArray2, jsonArray3, visited);
		_mergeJSONArrays(jsonArray2, jsonArray1, jsonArray3, visited);

		return jsonArray3;
	}

	private JSONObject _deepMergeJSONObjects(
			JSONObject jsonObject1, JSONObject jsonObject2)
		throws Exception {

		if (jsonObject1 == null) {
			return JSONFactoryUtil.createJSONObject(jsonObject2.toString());
		}

		if (jsonObject2 == null) {
			return JSONFactoryUtil.createJSONObject(jsonObject1.toString());
		}

		JSONObject jsonObject3 = JSONFactoryUtil.createJSONObject(
			jsonObject1.toString());

		Iterator<String> iterator = jsonObject2.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (!jsonObject3.has(key)) {
				jsonObject3.put(key, jsonObject2.get(key));
			}
			else {
				Object value1 = jsonObject1.get(key);
				Object value2 = jsonObject2.get(key);

				if ((value1 instanceof JSONObject) &&
					(value2 instanceof JSONObject)) {

					jsonObject3.put(
						key,
						_deepMergeJSONObjects(
							(JSONObject)value1,
							jsonObject2.getJSONObject(key)));
				}
				else if ((value1 instanceof JSONArray) &&
						 (value2 instanceof JSONArray)) {

					JSONArray jsonArray1 = (JSONArray)value1;
					JSONArray jsonArray2 = (JSONArray)value2;

					jsonObject3.put(
						key, _deepMergeJSONArrays(jsonArray1, jsonArray2));
				}
				else {
					jsonObject3.put(key, value2);
				}
			}
		}

		return jsonObject3;
	}

	private String _getActionURL(String actionName) {
		PortletURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, actionName);

		return actionURL.toString();
	}

	private JSONObject _getFrontendTokenDefinitionJSONObject()
		throws Exception {

		LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			_themeDisplay.getSiteGroupId(), false);

		LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			_themeDisplay.getSiteGroupId(), true);

		FrontendTokenDefinition publicFrontendTokenDefinition =
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				publicLayoutSet.getThemeId());

		FrontendTokenDefinition privateFrontendTokenDefinition =
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				privateLayoutSet.getThemeId());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (publicFrontendTokenDefinition != null) {
			jsonObject = _deepMergeJSONObjects(
				jsonObject,
				JSONFactoryUtil.createJSONObject(
					publicFrontendTokenDefinition.getJSON(
						_themeDisplay.getLocale())));
		}

		if (privateFrontendTokenDefinition != null) {
			jsonObject = _deepMergeJSONObjects(
				jsonObject,
				JSONFactoryUtil.createJSONObject(
					privateFrontendTokenDefinition.getJSON(
						_themeDisplay.getLocale())));
		}

		return jsonObject;
	}

	private JSONObject _getInitialPreviewLayoutJSONObject() throws Exception {
		Group group = StagingUtil.getStagingGroup(
			_themeDisplay.getScopeGroupId());

		Layout layout = LayoutLocalServiceUtil.fetchFirstLayout(
			group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if (layout == null) {
			layout = LayoutLocalServiceUtil.fetchFirstLayout(
				group.getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (layout == null) {
				return null;
			}
		}

		String layoutURL = HttpUtil.addParameter(
			PortalUtil.getLayoutFullURL(layout, _themeDisplay), "p_l_mode",
			Constants.PREVIEW);

		return JSONUtil.put(
			"layoutName", layout.getName(_themeDisplay.getLocale())
		).put(
			"layoutURL", layoutURL
		);
	}

	private JSONObject _getJSONObject(
		JSONArray jsonArray, String uniquePropertyName,
		String uniquePropertyValue) {

		for (Object object : jsonArray) {
			if (object instanceof JSONObject) {
				JSONObject currentJSONObject = (JSONObject)object;

				String propertyValue = currentJSONObject.getString(
					uniquePropertyName);

				if (Objects.equals(propertyValue, uniquePropertyValue)) {
					return currentJSONObject;
				}
			}
		}

		return null;
	}

	private String _getRedirect() {
		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/style_book/view");

		return portletURL.toString();
	}

	private StyleBookEntry _getStyleBookEntry() {
		if (_styleBookEntry != null) {
			return _styleBookEntry;
		}

		_styleBookEntry = StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
			_getStyleBookEntryId());

		if (_styleBookEntry.isHead()) {
			StyleBookEntry draftStyleBookEntry =
				StyleBookEntryLocalServiceUtil.fetchDraft(_styleBookEntry);

			if (draftStyleBookEntry != null) {
				_styleBookEntry = draftStyleBookEntry;
			}
		}

		return _styleBookEntry;
	}

	private long _getStyleBookEntryId() {
		if (_styleBookEntryId != null) {
			return _styleBookEntryId;
		}

		_styleBookEntryId = ParamUtil.getLong(
			_httpServletRequest, "styleBookEntryId");

		return _styleBookEntryId;
	}

	private String _getStyleBookEntryTitle() {
		StyleBookEntry styleBookEntry = _getStyleBookEntry();

		return styleBookEntry.getName();
	}

	private void _mergeJSONArrays(
			JSONArray jsonArray1, JSONArray jsonArray2,
			JSONArray outputJSONArray, Set<Integer> visited)
		throws Exception {

		for (Object object : jsonArray1) {
			int hash = HashUtil.hash(0, object.toString());

			if (!visited.contains(hash) && (object instanceof JSONObject)) {
				JSONObject jsonObject1 = (JSONObject)object;

				String uniquePropertyName = "name";

				String uniquePropertyValue = jsonObject1.getString(
					uniquePropertyName);

				if (Validator.isNull(uniquePropertyValue)) {
					uniquePropertyName = "value";

					uniquePropertyValue = jsonObject1.getString(
						uniquePropertyName);
				}

				if (Validator.isNull(uniquePropertyValue)) {
					outputJSONArray.put(object);
				}
				else {
					JSONObject jsonObject2 = _getJSONObject(
						jsonArray2, uniquePropertyName, uniquePropertyValue);

					if (jsonObject2 == null) {
						outputJSONArray.put(jsonObject1);

						continue;
					}

					outputJSONArray.put(
						_deepMergeJSONObjects(jsonObject1, jsonObject2));

					visited.add(HashUtil.hash(0, jsonObject2.toString()));
				}

				visited.add(hash);
			}
			else if (!(object instanceof JSONObject)) {
				outputJSONArray.put(object);
			}
		}
	}

	private void _setViewAttributes() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(_getRedirect());

		_renderResponse.setTitle(_getStyleBookEntryTitle());
	}

	private final FrontendTokenDefinitionRegistry
		_frontendTokenDefinitionRegistry;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private StyleBookEntry _styleBookEntry;
	private Long _styleBookEntryId;
	private final ThemeDisplay _themeDisplay;

}