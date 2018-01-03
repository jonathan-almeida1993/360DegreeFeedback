$(document).ready(function() {

	/*-------------------------------------------OnClick of Self Feedback button-----------------------------------------------*/
	$('.nav #inbox1,#userHomePage #inbox1').click(function(){ 
		$("#feedbackQuestionList").removeAttr('data-feedbackId');
		$("#feedbackQuestionList").removeAttr('data-respondentId');
		feedbackObj.populateQuestions(0,0);	
		//check survey end date validation
	    var surveyStartDate = new Date(window.startDate);
		var surveyEndDate = new Date(window.endDate);
		var today = new Date();
		
		if(!(today>=surveyStartDate&&today<=surveyEndDate)){
			$('#feedbackQuestionList input[type="radio"]').attr('disabled','disabled');
			$('#feedbackForm input[type="button"]').attr('disabled','disabled');
			alert("Survey End Date has already passed.");
		}
	});
	/* ---------------------------------------------OnClick of Submit Button-------------------------------------------------------*/
	// $("input[type=submit]").attr("disabled", "disabled");

	$('#FeedBackSubmitBtn').click(function() {
		$('.errorRow').each(function(idx,obj) {
			$(obj).removeClass('errorRow');
		});
		var submitBtn =  true;
		//var firstErrRadioBtnGrp = "";
		$('#feedbackQuestionList :radio').each(function() {
			nam = $(this).attr('name');
			if (!$(':radio[name="' + nam + '"]:checked').length) {
				$('#feedbackQuestionList :radio[name="' + nam + '"]').parents('li').addClass('errorRow');
			}
			if (submitBtn && !$(':radio[name="' + nam + '"]:checked').length) {
				submitBtn = false;
			}
		});
/*		$('#feedbackQuestionList textarea').each(function() {
			if (submitBtn && $.trim($(this).val())==""||$.trim($(this).val())==undefined||$.trim($(this).val())==null) {
				submitBtn = false;
			}
		});*/
		
		/*firstErrRadioBtnGrp = $('#feedbackQuestionList').find('.errorRow :first-child').clone(true);
		console.log(firstErrRadioBtnGrp);*/
		
		//$('html, body').animate(function(){window.location.hash='#anbc';}, "slow");
		
		if (submitBtn == true) {
			var jsonResponse = feedbackObj.captureFeedback(true);
			jsonResponse = jQuery.parseJSON(jsonResponse);
			if(jsonResponse.dataFromServer=='EXECUTION_SUCCESSFULL'){
				$('#FeedBackSubmitBtn').attr("disabled", "disabled");
				$('#SaveBtn').attr("disabled", "disabled");
				utilObj.showSuccessMessageAlert("Success", "Feedback submitted successfully.");
				if(window.userRole=='RESPONDENT'){
					$('#inbox2').trigger('click');	
				}else{
					$('#outbox2').trigger('click');
				}
			}else{
				utilObj.showErrorMessageAlert("Error", jsonResponse.dataFromServer);
				//sessionCheck(jsonResponse.dataFromServer);
			}
			
		}else{
			utilObj.showErrorMessageAlert("Error", "All Questions are Mandatory.");
		}
		return submitBtn;
	});


	/*---------------------------------------------On click of Save button-------------------------------------------------*/

	$('#SaveBtn').click(function() {
		var jsonResponse = feedbackObj.captureFeedback(false);
		jsonResponse = jQuery.parseJSON(jsonResponse);
		if(jsonResponse.dataFromServer=="EXECUTION_SUCCESSFULL"){
			utilObj.showSuccessMessageAlert("Success", "Saved Data Partially..!");
			feedbackObj.populateQuestions(jsonResponse.respondentIdForPartialSave, jsonResponse.feedbackIdForPartialSave);
		}else{
			utilObj.showErrorMessageAlert("Error", jsonResponse.dataFromServer);
			//sessionCheck(jsonResponse.dataFromServer);
		}
		/*feedbackObject.feedbackid  = $("#feedbackQuestionList").attr('data-feedbackId');
		feedbackObject.respondentid  = $("#feedbackQuestionList").attr('data-respondentId');
		feedbackObj.populateQuestions(feedbackObject.feedbackid,feedbackObject.respondentid);
*/	});


	/*-----------------------TO GET COUNT OF RESPONDENT-----------------------------------*/
	/*$('#inbox2').click(function(){ 
	feedbackObj.countOfRespondent();
	});*/

});    //document ready ends


