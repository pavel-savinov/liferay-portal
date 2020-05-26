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

<%@ page import="com.liferay.info.taglib.list.renderer.BasicListInfoListItemStyle" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %>

<%@ page import="java.util.Objects" %>

<%
String infoListItemStyleKey = GetterUtil.getString(request.getAttribute("liferay-info:info-list-grid:listItemStyleKey"));

String listCssClass = "";
String listItemCssClass = "";

if (Objects.equals(infoListItemStyleKey, BasicListInfoListItemStyle.BORDERED.getKey())) {
	listCssClass = "list-group";
	listItemCssClass = "list-group-item";
}
else if (Objects.equals(infoListItemStyleKey, BasicListInfoListItemStyle.INLINE.getKey())) {
	listCssClass = "list-inline";
	listItemCssClass = "list-inline-item";
}
else if (Objects.equals(infoListItemStyleKey, BasicListInfoListItemStyle.UNSTYLED.getKey())) {
	listCssClass = "list-unstyled";
}
%>