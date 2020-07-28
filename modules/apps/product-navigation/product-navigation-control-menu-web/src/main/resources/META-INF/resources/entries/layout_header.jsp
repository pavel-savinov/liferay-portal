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
String headerTitle = HtmlUtil.escape(layout.getName(locale));

String layoutFriendlyURL = layout.getFriendlyURL();

String portletId = ParamUtil.getString(request, "p_p_id");

if (Validator.isNotNull(portletId) && layout.isSystem() && !layout.isTypeControlPanel() && layoutFriendlyURL.equals(PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL)) {
	headerTitle = PortalUtil.getPortletTitle(portletId, locale);
}

String cssClass = "control-menu-nav-item";

if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_COLLECTION)) {
	cssClass += " control-menu-nav-item-content";
}
%>

<li class="<%= cssClass %>">
	<span class="control-menu-level-1-heading text-truncate" data-qa-id="headerTitle"><%= headerTitle %></span>
</li>