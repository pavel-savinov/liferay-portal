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

import com.liferay.layout.responsive.ViewportSize;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Víctor Galán
 */
public class LayoutStructure {

	public static LayoutStructure of(String layoutStructure) {
		if (Validator.isNull(layoutStructure)) {
			return new LayoutStructure();
		}

		try {
			JSONObject layoutStructureJSONObject =
				JSONFactoryUtil.createJSONObject(layoutStructure);

			JSONObject rootItemsJSONObject =
				layoutStructureJSONObject.getJSONObject("rootItems");

			JSONObject itemsJSONObject =
				layoutStructureJSONObject.getJSONObject("items");

			Map<Long, LayoutStructureItem> fragmentLayoutStructureItems =
				new HashMap<>(itemsJSONObject.length());

			Map<String, LayoutStructureItem> layoutStructureItems =
				new HashMap<>(itemsJSONObject.length());

			for (String key : itemsJSONObject.keySet()) {
				LayoutStructureItem layoutStructureItem =
					LayoutStructureItem.of(itemsJSONObject.getJSONObject(key));

				layoutStructureItems.put(key, layoutStructureItem);

				if (layoutStructureItem instanceof
						FragmentLayoutStructureItem) {

					FragmentLayoutStructureItem fragmentLayoutStructureItem =
						(FragmentLayoutStructureItem)layoutStructureItem;

					fragmentLayoutStructureItems.put(
						fragmentLayoutStructureItem.getFragmentEntryLinkId(),
						fragmentLayoutStructureItem);
				}
			}

			return new LayoutStructure(
				fragmentLayoutStructureItems, layoutStructureItems,
				rootItemsJSONObject.getString("main"));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON", jsonException);
			}
		}

