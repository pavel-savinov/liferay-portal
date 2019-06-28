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

package com.liferay.asset.info.display.model.util;

import com.liferay.asset.info.display.model.AssetEntryInfoDisplayClassTypesHelper;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.service.persistence.AssetEntryUsagePersistence;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Locale;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Jürgen Kappler
 */
public class AssetEntryInfoDisplayClassTypesHelperUtil {

	public static List<InfoDisplayField> getClassTypeInfoDisplayFields(
			String className, long classTypeId, Locale locale)
		throws PortalException {

		return getService().getClassTypeInfoDisplayFields(
			className, classTypeId, locale);
	}

	public static List<ClassType> getClassTypes(
			long groupId, String className, Locale locale)
		throws PortalException {

		return getService().getClassTypes(groupId, className, locale);
	}

	public static AssetEntryInfoDisplayClassTypesHelper getService() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<AssetEntryInfoDisplayClassTypesHelper,
		 AssetEntryInfoDisplayClassTypesHelper> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetEntryUsagePersistence.class);

		ServiceTracker
			<AssetEntryInfoDisplayClassTypesHelper,
			 AssetEntryInfoDisplayClassTypesHelper> serviceTracker =
				new ServiceTracker<>(
					bundle.getBundleContext(),
					AssetEntryInfoDisplayClassTypesHelper.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}