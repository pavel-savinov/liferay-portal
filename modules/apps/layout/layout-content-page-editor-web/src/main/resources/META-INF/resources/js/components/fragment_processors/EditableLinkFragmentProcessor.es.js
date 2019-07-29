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

import {FLOATING_TOOLBAR_BUTTONS} from '../../utils/constants';
import {destroy, init} from './EditableTextFragmentProcessor.es';

/**
 * @return {object[]} Floating toolbar panels
 */
function getFloatingToolbarButtons(editableValues) {
	const buttons = [FLOATING_TOOLBAR_BUTTONS.edit];

	const linkButton = Object.assign({}, FLOATING_TOOLBAR_BUTTONS.link);

	if (
		editableValues.config.fieldId ||
		editableValues.config.href ||
		editableValues.config.mappedField
	) {
		linkButton.cssClass =
			'fragments-editor__floating-toolbar--linked-field';
	}

	buttons.push(linkButton);

	return buttons;
}

/**
 * @param {string} content editableField's original HTML
 * @param {string} value Translated/segmented value
 * @param {object} editableValues values of the element
 * @return {string} Transformed content
 */
function render(content, value, editableValues) {
	const wrapper = document.createElement('div');

	wrapper.innerHTML = content;

	const config = (editableValues && editableValues.config) || {};
	const link = wrapper.querySelector('a');

	if (link) {
		link.innerHTML = value;

		if (config.href) {
			link.href = config.href;
		}

		if (config.target) {
			link.target = config.target;
		}

		if (config.buttonType) {
			Array.from(link.classList).forEach(elementClass => {
				if (
					elementClass.indexOf('btn-') === 0 ||
					elementClass === 'btn'
				) {
					link.classList.remove(elementClass);
				}
			});

			if (config.buttonType && config.buttonType === 'link') {
				link.classList.add('link');
			} else {
				link.classList.add('btn');
				link.classList.add(`btn-${config.buttonType}`);
			}
		}
	}

	return wrapper.innerHTML;
}

export default {
	destroy,
	getFloatingToolbarButtons,
	init,
	render
};
