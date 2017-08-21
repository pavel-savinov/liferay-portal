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

<style>
	#wrapper .portlet-layout,
	.portlet {
		margin-bottom: 0;
	}

	.fragment-name {
		display: none;
	}
</style>

<%
String redirect = fragmentDisplayContext.getEditFragmentEntryRedirect();

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

long fragmentCollectionId = ParamUtil.getLong(request, "fragmentCollectionId");
long fragmentEntryId = ParamUtil.getLong(request, "fragmentEntryId");

FragmentEntry fragmentEntry = null;

if (fragmentEntryId > 0) {
	fragmentEntry = FragmentEntryServiceUtil.fetchFragmentEntry(fragmentEntryId);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((fragmentEntry == null) ? LanguageUtil.get(request, "add-fragment-entry") : fragmentEntry.getName()));
%>

<portlet:actionURL name="editFragmentEntry" var="editFragmentEntryURL">
	<portlet:param name="mvcPath" value="/edit_fragment_entry.jsp" />
</portlet:actionURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="code" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<aui:form action="<%= editFragmentEntryURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fragmentEntryId" type="hidden" value="<%= fragmentEntryId %>" />
	<aui:input name="fragmentCollectionId" type="hidden" value="<%= fragmentCollectionId %>" />
	<aui:input name="htmlContent" type="hidden" value="<%= HtmlUtil.escape(fragmentEntry != null ? fragmentEntry.getHtml() : StringPool.BLANK) %>" />
	<aui:input name="cssContent" type="hidden" value="<%= HtmlUtil.escape(fragmentEntry != null ? fragmentEntry.getCss() : StringPool.BLANK) %>" />
	<aui:input name="jsContent" type="hidden" value="<%= HtmlUtil.escape(fragmentEntry != null ? fragmentEntry.getJs() : StringPool.BLANK) %>" />

	<aui:model-context bean="<%= fragmentEntry %>" model="<%= FragmentEntry.class %>" />

	<liferay-ui:error exception="<%= DuplicateFragmentEntryException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= FragmentEntryNameException.class %>" message="please-enter-a-valid-name" />

	<div class="fragment-name">
		<aui:input autoFocus="<%= true %>" label="name" name="name" placeholder="name" />
	</div>

	<%
	Map<String, Object> context = new HashMap<>();
	context.put("namespace", portletDisplay.getNamespace());
	%>

	<soy:template-renderer
		context="<%= context %>"
		module="modern-site-building-fragment-web/js/FragmentEditor.es"
		templateNamespace="FragmentEditor.render"
	/>

	<aui:button-row cssClass="fragment-submit-buttons">
		<aui:button cssClass="btn" type="submit" />
	</aui:button-row>
</aui:form>

<script id="<%= portletDisplay.getNamespace() %>nameEditorTemplate" type="text/template">
	<aui:form name="fragmentNameFm">
		<aui:input autoFocus="<%= true %>" label="name" name="fragmentNameInput" type="text">
			<aui:validator errorMessage="please-enter-a-valid-name" name="required" />
		</aui:input>

		<aui:button-row cssClass="pull-right">
			<aui:button cssClass="btn" name="fragmentNameCancel" type="cancel" />
			<aui:button cssClass="btn" type="submit" />
		</aui:button-row>
	</aui:form>
</script>

<aui:script require="modern-site-building-fragment-web/js/FragmentNameEditor.es">
	new modernSiteBuildingFragmentWebJsFragmentNameEditorEs.default({
		namespace: "<%= portletDisplay.getNamespace() %>",
		backUrl: "<%= redirect %>",
		title: "<%= LanguageUtil.get(request, "add-fragment-entry") %>"
	});
</aui:script>