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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {RowConfigurationPanel} from '../../../../src/main/resources/META-INF/resources/page_editor/app/components/floating-toolbar/RowConfigurationPanel';
import {StoreAPIContextProvider} from '../../../../src/main/resources/META-INF/resources/page_editor/app/store';
import updateItemConfig from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import updateRowColumns from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateRowColumns';

const renderComponent = (dispatch = () => {}) =>
	render(
		<StoreAPIContextProvider
			dispatch={dispatch}
			getState={() => ({
				segmentsExperienceId: '0',
				selectedViewportSize: 'tablet',
			})}
		>
			<RowConfigurationPanel
				item={{
					children: [],
					config: {gutters: true},
					itemId: '0',
					parentId: '',
					type: '',
				}}
			/>
		</StoreAPIContextProvider>
	);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({config: {availableViewportSizes: {tablet: {label: 'Tablet'}}}})
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateRowColumns',
	() => jest.fn()
);

describe('RowConfigurationPanel', () => {
	afterEach(() => {
		cleanup();
		updateItemConfig.mockClear();
		updateRowColumns.mockClear();
	});

	it('allows changing the number of columns of a row', async () => {
		const {getByLabelText} = renderComponent();
		const input = getByLabelText('number-of-columns');

		await fireEvent.change(input, {
			target: {value: '6'},
		});

		expect(updateRowColumns).toHaveBeenCalledWith({
			itemId: '0',
			numberOfColumns: 6,
			segmentsExperienceId: '0',
			viewportSizeId: 'tablet',
		});
	});

	it('allows changing the gutter', async () => {
		const {getByLabelText} = renderComponent();
		const input = getByLabelText('show-gutter');

		await fireEvent.click(input);

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {gutters: false},
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});
});