function Feedback() {
	if (!(this instanceof Feedback)) {
		return new Feedback();
	}


	/*-------------------------------------------------Populating the Questions-------------------------------------------------------------*/
	Feedback.prototype.populateQuestions = function(respondentId,feedbackId) {

		var questionList = '';
		$.ajax({
			type: "POST",
			url: "FeedbackController",
			async: false,
			data: {
				message: "fetchQuestions",
				JDATA: '{"respondentid":"'+respondentId+'","feedbackid":"'+feedbackId+'"}'
			},
			success: function(data) {
				questionList = data;
				console.log(questionList);
//				sessioncheckforpopulatingdata(data);
			},
			error: function(xhr, ajaxOptions, throwError) {
				utilObj.showErrorMessageAlertForSessionTimeout("Error", throwError);
			}
		});

		var htmlToAppend = "";
		var count = 0;
		var userObject = JSON.parse(questionList);
		var status = "";
		var textareaCount = 0;

		$.each(jQuery.parseJSON(questionList), function(idx, obj) {

			status = obj.feedbackstatus;
			var questSrNo = idx;
	        
			if(obj.questType == "RATING" || obj.questType == "Rating"){
//				alert("Negative Question Alert-"+obj.questdesc+"   ----?----"+obj.negativeQuest);
				if(obj.negativeQuest==false){
					htmlToAppend += "<li class='row' data-question-id='" + obj.questionid + "'>" +
		            "<span class='col-md-5 col-sm-12 col-xs-12 question'>" +(++questSrNo)+". "+obj.questdesc + "</span>" +
		            "<input type='hidden' name='queryType' value='add'>" +
		            "<span class='col-md-7 col-sm-12 col-xs-12 questionOptions'>" +
		            "<div class='col-md-2 radioOption'>" +
		            "<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "' type='radio' value='6'>" +
		            "<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
		            "</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-2 radioOption'>" +
			            "<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='5'>" +
			            "<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
			            "</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-1 radioOption'>" +
			            "<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='4'>" +
			            "<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
			            "</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-2 radioOption'>" +
			        	"<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='3'>" +
			        	"<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
			        	"</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-1 radioOption'>" +
				        "<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='2'>" +
				        "<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
				        "</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-2 radioOption'>" +
			        	"<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='1'>" +
			        	"<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
			        	"</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-2 radioOption'>" +
			            "<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='-1'>" +
			            "<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
			            "</div>"+
			            "</span>" +
			            "</li>";
				}else{
					htmlToAppend += "<li class='row' data-question-id='" + obj.questionid + "'>" +
		            "<span class='col-md-5 col-sm-12 col-xs-12 question'>" +(++questSrNo)+". "+obj.questdesc + "</span>" +
		            "<input type='hidden' name='queryType' value='add'>" +
		            "<span class='col-md-7 col-sm-12 col-xs-12 questionOptions'>" +
		            "<div class='col-md-2 radioOption'>" +
		            "<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "' type='radio' value='1'>" +
		            "<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
		            "</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-2 radioOption'>" +
			            "<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='2'>" +
			            "<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
			            "</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-1 radioOption'>" +
			            "<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='3'>" +
			            "<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
			            "</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-2 radioOption'>" +
			        	"<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='4'>" +
			        	"<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
			        	"</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-1 radioOption'>" +
				        "<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='5'>" +
				        "<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
				        "</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-2 radioOption'>" +
			        	"<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='6'>" +
			        	"<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
			        	"</div>";
			        count++;
			        htmlToAppend += "<div class='col-md-2 radioOption'>" +
			            "<input id='radio" + count + "" + idx + "' class='radio-custom' name='radio-group" + idx + "'  type='radio' value='-1'>" +
			            "<label for='radio" + count + "" + idx + "' class='radio-custom-label'></label>" +
			            "</div>"+
			            "</span>" +
			            "</li>";

				}
			}else if(obj.questType == "FREETEXT"|| obj.questType == "TEXT"){
				textareaCount++;
				if(textareaCount == 1){
					htmlToAppend += "<li class='row'>" +
		            "<div class='col-md-12 col-sm-12 col-xs-12'>" +
		            "<strong>This section is entirely optional and you can choose to answer none, one or both questions below. Your feedback would be presented in the report without any editing.Please restrict your feedback to a maximum of 140 characters for each question.</strong>" +
		            "</div>"+
		            "</li>";
					htmlToAppend += "<li class='row' data-question-id='" + obj.questionid + "'>" +
		            "<span class='col-md-4 col-sm-12 col-xs-12 question'>"+(++questSrNo)+". "+ obj.questdesc + "</span>" +
		            "<input type='hidden' name='queryType' value='add'>" +
		            "<span class='col-md-8 col-sm-12 col-xs-12 questionOptions'>" +
		            "<div class='col-md-12 col-sm-12 col-xs-12'>" +
		            "<textarea id='freeTextArea" + obj.questionid + "' class='form-control' maxlength='140'></textarea>" +
		            "</div>"+
		            "</span>" +
		            "</li>";
				}else{
					htmlToAppend += "<li class='row' data-question-id='" + obj.questionid + "'>" +
		            "<span class='col-md-4 col-sm-12 col-xs-12 question'>"+ (++questSrNo)+". "+obj.questdesc + "</span>" +
		            "<input type='hidden' name='queryType' value='add'>" +
		            "<span class='col-md-8 col-sm-12 col-xs-12 questionOptions'>" +
		            "<div class='col-md-12 col-sm-12 col-xs-12'>" +
		            "<textarea id='freeTextArea" + obj.questionid + "' class='form-control' maxlength='140'></textarea>" +
		            "</div>"+
		            "</span>" +
		            "</li>";
				}
				
			}
			count = 0;
		});

		$("#feedbackQuestionList").empty();
		$("#feedbackQuestionList").append(htmlToAppend);

		$('#feedbackForm input[type="button"]').removeAttr("disabled");
		if (status == "INPROGRESS"||status == "COMPLETED") {
			feedbackObj.appendResponses(questionList);
			if(status == "COMPLETED"){
				//$('input[type="text"]').attr("disabled", "disabled");
				$('#feedbackForm input[type="radio"]').attr("disabled", "disabled");
				$('#feedbackForm input[type="button"]').attr("disabled", "disabled");
				$('#feedbackForm textarea').attr("readonly", true);
			}
		}

	};

	//POPULATE PARTIALLY SAVED RESPONSES
	Feedback.prototype.appendResponses = function(questionList) {
		$.each(jQuery.parseJSON(questionList), function(idx, obj) {
			var questionRow = $('#feedbackQuestionList li[data-question-id=' + obj.questionid + ']');

			if(obj.questType == "FREETEXT"|| obj.questType == "TEXT"){
				$(questionRow).find("#freeTextArea" + obj.questionid).val(obj.freeTextResponse);
			}
			if(obj.questType == "RATING" || obj.questType == "Rating"){
				var radioButtonGroupName = $(questionRow).find(".questionOptions input:radio").attr("name");
				$('input[type=radio][name=' + radioButtonGroupName + '][value=' + obj.response + ']').attr("checked", true);
			}
			
			$(questionRow).find('input[name= queryType]').val('edit');
		});
	};

	/* ------------------------------------------------Capturing Feedback Function----------------------------------------------------------*/
	Feedback.prototype.captureFeedback = function(submit) {

		var feedbackData = new Array();

		var qId = null;
		var ans = null;
		var querytype = null;
		var dataFromServer = "";
		var feedbackIdForPartialSave = 0;
		var respondentIdForPartialSave = 0;


		$("#feedbackQuestionList li").each(function(idx, obj) {
			qId = $(obj).attr("data-question-id");
			var feedbackObject = new Object();
			
			if($(obj).find(".questionOptions div").children().is("textarea")){
				ans = $("#freeTextArea" + qId).val();
				feedbackObject.response = 0;
				feedbackObject.freetextinput = ans;
				}
	  		if($(obj).find(".questionOptions div").children().is("input[type=radio]")){
	  			ans = $(obj).find('input[name=radio-group' + (idx) + ']:checked').val();
	  			feedbackObject.response = ans;
	  			feedbackObject.freetextinput = "";
			}
			querytype = $(obj).find('input[name= queryType]').val();

			feedbackObject.questionid = qId;			
			feedbackObject.querytype = querytype;
			feedbackObject.isSubmit  = submit;
			feedbackObject.feedbackid  = $("#feedbackQuestionList").attr('data-feedbackId');
			feedbackObject.respondentid  = $("#feedbackQuestionList").attr('data-respondentId');

			feedbackIdForPartialSave = $("#feedbackQuestionList").attr('data-feedbackId')!=undefined?$("#feedbackQuestionList").attr('data-feedbackId'):0;
			respondentIdForPartialSave = $("#feedbackQuestionList").attr('data-respondentId')!=undefined?$("#feedbackQuestionList").attr('data-respondentId'):0;
			feedbackData.push(feedbackObject);
		});
		console.log("ResponseDataToServer - "+feedbackData);
		$.ajax({
			type: "POST",
			url: "FeedbackController",
			async: false,
			data: {
				message: "feedbackResponse",  
				JDATA: JSON.stringify(feedbackData),
			},
			success: function(data) {
				dataFromServer = data;
//				sessioncheckforpopulatingdata(data);
			},
			error: function(xhr, ajaxOptions, throwError) {
				utilObj.showErrorMessageAlertForSessionTimeout("Error", throwError);
			}
		});
		
		return '{"dataFromServer":"'+dataFromServer+'","feedbackIdForPartialSave":"'+feedbackIdForPartialSave+'","respondentIdForPartialSave":"'+respondentIdForPartialSave+'"}';
	}; //ends capturefeedback () 
	
	
	
	/* ------------------------------------------------AutoSave----------------------------------------------------------*/
	Feedback.prototype.autosave = function(submit) {

		var feedbackData = new Array();

		var qId = null;
		var ans = null;
		var querytype = null;
		var dataFromServer = "";
		var feedbackIdForPartialSave = 0;
		var respondentIdForPartialSave = 0;


		$("#feedbackQuestionList li").each(function(idx, obj) {
			qId = $(obj).attr("data-question-id");
			var feedbackObject = new Object();
			
			if($(obj).find(".questionOptions div").children().is("textarea")){
				ans = $("#freeTextArea" + qId).val();
				feedbackObject.response = 0;
				feedbackObject.freetextinput = ans;
				}
	  		if($(obj).find(".questionOptions div").children().is("input[type=radio]")){
	  			ans = $(obj).find('input[name=radio-group' + (idx) + ']:checked').val();
	  			feedbackObject.response = ans;
	  			feedbackObject.freetextinput = "";
			}
			querytype = $(obj).find('input[name= queryType]').val();

			feedbackObject.questionid = qId;			
			feedbackObject.querytype = querytype;
			feedbackObject.isSubmit  = submit;
			feedbackObject.feedbackid  = $("#feedbackQuestionList").attr('data-feedbackId');
			feedbackObject.respondentid  = $("#feedbackQuestionList").attr('data-respondentId');

			feedbackIdForPartialSave = $("#feedbackQuestionList").attr('data-feedbackId')!=undefined?$("#feedbackQuestionList").attr('data-feedbackId'):0;
			respondentIdForPartialSave = $("#feedbackQuestionList").attr('data-respondentId')!=undefined?$("#feedbackQuestionList").attr('data-respondentId'):0;
			feedbackData.push(feedbackObject);
		});

		$.ajax({
			type: "POST",
			url: "FeedbackController",
			async: false,
			data: {
				message: "feedbackResponse",  
				JDATA: JSON.stringify(feedbackData),
			},
			success: function(data) {
				dataFromServer = data;
//				sessioncheckforpopulatingdata(data);
			},
			error: function(xhr, ajaxOptions, throwError) {
				utilObj.showErrorMessageAlertForSessionTimeout("Error", throwError);
			}
		});
		return '{"dataFromServer":"'+dataFromServer+'","feedbackIdForPartialSave":"'+feedbackIdForPartialSave+'","respondentIdForPartialSave":"'+respondentIdForPartialSave+'"}';
	}; //ends Autocapturefeedback () 

	/*-----------------------------------------------------GET THE COUNT OF EMPRELATION-----------------------------------------------------------------------*/

	Feedback.prototype.countOfRespondent = function() {

		$.ajax({
			type: "POST",
			url: "FeedbackController",
			async: false,
			data: {
				message: "countOfEmployeeRelation",  
				JDATA: null                        
			},
			success: function(data) {
				var result = $.parseJSON(data);

				$.each(result, function(key, value) {
					if(value.empRelation== "PEER"){
						if(value.feedbackstatus == "PENDING"){
							//console.log(value.countOfEmp);
							$("#pendingPeerRequestCount").empty();
							$("#pendingPeerRequestCount").text(value.countOfEmp);
						}
						if(value.feedbackstatus == "INPROGRESS"){
							$("#inprogressPeerRequestCount").empty();
							$("#inprogressPeerRequestCount").text(value.countOfEmp);
						}
					}
					if(value.empRelation== "SUBORDINATE"){
						if(value.feedbackstatus == "PENDING"){
							$("#pendingSeniorRequestCount").empty();
							$("#pendingSeniorRequestCount").text(value.countOfEmp);
						}
						if(value.feedbackstatus == "INPROGRESS"){
							$("#inprogressSeniorRequestCount").empty();
							$("#inprogressSeniorRequestCount").test(value.countOfEmp);
						}
					}
					if(value.empRelation == "SENIOR"){
						if(value.feedbackstatus == "PENDING"){
							$("#pendingSubordinateRequestCount").empty();
							$("#pendingSubordinateRequestCount").text(value.countOfEmp);
						}
						if(value.feedbackstatus == "INPROGRESS"){
							$("#inprogressSubordinateRequestCount").empty();
							$("#inprogressSubordinateRequestCount").test(value.countOfEmp);
						}
					}
					if(value.empRelation == "OTHER"){
						if(value.feedbackstatus == "PENDING"){
							$("#pendingOtherRequestCount").empty();
							$("#pendingOtherRequestCount").text(value.countOfEmp);
						}
						if(value.feedbackstatus == "INPROGRESS"){
							$("#inprogressOtherRequestCount").empty();
							$("#inprogressOtherRequestCount").test(value.countOfEmp);
						}
					}
				});
//				sessioncheckforpopulatingdata(data);
			},
			error: function(xhr, ajaxOptions, throwError) {
				utilObj.showErrorMessageAlertForSessionTimeout("Error", throwError);
			}
		});
	};
} //Class ends 

var feedbackObj = new Feedback();