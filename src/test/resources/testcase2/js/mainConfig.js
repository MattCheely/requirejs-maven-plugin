require.config({
	paths: {
		"jquery": "lib/jquery-1.7.2",
		"json": "lib/json2",
		"underscore": "lib/underscore-1.3.3",
		"backbone": "lib/backbone-0.9.2",
		"dust": "lib/dust-core-1.1.0",
		"bootstrap": "lib/bootstrap",
		"base": "core/base"
	},
	useStrict: true,
	shim: {
		"dust": {
			exports: function() {
				return window.dust;
			}
		},
		"underscore": {
			exports: function() {
				return window._.noConflict();
			}
		},
		"backbone": {
			deps: ["underscore", "jquery"],
			exports: function() {
				Backbone.setDomLibrary(window.jQuery.noConflict());
				return window.Backbone.noConflict();
			}
		},
		"bootstrap": {
			deps: ["jquery"]
		}
	}

})