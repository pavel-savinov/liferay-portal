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

import ClayForm, {
	ClayCheckbox,
	ClayInput,
	ClaySelectWithOption,
} from '@clayui/form';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import MappingSelector from '../../../common/components/MappingSelector';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import selectLanguageId from '../../selectors/selectLanguageId';
import InfoItemService from '../../services/InfoItemService';
import {useSelector} from '../../store/index';
import isMapped from '../../utils/isMapped';
import {useId} from '../../utils/useId';

const SOURCE_OPTIONS = {
	fromContentField: {
		label: `${Liferay.Language.get('from-content-field')}`,
		value: 'fromContentField',
	},

	manual: {
		label: `${Liferay.Language.get('manual')}`,
		value: 'manual',
	},
};

export const TARGET_OPTIONS = {
	blank: '_blank',
	parent: '_parent',
	self: '_self',
	top: '_top',
};

export default function LinkField({field, onValueSelect, value}) {
	const [nextValue, setNextValue] = useState({});
	const [nextHref, setNextHref] = useState('');
	const [openNewTab, setOpenNewTab] = useState('');

	const [mappedHrefPreview, setMappedHrefPreview] = useState(null);
	const languageId = useSelector(selectLanguageId);

	const [source, setSource] = useState(SOURCE_OPTIONS.manual.value);

	useEffect(() => {
		setNextValue(value);
		setNextHref(value.href);
		setOpenNewTab(value.target === '_blank');

		setSource(
			isMapped(value) || source === SOURCE_OPTIONS.fromContentField.value
				? SOURCE_OPTIONS.fromContentField.value
				: SOURCE_OPTIONS.manual.value
		);
	}, [source, value]);

	const hrefInputId = useId();
	const hrefPreviewInputId = useId();
	const sourceInputId = useId();
	const targetInputId = useId();

	useEffect(() => {
		if (nextValue.classNameId && nextValue.classPK && nextValue.fieldId) {
			setMappedHrefPreview('');

			InfoItemService.getInfoItemFieldValue({
				...nextValue,
				languageId,
				onNetworkStatus: () => {},
			}).then(({fieldValue}) => {
				setMappedHrefPreview(fieldValue || '');
			});
		}
		else {
			setMappedHrefPreview(null);
		}
	}, [languageId, nextValue]);

	const handleChange = (value) => {
		const updatedValue = {
			...nextValue,
			...value,
		};

		onValueSelect(field.name, updatedValue);
		setNextValue(updatedValue);
	};

	const handleSourceChange = (event) => {
		onValueSelect(field.name, {});
		setNextValue({});
		setSource(event.target.value);
		setMappedHrefPreview(null);
	};

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor={sourceInputId}>
					{Liferay.Language.get('link')}
				</label>

				<ClaySelectWithOption
					id={sourceInputId}
					onChange={handleSourceChange}
					options={Object.values(SOURCE_OPTIONS)}
					value={source}
				/>
			</ClayForm.Group>

			{source === SOURCE_OPTIONS.manual.value && (
				<ClayForm.Group small>
					<label htmlFor={hrefInputId}>
						{Liferay.Language.get('url')}
					</label>

					<ClayInput
						id={hrefInputId}
						onBlur={() => handleChange({href: nextHref})}
						onChange={(event) => setNextHref(event.target.value)}
						type="text"
						value={nextHref || ''}
					/>
				</ClayForm.Group>
			)}

			{source === SOURCE_OPTIONS.fromContentField.value && (
				<>
					<MappingSelector
						fieldType={EDITABLE_TYPES.link}
						mappedItem={nextValue}
						onMappingSelect={(mappedItem) =>
							handleChange(mappedItem)
						}
					/>

					{mappedHrefPreview !== null && (
						<ClayForm.Group small>
							<label htmlFor={hrefPreviewInputId}>
								{Liferay.Language.get('url')}
							</label>

							<ClayInput
								id={hrefPreviewInputId}
								readOnly
								value={mappedHrefPreview}
							/>
						</ClayForm.Group>
					)}
				</>
			)}

			<ClayCheckbox
				aria-label={Liferay.Language.get('open-in-a-new-tab')}
				checked={openNewTab}
				id={targetInputId}
				label={Liferay.Language.get('open-in-a-new-tab')}
				onChange={(event) => {
					setOpenNewTab(event.target.checked);
					handleChange({
						target: event.target.checked
							? TARGET_OPTIONS.blank
							: TARGET_OPTIONS.self,
					});
				}}
			/>
		</>
	);
}

LinkField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([
		PropTypes.shape({
			classNameId: PropTypes.string,
			classPK: PropTypes.string,
			fieldId: PropTypes.string,
			target: PropTypes.oneOf(Object.values(TARGET_OPTIONS)),
		}),

		PropTypes.shape({
			href: PropTypes.string,
			target: PropTypes.oneOf(Object.values(TARGET_OPTIONS)),
		}),

		PropTypes.shape({
			mappedField: PropTypes.string,
			target: PropTypes.oneOf(Object.values(TARGET_OPTIONS)),
		}),
	]),
};
