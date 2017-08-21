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
String redirect = ParamUtil.getString(request, "redirect");

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();

	redirect = portletURL.toString();
}

long fragmentCollectionId = ParamUtil.getLong(request, "fragmentCollectionId");

FragmentCollection fragmentCollection = null;

if (fragmentCollectionId > 0) {
	fragmentCollection = FragmentCollectionServiceUtil.fetchFragmentCollection(fragmentCollectionId);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((fragmentCollection == null) ? LanguageUtil.get(request, "add-fragment-collection") : fragmentCollection.getName()));
%>

<portlet:actionURL name="editFragmentCollection" var="editFragmentCollectionURL">
	<portlet:param name="mvcPath" value="/edit_fragment_collection.jsp" />
</portlet:actionURL>

<aui:form action="<%= editFragmentCollectionURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fragmentCollectionId" type="hidden" value="<%= fragmentCollectionId %>" />

	<liferay-ui:error exception="<%= DuplicateFragmentCollectionException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= FragmentCollectionNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= fragmentCollection %>" model="<%= FragmentCollection.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" label="name" name="name" placeholder="name">
				<aui:validator errorMessage="please-enter-a-valid-name" name="required" />
			</aui:input>

			<aui:input name="description" placeholder="description" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>