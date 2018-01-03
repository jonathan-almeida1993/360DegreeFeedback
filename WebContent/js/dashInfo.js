$(document).ready(function() {
	getDashBrd();
	
});

function getDashBrd() {

	var dashBrdJson = null;
	$.ajax({
		type : "POST",
		url : "DashBoardUIController",
		async : false,
		data : {
			message : "",
			JDATA : ""
		},
		success : function(data) {
			dashBrdJson = data;
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});

	 var tr;
	    $('table#dashMasterTable tbody').empty();
	    $.each(jQuery.parseJSON(dashBrdJson),function(idx, obj) {
	    tr = $('<tr/>');
	    tr.append("<td>" + (idx+1)+ "</td>");
	    tr.append("<td>" + obj.Name+ "</td>");
	    tr.append("<td>" + obj.TemplateName+ "</td>");
	    tr.append("<td>" + obj.StartDate+ "</td>");
	    tr.append("<td>" + obj.EndDate+ "</td>");
	    tr.append("<td>" + obj.NoOfRespondents+ "</td>");       
		tr.append("<td>" + obj.NoOfRespponsesReceived+"</td>");
		tr.append("<td>" + obj.SelfFeedback+"</td>");
		tr.append('<td><button type="button" class="btn btn-danger editQuestionBtn" data-toggle="tooltip" id="closeSurveyBtn#'+obj.FeedBackId+'" data-placement="bottom" title="Close">'+
		'<span class="glyphicon glyphicon-pencil"></span></button></td>');
		tr.append("<td>" + obj.Status+"</td>");
	    tr.append('<td><button type="button" class="btn btn-danger editQuestionBtn" data-toggle="tooltip" id="ReminderBtn#'+obj.FeedBackId+'" data-placement="bottom" title="Remind">'+
				'<span class="glyphicon glyphicon-pencil"></span></button></td>');
	    $('table#dashMasterTable tbody').append(tr);
	    });
}