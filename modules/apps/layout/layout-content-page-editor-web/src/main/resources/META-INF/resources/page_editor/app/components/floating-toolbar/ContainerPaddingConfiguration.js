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

import {CONTAINER_PADDING_IDENTIFIERS} from '../../config/constants/containerPaddingIdentifiers';
import {config} from '../../config/index';
import {useId} from '../../utils/useId';

export const ContainerPaddingVerticalConfiguration = ({
	onValueChange,
	paddingBottom,
	paddingTop,
}) => {
	const containerPaddingTopId = useId();
	const containerPaddingBottomId = useId();

	return (
		<ClayForm.Group className="form-group-autofit" small>
			<div className="form-group-item">
				<label htmlFor={containerPaddingTopId}>
					{Liferay.Language.get('padding-top')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('padding-top')}
					id={containerPaddingTopId}
					onChange={({target: {value}}) =>
						onValueChange({
							[CONTAINER_PADDING_IDENTIFIERS.paddingTop]: Number(
								value
							),
						})
					}
					options={config.paddingOptions}
					value={String(paddingTop)}
				/>
			</div>

			<div className="form-group-item">
				<label htmlFor={containerPaddingBottomId}>
					{Liferay.Language.get('padding-bottom')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('padding-bottom')}
					id={containerPaddingBottomId}
					onChange={({target: {value}}) =>
						onValueChange({
							[CONTAINER_PADDING_IDENTIFIERS.paddingBottom]: Number(
								value
							),
						})
					}
					options={config.paddingOptions}
					value={String(paddingBottom)}
				/>
			</div>
		</ClayForm.Group>
	);
};

ContainerPaddingVerticalConfiguration.propTypes = {
	onValueChange: PropTypes.func.isRequired,
	paddingBottom: PropTypes.number,
	paddingTop: PropTypes.number,
};

export const ContainerPaddingHorizontalConfiguration = ({
	onValueChange,
	paddingHorizontal,
}) => {
	const containerPaddingHorizontalId = useId();

	return (
		<ClayForm.Group className="form-group-autofit" small>
			<div className="form-group-item">
				<label htmlFor={containerPaddingHorizontalId}>
					{Liferay.Language.get('padding-horizontal')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('padding-horizontal')}
					id={containerPaddingHorizontalId}
					onChange={({target: {value}}) =>
						onValueChange({
							[CONTAINER_PADDING_IDENTIFIERS.paddingHorizontal]: Number(
								value
							),
						})
					}
					options={config.paddingOptions}
					value={String(paddingHorizontal)}
				/>
			</div>
		</ClayForm.Group>
	);
};

ContainerPaddingVerticalConfiguration.propTypes = {
	onValueChange: PropTypes.func.isRequired,
	paddingHorizontal: PropTypes.number,
};
