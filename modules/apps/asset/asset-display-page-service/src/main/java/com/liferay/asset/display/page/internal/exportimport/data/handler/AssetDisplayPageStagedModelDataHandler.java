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

package com.liferay.asset.display.page.internal.exportimport.data.handler;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetDisplayPageStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetDisplayPageEntry> {

	public static final String[] CLASS_NAMES = {
		AssetDisplayPageEntry.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			AssetDisplayPageEntry assetDisplayPageEntry)
		throws Exception {

		Element assetDisplayPageElement =
			portletDataContext.getExportDataElement(assetDisplayPageEntry);

		portletDataContext.addClassedModel(
			assetDisplayPageElement,
			ExportImportPathUtil.getModelPath(assetDisplayPageEntry),
			assetDisplayPageEntry);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				assetDisplayPageEntry.getLayoutPageTemplateEntryId());

		if (layoutPageTemplateEntry != null) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, layoutPageTemplateEntry);
		}
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			AssetDisplayPageEntry assetDisplayPageEntry)
		throws Exception {

		AssetDisplayPageEntry importedAssetDisplayPageEntry =
			(AssetDisplayPageEntry)assetDisplayPageEntry.clone();

		importedAssetDisplayPageEntry.setGroupId(
			portletDataContext.getScopeGroupId());

		AssetDisplayPageEntry existingAssetDisplayPageEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				assetDisplayPageEntry.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingAssetDisplayPageEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedAssetDisplayPageEntry =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedAssetDisplayPageEntry);
		}
		else {
			importedAssetDisplayPageEntry.setAssetDisplayPageEntryId(
				existingAssetDisplayPageEntry.getAssetDisplayPageEntryId());
			importedAssetDisplayPageEntry.setClassPK(
				existingAssetDisplayPageEntry.getClassPK());
			importedAssetDisplayPageEntry.setLayoutPageTemplateEntryId(
				existingAssetDisplayPageEntry.getLayoutPageTemplateEntryId());

			importedAssetDisplayPageEntry =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedAssetDisplayPageEntry);
		}

		_importLayoutPageTemplateEnty(
			portletDataContext, existingAssetDisplayPageEntry,
			importedAssetDisplayPageEntry);

		portletDataContext.importClassedModel(
			assetDisplayPageEntry, importedAssetDisplayPageEntry);
	}

	@Override
	protected StagedModelRepository<AssetDisplayPageEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	private void _importLayoutPageTemplateEnty(
			PortletDataContext portletDataContext,
			AssetDisplayPageEntry existingAssetDisplayPageEntry,
			AssetDisplayPageEntry importedAssetDisplayPageEntry)
		throws PortalException {

		LayoutPageTemplateEntry existingLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				existingAssetDisplayPageEntry.getLayoutPageTemplateEntryId());

		if ((existingLayoutPageTemplateEntry == null) ||
			(existingLayoutPageTemplateEntry.getGroupId() !=
				existingAssetDisplayPageEntry.getGroupId())) {

			String path = ExportImportPathUtil.getModelPath(
				portletDataContext, LayoutPageTemplateEntry.class.getName(),
				Long.valueOf(
					existingAssetDisplayPageEntry.
						getLayoutPageTemplateEntryId()));

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				(LayoutPageTemplateEntry)portletDataContext.getZipEntryAsObject(
					path);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutPageTemplateEntry);

			Map<Long, Long> layoutPageTemplateEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					LayoutPageTemplateEntry.class);

			long layoutPageTemplateEntryId = MapUtil.getLong(
				layoutPageTemplateEntryIds,
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

			LayoutPageTemplateEntry importedLayoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);

			importedAssetDisplayPageEntry.setLayoutPageTemplateEntryId(
				layoutPageTemplateEntryId);

			importedAssetDisplayPageEntry.setPlid(
				importedLayoutPageTemplateEntry.getPlid());

			_stagedModelRepository.updateStagedModel(
				portletDataContext, importedAssetDisplayPageEntry);
		}
	}

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.display.page.model.AssetDisplayPageEntry)"
	)
	private StagedModelRepository<AssetDisplayPageEntry> _stagedModelRepository;

}