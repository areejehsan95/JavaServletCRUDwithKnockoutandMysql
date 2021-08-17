define([ "binder" ], function(_binder) {
	var binder = _binder;


	function Attachment(data) {
	    this.aid = ko.observable(data.aid);
	    this.itemid = ko.observable(data.itemid);
	    this.filename = ko.observable(data.filename);
	    this.description = ko.observable(data.description);
	    this.category = ko.observable(data.category);
	    this.shared = ko.observable(data.shared);
	    this.checkedoutby = ko.observable(data.checkedoutby);
	    this.revision = ko.observable(data.revision);
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
			}, "Opening Item's Attachments...");
		};

		self.closeWindow = function() {
			window.close();
		}
		
		
		var SelectedId = sessionStorage.getItem('SelectedId');
		self.attachments = ko.observableArray([]);
		self.attachmentsURI = 'http://localhost:8080/TemplateProject/attachmentservlet';
		
		self.newfilename = ko.observable();
		self.newdescription = ko.observable();
		self.newcategory = ko.observable();
		self.newshared = ko.observable("No");
		self.newcheckedoutby = ko.observable();
		self.newrevision = ko.observable();
		
		self.editfilename = ko.observable();
		self.editdescription = ko.observable();
		self.editcategory = ko.observable();
		self.editshared = ko.observable();
		self.editcheckedoutby = ko.observable();
		self.editrevision = ko.observable();
		self.SelectedAttachmentId = ko.observable();

		this.availableCategories = ko.observableArray ([
               'Category:  A','Category:  B',
               'Category:  C','Category:  D'
            ]);
		
		 this.availableSharedStatus = ko.observableArray ([
               'Yes','No',
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
            self.ajax(self.attachmentsURI + "?itemid="+SelectedId, 'GET').done(function(data) {
	           	var mapdata = $.map(data, function(attachment) { return new Attachment(attachment) });
				self.attachments(mapdata);
        	});
        }
		self.loadData();

		self.addAttachment = function() {	
			console.log(self.newfilename());
			console.log(self.newdescription());
			console.log(self.newcategory());
			console.log(self.newshared());
			console.log(self.newcheckedoutby());
			console.log(self.newrevision());
			
			var request = new Object();
			request.itemid = SelectedId;
			request.filename = self.newfilename();
			request.description = self.newdescription();
			request.category = self.newcategory();
			request.shared = self.newshared();
			request.checkedoutby = self.newcheckedoutby();
			request.revision = self.newrevision();
			
			request = JSON.stringify(request);
			console.log(request);
			System.sendPostRequest(self.attachmentsURI + "?itemid="+SelectedId, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.newfilename("");
				self.newdescription("");
				self.newcategory("");
				self.newshared("");
				self.newcheckedoutby("");
				self.newrevision("");
				self.loadData();
				$("#addAttachmentModal").modal('hide')
			}, "Adding Attachment...");
		}
		
		
		self.selectAttachment = function(attachment) {
	 		console.log(attachment.aid());
			self.SelectedAttachmentId(attachment.aid());
			self.editfilename(attachment.filename());
		    self.editdescription(attachment.description());
		    self.editcategory(attachment.category());
			self.editshared(attachment.shared());
			self.editcheckedoutby(attachment.checkedoutby());
			self.editrevision(attachment.revision());	  	
		    
			console.log(attachment.aid());
			console.log("aid attachment"+self.SelectedAttachmentId());
		}
		
		self.editAttachment = function(attachment) {
			self.selectAttachment(attachment);
		}
		
		self.removeAttachment = function(attachment) {
			self.selectAttachment(attachment);
		}
		
		self.updateAttachment = function() {
			console.log(SelectedId);
			console.log(self.SelectedAttachmentId());
			console.log(self.editfilename());
			console.log(self.editdescription());
			console.log(self.editcategory());
			console.log(self.editshared());
			console.log(self.editcheckedoutby());
			console.log(self.editrevision());
			
			var request = new Object();
			request.aid = self.SelectedAttachmentId();
			request.itemid = SelectedId;
			request.filename = self.editfilename();
			request.description = self.editdescription();
			request.category = self.editcategory();
			request.shared = self.editshared();
			request.checkedoutby = self.editcheckedoutby();
			request.revision = self.editrevision();
			
			request = JSON.stringify(request);
			console.log(request);

			System.sendPutRequest(self.attachmentsURI + "?itemid="+SelectedId, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.loadData();
				$("#editAttachmentModal").modal('hide')
			}, "Editing Attachment...");
		}


		self.deleteAttachment = function() {
			var request = new Object();
			request.aid = self.SelectedAttachmentId();
			console.log("deleting id : " + self.SelectedAttachmentId());
			request = JSON.stringify(request);
			console.log(request);
			System.sendDeleteRequest(self.attachmentsURI + "?itemid="+SelectedId, request, function(response) {
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}
					self.validationCompleted(true);
				}
				self.loadData();
				$("#deleteAttachmentModal").modal('hide')
			}, "Deleting Attachment...");
		}
		
		
		self.backtoitems = function() {
			window.open("http://localhost:8080/TemplateProject/#items","_self")
		}

		
		$("#FileInput").on('change',function (e) {
            var labelVal = $(".title").text();
            var oldfileName = $(this).val();
                fileName = e.target.value.split( '\\' ).pop();

                if (oldfileName == fileName) {return false;}
                var extension = fileName.split('.').pop();

            if ($.inArray(extension,['jpg','jpeg','png']) >= 0) {
                $(".filelabel i").removeClass().addClass('fa fa-file-image-o');
                $(".filelabel i, .filelabel .title").css({'color':'#208440'});
                $(".filelabel").css({'border':' 2px solid #208440'});
            }
            else if(extension == 'pdf'){
                $(".filelabel i").removeClass().addClass('fa fa-file-pdf-o');
                $(".filelabel i, .filelabel .title").css({'color':'red'});
                $(".filelabel").css({'border':' 2px solid red'});

            }
  			else if(extension == 'doc' || extension == 'docx'){
            $(".filelabel i").removeClass().addClass('fa fa-file-word-o');
            $(".filelabel i, .filelabel .title").css({'color':'#2388df'});
            $(".filelabel").css({'border':' 2px solid #2388df'});
        }
            else{
                $(".filelabel i").removeClass().addClass('fa fa-file-o');
                $(".filelabel i, .filelabel .title").css({'color':'black'});
                $(".filelabel").css({'border':' 2px solid black'});
            }

            if(fileName ){
                if (fileName.length > 10){
                    $(".filelabel .title").text(fileName.slice(0,4)+'...'+extension);
                }
                else{
                    $(".filelabel .title").text(fileName);
                }
            }
            else{
                $(".filelabel .title").text(labelVal);
            }
        });

	}

	return {
		getInstance : function() {
			return new actionVM();
		}
	};
});
