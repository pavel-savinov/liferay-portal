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

<%
renderResponse.setTitle(fragmentDisplayContext.getFragmentCollectionTitle());
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="fragments" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= true %>">
		<portlet:renderURL var="portletURL">
			<portlet:param name="mvcPath" value="/view_fragment_entries.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentDisplayContext.getFragmentCollectionId()) %>" />
			<portlet:param name="displayStyle" value="<%= fragmentDisplayContext.getDisplayStyle() %>" />
		</portlet:renderURL>

		<aui:nav-bar-search>
			<aui:form action="<%= portletURL.toString() %>" method="post" name="fm1">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="false"
	includeCheckBox="true"
	searchContainerId="fragmentEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon"} %>'
			portletURL="<%= currentURLObj %>"
			selectedDisplayStyle="<%= fragmentDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= currentURLObj %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= fragmentDisplayContext.getOrderByCol() %>"
			orderByType="<%= fragmentDisplayContext.getOrderByType() %>"
			orderColumns="<%= fragmentDisplayContext.getOrderColumns() %>"
			portletURL="<%= currentURLObj %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<aui:form action="<%= currentURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="fragmentEntries"
		searchContainer="<%= fragmentDisplayContext.getFragmentEntriesSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.modern.site.building.fragment.model.FragmentEntry"
			keyProperty="fragmentEntryId"
			modelVar="fragmentEntry"
		>

			<%
			row.setCssClass("entry-card lfr-asset-item " + row.getCssClass());
			%>

			<liferay-ui:search-container-column-text>
				<liferay-frontend:icon-vertical-card
					actionJsp="/fragment_entry_action.jsp"
					actionJspServletContext="<%= application %>"
					cssClass="entry-display-style"
					icon="page"
					resultRow="<%= row %>"
					rowChecker="<%= searchContainer.getRowChecker() %>"
					title="<%= fragmentEntry.getName() %>"
				>
					<liferay-frontend:vertical-card-header>
						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - fragmentEntry.getCreateDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
					</liferay-frontend:vertical-card-header>
				</liferay-frontend:icon-vertical-card>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= fragmentDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<portlet:renderURL var="addFragmentEntryURL">
	<portlet:param name="mvcPath" value="/edit_fragment_entry.jsp" />
	<portlet:param name="fragmentCollectionId" value="<%= String.valueOf(fragmentDisplayContext.getFragmentCollectionId()) %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-fragment-entry") %>' url="<%= addFragmentEntryURL.toString() %>" />
</liferay-frontend:add-menu>