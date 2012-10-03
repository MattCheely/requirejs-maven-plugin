/*!
 * Alpha 
 */
require(["jquery"], function($) {
	$.fn.alpha = function() {
		return this.append('<p id="alpha">Alpha is Go!</p>');
	};
});
