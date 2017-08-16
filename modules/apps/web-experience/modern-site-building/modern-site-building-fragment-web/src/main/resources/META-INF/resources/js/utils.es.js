/**
 * Debounces the given function, so it is only executed after
 * a given amount of time. If multiple calls are made to the
 * function, only the last of them will be executed.
 * @param {Function} fn Function to be executed
 * @param {number} time Amount of time to debounce
 * @return {Function} Debounced version of the function
 */
function debounce(fn, time) {
	let timeoutId;
	return (...args) => {
		clearTimeout(timeoutId);
		timeoutId = setTimeout(() => fn(...args), time);
	};
}

/**
 * Returns a text with the html entities (eg. &bnsp;)
 * transformed into unencoded characters.
 * @param  {string} text Original text
 * @return {string} Transformed text
 */
function decodeHTMLEntities(text) {
	const element = document.createElement('div');
element.innerHTML = text;
text = element.textContent;
return element.textContent;
}

export {
	debounce,
	decodeHTMLEntities,
};