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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/add_structured_content"
	},
	service = MVCActionCommand.class
)
public class AddStructuredContentMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Creating article " +
					MapUtil.toString(actionRequest.getParameterMap()));
		}

		long ddmStructureId = ParamUtil.getLong(
			actionRequest, "ddmStructureId");

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			ddmStructureId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		String serializedDDMFormValues = GetterUtil.getString(
			serviceContext.getAttribute("ddmFormValues"));

		JSONObject serializedDDMFormValuesJSONObject =
			JSONFactoryUtil.createJSONObject(serializedDDMFormValues);

		JSONArray fieldValuesJSONArray =
			serializedDDMFormValuesJSONObject.getJSONArray("fieldValues");

		JSONArray updatedFieldValuesJSONArray =
			JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		for (int i = 0; i < fieldValuesJSONArray.length(); i++) {
			JSONObject fieldValueJSONObject =
				fieldValuesJSONArray.getJSONObject(i);

			String fieldName = fieldValueJSONObject.getString("name");
			String fieldValue = fieldValueJSONObject.getString("value");

			if (!fieldName.equals("-")) {
				String fieldType = ddmStructure.getFieldType(fieldName);

				if (fieldType.equals("ddm-image")) {

					// TODO Image is url to valid document library image
					// TODO Image is url to valid online image
					// TODO Image is url to invalid online image
					// TODO Image is base64

					FileEntry fileEntry = _addImage(
						fieldValue, serviceContext, themeDisplay);

					JSONObject imageFieldValueJSONObject = JSONUtil.put(
						"groupId", fileEntry.getGroupId()
					).put(
						"uuid", fileEntry.getUuid()
					);

					Locale locale = LocaleUtil.getDefault();

					//					JSONObject valueJSONObject = JSONUtil.put(
					//						locale.toLanguageTag(), imageFieldValueJSONObject.toString()
					//					);

					String valueJSONObject =
						imageFieldValueJSONObject.toString();

					JSONObject imageFieldJSONObject = JSONUtil.put(
						"name", fieldName
					).put(
						"value", valueJSONObject
					);

					fieldValueJSONObject = imageFieldJSONObject;
				}

				updatedFieldValuesJSONArray.put(fieldValueJSONObject);
			}
		}

		serializedDDMFormValuesJSONObject.put(
			"fieldValues", updatedFieldValuesJSONArray);

		serviceContext.setAttribute(
			"ddmFormValues", serializedDDMFormValuesJSONObject.toJSONString());

		Fields fields = _ddm.getFields(ddmStructureId, serviceContext);

		String content = _journalConverter.getContent(ddmStructure, fields);

		Locale articleDefaultLocale = LocaleUtil.fromLanguageId(
			LocalizationUtil.getDefaultLanguageId(content));

		long groupId = themeDisplay.getScopeGroupId();

		long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(
			articleDefaultLocale, ParamUtil.getString(actionRequest, "title"));

		JournalArticle journalArticle = _journalArticleService.addArticle(
			groupId, folderId, titleMap, null, content,
			ddmStructure.getStructureKey(), null, serviceContext);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"classNameId", _portal.getClassNameId(JournalArticle.class)
		).put(
			"classPK", journalArticle.getResourcePrimKey()
		).put(
			"title", journalArticle.getTitle(themeDisplay.getLocale())
		);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private FileEntry _addImage(
			String imageURLString, ServiceContext serviceContext,
			ThemeDisplay themeDisplay)
		throws IOException, PortalException {

		URL imageURL = new URL(imageURLString);

		return _dlAppLocalService.addFileEntry(
			themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			// TODO
			StringUtil.randomString(),
			// TODO
			ContentTypes.IMAGE_PNG,
			FileUtil.getBytes(imageURL.openStream()), serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddStructuredContentMVCActionCommand.class);

	@Reference
	private DDM _ddm;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private Portal _portal;

}