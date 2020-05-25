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

import {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {UPDATE_COL_SIZE_START} from '../../actions/types';
import updateColSize from '../../actions/updateColSize';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import {useDispatch, useSelector} from '../../store/index';
import duplicateItem from '../../thunks/duplicateItem';
import resizeColumns from '../../thunks/resizeColumns';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import Topper from '../Topper';
import FloatingToolbar from '../floating-toolbar/FloatingToolbar';
import SaveFragmentCompositionModal from '../floating-toolbar/SaveFragmentCompositionModal';
import ColumnOverlayGrid from './ColumnOverlayGrid';
import Row from './Row';
import hasDropZoneChild from './hasDropZoneChild';

export const ResizingContext = React.createContext();

const RowWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const {config} = layoutData.items[item.itemId];
		const dispatch = useDispatch();
		const {gutters} = item.config;
		const isMounted = useIsMounted();

		const [
			openSaveFragmentCompositionModal,
			setOpenSaveFragmentCompositionModal,
		] = useState(false);

		const {observer, onClose} = useModal({
			onClose: () => {
				if (isMounted()) {
					setOpenSaveFragmentCompositionModal(false);
				}
			},
		});

		const segmentsExperienceId = useSelector(
			(state) => state.segmentsExperienceId
		);
		const selectedViewportSize = useSelector(
			(state) => state.selectedViewportSize
		);

		const rowConfig = getResponsiveConfig(config, selectedViewportSize);

		const [setRef, itemElement] = useSetRef(ref);

		const rowRect = getRect(itemElement);
		const [highlightedColumn, setHighLightedColumn] = useState(null);
		const [resizeFinished, setResizeFinished] = useState(false);
		const [showOverlay, setShowOverlay] = useState(false);

		const handleButtonClick = useCallback(
			(id) => {
				if (
					id === LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem.id
				) {
					dispatch(
						duplicateItem({
							itemId: item.itemId,
							segmentsExperienceId,
						})
					);
				}
				else if (
					id ===
					LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.saveFragmentComposition
						.id
				) {
					setOpenSaveFragmentCompositionModal(true);
				}
			},
			[dispatch, item.itemId, segmentsExperienceId]
		);

		const getHighlightedColumnIndex = (clientX) => {
			const gridSizes = getGridSizes(rowRect.width);
			const mousePosition = clientX - rowRect.left;

			return getClosestGridIndexPosition(mousePosition, gridSizes);
		};

		const onResizeStart = ({clientX}) => {
			dispatch({itemId: item.itemId, type: UPDATE_COL_SIZE_START});
			setHighLightedColumn(getHighlightedColumnIndex(clientX));
			setShowOverlay(true);
		};

		const onResizing = ({clientX}, columnInfo) => {
			if (itemElement) {
				const index = getHighlightedColumnIndex(clientX);
				setHighLightedColumn(index);

				const columnSizes = getColumnAccumulationSizes(
					item.children,
					layoutData,
					selectedViewportSize
				);

				const {
					colIndex,
					currentColumn,
					nextColumn,
					nextColumnIndex,
				} = columnInfo;

				const currentRange = columnSizes[colIndex];
				const nextRange = columnSizes[nextColumnIndex];

				const addedIndex = index + 1;

				const responsiveConfig = getResponsiveConfig(
					columnInfo.currentColumnConfig,
					selectedViewportSize
				);

				const newCurrentSize =
					responsiveConfig.size + (addedIndex - currentRange);

				const newNextSize = nextRange - addedIndex;

				if (newCurrentSize >= 1 && newNextSize >= 1) {
					dispatch(
						updateColSize({
							itemId: currentColumn.itemId,
							nextColumnItemId: nextColumn.itemId,
							nextColumnSize: newNextSize,
							selectedViewportSize,
							size: newCurrentSize,
						})
					);
				}
			}
		};

		const onResizeEnd = () => {
			setHighLightedColumn(null);
			setShowOverlay(false);
			setResizeFinished(true);
		};

		useEffect(() => {
			if (resizeFinished) {
				setResizeFinished(false);

				dispatch(
					resizeColumns({
						layoutData,
						segmentsExperienceId,
					})
				);
			}
		}, [layoutData, dispatch, resizeFinished, segmentsExperienceId]);

		const buttons = [];

		if (!hasDropZoneChild(item, layoutData)) {
			buttons.push(LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem);
			buttons.push(
				LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.saveFragmentComposition
			);
		}

		buttons.push(LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.rowConfiguration);

		const {verticalAlignment} = rowConfig;

		return (
			<Topper
				item={item}
				itemElement={itemElement}
				layoutData={layoutData}
			>
				<Row
					className={classNames('page-editor__row', {
						'align-bottom': verticalAlignment === 'bottom',
						'align-middle': verticalAlignment === 'middle',
					})}
					item={item}
					layoutData={layoutData}
					ref={setRef}
				>
					<FloatingToolbar
						buttons={buttons}
						item={item}
						itemElement={itemElement}
						onButtonClick={handleButtonClick}
					/>

					<ResizingContext.Provider
						value={{
							onResizeEnd,
							onResizeStart,
							onResizing,
						}}
					>
						{children}

						{showOverlay && (
							<ColumnOverlayGrid
								columnSpacing={gutters}
								highlightedColumn={highlightedColumn}
								rowRect={rowRect}
							/>
						)}
					</ResizingContext.Provider>

					{openSaveFragmentCompositionModal && (
						<SaveFragmentCompositionModal
							errorMessage={''}
							itemId={item.itemId}
							observer={observer}
							onClose={onClose}
							onErrorDismiss={() => true}
						/>
					)}
				</Row>
			</Topper>
		);
	}
);

