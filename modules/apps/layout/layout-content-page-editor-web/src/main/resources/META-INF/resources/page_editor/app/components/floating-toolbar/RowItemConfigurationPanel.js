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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

export const RowItemConfigurationPanel = ({
	config,
	id,
	identifier,
	label,
	onValueChange,
	options,
}) => (
	<ClayForm.Group small>
		<label htmlFor={id}>{label}</label>
		<ClaySelectWithOption
			aria-label={label}
			id={id}
			onChange={({target: {value}}) => {
				const parseValue = Number(value) || value;
				onValueChange(identifier, parseValue);
			}}
			options={options.map(value => ({
				label: Number(value) || Liferay.Language.get(value),
				value,
			}))}
			value={String(config)}
		/>
	</ClayForm.Group>
);

RowItemConfigurationPanel.propTypes = {
	config: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
	id: PropTypes.string,
	identifier: PropTypes.string,
	label: PropTypes.string,
	onValueChange: PropTypes.func.isRequired,
	options: PropTypes.array,
};
