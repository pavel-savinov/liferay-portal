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

package com.liferay.fragment.internal.exportimport.content.processor;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.Iterator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.fragment.model.FragmentEntryLink",
	service = {
		ExportImportContentProcessor.class,
		FragmentEntryLinkExportImportContentProcessor.class
	}
)
public class FragmentEntryLinkExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			content);

		Iterator<String> keysIterator = editableValuesJSONObject.keys();

		while (keysIterator.hasNext()) {
			String key = keysIterator.next();

			JSONObject editableProcessorJSONObject =
				editableValuesJSONObject.getJSONObject(key);

			if (editableProcessorJSONObject == null) {
				continue;
			}

			Iterator<String> editableKeysIterator =
				editableProcessorJSONObject.keys();

			while (editableKeysIterator.hasNext()) {
				String editableKey = editableKeysIterator.next();

				JSONObject editableJSONObject =
					editableProcessorJSONObject.getJSONObject(editableKey);

				long classNameId = editableJSONObject.getLong("classNameId");
				long classPK = editableJSONObject.getLong("classPK");

				if ((classNameId == 0) || (classPK == 0)) {
					continue;
				}

				AssetEntry assetEntry = _assetEntryLocalService.getEntry(
					_portal.getClassName(classNameId), classPK);

				AssetRenderer assetRenderer = assetEntry.getAssetRenderer();

				if (assetRenderer == null) {
					continue;
				}

				AssetRendererFactory assetRendererFactory =
					assetRenderer.getAssetRendererFactory();

				StagingGroupHelper stagingGroupHelper =
					StagingGroupHelperUtil.getStagingGroupHelper();

				if (!stagingGroupHelper.isStagedPortlet(
						portletDataContext.getScopeGroupId(),
						assetRendererFactory.getPortletId())) {

					continue;
				}

				editableJSONObject.put(
					"className", _portal.getClassName(classNameId));

				if (exportReferencedContent) {
					try {
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, stagedModel,
							(StagedModel)assetRenderer.getAssetObject(),
							PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
					}
					catch (Exception e) {
						if (_log.isDebugEnabled()) {
							StringBundler messageSB = new StringBundler(12);

							messageSB.append("Staged model with class name ");
							messageSB.append(stagedModel.getModelClassName());
							messageSB.append(" and primary key ");
							messageSB.append(stagedModel.getPrimaryKeyObj());
							messageSB.append(" references asset entry with ");
							messageSB.append("class primary key ");
							messageSB.append(classPK);
							messageSB.append(" and class name ");
							messageSB.append(_portal.getClassName(classNameId));
							messageSB.append(" that could not be exported ");
							messageSB.append("due to ");
							messageSB.append(e);

							String errorMessage = messageSB.toString();

							if (Validator.isNotNull(e.getMessage())) {
								errorMessage = StringBundler.concat(
									errorMessage, ": ", e.getMessage());
							}

							_log.debug(errorMessage, e);
						}
					}
				}
				else {
					Element entityElement =
						portletDataContext.getExportDataElement(stagedModel);

					portletDataContext.addReferenceElement(
						stagedModel, entityElement,
						(ClassedModel)assetRenderer.getAssetObject(),
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
				}
			}
		}

		return editableValuesJSONObject.toString();
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		return null;
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkExportImportContentProcessor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private Portal _portal;

}