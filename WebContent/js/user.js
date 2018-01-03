$(document).ready(function () {
	
	$('html').animate({scrollTop:0}, 1);
    $('body').animate({scrollTop:0}, 1);
	var selector = '.nav li';

	$(selector).on('click', function(){
	    $(selector).removeClass('active');
	    $(this).addClass('active');
	});
	
	window.globalVar="";
	window.availableTags=[];
	window.feedbackAutoCount=[];
	/*
	 $.ajax({
			type : "POST",
			url : "RespondentController",

			data : {
			     	message:"employeeMstrList",
				    JDATA: ""
			},
			success : function(data) {
				globalVar = data;
				var parsedEmpList = jQuery.parseJSON(globalVar);
				$.each(parsedEmpList,function(idx,obj){
					availableTags.push(obj.EmailId);
				});
				
			},
			error : function(xhr, ajaxOptions, throwError) {
				//add appropriate error messages
			}
			
		});
	


$(function() {
	
	
	    $( "input[name=respondentEmailId]" ).autocomplete({
	      source: availableTags
	    });
	  });
	*/
	
	
    //Feedback Form : Fix the header on scroll
    $(window).on("scroll", function(e) {
    	//console.log($(window).scrollTop());
      if ($(window).scrollTop() > 400) {
          $(".feedbackHeader").addClass("fix-search");
      } else {
    	  $(".feedbackHeader").removeClass("fix-search");
      }   
   });
    
    
  
    $(document).on("click", '.radio-custom', function (e) {
    	var quesId= $(this).closest('li').attr('data-question-id');
    		var i;
    		var count=0;
    		for (i = 0; i < feedbackAutoCount.length; ++i) {
    		   if(feedbackAutoCount[i]===quesId)
    			   {
    			   count++;
    			   }
    		}
		if(count===0)
			{
			feedbackAutoCount.push(quesId);
			}
        if(feedbackAutoCount.length===5){
        	var jsonResponse = feedbackObj.autosave(false);
			jsonResponse = jQuery.parseJSON(jsonResponse);
			
			var scrollPosition = $(window).scrollTop();
			feedbackObj.populateQuestions(jsonResponse.respondentIdForPartialSave, jsonResponse.feedbackIdForPartialSave);
			$(window).scrollTop(scrollPosition);
			feedbackAutoCount=[];
			/*if(jsonResponse.dataFromServer=='EXECUTION_SUCCESSFULL'){
				feedbackAutoCount=[];
			}else{
				utilObj.showErrorMessageAlert("Error", jsonResponse.dataFromServer);
			}*/
        }
    });   
    
    $('.table').bootstrapTable('resetView');
    //Align table on page resize
    $(window).resize(function () {
        $('.table').bootstrapTable('resetView');
    });
    

	$('#respondantStatusTableView .btn-group').on("click", function(){
		//alert('hi');
		$(".wrapper").css('overflow-y', 'scroll !important');
	});
    
    
	//---------Code To Get UserName From Session  Starts------------
	var userDataJson = sendDataSync(null, "getUserName", "DataController");
	userDataJson = jQuery.parseJSON(userDataJson);
	
	window.userRole = userDataJson.role;
	//to get survey start date and end date
	if(userDataJson.isPwdChanged=="false"){
		window.location = "changePwd.html";
	}
	
	if(userDataJson.startDate!=undefined&&userDataJson.endDate!=undefined){
		window.startDate = userDataJson.startDate;
		window.endDate = userDataJson.endDate;
	}
	//to get survey start date and end date ends
	$('#loggedInUser').empty();
	$('#loggedInUser').text(userDataJson.userName);
	$('#loggedInUserName').empty();
	$('#loggedInUserName').text(userDataJson.userName);
	$('#userDesc').text(userDataJson.userName+' - '+userDataJson.role);
	$('#signOutBtn').click(function(){
		sendDataSync(null, "getUserName", "DataController");
	});
	$('#signOutBtn').click(function(){
		sendDataSync(null, "logout", "DataController");
	});
	//---------Code To Get UserName From Session  Ends------------
	


	//-----------Hide Self Feedback tab in case of Role = Respondent
	
	if(userDataJson.role == "EMP"){
//		$("#landingPageContentForNominatedEmp").show();
		$("#landingPageContentForRespondent").remove();
	}

	if(userDataJson.role==='RESPONDENT'){
		$("#getUserHomePage").remove();
		$("#getDashBrd").remove();
		$("#inbox1").remove();
		$("#nominateraters").remove();
		$("#outbox2").remove();
		$("#outbox1").remove();
		$(".verticalLine").remove();
		$("#landingPageContentForNominatedEmp").remove();
		$("#userHomePage").remove();
		$('.content-wrapper,.right-side').css('background', 'none');
		$("#respondantTableView").show();	
		//		$("#landingPageContentForRespondent").show();
		userObj.getEmployees();
	}
	
	
	//-----------------ends---------------------------------------
	
    //Button click events
	
	$(".nav #getUserHomePage").on("click",function(){
		$("#userHomePage").show();
		$('.content-wrapper,.right-side').css('background', 'url("images/background-360.jpg") no-repeat fixed 50% 50%');
		$('.content-wrapper,.right-side').css('background-size','cover');
		$("#feedbackForm").hide();
        $("#contentForSelfFeedbackForm").hide();
        $("#contentForOthersFeedbackForm").hide();
        $("#dashboard").hide();
        $("#nominateEmp").hide();
        $("#respondantTableView").hide();
        $("#feedbackDashboard").hide();
        $("#allRespondentTableView").hide();
		$("#respondantStatusTableView").hide();
//		$("#landingPageContent").hide();
	});
	
/*	$("#landingPage").on("click", function(){
		$("#userHomePage").hide();
		$('.content-wrapper, .right-side ').css('background','none');
		$("#feedbackForm").hide();
        $("#contentForSelfFeedbackForm").hide();
        $("#contentForOthersFeedbackForm").hide();
        $("#dashboard").show();
        $("#nominateEmp").hide();
        $("#respondantTableView").hide();
        $("#feedbackDashboard").hide();
        $("#allRespondentTableView").hide();
		$("#respondantStatusTableView").hide();
//		$("#landingPageContent").show();
	});*/
	
    $("#getNomineeList").on("click", function () {
    	$("#userHomePage").hide();
    	$('.content-wrapper, .right-side ').css('background','none');
		$("#feedbackForm").hide();
        $("#contentForSelfFeedbackForm").hide();
        $("#contentForOthersFeedbackForm").hide();
        $("#dashboard").hide();
        $("#nominateEmp").show();
        $("#respondantTableView").hide();
        $("#feedbackDashboard").hide();
        $("#allRespondentTableView").hide();
		$("#respondantStatusTableView").hide();
//		$("#landingPageContent").hide();
    });

    
//    function demo(){
//    	alert("demo");
//    }
    $(".nav #inbox1, #userHomePage #inbox1").on("click", function () {
    	
    	$("#userHomePage").hide();
    	$('.content-wrapper, .right-side ').css('background','none');

    	$('.nav li').removeClass('active');
		$('#inbox1').parent('li').addClass('active');

    	
    	$("#feedbackForm").show();
        $("#selfName").empty();
        $("#selfName").text(userDataJson.userName);
        $("#contentForSelfFeedbackForm").show();
        $("#contentForOthersFeedbackForm").hide();
        $("#dashboard").hide();
        $("#nominateEmp").hide();
        $("#respondantTableView").hide();
        $("#feedbackDashboard").hide();
        $("#allRespondentTableView").hide();
		$("#respondantStatusTableView").hide();
//		$("#landingPageContent").hide();
    });

    $(".nav #inbox2, #userHomePage #inbox2").on("click", function () {
    	$("#userHomePage").hide();
    	$('.content-wrapper, .right-side ').css('background','none');

    	$('.nav li').removeClass('active');
		$('#inbox2').parent('li').addClass('active');

    	
    	
    	$("#feedbackDashboard").show();
        $("#feedbackForm").hide();
        $("#contentForSelfFeedbackForm").hide();
        $("#contentForOthersFeedbackForm").show();
        $("#dashboard").hide();
        $("#nominateEmp").hide();
        $("#respondantTableView").hide();
        $("#allRespondentTableView").hide();
		$("#respondantStatusTableView").hide();
//		$("#landingPageContent").hide();
		userObj.getEmployees();
    });

    $(".nav #getDashBrd").on("click", function () {
    	$("#userHomePage").hide();
    	$('.content-wrapper, .right-side ').css('background','none');

    	
    	$("#feedbackForm").hide();
        $("#contentForSelfFeedbackForm").hide();
        $("#contentForOthersFeedbackForm").hide();
        $("#dashboard").show();
        $("#nominateEmp").hide();
        $("#respondantTableView").hide();
        $("#feedbackDashboard").hide();
        $("#allRespondentTableView").hide();
		$("#respondantStatusTableView").hide();
//		$("#landingPageContent").hide();
    });
    
    /* $(".tile").on("click", function () {		
    	var tileType = $(this).find(".caption").text();		
    	switch(tileType)		
    	{		
	    	case "Peers" : userObj.getEmployees("PEER");		
	    				   break;    				   		
	    	case "Seniors":  userObj.getEmployees("SUBORDINATE");
	    					break;		
	    	case "Subordinates": userObj.getEmployees("SENIOR");				
	    						 break;		
	    	case "Others(Clients)": userObj.getEmployees("OTHER");		
	    							break;   				
    	}		
    			
        $("#respondantTableView").show();		
        $("#feedbackForm").hide();	
        $("#contentForSelfFeedbackForm").hide();
        $("#contentForOthersFeedbackForm").hide();
        $("#dashboard").hide();		
        $("#nominateEmp").hide();		
        $("#feedbackDashboard").hide();		
        $("#allRespondentTableView").hide();		
		$("#respondantStatusTableView").hide();
		$("#landingPageContent").hide();
    });	*/	
	
    $("#feedbackDashboard .viewMoreBtn").on("click", function () {		
    	$("#userHomePage").hide();
    	$('.content-wrapper, .right-side ').css('background','none');
    	$("#respondantTableView").hide();		
        $("#feedbackForm").hide();	
        $("#contentForSelfFeedbackForm").hide();
        $("#contentForOthersFeedbackForm").hide();		
        $("#dashboard").hide();		
        $("#nominateEmp").hide();		
        $("#feedbackDashboard").hide();		
        $("#allRespondentTableView").show();	
		$("#respondantStatusTableView").hide();		
//		$("#landingPageContent").hide();
    });

    $(".viewMoreBtn").on("click", function () {
    	$("#userHomePage").hide();
    	$('.content-wrapper, .right-side ').css('background','none');
    	$("#respondantTableView").show();
        $("#feedbackForm").hide();
        $("#contentForSelfFeedbackForm").hide();
        $("#contentForOthersFeedbackForm").hide();
        $("#dashboard").hide();
        $("#nominateEmp").hide();
        $("#feedbackDashboard").hide();
        $("#allRespondentTableView").hide();	
        $("#respondantStatusTableView").hide();	
//		$("#landingPageContent").hide();
    });

    $(".nav #outbox1,#userHomePage #outbox1").on("click", function () {
    	$("#userHomePage").hide();
    	$('.content-wrapper, .right-side ').css('background','none');
    	$("#nominateEmp").show();
    	$('.nav li').removeClass('active');
		$('#outbox1').parent('li').addClass('active');
    	
    	$("#feedbackForm").hide();
        $("#contentForSelfFeedbackForm").hide();
        $("#contentForOthersFeedbackForm").hide();
        $("#dashboard").hide();
        $("#respondantTableView").hide();
        $("#feedbackDashboard").hide();
        $("#allRespondentTableView").hide();
		$("#respondantStatusTableView").hide();
		
//		$("#landingPageContent").hide();
        $.ajax({		
			type : "POST",		
			url : "RespondentController",		
			async:false,		
			data : {		
			     	message:"fetchRespondents",		
				    JDATA:null		
			},		
			success : function(data) {		
				console.log(data);
				if(data!='[]')
				populateExistingRespondents(jQuery.parseJSON(data));
//				sessioncheckforpopulatingdata(data);
			},		
			error : function(xhr, ajaxOptions, throwError) {		
				//add appropriate error messages		
				utilObj.showErrorMessageAlert("Error", throwError);
			}		
		});
        
      //to check survey start date and end date
        var surveyStartDate = new Date(window.startDate);
    	var surveyEndDate = new Date(window.endDate);
    	var today = new Date();
    	
    	if(!(today>=surveyStartDate&&today<=surveyEndDate)){
    		$("input#respondentSaveBtn").attr('disabled','disabled');
    		$("input#respondentSubmitBtn").attr('disabled','disabled');
    		$("#addMoreRespondentsBtnAfterSubmit").attr('disabled','disabled');
    		$('#selectRespondentTable input').attr('disabled','disabled');
    		alert("Survey End Date has already passed.");
    	}
		//to check survey start date and end date ends
    });

	$(".nav #outbox2,#userHomePage #outbox2").on("click", function(){		
		$("#userHomePage").hide();
		$('.content-wrapper, .right-side ').css('background','none');
		$('.nav li').removeClass('active');
		$('#outbox2').parent('li').addClass('active');
		
		$("#nominateEmp").hide();		
    	$("#feedbackForm").hide();	
        $("#contentForSelfFeedbackForm").hide();
        $("#contentForOthersFeedbackForm").hide();		
        $("#dashboard").hide();		
        $("#respondantTableView").hide();		
        $("#feedbackDashboard").hide();		
        $("#allRespondentTableView").hide();		
        $("#respondantStatusTableView").show();	
//    	$("#landingPageContent").hide();	
		
		 $.ajax({           
           type : "POST",             
           url : "RespondentController",            
           async:false,         
           data : {             
                  message:"fetchrespondentstatus",       
                     JDATA:null             
           },            
           success : function(data) {
                 userObj.bindRespondentStatusDetailsWithTable(jQuery.parseJSON(data));
//                 sessioncheckforpopulatingdata(data);
           },            
           error : function(xhr, ajaxOptions, throwError) {              
                    utilObj.showErrorMessageAlert("Error",throwError);
           }             
       });    
    });
	
/*	 $( 'input[name=respondentEmailId]' ).keyup(function(){
		 var str=$( 'input[name=respondentEmailId]' ).val();
		 var str=$(this).val();
		
			 availableTags=[];
			 $.ajax({
					type : "POST",
					url : "RespondentController",
					async:false,
					data : {
					     	message:"employeeMstrList",
						    JDATA:str
					},
					success : function(data) {
						globalVar = data;
//						sessioncheckforpopulatingdata(data);
					},
					error : function(xhr, ajaxOptions, throwError) {
						//add appropriate error messages
					}
					
				});
			 parsedEmpList = jQuery.parseJSON(globalVar);
				$.each(parsedEmpList,function(idx,obj){
					availableTags.push(obj.EmailId);
				});
				
				$( "input[name=respondentEmailId]" ).autocomplete({
				      source: availableTags
				    });
		 
	 });*/
	 
/*	 $( "input[name=respondentEmailId]" ).autocomplete({
	      source: availableTags
	 });*/
	
    $('[data-toggle="tooltip"]').tooltip();
    
    /*Add new row on click of add button*/
    $("div#nominateEmp").on("click","#addNewPeerRowBtn", function () {
            userObj.addNewPeerRow();
    });

    $("div#nominateEmp").on("click","#addNewSeniorRowBtn",function () {
            userObj.addNewSeniorRow();
    });

    $("div#nominateEmp").on("click","#addNewSubordinateRowBtn", function () {
            userObj.addNewSubordinateRow();
    });

    $("div#nominateEmp").on("click","#addNewOtherRowBtn", function () {
            userObj.addNewOtherRow();
    });

    
    
    $("#respondentSubmitBtn").on("click", function () {
    	
    	/*
    		On click of submit the following validations take place:
    		1. Check if atleast one category of Peers, others and subordinates is selected.
    		2. For any checked category see if all or none of the inputs in a single row are filled.
    	*/
    	
    	var selectAtleastOneCategory = false;
    	$.each($('input[type="checkbox"]'),function(idx,obj){
    		if($(obj).is(':checked')){
    			selectAtleastOneCategory = true;
    			return false;
    		}
    	});
    	if(!selectAtleastOneCategory){
    		utilObj.showErrorMessageAlert("Error","Please select atleast one category.");
    		return;
    	}
    	
    	
    	//checks if all fields in a row are filled...either fill all or none !!
    	//But only check this for superior and selected categories
    	
    	var emptySeniorFields=false;
    	$.each($('#seniorsTab form'),function(idx,obj){
    		if(utilObj.isEmpty($(obj).find('input[name=respondentLName]')) && 
    				utilObj.isEmpty( $(obj).find('input[name=respondentFName]')) &&
    				utilObj.isEmpty($(obj).find('input[name=respondentEmailId]')))
    			return true;

    		var respFName = $(obj).find('input[name=respondentFName]').val().trim();
    		if(utilObj.isEmpty( $(obj).find('input[name=respondentFName]'))){
    			emptySeniorFields=true;
    			utilObj.showErrorMessageAlert("Error","Enter First Name..!");
    			return false;
    		}else if(!(/^[a-zA-Z  ]*$/.test($(obj).find('input[name=respondentFName]').val()))){
    			emptySeniorFields=true;
    			utilObj.showErrorMessageAlert("Error","Name can contain alphabets only");
    			return false;
    		}
    		
    		var respLName = $(obj).find('input[name=respondentLName]').val().trim();
    		if(utilObj.isEmpty($(obj).find('input[name=respondentLName]'))){
    			emptySeniorFields=true;
    			utilObj.showErrorMessageAlert("Error","Enter Last Name..!");
    			return false;
    		}else if(!(/^[a-zA-Z  ]*$/.test($(obj).find('input[name=respondentLName]').val()))){
    			emptySeniorFields=true;
    			utilObj.showErrorMessageAlert("Error","Name can contain alphabets only");
    			return false;
    		}
    		
    		var respEmailId = $(obj).find('input[name=respondentEmailId]').val().trim();
    		if(!utilObj.isEmailValid(respEmailId))
    			{
    			emptySeniorFields=true;
    			utilObj.showErrorMessageAlert("Error","Enter valid Email-Id for "+respFName+" "+respLName+"..!");
    			return false;
    			}
    	});
    	if(emptySeniorFields){
    		return;
    	}
    	
    	
    	
    	//check if all inputs in a row are filled for selected category
    	var flag=false;
    	$.each($('input[type="checkbox"]:checked'),function(idx,obj){
    		var formsInASelectedCategory = $(this).parent().parent().find('form');
        	$.each(formsInASelectedCategory,function(idx,obj){
        		if(utilObj.isEmpty($(obj).find('input[name=respondentLName]')) && 
        				utilObj.isEmpty( $(obj).find('input[name=respondentFName]')) &&
        				utilObj.isEmpty($(obj).find('input[name=respondentEmailId]')))
        			return true;

        		var respFName = $(obj).find('input[name=respondentFName]').val().trim();

        		if(utilObj.isEmpty( $(obj).find('input[name=respondentFName]'))){
        			flag=true;
        			utilObj.showErrorMessageAlert("Error","Enter First Name..!");
        			return false;
        		}else if(!(/^[a-zA-Z  ]*$/.test($(obj).find('input[name=respondentFName]').val()))){
        			flag=true;
        			utilObj.showErrorMessageAlert("Error","Name can contain alphabets only");
        			return false;
        		}
        		
        		var respLName = $(obj).find('input[name=respondentLName]').val().trim();
        		if(utilObj.isEmpty($(obj).find('input[name=respondentLName]'))){
        			flag=true;
        			utilObj.showErrorMessageAlert("Error","Enter Last Name..!");
        			return false;
        		}else if(!(/^[a-zA-Z  ]*$/.test($(obj).find('input[name=respondentLName]').val()))){
        			flag=true;
        			utilObj.showErrorMessageAlert("Error","Name can contain alphabets only");
        			return false;
        		}
        		
        		var respEmailId = $(obj).find('input[name=respondentEmailId]').val().trim();
        		if(!utilObj.isEmailValid(respEmailId))
        			{
        			flag=true;
        			utilObj.showErrorMessageAlert("Error","Enter valid Email-Id for "+respFName+" "+respLName+"..!");
        			return false;
        			}
        	});
        	if(flag){
        		return false;
        	}
    	});
    	if(flag){
    		return;
    	}
    	

    	var validRespondentsFlag = userObj.validateRespondentSelection();//checks minimum criteria
        if(validRespondentsFlag){
        	var jsonDataToServer = '[';
        	$.each($('#seniorsTab form'),function(idx,obj){
        		var respFName = $(obj).find('input[name=respondentFName]').val().trim();
        		var respLName = $(obj).find('input[name=respondentLName]').val().trim();
        		var respEmailId = $(obj).find('input[name=respondentEmailId]').val().trim();
        		if(respFName.length!=0&&respLName.length!=0&&respEmailId.length!=0){
        			jsonDataToServer =  jsonDataToServer+ JSON.stringify($(obj).serializeJSON())+',';
        		}
        	});
        	
        	$.each($('input[type="checkbox"]:checked'),function(idx,obj){
        		var formsInASelectedCategory = $(this).parent().parent().find('form');
            	$.each(formsInASelectedCategory,function(idx,obj){
            		var respFName = $(obj).find('input[name=respondentFName]').val().trim();
            		var respLName = $(obj).find('input[name=respondentLName]').val().trim();
            		var respEmailId = $(obj).find('input[name=respondentEmailId]').val().trim();
            		if(respFName.length!=0&&respLName.length!=0&&respEmailId.length!=0){
            			jsonDataToServer =  jsonDataToServer+ JSON.stringify($(obj).serializeJSON())+',';
            		}
            	});
            	
        	});
        	
        	jsonDataToServer =  jsonDataToServer.slice(0,-1);
        	jsonDataToServer =  jsonDataToServer+ ']';
        	console.log(jsonDataToServer);

        	var seniorMultipleSelectionFlag = false;
        	var seniorSelfNominationFlag = false;        	
        	$.each($('#seniorsTab form input[name=respondentEmailId]'),function(idx,obj){
        		if($(obj).val().trim().length!=0){
        			if(userDataJson.emailId.toLowerCase() ===$(obj).val().trim().toLowerCase()){
        				utilObj.showErrorMessageAlert("Error","Cannot Select Self as Respondent.");
        				seniorSelfNominationFlag= true;
    	    			return false;
        			}
	        		if(checkForRepeatingEmailId(jsonDataToServer, $(obj).val().trim())){
	        			utilObj.showErrorMessageAlert("Error","Please enter each respondent only once.");
	        			seniorMultipleSelectionFlag = true;
	        			return false;
	        		}
        		}
        	});
        	if(seniorSelfNominationFlag){
        		return;
        	}
        	if(seniorMultipleSelectionFlag){
        		return;
        	}
        	
        	var multipleSelectionFlag = false;
        	var selfNominationFlag = false;
        	$.each($('input[type="checkbox"]:checked'),function(idx,obj){
        		var emailIdInputs = $(this).parent().parent().find('input[name=respondentEmailId]');
	        	$(emailIdInputs).each(function(index,obj ) {
	        		if($(obj).val().trim().length!=0){
	        			if(userDataJson.emailId.toLowerCase() ===$(obj).val().trim().toLowerCase()){
	        				utilObj.showErrorMessageAlert("Error","Cannot Select Self as Respondent.");
	    	    			selfNominationFlag= true;
	    	    			return false;
	        			}
		        		if(checkForRepeatingEmailId(jsonDataToServer, $(obj).val().trim())){
		        			utilObj.showErrorMessageAlert("Error","Please enter each respondent only once.");
		        			multipleSelectionFlag = true;
		        			return false;
		        		}
	        		}
	        	});
        	});
        	if(selfNominationFlag){
        		return;
        	}
        	if(multipleSelectionFlag){
        		return;
        	}

        	
        	$.ajax({
                type : "POST",
                url : "RespondentController",
                async:false,
                data : {
                        message:"insertRespondents",
                        JDATA:jsonDataToServer,
                        isSubmit:"true"
                },
                success : function(data) {
                    returnData = data;
                    
                    if(data=="OK"){
                    	//userObj.removeEditDeleteOptions();
                    	utilObj.showSuccessMessageAlert("Success", "Respondant nominated successfully.");
                    	$("#outbox1").trigger("click");
                    	//sessionCheck(data);
                    }else{
                    	//utilObj.showErrorMessageAlert("Error", data);
                    	sessionCheck(data);
                    }
                },
                error : function(xhr, ajaxOptions, throwError) {
                    utilObj.showErrorMessageAlert("Error", throwError);
                }
            });
        }/*Ankush 5 */
       // else
        //	utilObj.showErrorMessageAlert("Error", "Minimum criteria not satisfied..!");
        /*Ankush 5 */
        //$.isLoading({ text: "Please wait....", position: "overlay" });
        //$.isLoading("hide");
    });
    
    $("input#respondentSaveBtn").on("click", function () {
    	var jsonDataToServer = '[';
    	var flag=false;
    	$.each($('div#nominateEmp form'),function(idx,obj){
    		/*Ankush 1 */
    		if(!(utilObj.isEmpty($(obj).find('input[name=respondentLName]')) && 
    			utilObj.isEmpty( $(obj).find('input[name=respondentFName]')) &&
    			utilObj.isEmpty($(obj).find('input[name=respondentEmailId]'))))
    			{		
    					var respFName = $(obj).find('input[name=respondentFName]').val().trim();
			    		/*Ankush 2 */
			    		if(utilObj.isEmpty( $(obj).find('input[name=respondentFName]'))){
			    			flag=true;
			    			utilObj.showErrorMessageAlert("Error","Enter First Name..!");
			    			return false;
			    		}
			    		/*Ankush 2 */
			    		var respLName = $(obj).find('input[name=respondentLName]').val().trim();
			    		/*Ankush 3 */
			    		if(utilObj.isEmpty($(obj).find('input[name=respondentLName]'))){
			    			flag=true;
			    			utilObj.showErrorMessageAlert("Error","Enter Last Name..!");
			    			return false;
			    		}
			    		/*Ankush 3 */
			    		var respEmailId = $(obj).find('input[name=respondentEmailId]').val().trim();
			    		/*Ankush 4 */
			    		if(!utilObj.isEmailValid(respEmailId))
			    			{
			    			flag=true;
			    			utilObj.showErrorMessageAlert("Error","Enter valid Email-Id for "+respFName+" "+respLName+"..!");
			    			return false;
			    			}
			    		/*Ankush 4 */
			    		if(respFName.length!=0&&respLName.length!=0&&respEmailId.length!=0){
			    			jsonDataToServer =  jsonDataToServer+ JSON.stringify($(obj).serializeJSON())+',';
			    		}
    			}
    		});
    	/*Ankush 7*/
    	if(flag){
    		return;
    	}
    	/*Ankush 7*/
    	jsonDataToServer =  jsonDataToServer.slice(0,-1);
    	jsonDataToServer =  jsonDataToServer+ ']';
    	console.log(jsonDataToServer);
    	var multipleSelectionFlag = false;
    	var selfNominationFlag = false;
    	$('input[name=respondentEmailId]').each(function(index,obj ) {
    		
    		if($(obj).val().trim().length!=0){
    			if(userDataJson.emailId.toLowerCase() === $(obj).val().trim().toLowerCase()){
    				utilObj.showErrorMessageAlert("Error","Cannot Select Self as Respondent.");
	    			selfNominationFlag= true;
	    			return false;
    			}
    			
	    		if(checkForRepeatingEmailId(jsonDataToServer, $(obj).val().trim())){
	    			utilObj.showErrorMessageAlert("Error","Please enter each respondent only once.");
	    			multipleSelectionFlag = true;
	    			return false;
	    		}
    		}
    	});
    	if(selfNominationFlag){
    		return;
    	}
    	if(multipleSelectionFlag){
    		return;
    	}
    	if(jsonDataToServer==']'){
    		utilObj.showErrorMessageAlert("Error","Please enter atleast one respondent.");
    		return;
    	}
    	
    	$.ajax({
            type : "POST",
            url : "RespondentController",
            async:false,
            data : {
                    message:"insertRespondents",
                    JDATA:jsonDataToServer,
                    isSubmit:"false"
            },
            success : function(data) {
                returnData = data;
                if(returnData=="OK"){
                	utilObj.showSuccessMessageAlert("Success", data);
                	$("#outbox1").trigger("click");
                //	sessionCheck(data);
                }
                else
               // 	utilObj.showErrorMessageAlert("Error", data);
                sessionCheck(data);
            },
            error : function(xhr, ajaxOptions, throwError) {
                //add appropriate error messages
				utilObj.showErrorMessageAlert("Error", throwError);
            }
        });
    	
    });
    
    
    $(".tab-pane").on("click",".row span.deleteButtonHolder #deleterowbtn",function(){
    	var pointerToParentDiv = $(this).parents('.tab-pane');
    	var imgId = pointerToParentDiv.find('div.addBtnHolder img').attr('id');
    	pointerToParentDiv.find('div.addBtnHolder').remove();
    	if($(this).parents('.tab-pane').find('.row').length==1){
    		$(this).parents('.row .edit').find('form div input').removeAttr('readonly');
    		$(this).parents('.row .edit').find('form div input').val('');	
    		$(this).remove();
    	}else{
    		$(this).parents('.row .edit').remove(); 		
    	}
    	
    	$(pointerToParentDiv).find('div.content div.row').last().append('<div class="col-md-1 addBtnHolder">' +
    	        '<img title="Add" src="images/addBtn.png" width="20" id="'+imgId+'">' +
        '</div>');
    });
    
    $("#addtionalRespondentForms").on("click", "button#addMoreRespondentRow",function(){
    	
    	var formCount = $('#addtionalRespondentForms form').length;
    	$("#addMoreRespondentRow").remove();
	    var addMoreRespondentForm = '<form id="additionalRespondentForm'+(++formCount)+'" class="newRowForAddRespondents">'+
		   '<input type="hidden" name="respondentId" value="-1"/>'+
	       '<input type="hidden" name="operationType" value="SAVE"/>'+
			'<div class="col-md-12">' +
				'<div class="col-md-2">'+
					'<div class="form-group">'+
						'<input type="text" class="respondControl" name="respondentFName">'+
					'</div>'+
				'</div>'+
				'<div class="col-md-2"><div class="form-group">'+
						'<input type="text" class="respondControl" name="respondentLName">'+
					'</div></div>'+
				'<div class="col-md-3">'+
					'<div class="form-group">'+
						'<input type="text" class="respondControl" name="respondentEmailId">'+
					'</div> </div>'+
				
				'<div class="col-md-3">'+
					'<div class="form-group">'+
						'<select class="respondControl"  name="empRelation">'+
							'<option value="-1">Select Respondent Type</option>'+
							'<option value="SENIOR">Senior</option>'+
							'<option value="PEER">Peer</option>'+
							'<option value="SUBORDINATE">Subordinate</option>'+
							'<option value="OTHER">Other</option>'+
						'</select>'+
					'</div>'+
				'</div>'+
				'<div class="col-md-2 addMoreRespondentRowsBtnDiv">'+
						'<button type="button" class="btn addBtn" id="addMoreRespondentRow">'+
							'<span class="glyphicon glyphicon-plus-sign"></span>'+
						'</button>'+
				'</div>'+
			'</div>'+
		'</form>';	
	    
	    $('#addtionalRespondentForms').append(addMoreRespondentForm);
	    
    });
    $('#addMoreRespondentsBtnAfterSubmit').click(function() {
    	$("#addtionalRespondentForms form.newRowForAddRespondents").remove();
    	$("#addtionalRespondentForms .addMoreRespondentRowsBtnDiv").empty();
    	var addMoreRespBtl = '<div class="form-group">'+
 		'<button type="button" class="btn addBtn" id="addMoreRespondentRow">'
												+ '<span class="glyphicon glyphicon-plus-sign"></span>'
												+ '</button>' + '</div>';
    	$("#addtionalRespondentForms .addMoreRespondentRowsBtnDiv").append(addMoreRespBtl);
    	$('#addtionalRespondentForms input[type!=hidden]').each(function(idx,obj){ 
    		$(obj).val('');
    	});
    	$('#addtionalRespondentForms select[name=empRelation]').prop('selectedIndex',0);
    });
    $('#submitAdditionalRespondentsBtn').click(function(){

    	var emptyFields=false;
    	$("#addtionalRespondentForms form").each(function(idx,obj){
        	if(utilObj.isEmpty($(obj).find('input[name=respondentLName]')) && 
    				utilObj.isEmpty( $(obj).find('input[name=respondentFName]')) &&
    				utilObj.isEmpty($(obj).find('input[name=respondentEmailId]'))&&($(obj).find('select[name=empRelation]').val()!=-1))
    			return true;

    		var respFName = $(obj).find('input[name=respondentFName]').val().trim();
    		if(utilObj.isEmpty( $(obj).find('input[name=respondentFName]'))){
    			emptyFields=true;
    			utilObj.showErrorMessageAlert("Error","Enter First Name..!");
    			return false;
    		}
    		var respLName = $(obj).find('input[name=respondentLName]').val().trim();
    		if(utilObj.isEmpty($(obj).find('input[name=respondentLName]'))){
    			emptyFields=true;
    			utilObj.showErrorMessageAlert("Error","Enter Last Name..!");
    			return false;
    		}
    		var respEmailId = $(obj).find('input[name=respondentEmailId]').val().trim();
    		if(!utilObj.isEmailValid(respEmailId))
    			{
    			emptyFields=true;
    			utilObj.showErrorMessageAlert("Error","Enter valid Email-Id for "+respFName+" "+respLName+"..!");
    			return false;
    			}
    		var respEmpCat = $(obj).find('select[name=empRelation]').val();
    		if(respEmpCat==-1){
    			emptyFields=true;
    			utilObj.showErrorMessageAlert("Error","Select Respondent Category for "+respFName+" "+respLName+"..!");
    			return false;
    		}
    	});
    	if(emptyFields){
    		return;
    	}
    	
    	
    	var jsonDataPrevRespondents = '[';
    	$.each($('#seniorsTab form'),function(idx,obj){
    		var respFName = $(obj).find('input[name=respondentFName]').val().trim();
    		var respLName = $(obj).find('input[name=respondentLName]').val().trim();
    		var respEmailId = $(obj).find('input[name=respondentEmailId]').val().trim();
    		if(respFName.length!=0&&respLName.length!=0&&respEmailId.length!=0){
    			jsonDataPrevRespondents =  jsonDataPrevRespondents+ JSON.stringify($(obj).serializeJSON())+',';
    		}
    	});
    	
    	$.each($('input[type="checkbox"]:checked'),function(idx,obj){
    		var formsInASelectedCategory = $(this).parent().parent().find('form');
        	$.each(formsInASelectedCategory,function(idx,obj){
        		var respFName = $(obj).find('input[name=respondentFName]').val().trim();
        		var respLName = $(obj).find('input[name=respondentLName]').val().trim();
        		var respEmailId = $(obj).find('input[name=respondentEmailId]').val().trim();
        		if(respFName.length!=0&&respLName.length!=0&&respEmailId.length!=0){
        			jsonDataPrevRespondents =  jsonDataPrevRespondents+ JSON.stringify($(obj).serializeJSON())+',';
        		}
        	});
        	
    	});
    	$('#addtionalRespondentForms form').each(function(idx,obj){
    		jsonDataPrevRespondents = jsonDataPrevRespondents + JSON.stringify($('#'+$(obj).attr('id')).serializeJSON());
    		jsonDataPrevRespondents = jsonDataPrevRespondents +',';
    	});

    	
    	jsonDataPrevRespondents =  jsonDataPrevRespondents.slice(0,-1);
    	jsonDataPrevRespondents =  jsonDataPrevRespondents+ ']';

    	
    	
    	
    	
    	var multipleSelectionFlag = false;
    	var selfNominationFlag = false;
    	$.each($('#addtionalRespondentForms input[name=respondentEmailId]'),function(idx,obj){
        		if($(obj).val().trim().length!=0){
        			if(userDataJson.emailId.toLowerCase() ===$(obj).val().trim().toLowerCase()){
        				utilObj.showErrorMessageAlert("Error","Cannot Select Self as Respondent.");
    	    			selfNominationFlag= true;
    	    			return false;
        			}
	        		if(checkForRepeatingEmailId(jsonDataPrevRespondents, $(obj).val().trim())){
	        			utilObj.showErrorMessageAlert("Error","Please enter each respondent only once.");
	        			multipleSelectionFlag = true;
	        			return false;
	        		}
        		}
    	});
    	if(selfNominationFlag){
    		return;
    	}
    	if(multipleSelectionFlag){
    		return;
    	}

    	var jsonDataToServer = '[';
    	$('#addtionalRespondentForms form').each(function(idx,obj){
    		jsonDataToServer = jsonDataToServer + JSON.stringify($('#'+$(obj).attr('id')).serializeJSON());
    		jsonDataToServer = jsonDataToServer +',';
    	});
    	jsonDataToServer = jsonDataToServer.slice(0,-1);
    	jsonDataToServer = jsonDataToServer +']';
    	console.log("more respondents added - "+jsonDataToServer);
    	
    	
    	$.ajax({
            type : "POST",
            url : "RespondentController",
            async:false,
            data : {
                    message:"insertRespondentsAfterSubmit",
                    JDATA:jsonDataToServer
            },
            success : function(data) {
                returnData = data;
                if(returnData=="OK"){
                	utilObj.showSuccessMessageAlert("Success", data);
                	$("#outbox1").trigger("click");
                //	sessionCheck(data);
                }
                else
               // 	utilObj.showErrorMessageAlert("Error", data);
                sessionCheck(data);
            },
            error : function(xhr, ajaxOptions, throwError) {
                //add appropriate error messages
				utilObj.showErrorMessageAlert("Error", throwError);
            }
        });
    	$('#moreResponent').modal('hide');
    	
    });
    
/*    $( "input[name=respondentEmailId]" ).on( "autocompleteselect", function( event, ui ) {
    	var firstName='';
    	var lastName='';
    	var emailValue= ui.item.label;
    	//console.log("Object value"+$(this).val);
        $(jQuery.parseJSON(globalVar)).each(function(idx,obj) {
       	 if(obj.EmailId == ui.item.value)
       		 {
       		 	firstName = obj.firstName;
       		 	lastName = obj.lastName;
       		 	return;
       		 }
       	});

    	$(this).parent().parent().find('.fname').val(firstName);
    	$(this).parent().parent().find('.lname').val(lastName);
    } );*/
    
});
//document ready ends

