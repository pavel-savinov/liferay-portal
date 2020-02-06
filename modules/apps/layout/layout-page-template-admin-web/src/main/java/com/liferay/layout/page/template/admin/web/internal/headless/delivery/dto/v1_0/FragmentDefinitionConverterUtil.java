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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.delivery.dto.v1_0.FragmentDefinition;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = FragmentDefinitionConverterUtil.class)
public class FragmentDefinitionConverterUtil {

	public static FragmentDefinition toFragmentDefinition(
		FragmentLayoutStructureItem fragmentLayoutStructureItem) {

		return new FragmentDefinition() {
			{
				fragmentCollectionName = null;
				fragmentName = null;
			}
		};
	}

	@Reference(unbind = "-")
	protected void setFragmentCollectionContributorTracker(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker) {

		_fragmentCollectionContributorTracker =
			fragmentCollectionContributorTracker;
	}

	@Reference(unbind = "-")
	protected void setFragmentCollectionLocalService(
		FragmentCollectionLocalService fragmentCollectionLocalService) {

		_fragmentCollectionLocalService = fragmentCollectionLocalService;
	}

	@Reference(unbind = "-")
	protected void setFragmentEntryLinkLocalService(
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService) {

		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setFragmentEntryLocalService(
		FragmentEntryLocalService fragmentEntryLocalService) {

		_fragmentEntryLocalService = fragmentEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setFragmentRendererTracker(
		FragmentRendererTracker fragmentRendererTracker) {

		_fragmentRendererTracker = fragmentRendererTracker;
	}

	private static FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private static FragmentCollectionLocalService
		_fragmentCollectionLocalService;
	private static FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private static FragmentEntryLocalService _fragmentEntryLocalService;
	private static FragmentRendererTracker _fragmentRendererTracker;

}