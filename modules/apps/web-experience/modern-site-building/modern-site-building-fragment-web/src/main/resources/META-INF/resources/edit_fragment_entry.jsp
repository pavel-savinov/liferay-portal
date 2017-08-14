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

<aui:form action="<%= editFragmentEntryURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="fragmentEntryId" type="hidden" value="<%= fragmentEntryId %>" />
	<aui:input name="fragmentCollectionId" type="hidden" value="<%= fragmentCollectionId %>" />
	<aui:input name="cssContent" type="hidden" />
	<aui:input name="htmlContent" type="hidden" />
	<aui:input name="jsContent" type="hidden" />

	<aui:model-context bean="<%= fragmentEntry %>" model="<%= FragmentEntry.class %>" />

	<liferay-ui:error exception="<%= DuplicateFragmentEntryException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= FragmentEntryNameException.class %>" message="please-enter-a-valid-name" />

	<%
	Map<String, Object> editorContext = new HashMap<>();
	editorContext.put("namespace", portletDisplay.getNamespace());
	%>

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= true %>" label="name" name="name" placeholder="name" />

			<div class="entry-content form-group">
				<liferay-ui:input-editor
					contents="<%= HtmlUtil.escape(fragmentEntry != null ? fragmentEntry.getCss() : StringPool.BLANK) %>"
					editorName="alloyeditor"
					name="cssEditor"
					showSource="<%= false %>"
				/>
			</div>

			<div class="entry-content form-group">
				<liferay-ui:input-editor
					contents="<%= HtmlUtil.escape(fragmentEntry != null ? fragmentEntry.getHtml() : StringPool.BLANK) %>"
					editorName="alloyeditor"
					name="htmlEditor"
					showSource="<%= false %>"
				/>
			</div>

			<div class="entry-content form-group">
				<liferay-ui:input-editor
					contents="<%= HtmlUtil.escape(fragmentEntry != null ? fragmentEntry.getJs() : StringPool.BLANK) %>"
					editorName="alloyeditor"
					name="jsEditor"
					showSource="<%= false %>"
				/>
			</div>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base,event-input">
	var form = A.one('#<portlet:namespace />fm');

	form.on(
		'submit',
		function() {
			form.one('#<portlet:namespace />cssContent').val(window.<portlet:namespace />cssEditor.getText());
			form.one('#<portlet:namespace />htmlContent').val(window.<portlet:namespace />htmlEditor.getText());
			form.one('#<portlet:namespace />jsContent').val(window.<portlet:namespace />jsEditor.getText());
		}
	);
</aui:script>