function checkForRepeatingEmailId(jsonData,emailId){
	var matchingRecords = jsonData.match(new RegExp(emailId,'ig'));
	if(matchingRecords.length>1)
		return true;
	else
		return false;
}


function populateExistingRespondents(temp){
	var peerCount=0;
	var seniorCount=0;
	var subordinateCount=0;
	var otherCount=0;
	var feedbackStatus='';
	$.each(temp,function(idx,obj){
		var container;
		feedbackStatus = obj.feedbackStatus;
		if(obj.empRelation == 'PEER'||obj.empRelation == 'peer'){
			container = $('div#peersTab div.content');
			if(peerCount==0)
				container.empty();
			peerCount++;
		}else if(obj.empRelation == 'SENIOR'||obj.empRelation == 'senior'){
			container = $('div#seniorsTab div.content');
			if(seniorCount==0)
				container.empty();
			seniorCount++;
		}else if(obj.empRelation == 'SUBORDINATE'||obj.empRelation == 'subordinate'){
			container = $('div#subordinatesTab div.content');
			if(subordinateCount==0)
				container.empty();
			subordinateCount++;
		}else if(obj.empRelation == 'OTHER'||obj.empRelation == 'other'){
			container = $('div#othersTab div.content');
			if(otherCount==0)
				container.empty();
			otherCount++;
		}
		var newRowHtml = '<div class="row edit">' +
        '<div class="col-md-11 col-xs-11'+' '+obj.empRelation+'">' +
        '<form class="'+obj.empRelation+'">' +
        '<input type="hidden" name="respondentId" value="'+obj.respondentId+'"/>' +
    	'<input type="hidden" name="empRelation" value="'+obj.empRelation+'"/>' +
    	'<input type="hidden" name="operationType" value="SAVE" />' +
        '<div class="col-md-3 col-xs-3"><input type="text" class="form-control fname" name="respondentFName" placeholder="First Name" value ="'+obj.respondentFName+'" readonly/></div>' +
        '<div class="col-md-3 col-xs-3"><input type="text" class="form-control lname" name="respondentLName" placeholder="Last Name" value ="'+obj.respondentLName+'" readonly/></div>' +
        '<div class="col-md-6 col-xs-6" id="zoom-effect-container"><input type="text" class="form-control emailId emailId ui-autocomplete-input" name="respondentEmailId" placeholder="Email ID" value ="'+obj.respondentEmailId+'" readonly/> <span class="deleteButtonHolder"></span></div>' +
        '</form>' +
        '</div>' +
        '<div class="col-md-1 col-xs-1 deleteBtnHolder">' +
        '</div>' +
        '</div>';
		$(container).append(newRowHtml);
	});
	
	if(feedbackStatus=='PENDING_WITH_EMP'){
	$(".tab-pane").find(".row")/*.on("click", function () {
        if ($(this).hasClass('editableRow')) {
            //alert('yes it has');
        } else {
            userObj.addEditDeleteOptions(this);
        } 
    })*/.on("click","#addNewSeniorRowBtn",function () {
            userObj.addNewSeniorRow();
            return false;
    }).on("click","#addNewPeerRowBtn",function () {
        userObj.addNewPeerRow();
        return false;
    }).on("click","#addNewSubordinateRowBtn",function () {
        userObj.addNewSubordinateRow();
        return false;
    }).on("click","#addNewOtherRowBtn",function () {
        userObj.addNewOtherRow();
        return false;
    });
	
	$(".tab-pane").find(".row span.deleteButtonHolder").html('<img src="images/cross.png" title="Delete" width="15" id="deleterowbtn">');
	
	
	if(peerCount!=0)
	$('div#peersTab div.content div.row').last().append('<div class="col-md-1 addBtnHolder">' +
	        '<img title="Add" src="images/addBtn.png" width="20" id="addNewPeerRowBtn">' +
	        '</div>');
	if(seniorCount!=0)
	$('div#seniorsTab div.content div.row').last().append('<div class="col-md-1 addBtnHolder">' +
			'<img title="Add" src="images/addBtn.png" width="20" id="addNewSeniorRowBtn">' +
	'</div>');
	if(subordinateCount!=0)
	$('div#subordinatesTab div.content div.row').last().append('<div class="col-md-1 addBtnHolder">' +
			'<img title="Add" src="images/addBtn.png" width="20" id="addNewSubordinateRowBtn">' +
	'</div>');
	if(otherCount!=0)
	$('div#othersTab div.content div.row').last().append('<div class="col-md-1 addBtnHolder">' +
			'<img title="Add" src="images/addBtn.png" width="20" id="addNewOtherRowBtn">' +
	'</div>');
	}else{
		$("input#respondentSaveBtn").attr("disabled","disabled");
		$("input#respondentSubmitBtn").attr("disabled","disabled");
		$("#addMoreRespondentsBtnAfterSubmit").css("display","block");
	}
}


