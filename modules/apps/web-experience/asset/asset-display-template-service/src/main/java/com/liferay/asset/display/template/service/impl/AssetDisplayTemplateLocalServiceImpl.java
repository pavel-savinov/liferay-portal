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

package com.liferay.asset.display.template.service.impl;

import com.liferay.asset.display.template.model.AssetDisplayTemplate;
import com.liferay.asset.display.template.service.base.AssetDisplayTemplateLocalServiceBaseImpl;
import com.liferay.asset.display.template.util.comparator.AssetDisplayTemplateCreateDateComparator;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplateManager;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.layout.type.controller.asset.display.constants.AssetDisplayLayoutTypeControllerConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class AssetDisplayTemplateLocalServiceImpl
	extends AssetDisplayTemplateLocalServiceBaseImpl {

	@Override
	public AssetDisplayTemplate addAssetDisplayTemplate(
			long groupId, long userId, String name, long classNameId,
			String language, String scriptContent, boolean main,
			ServiceContext serviceContext)
		throws PortalException {

		// Asset display template

		User user = userLocalService.getUser(userId);

		long assetDisplayTemplateId = counterLocalService.increment();

		AssetDisplayTemplate assetDisplayTemplate =
			assetDisplayTemplatePersistence.create(assetDisplayTemplateId);

		assetDisplayTemplate.setGroupId(groupId);
		assetDisplayTemplate.setCompanyId(user.getCompanyId());
		assetDisplayTemplate.setUserId(userId);
		assetDisplayTemplate.setUserName(user.getFullName());
		assetDisplayTemplate.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		assetDisplayTemplate.setName(name);
		assetDisplayTemplate.setClassNameId(classNameId);

		Layout layout = layoutLocalService.addLayout(
			userId, groupId, false, 0, name, name, StringPool.BLANK,
			AssetDisplayLayoutTypeControllerConstants.LAYOUT_TYPE_ASSET_DISPLAY,
			true, null, serviceContext);

		assetDisplayTemplate.setPlid(layout.getPlid());

		long assetDisplayTemplateClassNameId = _portal.getClassNameId(
			AssetDisplayTemplate.class);
		long resourceClassNameId = _portal.getClassNameId(
			PortletDisplayTemplate.class);
		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getDefault(), name);

		DDMTemplate ddmTemplate = _ddmTemplateManager.addTemplate(
			userId, groupId, assetDisplayTemplateClassNameId,
			assetDisplayTemplate.getAssetDisplayTemplateId(),
			resourceClassNameId,
			String.valueOf(assetDisplayTemplate.getAssetDisplayTemplateId()),
			nameMap, Collections.emptyMap(),
			DDMTemplateManager.TEMPLATE_TYPE_DISPLAY,
			DDMTemplateManager.TEMPLATE_MODE_CREATE, language, scriptContent,
			true, false, null, null, serviceContext);

		assetDisplayTemplate.setDDMTemplateId(ddmTemplate.getTemplateId());

		assetDisplayTemplate.setMain(main);

		assetDisplayTemplatePersistence.update(assetDisplayTemplate);

		// Layout

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"assetDisplayTemplateId", String.valueOf(assetDisplayTemplateId));

		layoutLocalService.updateLayout(
			groupId, layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		// Resources

		resourceLocalService.addModelResources(
			assetDisplayTemplate, serviceContext);

		return assetDisplayTemplate;
	}

	@Override
	public AssetDisplayTemplate deleteAssetDisplayTemplate(
			AssetDisplayTemplate assetDisplayTemplate)
		throws PortalException {

		// Asset display template

		assetDisplayTemplatePersistence.remove(assetDisplayTemplate);

		// Dynamic data mapping template

		_ddmTemplateLocalService.deleteDDMTemplate(
			assetDisplayTemplate.getDDMTemplateId());

		// Layout

		layoutLocalService.deleteLayout(assetDisplayTemplate.getPlid());

		// Resources

		resourceLocalService.deleteResource(
			assetDisplayTemplate.getCompanyId(),
			AssetDisplayTemplate.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			assetDisplayTemplate.getAssetDisplayTemplateId());

		return assetDisplayTemplate;
	}

	@Override
	public AssetDisplayTemplate deleteAssetDisplayTemplate(
			long assetDisplayTemplateId)
		throws PortalException {

		AssetDisplayTemplate assetDisplayTemplate = getAssetDisplayTemplate(
			assetDisplayTemplateId);

		return deleteAssetDisplayTemplate(assetDisplayTemplate);
	}

	@Override
	public AssetDisplayTemplate fetchAssetDisplayTemplate(
		long groupId, long classNameId) {

		return assetDisplayTemplatePersistence.fetchByG_C_First(
			groupId, classNameId,
			new AssetDisplayTemplateCreateDateComparator());
	}

	@Override
	public int getAssetDisplayTemplatesCount(long groupId) {
		return assetDisplayTemplatePersistence.countByGroupId(groupId);
	}

	@Override
	public List<AssetDisplayTemplate> getAssetDisplayTemplates(long groupId) {
		return assetDisplayTemplatePersistence.findByGroupId(groupId);
	}

	@Override
	public List<AssetDisplayTemplate> getAssetDisplayTemplates(
		long groupId, int start, int end,
		OrderByComparator<AssetDisplayTemplate> orderByComparator) {

		return assetDisplayTemplatePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public AssetDisplayTemplate updateAssetDisplayTemplate(
			long assetDisplayTemplateId, String name, long classNameId,
			String language, String scriptContent, boolean main,
			ServiceContext serviceContext)
		throws PortalException {

		// Asset display template

		AssetDisplayTemplate assetDisplayTemplate = getAssetDisplayTemplate(
			assetDisplayTemplateId);

		assetDisplayTemplate.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		assetDisplayTemplate.setClassNameId(classNameId);
		assetDisplayTemplate.setName(name);
		assetDisplayTemplate.setMain(main);

		assetDisplayTemplatePersistence.update(assetDisplayTemplate);

		// Dynamic data mapping template

		DDMTemplate ddmTemplate = _ddmTemplateManager.getTemplate(
			assetDisplayTemplate.getDDMTemplateId());

		_ddmTemplateLocalService.updateTemplate(
			ddmTemplate.getUserId(), ddmTemplate.getTemplateId(),
			ddmTemplate.getClassPK(), ddmTemplate.getNameMap(),
			ddmTemplate.getDescriptionMap(), ddmTemplate.getType(),
			ddmTemplate.getMode(), language, scriptContent,
			ddmTemplate.isCacheable(), ddmTemplate.isSmallImage(),
			ddmTemplate.getSmallImageURL(), null, serviceContext);

		return assetDisplayTemplate;
	}

	@ServiceReference(type = DDMTemplateLocalService.class)
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@ServiceReference(type = DDMTemplateManager.class)
	private DDMTemplateManager _ddmTemplateManager;

	@ServiceReference(type = Portal.class)
	private Portal _portal;

}