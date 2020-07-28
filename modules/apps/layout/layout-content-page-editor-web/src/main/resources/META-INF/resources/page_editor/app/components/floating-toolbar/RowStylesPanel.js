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

import ClayForm, {ClayCheckbox, ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {config} from '../../config/index';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateItemConfig from '../../thunks/updateItemConfig';
import updateRowColumns from '../../thunks/updateRowColumns';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import {useId} from '../../utils/useId';
import {
	useCustomRowContext,
	useSetCustomRowContext,
	useSetUpdatedLayoutDataContext,
} from '../ResizeContext';
import {FieldSet} from '../floating-toolbar/FieldSet';

const CUSTOM_ROW = 'custom';

const MODULES_PER_ROW_OPTIONS = [
	[1],
	[1, 2],
	[1, 3],
	[1, 2, 4],
	[1, 2, 5],
	[1, 2, 3, 6],
];
const MODULES_PER_ROW_OPTIONS_WITH_CUSTOM = MODULES_PER_ROW_OPTIONS.map(
	(option) => [CUSTOM_ROW, ...option]
);

const VERTICAL_ALIGNMENT_OPTIONS = [
	{label: Liferay.Language.get('top'), value: 'top'},
	{label: Liferay.Language.get('middle'), value: 'middle'},
	{label: Liferay.Language.get('bottom'), value: 'bottom'},
];

const ROW_STYLE_IDENTIFIERS = {
	modulesPerRow: 'modulesPerRow',
	reverseOrder: 'reverseOrder',
	verticalAlignment: 'verticalAlignment',
};

export const RowStylesPanel = ({item}) => {
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const setUpdatedLayoutData = useSetUpdatedLayoutDataContext();
	const setCustomRow = useSetCustomRowContext();
	const customRow = useCustomRowContext();

	const {commonStyles} = config;

	const handleConfigurationValueChanged = (identifier, value) => {
		setCustomRow(false);
		setUpdatedLayoutData(null);

		let itemStyles = {[identifier]: value};

		if (
			selectedViewportSize !== VIEWPORT_SIZES.desktop &&
			identifier !== ROW_STYLE_IDENTIFIERS.gutters
		) {
			itemStyles = {[selectedViewportSize]: itemStyles};
		}

		if (identifier === ROW_STYLE_IDENTIFIERS.numberOfColumns) {
			const currentNumberOfColumns = rowConfig.numberOfColumns;
			const newNumberOfColumns = value;

			const columnsToBeModified = Math.abs(
				newNumberOfColumns - currentNumberOfColumns
			);

			if (columnsToBeModified === 0) {
				return;
			}

			if (item && item.itemId) {
				dispatch(
					updateRowColumns({
						itemId: item.itemId,
						numberOfColumns: newNumberOfColumns,
						segmentsExperienceId,
						viewportSizeId: selectedViewportSize,
					})
				);
			}

			return;
		}

		dispatch(
			updateItemConfig({
				itemConfig: itemStyles,
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);
	};

	const onCommonStylesValueSelect = (name, value) =>
		dispatch(
			updateItemConfig({
				itemConfig: {
					styles: {
						[name]: value,
					},
				},
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);

	const getModulesPerRowOptionLabel = (value) => {
		return value > 1
			? Liferay.Language.get('x-modules-per-row')
			: Liferay.Language.get('x-module-per-row');
	};

	const rowConfig = getResponsiveConfig(item.config, selectedViewportSize);
	const modulesPerRowOptions = customRow
		? MODULES_PER_ROW_OPTIONS_WITH_CUSTOM
		: MODULES_PER_ROW_OPTIONS;

	return (
		<>
			<>
				<div className="page-editor__floating-toolbar__panel__custom-styles">
					<Select
						configurationKey="modulesPerRow"
						handleChange={handleConfigurationValueChanged}
						label={Liferay.Language.get('layout')}
						options={modulesPerRowOptions[
							rowConfig.numberOfColumns - 1
						].map((option) => ({
							disabled: option === CUSTOM_ROW,
							label:
								option === CUSTOM_ROW
									? Liferay.Language.get('custom')
									: Liferay.Util.sub(
											getModulesPerRowOptionLabel(option),
											option
									  ),
							value: option,
						}))}
						value={customRow ? CUSTOM_ROW : rowConfig.modulesPerRow}
					/>

					{rowConfig.numberOfColumns === 2 &&
						rowConfig.modulesPerRow === 1 && (
							<ClayCheckbox
								checked={rowConfig.reverseOrder}
								label={Liferay.Language.get('inverse-order')}
								onChange={({target: {checked}}) =>
									handleConfigurationValueChanged(
										'reverseOrder',
										checked
									)
								}
							/>
						)}

					<Select
						configurationKey="verticalAlignment"
						handleChange={handleConfigurationValueChanged}
						label={Liferay.Language.get('vertical-alignment')}
						options={VERTICAL_ALIGNMENT_OPTIONS}
						value={rowConfig.verticalAlignment}
					/>
				</div>

				<div className="page-editor__floating-toolbar__panel__common-styles">
					{commonStyles.map((fieldSet, index) => {
						return (
							<FieldSet
								fields={fieldSet.styles}
								key={index}
								label={fieldSet.label}
								onValueSelect={onCommonStylesValueSelect}
								values={item.config.styles}
							/>
						);
					})}
				</div>
			</>
		</>
	);
};

RowStylesPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({numberOfColumns: PropTypes.number}),
	}),
};

const Select = ({configurationKey, handleChange, label, options, value}) => {
	const inputId = useId();

	return (
		<ClayForm.Group small>
			<label htmlFor={inputId}>{label}</label>

			<ClaySelectWithOption
				id={inputId}
				onChange={(event) => {
					const nextValue = event.target.value;

					handleChange(
						configurationKey,
						typeof value === 'string'
							? String(nextValue)
							: Number(nextValue)
					);
				}}
				options={options}
				value={String(value)}
			/>
		</ClayForm.Group>
	);
};

Select.propTypes = {
	configurationKey: PropTypes.string.isRequired,
	handleChange: PropTypes.func.isRequired,
	label: PropTypes.string.isRequired,
	options: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.oneOfType([
				PropTypes.string.isRequired,
				PropTypes.number.isRequired,
			]),
		})
	),
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};
