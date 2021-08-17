define([ "binder" ], function(_binder) {
	var binder = _binder;

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
			}, "Validating ChangeOrder...");
		};

		self.closeWindow = function() {
			window.close();
		}
		
		////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////
	
	/*	
		self.users = ko.observableArray();
		
		$.getJSON("http://localhost:8080/TemplateProject/#users").
        then(function(users) {
            $.each(users, function() {
                self.users.push({
                    id: ko.observable(this.id),
                    name: ko.observable(this.name),
          			email: ko.observable(this.email),
                    password: ko.observable(this.password)      
				});
				
            });

        });
		*/
		
		
		self.users = ko.observableArray();

	    self.getUsers = function () {
	        $.ajax({
	            type: 'GET',   
	            url: 'http://localhost:8080/TemplateProject/users',
	            contentType: "application/javascript",
	            dataType: "jsonp",
	            success: function(data) {
	                var observableData = ko.mapping.fromJS(data);
	                var array = observableData();
	                self.users(array);
	            },
				success: function(data) {
                var observableData = ko.mapping.fromJS(data);
                var array = observableData();
                self.users(array);
				},	
	            error:function(jq, st, error){
	                alert(error);
	            }
	        });
	    };
		
		self.edit = function(user) {
            alert("Edit: " );
        }
        self.remove = function(user) {
            alert("Remove: " + user.name());
        }
		
		
		////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////
	}

	return {
		getInstance : function() {
			return new actionVM();
		}
	};
});
