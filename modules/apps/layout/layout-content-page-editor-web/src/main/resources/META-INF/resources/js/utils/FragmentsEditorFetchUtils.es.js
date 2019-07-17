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

/**
 * @type {object}
 */
let _store;

/**
 * @param {object} body
 * @param {string} portletNamespace
 * @param {FormData} [_formData=new FormData()]
 * @return {FormData}
 * @review
 */
function _getFormData(body, portletNamespace, _formData = new FormData()) {
	Object.entries(body).forEach(([key, value]) => {
		_formData.append(`${portletNamespace}${key}`, value);
	});

	return _formData;
}

/**
 * @param {string} url
 * @param {object} [body={}]
 * @private
 * @return {Promise<Response>}
 * @review
 */
function _fetch(url, body = {}) {
	return fetch(url, {
		body: _getFormData(body, _store.getState().portletNamespace),
		credentials: 'include',
		method: 'POST'
	});
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {string} body
 */
function addFragmentEntryLinkComment(fragmentEntryLinkId, body) {
	const state = _store.getState();

	return _fetch(state.addFragmentEntryLinkCommentURL, {
		fragmentEntryLinkId,
		body
	});
}

function getExperienceUsedPortletIds(segmentsExperienceId) {
	return _fetch(_store.getState().getExperienceUsedPortletsURL, {
		segmentsExperienceId
	}).then(response => response.json());
}

/**
 * @param {string} segmentsExperienceId
 * @param {Array<string>} [fragmentEntryLinkIds=[]]
 * @return {Promise<Response>}
 */
function removeExperience(
	segmentsExperienceId,
	fragmentEntryLinkIds = [],
	deleteSegmentsExperience = true
) {
	const state = _store.getState();

	const body = {
		segmentsExperienceId,
		deleteSegmentsExperience
	};

	if (fragmentEntryLinkIds && fragmentEntryLinkIds.length) {
		body.fragmentEntryLinkIds = JSON.stringify(fragmentEntryLinkIds);
	}

	return _fetch(state.deleteSegmentsExperienceURL, body);
}

/**
 * @param {{}} layoutData
 * @param {string[]} fragmentEntryLinkIds
 * @param {string} segmentsExperienceId
 * @return {Promise<Response[]>}
 */
function removeFragmentEntryLinks(
	layoutData,
	fragmentEntryLinkIds,
	segmentsExperienceId
) {
	const state = _store.getState();

	return Promise.all(
		fragmentEntryLinkIds.map(fragmentEntryLinkId =>
			_fetch(state.deleteFragmentEntryLinkURL, {
				classNameId: state.classNameId,
				classPK: state.classPK,
				data: JSON.stringify(layoutData),
				fragmentEntryLinkId,
				segmentsExperienceId
			})
		)
	);
}

/**
 * Sets the store
 * @param {object} store
 */
function setStore(store) {
	_store = store;
}

/**
 * @param {string} fragmentEntryLinkId
 * @param {object} editableValues
 * @return {Promise<Response>}
 */
function updateEditableValues(fragmentEntryLinkId, editableValues) {
	const state = _store.getState();

	return _fetch(state.editFragmentEntryLinkURL, {
		editableValues: JSON.stringify(editableValues),
		fragmentEntryLinkId
	});
}

/**
 * @param {Object} layoutData
 * @param {string} segmentsExperienceId
 * @return {Promise<Response>}
 */
function updatePageEditorLayoutData(layoutData, segmentsExperienceId) {
	const state = _store.getState();

	return _fetch(state.updateLayoutPageTemplateDataURL, {
		classNameId: state.classNameId,
		classPK: state.classPK,
		data: JSON.stringify(layoutData),
		segmentsExperienceId
	});
}

export {
	addFragmentEntryLinkComment,
	getExperienceUsedPortletIds,
	removeExperience,
	removeFragmentEntryLinks,
	setStore,
	updateEditableValues,
	updatePageEditorLayoutData
};
