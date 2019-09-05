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

import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './FloatingToolbarFragmentBackgroundImagePanelDelegateTemplate.soy';
import {
	BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
	DEFAULT_LANGUAGE_ID_KEY
} from '../../../utils/constants';
import {getConnectedComponent} from '../../../store/ConnectedComponent.es';
import {prefixSegmentsExperienceId} from '../../../utils/prefixSegmentsExperienceId.es';
import {openImageSelector} from '../../../utils/FragmentsEditorDialogUtils';
import templates from './FloatingToolbarFragmentBackgroundImagePanel.soy';
import {updateEditableValueAction} from '../../../actions/updateEditableValue.es';
import {setIn} from '../../../utils/FragmentsEditorUpdateUtils.es';

/**
 * FloatingToolbarFragmentBackgroundImagePanel
 */
class FloatingToolbarFragmentBackgroundImagePanel extends Component {
	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		if (this.item.backgroundImage.title) {
			nextState = setIn(
				nextState,
				['_backgroundImage'],
				this.item.backgroundImage.title
			);
		} else {
			nextState = setIn(
				nextState,
				['_backgroundImage'],
				this.item.backgroundImage
			);
		}

		return nextState;
	}

	/**
	 * Show image selector
	 * @private
	 * @review
	 */
	_handleSelectButtonClick() {
		openImageSelector({
			callback: image => this._updateFragmentBackgroundImage(image),
			imageSelectorURL: this.imageSelectorURL,
			portletNamespace: this.portletNamespace
		});
	}

	/**
	 * Remove existing image if any
	 * @private
	 * @review
	 */
	_handleClearButtonClick() {
		this._updateFragmentBackgroundImage('');
	}

	/**
	 * Dispatches action to update editableValues with new background image url
	 * @param {string} backgroundImageURL
	 */
	_updateFragmentBackgroundImage(image) {
		this.store.dispatch(
			updateEditableValueAction({
				editableId: this.item.editableId,
				editableValueContent: image,
				editableValueId: this.languageId || DEFAULT_LANGUAGE_ID_KEY,
				fragmentEntryLinkId: this.item.fragmentEntryLinkId,
				processor: BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR,
				segmentsExperienceId: prefixSegmentsExperienceId(
					this.segmentsExperienceId ||
						this.defaultSegmentsExperienceId
				)
			})
		);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarFragmentBackgroundImagePanel.STATE = {
	/**
	 * @default ''
	 * @memberof FloatingToolbarFragmentBackgroundImagePanel
	 * @review
	 * @type {string}
	 */
	_backgroundImage: Config.object().value(''),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarFragmentBackgroundImagePanel
	 * @review
	 * @type {!object}
	 */
	item: Config.object().required()
};

const ConnectedFloatingToolbarFragmentBackgroundImagePanel = getConnectedComponent(
	FloatingToolbarFragmentBackgroundImagePanel,
	[
		'defaultSegmentsExperienceId',
		'imageSelectorURL',
		'languageId',
		'portletNamespace',
		'segmentsExperienceId'
	]
);

Soy.register(ConnectedFloatingToolbarFragmentBackgroundImagePanel, templates);

export {
	ConnectedFloatingToolbarFragmentBackgroundImagePanel,
	FloatingToolbarFragmentBackgroundImagePanel
};
export default ConnectedFloatingToolbarFragmentBackgroundImagePanel;
