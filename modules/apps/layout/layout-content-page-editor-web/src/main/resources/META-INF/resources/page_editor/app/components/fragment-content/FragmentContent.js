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

import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {updateFragmentEntryLinkContent} from '../../actions/index';
import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/editableFloatingToolbarButtons';
import selectCanUpdateLayoutContent from '../../selectors/selectCanUpdateLayoutContent';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import FragmentService from '../../services/FragmentService';
import {useDispatch, useSelector} from '../../store/index';
import {
	CollectionItemContext,
	useGetFieldValue,
} from '../CollectionItemContext';
import {useFrameContext} from '../Frame';
import Layout from '../Layout';
import UnsafeHTML from '../UnsafeHTML';
import {
	useEditableProcessorUniqueId,
	useSetEditableProcessorUniqueId,
} from './EditableProcessorContext';
import FragmentContentFloatingToolbar from './FragmentContentFloatingToolbar';
import FragmentContentInteractionsFilter from './FragmentContentInteractionsFilter';
import FragmentContentProcessor from './FragmentContentProcessor';
import getAllEditables from './getAllEditables';
import getEditableUniqueId from './getEditableUniqueId';
import resolveEditableValue from './resolveEditableValue';

const FragmentContent = React.forwardRef(
	({fragmentEntryLinkId, itemId}, ref) => {
		const dispatch = useDispatch();
		const isMounted = useIsMounted();
		const editableProcessorUniqueId = useEditableProcessorUniqueId();
		const frameContext = useFrameContext();
		const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
		const canUpdateLayoutContent = useSelector(
			selectCanUpdateLayoutContent
		);

		const getFieldValue = useGetFieldValue();

		const [editables, setEditables] = useState([]);

		const editableElements = useMemo(
			() => editables.map((editable) => editable.element),
			[editables]
		);

		const updateEditables = useCallback(
			(parent) => {
				let updatedEditableValues = [];
				if (isMounted()) {
					updatedEditableValues = parent
						? getAllEditables(parent)
						: [];
					setEditables(updatedEditableValues);
				}

				return updatedEditableValues;
			},
			[isMounted]
		);

		const languageId = useSelector((state) => state.languageId);

		const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

		const defaultContent = useSelector((state) =>
			state.fragmentEntryLinks[fragmentEntryLinkId]
				? state.fragmentEntryLinks[fragmentEntryLinkId].content
				: ''
		);

		const editableValues = useSelector((state) =>
			state.fragmentEntryLinks[fragmentEntryLinkId]
				? state.fragmentEntryLinks[fragmentEntryLinkId].editableValues
				: {}
		);

		const collectionItemContext = useContext(CollectionItemContext);

		const [content, setContent] = useState(defaultContent);

		const collectionItemClassName = collectionItemContext.collectionItem
			? collectionItemContext.collectionItem.className
			: '';

		const collectionItemClassPK = collectionItemContext.collectionItem
			? collectionItemContext.collectionItem.classPK
			: '';

		const collectionItemContent = collectionItemContext.collectionItem
			? collectionItemContext.collectionItemContent
			: '';

		const setCollectionItemContent = collectionItemContext.setCollectionItemContent
			? collectionItemContext.setCollectionItemContent
			: undefined;

		useEffect(() => {
			FragmentService.renderFragmentEntryLinkContent({
				collectionItemClassName,
				collectionItemClassPK,
				fragmentEntryLinkId,
				onNetworkStatus: dispatch,
				segmentsExperienceId,
			}).then(({content}) => {
				if (setCollectionItemContent) {
					setCollectionItemContent(content);

					setContent(content);
				}
				else {
					dispatch(
						updateFragmentEntryLinkContent({
							content,
							editableValues,
							fragmentEntryLinkId,
						})
					);
				}
			});
		}, [
			collectionItemClassName,
			collectionItemClassPK,
			dispatch,
			editableValues,
			fragmentEntryLinkId,
			segmentsExperienceId,
			setCollectionItemContent,
		]);

		useEffect(() => {
			let element = document.createElement('div');
			element.innerHTML = collectionItemContent || defaultContent;

			const updateContent = debounce(() => {
				if (isMounted() && element) {
					setContent(element.innerHTML);
				}
			}, 50);

			if (!editableProcessorUniqueId) {
				const updatedEditables = updateEditables(element);

				updatedEditables.forEach((editable) => {
					resolveEditableValue(
						editableValues,
						editable.editableId,
						editable.editableValueNamespace,
						languageId,
						getFieldValue
					).then(([value, editableConfig]) => {
						editable.processor.render(
							editable.element,
							value,
							editableConfig
						);

						editable.element.classList.add('page-editor__editable');

						updateContent();
					});
				});

				updateContent();
			}

			return () => {
				element = null;
			};
		}, [
			collectionItemContent,
			defaultContent,
			editableProcessorUniqueId,
			editableValues,
			getFieldValue,
			isMounted,
			languageId,
			updateEditables,
		]);

		const getPortals = useCallback(
			(element) =>
				Array.from(element.querySelectorAll('lfr-drop-zone')).map(
					(dropZoneElement) => {
						const mainItemId =
							dropZoneElement.getAttribute('uuid') || '';

						const Component = () =>
							mainItemId ? (
								<Layout mainItemId={mainItemId} />
							) : null;

						Component.displayName = `DropZone(${mainItemId})`;

						return {
							Component,
							element: dropZoneElement,
						};
					}
				),
			[]
		);

		const onFloatingToolbarButtonClick = (buttonId, editableId) => {
			if (buttonId === EDITABLE_FLOATING_TOOLBAR_BUTTONS.edit.id) {
				setEditableProcessorUniqueId(
					getEditableUniqueId(fragmentEntryLinkId, editableId)
				);
			}
		};

		return (
			<>
				<FragmentContentInteractionsFilter
					editableElements={editableElements}
					fragmentEntryLinkId={fragmentEntryLinkId}
					itemId={itemId}
				>
					<UnsafeHTML
						className={classNames('page-editor__fragment-content', {
							'page-editor__fragment-content--portlet-topper-hidden': !canUpdateLayoutContent,
						})}
						contentRef={ref}
						getPortals={getPortals}
						globalContext={frameContext || window}
						markup={content}
						onRender={updateEditables}
					/>
				</FragmentContentInteractionsFilter>

				{canUpdateLayoutContent && (
					<FragmentContentFloatingToolbar
						editables={editables}
						fragmentEntryLinkId={fragmentEntryLinkId}
						onButtonClick={onFloatingToolbarButtonClick}
					/>
				)}

				<FragmentContentProcessor
					editables={editables}
					fragmentEntryLinkId={fragmentEntryLinkId}
				/>
			</>
		);
	}
);

FragmentContent.displayName = 'FragmentContent';

FragmentContent.propTypes = {
	fragmentEntryLinkId: PropTypes.string.isRequired,
	itemId: PropTypes.string.isRequired,
};

export default FragmentContent;
