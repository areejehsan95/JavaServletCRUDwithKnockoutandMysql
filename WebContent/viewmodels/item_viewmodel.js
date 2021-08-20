define([ "binder" ], function(_binder) {
	var binder = _binder;

	function Item(data) {
	    this.id = ko.observable(data.id);
	    this.Class = ko.observable(data.Class);
	    this.description = ko.observable(data.description);
	}

	function actionVM() {
		var self = this;

		self.errors = ko.observableArray();
		self.validationCompleted = ko.observable(false);

		self.onLoad = function() {
	/*		var req = new Object();
			req.changeNotice = System.getParameterByName("changeNotice");
			req.organization = System.getParameterByName("organization");
			req.jwt = System.getParameterByName("jwt");
			req = "params=" + encodeURIComponent(JSON.stringify(req));

			System.sendPostRequest("Validate", req, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}

					self.validationCompleted(true);
				} else {
					var message = "Error: ";
					if (response.message)
						message += response.message;
					g_baseviewmodel.setFootNote("error", response.message);
				}
			}, "Loading Items...");*/
		};

		self.closeWindow = function() {
			window.close();
		}
		
		self.itemsURI = 'http://localhost:8080/TemplateProject/itemservlet';
		self.attachmentsURI = 'http://localhost:8080/TemplateProject/attachmentservlet';
        
        self.ajax = function(uri, method, data) {
            var request = {
                url: uri,
                type: method,
                contentType: "application/json",
                accepts: "application/json",
                cache: false,
                dataType: 'json',
                data: JSON.stringify(data),
                error: function(jqXHR) {
                    console.log("ajax error " + jqXHR.status);
                }
            };
            return $.ajax(request);
        }

        self.items = ko.observableArray([]);
		self.newItemClass = ko.observable();
		self.newItemdescription = ko.observable();

		self.editItemClass = ko.observable();
		self.editItemdescription = ko.observable();
		
		 self.loadData = function() {
            self.ajax(self.itemsURI, 'GET').done(function(data) {
	           	var mapdata = $.map(data, function(item) { return new Item(item) });
				self.items(mapdata);
        	});
        }
		self.loadData();

		self.addItem = function() {	
			console.log(self.newItemClass());
			console.log(self.newItemdescription());
			var request = new Object();
			request.Class = self.newItemClass();
			request.description = self.newItemdescription();
			request = JSON.stringify(request);
			console.log(request);
			System.sendPostRequest(self.itemsURI, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.newItemClass("");
				self.newItemdescription("");
				self.loadData();
				$("#addItemModal").modal('hide')
			}, "Adding Item...");
		}
		
		self.SelectedItemId = ko.observable();
		self.selectItem = function(item) {
		  	 self.editItemClass(item.Class())
		     self.editItemdescription(item.description());
		     self.SelectedItemId(item.id())
		}
		

		self.updateItem = function() {
			console.log(self.SelectedItemId());
			console.log(self.editItemClass());
			console.log(self.editItemdescription());
			
			var request = new Object();
			request.id = self.SelectedItemId();
			request.Class = self.editItemClass();
			request.description = self.editItemdescription();
			request = JSON.stringify(request);
			console.log(request);

			System.sendPutRequest(self.itemsURI, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.loadData();
				$("#editItemModal").modal('hide')
			}, "Editing Item...");
		}
		
		self.deleteItem = function() {
			var request = new Object();
			request.id = self.SelectedItemId();
			console.log("deleting id : " + self.SelectedItemId());
			request = JSON.stringify(request);
			console.log(request);
			System.sendDeleteRequest(self.itemsURI, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.loadData();
				$("#deleteItemModal").modal('hide')
			}, "Delete Item...");
		}
		
		self.showAttachments = function(item) {
			self.editItemClass(item.Class())
		    self.editItemdescription(item.description());
		    self.SelectedItemId(item.id());

			var request = new Object();
			request.itemid = self.SelectedItemId();
			console.log("Attachment itemid : " + self.SelectedItemId());
			request = JSON.stringify(request);
			console.log(request);
			
			sessionStorage.setItem("SelectedId", self.SelectedItemId());
			window.open(window.location.protocol + "//" + window.location.host + "/" + window.location.pathname +"#attachments", "_self");
			//window.open("http://localhost:8080/TemplateProject/#attachments","_self")
		}
		
		self.showStructure = function(item) {
			self.editItemClass(item.Class())
		    self.editItemdescription(item.description());
		    self.SelectedItemId(item.id());

			var request = new Object();
			request.itemid = self.SelectedItemId();
			console.log("Structure itemid : " + self.SelectedItemId());
			request = JSON.stringify(request);
			console.log(request);
			
			sessionStorage.setItem("SelectedId", self.SelectedItemId());
			window.open(window.location.protocol + "//" + window.location.host + "/" + window.location.pathname +"#structure", "_self");
		}
		
		self.showAml = function(item) {
			self.editItemClass(item.Class())
		    self.editItemdescription(item.description());
		    self.SelectedItemId(item.id());

			var request = new Object();
			request.itemid = self.SelectedItemId();
			console.log("AML itemid : " + self.SelectedItemId());
			request = JSON.stringify(request);
			console.log(request);
			
			sessionStorage.setItem("SelectedId", self.SelectedItemId());
			window.open(window.location.protocol + "//" + window.location.host + "/" + window.location.pathname +"#aml", "_self");
		}
	}

	return {
		getInstance : function() {
			return new actionVM();
		}
	};
});
