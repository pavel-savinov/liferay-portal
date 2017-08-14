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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.modern.site.building.fragment.exception.DuplicateFragmentCollectionException" %><%@
page import="com.liferay.modern.site.building.fragment.exception.DuplicateFragmentEntryException" %><%@
page import="com.liferay.modern.site.building.fragment.exception.FragmentCollectionNameException" %><%@
page import="com.liferay.modern.site.building.fragment.exception.FragmentEntryNameException" %><%@
page import="com.liferay.modern.site.building.fragment.model.FragmentCollection" %><%@
page import="com.liferay.modern.site.building.fragment.model.FragmentEntry" %><%@
page import="com.liferay.modern.site.building.fragment.service.FragmentCollectionServiceUtil" %><%@
page import="com.liferay.modern.site.building.fragment.service.FragmentEntryServiceUtil" %><%@
page import="com.liferay.modern.site.building.fragment.web.internal.display.context.FragmentDisplayContext" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
FragmentDisplayContext fragmentDisplayContext = new FragmentDisplayContext(renderRequest, renderResponse, request);
%>

<%@ include file="/init-ext.jsp" %>