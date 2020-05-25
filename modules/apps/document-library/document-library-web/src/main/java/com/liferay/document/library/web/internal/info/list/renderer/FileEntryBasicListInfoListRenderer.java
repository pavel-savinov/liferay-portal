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

package com.liferay.document.library.web.internal.info.list.renderer;

import com.liferay.document.library.web.internal.info.item.renderer.FileEntryTitleInfoItemRenderer;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListItemStyle;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererContext;
import com.liferay.info.taglib.list.renderer.BasicListInfoListItemStyle;
import com.liferay.info.taglib.servlet.taglib.InfoListBasicListTag;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = InfoListRenderer.class)
public class FileEntryBasicListInfoListRenderer
	implements InfoListRenderer<FileEntry> {

	@Override
	public List<InfoListItemStyle> getAvailableInfoListItemStyles() {
		return Stream.of(
			BasicListInfoListItemStyle.values()
		).map(
			basicListInfoListItemStyle ->
				basicListInfoListItemStyle.getInfoListItemStyle()
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "basic-list");
	}

	@Override
	public void render(
		List<FileEntry> fileEntries, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		render(
			fileEntries,
			new DefaultInfoListRendererContext(
				httpServletRequest, httpServletResponse));
	}

	@Override
	public void render(
		List<FileEntry> fileEntries,
		InfoListRendererContext infoListRendererContext) {

		InfoListBasicListTag infoListBasicListTag = new InfoListBasicListTag();

		infoListBasicListTag.setInfoListObjects(fileEntries);
		infoListBasicListTag.setItemRendererKey(
			FileEntryTitleInfoItemRenderer.class.getName());

		Optional<String> infoListItemStyleKeyOptional =
			infoListRendererContext.getListItemStyleKeyOptional();

		if (infoListItemStyleKeyOptional.isPresent()) {
			infoListBasicListTag.setListItemStyleKey(
				infoListItemStyleKeyOptional.get());
		}

		try {
			infoListBasicListTag.doTag(
				infoListRendererContext.getHttpServletRequest(),
				infoListRendererContext.getHttpServletResponse());
		}
		catch (Exception exception) {
			_log.error("Unable to render file entries list", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryBasicListInfoListRenderer.class);

}