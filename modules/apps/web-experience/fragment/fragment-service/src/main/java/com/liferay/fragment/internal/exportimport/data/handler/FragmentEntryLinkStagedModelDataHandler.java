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

package com.liferay.fragment.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class FragmentEntryLinkStagedModelDataHandler
	extends BaseStagedModelDataHandler<FragmentEntryLink> {

	public static final String[] CLASS_NAMES =
		{FragmentEntryLink.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			FragmentEntryLink fragmentEntryLink)
		throws Exception {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		if (fragmentEntry != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fragmentEntryLink, fragmentEntry,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		FragmentEntryLink originalFragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLink.getOriginalFragmentEntryLinkId());

		if (originalFragmentEntryLink != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fragmentEntryLink,
				originalFragmentEntryLink,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element fragmentEntryLinkElement =
			portletDataContext.getExportDataElement(fragmentEntryLink);

		portletDataContext.addClassedModel(
			fragmentEntryLinkElement,
			ExportImportPathUtil.getModelPath(fragmentEntryLink),
			fragmentEntryLink);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long fragmentEntryLinkId)
		throws Exception {

		FragmentEntryLink existingFragmentEntryLink = fetchMissingReference(
			uuid, groupId);

		if (existingFragmentEntryLink == null) {
			return;
		}

		Map<Long, Long> fragmentEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FragmentEntryLink.class);

		fragmentEntryIds.put(
			fragmentEntryLinkId,
			existingFragmentEntryLink.getFragmentEntryLinkId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			FragmentEntryLink fragmentEntryLink)
		throws Exception {

		Map<Long, Long> fragmentEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FragmentEntry.class);

		long fragmentEntryId = MapUtil.getLong(
			fragmentEntryIds, fragmentEntryLink.getFragmentEntryId(),
			fragmentEntryLink.getFragmentEntryId());

		Map<Long, Long> originalFragmentEntryLinkIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FragmentEntryLink.class);

		long originalFragmentEntryLinkId = MapUtil.getLong(
			originalFragmentEntryLinkIds,
			fragmentEntryLink.getOriginalFragmentEntryLinkId(),
			fragmentEntryLink.getOriginalFragmentEntryLinkId());

		FragmentEntryLink importedFragmentEntryLink =
			(FragmentEntryLink)fragmentEntryLink.clone();

		importedFragmentEntryLink.setGroupId(
			portletDataContext.getScopeGroupId());
		importedFragmentEntryLink.setFragmentEntryId(fragmentEntryId);
		importedFragmentEntryLink.setOriginalFragmentEntryLinkId(
			originalFragmentEntryLinkId);

		FragmentEntryLink existingFragmentEntryLink =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				fragmentEntryLink.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingFragmentEntryLink == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedFragmentEntryLink = _stagedModelRepository.addStagedModel(
				portletDataContext, importedFragmentEntryLink);
		}
		else {
			importedFragmentEntryLink.setFragmentEntryLinkId(
				existingFragmentEntryLink.getFragmentEntryLinkId());

			importedFragmentEntryLink =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedFragmentEntryLink);
		}

		portletDataContext.importClassedModel(
			fragmentEntryLink, importedFragmentEntryLink);
	}

	@Override
	protected StagedModelRepository<FragmentEntryLink>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.fragment.model.FragmentEntryLink)",
		unbind = "-"
	)
	private StagedModelRepository<FragmentEntryLink> _stagedModelRepository;

}