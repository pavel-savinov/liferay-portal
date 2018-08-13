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

package com.liferay.asset.lists.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AssetList}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetList
 * @generated
 */
@ProviderType
public class AssetListWrapper implements AssetList, ModelWrapper<AssetList> {
	public AssetListWrapper(AssetList assetList) {
		_assetList = assetList;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetList.class;
	}

	@Override
	public String getModelClassName() {
		return AssetList.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("assetListId", getAssetListId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long assetListId = (Long)attributes.get("assetListId");

		if (assetListId != null) {
			setAssetListId(assetListId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new AssetListWrapper((AssetList)_assetList.clone());
	}

	@Override
	public int compareTo(AssetList assetList) {
		return _assetList.compareTo(assetList);
	}

	/**
	* Returns the asset list ID of this asset list.
	*
	* @return the asset list ID of this asset list
	*/
	@Override
	public long getAssetListId() {
		return _assetList.getAssetListId();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _assetList.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this asset list.
	*
	* @return the company ID of this asset list
	*/
	@Override
	public long getCompanyId() {
		return _assetList.getCompanyId();
	}

	/**
	* Returns the create date of this asset list.
	*
	* @return the create date of this asset list
	*/
	@Override
	public Date getCreateDate() {
		return _assetList.getCreateDate();
	}

	@Override
	public String getDefaultLanguageId() {
		return _assetList.getDefaultLanguageId();
	}

	/**
	* Returns the description of this asset list.
	*
	* @return the description of this asset list
	*/
	@Override
	public String getDescription() {
		return _assetList.getDescription();
	}

	/**
	* Returns the localized description of this asset list in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized description of this asset list
	*/
	@Override
	public String getDescription(java.util.Locale locale) {
		return _assetList.getDescription(locale);
	}

	/**
	* Returns the localized description of this asset list in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this asset list. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getDescription(java.util.Locale locale, boolean useDefault) {
		return _assetList.getDescription(locale, useDefault);
	}

	/**
	* Returns the localized description of this asset list in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized description of this asset list
	*/
	@Override
	public String getDescription(String languageId) {
		return _assetList.getDescription(languageId);
	}

	/**
	* Returns the localized description of this asset list in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized description of this asset list
	*/
	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _assetList.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _assetList.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _assetList.getDescriptionCurrentValue();
	}

	/**
	* Returns a map of the locales and localized descriptions of this asset list.
	*
	* @return the locales and localized descriptions of this asset list
	*/
	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return _assetList.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetList.getExpandoBridge();
	}

	/**
	* Returns the group ID of this asset list.
	*
	* @return the group ID of this asset list
	*/
	@Override
	public long getGroupId() {
		return _assetList.getGroupId();
	}

	/**
	* Returns the last publish date of this asset list.
	*
	* @return the last publish date of this asset list
	*/
	@Override
	public Date getLastPublishDate() {
		return _assetList.getLastPublishDate();
	}

	/**
	* Returns the modified date of this asset list.
	*
	* @return the modified date of this asset list
	*/
	@Override
	public Date getModifiedDate() {
		return _assetList.getModifiedDate();
	}

	/**
	* Returns the name of this asset list.
	*
	* @return the name of this asset list
	*/
	@Override
	public String getName() {
		return _assetList.getName();
	}

	/**
	* Returns the localized name of this asset list in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized name of this asset list
	*/
	@Override
	public String getName(java.util.Locale locale) {
		return _assetList.getName(locale);
	}

	/**
	* Returns the localized name of this asset list in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this asset list. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public String getName(java.util.Locale locale, boolean useDefault) {
		return _assetList.getName(locale, useDefault);
	}

	/**
	* Returns the localized name of this asset list in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized name of this asset list
	*/
	@Override
	public String getName(String languageId) {
		return _assetList.getName(languageId);
	}

	/**
	* Returns the localized name of this asset list in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized name of this asset list
	*/
	@Override
	public String getName(String languageId, boolean useDefault) {
		return _assetList.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _assetList.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _assetList.getNameCurrentValue();
	}

	/**
	* Returns a map of the locales and localized names of this asset list.
	*
	* @return the locales and localized names of this asset list
	*/
	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return _assetList.getNameMap();
	}

	/**
	* Returns the primary key of this asset list.
	*
	* @return the primary key of this asset list
	*/
	@Override
	public long getPrimaryKey() {
		return _assetList.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetList.getPrimaryKeyObj();
	}

	/**
	* Returns the type of this asset list.
	*
	* @return the type of this asset list
	*/
	@Override
	public int getType() {
		return _assetList.getType();
	}

	/**
	* Returns the user ID of this asset list.
	*
	* @return the user ID of this asset list
	*/
	@Override
	public long getUserId() {
		return _assetList.getUserId();
	}

	/**
	* Returns the user name of this asset list.
	*
	* @return the user name of this asset list
	*/
	@Override
	public String getUserName() {
		return _assetList.getUserName();
	}

	/**
	* Returns the user uuid of this asset list.
	*
	* @return the user uuid of this asset list
	*/
	@Override
	public String getUserUuid() {
		return _assetList.getUserUuid();
	}

	/**
	* Returns the uuid of this asset list.
	*
	* @return the uuid of this asset list
	*/
	@Override
	public String getUuid() {
		return _assetList.getUuid();
	}

	@Override
	public int hashCode() {
		return _assetList.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _assetList.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetList.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetList.isNew();
	}

	@Override
	public void persist() {
		_assetList.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_assetList.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_assetList.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the asset list ID of this asset list.
	*
	* @param assetListId the asset list ID of this asset list
	*/
	@Override
	public void setAssetListId(long assetListId) {
		_assetList.setAssetListId(assetListId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetList.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this asset list.
	*
	* @param companyId the company ID of this asset list
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetList.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this asset list.
	*
	* @param createDate the create date of this asset list
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_assetList.setCreateDate(createDate);
	}

	/**
	* Sets the description of this asset list.
	*
	* @param description the description of this asset list
	*/
	@Override
	public void setDescription(String description) {
		_assetList.setDescription(description);
	}

	/**
	* Sets the localized description of this asset list in the language.
	*
	* @param description the localized description of this asset list
	* @param locale the locale of the language
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale) {
		_assetList.setDescription(description, locale);
	}

	/**
	* Sets the localized description of this asset list in the language, and sets the default locale.
	*
	* @param description the localized description of this asset list
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescription(String description, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_assetList.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_assetList.setDescriptionCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized descriptions of this asset list from the map of locales and localized descriptions.
	*
	* @param descriptionMap the locales and localized descriptions of this asset list
	*/
	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		_assetList.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the localized descriptions of this asset list from the map of locales and localized descriptions, and sets the default locale.
	*
	* @param descriptionMap the locales and localized descriptions of this asset list
	* @param defaultLocale the default locale
	*/
	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, String> descriptionMap,
		java.util.Locale defaultLocale) {
		_assetList.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetList.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetList.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetList.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this asset list.
	*
	* @param groupId the group ID of this asset list
	*/
	@Override
	public void setGroupId(long groupId) {
		_assetList.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this asset list.
	*
	* @param lastPublishDate the last publish date of this asset list
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_assetList.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this asset list.
	*
	* @param modifiedDate the modified date of this asset list
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_assetList.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this asset list.
	*
	* @param name the name of this asset list
	*/
	@Override
	public void setName(String name) {
		_assetList.setName(name);
	}

	/**
	* Sets the localized name of this asset list in the language.
	*
	* @param name the localized name of this asset list
	* @param locale the locale of the language
	*/
	@Override
	public void setName(String name, java.util.Locale locale) {
		_assetList.setName(name, locale);
	}

	/**
	* Sets the localized name of this asset list in the language, and sets the default locale.
	*
	* @param name the localized name of this asset list
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setName(String name, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_assetList.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_assetList.setNameCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized names of this asset list from the map of locales and localized names.
	*
	* @param nameMap the locales and localized names of this asset list
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		_assetList.setNameMap(nameMap);
	}

	/**
	* Sets the localized names of this asset list from the map of locales and localized names, and sets the default locale.
	*
	* @param nameMap the locales and localized names of this asset list
	* @param defaultLocale the default locale
	*/
	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap,
		java.util.Locale defaultLocale) {
		_assetList.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_assetList.setNew(n);
	}

	/**
	* Sets the primary key of this asset list.
	*
	* @param primaryKey the primary key of this asset list
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetList.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetList.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type of this asset list.
	*
	* @param type the type of this asset list
	*/
	@Override
	public void setType(int type) {
		_assetList.setType(type);
	}

	/**
	* Sets the user ID of this asset list.
	*
	* @param userId the user ID of this asset list
	*/
	@Override
	public void setUserId(long userId) {
		_assetList.setUserId(userId);
	}

	/**
	* Sets the user name of this asset list.
	*
	* @param userName the user name of this asset list
	*/
	@Override
	public void setUserName(String userName) {
		_assetList.setUserName(userName);
	}

	/**
	* Sets the user uuid of this asset list.
	*
	* @param userUuid the user uuid of this asset list
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_assetList.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this asset list.
	*
	* @param uuid the uuid of this asset list
	*/
	@Override
	public void setUuid(String uuid) {
		_assetList.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetList> toCacheModel() {
		return _assetList.toCacheModel();
	}

	@Override
	public AssetList toEscapedModel() {
		return new AssetListWrapper(_assetList.toEscapedModel());
	}

	@Override
	public String toString() {
		return _assetList.toString();
	}

	@Override
	public AssetList toUnescapedModel() {
		return new AssetListWrapper(_assetList.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _assetList.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetListWrapper)) {
			return false;
		}

		AssetListWrapper assetListWrapper = (AssetListWrapper)obj;

		if (Objects.equals(_assetList, assetListWrapper._assetList)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _assetList.getStagedModelType();
	}

	@Override
	public AssetList getWrappedModel() {
		return _assetList;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetList.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetList.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetList.resetOriginalValues();
	}

	private final AssetList _assetList;
}