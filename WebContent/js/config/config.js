require.config({
	baseUrl: "js",
	paths: {
		knockout: "lib/knockout-3.2.0",
		views: "../views",
		viewmodels: "../viewmodels",
		binder: "config/binder",
		globals: "config/globals"
	}
})

require(["config/ko_config"], function(dep){});