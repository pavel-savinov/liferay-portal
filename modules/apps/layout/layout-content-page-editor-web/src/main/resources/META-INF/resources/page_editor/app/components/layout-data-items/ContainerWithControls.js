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

import {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import React, {useState} from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import {config} from '../../config/index';
import selectShowLayoutItemTopper from '../../selectors/selectShowLayoutItemTopper';
import {useDispatch, useSelector} from '../../store/index';
import duplicateItem from '../../thunks/duplicateItem';
import {useSelectItem} from '../Controls';
import Topper from '../Topper';
import FloatingToolbar from '../floating-toolbar/FloatingToolbar';
import SaveFragmentCompositionModal from '../floating-toolbar/SaveFragmentCompositionModal';
import Container from './Container';
import hasDropZoneChild from './hasDropZoneChild';

const ContainerWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const dispatch = useDispatch();
		const isMounted = useIsMounted();
		const [
			openSaveFragmentCompositionModal,
			setOpenSaveFragmentCompositionModal,
		] = useState(false);
		const {observer, onClose} = useModal({
			onClose: () => {
				if (isMounted()) {
					setOpenSaveFragmentCompositionModal(false);
				}
			},
		});

		const segmentsExperienceId = useSelector(
			state => state.segmentsExperienceId
		);
		const selectItem = useSelectItem();
		const showLayoutItemTopper = useSelector(selectShowLayoutItemTopper);

		const handleButtonClick = id => {
			if (id === LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem.id) {
				dispatch(
					duplicateItem({
						itemId: item.itemId,
						selectItem,
						store: {segmentsExperienceId},
					})
				);
			}
			else if (
				id ===
				LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.saveFragmentComposition.id
			) {
				setOpenSaveFragmentCompositionModal(true);
			}
		};

		const buttons = [];

		if (!hasDropZoneChild(item, layoutData)) {
			buttons.push(LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem);

			if (config.fragmentCompositionsEnabled) {
				buttons.push(
					LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.saveFragmentComposition
				);
			}
		}

		buttons.push(
			LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.containerConfiguration
		);

		const content = (
			<Container
				className={classNames('page-editor__container', {
					empty: !item.children.length,
				})}
				item={item}
				ref={ref}
			>
				<FloatingToolbar
					buttons={buttons}
					item={item}
					itemRef={ref}
					onButtonClick={handleButtonClick}
				/>

				{children}

				{openSaveFragmentCompositionModal && (
					<SaveFragmentCompositionModal
						errorMessage={''}
						itemId={item.itemId}
						observer={observer}
						onClose={onClose}
						onErrorDismiss={() => true}
					/>
				)}
			</Container>
		);

		return showLayoutItemTopper ? (
			<Topper item={item} itemRef={ref} layoutData={layoutData}>
				{() => content}
			</Topper>
		) : (
			content
		);
	}
);

ContainerWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default ContainerWithControls;
