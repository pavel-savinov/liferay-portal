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

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.content.page.editor.web.internal.configuration.LayoutContentPageEditorConfiguration;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class ContentPageEditorLayoutPageTemplateDisplayContext
	extends ContentPageEditorDisplayContext {

	public ContentPageEditorLayoutPageTemplateDisplayContext(
		CommentManager commentManager,
		List<ContentPageEditorSidebarPanel> contentPageEditorSidebarPanels,
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererController fragmentRendererController,
		FragmentRendererTracker fragmentRendererTracker,
		HttpServletRequest httpServletRequest,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		ItemSelector itemSelector,
		LayoutContentPageEditorConfiguration
			layoutContentPageEditorConfiguration,
		boolean pageIsDisplayPage, PortletRequest portletRequest,
		RenderResponse renderResponse) {

		super(
			commentManager, contentPageEditorSidebarPanels,
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererController,
			fragmentRendererTracker, httpServletRequest,
			infoDisplayContributorTracker, itemSelector,
			layoutContentPageEditorConfiguration, portletRequest,
			renderResponse);

		_pageIsDisplayPage = pageIsDisplayPage;
	}

	@Override
	public Map<String, Object> getEditorContext(String npmResolvedPackageName)
		throws Exception {

		Map<String, Object> editorContext = super.getEditorContext(
			npmResolvedPackageName);

		if (!_pageIsDisplayPage) {
			return editorContext;
		}

		Map<String, Object> configContext =
			(Map<String, Object>)editorContext.get("config");

		configContext.put(
			"mappingFieldsURL",
			getResourceURL("/content_layout/get_mapping_fields"));
		configContext.put("selectedMappingTypes", _getSelectedMappingTypes());

		return editorContext;
	}

	@Override
	public String getPublishURL() {
		return getFragmentEntryActionURL(
			"/content_layout/publish_layout_page_template_entry");
	}

	@Override
	public List<Map<String, Object>> getSidebarPanels() {
		return getSidebarPanels(_pageIsDisplayPage);
	}

	@Override
	public boolean isWorkflowEnabled() {
		return false;
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry() {
		if (_layoutPageTemplateEntry != null) {
			return _layoutPageTemplateEntry;
		}

		Layout draftLayout = themeDisplay.getLayout();

		_layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(draftLayout.getClassPK());

		return _layoutPageTemplateEntry;
	}

	private String _getMappingSubtypeLabel() throws PortalException {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				layoutPageTemplateEntry.getClassName());

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSupportsClassTypes()) {

			return null;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(
			layoutPageTemplateEntry.getClassTypeId(), themeDisplay.getLocale());

		return classType.getName();
	}

	private String _getMappingTypeLabel() {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		InfoDisplayContributor infoDisplayContributor =
			infoDisplayContributorTracker.getInfoDisplayContributor(
				layoutPageTemplateEntry.getClassName());

		if (infoDisplayContributor == null) {
			return null;
		}

		return infoDisplayContributor.getLabel(themeDisplay.getLocale());
	}

	private Map<String, Object> _getSelectedMappingTypes() {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry();

		if ((layoutPageTemplateEntry == null) ||
			(layoutPageTemplateEntry.getClassNameId() <= 0)) {

			return Collections.emptyMap();
		}

		return HashMapBuilder.<String, Object>put(
			"type",
			HashMapBuilder.<String, Object>put(
				"id", layoutPageTemplateEntry.getClassNameId()
			).put(
				"label", _getMappingTypeLabel()
			).build()
		).put(
			"subtype",
			() -> {
				String subtypeLabel = _getMappingSubtypeLabel();

				if (Validator.isNull(subtypeLabel)) {
					return StringPool.BLANK;
				}

				return HashMapBuilder.<String, Object>put(
					"id", layoutPageTemplateEntry.getClassTypeId()
				).put(
					"label", subtypeLabel
				).build();
			}
		).build();
	}

	private LayoutPageTemplateEntry _layoutPageTemplateEntry;
	private final boolean _pageIsDisplayPage;

}