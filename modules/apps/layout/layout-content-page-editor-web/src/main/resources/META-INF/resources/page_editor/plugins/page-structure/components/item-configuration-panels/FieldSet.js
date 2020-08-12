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
import React from 'react';

import {FRAGMENT_CONFIGURATION_FIELDS} from '../../../../app/components/fragment-configuration-fields/index';
import {ConfigurationFieldPropTypes} from '../../../../prop-types/index';

export const FieldSet = ({fields, label, onValueSelect, values}) => {
	return (
		<>
			{label && (
				<div className="align-items-center d-flex justify-content-between page-editor__sidebar__fieldset-label pt-3">
					<p className="mb-3 text-uppercase">{label}</p>
				</div>
			)}

			{fields.map((field, index) => {
				const FieldComponent =
					field.type && FRAGMENT_CONFIGURATION_FIELDS[field.type];

				const fieldValue = values[field.name] || field.defaultValue;

				const visible =
					!field.dependencies ||
					field.dependencies.every(
						(dependency) =>
							values[dependency.styleName] === dependency.value
					);

				return (
					visible && (
						<FieldComponent
							field={field}
							key={index}
							onValueSelect={onValueSelect}
							value={fieldValue}
						/>
					)
				);
			})}
		</>
	);
};

FieldSet.propTypes = {
	fields: PropTypes.arrayOf(PropTypes.shape(ConfigurationFieldPropTypes)),
	label: PropTypes.string,
	onValueSelect: PropTypes.func.isRequired,
	values: PropTypes.object,
};
