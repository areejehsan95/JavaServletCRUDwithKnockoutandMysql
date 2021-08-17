define(["globals"], function() {
	function isViewAlreadyLoaded(viewFileName) {
		if(g_currentpage)	
			if(g_currentpage.viewFileName == viewFileName)
				return true;
		return false
	}
	
	function callExitFunctionOfCurrentPage() {
		if(g_currentpage && g_currentpage.viewModel && g_currentpage.viewModel.onExit)
				g_currentpage.viewModel.onExit()
	}
	
	function loadBaseView(loadHomepage, callback) {
		require(["viewmodels/base_viewmodel"], function(vm) {
			g_baseviewmodel = vm.getInstance();
			ko.applyBindings(g_baseviewmodel)

			if(callback)
				callback()
			
			if(loadHomepage)
				g_baseviewmodel.showLandingPage()
		});
	}

	function loadView(viewFileName, viewModelFileName, containerToBind, templateContainer, init) {
		if(!isViewAlreadyLoaded(viewFileName) || init.popupId) {
			callExitFunctionOfCurrentPage();				

			templateContainer = templateContainer?templateContainer:"pageContainer"
			containerToBind = containerToBind?containerToBind:templateContainer

			var viewTemplateFile = "text!views/"+viewFileName+".html"
			var viewModelToLoad = "viewmodels/"+viewModelFileName

			require([viewTemplateFile, viewModelToLoad], function(view, vm) {
				$("#"+templateContainer).html(view)
				var pageVM = vm.getInstance()
				ko.applyBindings(pageVM, document.getElementById(containerToBind))

				//update current page info
				g_currentpage = {
					viewFileName: viewFileName,
					viewModel: pageVM
				}

				if(init)
					pageVM.onLoad(init);
				else
					pageVM.onLoad();
			})
		}
	}

	function loadViewInPopup(viewFileName, viewModelFileName, containerToBind, popupwidth, popupheight, triggeringVM, index) {
		var viewFileToLoad = "text!views/"+viewFileName+".html"
		var viewModelToLoad = "viewmodels/"+viewModelFileName
		
		require([viewFileToLoad, viewModelToLoad], function(view, vm) {
			TINY.box.show({
				html : view,
				width : popupwidth,
				height : popupheight,
				openjs : function() {
					var popupvm = vm.getInstance()
					ko.applyBindings(popupvm, document.getElementById(containerToBind))
					popupvm.onLoad(triggeringVM, index)
				}
			});
		});
	}
	
	function bindVMWithContainer(viewModelFileName, containerToBind) {
		var viewModelToLoad = "viewmodels/"+viewModelFileName
		require([viewModelToLoad], function(vm) {
			var viewmodel = vm.getInstance()
			ko.applyBindings(viewmodel, document.getElementById(containerToBind))
		});
	}

	return {
		loadView: loadView,
		loadBaseView: loadBaseView,
		loadViewInPopup: loadViewInPopup,
		bindVMWithContainer: bindVMWithContainer
	}
});