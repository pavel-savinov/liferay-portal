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

import {usePrevious} from 'frontend-js-react-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import MillerColumnsColumn from './MillerColumnsColumn';

const getItemsMap = (columns, oldItems) => {
	const map = new Map();

	let parentId, parentKey;

	columns.forEach((column, columnIndex) => {
		let childrenCount = 0;
		let newParentId, newParentKey;

		column.forEach((item) => {
			childrenCount++;

			const oldItem = Array.from(oldItems.values()).find(
				(oldItem) => oldItem.id === item.id
			);

			map.set(item.key, {
				...item,
				checked: oldItem ? oldItem.checked : false,
				columnIndex,
				parentId,
				parentKey,
			});

			if (item.active && item.hasChild) {
				newParentId = item.id;
				newParentKey = item.key;
			}
		});

		if (parentKey) {
			map.set(parentKey, {
				...map.get(parentKey),
				childrenCount,
			});
		}

		parentId = newParentId;
		parentKey = newParentKey;
	});

	return map;
};

const noop = () => {};

const MillerColumns = ({
	actionHandlers,
	initialColumns = [],
	namespace,
	onColumnsChange = noop,
	onItemMove = noop,
	onItemStayHover,
	searchContainer,
}) => {
	const ref = useRef();

	const [items, setItems] = useState(() => getItemsMap(initialColumns, []));

	// Transform items map into a columns-like array.

	const columns = useMemo(() => {
		const columns = [];

		// eslint-disable-next-line no-for-of-loops/no-for-of-loops
		for (const item of items.values()) {
			if (!columns[item.columnIndex]) {
				columns[item.columnIndex] = {
					items: [],
					parent: items.get(item.parentId),
				};
			}

			const column = columns[item.columnIndex];

			column.items.push(item);
		}

		// Add empty column in the end if last column has an active item

		const lastColumnActiveItem = columns[columns.length - 1].items.find(
			(item) => item.active
		);
		if (lastColumnActiveItem && !lastColumnActiveItem.hasChild) {
			columns.push({
				items: [],
				parent: lastColumnActiveItem,
			});
		}

		return columns;
	}, [items]);

	const previousColumnsValue = usePrevious(columns);
	const previousInitialColumnsValue = usePrevious(initialColumns);

	useEffect(() => {
		if (previousInitialColumnsValue !== initialColumns) {
			setItems(getItemsMap(initialColumns, items));
		}
	}, [initialColumns, items, previousInitialColumnsValue]);

	useEffect(() => {
		if (previousColumnsValue !== columns) {
			onColumnsChange(columns);
		}
	}, [columns, onColumnsChange, previousColumnsValue]);

	useEffect(() => {
		if (ref.current) {
			ref.current.scrollLeft = ref.current.scrollWidth;
		}
	}, []);

	useEffect(() => {
		if (searchContainer) {
			searchContainer.on('rowToggled', (event) => {
				setItems((oldItems) => {
					const newItems = new Map(oldItems);

					newItems.forEach((item) => {
						const itemNode = event.elements.allElements._nodes.find(
							(node) => node.value === item.id
						);

						newItems.set(item.id, {
							...newItems.get(item.id),
							checked: itemNode?.checked,
						});
					});

					return newItems;
				});
			});
		}
	}, [searchContainer]);

	const getMovedItems = (movedItemId, newIndex) => {
		const movedItem = items.get(movedItemId);

		if (movedItem.checked) {
			let position = newIndex;

			return Array.from(items.values())
				.filter((item) => item.checked)
				.map((item) => ({
					plid: item.id,
					position: position++,
				}));
		}

		return [{plid: movedItemId, position: newIndex}];
	};

	const onItemDrop = (sourceId, newParentId, newIndex) => {
		const draggedItem = items.get(sourceId);

		const parent = Array.from(items.values()).find(
			(item) => item.id === newParentId
		);

		const sources = draggedItem.checked
			? Array.from(items.values()).filter((item) => item.checked)
			: [draggedItem];

		const newSources = sources.map((source) => ({
			...source,
			active: newParentId === source.parentId && source.active,
			checked: source.checked && parent.active,
			columnIndex: parent.columnIndex + 1,
			parentId: newParentId,
		}));

		// If no newIndex is provided set it as the last of the siblings.

		if (typeof newIndex !== 'number') {
			newIndex = parent.childrenCount || 0;
		}

		const newItems = new Map();
		let prevColumnIndex;
		let itemIndex = 0;

		// eslint-disable-next-line no-for-of-loops/no-for-of-loops
		for (let item of items.values()) {
			const columnIndex = item.columnIndex;

			if (columnIndex > prevColumnIndex) {

				// Exit if source was active but not anymore and we are on the
				// next column to where source used to live to avoid saving its
				// children (which must not be shown anymore)

				const activeSource = sources.find((source) => source.active);
				const newActiveSource =
					activeSource &&
					newSources.find(
						(newSource) => newSource.id === activeSource.id
					);

				if (
					!newActiveSource?.active &&
					columnIndex > activeSource?.columnIndex
				) {
					break;
				}

				// Reset itemIndex counter on each column

				itemIndex = 0;
			}

			// Skip the source item iteration

			if (sources.some((source) => item.id === source.id)) {
				itemIndex++;
				prevColumnIndex = columnIndex;
				continue;
			}

			if (item.id === newParentId) {
				let newChildrenCount = item.childrenCount;

				sources.forEach((source) => {
					if (newParentId !== source.parentId) {
						newChildrenCount++;
					}
				});

				item = {
					...item,
					childrenCount: newChildrenCount,
					hasChild: true,
				};
			}
			else if (sources.some((source) => item.id === source.parentId)) {
				let newChildrenCount = item.childrenCount;

				sources.forEach((source) => {
					if (item.id === source.parentId) {
						newChildrenCount--;
					}
				});

				item = {
					...item,
					childrenCount: newChildrenCount,
					hasChild: newChildrenCount > 0,
				};
			}

			if (
				itemIndex === newIndex &&
				columnIndex === draggedItem.columnIndex &&
				parent.active
			) {
				newSources.forEach((newSource) =>
					newItems.set(newSource.key, newSource)
				);
			}

			newItems.set(item.key, {...item});

			itemIndex++;
			prevColumnIndex = columnIndex;
		}

		// If source parent is active (children are visible) set (again or not)
		// the newSource in the map in case it's being placed as the last
		// element (so won't reach that position in the loop).

		if (parent.active) {
			newSources.forEach((newSource) =>
				newItems.set(newSource.key, newSource)
			);
		}

		setItems(newItems);

		onItemMove(getMovedItems(sourceId, newIndex), newParentId);
	};

	return (
		<DndProvider backend={HTML5Backend}>
			<div className="bg-white miller-columns-row" ref={ref}>
				{columns.map((column, index) => (
					<MillerColumnsColumn
						actionHandlers={actionHandlers}
						columnItems={column.items}
						items={items}
						key={index}
						namespace={namespace}
						onItemDrop={onItemDrop}
						onItemStayHover={onItemStayHover}
						parent={column.parent}
					/>
				))}
			</div>
		</DndProvider>
	);
};

export default MillerColumns;
