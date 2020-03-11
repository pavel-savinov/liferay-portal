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

package com.liferay.fragment.internal.validator;

import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.petra.json.validator.JSONValidator;
import com.liferay.petra.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = FragmentEntryValidator.class)
public class FragmentEntryValidatorImpl implements FragmentEntryValidator {

	@Override
	public void validateConfiguration(String configuration)
		throws FragmentEntryConfigurationException {

		if (Validator.isNull(configuration)) {
			return;
		}

		InputStream configurationJSONSchemaInputStream =
			FragmentEntryValidatorImpl.class.getResourceAsStream(
				"dependencies/configuration-json-schema.json");

		try {
			JSONValidator.validate(
				configuration, configurationJSONSchemaInputStream);

			JSONObject configurationJSONObject =
				JSONFactoryUtil.createJSONObject(configuration);

			JSONArray fieldSetsJSONArray = configurationJSONObject.getJSONArray(
				"fieldSets");

			Set<String> fieldNames = new HashSet<>();

			for (int fieldSetIndex = 0;
				 fieldSetIndex < fieldSetsJSONArray.length(); fieldSetIndex++) {

				JSONObject fieldSetJSONObject =
					fieldSetsJSONArray.getJSONObject(fieldSetIndex);

				JSONArray fieldsJSONArray = fieldSetJSONObject.getJSONArray(
					"fields");

				for (int fieldIndex = 0; fieldIndex < fieldsJSONArray.length();
					 fieldIndex++) {

					JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(
						fieldIndex);

					if (fieldNames.contains(
							fieldJSONObject.getString("name"))) {

						throw new FragmentEntryConfigurationException(
							"Field names must be unique");
					}

					JSONObject typeOptionsJSONObject =
						fieldJSONObject.getJSONObject("typeOptions");

					if ((typeOptionsJSONObject != null) &&
						typeOptionsJSONObject.has("validation")) {

						String defaultValue = fieldJSONObject.getString(
							"defaultValue");

						_checkValidationRules(
							defaultValue,
							typeOptionsJSONObject.getJSONObject("validation"));
					}

					fieldNames.add(fieldJSONObject.getString("name"));
				}
			}
		}
		catch (JSONException jsonException) {
			throw new FragmentEntryConfigurationException(
				jsonException.getMessage(), jsonException);
		}
		catch (JSONValidatorException jsonValidatorException) {
			throw new FragmentEntryConfigurationException(
				jsonValidatorException.getMessage(), jsonValidatorException);
		}
	}

	private boolean _checkValidationRules(
		String defaultValue, JSONObject validationJSONObject) {

		if (Validator.isNull(defaultValue) || (validationJSONObject == null)) {
			return true;
		}

		String type = validationJSONObject.getString("type");

		if (Objects.equals(type, "email")) {
			return Validator.isEmailAddress(defaultValue);
		}
		else if (Objects.equals(type, "number")) {
			long max = validationJSONObject.getLong("max", Long.MAX_VALUE);
			long min = validationJSONObject.getLong("min", Long.MIN_VALUE);

			boolean valid = false;

			if (Validator.isNumber(defaultValue) &&
				(GetterUtil.getLong(defaultValue) <= max) &&
				(GetterUtil.getLong(defaultValue) >= min)) {

				valid = true;
			}

			return valid;
		}
		else if (Objects.equals(type, "pattern")) {
			String regexp = validationJSONObject.getString("regexp");

			return defaultValue.matches(regexp);
		}
		else if (Objects.equals(type, "url")) {
			return Validator.isUrl(defaultValue);
		}

		long maxLength = validationJSONObject.getLong(
			"maxLength", Long.MAX_VALUE);
		long minLength = validationJSONObject.getLong(
			"minLength", Long.MIN_VALUE);

		if ((defaultValue.length() <= maxLength) &&
			(defaultValue.length() >= minLength)) {

			return true;
		}

		return false;
	}

}