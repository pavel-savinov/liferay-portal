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
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);
PanelCategoryHelper panelCategoryHelper = (PanelCategoryHelper)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_HELPER);
%>

<liferay-application-list:panel-category
	panelCategory="<%= panelCategory %>"
	showBody="<%= false %>"
>

	<%
	Group curSite = themeDisplay.getSiteGroup();

	List<Layout> scopeLayouts = LayoutLocalServiceUtil.getScopeGroupLayouts(curSite.getGroupId());
	%>

	<c:choose>
		<c:when test="<%= scopeLayouts.isEmpty() %>">
			<liferay-application-list:panel-category-body
				panelCategory="<%= panelCategory %>"
			/>
		</c:when>
		<c:otherwise>

			<%
			PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);

			List<PanelApp> panelApps = panelAppRegistry.getPanelApps(panelCategory, themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup());
			%>

			<c:if test="<%= !panelApps.isEmpty() %>">
				<div class="scope-selector">

					<%
					Group curScopeGroup = themeDisplay.getScopeGroup();
					%>

					<div class="autofit-row autofit-row-center">
						<div class="autofit-col autofit-col-expand">
							<span class="scope-name">
								<c:choose>
									<c:when test="<%= curScopeGroup.isLayout() %>">
										<%= curScopeGroup.getDescriptiveName(locale) %> (<liferay-ui:message key="scope" />)
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="default-scope" />
									</c:otherwise>
								</c:choose>
							</span>
						</div>

						<%
						String portletId = themeDisplay.getPpid();

						if (Validator.isNull(portletId) || !panelCategoryHelper.containsPortlet(portletId, PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT, permissionChecker, curSite)) {
							portletId = panelCategoryHelper.getFirstPortletId(PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT, permissionChecker, curSite);
						}

						final PortletURL portletURL = PortalUtil.getControlPanelPortletURL(request, curSite, portletId, 0, 0, PortletRequest.RENDER_PHASE);

						final String itemLabel = LanguageUtil.get(locale, "default-scope");
						
						JSPDropdownItemList dropdownItems = new JSPDropdownItemList(pageContext){
							{
								add(
									dropdownItem -> {
										dropdownItem.setHref(portletURL.toString());
										dropdownItem.setLabel(itemLabel);
									}
								);


								for (Layout curScopeLayout : scopeLayouts) {
									Group scopeGroup = curScopeLayout.getScopeGroup();

									if (Validator.isNull(portletId) || !panelCategoryHelper.containsPortlet(portletId, PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT, permissionChecker, scopeGroup)) {
										portletId = panelCategoryHelper.getFirstPortletId(PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT, permissionChecker, scopeGroup);
									}

									if (Validator.isNull(portletId)) {
										continue;
									}

									final PortletURL portletURLa = PortalUtil.getControlPanelPortletURL(request, scopeGroup, portletId, 0, 0, PortletRequest.RENDER_PHASE);
								
									final String itemLabela = LanguageUtil.get(locale, HtmlUtil.escape(curScopeLayout.getName(locale)));

									add(
										dropdownItem -> {
											dropdownItem.setHref(portletURLa.toString());
											dropdownItem.setLabel(itemLabela);
										}
									);
								}

								
							}
						};
						%>
						
						<div class="autofit-col autofit-col-end">
							<clay:dropdown-menu
								dropdownItems="<%= dropdownItems %>"
								icon="cog"
								triggerCssClasses="dropdown-toggle icon-monospaced text-light"
							/>
						</div>
					</div>

					<liferay-application-list:panel-category-body
						panelCategory="<%= panelCategory %>"
					/>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-application-list:panel-category>