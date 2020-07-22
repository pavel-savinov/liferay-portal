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

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {useSelector} from '../../store/index';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import {useCustomRowContext} from '../ResizeContext';

const Row = React.forwardRef(({children, className, item, layoutData}, ref) => {
	const customRow = useCustomRowContext();
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const itemStyles = getResponsiveConfig(
		item.config.styles,
		selectedViewportSize
	);
	const {modulesPerRow, reverseOrder} = itemStyles;

	const rowContent = (
		<ClayLayout.Row
			className={classNames(className, {
				empty:
					item.config.numberOfColumns === modulesPerRow &&
					!item.children.some(
						(childId) => layoutData.items[childId].children.length
					),
				'flex-column': customRow && modulesPerRow === 1,
				'flex-column-reverse':
					item.config.numberOfColumns === 2 &&
					modulesPerRow === 1 &&
					reverseOrder,

				'no-gutters': !item.config.gutters,
			})}
			ref={ref}
		>
			{children}
		</ClayLayout.Row>
	);

	const masterLayoutData = useSelector((state) => state.masterLayoutData);

	const masterParent = useMemo(() => {
		const dropZone =
			masterLayoutData &&
			masterLayoutData.items[masterLayoutData.rootItems.dropZone];

		return dropZone ? getItemParent(dropZone, masterLayoutData) : undefined;
	}, [masterLayoutData]);

	const shouldAddContainer = useSelector(
		(state) => !getItemParent(item, state.layoutData) && !masterParent
	);

	return shouldAddContainer ? (
		<ClayLayout.ContainerFluid className="p-0" size={false}>
			{rowContent}
		</ClayLayout.ContainerFluid>
	) : (
		rowContent
	);
});

Row.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			gutters: PropTypes.bool,
			styles: PropTypes.object,
		}),
	}).isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

function getItemParent(item, itemLayoutData) {
	const parent = itemLayoutData.items[item.parentId];

	return parent &&
		(parent.type === LAYOUT_DATA_ITEM_TYPES.root ||
			parent.type === LAYOUT_DATA_ITEM_TYPES.fragmentDropZone)
		? getItemParent(parent, itemLayoutData)
		: parent;
}

export default Row;
