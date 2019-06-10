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
FragmentEntryLink fragmentEntryLink = FragmentEntryLinkLocalServiceUtil.createFragmentEntryLink(0);

String css = StringPool.BLANK;
String html = StringPool.BLANK;
String js = StringPool.BLANK;

if (renderRequest != null) {
	long fragmentEntryId = ParamUtil.getLong(renderRequest, "fragmentEntryId");

	FragmentEntry fragmentEntry = FragmentEntryLocalServiceUtil.fetchFragmentEntry(fragmentEntryId);

	fragmentEntryLink.setFragmentEntryId(fragmentEntryId);

	css = BeanParamUtil.getString(fragmentEntry, renderRequest, "css");
	html = BeanParamUtil.getString(fragmentEntry, renderRequest, "html");
	js = BeanParamUtil.getString(fragmentEntry, renderRequest, "js");
}
else {
	css = ParamUtil.getString(request, "css");
	html = ParamUtil.getString(request, "html");
	js = ParamUtil.getString(request, "js");
}

fragmentEntryLink.setCss(css);
fragmentEntryLink.setHtml(html);
fragmentEntryLink.setJs(js);

DefaultFragmentRendererContext defaultFragmentRendererContext = new DefaultFragmentRendererContext(fragmentEntryLink);

defaultFragmentRendererContext.setMode(FragmentEntryLinkConstants.VIEW);

try {
%>

	<%= fragmentRendererController.render(defaultFragmentRendererContext, request, response) %>

<%
}
catch (IOException ioe) {
%>

	<div class="alert alert-danger">
		<liferay-ui:message key="<%= ioe.getMessage() %>" />
	</div>

<%
}
%>