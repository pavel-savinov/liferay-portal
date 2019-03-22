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

package com.liferay.layout.page.template.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.model.LayoutPageTemplateVersion;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutPageTemplateVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutPageTemplateVersionCacheModel
	implements CacheModel<LayoutPageTemplateVersion>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateVersionCacheModel)) {
			return false;
		}

		LayoutPageTemplateVersionCacheModel
			layoutPageTemplateVersionCacheModel =
				(LayoutPageTemplateVersionCacheModel)obj;

		if (layoutPageTemplateVersionId ==
				layoutPageTemplateVersionCacheModel.
					layoutPageTemplateVersionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, layoutPageTemplateVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", layoutPageTemplateVersionId=");
		sb.append(layoutPageTemplateVersionId);
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
		sb.append(", layoutPageTemplateEntryId=");
		sb.append(layoutPageTemplateEntryId);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", version=");
		sb.append(version);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutPageTemplateVersion toEntityModel() {
		LayoutPageTemplateVersionImpl layoutPageTemplateVersionImpl =
			new LayoutPageTemplateVersionImpl();

		if (uuid == null) {
			layoutPageTemplateVersionImpl.setUuid("");
		}
		else {
			layoutPageTemplateVersionImpl.setUuid(uuid);
		}

		layoutPageTemplateVersionImpl.setLayoutPageTemplateVersionId(
			layoutPageTemplateVersionId);
		layoutPageTemplateVersionImpl.setGroupId(groupId);
		layoutPageTemplateVersionImpl.setCompanyId(companyId);
		layoutPageTemplateVersionImpl.setUserId(userId);

		if (userName == null) {
			layoutPageTemplateVersionImpl.setUserName("");
		}
		else {
			layoutPageTemplateVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutPageTemplateVersionImpl.setCreateDate(null);
		}
		else {
			layoutPageTemplateVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutPageTemplateVersionImpl.setModifiedDate(null);
		}
		else {
			layoutPageTemplateVersionImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		layoutPageTemplateVersionImpl.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
		layoutPageTemplateVersionImpl.setPlid(plid);
		layoutPageTemplateVersionImpl.setVersion(version);

		layoutPageTemplateVersionImpl.resetOriginalValues();

		return layoutPageTemplateVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		layoutPageTemplateVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		layoutPageTemplateEntryId = objectInput.readLong();

		plid = objectInput.readLong();

		version = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(layoutPageTemplateVersionId);

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

		objectOutput.writeLong(layoutPageTemplateEntryId);

		objectOutput.writeLong(plid);

		objectOutput.writeDouble(version);
	}

	public String uuid;
	public long layoutPageTemplateVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long layoutPageTemplateEntryId;
	public long plid;
	public double version;

}