RowWithControls.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({gutters: PropTypes.bool}),
	}).isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default RowWithControls;

const TOTAL_NUMBER_OF_COLUMNS = 12;

/**
 * Generates an array containing the current width of each column on the grid of 12.
 * For example, for a column width of 100, the result will be:
 *
 * > [100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200]
 *
 * @param {!Number} The starting colWidth.
 *
 * @returns{!Array<number>}
 */
function getGridRanges(colWidth) {
	return [...Array(TOTAL_NUMBER_OF_COLUMNS).keys()].reduce(
		(acc, current, index) => {
			if (index === 0) {
				return acc;
			}

			return [...acc, acc[current - 1] + colWidth];
		},
		[colWidth]
	);
}

/**
 * Returns an accumulative array of sizes.
 * For example, for a Row containing 3 columns sizing `4`:
 * The result is [4, 8, 12]. This result indicates the grid position of each column end.
 *
 * @param {!Array<String>} A rowChildren array, that contains the related itemId of each Row child.
 * @param {!Object} The current value of layoutData from store.
 * @param {String} Selected viewport size.
 *
 * @returns {!Array<number>}
 */
function getColumnAccumulationSizes(
	rowChildren,
	layoutData,
	selectedViewportSize
) {
	const initialConfig = getResponsiveConfig(
		layoutData.items[rowChildren[0]].config,
		selectedViewportSize
	);

	return rowChildren.reduce(
		(acc, currentId, index) => {
			if (index === 0) {
				return acc;
			}

			const currentConfig = getResponsiveConfig(
				layoutData.items[currentId].config,
				selectedViewportSize
			);

			return [...acc, acc[index - 1] + currentConfig.size];
		},
		[initialConfig.size]
	);
}

/**
 * Calculates the current Grid position when receiving a mousePosition.
 *
 * @param {!Number} Current mousePosition from mousemove event.
 * @param {!Number} The index of the current hovered grid.
 */
function getClosestGridIndexPosition(mousePosition, gridSizes) {
	const closest = gridSizes.reduce((prev, curr) =>
		Math.abs(curr - mousePosition) < Math.abs(prev - mousePosition)
			? curr
			: prev
	);

	return gridSizes.indexOf(closest);
}

/**
 * Receives the current Row OffsetWidth and calls `getGridRanges`.
 * @param {!Number} Row's OffsetWidth
 *
 * @returns {!Array}
 */
function getGridSizes(rowOffsetWidth) {
	const colWidth = Math.floor(rowOffsetWidth / TOTAL_NUMBER_OF_COLUMNS);

	return getGridRanges(colWidth);
}

/**
 *
 * @param {!Element} An element
 *
 * @returns {!DOMRect} The DOMRect of the given element.
 */
function getRect(element) {
	if (!element) {
		return {
			bottom: 0,
			height: 0,
			left: 0,
			right: 0,
			top: 0,
			width: 0,
		};
	}

	return element.getBoundingClientRect();
}
