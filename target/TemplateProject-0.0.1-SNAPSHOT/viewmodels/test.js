define([ "binder" ], function(_binder) {
	var binder = _binder;
/////////////////////////////////////////////

function Item(data) {
    this.id = ko.observable(data.id);
    this.Class = ko.observable(data.Class);
    this.description = ko.observable(data.description);
}




///////////////////////////////////////////////
	function actionVM() {
		var self = this;

		this.name = ko.observable("Areej");
   		this.fullName = ko.observable("Areej Ehsan");
	
	////////////////////////////////////////////////
	
	var self = this;
        self.itemsURI = 'http://localhost:8080/TemplateProject/itemservlet';
        //self.username = "miguel";
        //self.password = "python";

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

		self.newItem = ko.observable();
        self.items = ko.observableArray([]);
	
		 self.loadData = function() {
            self.ajax(self.itemsURI, 'GET').done(function(data) {
           /*
			for (var i = 0; i < data.items().length; i++) {
                self.items.push({
                    id: ko.observable(data.items[i].id),
                    Class: ko.observable(data.items[i].Class),
                    description: ko.observable(data.items[i].description),
                });
            }
			*/
			var mapdata = $.map(data, function(item) { return new Item(item) });
			self.items(mapdata);
        	});
        }
		
        
	///////////////////////////////////////////////
	}

	return {
		getInstance : function() {
			return new actionVM();
		}
	};
});
