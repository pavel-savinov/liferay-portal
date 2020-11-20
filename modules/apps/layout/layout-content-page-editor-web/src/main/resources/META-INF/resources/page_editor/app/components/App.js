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
import React, {useEffect} from 'react';

import {StyleBookContextProvider} from '../../plugins/page-design-options/hooks/useStyleBook';
import {INIT} from '../actions/types';
import {config} from '../config/index';
import {reducer} from '../reducers/index';
import selectLanguageId from '../selectors/selectLanguageId';
import {StoreContextProvider, useSelector} from '../store/index';
import {DragAndDropContextProvider} from '../utils/dragAndDrop/useDragAndDrop';
import {CollectionActiveItemContextProvider} from './CollectionActiveItemContext';
import {ControlsProvider} from './Controls';
import DragPreview from './DragPreview';
import LayoutViewport from './LayoutViewport';
import ShortcutManager from './ShortcutManager';
import Sidebar from './Sidebar';
import Toolbar from './Toolbar';
import URLParser from './URLParser';

const DEFAULT_SESSION_LENGTH = 60 * 1000;

export default function App({state}) {
	const initialState = reducer(state, {type: INIT});

	useEffect(() => {
		if (Liferay.Session && config.autoExtendSessionEnabled) {
			const sessionLength =
				Liferay.Session.get('sessionLength') || DEFAULT_SESSION_LENGTH;

			const interval = setInterval(() => {
				Liferay.Session.extend();
			}, sessionLength / 2);

			return () => clearInterval(interval);
		}
	}, []);

	return (
		<StoreContextProvider initialState={initialState} reducer={reducer}>
			<LanguageDirection />
			<URLParser />
			<ControlsProvider>
				<CollectionActiveItemContextProvider>
					<DragAndDropContextProvider>
						<DragPreview />
						<Toolbar />
						<LayoutViewport />
						<ShortcutManager />

						<StyleBookContextProvider>
							<Sidebar />
						</StyleBookContextProvider>
					</DragAndDropContextProvider>
				</CollectionActiveItemContextProvider>
			</ControlsProvider>
		</StoreContextProvider>
	);
}

App.propTypes = {
	state: PropTypes.object.isRequired,
};

const LanguageDirection = () => {
	const languageId = useSelector(selectLanguageId);

	useEffect(() => {
		const currentLanguageDirection = config.languageDirection[languageId];
		const wrapper = document.getElementById('wrapper');

		if (wrapper) {
			wrapper.dir = currentLanguageDirection;
			wrapper.lang = languageId;
		}
	}, [languageId]);

	return null;
};
