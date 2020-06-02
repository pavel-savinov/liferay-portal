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

import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {addParams} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import ExperienceToolbarSection from '../../plugins/experience/components/ExperienceToolbarSection';
import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {config} from '../config/index';
import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import {useSelector} from '../store/index';
import {useId} from '../utils/useId';
import Translation from './Translation';
import ViewportSizeSelector from './ViewportSizeSelector';

const PreviewModal = ({observer}) => {
	const [languageId, setLanguageId] = useState(
		useSelector((state) => state.languageId)
	);

	const [viewportSize, setViewportSize] = useState(
		useSelector((state) => state.selectedViewportSize)
	);

	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const experienceSelectId = useId();

	const previewURL = useMemo(
		() =>
			addParams(
				{
					[`${config.portletNamespace}languageId`]: languageId,
					[`${config.portletNamespace}segmentsExperienceId`]: segmentsExperienceId,
				},
				config.previewPageURL
			),
		[languageId, segmentsExperienceId]
	);

	const modalBodyWidth = useMemo(() => {
		const {maxWidth, minWidth} = config.availableViewportSizes[
			viewportSize
		];

		return viewportSize === VIEWPORT_SIZES.desktop
			? '100%'
			: (maxWidth + minWidth) / 2;
	}, [viewportSize]);

	return (
		<ClayModal
			className={classNames('page-editor__preview-modal', {
				'page-editor__preview-modal--with-border':
					viewportSize !== VIEWPORT_SIZES.desktop,
			})}
			observer={observer}
			size="full-screen"
		>
			<ClayModal.Header className="container-fluid">
				<ClayModal.Title className="pb-3 pt-3">
					<div
						className={classNames(
							'd-flex',
							'justify-content-between',
							{
								'responsive-mode': config.responsiveEnabled,
							}
						)}
					>
						<ul className="navbar-nav page-editor__preview-modal__part">
							<li className="nav-item">
								<ExperienceToolbarSection
									selectId={experienceSelectId}
								/>
							</li>

							<li className="nav-item">
								<Translation
									availableLanguages={
										config.availableLanguages
									}
									defaultLanguageId={config.defaultLanguageId}
									dispatch={({languageId: nextLanguageId}) =>
										setLanguageId(nextLanguageId)
									}
									fragmentEntryLinks={fragmentEntryLinks}
									languageId={languageId}
									segmentsExperienceId={segmentsExperienceId}
								/>
							</li>
						</ul>

						<div className="page-editor__preview-modal__part">
							{config.responsiveEnabled && (
								<ViewportSizeSelector
									onSizeSelected={setViewportSize}
									selectedSize={viewportSize}
								/>
							)}
						</div>

						<div className="page-editor__preview-modal__part"></div>
					</div>
				</ClayModal.Title>
			</ClayModal.Header>

			<ClayModal.Body
				iFrameProps={{
					style: {maxWidth: modalBodyWidth},
				}}
				url={previewURL}
			/>
		</ClayModal>
	);
};

PreviewModal.propTypes = {
	observer: PropTypes.object.isRequired,
};

export default PreviewModal;
