define([ "binder" ], function(_binder) {
	var binder = _binder;

	function Structure(data) {
		this.sid = ko.observable(data.sid);
	    this.sitemid = ko.observable(data.sitemid);
	    this.Class = ko.observable(data.Class);
		this.description = ko.observable(data.description);
	    this.lifecyclephase = ko.observable(data.lifecyclephase);
	    this.createdby = ko.observable(data.createdby);
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
			}, "Opening Item's Structure...");
		};

		self.closeWindow = function() {
			window.close();
		}
		
		
		self.newClass = ko.observable();
		self.newdescription = ko.observable();
		self.newlifecyclephase = ko.observable();
		self.newcreatedby = ko.observable();
		
		self.editsid= ko.observable();
		self.editsitemid= ko.observable();
		self.editClass = ko.observable();
		self.editdescription = ko.observable();
		self.editlifecyclephase = ko.observable();
		self.editcreatedby = ko.observable();
		
		var SelectedId = sessionStorage.getItem('SelectedId');
		self.structures = ko.observableArray([]);
		self.structureURI = 'http://localhost:8080/TemplateProject/structureservlet';
		         
        this.availableLifecyclePhases = ko.observableArray ([
               'DEvelopment','Production',
               'Initial','Completion'
            ]);


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
            self.ajax(self.structureURI + "?sitemid="+SelectedId, 'GET').done(function(data) {
	           	var mapdata = $.map(data, function(structure) { return new Structure(structure) });
				self.structures(mapdata);
        	});
        }
		self.loadData();
		
	
		self.addStructure = function() {	
			console.log(self.newClass());
			console.log(self.newdescription());
			console.log(self.newlifecyclephase());
			console.log(self.newcreatedby());
			var request = new Object();
			request.sitemid = SelectedId;
			request.Class = self.newClass();
			request.description = self.newdescription();
			request.lifecyclephase = self.newlifecyclephase();
			request.createdby = self.newcreatedby();
			
			request = JSON.stringify(request);
			console.log(request);
			System.sendPostRequest(self.structureURI + "?sitemid="+SelectedId, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.newClass("");
				self.newdescription("");
				self.newlifecyclephase("");
				self.newcreatedby("");

				self.loadData();
				$("#addStructureModal").modal('hide')
			}, "Adding Structure...");
		}
		
		self.SelectedStructureId = ko.observable();
		self.selectStructure = function(structure) {
		  	 self.editClass(structure.Class())
		     self.editdescription(structure.description());
		     self.editlifecyclephase(structure.lifecyclephase());
		     self.editcreatedby(structure.createdby());
		     self.editsitemid(SelectedId);
		     self.SelectedStructureId(structure.sid())
		}
		

		self.updateStructure = function() {
			console.log(self.SelectedStructureId());
			console.log(self.editClass());
			console.log(self.editdescription());
			console.log(self.editlifecyclephase());
			console.log(self.editcreatedby());
			
			var request = new Object();
			request.sid = self.SelectedStructureId();
			request.sitemid = SelectedId;
			request.Class = self.editClass();
			request.description = self.editdescription();
			request.lifecyclephase = self.editlifecyclephase();
			request.createdby = self.editcreatedby();
			
			request = JSON.stringify(request);
			console.log(request);

			System.sendPutRequest(self.structureURI + "?sitemid="+SelectedId, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.loadData();
				$("#editStructureModal").modal('hide')
			}, "Editing Structure...");
		}
		
		self.deleteStructure = function() {
			var request = new Object();
			request.sid = self.SelectedStructureId();
			console.log("deleting id : " + self.SelectedStructureId());
			request = JSON.stringify(request);
			console.log(request);
			System.sendDeleteRequest(self.structureURI + "?sitemid="+SelectedId, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.loadData();
				$("#deleteStructureModal").modal('hide')
			}, "Deleting Structure...");
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