function User() {
    if (!(this instanceof User)) {
        return new User();
    }

    //this.kontappsURL = 'http://kontappsapi.azurewebsites.net/';
    //this.kontappsURL = 'http://localhost:40899/';
    //this.apiUrl = "";

    //ADD NEW ROW FOR RESPONDENT SELECTION STARTS
    User.prototype.addNewPeerRow = function () {
        var newRowHtml = '<div class="row">'+
                            '<div class="col-md-11 col-xs-11 peer">' +
                            '<form class="peer">' +
                            '<input type="hidden" name="respondentId" value="-1"/>' +
                        	'<input type="hidden" name="empRelation" value="PEER"/>' +
                        	'<input type="hidden" name="operationType" value="SAVE"/>' +
                            '<div class="col-md-3 col-xs-3"><input type="text" class="form-control fname" name="respondentFName" placeholder="First Name"></div>' +
                            '<div class="col-md-3 col-xs-3"><input type="text" class="form-control lname" name="respondentLName" placeholder="Last Name"></div>' +
                            '<div class="col-md-6 col-xs-6"><input type="text" class="form-control emailId ui-autocomplete-input" name="respondentEmailId" placeholder="Email ID"></div>' +
                            '</form>' +
                            '</div>'+
                            '<div class="col-md-1 addBtnHolder">'+
                            '<img src="images/addBtn.png" width="20" id="addNewPeerRowBtn">' +
                            '</div>'+
                            '</div>';

        $("#peersTab .content").find(".addBtnHolder").empty();
        $("#peersTab").find(".content").append(newRowHtml);
        $("#addNewPeerRowBtn").on("click", function () {
            userObj.addNewPeerRow();
        });
        /*$( 'input[name=respondentEmailId]' ).keyup(function(){
    		 var str=$( 'input[name=respondentEmailId]' ).val();
    		 var str=$(this).val();
    		
    			 availableTags=[];
    			 $.ajax({
    					type : "POST",
    					url : "RespondentController",
    					async:false,
    					data : {
    					     	message:"employeeMstrList",
    						    JDATA:str
    					},
    					success : function(data) {
    						globalVar = data;
//    						sessioncheckforpopulatingdata(data);
    					},
    					error : function(xhr, ajaxOptions, throwError) {
    						//add appropriate error messages
    					}
    					
    				});
    			 parsedEmpList = jQuery.parseJSON(globalVar);
    				$.each(parsedEmpList,function(idx,obj){
    					availableTags.push(obj.EmailId);
    				});
    				
    		 
    		 
    		 //$(function() {
    			    $( "input[name=respondentEmailId]" ).autocomplete({
    			      source: availableTags,
    			      select:function( event, ui ) {
    				      	var firstName='';
    				    	var lastName='';
    				    	var emailValue= ui.item.label;
    				    	//console.log("Object value"+$(this).val);
    				        $(jQuery.parseJSON(globalVar)).each(function(idx,obj) {
    				       	 if(obj.EmailId == ui.item.value)
    				       		 {
    				       		 	firstName = obj.firstName;
    				       		 	lastName = obj.lastName;
    				       		 	return;
    				       		 }
    				       	});

    				    	$(this).parent().parent().find('.fname').val(firstName);
    				    	$(this).parent().parent().find('.lname').val(lastName);
    				    }
    			    });
    			//  });
    	 });
*/     /*   $( "input[name=respondentEmailId]" ).autocomplete({
		      source: availableTags,
		      select:function( event, ui ) {
		      	var firstName='';
		    	var lastName='';
		    	var emailValue= ui.item.label;
		    	//console.log("Object value"+$(this).val);
		        $(jQuery.parseJSON(globalVar)).each(function(idx,obj) {
		       	 if(obj.EmailId == ui.item.value)
		       		 {
		       		 	firstName = obj.firstName;
		       		 	lastName = obj.lastName;
		       		 	return;
		       		 }
		       	});

		    	$(this).parent().parent().find('.fname').val(firstName);
		    	$(this).parent().parent().find('.lname').val(lastName);
		    }
		    });*/
    };

    User.prototype.addNewSeniorRow = function () {
        var newRowHtml = '<div class="row">' +
                            '<div class="col-md-11 col-xs-11 senior">' +
                            '<form class="senior">' +
                            '<input type="hidden" name="respondentId" value="-1"/>' +
                        	'<input type="hidden" name="empRelation" value="SENIOR"/>' +
                        	'<input type="hidden" name="operationType" value="SAVE" />' +
                            '<div class="col-md-3 col-xs-3"><input type="text" class="form-control fname" name="respondentFName" placeholder="First Name"></div>' +
                            '<div class="col-md-3 col-xs-3"><input type="text" class="form-control lname" name="respondentLName" placeholder="Last Name"></div>' +
                            '<div class="col-md-6 col-xs-6"><input type="text" class="form-control emailId ui-autocomplete-input" name="respondentEmailId" placeholder="Email ID"></div>' +
                            '</form>' +
                            '</div>' +
                            '<div class="col-md-1 addBtnHolder">' +
                            '<img src="images/addBtn.png" width="20" id="addNewSeniorRowBtn">' +
                            '</div>' +
                            '</div>';

        $("#seniorsTab .content").find(".addBtnHolder").empty();
        $("#seniorsTab").find(".content").append(newRowHtml);
        $("#addNewSeniorRowBtn").on("click", function () {
            userObj.addNewSeniorRow();
        });
       /* $( 'input[name=respondentEmailId]' ).keyup(function(){
        	
    		 var str=$( 'input[name=respondentEmailId]' ).val();
    		 var str=$(this).val();
    		
    			 availableTags=[];
    			 $.ajax({
    					type : "POST",
    					url : "RespondentController",
    					async:false,
    					data : {
    					     	message:"employeeMstrList",
    						    JDATA:str
    					},
    					success : function(data) {
    						globalVar = data;
//    						sessioncheckforpopulatingdata(data);
    					},
    					error : function(xhr, ajaxOptions, throwError) {
    						//add appropriate error messages
    					}
    					
    				});
    			 parsedEmpList = jQuery.parseJSON(globalVar);
    				$.each(parsedEmpList,function(idx,obj){
    					availableTags.push(obj.EmailId);
    				});
    				
    		 
    		 
//    		 $(function() {
    			    $( "input[name=respondentEmailId]" ).autocomplete({
    			      source: availableTags,
    			      select:function( event, ui ) {
    				      	var firstName='';
    				    	var lastName='';
    				    	var emailValue= ui.item.label;
    				    	//console.log("Object value"+$(this).val);
    				        $(jQuery.parseJSON(globalVar)).each(function(idx,obj) {
    				       	 if(obj.EmailId == ui.item.value)
    				       		 {
    				       		 	firstName = obj.firstName;
    				       		 	lastName = obj.lastName;
    				       		 	return;
    				       		 }
    				       	});

    				    	$(this).parent().parent().find('.fname').val(firstName);
    				    	$(this).parent().parent().find('.lname').val(lastName);
    				    }
    			    });
//    			  });
    	 });    
*/       /*  $( "input[name=respondentEmailId]" ).autocomplete({
		      source: availableTags,
		      select:function( event, ui ) {
			      	var firstName='';
			    	var lastName='';
			    	var emailValue= ui.item.label;
			    	//console.log("Object value"+$(this).val);
			        $(jQuery.parseJSON(globalVar)).each(function(idx,obj) {
			       	 if(obj.EmailId == ui.item.value)
			       		 {
			       		 	firstName = obj.firstName;
			       		 	lastName = obj.lastName;
			       		 	return;
			       		 }
			       	});

			    	$(this).parent().parent().find('.fname').val(firstName);
			    	$(this).parent().parent().find('.lname').val(lastName);
			    }
		 });*/
    
    };

    User.prototype.addNewSubordinateRow = function () {
        var newRowHtml = '<div class="row">' +
                            '<div class="col-md-11 col-xs-11 subordinate">' +
                            '<form class="subordinate">' +
                            '<input type="hidden" name="respondentId" value="-1"/>' +
                        	'<input type="hidden" name="empRelation" value="SUBORDINATE"/>' +
                        	'<input type="hidden" name="operationType" value="SAVE" />' +
                            '<div class="col-md-3 col-xs-3"><input type="text" class="form-control fname" name="respondentFName" placeholder="First Name"></div>' +
                            '<div class="col-md-3 col-xs-3"><input type="text" class="form-control lname" name="respondentLName" placeholder="Last Name"></div>' +
                            '<div class="col-md-6 col-xs-6"><input type="text" class="form-control emailId ui-autocomplete-input" name="respondentEmailId" placeholder="Email ID"></div>' +
                            '</form>' +
                            '</div>' +
                            '<div class="col-md-1 addBtnHolder">' +
                            '<img src="images/addBtn.png" width="20" id="addNewSubordinateRowBtn">' +
                            '</div>' +
                            '</div>';

        $("#subordinatesTab .content").find(".addBtnHolder").empty();
        $("#subordinatesTab").find(".content").append(newRowHtml);
        $("#addNewSubordinateRowBtn").on("click", function () {
            userObj.addNewSubordinateRow();
        });
        /*$( 'input[name=respondentEmailId]' ).keyup(function(){
    		 var str=$( 'input[name=respondentEmailId]' ).val();
    		 var str=$(this).val();
    		
    			 availableTags=[];
    			 $.ajax({
    					type : "POST",
    					url : "RespondentController",
    					async:false,
    					data : {
    					     	message:"employeeMstrList",
    						    JDATA:str
    					},
    					success : function(data) {
    						globalVar = data;
//    						sessioncheckforpopulatingdata(data);
    					},
    					error : function(xhr, ajaxOptions, throwError) {
    						//add appropriate error messages
    					}
    					
    				});
    			 parsedEmpList = jQuery.parseJSON(globalVar);
    				$.each(parsedEmpList,function(idx,obj){
    					availableTags.push(obj.EmailId);
    				});
    				
    		 
    		 
//    		 $(function() {
    			    $( "input[name=respondentEmailId]" ).autocomplete({
    			      source: availableTags
    			    });
//    			  });
    	 });*/    
        /*$( "input[name=respondentEmailId]" ).autocomplete({
		      source: availableTags
		 });*/
    
    };

    User.prototype.addNewOtherRow = function () {
        var newRowHtml = '<div class="row">' +
                            '<div class="col-md-11 col-xs-11 other">' +
                            '<form class="other"> ' +
                            '<input type="hidden" name="respondentId" value="-1"/>' +
                        	'<input type="hidden" name="empRelation" value="OTHER"/>' +
                        	'<input type="hidden" name="operationType" value="SAVE" />' +
                            '<div class="col-md-3 col-xs-3"><input type="text" class="form-control fname" name="respondentFName" placeholder="First Name"></div>' +
                            '<div class="col-md-3 col-xs-3"><input type="text" class="form-control lname" name="respondentLName" placeholder="Last Name"></div>' +
                            '<div class="col-md-6 col-xs-6"><input type="text" class="form-control emailId ui-autocomplete-input" name="respondentEmailId" placeholder="Email ID"></div>' +
                            '</form>' +
                            '</div>' +
                            '<div class="col-md-1 addBtnHolder">' +
                            '<img src="images/addBtn.png" width="20" id="addNewOtherRowBtn">' +
                            '</div>' +
                            '</div>';

        $("#othersTab .content").find(".addBtnHolder").empty();
        $("#othersTab").find(".content").append(newRowHtml);
        $("#addNewOtherRowBtn").on("click", function () {
            userObj.addNewOtherRow();
        });
/*        $( 'input[name=respondentEmailId]' ).keyup(function(){
    		 var str=$( 'input[name=respondentEmailId]' ).val();
    		 var str=$(this).val();
    		
    			 availableTags=[];
    			 $.ajax({
    					type : "POST",
    					url : "RespondentController",
    					async:false,
    					data : {
    					     	message:"employeeMstrList",
    						    JDATA:str
    					},
    					success : function(data) {
    						globalVar = data;
//    						sessioncheckforpopulatingdata(data);
    					},
    					error : function(xhr, ajaxOptions, throwError) {
    						//add appropriate error messages
    					}
    					
    				});
    			 parsedEmpList = jQuery.parseJSON(globalVar);
    				$.each(parsedEmpList,function(idx,obj){
    					availableTags.push(obj.EmailId);
    				});
    				
    		 
    		 
//    		 $(function() {
    			    $( "input[name=respondentEmailId]" ).autocomplete({
    			      source: availableTags,
    			      select:function( event, ui ) {
    				      	var firstName='';
    				    	var lastName='';
    				    	var emailValue= ui.item.label;
    				    	//console.log("Object value"+$(this).val);
    				        $(jQuery.parseJSON(globalVar)).each(function(idx,obj) {
    				       	 if(obj.EmailId == ui.item.value)
    				       		 {
    				       		 	firstName = obj.firstName;
    				       		 	lastName = obj.lastName;
    				       		 	return;
    				       		 }
    				       	});

    				    	$(this).parent().parent().find('.fname').val(firstName);
    				    	$(this).parent().parent().find('.lname').val(lastName);
    				    }
    			    });
//    			  });
    	 });*/
/*        $( "input[name=respondentEmailId]" ).autocomplete({
		      source: availableTags,
		      select:function( event, ui ) {
			      	var firstName='';
			    	var lastName='';
			    	var emailValue= ui.item.label;
			    	//console.log("Object value"+$(this).val);
			        $(jQuery.parseJSON(globalVar)).each(function(idx,obj) {
			       	 if(obj.EmailId == ui.item.value)
			       		 {
			       		 	firstName = obj.firstName;
			       		 	lastName = obj.lastName;
			       		 	return;
			       		 }
			       	});

			    	$(this).parent().parent().find('.fname').val(firstName);
			    	$(this).parent().parent().find('.lname').val(lastName);
			    }
		 });*/
        };
//ADD NEW ROW FOR RESPONDENT SELECTION ENDS

    //Validate Respondent fields
    User.prototype.validatePeerRespondentFields = function (formName) {
        //TO DO : Add code to validate input fields
        $("#addNewOtherRowBtn").on("click", function () {
            userObj.addNewOtherRow();
        });
    };

/*    User.prototype.addEditDeleteOptions = function (rowToEdit) {
        //var tagType = $(rowToEdit).find('input[name="respondentFName"]').prop("tagName").toLowerCase();
        var rowType = $(rowToEdit).hasClass('edit');
        var isFNameEmpty, isLNameEmpty, isEmailIDEmpty;        
        
        isFNameEmpty = utilObj.isEmpty($(rowToEdit).find('input[name="respondentFName"]'));
        isLNameEmpty = utilObj.isEmpty($(rowToEdit).find('input[name="respondentLName"]'));
        isEmailIDEmpty = utilObj.isEmpty($(rowToEdit).find('input[name="respondentEmailId"]'));
        		
        //Check if fields are empty       
        if((isFNameEmpty != true || isLNameEmpty!= true || isEmailIDEmpty != true)){
        	//Find the element that has editableRow class and remove the editableRow class.
        	userObj.removeEditDeleteOptions();
            $(rowToEdit).addClass("editableRow");
            $(rowToEdit).find('input[name="respondentFName"]').focus();
            $(rowToEdit).find('.deleteBtnHolder').empty();
            $(rowToEdit).find('.deleteBtnHolder').append('<a href="#" id="editRowBtn" class=""><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a><a href="#" id="deleteRowBtn" class=""><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>');
            $(".deleteBtnHolder").hide();
            $(rowToEdit).find(".deleteBtnHolder").show();
        }else{   	
        	//TO DO: write code if row is empty.
        }

        //Click events
        $("#editRowBtn").on("click", function (rowToEdit) {
            userObj.editRow(rowToEdit);
        });

        $("#deleteRowBtn").on("click", function (rowToEdit) {
            userObj.deleteRow(rowToEdit);
        });
        
    };

    User.prototype.removeEditDeleteOptions = function () {
        //$('.editableRow').find('p').removeAttr('contenteditable');
    	$('.editableRow').find('input[type="text"]').attr("readonly", "readonly");
    	$('.editableRow').find('input[type="email"]').attr("readonly", "readonly");
    	$('.editableRow').find('input[name="respondentFName"]').focus();
        $('.editableRow').find('.deleteBtnHolder').empty();
        $('.editableRow').removeClass("editableRow");
        $('.tab-pane').find('row').removeClass("editableRow");
    };

    User.prototype.editRow = function (rowToEdit) {    	
        //Apply readonly attribute to input tags.
        $('.editableRow').find('input[type="text"]').removeAttr("readonly");
        $('.editableRow').find('input[type="email"]').removeAttr("readonly");
        $('.editableRow').find('input[name="respondentFName"]').focus();
    };

    User.prototype.deleteRow = function (rowToEdit) {
        utilObj.showConfirmAlert(function (isConfirm) {
            if (isConfirm) {
            	$('.editableRow form input[name=operationType]').val('DELETE');
            	var jsonDataToServer = JSON.stringify($('.editableRow form').serializeJSON());
            	jsonDataToServer = '['+jsonDataToServer+']';
            	var empRelation = $('.editableRow form').serializeJSON().empRelation;

            	$.ajax({
                    type : "POST",
                    url : "RespondentController",
                    async:false,
                    data : {
                            message:"insertRespondents",
                            JDATA:jsonDataToServer
                    },
                    success : function(data) {
                        if(data=='OK'){
	                        $('.editableRow').empty();
	                        //userObj.removeEditDeleteOptions();
	                        
	                        if(empRelation == "SENIOR"){
	                                userObj.addNewSeniorRow();
	                        }
	                        if(empRelation == "PEER"){
	                        	userObj.addNewPeerRow();
	                        }
	                        if(empRelation == "SUBORDINATE"){
	                        	userObj.addNewSubordinateRow();
	                        }
	                        if(empRelation == "OTHER"){
	                        	userObj.addNewOtherRow();
	                        }
            		       
	                        utilObj.showSuccessMessageAlert("Deleted!", "Your data row has been deleted.");
                        }
                    },
                    error : function(xhr, ajaxOptions, throwError) {
                        utilObj.showErrorMessageAlert("Error", throwError);
                    }
                });
            	
            }
            else {
                utilObj.showErrorMessageAlert("Cancelled", "Your data row is safe :)");
                //swal("Cancelled", "Your data row is safe :)", "error");
            }
        });     
    };
*/
    User.prototype.validateRespondentSelection = function () {
        var peerCount = 0;
        var seniorCount = 0;
        var subordinateCount = 0;
        var othersCount = 0;

        var peers = $("div#peersTab form.peer,form.PEER");
        var seniors = $("div#seniorsTab form.senior,form.SENIOR");
        var subordinates = $("div#subordinatesTab form.subordinate,form.SUBORDINATE");
        var others = $("div#othersTab form.other,form.OTHER");

        /*if (peers.length < 3 || seniors.length < 1 || others.length < 3 || subordinates.length < 3) {
            return false;
        } else {*/
            //check peer count
            $.each(peers, function (idx, obj) {
                var inputFields = $(obj).find('input[type!=hidden]');
                var flag = true;
                for (var i = 0; i < inputFields.length; i++) {
                    if (inputFields[i].value == "" || inputFields[i].value == null) {
                        flag = false;
                        break;
                    }

                }
                if (flag) {
                    peerCount++;
                }

            });

            //check senior count
            $.each(seniors, function (idx, obj) {
                var inputFields = $(obj).find('input[type!=hidden]');
                var flag = true;
                for (var i = 0; i < inputFields.length; i++) {
                    if (inputFields[i].value == "" || inputFields[i].value == null) {
                        flag = false;
                        break;
                    }

                }
                if (flag) {
                    seniorCount++;
                }

            });

            //check subordinate count
            $.each(subordinates, function (idx, obj) {
                var inputFields = $(obj).find('input[type!=hidden]');
                var flag = true;
                for (var i = 0; i < inputFields.length; i++) {
                    if (inputFields[i].value == "" || inputFields[i].value == null) {
                        flag = false;
                        break;
                    }

                }
                if (flag) {
                    subordinateCount++;
                }

            });

            //check others count
            $.each(others, function (idx, obj) {
                var inputFields = $(obj).find('input[type!=hidden]');
                var flag = true;
                for (var i = 0; i < inputFields.length; i++) {
                    if (inputFields[i].value == "" || inputFields[i].value == null) {
                        flag = false;
                        break;
                    }

                }
                if (flag) {
                    othersCount++;
                }

            });

            /*if (peerCount < 3 || seniorCount < 1 || othersCount < 3 || subordinateCount < 3) {
                return false;
            }
            else {
                return true;
            }*/
            if(seniorCount < 1){
            	utilObj.showErrorMessageAlert("Error","Minimum 1 superior required.");
            	return false;
            }else if($('input[name="peersTab"]').is(':checked')&&peerCount < 3){
            	utilObj.showErrorMessageAlert("Error","Minimum 3 Peers should be selected.");
            	return false;
            }else if($('input[name="subordinatesTab"]').is(':checked')&&subordinateCount < 1){
            	utilObj.showErrorMessageAlert("Error","Minimum 1 Subordinate should be selected.");
            	return false;
            }else if($('input[name="othersTab"]').is(':checked')&&othersCount<3){
            	utilObj.showErrorMessageAlert("Error","Minimum 3 members required into others category.");
            	return false;
            }else{
            	return true;
            }
    };
    
    /*-------------------------------------------------------DISPLAY PEERS--------------------------------------------------*/		
	User.prototype.getEmployees=function(){
	//User.prototype.getEmployees=function(empRelation){		
		   var employeeList ="";
		   $.ajax({		
			type : "POST",		
			url : "FeedbackController",		
			async:false,		
			data : {		
				message:"fetchEmployees",		
				//JDATA:'{"empRelation":"'+empRelation+'"}'	
				JDATA:null	
			},		
			success : function(data) {		
				employeeList = jQuery.parseJSON(data);		
						
				console.log(employeeList);		
						
				userObj.bindRespondentRequestsDetailsWithTable(employeeList);	
//				sessioncheckforpopulatingdata(data);
			},		
			error : function(xhr, ajaxOptions, throwError) {		
				utilObj.showErrorMessageAlert("Error", throwError);		
			}		
		});  		
	};		
			/*
	-----------------------------------------------------DISPLAY SENIORS-----------------------------------------------------------------		
	User.prototype.getSeniorsRequests=function(){		
		var seniorRequestList = '[{"feedbackId":2,"empRelation":"SENIORS","respondentFirstName":"Geeta","respondentLastName":"Bhosale","feedbackStatus":"INPROGRESS","count":0},{"feedbackId":2,"empRelation":"SENIORS","respondentFirstName":"Ajay","respondentLastName":"Dubey","feedbackStatus":"PENDING","count":0},{"feedbackId":2,"empRelation":"SENIORS","respondentFirstName":"Vivek","respondentLastName":"Birdi","feedbackStatus":"PENDING","count":0}]';
		var jsonObj = JSON.parse(seniorRequestList);
		userObj.bindRespondentRequestsDetailsWithTable(jsonObj);
		var seniorRequestList ="";		
		$.ajax({		
			type : "POST",		
			url : "OthersFeedbackController",		
			async:false,		
			data : {		
				message:"fetchSeniors",		
				//message:"feedbacckResponse",		
				JDATA:'{"feedbackId":"2","empRelation":"SENIORS"}'		
			},		
			success : function(data) {		
				seniorRequestList = jQuery.parseJSON(data);		
						
				console.log(seniorRequestList);		
				userObj.bindRespondentRequestsDetailsWithTable(seniorRequestList);		
			},		
			error : function(xhr, ajaxOptions, throwError) {		
				//add appropriate error messages		
			}		
		});  		
	};		
			
	--------------------------------------------------DISPLAY SUBORDINATES----------------------------------------------------------------		
			
	User.prototype.getSubordinatesRequests=function(){		
		
		var subordinatesRequestList = '[{"feedbackId":2,"empRelation":"SUBORDINATE","respondentFirstName":"Sonal","respondentLastName":"Tirlotkar","feedbackStatus":"INPROGRESS","count":0}]';
		var jsonObj = JSON.parse(subordinatesRequestList);
		userObj.bindRespondentRequestsDetailsWithTable(jsonObj);
		var subordinatesRequestList ="";		
		$.ajax({		
			type : "POST",		
			url : "OthersFeedbackController",		
			async:false,		
			data : {		
				message:"fetchSubordinates",		
				JDATA:'{"feedbackId":"2","empRelation":"SUBORDINATE"}'		
			},		
			success : function(data) {		
				subordinatesRequestList = jQuery.parseJSON(data);		
						
				console.log(subordinatesRequestList);		
				userObj.bindRespondentRequestsDetailsWithTable(subordinatesRequestList);		
			},		
			error : function(xhr, ajaxOptions, throwError) {		
				//add appropriate error messages		
			}		
		});  		
	};		
			
	----------------------------------------------------DISPLAY OTHERS---------------------------------------------------------------------		
			
	User.prototype.getOthersRequests=function(){		
		var othersRequestList = '[{"feedbackId":2,"empRelation":"OTHERS","respondentFirstName":"Micky","respondentLastName":"Dange","feedbackStatus":"PENDING","count":0},{"feedbackId":2,"empRelation":"OTHERS","respondentFirstName":"Vivek","respondentLastName":"Birdi","feedbackStatus":"PENDING","count":0},{"feedbackId":2,"empRelation":"OTHERS","respondentFirstName":"Ajay","respondentLastName":"Dubey","feedbackStatus":"PENDING","count":0}] ';
		var jsonObj = JSON.parse(othersRequestList);
		userObj.bindRespondentRequestsDetailsWithTable(jsonObj);
		var othersRequestList ="";		
		$.ajax({		
			type : "POST",		
			url : "OthersFeedbackController",		
			async:false,		
			data : {		
				message:"fetchOthers",		
				//message:"feedbacckResponse",		
				JDATA:'{"feedbackId":"2","empRelation":"OTHERS"}'		
			},		
			success : function(data) {		
				othersRequestList = jQuery.parseJSON(data);		
						
				console.log(othersRequestList);		
				userObj.bindRespondentRequestsDetailsWithTable(othersRequestList);		
			},		
			error : function(xhr, ajaxOptions, throwError) {		
				//add appropriate error messages		
			}		
		});  		
	};		
*/			

	User.prototype.getRespondentStatus = function(){		
		var statuslist = '';		
        $.ajax({		
               type: "POST",		
               url: "FeedbackController",		
               async: false,		
               data: {		
                     message: "fetchrespondentstatus",		
                     JDATA: null //     '{"feedbackid":"'+feedbackId+'"}'		
               },		
               success: function(data) {		
                     statuslist = data;		
                     console.log(statuslist);		
                     bindRespondentStatusDetailsWithTable(statuslist);    
//                     sessioncheckforpopulatingdata(data);
               },		
               error: function(xhr, ajaxOptions, throwError) {		
                     //add appropriate error messages		
            	   utilObj.showErrorMessageAlertForSessionTimeout("Error", throwError);		
               }		
        });		
	};
	
	//-------------Title Case---------------
	function toTitleCase(str) {
	    return str.replace(/(?:^|\s)\w/g, function(match) {
	        return match.toUpperCase();
	    });
	}
	//---------------end--------------------	
	

	
	User.prototype.bindRespondentRequestsDetailsWithTable = function(requestDetailsList){		
		var btnHtml = "";		
	    var feedbackStatus  ="";		
	    var respondentRequestList = new Array();	
	    
	    console.log(requestDetailsList);
	    			  		
		$.each(requestDetailsList, function( index, peer)  {	
			var dis="";
			var surveyStartDate = new Date(peer.startDate);
			var surveyEndDate = new Date(peer.endDate);
			var today = new Date();
			var dateInvalidFlag = false;
			if(!(today>=surveyStartDate&&today<=surveyEndDate)){
				dateInvalidFlag = true;
			}
			if(dateInvalidFlag){
				dis="disabled";
			}
			if(peer.closedStatus){
				peer.closed="CLOSED";
				dis="disabled";
			}
			else{
				peer.closed="OPEN";
		}
			if (peer.feedbackstatus == "COMPLETED") {	
				btnHtml = '';
				btnHtml = '<span>Feedback is completed.</span>';
		        //btnHtml = "<input type='button' value='View Feedback' data-feedbackId="+peer.feedbackid+" data-respondentId="+peer.respondentid+" class='btn btn-default viewFeedbackForm'/>";		
		    } else {		 
		       // btnHtml = "<input type='button' "+ dis +  " value='Give Feedback' data-emailId="+peer.emailid+" data-feedbackId="+peer.feedbackid+" data-respondentId="+peer.respondentid+" class='btn btn-default viewFeedbackForm'/>";
		    	
		    	//btnHtml = "<input type='button' "+ dis +  " value='Give Feedback' data-emailId='"+peer.firstName+" "+peer.lastName+"' data-feedbackId="+peer.feedbackid+" data-respondentId="+peer.respondentid+" class='btn btn-default viewFeedbackForm'/>";
		    	if(peer.closedStatus){
		    		btnHtml = '<span>Feedback is closed.</span>';
		    	}else if(dateInvalidFlag){
		    		btnHtml = '<span>Feedback end date has passed.</span>';
		    	}
		    	else{
		    		btnHtml = "<input type='button'  value='Give Feedback' data-emailId='"+peer.firstName+" "+peer.lastName+"' data-feedbackId="+peer.feedbackid+" data-respondentId="+peer.respondentid+" class='btn btn-default viewFeedbackForm'/>";
		    	}
		    }		
            peer.actionType = btnHtml;
            peer.fullName =peer.firstName+" "+peer.lastName; // peer.emailid;//peer.respondentFirstName + " " + peer.respondentLastName;		
           
          //------------Camel Case feedbackstatus-----------
             if(peer.feedbackstatus!=null){
             var camelCaseStatus=toTitleCase(peer.feedbackstatus.toLowerCase());
             peer.feedbackstatus=camelCaseStatus;
             }
          //-----------------------end----------------------
            
            respondentRequestList.push(peer);	                	                		
		});		
				
		$('#fdTable').bootstrapTable("destroy");		
		$('#fdTable').bootstrapTable({		
	        method: 'get',		
	        data: respondentRequestList,		
	        cache: false,		
	        height: 400,		
	        striped: true,		
	        pagination: true,		
	        pageSize: 15,		
	        pageList: [15, 25, 50, 100, 200],		
	        search: true,		
	        showRefresh: false,		
	        showToggle: false,		
	        clickToSelect: true,		
	        columns: [ {		
	            field: 'fullName',		
	            title: 'Name',		
	            align: 'center',		
	            valign: 'middle',		
	            sortable: true		
	        }, {		
	            field: 'feedbackstatus',		
	            title: 'Status',		
	            align: 'center',		
	            valign: 'middle',		
	            sortable: true		
	        }, {		
	            field: 'actionType',		
	            title: 'Action',		
	            align: 'center',		
	            valign: 'top',		
	            sortable: true		
	        }/*, {		
	            field: 'closed',		
	            title: 'Closed',		
	            align: 'center',		
	            valign: 'top',		
	            sortable: true		
	        }*/]		
	    });		
		
		$(".search").addClass("right-inner-addon");
		$(".search").append("<i class='glyphicon glyphicon-search'></i>");
		

		$("#respondantTableView").show();
		
		$(".viewFeedbackForm").on("click", function(){
	    	var respondentId = $(this).attr('data-respondentId');
	    	var feedbackId = $(this).attr('data-feedbackId');
			feedbackObj.populateQuestions(respondentId,feedbackId);	
			$("#respondantTableView").hide();
			$("#feedbackQuestionList").show();
			$("#feedbackQuestionList").attr('data-respondentId',respondentId);
			$("#feedbackQuestionList").attr('data-feedbackId',feedbackId);
			$("#nameOfFeedbackSeeker").empty();
			$("#nameOfFeedbackSeeker").text($(this).attr('data-emailId'));
			$("#contentForOthersFeedbackForm").show();
			$("#contentForSelfFeedbackForm").hide();
			$("#feedbackForm").show();
		});
	};

	User.prototype.bindRespondentStatusDetailsWithTable = function(statusDetailsList){					
	    var respondentStatusList = new Array();		
	    			  				
		$.each(statusDetailsList, function( index, respondent)  {						
			respondent.fullName = respondent.respondentFName + " " + respondent.respondentLName;				
/*			if(respondent.empRelation == 'SUBORDINATE')
			respondent.empRelation ='TEAM MEMBER';*/
			if($.trim(respondent.empRelation).toLowerCase() == 'senior')
			respondent.empRelation ='Superior';
			//***-----------camel case------------------------
			 if(respondent.empRelation!=null){
		            var camelempRelation=toTitleCase(respondent.empRelation.toLowerCase());
		            respondent.empRelation=camelempRelation;
		     }
			 
			 if(respondent.respondentStatus!=null){
		            var camelCaseStatus=toTitleCase(respondent.respondentStatus.toLowerCase());
		            respondent.respondentStatus=camelCaseStatus;
		     }
			//***---------------end---------------------------
			respondentStatusList.push(respondent);	                	                				
        });				
						
		$('#respondantStatusTable').bootstrapTable("destroy");				
		$('#respondantStatusTable').bootstrapTable({				
	        method: 'get',				
	        data: respondentStatusList,				
	        cache: false,					       			
	        striped: true,				
	        pagination: true,				
	        pageSize: 15,				
	        pageList: [15, 25, 50, 100, 200],				
	        search: true,				
	        showRefresh: false,				
	        showToggle: false,				
	        clickToSelect: true,				
	        columns: [ {				
	            field: 'fullName',				
	            title: 'Name',				
	            align: 'left',				
	            valign: 'middle',				
	            sortable: true				
	        }, {				
	            field: 'empRelation',				
	            title: 'Relation',				
	            align: 'left',				
	            valign: 'middle',				
	            sortable: true				
	        }, {				
	            field: 'respondentStatus',				
	            title: 'Status',				
	            align: 'left',				
	            valign: 'middle',				
	            sortable: true				
	        }, {				
	            field: 'respondentEmailId',				
	            title: 'Email ID',				
	            align: 'left',				
	            valign: 'top',				
	            sortable: true				
	        }]				
	    });		
		
		$(".search").addClass("right-inner-addon");
		$(".search").append("<i class='glyphicon glyphicon-search'></i>");
	};	

}

var userObj = new User();


