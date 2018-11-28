<%--
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
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:search-container
	id="pages"
	searchContainer="<%= layoutsAdminDisplayContext.getLayoutsSearchContainer() %>"
>
	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.model.Layout"
		keyProperty="plid"
		modelVar="layout"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand table-cell-minw-200 table-title"
			name="title"
			value="<%= layout.getName(locale) %>"
		/>

		<%
		List<Layout> curLayouts = layout.getAncestors();

		Collections.reverse(curLayouts);

		StringBundler sb = new StringBundler((layouts.size() * 2) + 2);

		sb.append("../ ");

		for (Layout curLayout : curLayouts) {
			sb.append(curLayout.getName(locale));
			sb.append(" / ");
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand table-cell-minw-200"
			name="path"
			value="<%= sb.toString() %>"
		/>

		<%
		LayoutTypeController layoutTypeController = LayoutTypeControllerTracker.getLayoutTypeController(layout.getType());

		ResourceBundle layoutTypeResourceBundle = ResourceBundleUtil.getBundle("content.Language", locale, layoutTypeController.getClass());
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-ws-nowrap"
			name="type"
			value='<%= LanguageUtil.get(request, layoutTypeResourceBundle, "layout.types." + layout.getType()) %>'
		/>

		<liferay-ui:search-container-column-date
			cssClass="table-cell-ws-nowrap"
			name="create-date"
			property="createDate"
		/>

		<liferay-ui:search-container-column-jsp
			path="/layout_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="list"
		markupView="lexicon"
	/>
</liferay-ui:search-container>