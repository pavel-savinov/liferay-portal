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

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import React, {useEffect, useState} from 'react';
import ReactDOM from 'react-dom';

import useLazy from '../../core/hooks/useLazy';
import useLoad from '../../core/hooks/useLoad';
import usePlugins from '../../core/hooks/usePlugins';
import selectExperience from '../../plugins/experience/actions/selectExperience';
import * as Actions from '../actions/index';
import {PAGE_TYPES} from '../config/constants/pageTypes';
import {config} from '../config/index';
import {useDispatch, useSelector} from '../store/index';
import undo from '../thunks/undo';
import {useDropClear} from '../utils/useDragAndDrop';
import {useSelectItem} from './Controls';
import ExperimentsLabel from './ExperimentsLabel';
import NetworkStatusBar from './NetworkStatusBar';
import PreviewModal from './PreviewModal';
import Translation from './Translation';
import UnsafeHTML from './UnsafeHTML';
import ViewportSizeSelector from './ViewportSizeSelector';
import Undo from './undo/Undo';

const {Suspense, useCallback, useRef} = React;

function ToolbarBody() {
	const dispatch = useDispatch();
	const dropClearRef = useDropClear();
	const {getInstance, register} = usePlugins();
	const isMounted = useIsMounted();
	const load = useLoad();
	const selectItem = useSelectItem();
	const store = useSelector((state) => state);

	const {
		network,
		segmentsExperienceId,
		segmentsExperimentStatus,
		selectedViewportSize,
	} = store;

	const [enableDiscard, setEnableDiscard] = useState(false);

	const [openPreviewModal, setOpenPreviewModal] = useState(false);

	const [
		currentSegmentsExperienceId,
		setCurrentSegmentsExperienceId,
	] = useState('');
	const [
		currentSelectedViewportSize,
		setCurrentSelectedViewportSize,
	] = useState('');

	const {observer} = useModal({
		onClose: () => {
			if (isMounted()) {
				setOpenPreviewModal(false);

				dispatch(
					selectExperience({
						segmentsExperienceId: currentSegmentsExperienceId,
					})
				);

				if (config.responsiveEnabled) {
					dispatch(
						Actions.switchViewportSize({
							size: currentSelectedViewportSize,
						})
					);
				}
			}
		},
	});

	useEffect(() => {
		const isConversionPage = config.pageType === PAGE_TYPES.conversion;

		setEnableDiscard(network.lastFetch || config.draft || isConversionPage);
	}, [network.lastFetch]);

	const loading = useRef(() => {
		Promise.all(
			config.toolbarPlugins.map((toolbarPlugin) => {
				const {pluginEntryPoint} = toolbarPlugin;
				const promise = load(pluginEntryPoint, pluginEntryPoint);

				const app = {
					Actions,
					config,
					dispatch,
					store,
				};

				return register(pluginEntryPoint, promise, {
					app,
					toolbarPlugin,
				}).then((plugin) => {
					if (!plugin) {
						throw new Error(
							`Failed to get instance from ${pluginEntryPoint}`
						);
					}
					else if (isMounted()) {
						if (typeof plugin.activate === 'function') {
							plugin.activate();
						}
					}
				});
			})
		).catch((error) => {
			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}
		});
	});

	if (loading.current) {

		// Do this once only.

		loading.current();
		loading.current = null;
	}

	const ToolbarSection = useLazy(
		useCallback(({instance}) => {
			if (typeof instance.renderToolbarSection === 'function') {
				return instance.renderToolbarSection();
			}
			else {
				return null;
			}
		}, [])
	);

	const handleDiscardDraft = (event) => {
		if (
			!confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
				)
			)
		) {
			event.preventDefault();
		}
	};

	const handlePreviewPage = () => {
		setCurrentSegmentsExperienceId(segmentsExperienceId);
		setCurrentSelectedViewportSize(selectedViewportSize);

		setOpenPreviewModal(true);
	};

	const handleSubmit = (event) => {
		if (
			config.masterUsed &&
			!confirm(
				Liferay.Language.get(
					'changes-made-on-this-master-are-going-to-be-propagated-to-all-page-templates,-display-page-templates,-and-pages-using-it.are-you-sure-you-want-to-proceed'
				)
			)
		) {
			event.preventDefault();
		}
	};

	const onUndo = () => {
		dispatch(undo({store}));
	};

	const deselectItem = (event) => {
		if (event.target === event.currentTarget) {
			selectItem(null);
		}
	};

	let draftButtonLabel = Liferay.Language.get('discard-draft');

	if (config.pageType === PAGE_TYPES.conversion) {
		draftButtonLabel = Liferay.Language.get('discard-conversion-draft');
	}
	else if (config.singleSegmentsExperienceMode) {
		draftButtonLabel = Liferay.Language.get('discard-variant');
	}

	let publishButtonLabel = Liferay.Language.get('publish');

	if (config.pageType === PAGE_TYPES.master) {
		publishButtonLabel = Liferay.Language.get('publish-master');
	}
	else if (config.singleSegmentsExperienceMode) {
		publishButtonLabel = Liferay.Language.get('save-variant');
	}
	else if (config.workflowEnabled) {
		publishButtonLabel = Liferay.Language.get('submit-for-publication');
	}

	return (
		<div
			className="container-fluid container-fluid-max-xl"
			onClick={deselectItem}
			ref={dropClearRef}
		>
			<ul
				className={classNames('navbar-nav', {
					'responsive-mode': config.responsiveEnabled,
				})}
				onClick={deselectItem}
			>
				{config.toolbarPlugins.map(
					({loadingPlaceholder, pluginEntryPoint}) => {
						return (
							<li className="nav-item" key={pluginEntryPoint}>
								<ErrorBoundary>
									<Suspense
										fallback={
											<UnsafeHTML
												markup={loadingPlaceholder}
											/>
										}
									>
										<ToolbarSection
											getInstance={getInstance}
											pluginId={pluginEntryPoint}
										/>
									</Suspense>
								</ErrorBoundary>
							</li>
						);
					}
				)}
				<li className="nav-item">
					<Translation
						availableLanguages={config.availableLanguages}
						defaultLanguageId={config.defaultLanguageId}
						dispatch={dispatch}
						fragmentEntryLinks={store.fragmentEntryLinks}
						languageId={store.languageId}
						segmentsExperienceId={segmentsExperienceId}
					/>
				</li>
				{config.responsiveEnabled && (
					<li className="nav-item">
						<ViewportSizeSelector
							selectedSize={selectedViewportSize}
						/>
					</li>
				)}
				{!config.singleSegmentsExperienceMode &&
					segmentsExperimentStatus && (
						<li className="nav-item pl-2">
							<ExperimentsLabel
								label={segmentsExperimentStatus.label}
								value={segmentsExperimentStatus.value}
							/>
						</li>
					)}
			</ul>

			<ul className="navbar-nav" onClick={deselectItem}>
				<NetworkStatusBar {...network} />
				{config.undoEnabled && <Undo onUndo={onUndo} />}

				<li className="nav-item">
					<ClayButton
						className="btn btn-secondary mr-3"
						displayType="secondary"
						onClick={handlePreviewPage}
						small
						type="button"
					>
						{Liferay.Language.get('preview')}
					</ClayButton>
				</li>
				<li className="nav-item">
					<form action={config.discardDraftURL} method="POST">
						<input
							name={`${config.portletNamespace}redirect`}
							type="hidden"
							value={config.discardDraftRedirectURL}
						/>

						<ClayButton
							className="btn btn-secondary mr-3"
							disabled={!enableDiscard}
							displayType="secondary"
							onClick={handleDiscardDraft}
							small
							type="submit"
						>
							{draftButtonLabel}
						</ClayButton>
					</form>
				</li>

				<li className="nav-item">
					<form action={config.publishURL} method="POST">
						<input
							name={`${config.portletNamespace}redirect`}
							type="hidden"
							value={config.redirectURL}
						/>

						<ClayButton
							disabled={config.pending}
							displayType="primary"
							onClick={handleSubmit}
							small
							type="submit"
						>
							{publishButtonLabel}
						</ClayButton>
					</form>
				</li>
			</ul>

			{openPreviewModal && <PreviewModal observer={observer} />}
		</div>
	);
}

class ErrorBoundary extends React.Component {
	static getDerivedStateFromError(_error) {
		return {hasError: true};
	}

	constructor(props) {
		super(props);

		this.state = {hasError: false};
	}

	componentDidCatch(error) {
		if (process.env.NODE_ENV === 'development') {
			console.error(error);
		}
	}

	render() {
		if (this.state.hasError) {
			return null;
		}
		else {
			return this.props.children;
		}
	}
}

export default function Toolbar() {
	const container = document.getElementById(config.toolbarId);
	const isMounted = useIsMounted();

	if (!isMounted()) {

		// First time here, must empty JSP-rendered markup from container.

		while (container.firstChild) {
			container.removeChild(container.firstChild);
		}
	}

	return ReactDOM.createPortal(<ToolbarBody />, container);
}
