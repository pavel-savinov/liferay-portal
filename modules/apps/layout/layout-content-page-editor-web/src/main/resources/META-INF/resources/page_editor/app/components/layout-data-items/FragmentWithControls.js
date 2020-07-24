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
import React, {useEffect, useMemo, useState} from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {FRAGMENT_CONFIGURATION_ROLES} from '../../config/constants/fragmentConfigurationRoles';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectShowFloatingToolbar from '../../selectors/selectShowFloatingToolbar';
import {useSelector, useSelectorCallback} from '../../store/index';
import loadBackgroundImage from '../../utils/loadBackgroundImage';
import {useIsActive} from '../Controls';
import Topper from '../Topper';
import FloatingToolbar from '../floating-toolbar/FloatingToolbar';
import FragmentContent from '../fragment-content/FragmentContent';

const FragmentWithControls = React.forwardRef(({item, layoutData}, ref) => {
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);
	const isActive = useIsActive();
	const [setRef, itemElement] = useSetRef(ref);
	const showFloatingToolbar = useSelector(selectShowFloatingToolbar);

	const floatingToolbarButtons = useMemo(() => {
		const buttons = [];

		const fieldSets = fragmentEntryLink.configuration?.fieldSets;

		const stylesFieldSets = fieldSets?.filter(
			(fieldSet) =>
				fieldSet.configurationRole ===
				FRAGMENT_CONFIGURATION_ROLES.style
		);

		const configFieldSets = fieldSets?.filter(
			(fieldSet) =>
				fieldSet.configurationRole !==
				FRAGMENT_CONFIGURATION_ROLES.style
		);

		if (canUpdateItemConfiguration && stylesFieldSets?.length) {
			buttons.push(LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.fragmentStyles);
		}

		if (canUpdateItemConfiguration && configFieldSets?.length) {
			buttons.push(
				LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.fragmentConfiguration
			);
		}

		return buttons;
	}, [canUpdateItemConfiguration, fragmentEntryLink.configuration]);

	const {
		backgroundColor,
		backgroundImage,
		borderColor,
		borderRadius,
		borderWidth,
		fontFamily,
		fontSize,
		fontWeight,
		height,
		marginBottom,
		marginLeft,
		marginRight,
		marginTop,
		maxHeight,
		maxWidth,
		minHeight,
		minWidth,
		overflow,
		paddingBottom,
		paddingLeft,
		paddingRight,
		paddingTop,
		shadow,
		textAlign,
		textColor,
		width,
	} = item.config.styles;

	const [backgroundImageValue, setBackgroundImageValue] = useState('');

	useEffect(() => {
		loadBackgroundImage(backgroundImage).then(setBackgroundImageValue);
	}, [backgroundImage]);

	const style = {};

	if (backgroundImageValue) {
		style.backgroundImage = `url(${backgroundImageValue})`;
		style.backgroundPosition = '50% 50%';
		style.backgroundRepeat = 'no-repeat';
		style.backgroundSize = 'cover';
	}

	if (fontSize) {
		style.fontSize = fontSize;
	}

	style.borderWidth = `${borderWidth}px`;
	style.maxHeight = maxHeight;
	style.maxWidth = maxWidth;
	style.minHeight = minHeight && minHeight !== 'none';
	style.minWidth = minWidth && minWidth !== 'none';
	style.overflow = overflow;

	return (
		<Topper item={item} itemElement={itemElement} layoutData={layoutData}>
			<>
				{isActive(item.itemId) && showFloatingToolbar && (
					<FloatingToolbar
						buttons={floatingToolbarButtons}
						item={item}
						itemElement={itemElement}
					/>
				)}

				<div
					className={classNames(
						fontWeight,
						height,
						marginBottom,
						marginLeft,
						marginRight,
						marginTop,
						paddingBottom,
						paddingLeft,
						paddingRight,
						paddingTop,
						shadow,
						width,
						{
							[`bg-${backgroundColor?.cssClass}`]: backgroundColor,
							[`border-${borderColor?.cssClass}`]: borderColor,
							[borderRadius]: !!borderRadius,
							[fontFamily]: fontFamily !== 'default',
							'no-gutters': !item.config.gutters,
							[textAlign]: textAlign !== 'none',
							[`text-${textColor?.cssClass}`]: textColor,
						}
					)}
					style={style}
				>
					<FragmentContent
						elementRef={setRef}
						fragmentEntryLinkId={
							fragmentEntryLink.fragmentEntryLinkId
						}
						itemId={item.itemId}
					/>
				</div>
			</>
		</Topper>
	);
});

FragmentWithControls.displayName = 'FragmentWithControls';

FragmentWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default FragmentWithControls;
