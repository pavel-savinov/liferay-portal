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

package com.liferay.fragment.internal.instance.lifecycle;

import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {
		"applicationName=Fragment Renderer", "clientId=FragmentRenderer"
	},
	service = PortalInstanceLifecycleListener.class
)
public class OAuth2ProviderShortcutPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		long companyId = company.getCompanyId();

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				companyId, _clientId);

		if (oAuth2Application != null) {
			return;
		}

		User user = _userLocalService.getDefaultUser(company.getCompanyId());

		_oAuth2ApplicationLocalService.addOAuth2Application(
			company.getCompanyId(), user.getUserId(), user.getScreenName(),
			new ArrayList<GrantType>() {
				{
					add(GrantType.REFRESH_TOKEN);
					add(GrantType.RESOURCE_OWNER_PASSWORD);
				}
			},
			user.getUserId(), _clientId, ClientProfile.NATIVE_APPLICATION.id(),
			StringPool.BLANK, null, null, null, 0, _applicationName, null,
			Collections.emptyList(),
			new ArrayList(
				_scopeLocator.getScopeAliases(
					companyId, "liferay-json-web-services")),
			new ServiceContext());
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_clientId = GetterUtil.getString(properties.get("clientId"));
		_applicationName = GetterUtil.getString(
			properties.get("applicationName"));
	}

	private String _applicationName = "Fragment Renderer";
	private String _clientId = "FragmentRenderer";

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private ScopeLocator _scopeLocator;

	@Reference
	private UserLocalService _userLocalService;

}