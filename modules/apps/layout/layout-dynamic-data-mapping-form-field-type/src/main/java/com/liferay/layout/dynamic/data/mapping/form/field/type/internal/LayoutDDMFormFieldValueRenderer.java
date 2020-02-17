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

package com.liferay.layout.dynamic.data.mapping.form.field.type.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=link_to_layout",
	service = DDMFormFieldValueRenderer.class
)
public class LayoutDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(locale);

		if (Validator.isNull(valueString)) {
			return StringPool.BLANK;
		}

		try {
			JSONObject fieldValueJSONObject = _jsonFactory.createJSONObject(
				valueString);

			long groupId = fieldValueJSONObject.getLong("groupId");
			boolean privateLayout = fieldValueJSONObject.getBoolean(
				"privateLayout");
			long layoutId = fieldValueJSONObject.getLong("layoutId");

			Layout layout = _layoutService.fetchLayout(
				groupId, privateLayout, layoutId);

			if (layout == null) {
				return StringPool.BLANK;
			}

			return layout.getName(locale);
		}
		catch (Exception exception) {
			return LanguageUtil.format(
				locale, "is-temporarily-unavailable", "page");
		}
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutService _layoutService;

}