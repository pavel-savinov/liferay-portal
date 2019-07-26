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

package com.liferay.fragment.web.internal.servlet.taglib.clay;

import com.liferay.fragment.web.internal.constants.FragmentTypeConstants;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jürgen Kappler
 */
public class FragmentEntryVerticalCardFactory {

	public FragmentEntryVerticalCardFactory(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		_baseModel = baseModel;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_rowChecker = rowChecker;
	}

	public VerticalCard getVerticalCard(String type) {
		if (Objects.equals(type, FragmentTypeConstants.BASIC_FRAGMENT_TYPE)) {
			return new BasicFragmentEntryVerticalCard(
				_baseModel, _renderRequest, _renderResponse, _rowChecker);
		}

		return null;
	}

	private final BaseModel _baseModel;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final RowChecker _rowChecker;

}