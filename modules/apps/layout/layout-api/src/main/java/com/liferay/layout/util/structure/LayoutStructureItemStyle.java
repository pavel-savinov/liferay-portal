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

package com.liferay.layout.util.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Savinov
 */
public class LayoutStructureItemStyle {

	public LayoutStructureItemStyle(
		String defaultValue, String label, String name, boolean responsive,
		String type) {

		_defaultValue = defaultValue;
		_label = label;
		_name = name;
		_responsive = responsive;
		_type = type;

		_layoutStructureItemStyleDependencies = new ArrayList<>();
		_validLayoutStructureItemStyleValues = new ArrayList<>();
	}

	public void addLayoutStructureItemStyleDependency(
		LayoutStructureItemStyleValue layoutStructureItemStyleValue) {

		_layoutStructureItemStyleDependencies.add(
			layoutStructureItemStyleValue);
	}

	public void addValidLayoutStructureItemStyleValue(
		LayoutStructureItemStyleValue layoutStructureItemStyleValue) {

		_validLayoutStructureItemStyleValues.add(layoutStructureItemStyleValue);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof LayoutStructureItemStyle)) {
			return false;
		}

		LayoutStructureItemStyle layoutStructureItemStyle =
			(LayoutStructureItemStyle)object;

		return _name.equals(layoutStructureItemStyle.getName());
	}

	public String getDefaultValue() {
		return _defaultValue;
	}

	public String getLabel() {
		return _label;
	}

	public List<LayoutStructureItemStyleValue>
		getLayoutStructureItemStyleDependencies() {

		return _layoutStructureItemStyleDependencies;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public List<LayoutStructureItemStyleValue>
		getValidLayoutStructureItemStyleValues() {

		return _validLayoutStructureItemStyleValues;
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	public boolean isResponsive() {
		return _responsive;
	}

	private final String _defaultValue;
	private final String _label;
	private final List<LayoutStructureItemStyleValue>
		_layoutStructureItemStyleDependencies;
	private final String _name;
	private final boolean _responsive;
	private final String _type;
	private final List<LayoutStructureItemStyleValue>
		_validLayoutStructureItemStyleValues;

}