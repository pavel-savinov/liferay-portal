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
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;

import org.json.JSONException;
import org.json.JSONTokener;

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
			org.json.JSONObject jsonObject = new org.json.JSONObject(
				new JSONTokener(configurationJSONSchemaInputStream));

			Schema schema = SchemaLoader.load(jsonObject);

			org.json.JSONObject configurationJSONObject =
				new org.json.JSONObject(configuration);

			schema.validate(configurationJSONObject);

			org.json.JSONArray fieldSetsNativeJSONArray =
				configurationJSONObject.getJSONArray("fieldSets");

			JSONArray fieldSetsJSONArray = JSONFactoryUtil.createJSONArray(
				fieldSetsNativeJSONArray.toString());

			Iterator<JSONObject> iterator = fieldSetsJSONArray.iterator();

			Set<String> fieldNames = new HashSet<>();

			while (iterator.hasNext()) {
				JSONObject fieldSetJSONObject = iterator.next();

				JSONArray fieldsJSONArray = fieldSetJSONObject.getJSONArray(
					"fields");

				Iterator<JSONObject> fieldsIterator =
					fieldsJSONArray.iterator();

				while (fieldsIterator.hasNext()) {
					JSONObject fieldJSONObject = fieldsIterator.next();

					if (fieldNames.contains(
							fieldJSONObject.getString("name"))) {

						throw new FragmentEntryConfigurationException(
							"Field names must be unique");
					}

					fieldNames.add(fieldJSONObject.getString("name"));
				}
			}
		}
		catch (Exception e) {
			if (e instanceof JSONException) {
				JSONException jsonException = (JSONException)e;

				throw new FragmentEntryConfigurationException(
					jsonException.getMessage(), jsonException);
			}
			else if (e instanceof ValidationException) {
				ValidationException validationException =
					(ValidationException)e;

				String errorMessage = validationException.getErrorMessage();

				List<String> messages = validationException.getAllMessages();

				if (!messages.isEmpty()) {
					List<String> formattedMessages = new ArrayList<>();

					messages.forEach(
						message -> {
							if (message.startsWith("#: ")) {
								message = message.substring(3);
							}
							else if (message.startsWith("#")) {
								message = message.substring(1);
							}

							formattedMessages.add(message);
						});

					errorMessage = StringUtil.merge(
						formattedMessages, StringPool.NEW_LINE);
				}

				throw new FragmentEntryConfigurationException(errorMessage, e);
			}

			throw new FragmentEntryConfigurationException(e);
		}
	}

}