		return new LayoutStructure();
	}

	public LayoutStructure() {
		_fragmentLayoutStructureItems = new HashMap<>();
		_layoutStructureItems = new HashMap<>();
		_mainItemId = StringPool.BLANK;
	}

	public LayoutStructureItem addCollectionItemLayoutStructureItem(
		String parentItemId, int position) {

		CollectionItemLayoutStructureItem collectionItemLayoutStructureItem =
			new CollectionItemLayoutStructureItem(parentItemId);

		_updateLayoutStructure(collectionItemLayoutStructureItem, position);

		return collectionItemLayoutStructureItem;
	}

	public LayoutStructureItem addCollectionLayoutStructureItem(
		String parentItemId, int position) {

		CollectionLayoutStructureItem collectionLayoutStructureItem =
			new CollectionLayoutStructureItem(parentItemId);

		_updateLayoutStructure(collectionLayoutStructureItem, position);

		addCollectionItemLayoutStructureItem(
			collectionLayoutStructureItem.getItemId(), 0);

		return collectionLayoutStructureItem;
	}

	public LayoutStructureItem addColumnLayoutStructureItem(
		String parentItemId, int position) {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			new ColumnLayoutStructureItem(parentItemId);

		columnLayoutStructureItem.setSize(_MAX_COLUMNS);

		_updateLayoutStructure(columnLayoutStructureItem, position);

		return columnLayoutStructureItem;
	}

	public LayoutStructureItem addContainerLayoutStructureItem(
		String parentItemId, int position) {

		ContainerLayoutStructureItem containerLayoutStructureItem =
			new ContainerLayoutStructureItem(parentItemId);

		_updateLayoutStructure(containerLayoutStructureItem, position);

		return containerLayoutStructureItem;
	}

	public LayoutStructureItem addDropZoneLayoutStructureItem(
		String parentItemId, int position) {

		DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
			new DropZoneLayoutStructureItem(parentItemId);

		_updateLayoutStructure(dropZoneLayoutStructureItem, position);

		return dropZoneLayoutStructureItem;
	}

	public LayoutStructureItem addFragmentDropZoneLayoutStructureItem(
		String parentItemId, int position) {

		FragmentDropZoneLayoutStructureItem
			fragmentDropZoneLayoutStructureItem =
				new FragmentDropZoneLayoutStructureItem(parentItemId);

		_updateLayoutStructure(fragmentDropZoneLayoutStructureItem, position);

		return fragmentDropZoneLayoutStructureItem;
	}

	public LayoutStructureItem addFragmentLayoutStructureItem(
		long fragmentEntryLinkId, String parentItemId, int position) {

		FragmentLayoutStructureItem fragmentLayoutStructureItem =
			new FragmentLayoutStructureItem(parentItemId);

		_updateLayoutStructure(fragmentLayoutStructureItem, position);

		fragmentLayoutStructureItem.setFragmentEntryLinkId(fragmentEntryLinkId);

		return fragmentLayoutStructureItem;
	}

	public LayoutStructureItem addLayoutStructureItem(
		LayoutStructureItem layoutStructureItem) {

		_layoutStructureItems.put(
			layoutStructureItem.getItemId(), layoutStructureItem);

		return layoutStructureItem;
	}

	public LayoutStructureItem addLayoutStructureItem(
		String itemType, String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem =
			LayoutStructureItemUtil.create(itemType, parentItemId);

		_updateLayoutStructure(layoutStructureItem, position);

		return layoutStructureItem;
	}

	public LayoutStructureItem addRootLayoutStructureItem() {
		RootLayoutStructureItem rootLayoutStructureItem =
			new RootLayoutStructureItem();

		_updateLayoutStructure(rootLayoutStructureItem, 0);

		if (Validator.isNull(_mainItemId)) {
			_mainItemId = rootLayoutStructureItem.getItemId();
		}

		return rootLayoutStructureItem;
	}

	public LayoutStructureItem addRowLayoutStructureItem(
		String parentItemId, int position, int numberOfColumns) {

		RowLayoutStructureItem rowLayoutStructureItem =
			new RowLayoutStructureItem(parentItemId);

		_updateLayoutStructure(rowLayoutStructureItem, position);

		rowLayoutStructureItem.setNumberOfColumns(numberOfColumns);

		return rowLayoutStructureItem;
	}

	public List<LayoutStructureItem> deleteLayoutStructureItem(String itemId) {
		List<LayoutStructureItem> deletedLayoutStructureItems =
			new ArrayList<>();

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		if (layoutStructureItem instanceof DropZoneLayoutStructureItem) {
			throw new UnsupportedOperationException(
				"Removing the drop zone of a layout structure is not allowed");
		}

		List<String> childrenItemIds = new ArrayList<>(
			layoutStructureItem.getChildrenItemIds());

		for (String childrenItemId : childrenItemIds) {
			deletedLayoutStructureItems.addAll(
				deleteLayoutStructureItem(childrenItemId));
		}

		deletedLayoutStructureItems.add(layoutStructureItem);

		if (Validator.isNotNull(layoutStructureItem.getParentItemId())) {
			LayoutStructureItem parentLayoutStructureItem =
				_layoutStructureItems.get(
					layoutStructureItem.getParentItemId());

			if (parentLayoutStructureItem != null) {
				parentLayoutStructureItem.deleteChildrenItem(itemId);
			}
		}

		_layoutStructureItems.remove(itemId);

		return deletedLayoutStructureItems;
	}

	public List<LayoutStructureItem> duplicateLayoutStructureItem(
		String itemId) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		List<String> childrenItemIds =
			parentLayoutStructureItem.getChildrenItemIds();

		int position = childrenItemIds.indexOf(itemId) + 1;

		return _duplicateLayoutStructureItem(
			itemId, layoutStructureItem.getParentItemId(), position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutStructure)) {
			return false;
		}

		LayoutStructure layoutStructure = (LayoutStructure)obj;

		if (Objects.equals(_mainItemId, layoutStructure._mainItemId) &&
			Objects.equals(
				_layoutStructureItems, layoutStructure._layoutStructureItems)) {

			return true;
		}

		return false;
	}

	public LayoutStructureItem getDropZoneLayoutStructureItem() {
		for (LayoutStructureItem layoutStructureItem :
				getLayoutStructureItems()) {

			if (layoutStructureItem instanceof DropZoneLayoutStructureItem) {
				return layoutStructureItem;
			}
		}

		return null;
	}

	public LayoutStructureItem getLayoutStructureItem(String itemId) {
		return _layoutStructureItems.get(itemId);
	}

	public LayoutStructureItem getLayoutStructureItemByFragmentEntryLinkId(
		long fragmentEntryLinkId) {

		return _fragmentLayoutStructureItems.get(fragmentEntryLinkId);
	}

	public List<LayoutStructureItem> getLayoutStructureItems() {
		return ListUtil.fromCollection(_layoutStructureItems.values());
	}

	public String getMainItemId() {
		return _mainItemId;
	}

	public LayoutStructureItem getMainLayoutStructureItem() {
		return _layoutStructureItems.get(_mainItemId);
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getMainItemId());
	}

	public LayoutStructureItem moveLayoutStructureItem(
		String itemId, String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem oldParentLayoutStructureItem =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		oldParentLayoutStructureItem.deleteChildrenItem(itemId);

		LayoutStructureItem newParentLayoutStructureItem =
			_layoutStructureItems.get(parentItemId);

		newParentLayoutStructureItem.addChildrenItem(position, itemId);

		layoutStructureItem.setParentItemId(parentItemId);

		return layoutStructureItem;
	}

	public void setMainItemId(String mainItemId) {
		_mainItemId = mainItemId;
	}

	public JSONObject toJSONObject() {
		String dropZoneItemId = StringPool.BLANK;
		JSONObject layoutStructureItemsJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, LayoutStructureItem> entry :
				_layoutStructureItems.entrySet()) {

			LayoutStructureItem layoutStructureItem = entry.getValue();

			if (layoutStructureItem instanceof DropZoneLayoutStructureItem) {
				dropZoneItemId = layoutStructureItem.getItemId();
			}

			layoutStructureItemsJSONObject.put(
				entry.getKey(), layoutStructureItem.toJSONObject());
		}

		return JSONUtil.put(
			"items", layoutStructureItemsJSONObject
		).put(
			"rootItems",
			JSONUtil.put(
				"dropZone", dropZoneItemId
			).put(
				"main", _mainItemId
			)
		).put(
			"version", 1
		);
	}

	@Override
	public String toString() {
		JSONObject jsonObject = toJSONObject();

		return jsonObject.toJSONString();
	}

	public LayoutStructureItem updateItemConfig(
		JSONObject itemConfigJSONObject, String itemId) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		layoutStructureItem.updateItemConfig(itemConfigJSONObject);

		return layoutStructureItem;
	}

	public List<LayoutStructureItem> updateRowColumnsLayoutStructureItem(
		String itemId, int numberOfColumns) {

		if (numberOfColumns > _MAX_COLUMNS) {
			return Collections.emptyList();
		}

		RowLayoutStructureItem rowLayoutStructureItem =
			(RowLayoutStructureItem)_layoutStructureItems.get(itemId);

		for (ViewportSize viewportSize : ViewportSize.values()) {
			_updateNumberOfColumns(
				rowLayoutStructureItem, viewportSize.getViewportSizeId(),
				numberOfColumns);
		}

		int oldNumberOfColumns = rowLayoutStructureItem.getNumberOfColumns();

		if (oldNumberOfColumns == numberOfColumns) {
			return Collections.emptyList();
		}

		rowLayoutStructureItem.setModulesPerRow(numberOfColumns);
		rowLayoutStructureItem.setNumberOfColumns(numberOfColumns);

		List<String> childrenItemIds = new ArrayList<>(
			rowLayoutStructureItem.getChildrenItemIds());

		if (oldNumberOfColumns < numberOfColumns) {
			for (int i = 0; i < oldNumberOfColumns; i++) {
				String childrenItemId = childrenItemIds.get(i);

				ColumnLayoutStructureItem columnLayoutStructureItem =
					(ColumnLayoutStructureItem)_layoutStructureItems.get(
						childrenItemId);

				columnLayoutStructureItem.setSize(
					_COLUMN_SIZES[numberOfColumns - 1][i]);
			}

			for (int i = oldNumberOfColumns; i < numberOfColumns; i++) {
				_addColumnLayoutStructureItem(
					itemId, i, _COLUMN_SIZES[numberOfColumns - 1][i]);
			}

			return Collections.emptyList();
		}

		for (int i = 0; i < numberOfColumns; i++) {
			String childrenItemId = childrenItemIds.get(i);

			ColumnLayoutStructureItem columnLayoutStructureItem =
				(ColumnLayoutStructureItem)_layoutStructureItems.get(
					childrenItemId);

			columnLayoutStructureItem.setSize(
				_COLUMN_SIZES[numberOfColumns - 1][i]);
		}

		List<LayoutStructureItem> deletedLayoutStructureItems =
			new ArrayList<>();

		for (int i = numberOfColumns; i < oldNumberOfColumns; i++) {
			String childrenItemId = childrenItemIds.get(i);

			deletedLayoutStructureItems.addAll(
				deleteLayoutStructureItem(childrenItemId));
		}

		return deletedLayoutStructureItems;
	}

	private LayoutStructure(
		Map<Long, LayoutStructureItem> fragmentLayoutStructureItems,
		Map<String, LayoutStructureItem> layoutStructureItems,
		String mainItemId) {

		_fragmentLayoutStructureItems = fragmentLayoutStructureItems;
		_layoutStructureItems = layoutStructureItems;
		_mainItemId = mainItemId;
	}

	private void _addColumnLayoutStructureItem(
		String parentItemId, int position, int size) {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			new ColumnLayoutStructureItem(parentItemId);

		columnLayoutStructureItem.setSize(size);

		_updateLayoutStructure(columnLayoutStructureItem, position);
	}

	private List<LayoutStructureItem> _duplicateLayoutStructureItem(
		String itemId, String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem newLayoutStructureItem =
			LayoutStructureItemUtil.create(
				layoutStructureItem.getItemType(), parentItemId);

		List<LayoutStructureItem> duplicatedLayoutStructureItems =
			new ArrayList<>();

		newLayoutStructureItem.setItemId(String.valueOf(UUID.randomUUID()));

		newLayoutStructureItem.updateItemConfig(
			layoutStructureItem.getItemConfigJSONObject());

		_updateLayoutStructure(newLayoutStructureItem, position);

		duplicatedLayoutStructureItems.add(newLayoutStructureItem);

		for (String childrenItemId : layoutStructureItem.getChildrenItemIds()) {
			duplicatedLayoutStructureItems.addAll(
				_duplicateLayoutStructureItem(
					childrenItemId, newLayoutStructureItem.getItemId(), -1));
		}

		return duplicatedLayoutStructureItems;
	}

	private void _updateLayoutStructure(
		LayoutStructureItem layoutStructureItem, int position) {

		_layoutStructureItems.put(
			layoutStructureItem.getItemId(), layoutStructureItem);

		if (Validator.isNull(layoutStructureItem.getParentItemId())) {
			return;
		}

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		if (position >= 0) {
			parentLayoutStructureItem.addChildrenItem(
				position, layoutStructureItem.getItemId());
		}
		else {
			parentLayoutStructureItem.addChildrenItem(
				layoutStructureItem.getItemId());
		}
	}

	private void _updateNumberOfColumns(
		RowLayoutStructureItem rowLayoutStructureItem, String viewportSizeId,
		int numberOfColumns) {

		Map<String, JSONObject> viewportSizeConfigurations =
			rowLayoutStructureItem.getViewportSizeConfigurations();

		JSONObject viewportSizeConfigurationJSONObject =
			viewportSizeConfigurations.getOrDefault(
				viewportSizeId, JSONFactoryUtil.createJSONObject());

		viewportSizeConfigurationJSONObject.put(
			"modulesPerRow", numberOfColumns
		).put(
			"numberOfColumns", numberOfColumns
		);
	}

	private static final int[][] _COLUMN_SIZES = {
		{12}, {6, 6}, {4, 4, 4}, {3, 3, 3, 3}, {2, 2, 4, 2, 2},
		{2, 2, 2, 2, 2, 2}, {1, 1, 1, 6, 1, 1, 1}, {1, 1, 1, 3, 3, 1, 1, 1},
		{1, 1, 1, 1, 4, 1, 1, 1, 1}, {1, 1, 1, 1, 2, 2, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};

	private static final int _MAX_COLUMNS = 12;

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStructure.class);

	private final Map<Long, LayoutStructureItem> _fragmentLayoutStructureItems;
	private final Map<String, LayoutStructureItem> _layoutStructureItems;
	private String _mainItemId;

}