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

package com.liferay.asset.lists.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.lists.model.AssetList;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetList in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetList
 * @generated
 */
@ProviderType
public class AssetListCacheModel implements CacheModel<AssetList>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListCacheModel)) {
			return false;
		}

		AssetListCacheModel assetListCacheModel = (AssetListCacheModel)obj;

		if (assetListId == assetListCacheModel.assetListId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, assetListId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", assetListId=");
		sb.append(assetListId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetList toEntityModel() {
		AssetListImpl assetListImpl = new AssetListImpl();

		if (uuid == null) {
			assetListImpl.setUuid("");
		}
		else {
			assetListImpl.setUuid(uuid);
		}

		assetListImpl.setAssetListId(assetListId);
		assetListImpl.setGroupId(groupId);
		assetListImpl.setCompanyId(companyId);
		assetListImpl.setUserId(userId);

		if (userName == null) {
			assetListImpl.setUserName("");
		}
		else {
			assetListImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			assetListImpl.setCreateDate(null);
		}
		else {
			assetListImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			assetListImpl.setModifiedDate(null);
		}
		else {
			assetListImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			assetListImpl.setName("");
		}
		else {
			assetListImpl.setName(name);
		}

		if (description == null) {
			assetListImpl.setDescription("");
		}
		else {
			assetListImpl.setDescription(description);
		}

		assetListImpl.setType(type);

		if (lastPublishDate == Long.MIN_VALUE) {
			assetListImpl.setLastPublishDate(null);
		}
		else {
			assetListImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		assetListImpl.resetOriginalValues();

		return assetListImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		assetListId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		type = objectInput.readInt();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(assetListId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(type);
		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long assetListId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public int type;
	public long lastPublishDate;
}