require([ "config/config" ], function() {
	require([ "binder", "globals" ], function(loader) {
		var navigationHandler = function() {
			Sammy(function() {
				// Catch all possible patterns of hash.
				this.get(/\#(.*)/, function() {
					var allParams = this.params.splat[0].split("/");
					var url = allParams[0].toLowerCase();
					switch (url) {
					case "inventorydata":
						g_baseviewmodel.showInventoryDataPage();
						break;
					case "items":
						g_baseviewmodel.showItemDataPage();
						break;
					case "structure":
						g_baseviewmodel.showItemStructurePage();
						break;
					case "attachments":
						g_baseviewmodel.showItemAttachmentsPage();
						break;
					case "aml":
						g_baseviewmodel.showItemAmlPage();
						break;
					case "test":
						g_baseviewmodel.showTestDataPage();
						break;
					default:
						alert("The URL doesn't exist.");
					}
				});

				// If no #route is given then route to main page
				this.get("", function(url) {
					if (g_baseviewmodel)
						g_baseviewmodel.showLandingPage();
				});
			}).run();
		};

		loader.loadBaseView(false, navigationHandler);
	});
});