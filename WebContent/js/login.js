$(document).ready(function() {
	
	if($('#emailIdLoginForm').val() != "" || $('#passwordLoginForm').val() != ""){
		$('#loginbox').find('.form-label').css('top', '-16px');
	}

	var status = GetURLParameter('status');
	if(!(status=="undefined")&&!(status==null)){
		if(status=="PWD_CHANGE_SUCCESSFULL"){
//			window.location = "login.html";
			utilObj.showSuccessMessageAlertForPwdChange("Success", "Your password has been changed. Please log in again.");
//			alert("Your password has been changed. Please log in again.");
		}else if(status=="INVALID_OLD_PWD"){
			utilObj.showErrorMessageAlert("Error", "The old password is invalid.");
		}else{
			alert(status);
		}
		
	}
	
	$('#forgotPassword').click(function(){
		if(!IsEmail($("input#emailIdLoginForm").val())){
			utilObj.showErrorMessageAlert("Error", "Please Enter a Valid Email Id");
		}else{
			/*alert("forgot password triggered");*/
			var status = sendDataSync($("input#emailIdLoginForm").val(), "forgotPassword", "LoginController");
			if(status =='PWD_STATUS_OK'){
				utilObj.showSuccessMessageAlert("Success", "Please check the mail that has been sent to get your password");
			}else{
				utilObj.showErrorMessageAlert(status, "Please try again with a valid emailId");
			}
		}
	});
	
});

function GetURLParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}

function validateForm(){
	
	if(!IsEmail($("input#emailIdLoginForm").val())){
		utilObj.showErrorMessageAlert("Error", "Please Enter a Valid Email Id");
		return false;
	}
	else if(($("input#passwordLoginForm").val()=="")||($("input#passwordLoginForm").val()==null))
	{
		utilObj.showErrorMessageAlert("Error", "Please Enter the Password");
		return false;
	}
	else{
		$("input#passwordLoginForm").val(CryptoJS.SHA256($('#passwordLoginForm').val()));
		return true;
	}

}

function IsEmail(email) {
	  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	  return regex.test(email);
}

function validateChangePwdForm(){
	
	if(($("input#oldPwdChngPwd").val()=="")||($("input#oldPwdChngPwd").val()==null))
	{
		utilObj.showErrorMessageAlert("Error", "Please Enter the Old Password");
		return false;
	}else if(($("input#newPwdChngPwd").val()=="")||($("input#newPwdChngPwd").val()==null))
	{
		utilObj.showErrorMessageAlert("Error", "Please Enter the New Password");
		return false;
	}else if(($("input#newPwdChngPwd").val().length<5))
	{
		utilObj.showErrorMessageAlert("Error", "The password should contain atleast 5 characters");
		return false;
	}else if(($("input#confirmNewPwdChngPwd").val()=="")||($("input#confirmNewPwdChngPwd").val()==null))
	{
		utilObj.showErrorMessageAlert("Error", "Please Enter the Confirm Password");
		return false;
	}else if(!(/^[a-zA-Z0-9#$&@,]*$/.test($("input#newPwdChngPwd").val())))
	{
		utilObj.showErrorMessageAlert("Error", "The password can contain @,#,$,&,comma and alphanumeric characters only.");
		return false;
	}else if(!($("input#newPwdChngPwd").val()===$("input#confirmNewPwdChngPwd").val())){
		utilObj.showErrorMessageAlert("Error", "The new passwords failed to match");
		return false;
	}else{
		$("input#oldPwdChngPwd").val(CryptoJS.SHA256($('input#oldPwdChngPwd').val()));
		$("input#newPwdChngPwd").val(CryptoJS.SHA256($('input#newPwdChngPwd').val()));
		$("input#confirmNewPwdChngPwd").val(CryptoJS.SHA256($('input#confirmNewPwdChngPwd').val()));
		return true;
	}

}