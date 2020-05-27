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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {useSelector} from '../../store/index';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import {useUpdatedLayoutDataContext} from './RowWithControls';

const Column = React.forwardRef(
	({children, className, item, ...props}, ref) => {
		const layoutData = useSelector((state) => state.layoutData);
		const parentItem = layoutData.items[item.parentId];
		const selectedViewportSize = useSelector(
			(state) => state.selectedViewportSize
		);
		const updatedLayoutData = useUpdatedLayoutDataContext();

		const parentItemConfig = getResponsiveConfig(
			parentItem.config,
			selectedViewportSize
		);

		const {modulesPerRow, numberOfColumns} = parentItemConfig;

		let columnSize = (item.config.size * numberOfColumns) / modulesPerRow;

		if (numberOfColumns === 5 && modulesPerRow !== numberOfColumns) {
			columnSize = parentItem.children.indexOf(item.itemId) > 2 ? 6 : 4;
		}

		if (updatedLayoutData && updatedLayoutData.items[item.itemId]) {
			columnSize = updatedLayoutData.items[item.itemId].config.size;
		}

		return (
			<div
				{...props}
				className={classNames(className, 'col', {
					[`col-${columnSize}`]: columnSize,
					empty:
						modulesPerRow !== numberOfColumns &&
						!item.children.length,
				})}
				ref={ref}
			>
				<div className="page-editor__col__border">{children}</div>
			</div>
		);
	}
);

Column.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({size: PropTypes.number}),
	}).isRequired,
};

export default Column;
