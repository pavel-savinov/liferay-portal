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

import com.liferay.headless.delivery.client.dto.v1_0.ViewportColumnConfig;
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
public class ViewportColumnConfigSerDes {

	public static ViewportColumnConfig toDTO(String json) {
		ViewportColumnConfigJSONParser viewportColumnConfigJSONParser =
			new ViewportColumnConfigJSONParser();

		return viewportColumnConfigJSONParser.parseToDTO(json);
	}

	public static ViewportColumnConfig[] toDTOs(String json) {
		ViewportColumnConfigJSONParser viewportColumnConfigJSONParser =
			new ViewportColumnConfigJSONParser();

		return viewportColumnConfigJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ViewportColumnConfig viewportColumnConfig) {
		if (viewportColumnConfig == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (viewportColumnConfig.getLandscapeMobile() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"landscapeMobile\": ");

			sb.append(
				String.valueOf(viewportColumnConfig.getLandscapeMobile()));
		}

		if (viewportColumnConfig.getPortraitMobile() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"portraitMobile\": ");

			sb.append(String.valueOf(viewportColumnConfig.getPortraitMobile()));
		}

		if (viewportColumnConfig.getTablet() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"tablet\": ");

			sb.append(String.valueOf(viewportColumnConfig.getTablet()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ViewportColumnConfigJSONParser viewportColumnConfigJSONParser =
			new ViewportColumnConfigJSONParser();

		return viewportColumnConfigJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ViewportColumnConfig viewportColumnConfig) {

		if (viewportColumnConfig == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (viewportColumnConfig.getLandscapeMobile() == null) {
			map.put("landscapeMobile", null);
		}
		else {
			map.put(
				"landscapeMobile",
				String.valueOf(viewportColumnConfig.getLandscapeMobile()));
		}

		if (viewportColumnConfig.getPortraitMobile() == null) {
			map.put("portraitMobile", null);
		}
		else {
			map.put(
				"portraitMobile",
				String.valueOf(viewportColumnConfig.getPortraitMobile()));
		}

		if (viewportColumnConfig.getTablet() == null) {
			map.put("tablet", null);
		}
		else {
			map.put("tablet", String.valueOf(viewportColumnConfig.getTablet()));
		}

		return map;
	}

	public static class ViewportColumnConfigJSONParser
		extends BaseJSONParser<ViewportColumnConfig> {

		@Override
		protected ViewportColumnConfig createDTO() {
			return new ViewportColumnConfig();
		}

		@Override
		protected ViewportColumnConfig[] createDTOArray(int size) {
			return new ViewportColumnConfig[size];
		}

		@Override
		protected void setField(
			ViewportColumnConfig viewportColumnConfig,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "landscapeMobile")) {
				if (jsonParserFieldValue != null) {
					viewportColumnConfig.setLandscapeMobile(
						ViewportColumnConfigDefinitionSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "portraitMobile")) {
				if (jsonParserFieldValue != null) {
					viewportColumnConfig.setPortraitMobile(
						ViewportColumnConfigDefinitionSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "tablet")) {
				if (jsonParserFieldValue != null) {
					viewportColumnConfig.setTablet(
						ViewportColumnConfigDefinitionSerDes.toDTO(
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