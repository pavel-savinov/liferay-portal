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

package com.liferay.layout.page.template.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.layout.page.template.service.http.LayoutPageTemplateVersionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutPageTemplateVersionSoap implements Serializable {

	public static LayoutPageTemplateVersionSoap toSoapModel(
		LayoutPageTemplateVersion model) {

		LayoutPageTemplateVersionSoap soapModel =
			new LayoutPageTemplateVersionSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setLayoutPageTemplateVersionId(
			model.getLayoutPageTemplateVersionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setLayoutPageTemplateEntryId(
			model.getLayoutPageTemplateEntryId());
		soapModel.setPlid(model.getPlid());
		soapModel.setVersion(model.getVersion());

		return soapModel;
	}

	public static LayoutPageTemplateVersionSoap[] toSoapModels(
		LayoutPageTemplateVersion[] models) {

		LayoutPageTemplateVersionSoap[] soapModels =
			new LayoutPageTemplateVersionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateVersionSoap[][] toSoapModels(
		LayoutPageTemplateVersion[][] models) {

		LayoutPageTemplateVersionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new LayoutPageTemplateVersionSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutPageTemplateVersionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutPageTemplateVersionSoap[] toSoapModels(
		List<LayoutPageTemplateVersion> models) {

		List<LayoutPageTemplateVersionSoap> soapModels =
			new ArrayList<LayoutPageTemplateVersionSoap>(models.size());

		for (LayoutPageTemplateVersion model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new LayoutPageTemplateVersionSoap[soapModels.size()]);
	}

	public LayoutPageTemplateVersionSoap() {
	}

	public long getPrimaryKey() {
		return _layoutPageTemplateVersionId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutPageTemplateVersionId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLayoutPageTemplateVersionId() {
		return _layoutPageTemplateVersionId;
	}

	public void setLayoutPageTemplateVersionId(
		long layoutPageTemplateVersionId) {

		_layoutPageTemplateVersionId = layoutPageTemplateVersionId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getLayoutPageTemplateEntryId() {
		return _layoutPageTemplateEntryId;
	}

	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		_layoutPageTemplateEntryId = layoutPageTemplateEntryId;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		_version = version;
	}

	private String _uuid;
	private long _layoutPageTemplateVersionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _layoutPageTemplateEntryId;
	private long _plid;
	private double _version;

}