var LOADING_SEM = 0;

/**
 * Set to true for disable logging.
 */
var DISABLE_LOGGING = false;

/**
 * localStorage - stores data with no expiration date
 * sessionStorage - stores data for one session (data is lost when the tab is closed)
 */
var DEFAULT_STORAGE_TYPE = "sessionStorage";

var System = {
	
	uuid : "",
	
	getParameterByName:function(name, url) {
	    if (!url) url = window.location.href;
	    name = name.replace(/[\[\]]/g, "\\$&");
	    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
	        results = regex.exec(url);
	    if (!results) return null;
	    if (!results[2]) return '';
	    return decodeURIComponent(results[2].replace(/\+/g, " "));
	},
	
	/**
	 * Sends ajax post request. Blocks UI and shows waiting message if given waitingMessage.
	 * @param url URL
	 * @param param Parameters
	 * @param callback Callback in case of success
	 * @param waitingMessage Waitings message to display
	 * @param divToMask Target div to mask
	 */
	sendPostRequest:function(url, param, callback, waitingMessage, divToMask) {
		var timestamp = new Date().getUTCMilliseconds();
		System.uuid = timestamp;
		
		if(waitingMessage) {
			System.showLoadingScreen(waitingMessage, divToMask);
		}
		$.ajax({
			  type: "POST",
			  url: url,
			  data: param,
			  success: function(responseJson) {
				if(System.uuid == timestamp){
					if(waitingMessage){
						System.hideLoadingScreen(divToMask);
					}
					callback(responseJson);		
				}else{
					if(waitingMessage){
						System.hideLoadingScreen(divToMask);
					}
				}
			  },		 
		}).fail(function(jqXHR, textStatus) {
			console.info(textStatus);
			//IndexPageViewModel.setFooterMessage("error", "<b>Error:</b> Unable to connect to server. Please try again");
			System.hideLoadingScreen(divToMask);
		});
	},
	
	sendPutRequest:function(url, param, callback, waitingMessage, divToMask) {
		var timestamp = new Date().getUTCMilliseconds();
		System.uuid = timestamp;
		
		if(waitingMessage) {
			System.showLoadingScreen(waitingMessage, divToMask);
		}
		$.ajax({
			  type: "PUT",
			  url: url,
			  data: param,
			  success: function(responseJson) {
				if(System.uuid == timestamp){
					if(waitingMessage){
						System.hideLoadingScreen(divToMask);
					}
					callback(responseJson);		
				}else{
					if(waitingMessage){
						System.hideLoadingScreen(divToMask);
					}
				}
			  },		 
		}).fail(function(jqXHR, textStatus) {
			console.info(textStatus);
			//IndexPageViewModel.setFooterMessage("error", "<b>Error:</b> Unable to connect to server. Please try again");
			System.hideLoadingScreen(divToMask);
		});
	},
	
	sendDeleteRequest:function(url, param, callback, waitingMessage, divToMask) {
		var timestamp = new Date().getUTCMilliseconds();
		System.uuid = timestamp;
		
		if(waitingMessage) {
			System.showLoadingScreen(waitingMessage, divToMask);
		}
		$.ajax({
			  type: "DELETE",
			//  url: url + '?' + $.param({"id": id}),
			  url: url,
			  data: param,
			  success: function(responseJson) {
				if(System.uuid == timestamp){
					if(waitingMessage){
						System.hideLoadingScreen(divToMask);
					}
					callback(responseJson);		
				}else{
					if(waitingMessage){
						System.hideLoadingScreen(divToMask);
					}
				}
			  },		 
		}).fail(function(jqXHR, textStatus) {
			console.info(textStatus);
			//IndexPageViewModel.setFooterMessage("error", "<b>Error:</b> Unable to connect to server. Please try again");
			System.hideLoadingScreen(divToMask);
		});
	},
	
	sendGetAttachmentRequest:function(url, param, callback, waitingMessage, divToMask) {
		var timestamp = new Date().getUTCMilliseconds();
		System.uuid = timestamp;
		
		if(waitingMessage) {
			System.showLoadingScreen(waitingMessage, divToMask);
		}
		$.ajax({
			  type: "GET",
			 // url: url + '?' + $.param({"id": id}),
			  url: url,
			  data: param,
			  success: function(responseJson) {
				if(System.uuid == timestamp){
					if(waitingMessage){
						System.hideLoadingScreen(divToMask);
					}
					//callback(responseJson);		
				}else{
					if(waitingMessage){
						System.hideLoadingScreen(divToMask);
					}
				}
			  },		 
		}).fail(function(jqXHR, textStatus) {
			console.info(textStatus);
			//IndexPageViewModel.setFooterMessage("error", "<b>Error:</b> Unable to connect to server. Please try again");
			System.hideLoadingScreen(divToMask);
		});
	},
	
	/**
	 * Displays modal of waiting message.
	 * @param message Message to show
	 * @param divToMask Div to mask
	 */
	showLoadingScreen:function(message, divToMask) {	
		if(LOADING_SEM == 0) {
			var pleaseWaitDiv = $('#pleaseWaitDialog');
			pleaseWaitDiv.modal();
			$("#pleaseWaitMessage").html(message);
		}
		
		LOADING_SEM++;
	},
	
	/**
	 * Hides modal of waiting message.
	 * @param divToMask Div to unmask
	 */
	hideLoadingScreen: function(divToMask) {	
		if(LOADING_SEM > 0) {
			LOADING_SEM--;	
			if(LOADING_SEM == 0) {
				setTimeout(function() {
					$("#pleaseWaitDialog").modal('hide');
				}, 800);
			}			
		}
	}
};