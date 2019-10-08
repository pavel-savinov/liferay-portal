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

import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState, useCallback} from 'react';

function LayoutFinder(props) {
	const [keywords, setKeywords] = useState('');
	const [layouts, setLayouts] = useState([]);
	const [loading, setLoading] = useState(false);
	const [maxItems] = useState(10);
	const [totalCount, setTotalCount] = useState(-1);
	const [
		viewInPageAdministrationURL,
		setViewInPageAdministrationURL
	] = useState('');

	const handleFormSubmit = useCallback(event => {
		event.preventDefault();
		event.stopPropagation();
	}, []);

	const updatePageResults = useCallback(
		newKeywords => {
			let promise = Promise.resolve();

			if (!loading && newKeywords.length >= 2) {
				setLoading(true);

				const formData = new FormData();

				formData.append(`${props.namespace}keywords`, newKeywords);

				promise = fetch(props.findLayoutsURL, {
					body: formData,
					method: 'post'
				})
					.then(response => {
						return response.ok
							? response.json()
							: {
									layouts: [],
									totalCount: 0
							  };
					})
					.then(response => {
						setKeywords(newKeywords);
						setLayouts(response.layouts);
						setLoading(false);
						setTotalCount(response.totalCount);
						setViewInPageAdministrationURL(
							`${props.administrationPortletURL}&${props.administrationPortletNamespace}keywords=${keywords}`
						);
					});
			}

			return promise;
		},
		[
			keywords,
			loading,
			props.administrationPortletNamespace,
			props.administrationPortletURL,
			props.findLayoutsURL,
			props.namespace
		]
	);

	const handleSearchInputKeyUp = useCallback(
		event => {
			const newKeywords = event.target.value;

			if (newKeywords.length < 2) {
				setLayouts([]);
				setTotalCount(0);
				setKeywords('');
			} else if (newKeywords !== keywords) {
				setKeywords(keywords);

				updatePageResults(newKeywords);
			}
		},
		[updatePageResults, keywords]
	);

	return (
		<div className="layout-finder">
			<form onSubmit={handleFormSubmit} role="search">
				<label
					className="sr-only"
					htmlFor={`${props.namespace}-layout-finder-page-input`}
				>
					{`${Liferay.Language.get('page-name')}: `}
				</label>

				<input
					autoComplete="off"
					autoFocus
					className="form-control"
					id={`${props.namespace}-layout-finder-page-input`}
					onKeyUp={handleSearchInputKeyUp}
					placeholder={Liferay.Language.get(
						'start-typing-to-find-a-page'
					)}
					type="text"
				/>
			</form>

			{totalCount > 0 && (
				<div>
					<table className="mt-3">
						<tbody>
							{layouts.map(
								(layout, layoutIndex) =>
									layoutIndex < maxItems && (
										<tr key={layout.url}>
											<td>
												<a
													className="d-block p-2 text-truncate"
													href={layout.url}
												>
													{layout.name}
												</a>
											</td>
										</tr>
									)
							)}
						</tbody>
					</table>

					{totalCount > maxItems && (
						<div>
							<div className="mt-3 text-center">
								{totalCount - maxItems}
								{Liferay.Language.get(
									'results-more-refine-keywords'
								)}
							</div>

							<div className="text-center">
								<a
									className="text-primary"
									href={viewInPageAdministrationURL}
								>
									{Liferay.Language.get(
										'view-in-page-administration'
									)}
								</a>
							</div>
						</div>
					)}

					{loading && (
						<span
							aria-hidden="true"
							className="loading-animation"
						></span>
					)}
				</div>
			)}

			{totalCount === 0 && !loading && (
				<div className="taglib-empty-result-message">
					<div className="taglib-empty-result-message-header"></div>

					<div className="text-center text-muted">
						{Liferay.Language.get('page-not-found')}
					</div>
				</div>
			)}
		</div>
	);
}

LayoutFinder.propTypes = {
	administrationPortletNamespace: PropTypes.string,
	administrationPortletURL: PropTypes.string,
	findLayoutsURL: PropTypes.string,
	namespace: PropTypes.string,
	viewInPageAdministrationURL: PropTypes.string
};

export default function(props) {
	return <LayoutFinder {...props} />;
}
