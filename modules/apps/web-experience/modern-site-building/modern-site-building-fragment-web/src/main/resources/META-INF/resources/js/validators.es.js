/**
 * Returns a function that checks if the given value is inside the given array.
 * @param  {Array} values Group of values that will be used for checking
 * @return {Function} Function checker that receives a value and returns a boolean
 */
function isOneOf(values) {
	return (value) => values.indexOf(value) !== -1;
}

export {
	isOneOf,
};