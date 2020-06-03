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

import updateFragmentEntryLinkConfiguration from '../actions/updateFragmentEntryLinkConfiguration';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import FragmentService from '../services/FragmentService';

export default function updateFragmentConfiguration({
	configurationValues,
	fragmentEntryLink,
	isUndo = false,
	selectedViewportSize,
}) {
	const {editableValues, fragmentEntryLinkId} = fragmentEntryLink;

	const nextEditableValues = {
		...editableValues,
		[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]: {
			...editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR],
			...(selectedViewportSize === VIEWPORT_SIZES.desktop
				? configurationValues
				: {[selectedViewportSize]: configurationValues}),
		},
	};

	return (dispatch) => {
		return FragmentService.updateConfigurationValues({
			configurationValues: nextEditableValues,
			fragmentEntryLinkId,
			onNetworkStatus: dispatch,
			selectedViewportSize,
		}).then(({fragmentEntryLink, layoutData}) => {
			dispatch(
				updateFragmentEntryLinkConfiguration({
					fragmentEntryLink,
					fragmentEntryLinkId,
					isUndo,
					layoutData,
				})
			);
		});
	};
}
