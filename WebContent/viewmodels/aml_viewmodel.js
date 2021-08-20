define([ "binder" ], function(_binder) {
	var binder = _binder;

	function Aml(data) {
		this.mid = ko.observable(data.mid);
	    this.mitemid = ko.observable(data.mitemid);
	    this.mpart = ko.observable(data.mpart);
	    this.manufacturer = ko.observable(data.manufacturer);
		this.registryid = ko.observable(data.registryid);
		this.amlstatus = ko.observable(data.amlstatus);
		this.description = ko.observable(data.description);
		this.mstatus = ko.observable(data.mstatus);
	}
		
	function actionVM() {
		var self = this;

		self.errors = ko.observableArray();
		self.validationCompleted = ko.observable(false);

		self.onLoad = function() {
			var req = new Object();
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
			}, "Opening Item's Aml...");
		};

		self.closeWindow = function() {
			window.close();
		}
		
		
		self.newmpart = ko.observable();
		self.newmanufacturer = ko.observable();
		self.newregistryid = ko.observable();
		self.newamlstatus = ko.observable();
		self.newdescription = ko.observable();
		self.newmstatus = ko.observable();
		
		self.editmpart = ko.observable();
		self.editmitemid = ko.observable();
		self.editmanufacturer = ko.observable();
		self.editregistryid = ko.observable();
		self.editamlstatus = ko.observable();
		self.editdescription = ko.observable();
		self.editmstatus = ko.observable();
		self.SelectedAmlId = ko.observable();
		
		self.availableAMLStatus = ko.observableArray ([
               'Inactive','Active'
            ]);
		self.availableMStatus = ko.observableArray ([
               'Inactive','Active'
            ]);

		self.isStatusActive = ko.observable(false);
		self.checkStatus = function() {
			if (self.newamlstatus() == 'Active') {
				self.isStatusActive(true);
			}
		}
		self.checkStatus();
		
		var SelectedId = sessionStorage.getItem('SelectedId');
		self.aml = ko.observableArray([]);
		self.amlURI = 'http://localhost:8080/TemplateProject/amlservlet';
		
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

		self.loadData = function() {
            self.ajax(self.amlURI + "?mitemid="+SelectedId, 'GET').done(function(data) {
	           	var mapdata = $.map(data, function(aml) { return new Aml(aml) });
				self.aml(mapdata);
        	});
        }
		self.loadData();


		self.addAml = function() {	
			console.log(self.newmpart());
			console.log(self.newmanufacturer());
			console.log(self.newregistryid());
			console.log(self.newamlstatus());
			console.log(self.newdescription());
			console.log(self.newmstatus());
			var request = new Object();
			request.mitemid = SelectedId;
			request.mpart = self.newmpart();
			request.manufacturer = self.newmanufacturer();
			request.registryid = self.newregistryid();
			request.amlstatus = self.newamlstatus();
			request.description = self.newdescription();
			request.mstatus = self.newmstatus();
		
			request = JSON.stringify(request);
			console.log(request);
			System.sendPostRequest(self.amlURI + "?mitemid="+SelectedId, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.newmpart("");
				self.newmanufacturer("");
				self.newregistryid("");
				self.newamlstatus("");
				self.newdescription("");
				self.newmstatus("");

				$("#addAMLModal").modal('hide')
				self.loadData();
			}, "Adding Aml...");
		}
		
		self.SelectedAmlId = ko.observable();
		self.selectAml = function(aml) {
		  	 $("#ediAMLModal").modal('show')
		  	 self.editmpart(aml.mpart())
		     self.editmanufacturer(aml.manufacturer());
		     self.editregistryid(aml.registryid());
		     self.editamlstatus(aml.amlstatus());
		     self.editdescription(aml.description());
		     self.editmstatus(aml.mstatus());
		     self.editmitemid(SelectedId);
		     self.SelectedAmlId(aml.mid())
		}

		self.updateAml = function() {
			console.log(self.editmitemid());
			console.log(self.editmpart());
			console.log(self.editmanufacturer());
			console.log(self.editregistryid());
			console.log(self.editamlstatus());
			console.log(self.editdescription());
			console.log(self.editmstatus());
			
			var request = new Object();
			request.mid = self.SelectedAmlId();
			request.mitemid = SelectedId;
			request.mpart = self.editmpart();
			request.manufacturer = self.editmanufacturer();
			request.registryid = self.editregistryid();
			request.amlstatus = self.editamlstatus();
			request.description = self.editdescription();
			request.mstatus = self.editmstatus();
			request = JSON.stringify(request);
			console.log(request);

			System.sendPutRequest(self.amlURI + "?mitemid="+SelectedId, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.loadData();
				$("#editAMLModal").modal('hide')
			}, "Editing Aml...");
		}
		
		self.deleteAml = function() {
			var request = new Object();
			request.mid = self.SelectedAmlId();
			console.log("deleting id : " + self.SelectedAmlId());
			request = JSON.stringify(request);
			console.log(request);
			System.sendDeleteRequest(self.amlURI + "?mitemid="+SelectedId, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.loadData();
				$("#deleteAMLModal").modal('hide')
			}, "Deleting Aml...");
		}

		self.backtoitems = function() {
			window.open("http://localhost:8080/TemplateProject/#items","_self")
		}

	}

	return {
		getInstance : function() {
			return new actionVM();
		}
	};
});
