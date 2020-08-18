/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.display.context;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.data.provider.AnalyticsReportsDataProvider;
import com.liferay.analytics.reports.web.internal.model.TimeRange;
import com.liferay.analytics.reports.web.internal.model.TimeSpan;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

/**
 * @author David Arques
 * @author Sarai Díaz
 */
public class AnalyticsReportsDisplayContext<T> {

	public AnalyticsReportsDisplayContext(
		AnalyticsReportsDataProvider analyticsReportsDataProvider,
		AnalyticsReportsInfoItem<T> analyticsReportsInfoItem,
		T analyticsReportsInfoItemObject, String canonicalURL,
		LayoutDisplayPageObjectProvider<T> layoutDisplayPageObjectProvider,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse, ResourceBundle resourceBundle,
		ThemeDisplay themeDisplay, User user) {

		_analyticsReportsDataProvider = analyticsReportsDataProvider;
		_analyticsReportsInfoItem = analyticsReportsInfoItem;
		_analyticsReportsInfoItemObject = analyticsReportsInfoItemObject;
		_canonicalURL = canonicalURL;
		_layoutDisplayPageObjectProvider = layoutDisplayPageObjectProvider;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_resourceBundle = resourceBundle;
		_themeDisplay = themeDisplay;
		_user = user;

		_validAnalyticsConnection =
			_analyticsReportsDataProvider.isValidAnalyticsConnection(
				_themeDisplay.getCompanyId());
	}

	public Map<String, Object> getData() {
		if (_data != null) {
			return _data;
		}

		_data = HashMapBuilder.<String, Object>put(
			"context", _getContext()
		).put(
			"props", _getProps()
		).build();

		return _data;
	}

	public String getLiferayAnalyticsURL(long companyId) {
		return PrefsPropsUtil.getString(companyId, "liferayAnalyticsURL");
	}

	private Map<String, Object> _getContext() {
		return HashMapBuilder.<String, Object>put(
			"defaultTimeRange",
			() -> {
				TimeSpan defaultTimeSpan = TimeSpan.of(
					TimeSpan.defaultTimeSpanKey());

				TimeRange defaultTimeRange = defaultTimeSpan.toTimeRange(0);

				return HashMapBuilder.<String, Object>put(
					"endDate",
					DateTimeFormatter.ISO_DATE.format(
						defaultTimeRange.getEndLocalDate())
				).put(
					"startDate",
					DateTimeFormatter.ISO_DATE.format(
						defaultTimeRange.getStartLocalDate())
				).build();
			}
		).put(
			"defaultTimeSpanKey", TimeSpan.defaultTimeSpanKey()
		).put(
			"endpoints",
			HashMapBuilder.<String, Object>put(
				"getAnalyticsReportsHistoricalReadsURL",
				() -> String.valueOf(
					_getResourceURL("/analytics_reports/get_historical_reads"))
			).put(
				"getAnalyticsReportsHistoricalViewsURL",
				() -> String.valueOf(
					_getResourceURL("/analytics_reports/get_historical_views"))
			).put(
				"getAnalyticsReportsTotalReadsURL",
				() -> String.valueOf(
					_getResourceURL("/analytics_reports/get_total_reads"))
			).put(
				"getAnalyticsReportsTotalViewsURL",
				() -> String.valueOf(
					_getResourceURL("/analytics_reports/get_total_views"))
			).build()
		).put(
			"languageTag",
			() -> {
				Locale locale = _getLocale();

				return locale.toLanguageTag();
			}
		).put(
			"namespace",
			_portal.getPortletNamespace(
				AnalyticsReportsPortletKeys.ANALYTICS_REPORTS)
		).put(
			"page",
			HashMapBuilder.<String, Object>put(
				"plid",
				() -> {
					Layout layout = _themeDisplay.getLayout();

					return layout.getPlid();
				}
			).build()
		).put(
			"timeSpans", _getTimeSpansJSONArray()
		).put(
			"validAnalyticsConnection", _validAnalyticsConnection
		).build();
	}

	private Locale _getLocale() {
		return LocaleUtil.fromLanguageId(
			ParamUtil.getString(
				_portal.getOriginalServletRequest(
					_portal.getHttpServletRequest(_renderRequest)),
				"languageId", _themeDisplay.getLanguageId()));
	}

	private Map<String, Object> _getProps() {
		return HashMapBuilder.<String, Object>put(
			"authorName",
			_analyticsReportsInfoItem.getAuthorName(
				_analyticsReportsInfoItemObject)
		).put(
			"authorPortraitURL",
			() -> Optional.ofNullable(
				_user
			).filter(
				user -> _user.getPortraitId() > 0
			).map(
				user -> {
					try {
						return user.getPortraitURL(_themeDisplay);
					}
					catch (PortalException portalException) {
						_log.error(portalException, portalException);

						return StringPool.BLANK;
					}
				}
			).orElse(
				StringPool.BLANK
			)
		).put(
			"authorUserId",
			_analyticsReportsInfoItem.getAuthorUserId(
				_analyticsReportsInfoItemObject)
		).put(
			"publishDate",
			() -> _analyticsReportsInfoItem.getPublishDate(
				_analyticsReportsInfoItemObject)
		).put(
			"title",
			_analyticsReportsInfoItem.getTitle(
				_analyticsReportsInfoItemObject, _getLocale())
		).put(
			"trafficSources", _getTrafficSourcesJSONArray()
		).put(
			"viewURLs",
			_getViewURLsJSONArray(
				_analyticsReportsInfoItem, _analyticsReportsInfoItemObject)
		).build();
	}

