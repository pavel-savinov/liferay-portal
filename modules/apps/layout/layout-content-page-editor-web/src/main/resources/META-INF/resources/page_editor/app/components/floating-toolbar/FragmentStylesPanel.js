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

import PropTypes from 'prop-types';
import React, {useCallback} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {FRAGMENT_CONFIGURATION_ROLES} from '../../config/constants/fragmentConfigurationRoles';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/freemarkerFragmentEntryProcessor';
import {config} from '../../config/index';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateFragmentConfiguration from '../../thunks/updateFragmentConfiguration';
import updateItemConfig from '../../thunks/updateItemConfig';
import {FieldSet} from './FieldSet';

export const FragmentStylesPanel = ({item}) => {
	const dispatch = useDispatch();

	const {commonStyles} = config;

	const fragmentEntryLink = useSelector(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId]
	);

	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const onCustomStyleValueSelect = useCallback(
		(name, value) => {
			const configurationValues = getConfigurationValues(
				fragmentEntryLink
			);

			const nextConfigurationValues = {
				...configurationValues,
				[name]: value,
			};

			dispatch(
				updateFragmentConfiguration({
					configurationValues: nextConfigurationValues,
					fragmentEntryLink,
					segmentsExperienceId,
				})
			);
		},
		[dispatch, fragmentEntryLink, segmentsExperienceId]
	);

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

	return (
		<>
			<div className="page-editor__floating-toolbar__panel-header">
				<p>{Liferay.Language.get('styles')}</p>
			</div>

			<CustomStyles
				fragmentEntryLink={fragmentEntryLink}
				onValueSelect={onCustomStyleValueSelect}
			/>

			<CommonStyles
				commonStyles={commonStyles}
				item={item}
				onValueSelect={onCommonStylesValueSelect}
			/>
		</>
	);
};

FragmentStylesPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired,
		}).isRequired,
	}),
};

const CustomStyles = ({fragmentEntryLink, onValueSelect}) => {
	const fieldSets = fragmentEntryLink.configuration?.fieldSets.filter(
		(fieldSet) =>
			fieldSet.configurationRole === FRAGMENT_CONFIGURATION_ROLES.style
	);

	return (
		<div className="page-editor__floating-toolbar__panel__custom-styles">
			{fieldSets.map((fieldSet, index) => {
				return (
					<FieldSet
						configurationValues={getConfigurationValues(
							fragmentEntryLink
						)}
						fields={fieldSet.fields}
						key={index}
						label={fieldSet.label}
						onValueSelect={onValueSelect}
					/>
				);
			})}
		</div>
	);
};

CustomStyles.propTypes = {
	fragmentEntryLink: PropTypes.object.isRequired,
	onValueSelect: PropTypes.func.isRequired,
};

const CommonStyles = ({commonStyles, item, onValueSelect}) => (
	<div className="page-editor__floating-toolbar__panel__common-styles">
		{commonStyles.map((fieldSet, index) => {
			return (
				<FieldSet
					configurationValues={item.config.styles}
					fields={fieldSet.styles}
					key={index}
					label={fieldSet.label}
					onValueSelect={onValueSelect}
				/>
			);
		})}
	</div>
);

CustomStyles.propTypes = {
	fragmentEntryLink: PropTypes.object.isRequired,
	onValueSelect: PropTypes.func.isRequired,
};

function getConfigurationValues(fragmentEntryLink) {
	return {
		...fragmentEntryLink.defaultConfigurationValues,
		...fragmentEntryLink.editableValues[
			FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
		],
	};
}
