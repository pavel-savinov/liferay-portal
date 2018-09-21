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
String eventName = "_" + HtmlUtil.escapeJS(editAssetListDisplayContext.getPortletResource()) + "_selectAsset";

SearchContainer assetListEntryAssetEntryRelSearchContainer = editAssetListDisplayContext.getSearchContainer();
%>

<liferay-frontend:edit-form
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />
	<aui:input name="assetEntryId" type="hidden" />
	<aui:input name="position" type="hidden" />

	<liferay-frontend:edit-form-body>
		<h3 class="sheet-subtitle">
				<span class="autofit-padded-no-gutters autofit-row">
					<span class="autofit-col autofit-col-expand">
						<span class="heading-text">
							<liferay-ui:message key="asset-entries" />
						</span>
					</span>
				</span>
		</h3>

		<liferay-frontend:fieldset-group>
			<liferay-ui:search-container
				compactEmptyResultsMessage="<%= true %>"
				emptyResultsMessage="none"
				searchContainer="<%= assetListEntryAssetEntryRelSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.asset.list.model.AssetListEntryAssetEntryRel"
					escapedModel="<%= true %>"
					keyProperty="entryId"
					modelVar="assetListEntryAssetEntryRel"
				>

					<%
					AssetEntry assetEntry = AssetEntryServiceUtil.getEntry(assetListEntryAssetEntryRel.getAssetEntryId());

					AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetEntry.getClassName());

					AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(assetEntry.getClassPK(), AssetRendererFactory.TYPE_LATEST);
					%>

					<liferay-ui:search-container-column-text
						name="title"
						truncate="<%= true %>"
					>
						<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>

						<c:if test="<%= !assetEntry.isVisible() %>">
							(<aui:workflow-status
								markupView="lexicon"
								showIcon="<%= false %>"
								showLabel="<%= false %>"
								status="<%= assetRenderer.getStatus() %>"
								statusMessage='<%= (assetRenderer.getStatus() == 0) ? "not-visible" : WorkflowConstants.getStatusLabel(assetRenderer.getStatus()) %>'
							/>)
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="type"
						value="<%= assetRendererFactory.getTypeName(locale) %>"
					/>

					<liferay-ui:search-container-column-date
						name="modified-date"
						value="<%= assetEntry.getModifiedDate() %>"
					/>

					<liferay-ui:search-container-column-jsp
						path="/asset_list/asset_selection_action.jsp"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/asset_list/asset_selection_order_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>

		<%
		long[] groupIds = editAssetListDisplayContext.getGroupIds();

		for (long groupId : groupIds) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);
		%>

			<div class="select-asset-selector">
				<div class="edit-controls lfr-meta-actions">
					<liferay-ui:icon-menu
						cssClass="select-existing-selector"
						direction="right"
						message='<%= LanguageUtil.format(request, (groupIds.length == 1) ? "select" : "select-in-x", HtmlUtil.escape(group.getDescriptiveName(locale)), false) %>'
						showArrow="<%= false %>"
						showWhenSingleIcon="<%= true %>"
					>

						<%
						List<AssetRendererFactory<?>> assetRendererFactories = ListUtil.sort(AssetRendererFactoryRegistryUtil.getAssetRendererFactories(company.getCompanyId()), new AssetRendererFactoryTypeNameComparator(locale));

						for (AssetRendererFactory<?> curRendererFactory : assetRendererFactories) {
							long curGroupId = groupId;

							if (!curRendererFactory.isSelectable()) {
								continue;
							}

							PortletURL assetBrowserURL = PortletProviderUtil.getPortletURL(request, curRendererFactory.getClassName(), PortletProvider.Action.BROWSE);

							if (assetBrowserURL == null) {
								continue;
							}

							String portletId = curRendererFactory.getPortletId();

							if (group.isStagingGroup() && !group.isStagedPortlet(portletId)) {
								curGroupId = group.getLiveGroupId();
							}

							assetBrowserURL.setParameter("groupId", String.valueOf(curGroupId));
							assetBrowserURL.setParameter("selectedGroupIds", String.valueOf(curGroupId));
							assetBrowserURL.setParameter("typeSelection", curRendererFactory.getClassName());
							assetBrowserURL.setParameter("showNonindexable", String.valueOf(Boolean.TRUE));
							assetBrowserURL.setParameter("showScheduled", String.valueOf(Boolean.TRUE));
							assetBrowserURL.setParameter("eventName", eventName);
							assetBrowserURL.setPortletMode(PortletMode.VIEW);
							assetBrowserURL.setWindowState(LiferayWindowState.POP_UP);

							Map<String, Object> data = new HashMap<String, Object>();

							data.put("groupid", String.valueOf(curGroupId));

							if (!curRendererFactory.isSupportsClassTypes()) {
								data.put("href", assetBrowserURL.toString());

								String type = curRendererFactory.getTypeName(locale);

								data.put("destroyOnHide", true);
								data.put("title", LanguageUtil.format(request, "select-x", type, false));
								data.put("type", type);
						%>

								<liferay-ui:icon
									cssClass="asset-selector"
									data="<%= data %>"
									id="<%= curGroupId + FriendlyURLNormalizerUtil.normalize(type) %>"
									message="<%= HtmlUtil.escape(type) %>"
									url="javascript:;"
								/>

							<%
							}
							else {
								ClassTypeReader classTypeReader = curRendererFactory.getClassTypeReader();

								List<ClassType> assetAvailableClassTypes = classTypeReader.getAvailableClassTypes(PortalUtil.getCurrentAndAncestorSiteGroupIds(curGroupId), locale);

								for (ClassType assetAvailableClassType : assetAvailableClassTypes) {
									assetBrowserURL.setParameter("subtypeSelectionId", String.valueOf(assetAvailableClassType.getClassTypeId()));
									assetBrowserURL.setParameter("showNonindexable", String.valueOf(Boolean.TRUE));
									assetBrowserURL.setParameter("showScheduled", String.valueOf(Boolean.TRUE));

									data.put("href", assetBrowserURL.toString());

									String type = assetAvailableClassType.getName();

									data.put("destroyOnHide", true);
									data.put("title", LanguageUtil.format(request, "select-x", type, false));
									data.put("type", type);
							%>

									<liferay-ui:icon
										cssClass="asset-selector"
										data="<%= data %>"
										id="<%= curGroupId + FriendlyURLNormalizerUtil.normalize(type) %>"
										message="<%= HtmlUtil.escape(type) %>"
										url="javascript:;"
									/>

						<%
								}
							}
						}
						%>

					</liferay-ui:icon-menu>
				</div>
			</div>

		<%
		}
		%>

	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<aui:script>
	function <portlet:namespace />moveSelectionDown(position) {
		<portlet:namespace />fm.<portlet:namespace />position.value = position;

		<portlet:actionURL name="/asset_list/move_asset_entry_selection" var="moveAssetEntrySelectionDownURL">
			<portlet:param name="moveDirection" value="<%= AssetListSelectionConstants.MOVE_DOWN %>" />
		</portlet:actionURL>

		submitForm(document.<portlet:namespace />fm, '<%= moveAssetEntrySelectionDownURL.toString() %>');
	}

	function <portlet:namespace />moveSelectionUp(position) {
		<portlet:namespace />fm.<portlet:namespace />position.value = position;

		<portlet:actionURL name="/asset_list/move_asset_entry_selection" var="moveAssetEntrySelectionUpURL">
			<portlet:param name="moveDirection" value="<%= AssetListSelectionConstants.MOVE_UP %>" />
		</portlet:actionURL>

		submitForm(document.<portlet:namespace />fm, '<%= moveAssetEntrySelectionUpURL.toString() %>');
	}

	function selectAsset(assetEntryId, assetClassName, assetType, assetEntryTitle, groupName) {
		<portlet:namespace />fm.<portlet:namespace />assetEntryId.value = assetEntryId;

		<portlet:actionURL name="/asset_list/add_asset_entry_selection" var="addAssetEntrySelectionURL" />

		submitForm(document.<portlet:namespace />fm, '<%= addAssetEntrySelectionURL.toString() %>');
	}
</aui:script>

<aui:script sandbox="<%= true %>">
	$('body').on(
		'click',
		'.asset-selector a',
		function(event) {
			event.preventDefault();

			var currentTarget = $(event.currentTarget);

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: '<%= eventName %>',
					id: '<%= eventName %>' + currentTarget.attr('id'),
					title: currentTarget.data('title'),
					uri: currentTarget.data('href')
				},
				function(event) {
					selectAsset(event.entityid, event.assetclassname, event.assettype, event.assettitle, event.groupdescriptivename);
				}
			);
		}
	);
</aui:script>