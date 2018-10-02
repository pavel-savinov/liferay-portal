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

package com.liferay.asset.list.service.impl;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.base.AssetListEntryAssetEntryRelLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Pavel savinov
 */
public class AssetListEntryAssetEntryRelLocalServiceImpl
	extends AssetListEntryAssetEntryRelLocalServiceBaseImpl {

	@Override
	public AssetListEntryAssetEntryRel addAssetListEntryAssetEntryRel(
		AssetListEntry assetListEntry, long assetEntryId,
		ServiceContext serviceContext) {

		long assetListEntryAssetEntryRelId = counterLocalService.increment();

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.create(
				assetListEntryAssetEntryRelId);

		assetListEntryAssetEntryRel.setUuid(serviceContext.getUuid());
		assetListEntryAssetEntryRel.setGroupId(assetListEntry.getGroupId());
		assetListEntryAssetEntryRel.setCompanyId(assetListEntry.getCompanyId());
		assetListEntryAssetEntryRel.setUserId(assetListEntry.getUserId());
		assetListEntryAssetEntryRel.setUserName(assetListEntry.getUserName());
		assetListEntryAssetEntryRel.setAssetListEntryId(
			assetListEntry.getAssetListEntryId());
		assetListEntryAssetEntryRel.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		assetListEntryAssetEntryRel.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		assetListEntryAssetEntryRel.setAssetEntryId(assetEntryId);
		assetListEntryAssetEntryRel.setPosition(
			getAssetListEntryAssetEntryRelsCount(
				assetListEntry.getAssetListEntryId()));

		return assetListEntryAssetEntryRelPersistence.update(
			assetListEntryAssetEntryRel);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public AssetListEntryAssetEntryRel deleteAssetListEntryAssetEntryRel(
			long assetListEntryId, int position)
		throws PortalException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.removeByA_P(
				assetListEntryId, position);

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			assetListEntryAssetEntryRelPersistence.findByA_GtP(
				assetListEntryId, position);

		for (AssetListEntryAssetEntryRel curAssetListEntryAssetEntryRel :
				assetListEntryAssetEntryRels) {

			curAssetListEntryAssetEntryRel.setPosition(
				curAssetListEntryAssetEntryRel.getPosition() - 1);

			assetListEntryAssetEntryRelPersistence.update(
				curAssetListEntryAssetEntryRel);
		}

		return assetListEntryAssetEntryRel;
	}

	@Override
	public List<AssetListEntryAssetEntryRel> getAssetListEntryAssetEntryRels(
		long assetListEntryId, int start, int end) {

		return assetListEntryAssetEntryRelPersistence.findByAssetListEntryId(
			assetListEntryId, start, end);
	}

	@Override
	public int getAssetListEntryAssetEntryRelsCount(long assetListEntryId) {
		return assetListEntryAssetEntryRelPersistence.countByAssetListEntryId(
			assetListEntryId);
	}

	@Override
	public AssetListEntryAssetEntryRel moveAssetListEntryAssetEntryRel(
			long assetListEntryId, int position, int newPosition)
		throws PortalException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.findByA_P(
				assetListEntryId, position);

		int count =
			assetListEntryAssetEntryRelPersistence.countByAssetListEntryId(
				assetListEntryId);

		if ((newPosition < 0) || (newPosition >= count)) {
			return assetListEntryAssetEntryRel;
		}

		AssetListEntryAssetEntryRel swapAssetListEntryAssetEntryRel =
			assetListEntryAssetEntryRelPersistence.fetchByA_P(
				assetListEntryId, newPosition);

		if (swapAssetListEntryAssetEntryRel == null) {
			assetListEntryAssetEntryRel.setPosition(newPosition);

			return assetListEntryAssetEntryRelPersistence.update(
				assetListEntryAssetEntryRel);
		}

		assetListEntryAssetEntryRel.setPosition(-1);

		assetListEntryAssetEntryRelPersistence.update(
			assetListEntryAssetEntryRel);

		swapAssetListEntryAssetEntryRel.setPosition(-2);

		assetListEntryAssetEntryRelPersistence.update(
			swapAssetListEntryAssetEntryRel);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				assetListEntryAssetEntryRel.setPosition(newPosition);

				assetListEntryAssetEntryRelLocalService.
					updateAssetListEntryAssetEntryRel(
						assetListEntryAssetEntryRel);

				swapAssetListEntryAssetEntryRel.setPosition(position);

				assetListEntryAssetEntryRelLocalService.
					updateAssetListEntryAssetEntryRel(
						swapAssetListEntryAssetEntryRel);

				return null;
			});

		return assetListEntryAssetEntryRel;
	}

}