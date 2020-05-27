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

import {SELECT_SEGMENTS_EXPERIENCE} from '../../../plugins/experience/actions';
import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_ITEM,
	DELETE_ITEM,
	DUPLICATE_ITEM,
	MOVE_ITEM,
	UPDATE_COL_SIZE,
	UPDATE_EDITABLE_VALUES,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_ITEM_CONFIG,
	UPDATE_LANGUAGE_ID,
} from '../../actions/types';
import getLayoutDataItemLabel from '../../utils/getLayoutDataItemLabel';
import * as undoDelete from './undoDelete';
import * as undoDuplicateItem from './undoDuplicateItem';
import * as undoEditableValuesAction from './undoEditableValuesAction';
import * as undoFragmentConfiguration from './undoFragmentConfiguration';
import * as undoFragmentEntryLinks from './undoFragmentEntryLinks';
import * as undoLayoutDataAction from './undoLayoutDataAction';
import * as undoSelectExperience from './undoSelectExperience';
import * as undoUpdateLanguage from './undoUpdateLanguage';

const UNDO_ACTIONS = {
	[ADD_FRAGMENT_ENTRY_LINKS]: undoFragmentEntryLinks,
	[ADD_ITEM]: undoLayoutDataAction,
	[DELETE_ITEM]: undoDelete,
	[DUPLICATE_ITEM]: undoDuplicateItem,
	[MOVE_ITEM]: undoLayoutDataAction,
	[SELECT_SEGMENTS_EXPERIENCE]: undoSelectExperience,
	[UPDATE_COL_SIZE]: undoLayoutDataAction,
	[UPDATE_EDITABLE_VALUES]: undoEditableValuesAction,
	[UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION]: undoFragmentConfiguration,
	[UPDATE_ITEM_CONFIG]: undoLayoutDataAction,
	[UPDATE_LANGUAGE_ID]: undoUpdateLanguage,
};

export function canUndoAction(action) {
	return Object.keys(UNDO_ACTIONS).includes(action.type) && !action.isUndo;
}

export function getDerivedStateForUndo({action, state, type}) {
	const undoAction = UNDO_ACTIONS[type];

	const layoutData = action.layoutData || state.layoutData;

	const fragmentEntryLinks = action.fragmentEntryLinks
		? Object.fromEntries(
				action.fragmentEntryLinks.map((fragmentEntryLink) => [
					fragmentEntryLink.fragmentEntryLinkId,
					fragmentEntryLink,
				])
		  )
		: state.fragmentEntryLinks;

	const item =
		layoutData.items[action.itemId] ||
		Object.values(state.layoutData.items).find(
			(item) =>
				item.config.fragmentEntryLinkId === action.fragmentEntryLinkId
		);

	return {
		...undoAction.getDerivedStateForUndo({action, state}),
		itemName: getLayoutDataItemLabel(item, fragmentEntryLinks),
		type,
	};
}

export function undoAction({action, store}) {
	const {type} = action;

	const undoAction = UNDO_ACTIONS[type];

	return undoAction.undoAction({action, store});
}
