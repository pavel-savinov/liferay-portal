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

package com.liferay.fragment.entry.processor.util;

import java.util.Objects;

import org.jsoup.nodes.Element;

/**
 * @author Jürgen Kappler
 */
public class EditableFragmentEntryProcessorUtil {

	public static String getElementId(Element element) {
		if (Objects.equals(element.tagName(), "lfr-editable")) {
			return element.attr("id");
		}

		return element.attr("data-lfr-editable-id");
	}

	public static String getElementType(Element element) {
		if (Objects.equals(element.tagName(), "lfr-editable")) {
			return element.attr("type");
		}

		return element.attr("data-lfr-editable-type");
	}

}