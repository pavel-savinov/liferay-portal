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

package com.liferay.layout.page.template.internal.importer.helper;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.model.Layout;

/**
 * @author Jürgen Kappler
 */
public interface LayoutStructureItemHelper {

	public LayoutStructureItem addLayoutStructureItem(
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			FragmentEntryValidator fragmentEntryValidator, Layout layout,
			LayoutStructure layoutStructure, PageElement pageElement,
			String parentItemId, int position)
		throws Exception;

}