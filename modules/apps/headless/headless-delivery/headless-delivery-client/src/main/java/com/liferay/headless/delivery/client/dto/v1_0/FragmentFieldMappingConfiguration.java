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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.FragmentFieldMappingConfigurationSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentFieldMappingConfiguration implements Cloneable {

	public String getMappedEntryField() {
		return mappedEntryField;
	}

	public void setMappedEntryField(String mappedEntryField) {
		this.mappedEntryField = mappedEntryField;
	}

	public void setMappedEntryField(
		UnsafeSupplier<String, Exception> mappedEntryFieldUnsafeSupplier) {

		try {
			mappedEntryField = mappedEntryFieldUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String mappedEntryField;

	public String getMappedEntryKey() {
		return mappedEntryKey;
	}

	public void setMappedEntryKey(String mappedEntryKey) {
		this.mappedEntryKey = mappedEntryKey;
	}

	public void setMappedEntryKey(
		UnsafeSupplier<String, Exception> mappedEntryKeyUnsafeSupplier) {

		try {
			mappedEntryKey = mappedEntryKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String mappedEntryKey;

	@Override
	public FragmentFieldMappingConfiguration clone()
		throws CloneNotSupportedException {

		return (FragmentFieldMappingConfiguration)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FragmentFieldMappingConfiguration)) {
			return false;
		}

		FragmentFieldMappingConfiguration fragmentFieldMappingConfiguration =
			(FragmentFieldMappingConfiguration)object;

		return Objects.equals(
			toString(), fragmentFieldMappingConfiguration.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FragmentFieldMappingConfigurationSerDes.toJSON(this);
	}

}