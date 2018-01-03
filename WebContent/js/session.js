function sessioncheckforpopulatingdata(data){
	 if(data.startsWith('<!DOCTYPE html>')){
	    	sweetAlert({
	    	    title: "Session Timeout",
	    	    text: "Your session has been expired!",
	    	    type: "success"
	    	},

	    	function () {
	    	    window.location.href = 'login.html';
	    	});
	 }
}

function sessionCheck(data){
	 if(data.startsWith('<!DOCTYPE html>')){
	    	sweetAlert({
	    	    title: "Session Timeout",
	    	    text: "Your session has been expired!",
	    	    type: "success"
	    	},

	    	function () {
	    	    window.location.href = 'login.html';
	    	});
	 }
	 else
		 utilObj.showErrorMessageAlert("Error", data);

};

/*if (!String.prototype.startsWith) {
	  String.prototype.startsWith = function(searchString, position) {
	    position = position || 0;
	    return this.indexOf(searchString, position) === position;
	  };
}*/