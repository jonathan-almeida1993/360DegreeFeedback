// JavaScript source code
function Util() {
    if (!(this instanceof Util)) {
        return new Util();
    }

    Util.prototype.showErrorMessageAlert = function (headerText, errorText) {
        swal({ title: headerText, text: errorText, type: "error", confirmButtonText: "OK" });
        //swal({ title: "Error!", text: "Here's my error message!", type: "error", confirmButtonText: "Cool" });
    }
    
    Util.prototype.showErrorMessageAlertForSessionTimeout = function (headerText, errorText) {
        swal({ title: headerText, text: errorText, type: "error", confirmButtonText: "OK" },
        		function () {
    	    window.location.href = 'login.html';
    	});
        
        //swal({ title: "Error!", text: "Here's my error message!", type: "error", confirmButtonText: "Cool" });
    }

    Util.prototype.showSuccessMessageAlert = function (headerText, successText) {
        swal(headerText, successText, "success");
        //swal("Good job!", "You clicked the button!", "success")
    }

    Util.prototype.showSuccessMessageAlertForPwdChange = function (headerText, errorText) {
        swal({ title: headerText, text: errorText, type: "success", confirmButtonText: "OK" },
        		function () {
    	    window.location.href = 'login.html';
    	});
        
        //swal({ title: "Error!", text: "Here's my error message!", type: "error", confirmButtonText: "Cool" });
    }
    
    Util.prototype.showConfirmAlert = function (callback) {
        var response = "";
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this imaginary file!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, cancel!",
            closeOnConfirm: false,
            closeOnCancel: false
        }, function (isConfirm) {
            callback(isConfirm);
            //if (isConfirm) {
            //   swal("Deleted!", "Your imaginary file has been deleted.", "success");
            //}
            //else {
            //   swal("Cancelled", "Your imaginary file is safe :)", "error");
            //}
        });
        
       // 
    };
    
    Util.prototype.showConfirmClose = function (callback) {
        var response = "";
        swal({
            title: "Minimum Criteria not Satisfied..!",
            text: "Close anyway..?",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, Close it!",
            cancelButtonText: "No, cancel!",
            closeOnConfirm: false,
            closeOnCancel: true
        }, function (isConfirm) {
            callback(isConfirm);
            //if (isConfirm) {
            //   swal("Deleted!", "Your imaginary file has been deleted.", "success");
            //}
            //else {
            //   swal("Cancelled", "Your imaginary file is safe :)", "error");
            //}
        });
        
       // 
    };
    
    Util.prototype.isEmpty = function(fieldName){
    	var fieldValue = $(fieldName).val();
    	if(fieldValue == "" || fieldValue == null || fieldValue == "undefined"){
    		return true;
    	}else{
    		return false;
    	}
    };
    
    /*Ankush 1 */
    Util.prototype.isEmailValid=function(emailId){
    	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  	  var flag= regex.test(emailId);
    	return flag;
    };
    /*Ankush 1 */
    
    Util.prototype.isValidDate = function(fieldName){
    	var fieldValue = $(fieldName).val();
    	var regEx= /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/;
    	if(regEx.test(fieldValue)){
    		return true;
    	}else{
    		return false;
    	}
    };
}

var utilObj = new Util();

