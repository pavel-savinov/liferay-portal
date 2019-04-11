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
ContentPageEditorDisplayContext contentPageEditorDisplayContext = (ContentPageEditorDisplayContext)request.getAttribute(ContentPageEditorWebKeys.LIFERAY_SHARED_CONTENT_PAGE_EDITOR_DISPLAY_CONTEXT);

String namespace = PortalUtil.getPortletNamespace(ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);
%>

<liferay-editor:resources
	editorName="alloyeditor"
/>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/layout-content-page-editor-web/css/main.css") %>" rel="stylesheet">
</liferay-util:html-top>

<soy:component-renderer
	componentId='<%= namespace + "fragmentsEditor" %>'
	context="<%= contentPageEditorDisplayContext.getEditorSoyContext() %>"
	module="js/FragmentsEditor.es"
	templateNamespace="com.liferay.layout.content.page.editor.web.FragmentsEditor.render"
/>

<%
StringBundler sb = new StringBundler(8);

sb.append(npmResolvedPackageName);
sb.append("/js/components/edit_mode/DisabledAreaMask.es as DisabledAreaMaskModule, ");
sb.append(npmResolvedPackageName);
sb.append("/js/components/edit_mode/EditModeWrapper.es as EditModeWrapperModule, ");
sb.append(npmResolvedPackageName);
sb.append("/js/reducers/reducers.es as ReducersModule, ");
sb.append(npmResolvedPackageName);
sb.append("/js/store/store.es as StoreModule");

JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
%>

<aui:script require="<%= sb.toString() %>">
	var store = StoreModule.createStore(
		<%= jsonSerializer.serializeDeep(contentPageEditorDisplayContext.getEditorSoyContext()) %>,
		ReducersModule.reducers,
		[
			'<%= namespace + "disabledAreaMaskWrapper" %>',
			'<%= namespace + "editModeWrapper" %>',
			'<%= namespace + "fragmentsEditor" %>',
			'<%= namespace + "sidebar" %>',
			'<%= namespace + "toolbar" %>'
		]
	);

	var editModeComponents = {
		'<%= namespace + "disabledAreaMaskWrapper" %>': DisabledAreaMaskModule.default,
		'<%= namespace + "editModeWrapper" %>': EditModeWrapperModule.default
	};

	Object.keys(editModeComponents).forEach(
		function(key) {
			Liferay.component(
				key,
				new editModeComponents[key](
					{
						store: store
					}
				)
			);
		}
	);

	function handleDestroyPortlet() {
		Object.keys(editModeComponents).forEach(
			function(key) {
				editModeComponents[key].dispose();

				Liferay.destroyComponent(key);
			}
		);

		Liferay.detach('destroyPortlet', handleDestroyPortlet);
	}

	Liferay.on('destroyPortlet', handleDestroyPortlet);
</aui:script>