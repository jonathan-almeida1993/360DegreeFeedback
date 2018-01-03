function sendDataSync(jsonData,message,URL){
	var returnData;
	$.ajax({
		type : "POST",
		url : URL,
		async:false,
		data : {
		     	message:message,
			    JDATA:jsonData
		},
		success : function(data) {
			returnData = data;
		},
		error : function(xhr, ajaxOptions, throwError) {
			//add appropriate error messages
		}
	});
	return returnData;
}


function sendDataAsync(jsonData,message,URL){
	var returnData;
	$.ajax({
		type : "POST",
		url : URL,
		data : {
		     	message:message,
			    JDATA:jsonData
		},
		success : function(data) {
			returnData = data;
		},
		error : function(xhr, ajaxOptions, throwError) {
           
		}
	});
	return returnData;
}
