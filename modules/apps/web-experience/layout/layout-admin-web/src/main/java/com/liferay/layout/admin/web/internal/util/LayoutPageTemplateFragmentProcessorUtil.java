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

package com.liferay.layout.admin.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateFragmentProcessorUtil {

	public static String processFragmentEntryContent(
			String content, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		TemplateResource templateResource = new StringTemplateResource(
			"template_id", content);

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, templateResource, false);

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(
				TemplateConstants.LANG_TYPE_FTL);

		templateManager.addTaglibSupport(template, request, response);
		templateManager.addTaglibTheme(
			template, "taglibLiferay", request, response);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.put(TemplateConstants.WRITER, unsyncStringWriter);

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

}