/*!
 * Beta 
 */
require(["jquery"], function($) {
	$.fn.beta = function() {
		return this.append('<p id="beta">Beta is Go!</p>');
	};
});