	private ResourceURL _getResourceURL(String resourceID) {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setParameter(
			"classNameId",
			String.valueOf(_layoutDisplayPageObjectProvider.getClassNameId()));
		resourceURL.setParameter(
			"classPK",
			String.valueOf(_layoutDisplayPageObjectProvider.getClassPK()));

		resourceURL.setResourceID(resourceID);

		return resourceURL;
	}

	private JSONArray _getTimeSpansJSONArray() {
		JSONArray timeSpansJSONArray = JSONFactoryUtil.createJSONArray();

		Stream<TimeSpan> stream = Arrays.stream(TimeSpan.values());

		stream.filter(
			timeSpan -> timeSpan != TimeSpan.TODAY
		).sorted(
			Comparator.comparingInt(TimeSpan::getDays)
		).forEach(
			timeSpan -> timeSpansJSONArray.put(
				JSONUtil.put(
					"key", timeSpan.getKey()
				).put(
					"label",
					ResourceBundleUtil.getString(
						_resourceBundle, timeSpan.getKey())
				))
		);

		return timeSpansJSONArray;
	}

	private List<TrafficSource> _getTrafficSources() {
		List<TrafficSource> trafficSources = Collections.emptyList();

		if (_validAnalyticsConnection) {
			try {
				trafficSources =
					_analyticsReportsDataProvider.getTrafficSources(
						_themeDisplay.getCompanyId(), _canonicalURL);
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}

		return trafficSources;
	}

	private JSONArray _getTrafficSourcesJSONArray() {
		JSONArray trafficSourcesJSONArray = JSONFactoryUtil.createJSONArray();

		Map<String, String> helpMessageMap = HashMapBuilder.put(
			"organic",
			ResourceBundleUtil.getString(
				_resourceBundle,
				"this-number-refers-to-the-volume-of-people-that-find-your-" +
					"page-through-a-search-engine")
		).put(
			"paid",
			ResourceBundleUtil.getString(
				_resourceBundle,
				"this-number-refers-to-the-volume-of-people-that-find-your-" +
					"page-through-paid-keywords")
		).build();

		Map<String, String> titleMap = HashMapBuilder.put(
			"organic", ResourceBundleUtil.getString(_resourceBundle, "organic")
		).put(
			"paid", ResourceBundleUtil.getString(_resourceBundle, "paid")
		).build();

		List<TrafficSource> trafficSources = _getTrafficSources();

		titleMap.forEach(
			(name, title) -> {
				Stream<TrafficSource> stream = trafficSources.stream();

				trafficSourcesJSONArray.put(
					stream.filter(
						trafficSource -> Objects.equals(
							name, trafficSource.getName())
					).findFirst(
					).map(
						trafficSource -> trafficSource.toJSONObject(
							helpMessageMap.get(name), _getLocale(), title)
					).orElse(
						JSONUtil.put(
							"helpMessage", helpMessageMap.get(name)
						).put(
							"name", name
						).put(
							"title", title
						)
					));
			});

		return trafficSourcesJSONArray;
	}

	private String _getViewURL(Locale locale) {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("languageId", LocaleUtil.toLanguageId(locale));

		return String.valueOf(portletURL);
	}

	private <T> JSONArray _getViewURLsJSONArray(
		AnalyticsReportsInfoItem<T> analyticsReportsInfoItem, T model) {

		List<Locale> locales = analyticsReportsInfoItem.getAvailableLocales(
			model);

		Stream<Locale> stream = locales.stream();

		return JSONUtil.putAll(
			stream.map(
				locale -> JSONUtil.put(
					"default",
					Objects.equals(
						locale,
						analyticsReportsInfoItem.getDefaultLocale(model))
				).put(
					"languageId", LocaleUtil.toBCP47LanguageId(locale)
				).put(
					"viewURL", _getViewURL(locale)
				)
			).toArray());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsReportsDisplayContext.class);

	private final AnalyticsReportsDataProvider _analyticsReportsDataProvider;
	private final AnalyticsReportsInfoItem<T> _analyticsReportsInfoItem;
	private final T _analyticsReportsInfoItemObject;
	private final String _canonicalURL;
	private Map<String, Object> _data;
	private final LayoutDisplayPageObjectProvider<T>
		_layoutDisplayPageObjectProvider;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ResourceBundle _resourceBundle;
	private final ThemeDisplay _themeDisplay;
	private final User _user;
	private final boolean _validAnalyticsConnection;

}