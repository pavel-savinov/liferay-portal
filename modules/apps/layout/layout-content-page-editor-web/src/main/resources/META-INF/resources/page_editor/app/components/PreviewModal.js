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
import React, {useEffect, useState} from 'react';

import ExperienceToolbarSection from '../../plugins/experience/components/ExperienceToolbarSection';
import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {config} from '../config/index';
import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../store/index';
import {useId} from '../utils/useId';
import Translation from './Translation';
import ViewportSizeSelector from './ViewportSizeSelector';

const PreviewModal = ({observer}) => {
	const dispatch = useDispatch();

	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const languageId = useSelector((state) => state.languageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const experienceSelectId = useId();

	const [previewURL, setPreviewURL] = useState('');

	useEffect(() => {
		setPreviewURL(
			addParams(
				{
					[`${config.portletNamespace}languageId`]: languageId,
					[`${config.portletNamespace}segmentsExperienceId`]: segmentsExperienceId,
				},
				config.previewPageURL
			)
		);
	}, [languageId, segmentsExperienceId]);

	useEffect(() => {
		const modalBody = document.querySelector('.modal-body');

		if (modalBody) {
			const {maxWidth, minWidth} = config.availableViewportSizes[
				selectedViewportSize
			];

			if (selectedViewportSize === VIEWPORT_SIZES.desktop) {
				modalBody.style.width = '100%';
			}
			else {
				const width = (maxWidth + minWidth) / 2;

				modalBody.style.width = `${width}px`;
			}
		}
	}, [selectedViewportSize]);

	return (
		<ClayModal
			className="page-editor__preview__modal"
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
						<div key={experienceSelectId}>
							<ul className="navbar-nav">
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
										defaultLanguageId={
											config.defaultLanguageId
										}
										dispatch={dispatch}
										fragmentEntryLinks={fragmentEntryLinks}
										languageId={languageId}
										segmentsExperienceId={
											segmentsExperienceId
										}
									/>
								</li>
							</ul>
						</div>

						{config.responsiveEnabled && (
							<div className="mr-8">
								<ViewportSizeSelector
									selectedSize={selectedViewportSize}
								/>
							</div>
						)}

						<div></div>
					</div>
				</ClayModal.Title>
			</ClayModal.Header>

			<ClayModal.Body url={previewURL}></ClayModal.Body>
		</ClayModal>
	);
};

PreviewModal.propTypes = {
	observer: PropTypes.object.isRequired,
};

export default PreviewModal;
