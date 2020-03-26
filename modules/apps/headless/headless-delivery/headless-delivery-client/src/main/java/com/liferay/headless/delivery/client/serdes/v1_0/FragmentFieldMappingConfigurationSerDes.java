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

package com.liferay.headless.delivery.client.serdes.v1_0;

import com.liferay.headless.delivery.client.dto.v1_0.FragmentFieldMappingConfiguration;
import com.liferay.headless.delivery.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class FragmentFieldMappingConfigurationSerDes {

	public static FragmentFieldMappingConfiguration toDTO(String json) {
		FragmentFieldMappingConfigurationJSONParser
			fragmentFieldMappingConfigurationJSONParser =
				new FragmentFieldMappingConfigurationJSONParser();

		return fragmentFieldMappingConfigurationJSONParser.parseToDTO(json);
	}

	public static FragmentFieldMappingConfiguration[] toDTOs(String json) {
		FragmentFieldMappingConfigurationJSONParser
			fragmentFieldMappingConfigurationJSONParser =
				new FragmentFieldMappingConfigurationJSONParser();

		return fragmentFieldMappingConfigurationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		FragmentFieldMappingConfiguration fragmentFieldMappingConfiguration) {

		if (fragmentFieldMappingConfiguration == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fragmentFieldMappingConfiguration.getMappedEntryField() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"mappedEntryField\": ");

			sb.append("\"");

			sb.append(
				_escape(
					fragmentFieldMappingConfiguration.getMappedEntryField()));

			sb.append("\"");
		}

		if (fragmentFieldMappingConfiguration.getMappedEntryKey() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"mappedEntryKey\": ");

			sb.append("\"");

			sb.append(
				_escape(fragmentFieldMappingConfiguration.getMappedEntryKey()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FragmentFieldMappingConfigurationJSONParser
			fragmentFieldMappingConfigurationJSONParser =
				new FragmentFieldMappingConfigurationJSONParser();

		return fragmentFieldMappingConfigurationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		FragmentFieldMappingConfiguration fragmentFieldMappingConfiguration) {

		if (fragmentFieldMappingConfiguration == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fragmentFieldMappingConfiguration.getMappedEntryField() == null) {
			map.put("mappedEntryField", null);
		}
		else {
			map.put(
				"mappedEntryField",
				String.valueOf(
					fragmentFieldMappingConfiguration.getMappedEntryField()));
		}

		if (fragmentFieldMappingConfiguration.getMappedEntryKey() == null) {
			map.put("mappedEntryKey", null);
		}
		else {
			map.put(
				"mappedEntryKey",
				String.valueOf(
					fragmentFieldMappingConfiguration.getMappedEntryKey()));
		}

		return map;
	}

	public static class FragmentFieldMappingConfigurationJSONParser
		extends BaseJSONParser<FragmentFieldMappingConfiguration> {

		@Override
		protected FragmentFieldMappingConfiguration createDTO() {
			return new FragmentFieldMappingConfiguration();
		}

		@Override
		protected FragmentFieldMappingConfiguration[] createDTOArray(int size) {
			return new FragmentFieldMappingConfiguration[size];
		}

		@Override
		protected void setField(
			FragmentFieldMappingConfiguration fragmentFieldMappingConfiguration,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "mappedEntryField")) {
				if (jsonParserFieldValue != null) {
					fragmentFieldMappingConfiguration.setMappedEntryField(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "mappedEntryKey")) {
				if (jsonParserFieldValue != null) {
					fragmentFieldMappingConfiguration.setMappedEntryKey(
						(String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\":");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(",");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}