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

export const AVAILABLE_VIEWPORT_SIZES = {
	availableViewportSizes: {
		desktop: {
			icon: 'desktop',
			label: Liferay.Language.get('desktop'),
			maxWidth: 992,
			minWidth: 960,
			sizeId: 'desktop',
		},
		landscapeMobile: {
			icon: 'mobile-landscape',
			label: Liferay.Language.get('mobile-landscape'),
			maxWidth: 576,
			minWidth: 540,
			sizeId: 'landscapeMobile',
		},
		portraitMobile: {
			icon: 'mobile-portrait',
			label: Liferay.Language.get('mobile-portrait'),
			maxWidth: 540,
			minWidth: 0,
			sizeId: 'portraitMobile',
		},
		tablet: {
			icon: 'tablet-portrait',
			label: Liferay.Language.get('tablet'),
			maxWidth: 768,
			minWidth: 720,
			sizeId: 'tablet',
		},
	},
};
