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

import com.liferay.headless.delivery.client.dto.v1_0.ViewportRowConfig;
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
public class ViewportRowConfigSerDes {

	public static ViewportRowConfig toDTO(String json) {
		ViewportRowConfigJSONParser viewportRowConfigJSONParser =
			new ViewportRowConfigJSONParser();

		return viewportRowConfigJSONParser.parseToDTO(json);
	}

	public static ViewportRowConfig[] toDTOs(String json) {
		ViewportRowConfigJSONParser viewportRowConfigJSONParser =
			new ViewportRowConfigJSONParser();

		return viewportRowConfigJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ViewportRowConfig viewportRowConfig) {
		if (viewportRowConfig == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (viewportRowConfig.getLandscapeMobile() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"landscapeMobile\": ");

			sb.append(String.valueOf(viewportRowConfig.getLandscapeMobile()));
		}

		if (viewportRowConfig.getPortraitMobile() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portraitMobile\": ");

			sb.append(String.valueOf(viewportRowConfig.getPortraitMobile()));
		}

		if (viewportRowConfig.getTablet() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tablet\": ");

			sb.append(String.valueOf(viewportRowConfig.getTablet()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ViewportRowConfigJSONParser viewportRowConfigJSONParser =
			new ViewportRowConfigJSONParser();

		return viewportRowConfigJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ViewportRowConfig viewportRowConfig) {

		if (viewportRowConfig == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (viewportRowConfig.getLandscapeMobile() == null) {
			map.put("landscapeMobile", null);
		}
		else {
			map.put(
				"landscapeMobile",
				String.valueOf(viewportRowConfig.getLandscapeMobile()));
		}

		if (viewportRowConfig.getPortraitMobile() == null) {
			map.put("portraitMobile", null);
		}
		else {
			map.put(
				"portraitMobile",
				String.valueOf(viewportRowConfig.getPortraitMobile()));
		}

		if (viewportRowConfig.getTablet() == null) {
			map.put("tablet", null);
		}
		else {
			map.put("tablet", String.valueOf(viewportRowConfig.getTablet()));
		}

		return map;
	}

	public static class ViewportRowConfigJSONParser
		extends BaseJSONParser<ViewportRowConfig> {

		@Override
		protected ViewportRowConfig createDTO() {
			return new ViewportRowConfig();
		}

		@Override
		protected ViewportRowConfig[] createDTOArray(int size) {
			return new ViewportRowConfig[size];
		}

		@Override
		protected void setField(
			ViewportRowConfig viewportRowConfig, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "landscapeMobile")) {
				if (jsonParserFieldValue != null) {
					viewportRowConfig.setLandscapeMobile(
						ViewportRowConfigDefinitionSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "portraitMobile")) {
				if (jsonParserFieldValue != null) {
					viewportRowConfig.setPortraitMobile(
						ViewportRowConfigDefinitionSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "tablet")) {
				if (jsonParserFieldValue != null) {
					viewportRowConfig.setTablet(
						ViewportRowConfigDefinitionSerDes.toDTO(
							(String)jsonParserFieldValue));
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