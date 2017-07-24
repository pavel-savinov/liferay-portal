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

package com.liferay.friendly.url.service.impl;

import com.liferay.friendly.url.exception.DuplicateFriendlyURLEntryException;
import com.liferay.friendly.url.exception.FriendlyURLLengthException;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.model.FriendlyURLMapping;
import com.liferay.friendly.url.service.base.FriendlyURLEntryLocalServiceBaseImpl;
import com.liferay.friendly.url.service.persistence.FriendlyURLMappingPK;
import com.liferay.friendly.url.util.comparator.FriendlyURLEntryCreateDateComparator;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class FriendlyURLEntryLocalServiceImpl
	extends FriendlyURLEntryLocalServiceBaseImpl {

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long groupId, Class<?> clazz, long classPK, String urlTitle,
			ServiceContext serviceContext)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return addFriendlyURLEntry(
			groupId, classNameId, classPK, urlTitle, serviceContext);
	}

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long groupId, long classNameId, long classPK,
			Map<String, String> urlTitleMap, ServiceContext serviceContext)
		throws PortalException {

		validate(groupId, classNameId, classPK, urlTitleMap);

		FriendlyURLMappingPK friendlyURLMappingPK = new FriendlyURLMappingPK(
			classNameId, classPK);

		FriendlyURLMapping friendlyURLMapping =
			friendlyURLMappingPersistence.fetchByPrimaryKey(
				friendlyURLMappingPK);

		if (friendlyURLMapping == null) {
			friendlyURLMapping = friendlyURLMappingPersistence.create(
				friendlyURLMappingPK);
		}
		else {
			for (String urlTitle : urlTitleMap.values()) {
				String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
					urlTitle);

				FriendlyURLEntryLocalization oldFriendlyURLEntryLocalization =
					friendlyURLEntryLocalizationPersistence.fetchByG_C_U(
						groupId, classNameId, normalizedUrlTitle);

				FriendlyURLEntry oldFriendlyURLEntry = null;

				if (oldFriendlyURLEntryLocalization != null) {
					oldFriendlyURLEntry =
						friendlyURLEntryPersistence.fetchByPrimaryKey(
							oldFriendlyURLEntryLocalization.
								getFriendlyURLEntryId());
				}

				if (oldFriendlyURLEntry != null) {
					friendlyURLMapping.setFriendlyURLId(
						oldFriendlyURLEntry.getFriendlyURLEntryId());

					friendlyURLMappingPersistence.update(friendlyURLMapping);

					updateFriendlyURLEntryLocalizations(
						oldFriendlyURLEntry.getFriendlyURLEntryId(),
						oldFriendlyURLEntry.getCompanyId(),
						oldFriendlyURLEntry.getGroupId(),
						oldFriendlyURLEntry.getClassNameId(),
						oldFriendlyURLEntry.getClassPK(), urlTitleMap);

					return oldFriendlyURLEntry;
				}
			}
		}

		long friendlyURLEntryId = counterLocalService.increment();

		FriendlyURLEntry friendlyURLEntry = friendlyURLEntryPersistence.create(
			friendlyURLEntryId);

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		friendlyURLEntry.setUuid(serviceContext.getUuid());

		Group group = _groupLocalService.getGroup(groupId);

		friendlyURLEntry.setCompanyId(group.getCompanyId());

		friendlyURLEntry.setGroupId(groupId);
		friendlyURLEntry.setClassNameId(classNameId);
		friendlyURLEntry.setClassPK(classPK);
		friendlyURLEntry.setDefaultLanguageId(defaultLanguageId);

		friendlyURLMapping.setFriendlyURLId(friendlyURLEntryId);

		friendlyURLMappingPersistence.update(friendlyURLMapping);

		updateFriendlyURLEntryLocalizations(
			friendlyURLEntryId, group.getCompanyId(), groupId, classNameId,
			classPK, urlTitleMap);

		return friendlyURLEntryPersistence.update(friendlyURLEntry);
	}

	@Override
	public FriendlyURLEntry addFriendlyURLEntry(
			long groupId, long classNameId, long classPK, String urlTitle,
			ServiceContext serviceContext)
		throws PortalException {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		return addFriendlyURLEntry(
			groupId, classNameId, classPK,
			Collections.singletonMap(defaultLanguageId, urlTitle),
			serviceContext);
	}

	@Override
	public void deleteFriendlyURLEntry(
		long groupId, Class<?> clazz, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		List<FriendlyURLEntry> friendlyURLEntries =
			friendlyURLEntryPersistence.findByG_C_C(
				groupId, classNameId, classPK);

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			friendlyURLEntryLocalizationPersistence.removeByFriendlyURLEntryId(
				friendlyURLEntry.getFriendlyURLEntryId());

			friendlyURLEntryPersistence.remove(friendlyURLEntry);
		}
	}

	@Override
	public void deleteFriendlyURLEntry(
			long groupId, Class<?> clazz, long classPK, String urlTitle)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		deleteFriendlyURLEntry(groupId, classNameId, classPK, urlTitle);
	}

	@Override
	public void deleteFriendlyURLEntry(
			long groupId, long classNameId, long classPK, String urlTitle)
		throws PortalException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			friendlyURLEntryLocalizationPersistence.fetchByG_C_U(
				groupId, classNameId, urlTitle);

		if (friendlyURLEntryLocalization == null) {
			return;
		}

		friendlyURLEntryLocalizationPersistence.removeByFriendlyURLEntryId(
			friendlyURLEntryLocalization.getFriendlyURLEntryId());

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryPersistence.fetchByG_C_C_First(
				groupId, classNameId, classPK,
				new FriendlyURLEntryCreateDateComparator());

		FriendlyURLMappingPK friendlyURLMappingPK = new FriendlyURLMappingPK(
			classNameId, classPK);

		if (friendlyURLEntry == null) {
			friendlyURLMappingPersistence.remove(friendlyURLMappingPK);
		}
		else {
			FriendlyURLMapping friendlyURLMapping =
				friendlyURLMappingPersistence.findByPrimaryKey(
					friendlyURLMappingPK);

			friendlyURLMapping.setFriendlyURLId(
				friendlyURLEntry.getFriendlyURLEntryId());

			friendlyURLMappingPersistence.update(friendlyURLMapping);
		}
	}

	@Override
	public void deleteGroupFriendlyURLEntries(
		final long groupId, final long classNameId) {

		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName(
						"classNameId");

					dynamicQuery.add(property.eq(classNameId));
				}

			});
		actionableDynamicQuery.setGroupId(groupId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<FriendlyURLEntry>() {

				@Override
				public void performAction(FriendlyURLEntry friendlyURLEntry)
					throws PortalException {

					friendlyURLEntryLocalizationPersistence.
						removeByFriendlyURLEntryId(
							friendlyURLEntry.getFriendlyURLEntryId());

					friendlyURLEntryPersistence.remove(friendlyURLEntry);
				}

			});
	}

	@Override
	public FriendlyURLEntry fetchFriendlyURLEntry(
		long groupId, Class<?> clazz, String urlTitle) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return fetchFriendlyURLEntry(groupId, classNameId, urlTitle);
	}

	@Override
	public FriendlyURLEntry fetchFriendlyURLEntry(
		long groupId, long classNameId, String urlTitle) {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			friendlyURLEntryLocalizationPersistence.fetchByG_C_U(
				groupId, classNameId, urlTitle);

		if (friendlyURLEntryLocalization != null) {
			return friendlyURLEntryPersistence.fetchByPrimaryKey(
				friendlyURLEntryLocalization.getFriendlyURLEntryId());
		}

		return null;
	}

	@Override
	public List<FriendlyURLEntry> getFriendlyURLEntries(
		long groupId, long classNameId, long classPK) {

		return friendlyURLEntryPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public FriendlyURLEntry getMainFriendlyURLEntry(
			long groupId, Class<?> clazz, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return getMainFriendlyURLEntry(groupId, classNameId, classPK);
	}

	@Override
	public FriendlyURLEntry getMainFriendlyURLEntry(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		FriendlyURLMapping friendlyURLMapping =
			friendlyURLMappingPersistence.findByPrimaryKey(
				new FriendlyURLMappingPK(classNameId, classPK));

		return friendlyURLEntryPersistence.findByPrimaryKey(
			friendlyURLMapping.getFriendlyURLId());
	}

	@Override
	public String getUniqueUrlTitle(
		long groupId, long classNameId, long classPK, String urlTitle) {

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntryLocalization.class.getName(), "urlTitle");

		String curUrlTitle = normalizedUrlTitle.substring(
			0, Math.min(maxLength, normalizedUrlTitle.length()));

		for (int i = 1;; i++) {
			FriendlyURLEntryLocalization friendlyURLEntryLocalization =
				friendlyURLEntryLocalizationPersistence.fetchByG_C_U(
					groupId, classNameId, curUrlTitle);

			if ((friendlyURLEntryLocalization == null) ||
				(friendlyURLEntryLocalization.getClassPK() == classPK)) {

				break;
			}

			String suffix = StringPool.DASH + i;

			String prefix = normalizedUrlTitle.substring(
				0,
				Math.min(
					maxLength - suffix.length(), normalizedUrlTitle.length()));

			curUrlTitle = prefix + suffix;
		}

		return curUrlTitle;
	}

	@Override
	public void setMainFriendlyURLEntry(FriendlyURLEntry friendlyURLEntry) {
		FriendlyURLMappingPK friendlyURLMappingPK = new FriendlyURLMappingPK(
			friendlyURLEntry.getClassNameId(), friendlyURLEntry.getClassPK());

		FriendlyURLMapping friendlyURLMapping =
			friendlyURLMappingPersistence.fetchByPrimaryKey(
				friendlyURLMappingPK);

		if (friendlyURLMapping == null) {
			friendlyURLMapping = friendlyURLMappingPersistence.create(
				friendlyURLMappingPK);
		}

		friendlyURLMapping.setFriendlyURLId(
			friendlyURLEntry.getFriendlyURLEntryId());

		friendlyURLMappingPersistence.update(friendlyURLMapping);
	}

	@Override
	public FriendlyURLEntryLocalization updateFriendlyURLLocalization(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {

		return friendlyURLEntryLocalizationPersistence.update(
			friendlyURLEntryLocalization);
	}

	@Override
	public FriendlyURLEntryLocalization updateFriendlyURLLocalization(
			long friendlyURLLocalizationId, String urlTitle)
		throws PortalException {

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			friendlyURLEntryLocalizationPersistence.fetchByPrimaryKey(
				friendlyURLLocalizationId);

		if (friendlyURLEntryLocalization != null) {
			friendlyURLEntryLocalization.setUrlTitle(urlTitle);

			return friendlyURLEntryLocalizationPersistence.update(
				friendlyURLEntryLocalization);
		}

		return null;
	}

	@Override
	public void validate(
			long groupId, long classNameId, long classPK,
			Map<String, String> urlTitleMap)
		throws PortalException {

		for (String urlTitle : urlTitleMap.values()) {
			String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
				urlTitle);

			validate(groupId, classNameId, classPK, normalizedUrlTitle);
		}
	}

	@Override
	public void validate(
			long groupId, long classNameId, long classPK, String urlTitle)
		throws PortalException {

		int maxLength = ModelHintsUtil.getMaxLength(
			FriendlyURLEntryLocalization.class.getName(), "urlTitle");

		if (urlTitle.length() > maxLength) {
			throw new FriendlyURLLengthException(
				urlTitle + " is longer than " + maxLength);
		}

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			friendlyURLEntryLocalizationPersistence.fetchByG_C_U(
				groupId, classNameId, normalizedUrlTitle);

		if (friendlyURLEntryLocalization == null) {
			return;
		}

		if ((classPK <= 0) ||
			(friendlyURLEntryLocalization.getClassPK() != classPK)) {

			throw new DuplicateFriendlyURLEntryException(
				friendlyURLEntryLocalization.toString());
		}
	}

	@Override
	public void validate(long groupId, long classNameId, String urlTitle)
		throws PortalException {

		validate(groupId, classNameId, 0, urlTitle);
	}

	protected void updateFriendlyURLEntryLocalizations(
		long friendlyURLEntryId, long companyId, long groupId, long classNameId,
		long classPK, Map<String, String> urlTitleMap) {

		for (Map.Entry<String, String> entry : urlTitleMap.entrySet()) {
			String languageId = entry.getKey();
			String urlTitle = entry.getValue();

			String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
				urlTitle);

			if (Validator.isNull(normalizedUrlTitle)) {
				continue;
			}

			FriendlyURLEntryLocalization friendlyURLEntryLocalization =
				friendlyURLEntryLocalizationPersistence.
					fetchByFriendlyURLEntryId_LanguageId(
						friendlyURLEntryId, languageId);

			if (friendlyURLEntryLocalization != null) {
				normalizedUrlTitle = getUniqueUrlTitle(
					groupId, classNameId, classPK, urlTitle);

				friendlyURLEntryLocalization.setUrlTitle(normalizedUrlTitle);

				friendlyURLEntryLocalizationPersistence.updateImpl(
					friendlyURLEntryLocalization);

				continue;
			}

			long friendlyURLEntryLocalizationId =
				counterLocalService.increment();

			friendlyURLEntryLocalization =
				friendlyURLEntryLocalizationPersistence.create(
					friendlyURLEntryLocalizationId);

			friendlyURLEntryLocalization.setCompanyId(companyId);
			friendlyURLEntryLocalization.setFriendlyURLEntryId(
				friendlyURLEntryId);
			friendlyURLEntryLocalization.setGroupId(groupId);
			friendlyURLEntryLocalization.setClassNameId(classNameId);
			friendlyURLEntryLocalization.setClassPK(classPK);
			friendlyURLEntryLocalization.setUrlTitle(normalizedUrlTitle);
			friendlyURLEntryLocalization.setLanguageId(languageId);

			friendlyURLEntryLocalizationPersistence.update(
				friendlyURLEntryLocalization);
		}
	}

	@ServiceReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

}