$(document).ready(function(){

	//Reset Page scroll postion on page refresh
	$('html').animate({scrollTop:0}, 1);
    $('body').animate({scrollTop:0}, 1);
	
    //To Underline the selected Tab
	var selector1 = '.nav li';
	$(selector1).on('click', function(){
	    $(selector1).removeClass('active');
	    $(this).addClass('active');
	});
	
	/*Code for floating table headers*/
	 $(window).bind("scroll", function(e) {	 		 
			 if($('#hdrTitle').text() === "Question Bank"){
				 if ($(window).scrollTop() > 210) {
					 $(".tclass").addClass("fix-search2");
					 $("#quesMaster").addClass("fixTableHeaderContainer"); 
				 }
				 else {
				   	  $(".tclass").removeClass("fix-search2");
				   	  $("#quesMaster").removeClass("fixTableHeaderContainer");
				 }
			 }
		 $('#questionMasterTable').floatThead(); 
	 });
	 
	 $('.navbar-toggle').click(function(){
		 $(".floatThead-container").css('top','auto');
	 });

	$('#SrchStartDate').datepicker();
	$('#SrchEndDate').datepicker();
	$('#editstartdatefdext').datepicker();
	$('#editenddatefdext').datepicker({minDate:0});
	
	//---------Code To Get UserName From Session  Starts------------
	var userDataJson = "";
	$.ajax({
		type : "POST",
		url : "DataController",
		async:false,
		data : {
		     	message:"getUserName",
			    JDATA:null
		},
		success : function(data) {
			userDataJson = data;
			//sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
			//add appropriate error messages
		}
	});
	
	userDataJson = jQuery.parseJSON(userDataJson);
	$('#loggedInUser').empty();
	$('#loggedInUser').text(userDataJson.userName);
	$('#loggedInUserName').empty();
	$('#loggedInUserName').text(userDataJson.userName);
	$('#userDesc').text(userDataJson.userName+' - '+userDataJson.role);
	//---------Code To Get UserName From Session  Ends------------

	$('#signOutBtn').click(function(){
		$.ajax({
			type : "POST",
			url : "DataController",
			async:false,
			data : {
			     	message:"logout",
				    JDATA:null
			},
			success : function(data) {
				userDataJson = data;
			},
			error : function(xhr, ajaxOptions, throwError) {
				//add appropriate error messages
			}
		});
	});

	
	$('#landingPage').click(function(){

		if(userDataJson.role==='SEA'){
			//alert("SEA");
			$("#landingPageContentforCOA").remove();
			$("#landingPageContent").remove();
			$("#landingPageContentforSEA").show();
		}
		
		else if(userDataJson.role==='COA'){
			//alert("COA");			
			$("#landingPageContentforSEA").remove();
			$("#landingPageContent").remove();
			$("#landingPageContentforCOA").show();
		}
		
		else if(userDataJson.role==='SA'){
			//alert("SA");
			$("#landingPageContentforCOA").remove();
			$("#landingPageContentforSEA").remove();
			$("#landingPageContent").show();
		}
		
	});
	
	
	
	// ----------Left Menu Click Code for Nomination Starts---------
	$('a.submenu, #homePage div.menu-item').click(function(){
		var curmaster = $(this).attr("id"); 
		if(curmaster == 'homePageTab'){
			$('#homePage').show();
			$('.content-wrapper,.right-side').css('background', 'url("images/background-360.jpg") no-repeat fixed 50% 50%');
			$('.content-wrapper,.right-side').css('background-size','cover');
			$(".optional").addClass('hideNavbarItems');
			$('.nav li').removeClass('active');
			$('#homePageTab').parent('li').addClass('active');
			$('#hdrTitle').hide();
			$('#hdrTitle').text('');			
			$('#dashMaster').hide();
			$('#reportMaster').hide();
			$('#quesMaster').hide();
			$('#feedbackExtension').hide();
			$('#nominateEmp').hide();
			$('#feedbackCreation').hide();
			$('#landingPageContent').hide();
		}
		else if(curmaster == 'landingPage'){
			$('.nav li').removeClass('active');
			$('#landingPage').parent('li').addClass('active');
			$('#hdrTitle').hide();
			$('#hdrTitle').text("Dashboard");			
			$('#dashMaster').hide();
			$('#reportMaster').hide();
			$('#quesMaster').hide();
			$('#feedbackExtension').hide();
			$('#nominateEmp').hide();
			$('#feedbackCreation').hide();
			$('#landingPageContent').show();
			$('#homePage').hide();
			$('.content-wrapper, .right-side ').css('background','none');
			$(".optional").removeClass('hideNavbarItems');
		}
		else if (curmaster == 'getDashBrd') {
			$('.nav li').removeClass('active');
			$('#getDashBrd').parent('li').addClass('active');
			$(window).scrollTop(0);
			$('#hdrTitle').show();
			$('#hdrTitle').text("Status");			
			$('#dashMaster').show();
			$('#reportMaster').hide();
			$('#quesMaster').hide();
			$('#feedbackExtension').hide();
			$('#nominateEmp').hide();
			$('#feedbackCreation').hide();
			$('#landingPageContent').hide();
			$('#homePage').hide();
			$('.content-wrapper, .right-side ').css('background','none');
			$(".optional").removeClass('hideNavbarItems');
			getDashBrd();
			
		}else if (curmaster == 'getReports') {   //reports
			$('.nav li').removeClass('active');
			$('#getReports').parent('li').addClass('active');
			$(window).scrollTop(0);
			$('#hdrTitle').show();
			$('#hdrTitle').text("Reports");			
			$('#dashMaster').hide();
			$('#reportMaster').show();
			$('#quesMaster').hide();
			$('#feedbackExtension').hide();
			$('#nominateEmp').hide();
			$('#feedbackCreation').hide();
			$('#landingPageContent').hide();
			$('#homePage').hide();
			$('.content-wrapper, .right-side ').css('background','none');
			$(".optional").removeClass('hideNavbarItems');
			getReportDash();
			
		}else if (curmaster == 'getNomineeList') {
			$('.nav li').removeClass('active');
			$('#getNomineeList').parent('li').addClass('active');
			$('#hdrTitle').show();
			$('#landingPageContent').hide();
			$('#hdrTitle').text("Nomination");
			$('#quesMaster').hide();
			$('#nominateEmp').show();
			$('#feedbackCreation').hide();
			$('#templateSelectDiv').hide();
			$('#empDtlsDiv').hide();
			$('#dashMaster').hide();
			$('#feedbackExtension').hide();
			$('#reportMaster').hide();
			$('#reminderDiv').hide();
			$('#homePage').hide();
			$('.content-wrapper, .right-side ').css('background','none');
			$(".optional").removeClass('hideNavbarItems');
			if(userDataJson.role==='SA'){
				populateTemplateList();
				getSectorList();
			}else if(userDataJson.role==='SEA'){
				getCompanyList("SEA");
			}
			populatePendingRequestList();
		}else if(curmaster == 'getQuesMaster'){
			$('.nav li').removeClass('active');
			$('#getQuesMaster').parent('li').addClass('active');
			$(window).scrollTop(0);
			$("#quesMaster").removeClass("fixTableHeaderContainer");
			$("#questionMasterTable").floatThead();
			$("#questionMasterTable").floatThead('reflow');
			$('#hdrTitle').show();
			$('#landingPageContent').hide();
			$('#hdrTitle').text("Question Bank");
			$('#quesMaster').show();
			$('#nominateEmp').hide();
			$('#dashMaster').hide();
			$('#reportMaster').hide();
			$('#feedbackExtension').hide();
			$('#feedbackCreation').hide();
			$("#landingPageContentforCOA").css('display','none');
			$("#landingPageContentforSEA").hide();
			$('#homePage').hide();
			$('.content-wrapper, .right-side ').css('background','none');
			$(".optional").removeClass('hideNavbarItems');
			populateQuestionList();
			
			if(userDataJson.role==='SEA'){
				$(".editQuestionBtn").attr('disabled','disabled');
			}
			if(userDataJson.role==='COA'){
				$(".editQuestionBtn").attr('disabled','disabled');
			}
			
		}else if(curmaster == 'fbReOpenExtn'){
			$('.nav li').removeClass('active');
			$('#fbReOpenExtn').parent('li').addClass('active');

			$('#hdrTitle').show();
			$('#landingPageContent').hide();
			$('#hdrTitle').text("Feedback Extension");
			$('#quesMaster').hide(); 
			$('#dashMaster').hide();
			$('#reportMaster').hide();
			$('#nominateEmp').hide();
			$('#feedbackExtension').show();
			$('#feedbackCreation').hide();
			$('#homePage').hide();
			$('.content-wrapper, .right-side ').css('background','none');
			$(".optional").removeClass('hideNavbarItems');
			getFeedbackExtension();
			
			
		}else if(curmaster == 'fbCreation'){
			$('.nav li').removeClass('active');
			$('#fbCreation').parent('li').addClass('active');

			$(window).scrollTop(0);
			$('#hdrTitle').show();
			$('#landingPageContent').hide();
			$('#hdrTitle').text("Template Creation");
			$('#quesMaster').hide();
			$('#nominateEmp').hide();
			$('#dashMaster').hide();
			$('#reportMaster').hide();
			$('#feedbackCreation').show();
			$('#feedbackExtension').hide();
			$('#templateCreationPage').attr('src','Drag.html');
			$('#homePage').hide();
			$('.content-wrapper, .right-side ').css('background','none');
			$(".optional").removeClass('hideNavbarItems');
			populateTemplateMasterList();
			
		}
	});
	// ----------Left Menu Click Code for Nomination Ends---------


	
	
	//--------------Employee Nomination Module Starts--------------
	var selector = $('select#nominationCat');
	selector.empty();
	selector.append('<option value="1">---Select---</option>');
	if(userDataJson.role==='SA'){
		$("#pendingRequestDiv").remove();
		selector.append('<option value="SEA">Nominate Sector Admin</option>');
		selector.append('<option value="COA">Nominate Company Admin</option>');
		selector.append('<option value="EMP">Nominate Employee</option>');
		$("#templateSelectDiv").hide();
		createReminderDiv(true);
		//image code goes here
		//$(".usrImg").attr("src","images/sa.jpg");
		$(".outboxMenu,.inboxMenu").hide();
		
		$("#textid").append("&nbsp;Add, edit questions and view template for 360 Degree Feedback/Survey.");
		$("#landingPageContentforCOA").css('display','none');		
		$("#landingPageContent").css('display','none');
		$("#landingPageContentforSEA").css('display','none');
		
	}else if(userDataJson.role==='SEA'){
		selector.append('<option value="COA">Nominate Company Admin</option>');
		selector.append('<option value="EMP">Nominate Employee</option>');
		createReminderDiv(true);
		//image code goes here
		//$(".usrImg").attr("src","images/sea.jpg");
		$("#templateSelectDiv").remove();
		$("#selectSectorDiv").remove();
		//$(".quesMenu,.fbMenu").hide();
		$(".outboxMenu,.inboxMenu").hide();
		$("#questionMasterAddBtn").hide();
		$("#editQuestionFormSubmit").hide();
		$("#editTemplateSubmit").hide();
		$("#questionActive").parents('label').attr('disabled','disabled');
		$("#editTemplateActiveStatus").parents('label').attr('disabled','disabled');
		$("#editTemplateInActiveStatus").parents('label').attr('disabled','disabled');
		$("#templateForm").find("label").css('color','black'); 	
		$("#templateMasterAddBtn").hide();
		$("#textid").empty();
		//$("#textid").append("&nbsp;View questions and template for 360 Degree Feedback/Survey.");
		$("#landingPageContentforCOA").css('display','none');
		$("#landingPageContent").css('display','none');
		$("#landingPageContentforSEA").css('display','none');
		
		
	}else if(userDataJson.role==='COA'){
		selector.append('<option value="EMP">Nominate Employee</option>');
		createReminderDiv(true);
		$("#templateSelectDiv").remove();
		$("#selectSectorDiv").remove();
		$("#selectCompanyDiv").remove();
		//$(".quesMenu,.fbMenu").hide();
		$(".outboxMenu,.inboxMenu").hide();
		
		$("#questionMasterAddBtn").hide();
		$("#templateMasterAddBtn").hide();
		$("#editTemplateActiveStatus").parents('label').attr('disabled','disabled');
		$("#editTemplateInActiveStatus").parents('label').attr('disabled','disabled');
		$("#templateForm").find("label").css('color','black');
		$("#editTemplateSubmit").hide();
		
		$("#textid").append("&nbsp;View questions and template for 360 Degree Feedback/Survey.");
		$("#landingPageContent").css('display','none');
		$("#landingPageContentforCOA").css('display','none');
		$("#landingPageContentforSEA").css('display','none');
		
	}else if(userDataJson.role==='EMP'){
		$("#fbCreation").hide();
		$('#nominateEmp').empty();
		//$('#outbox2').remove();
		$('#nominateEmp').text("Coming Soon......");
		$('#hdrTitle').show();
		$(".quesMenu,.nomMenu,.fbMenu,.reportsMenu").hide();
		$(".outboxMenu,.inboxMenu").show();
	}
	
	$("select#nominationCat" ).change(function () {
		$('#secAdminSelect').show();
		$('#comAdminSelect').show();
		$('#fromDt').datepicker('setDate', new Date());
		$('#toDt').datepicker('setDate', null);
		
		$('#tempSelect').prop('selectedIndex',0);
		$('#selectReminder').prop('selectedIndex',0);
		
		var nominationCat=$(this).val();
		 if(userDataJson.role==='SA'){
				var selector = $('.comAdminSelect');
				selector.empty();
				selector.append("<option value='0'>---Select---</option>");
		 }
		 
		 if(nominationCat ==='SEA'){
			$('#selectSectorDiv').show();
			$("#templateSelectDiv").show();
			$('#selectCompanyDiv').hide();
			$('#empDtlsDiv').hide();
			$("#reminderDiv").hide();
			
		 }else if(nominationCat ==='COA' && userDataJson.role==='SA'){
			$('#selectSectorDiv').show();
			$("#templateSelectDiv").show();
			$('#selectCompanyDiv').show();
			$('#empDtlsDiv').hide();
			$("#reminderDiv").hide();
			
		}else if(nominationCat ==='COA' && userDataJson.role==='SEA'){
			$('#selectSectorDiv').remove();
			$("#templateSelectDiv").remove();
			$('#selectCompanyDiv').show();
			$('#empDtlsDiv').hide();
			$("#reminderDiv").hide();
			
		}else if(nominationCat ==='EMP' && userDataJson.role==='SA'){
			$('#selectSectorDiv').show();
			$('#selectCompanyDiv').show();
			$("#templateSelectDiv").show();
			$('#empDtlsDiv').show();
			$("#reminderDiv").show();		
			
		}else if(nominationCat ==='EMP' && userDataJson.role==='SEA'){
			$('#selectSectorDiv').remove();
			$('#selectCompanyDiv').show();
			$("#templateSelectDiv").remove();
			$('#empDtlsDiv').show();
			$("#reminderDiv").show();		
			
		}else if(nominationCat ==='EMP' && userDataJson.role==='COA'){
			$('#selectSectorDiv').remove();
			$('#selectCompanyDiv').remove();
			$("#templateSelectDiv").remove();
			$('#empDtlsDiv').show();
			$("#reminderDiv").show();		
			
		}else if(nominationCat ==='1' ){
			$('#selectSectorDiv').hide();
			$('#selectCompanyDiv').hide();
			$('#empDtlsDiv').hide();
			$("#templateSelectDiv").hide();
			$("#reminderDiv").hide();	
		}
		$('#secAdminSelect').prop('selectedIndex',0); 
	}).change();
	
	
	$('#getNomineeList, #homePage #getNomineeList').on("click", function(){
		$('#nominationCat').prop('selectedIndex',0);
        $('#secAdminSelect').prop('selectedIndex',0); 
        $('#comAdminSelect').prop('selectedIndex',0);
        $('#selectReminder').prop('selectedIndex',0);
        $('#selectSectorDiv').hide();
        $('#selectCompanyDiv').hide();
        $('#secAdminSelect').hide();      
        $('#comAdminSelect').hide();
        $("#landingPageContentforSEA").hide();
        $("#landingPageContentforCOA").hide();
        $("#landingPageContent").hide();
		$('#fromDt').datepicker('setDate', new Date());
        $('#toDt').datepicker('setDate', null);
    });
	
	
	$('#fbCreation').on("click", function(){
		$("#landingPageContentforSEA").hide();
        $("#landingPageContentforCOA").hide();
        $("#landingPageContent").hide();		
    });
	
	
	$('#fbReOpenExtn').on("click", function(){
		
		if(userDataJson.role==='SEA'){
			$("#landingPageContentforSEA").hide();
		}
		if(userDataJson.role==='COA'){
			$("#landingPageContentforCOA").hide();
		}
		
	});
	
	$('#getReports').on("click", function(){
		
		if(userDataJson.role==='SEA'){
			$("#landingPageContentforSEA").hide();
		}
		if(userDataJson.role==='COA'){
			$("#landingPageContentforCOA").hide();
		}
		
	});
	
	$('#getDashBrd').on("click", function(){
		
		if(userDataJson.role==='SEA'){
			$("#landingPageContentforSEA").hide();
		}
		if(userDataJson.role==='COA'){
			$("#landingPageContentforCOA").hide();
		}
		
	});
	
		
	$( "select#secAdminSelect" ).change(function () {
		var id=$(this).val();
		var dataString = ''+ id;
		if(userDataJson.role==='SA' && id != 0 && id != '---Select---'){
			getCompanyList(dataString);
		}
	}).change();

	$('button#nominateBtn').click(function(){
		var destination='';
		var todt=$('#toDt').val();
		var destinationDesc = $('#nominationCat').val();
			if($('#nominationCat').val()=='SEA'){
				destination = $('#secAdminSelect').val();
			}else if($('#nominationCat').val()=='COA'){
				destination = $('#comAdminSelect').val();
			}else if($('#nominationCat').val()=='EMP'){
				destination = $('#email').val();
				/*Ankush 1 */
				flag=utilObj.isEmailValid(destination);
				if(!flag){
					utilObj.showErrorMessageAlert("Error","Enter valid Email-Id..!");
					return;
				}else if(!(  (/^[a-zA-Z0-9 ]*$/.test($('#fName').val()))  && (/^[a-zA-Z0-9 ]*$/.test($('#lName').val()))  ) ){
					utilObj.showErrorMessageAlert("Error","Name can contain only alphanumeric characters and spaces.");
					return;
				}
				if(todt=="" || !utilObj.isValidDate("#toDt")){
					utilObj.showErrorMessageAlert("Error","Enter valid To Date..!");
					return;
				}
				
				/*Ankush 1 */
			}
		
		var templateId= ($('#tempSelect').val()==undefined ||$('#tempSelect').val()=="")?"-1":$('#tempSelect').val();
		var fromDate = $('#fromDt').val()==undefined ?"":$('#fromDt').val();
		var toDate= $('#toDt').val()==undefined ?"":$('#toDt').val();
		var remDuration= ($('#selectReminder').val()==undefined ||$('#selectReminder').val()=="")?"-1":$('#selectReminder').val();
		var requestId= ($('#selectRequestId').val()==undefined||$('#selectRequestId').val()=="")?"-1":$('#selectRequestId').val();
		var sectorId= ($('#secAdminSelect').val()==undefined||$('#secAdminSelect').val()=="")?"-1":$('#secAdminSelect').val();
		var companyId= ($('#comAdminSelect').val()==undefined||$('#comAdminSelect').val()=="")?"-1":$('#comAdminSelect').val();
		var jsonDataToServer='{"destination":"'+destination+'",'+
								'"templateId":"'+templateId+'",'+		
								'"fromDate":"'+fromDate+'",'+		
								'"toDate":"'+toDate+'",'+		
								'"reminderDuration":"'+remDuration+'",'+		
								'"requestId":"'+requestId+'",'+		
								'"destinationDesc":"'+destinationDesc+'",'+		
								'"firstName":"'+$('#fName').val()+'",'+		
								'"lastName":"'+$('#lName').val()+'",'+		
								'"sectorId":"'+sectorId+'",'+		
								'"companyId":"'+companyId+'"'+		
							 '}';
		$.ajax({
			type : "POST",
			url : "DataController",
			async:false,
			data : {
			     	message:"nominateEmployee",
				    JDATA:jsonDataToServer
			},
			success : function(data) {
				dataFromServer = data;
				if("Saved successfully."==data){
					resetNominationReqForm();
					utilObj.showSuccessMessageAlert("Success", "Nomination Request Accepted.");
					$('#getNomineeList').trigger('click');
				}else{
//					utilObj.showErrorMessageAlert("Error", dataFromServer);
					sessionCheck(data);
				}
			},
			error : function(xhr, ajaxOptions, throwError) {
			    utilObj.showErrorMessageAlert("Error", "Error saving data. Please try again.");
			}
		});
		
	});
	//--------------Employee Nomination Module Ends--------------
	
	

	
	
	//----------Dashboard Search----------------
	$('#SrchBtn').click(function() {
		var dashBrdJson = null;
		if($('input[id="SrchStartDate"]').val()!="" && !utilObj.isValidDate("#SrchStartDate")){
			utilObj.showErrorMessageAlert("Error","Enter valid start Date..!");
			return;
		}else if($('input[id="SrchEndDate"]').val()!="" && !utilObj.isValidDate("#SrchEndDate")){
			utilObj.showErrorMessageAlert("Error","Enter valid end Date..!");
			return;
		}
	   	 var jsonDataToServer = '{"nameSrch":"' + $('input[id="SrchName"]').val() + 
	   	 '","statSrch":"'+ $('input[id="SrchStat"]').val() +'","templateSrch":"'+ 
	   	 $('input[id="TemplateSrch"]').val() +'","srchStartDate":"'+ $('input[id="SrchStartDate"]').val() +
	   	 '","srchEndDate":"'+ $('input[id="SrchEndDate"]').val() +'"}';
	   		$.ajax({
	   			type : "POST",
	   			url : "DashBoardUIController",
	   			async : false,
	   			data : {
	   				message : "srchDash",
	   				JDATA : jsonDataToServer
	   			},
	   			success : function(data) {
	   				dashBrdJson = data;
//	   				sessioncheckforpopulatingdata(data);
	   			},
	   			error : function(xhr, ajaxOptions, throwError) {
	   			    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
	   			}
	   		});
	   		
	   		var btnHtml = "";		
	   		var statusLink = "";
	   		dashBrdJson  = JSON.parse(dashBrdJson);
		    var customDashboardItemList=[];
			$.each(dashBrdJson, function( index, dashBrdItem)  {
				console.log(dashBrdItem);
				if(!dashBrdItem.closedStatus)
					btnHtml = "<button type='button' class='btn btn-danger editQuestionBtn' data-toggle='tooltip' id='closeSurveyBtn#"+dashBrdItem.feedBackId+"' data-placement='bottom' title='Close'><span class='glyphicon glyphicon-pencil'></span></button>";
				else
					btnHtml = "<button type='button' class='btn btn-danger editQuestionBtn' data-toggle='tooltip' id='reopenSurveyBtn#"+dashBrdItem.feedBackId+"' data-placement='bottom' title='Close'><span class='glyphicon glyphicon-open'></span></button>";
			    dashBrdItem.actionType = btnHtml;
			    
				statusLink = "<a href='#' data-feedbackId='"+dashBrdItem.feedBackId+"'>"+dashBrdItem.NoOfRespondents+"</a>";
				dashBrdItem.respCountStatus = statusLink;

			    
			    customDashboardItemList.push(dashBrdItem);	                	                		
			});	
			
			$('#dashMasterTable').bootstrapTable("destroy");		
			$('#dashMasterTable').bootstrapTable({		
		        method: 'get',		
		        data: customDashboardItemList,		
		        cache: false,		
		        height: 350,		
		        striped: true,		
		        pagination: true,		
		        pageSize: 10,		
		        pageList: [10, 15, 20, 25, 50, 100, 200],		
		        search: false,		
		        showRefresh: false,		
		        showToggle: false,		
		        clickToSelect: true,		
		        columns: [ {		
		            field: 'Name',		
		            title: 'Nominee Name',		
		            align: 'center',		
		            valign: 'middle',		
		            sortable: true		
		        }, {		
		            field: 'TemplateName',		
		            title: 'Template Name',		
		            align: 'center',		
		            valign: 'middle',		
		            sortable: true		
		        }, {		
		            field: 'StartDate',		
		            title: 'Start Date',		
		            align: 'center',		
		            valign: 'top',		
		            sortable: true		
		        }, {		
		            field: 'EndDate',		
		            title: 'End Date',		
		            align: 'center',		
		            valign: 'top',		
		            sortable: true		
		        }, {		
//		            field: 'NoOfRespondents',		
		            field: 'respCountStatus',		
		            title: 'Respondents',		
		            align: 'center',		
		            valign: 'top',		
		            sortable: true		
		        }, {		
		            field: 'NoOfRespponsesReceived',		
		            title: 'Responses',		
		            align: 'center',		
		            valign: 'top',		
		            sortable: true		
		        }, {		
		            field: 'SelfFeedback',		
		            title: 'Self Feedback',		
		            align: 'center',		
		            valign: 'top',		
		            sortable: true		
		        }, {		
		            field: 'actionType',		
		            title: 'Close',		
		            align: 'center',		
		            valign: 'top',		
		            sortable: false		
		        }, {		
		            field: 'Status',		
		            title: 'Status',		
		            align: 'center',		
		            valign: 'top',		
		            sortable: true		
		        }]		
		    });		

	   		/* var tr;
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
	   			tr.append('<td><button type="button" class="btn btn-danger editQuestionBtn" data-toggle="tooltip" id="closeSurveyBtn#'+obj.feedBackId+'" data-placement="bottom" title="Close">'+
	   			'<span class="glyphicon glyphicon-pencil"></span></button></td>');
	   			tr.append("<td>" + obj.Status+"</td>");
	   		    tr.append('<td><button type="button" class="btn btn-danger editQuestionBtn" data-toggle="tooltip" id="ReminderBtn#'+obj.feedBackId+'" data-placement="bottom" title="Remind">'+
	   					'<span class="glyphicon glyphicon-pencil"></span></button></td>');
	   		    $('table#dashMasterTable tbody').append(tr);
	   		    });*/
	});
	//----------Dashboard Search----------------
	
	
	
	
	
	//--------------Question Bank Module starts--------------
	$('#questionMasterAddBtn').click(function() {
		getCategoryList('#qbAddModalQuestCat');
		$('.addQuestionModal').modal('show');
	});
	
	
	$( "table#questionMasterTable tbody" ).on("click","button.editQuestionBtn" ,function() {
		var selector = $('#qbEditModalQuestCat');
		selector.empty();
		getCategoryList('#qbEditModalQuestCat');
		
		var jsonDataToServer = '{"quesId":"'+$(this).attr("id").split("#")[1]+'"}';
		var dataFromServer='';
		$.ajax({
			type : "POST",
			url : "DataController",
			async:false,
			data : {
			     	message:"fetchQuestions",
				    JDATA:jsonDataToServer
			},
			success : function(data) {
				dataFromServer = jQuery.parseJSON(data);
//				sessioncheckforpopulatingdata(data);
			},
			error : function(xhr, ajaxOptions, throwError) {
			    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
			}
		});
		$('#qbEditModalQuestDesc').val(dataFromServer[0].quesDesc);
		$('#qbEditModalQuestType').val(dataFromServer[0].quesType);
		$('#qbEditModalQuestDescTagLine').val(dataFromServer[0].quesTagLine);
		$('#qbEditModalQuestId').val(dataFromServer[0].quesId);
		
		if(dataFromServer[0].negativeQuest==true)
			$('#editQuestNegCheck').prop('checked',true);
		else
			$('#editQuestNegCheck').prop('checked',false);
		
		
		$('#qbEditModalQuestCat').find('option[value="' + dataFromServer[0].quesCat + '"]').attr('selected', 'selected');
		var selector = $('#qbEditModalQuestSubCat');
		selector.empty();
		getSubCategoryList(dataFromServer[0].quesCat,'#qbEditModalQuestSubCat');
		
		var selectedSubCategory = dataFromServer[0].quesSubCat;
		$("#qbEditModalQuestSubCat option").filter(function() {
		    return this.text == selectedSubCategory; 
		}).attr('selected', true);
		
		//@----radio button part for edit question------@
        var active=dataFromServer[0].quesStatus;
            if(active=='Active')
               {
                $('#editQuestionForm  #questionActive').parent("label").addClass("active");
                $("#editQuestionForm  #questionInActive").parent("label").removeClass("active");
                $("#editQuestionForm  #questionActive").attr('checked', 'checked');
               }
            else{
                $('#editQuestionForm  #questionActive').parent("label").removeClass("active");
                $("#editQuestionForm  #questionInActive").parent("label").addClass("active");
                $("#editQuestionForm  #questionInActive").attr('checked', 'checked');
            	 }       
         //@----radio button part for edit question------@
		$('.editQuestionModal').modal('show');
	});
	
	
	$('button#editQuestionFormSubmit').click(function() {
		var jsonDataToServer = JSON.stringify($('#editQuestionForm').serializeJSON());
		 console.log("AddQuestionData --->"+jsonDataToServer);
		 var category = $('#qbEditModalQuestCat').val();
		 var subCategory = $('#qbEditModalQuestSubCat').val();
		 var type = $('#qbEditModalQuestType').val();
		 var obj = jQuery.parseJSON(jsonDataToServer);
		if (category == '0') {
			utilObj.showErrorMessageAlert('Error','Please select a question category.');
          return false;
      }
		else if (subCategory == '0') {
			utilObj.showErrorMessageAlert('Error','Please select a question subcategory.');
          return false;
      }
		else if (type == '0') {
			utilObj.showErrorMessageAlert('Error','Please select a question type.');
          return false;
      }
		else if ((obj.quesDesc==="")||(obj.quesDesc===null) || $.trim( obj.quesDesc ) == '')
		{
			utilObj.showErrorMessageAlert('Error','Please enter question description');
			return false;
		}	
		else if (!(/^[a-zA-Z0-9-?. ]*$/.test(obj.quesDesc)))
		{
			utilObj.showErrorMessageAlert('Error','The special characters allowed are .(dot),spaces,-,?');
			return false;
		}	
		/*else if((obj.quesTagLine==="")||(obj.quesTagLine===null) || $.trim( obj.quesTagLine ) == ''){
			utilObj.showErrorMessageAlert('Error','Please enter question TagLine');
			return false;
		}*/else{
			$('.editQuestionModal').modal('hide');
			var dataFromServer ="";
			$.ajax({
				type : "POST",
				url : "DataController",
				async:false,
				data : {
						message:"editQuestion",
						JDATA:jsonDataToServer
				},
				success : function(data) {
					dataFromServer = data;
//					sessioncheckforpopulatingdata(data);
				},
				error : function(xhr, ajaxOptions, throwError) {
					utilObj.showErrorMessageAlert("Error", "Error editing question. Please try again.");
				}
			});
			if(dataFromServer =='editSuccessfull'){
				utilObj.showSuccessMessageAlert("Success", "Question Updated Successfully");
			}else{
				//utilObj.showErrorMessageAlert("Message from server", dataFromServer);
				sessionCheck(dataFromServer);
			}
			populateQuestionList();
		}
	});
	
	
	$('#qbAddModalQuestCat').on('change',function(){
		var selector = $('#qbAddModalQuestSubCat');
//		selector.empty();
		/*selector.append('<option>Select Sub Category</option>');*/
		
		console.log("Current selected value "+$(this).val());
		getSubCategoryList($(this).val(),'#qbAddModalQuestSubCat');
		
		/*if($('#qbAddModalQuestCat').val()=='Category-1'){
			selector.append('<option>Cat-1 SubCat-1</option>');
			selector.append('<option>Cat-1 SubCat-2</option>');
			selector.append('<option>Cat-1 SubCat-3</option>');
		}else if($('#qbAddModalQuestCat').val()=='Category-2'){
			selector.append('<option>Cat-2 SubCat-1</option>');
			selector.append('<option>Cat-2 SubCat-2</option>');
			selector.append('<option>Cat-2 SubCat-3</option>');
		}*/
	});
	
	
	$('#qbEditModalQuestCat').on('change',function(){
		var selector = $('#qbEditModalQuestSubCat');
		selector.empty();
		
		console.log("Current selected value in edit modal "+$(this).val());
		getSubCategoryList($(this).val(),'#qbEditModalQuestSubCat');
		
		/*selector.append('<option>Select Sub Category</option>');
		if($('#qbEditModalQuestCat').val()=='Category-1'){
			selector.append('<option>Cat-1 SubCat-1</option>');
			selector.append('<option>Cat-1 SubCat-2</option>');
			selector.append('<option>Cat-1 SubCat-3</option>');
		}else if($('#qbEditModalQuestCat').val()=='Category-2'){
			selector.append('<option>Cat-2 SubCat-1</option>');
			selector.append('<option>Cat-2 SubCat-2</option>');
			selector.append('<option>Cat-2 SubCat-3</option>');
		}*/
	});
	
	
	$('button#addQuestionFormSubmit').click(function() {
		 var jsonDataToServer = JSON.stringify($('#addQuestionForm').serializeJSON());
		 var category = $('#qbAddModalQuestCat').val();
		 var subCategory = $('#qbAddModalQuestSubCat').val();
		 var type = $('#qbAddModalQuestType').val();
		 var obj = jQuery.parseJSON(jsonDataToServer);
		if (category == '0') {
		    utilObj.showErrorMessageAlert('Error', 'Please select a question category.');
            return false;
		}
		else if (subCategory == '0') {
		    utilObj.showErrorMessageAlert('Error', 'Please select a question subcategory.');
		    return false;
		}
		else if (type == '0') {
		    utilObj.showErrorMessageAlert('Error', 'Please select a question type.');
		    return false;
		}
        else if ((obj.quesDesc === "") || (obj.quesDesc === null) || $.trim( obj.quesDesc ) == '') {
            utilObj.showErrorMessageAlert('Error', 'Please enter question description');
		    return false;
		}else if(!(/^[a-zA-Z0-9-?. ]*$/.test(obj.quesDesc))){
			utilObj.showErrorMessageAlert('Error', 'The special characters allowed are .(dot),spaces,-,?');
		    return false;
		}	
/*        else if ((obj.quesTagLine === "") || (obj.quesTagLine === null) || $.trim(obj.quesTagLine) == '') {
            utilObj.showErrorMessageAlert('Error', 'Please enter question TagLine');
		    return false;
		}*/
		else {
		    $('.addQuestionModal').modal('hide');
		    var dataFromServer = "";
		    $.ajax({
		        type: "POST",
		        url: "DataController",
		        async: false,
		        data: {
		            message: "insertQuestion",
		            JDATA: jsonDataToServer
		        },
		        success: function (data) {
		            dataFromServer = data;
		            resetAddQuestionForm();
//		            sessioncheckforpopulatingdata(data);
		        },
		        error: function (xhr, ajaxOptions, throwError) {
		            utilObj.showErrorMessageAlertForSessionTimeout("Error", "Error adding question. Please try again.");
		        }
		    });
		}
		if(dataFromServer =='insertSuccessfull'){
		    utilObj.showSuccessMessageAlert("Success", "Question inserted successfully.");
		}else{
		  //  utilObj.showErrorMessageAlert("Message from server", dataFromServer);
		    sessionCheck(dataFromServer);
		}
		populateQuestionList();
	});

	$('#addQuestionFormCancelBtn').click(function () {
	    resetAddQuestionForm();
	});
	//<--------------Question Bank Module ends-------------->
		

	//<--------------Template Creation Module Starts-------------->
	$('#templateMasterAddBtn').click(function() {
		$('.addTemplateModal').modal('show');
		populateQuestionTemplateMaster('.addTemplateModal');

		resetAddTemplateForm();

		$('.addTemplateModal .templateQuestionList li').on("click", function () {
		    $(this).toggleClass("selectedQuestion");
		    var selectedQuestionsCount = $(".templateQuestionList .selectedQuestion").length;
		    $(".addTemplateModal .selectedQuestionsCount").empty();
		    $(".addTemplateModal .selectedQuestionsCount").html(selectedQuestionsCount);
		});

	});
	
	
	$(".addTemplateModal .selectAllQuestion").click(function () {
	    //Note : Do not change the sequence of the code.
	    if ($(this).text() == "Select All") {
	        $('.addTemplateModal .templateQuestionList li').addClass("selectedQuestion");
	        $(this).text("Deselect All");
	    } else {
	        $('.addTemplateModal .templateQuestionList li').removeClass("selectedQuestion");
	        $(this).text("Select All");
	    }

	    var selectedQuestionsCount = $(".addTemplateModal .templateQuestionList .selectedQuestion").length;
	    $(".addTemplateModal .selectedQuestionsCount").empty();
	    $(".addTemplateModal .selectedQuestionsCount").html(selectedQuestionsCount);
	});
	
	$('button#addTemplateSubmit').click(function () {
	    var selectedQuestionsCount = $(".addTemplateModal .templateQuestionList .selectedQuestion").length;

		if($("#addTemplateMasterName").val() == "" || $("#addTemplateMasterName").val() == null || $.trim($("#addTemplateMasterName").val()) == ""){
	    	utilObj.showErrorMessageAlert('Error', 'Please enter template name.');
	        return false;
	    }else if (!(/^[a-zA-Z0-9- ]*$/.test($("#addTemplateMasterName").val()))) {
	        utilObj.showErrorMessageAlert('Error', 'The special characters allowed are spaces and -');
	        return false;
	    }
	    else if ($('input[name="templateStatus"]:checked').length <= 0) {
	        utilObj.showErrorMessageAlert('Error', 'Please select a template status.');
	        return false;
	    }
	    else if (selectedQuestionsCount <= 0) {
	        utilObj.showErrorMessageAlert('Error', 'Please select atleast one question.');
	        return false;
	    }
	    else {
	        var selectedQuestList = $('.addTemplateModal .templateQuestionList li.selectedQuestion');
	        var jsonDataToServer = '';
	        $.each(selectedQuestList, function (idx, lis) {
	            jsonDataToServer = jsonDataToServer + '{"quesId":"' + $(this).attr('id').split('_')[1] + '"}';
	            jsonDataToServer = jsonDataToServer + ',';
	        });
	        jsonDataToServer = jsonDataToServer.slice(0, -1);
	        jsonDataToServer = '{"templateName":"' + $('#addTemplateMasterName').val() + '","quesTempMap":[' + jsonDataToServer + '],"templateStatus":"'+$('input[name="templateStatus"]').val()+'"}';
	        $('.addTemplateModal').modal('hide');
	        var dataFromServer = "";
	        $.ajax({
	            type: "POST",
	            url: "DataController",
	            async: false,
	            data: {
	                message: "insertTemplate",
	                JDATA: jsonDataToServer
	            },
	            success: function (data) {
	                dataFromServer = data;
//	                sessioncheckforpopulatingdata(data);
	            },
	            error: function (xhr, ajaxOptions, throwError) {
	                utilObj.showErrorMessageAlertForSessionTimeout("Error", "Some error occurred.");
	            }
	        });
	    }

	    if (dataFromServer == 'insertSuccessfull') {
	        utilObj.showSuccessMessageAlert("Success", "Template Inserted Successfully");
	        populateTemplateMasterList();
	    } else {
//	        utilObj.showErrorMessageAlert("Message from server", dataFromServer);
	    	sessionCheck(dataFromServer);
	    }
	    //populateQuestionList();
	});

	

	$('button#editTemplateSubmit').click(function () {
	    var selectedQuestionsCount = $(".editTemplateModal .templateQuestionList .selectedQuestion").length;

	    if ($('#editTemplateMasterName').val === "" || $('#editTemplateMasterName').val === null || $.trim($('#editTemplateMasterName').val()) === "") {
	        utilObj.showErrorMessageAlert('Error', 'Please enter a template name.');
	        return false;
	    }
	    else if ($('input[name="templateStatus"]:checked').length <= 0) {
	        utilObj.showErrorMessageAlert('Error', 'Please select a template status.');
	        return false;
	    }
	    else if (selectedQuestionsCount <= 0) {
	        utilObj.showErrorMessageAlert('Error', 'Please select atleast one question.');
	        return false;
	    }
	    else {
	        var selectedQuestList = $('.editTemplateModal .templateQuestionList li.selectedQuestion');
	        var jsonDataToServer = '';
	        $.each(selectedQuestList, function (idx, lis) {
	            jsonDataToServer = jsonDataToServer + '{"quesId":"' + $(this).attr('id').split('_')[1] + '"}';
	            jsonDataToServer = jsonDataToServer + ',';
	        });
	       // jsonDataToServer = jsonDataToServer.slice(0, -1);
	       // jsonDataToServer = '{"templateName":"' + $('#editTemplateMasterName').val() + '","quesTempMap":[' + jsonDataToServer + '],"templateStatus":"'+$('input[name="templateStatus"]').val()+'"}';
	        // console.log(jQuery.parseJSON(jsonDataToServer));
	        
	        jsonDataToServer = JSON.stringify($('#templateForm').serializeJSON());
        	console.log(jsonDataToServer);
	        $('.editTemplateModal').modal('hide');
	        var dataFromServer ="";
	        $.ajax({
	            type : "POST",
	            url : "DataController",
	            async:false,
	            data : {
	                    message:"updateTemplate",
	                    JDATA:jsonDataToServer
	            },
	            success : function(data) {
	                dataFromServer = data;
	                if(dataFromServer=="editSuccessfull"){
	                	utilObj.showSuccessMessageAlert("Success","Updated successfully..!");
	                	}
	                else
	                	//utilObj.showErrorMessageAlert("Error",dataFromServer);
	                	sessionCheck(dataFromServer);
	            },
	            error : function(xhr, ajaxOptions, throwError) {
	                utilObj.showErrorMessageAlert("Error","Some error occurred.");
	            }
	        });
	        populateTemplateMasterList();
	       /* $('.editTemplateModal').modal('hide');
            var dataFromServer ="";
            $.ajax({
                type : "POST",
                url : "DataController",
                async:false,
                data : {
                        message:"insertTemplate",
                        JDATA:jsonDataToServer
                },
                success : function(data) {
                    dataFromServer = data;
                },
                error : function(xhr, ajaxOptions, throwError) {
                    utilObj.showErrorMessageAlert("Error","Some error occurred.");
                }
            });*/
	       
	    }


	    /*if(dataFromServer =='insertSuccessfull'){
			utilObj.showSuccessMessageAlert("Success","Template Inserted Successfully");
		}else{
			utilObj.showErrorMessageAlert("Message from server",dataFromServer);
		}*/
	    //populateQuestionList();
	});

	$("#addTemplateCancelBtn").on("click", function () {
	    resetAddTemplateForm();
	});

	$("#editTemplateCancelBtn").on("click", function () {
	    resetEditTemplateForm();
	});
	
	$(".addTemplateModal .close").on("click", function () {
	    resetAddTemplateForm();
	});
	
	$("#editfeedbackextensionCancelbtn").on("click", function () {
		resetFeedbackExtensionEditForm();
	});
	
	$(".editFeedbackExtensionModal .close").on("click", function () {
		resetFeedbackExtensionEditForm();
	});
	//<--------------Template Creation Module ends-------------->


	
	//nomination category dropdown event code goes here

	$("#dashMasterTable").on("click","tbody tr td a",function(){
		
		var feedbackId = $(this).attr('data-feedbackId');
		$.ajax({
            type : "POST",
            url : "RespondentController",
            async:false,
            data : {
                    message:"fetchrespondentstatusforadmin",
                    JDATA:'{"feedbackId":'+feedbackId+'}'
            },
            success : function(data) {
                dataFromServer = data;
                console.log("respondentStatusDetails-->"+dataFromServer);
                bindRespondentStatusDetailsForAdmin(jQuery.parseJSON(dataFromServer));
                $('.respondentStatusForAdmin').modal('show');
            },
            error : function(xhr, ajaxOptions, throwError) {
                utilObj.showErrorMessageAlert("Error","Some error occurred.");
            }
        });
		
	});
	
	
});//End of Document Ready


//-------------Title Case---------------
function toTitleCase(str) {
    return str.replace(/(?:^|\s)\w/g, function(match) {
        return match.toUpperCase();
    });
}
//---------------end--------------------


function populateQuestionList(){
	
	var questionList='';
	$.ajax({
		type : "POST",
		url : "DataController",
		async:false,
		data : {
		     	message:"fetchQuestions",
			    JDATA:""
		},
		success : function(data) {
			questionList = data;
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});
    var tr;
    $('table#questionMasterTable tbody').empty();
    $.each(jQuery.parseJSON(questionList),function(idx, obj) {
    	
    //-----------------Camel case queType-----------------   	
    	if(obj.quesType!=null){
            var camelquesType=toTitleCase(obj.quesType.toLowerCase());
            obj.quesType=camelquesType;
        }
   	 //--------------------------end-----------------   	
    tr = $('<tr/>');
    tr.append("<td>" + (idx+1)+ "</td>");
    if(obj.negativeQuest==true){
    	tr.append("<td style = 'color:red'>" + obj.quesDesc+ "</td>");
    }else{
    	tr.append("<td >" + obj.quesDesc+ "</td>");
    }
    tr.append("<td>" + obj.quesCat+ "</td>");
    tr.append("<td>" + obj.quesSubCat+ "</td>");
    tr.append("<td>" + obj.quesType+ "</td>");
    /*tr.append("<td>" + obj.quesTagLine+ "</td>"); */      
	tr.append("<td>"+obj.quesStatus+"</td>");
    tr.append('<td><button type="button" class="btn btn-danger editQuestionBtn" data-toggle="tooltip" id="editQuestBtn#'+obj.quesId+'" data-placement="bottom" title="Edit">'+
			'<span class="glyphicon glyphicon-pencil"></span></button></td>');
    $('table#questionMasterTable tbody').append(tr);
    });
}


/*function populateQuestionList(){
	
	var questionList='';
	$.ajax({
		type : "POST",
		url : "DataController",
		async:false,
		data : {
		     	message:"fetchQuestions",
			    JDATA:""
		},
		success : function(data) {
			questionList = data;
			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});
	
	
	
	var btnHtml = "";		
    var customQuestionList = new Array();	
    questionList  = JSON.parse(questionList);
	$.each(questionList, function( index, obj)  {
		
		btnHtml = '<button type="button" class="btn btn-danger editQuestionBtn" data-toggle="tooltip" id="editQuestBtn#'+obj.quesId+'" data-placement="bottom" title="Edit">'+
		'<span class="glyphicon glyphicon-pencil"></span></button>';
		obj.SrNo = index + 1;
		obj.Action = btnHtml;
		customQuestionList.push(obj);	                	                		
	});	
	
	$('#questionMasterTable').bootstrapTable("destroy");		
	$('#questionMasterTable').bootstrapTable({		
        method: 'get',		
        data: customQuestionList,		
        cache: false,		
        height: 1000,		
        striped: true,		
        pagination: false,		
        pageSize: 10,		
        pageList: [10, 15, 20, 25, 50, 100, 200],		
        search: false,		
        showRefresh: false,		
        showToggle: false,		
        clickToSelect: false,		
        columns: [ {		
            field: 'SrNo',		
            title: 'S. No.',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'quesDesc',		
            title: 'Question',		
            align: 'left',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'quesCat',		
            title: 'Category',		
            align: 'left',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'quesSubCat',		
            title: 'Sub Category',		
            align: 'left',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'quesType',		
            title: 'Type',		
            align: 'left',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'quesStatus',		
            title: 'Status',		
            align: 'left',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'Action',		
            title: 'Action',		
            align: 'left',		
            valign: 'top',		
            sortable: false		
        }]		
    });		

}
*/


function populateTemplateMasterList(){
	
	var templateList='';
	$.ajax({
		type : "POST",
		url : "DataController",
		async:false,
		data : {
		     	message:"getTemplateListAll",
			    JDATA:""
		},
		success : function(data) {
			templateList = data;
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});
	
	var btnHtml = "";		
    var customDashboardItemList = new Array();	
    templateList  = JSON.parse(templateList);
	$.each(templateList, function( index, templateMasterItem)  {
		btnHtml = "<button type='button' class='btn btn-danger editTemplateBtn' data-toggle='tooltip' id='editTemplateBtn#"+templateMasterItem.templateId+"' data-placement='bottom' title='View'>View</button>";
		templateMasterItem.SrNo = index + 1;
		templateMasterItem.actionType = btnHtml;	
	    customDashboardItemList.push(templateMasterItem);	                	                		
	});	
	console.log(customDashboardItemList);
	
	$('#templateMasterTable').bootstrapTable("destroy");		
	$('#templateMasterTable').bootstrapTable({		
        method: 'get',		
        data: customDashboardItemList,		
        cache: false,		
        height: 480,		
        striped: true,		
        pagination: true,		
        pageSize: 10,		
        pageList: [10,15, 20, 25, 50, 100, 200],		
        search: true,		
        showRefresh: false,		
        showToggle: false,		
        clickToSelect: true,		
        columns: [ {		
            field: 'SrNo',		
            title: 'S. No.',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'templateId',		
            title: 'Template Id',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'templateName',		
            title: 'Template Name',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'templateStatus',		
            title: 'Status',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'actionType',		
            title: 'View Template',		
            align: 'center',		
            valign: 'top',		
            sortable: false		
        }]		
    });	
	
   /* var tr;
    $('table#templateMasterTable tbody').empty();
    $.each(jQuery.parseJSON(templateList),function(idx, obj) {
    tr = $('<tr/>');
    tr.append("<td>" + (idx+1)+ "</td>");
    tr.append("<td>" + obj.templateId+ "</td>");
    tr.append("<td>" + obj.templateName+ "</td>");
    tr.append("<td>" + obj.templateStatus+ "</td>");
    tr.append('<td><button type="button" class="btn btn-danger editTemplateBtn" data-toggle="tooltip" id="editTemplateBtn#'+obj.templateId+'" data-placement="bottom" title="Edit">'+
			'<span class="glyphicon glyphicon-pencil"></span></button></td>');
    tr.append('<td><button type="button" class="btn btn-danger editTemplateBtn" data-toggle="tooltip" id="editTemplateBtn#'+obj.templateId+'" data-placement="bottom" title="View">View</button></td>'); 
    $('table#templateMasterTable tbody').append(tr);
    });*/
	
	$("table#templateMasterTable tbody").on("click", "button.editTemplateBtn", function () {
		    var jsonDataToServer = '{"templateId":"' + $(this).attr("id").split("#")[1] + '"}';
		    populateQuestionTemplateMaster_new('.editTemplateModal',jsonDataToServer);
		    
		    var dataFromServer = '';
		    $.ajax({
		        type: "POST",
		        url: "DataController",
		        async: false,
		        data: {
		            message: "fetchTemplate",
		            JDATA: jsonDataToServer
		        },
		        success: function (data) {
		            dataFromServer = jQuery.parseJSON(data);
//		            sessioncheckforpopulatingdata(data);
		         },
		        error: function (xhr, ajaxOptions, throwError) {
		            utilObj.showErrorMessageAlertForSessionTimeout("Error", "Error retrieving data.");
		        }
		    });


		    $('#editTemplateMasterName').val(dataFromServer.templateName);
		    $('#templateId').val(dataFromServer.templateId);
		    var selectedQuestionsList = dataFromServer.quesTempMap;
		    $.each(selectedQuestionsList, function (idx, list) {
		        $(".editTemplateModal .templateQuestionList").find("#quesId_" + list.quesId).addClass("selectedQuestion");
		    });
		    
		    var selectedQuestionsCount = $(".editTemplateModal .templateQuestionList .selectedQuestion").length;
	        $(".editTemplateModal .selectedQuestionsCount").empty();
	        $(".editTemplateModal .selectedQuestionsCount").html(selectedQuestionsCount);

		    //@----radio button part for edit question template------@
		    var active = dataFromServer.templateStatus;
		    if (active == 'Active') {
		        $('.editTemplateModal #editTemplateActiveStatus').parent("label").addClass("active");
		        $(".editTemplateModal #editTemplateInActiveStatus").parent("label").removeClass("active");
		        $(".editTemplateModal #editTemplateActiveStatus").attr('checked', 'checked');
		    }
		    else {
		        $('.editTemplateModal #editTemplateActiveStatus').parent("label").removeClass("active");
		        $(".editTemplateModal #editTemplateInActiveStatus").parent("label").addClass("active");
		        $(".editTemplateModal #editTemplateInActiveStatus").attr('checked', 'checked');
		    }
		    //@----radio button part for edit question template------@
		    $('.editTemplateModal').modal('show');
		});
}


/*$(document).on("click", "#templateMasterTable .editTemplateBtn", function(e) {
	var jsonDataToServer = '{"templateid":"' + $(this).attr("id").split("#")[1] + '"}';
	populateQuestionTemplateMaster_new('.editTemplateModal',jsonDataToServer);
});*/



function populateQuestionTemplateMaster_new(modalPopupElement,jsonDataToServer) {
    var dataFromServer = '';
    $.ajax({
        type: "POST",
        url: "DataController",
        async: false,
        data: {
            message: "fetchQuestions",
            JDATA: jsonDataToServer
        },
        success: function (data) {
            dataFromServer = jQuery.parseJSON(data);
//            sessioncheckforpopulatingdata(data);
        },
        error: function (xhr, ajaxOptions, throwError) {
            utilObj.showErrorMessageAlertForSessionTimeout("Error", "Error retrieving data.");
        }
    });

    var selector = $(modalPopupElement).find('.templateQuestionList');
    selector.empty();
    console.log("-------");
    console.log(dataFromServer);
    $.each(dataFromServer, function (idx, obj) {
        //selector.append('<li id="quesId_' + obj.quesId + '"><strong>[' + (idx+1) + '] </strong> ' + obj.quesDesc + '</li>');
    	if(obj.quesStatus == 'Active'){
            selector.append('<li id="quesId_' + obj.quesId + '"><strong>[' + (idx+1) + '] </strong> ' + obj.quesDesc + '</li>');
        } 
    });
}

/*
//----------Feedback Extension Modal----------
function populateFeedbackExtensionModal(modalPopupElement) {
	//var jsonDataToServer = '{"feedbackId":"' + $(this).attr("id").split("#")[1] + '"}';
	var a=$(this).parents('tr').find('#editNomineeName');
	
    var dataFromServer = '';
    $.ajax({
        type: "POST",
        url: "DataController",
        async: false,
        data: {
            message: "fetchFeedback",
            JDATA: jsonDataToServer
        },
        success: function (data) {
            dataFromServer = jQuery.parseJSON(data);
        },
        error: function (xhr, ajaxOptions, throwError) {
            utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
        }
    });
    	
    $('#editNomineeName').val('')
}
//----------Feedback Extension Modal----------
*/


function populateQuestionTemplateMaster(modalPopupElement) {
	
    var dataFromServer = '';
    $.ajax({
        type: "POST",
        url: "DataController",
        async: false,
        data: {
            message: "fetchQuestions",
            JDATA: ""
        },
        success: function (data) {
            dataFromServer = jQuery.parseJSON(data);
//            sessioncheckforpopulatingdata(data);
        },
        error: function (xhr, ajaxOptions, throwError) {
            utilObj.showErrorMessageAlertForSessionTimeout("Error", "Error retrieving data.");
        }
    });

    var selector = $(modalPopupElement).find('.templateQuestionList');
    selector.empty();
    console.log("-------");
    console.log(dataFromServer);
    $.each(dataFromServer, function (idx, obj) {
        //selector.append('<li id="quesId_' + obj.quesId + '"><strong>[' + (idx+1) + '] </strong> ' + obj.quesDesc + '</li>');
    	if(obj.quesStatus == 'Active'){
    		if(obj.negativeQuest==true)
    			selector.append('<li style="color:red" id="quesId_' + obj.quesId + '"><strong>[' + (idx+1) + '] </strong> ' + obj.quesDesc + '</li>');
    		else
    			selector.append('<li id="quesId_' + obj.quesId + '"><strong>[' + (idx+1) + '] </strong> ' + obj.quesDesc + '</li>');
        } 
    });
}

function getSectorList() {

	var userDataJson = null;
	$.ajax({
		type : "POST",
		url : "DataController",
		async : false,
		data : {
			message : "getSectorList",
			JDATA : null
		},
		success : function(data) {
			userDataJson = data;
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});

	// alert(userDataJson);
	userDataJson = jQuery.parseJSON(userDataJson);
	var selector = $('.secAdminSelect');
	selector.empty();
	selector.append("<option value='0'>---Select---</option>");
	$.each(userDataJson, function(idx, obj) {
		selector.append("<option value=" + obj.sectorId + ">" + obj.sectorId
				+ ' - ' + obj.sectorName + "</option>");
	});
}

function getCompanyList(dataString) {

	var userDataJson = null;
	$.ajax({
		type : "POST",
		url : "DataController",
		async : false,
		data : {
			message : "getCompanyList",
			JDATA : dataString
		},
		success : function(data) {
			userDataJson = data;
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});

	userDataJson = jQuery.parseJSON(userDataJson);
	var selector = $('.comAdminSelect');
	selector.empty();
	selector.append("<option value='0'>---Select---</option>");
	$.each(userDataJson, function(idx, obj) {
		selector.append("<option value=" + obj.companyId + ">" + obj.companyId
				+ ' - ' + obj.companyName + "</option>");
	});
}

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
//			console.log("status data-->"+dashBrdJson);
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});
	
	var btnHtml = "";		
	var statusLink = "";
    var customDashboardItemList = new Array();	
    dashBrdJson  = JSON.parse(dashBrdJson);
    			  		
	$.each(dashBrdJson, function( index, dashBrdItem)  {
				if(!dashBrdItem.closedStatus)
					btnHtml = "<button type='button' class='btn btn-danger editQuestionBtn' data-toggle='tooltip' id='closeSurveyBtn#"+dashBrdItem.feedBackId+"' data-placement='bottom' title='Close'><span class='glyphicon glyphicon-pencil'></span></button>";
				else
					btnHtml = "<button type='button' class='btn btn-danger editQuestionBtn' data-toggle='tooltip' id='reopenSurveyBtn#"+dashBrdItem.feedBackId+"' data-placement='bottom' title='Reopen'><span class='glyphicon glyphicon-open'></span></button>";
		dashBrdItem.actionType = btnHtml;
	    
	    //-----------------Camel case queType-----------------   	
		if(dashBrdItem.Status!=null){
		var str2 = dashBrdItem.Status.replace(/_/g, ' ');	
        var camelStatus=toTitleCase(str2.toLowerCase());
        dashBrdItem.Status=camelStatus;
		}
		//--------------------------end----------------- 
	    
		statusLink = "<a href='#' data-feedbackId='"+dashBrdItem.feedBackId+"'>"+dashBrdItem.NoOfRespondents+"</a>";
		dashBrdItem.respCountStatus = statusLink;
		
	    customDashboardItemList.push(dashBrdItem);	                	                		
	});	
	
	$('#dashMasterTable').bootstrapTable("destroy");		
	$('#dashMasterTable').bootstrapTable({		
        method: 'get',		
        data: customDashboardItemList,		
        cache: false,		
        height: 420,		
        striped: true,		
        pagination: true,		
        pageSize: 10,		
        pageList: [10, 15, 20, 25, 50, 100, 200],		
        search: false,		
        showRefresh: false,		
        showToggle: false,		
        clickToSelect: true,		
        columns: [ {		
            field: 'Name',		
            title: 'Nominee Name',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        },{		
            field: 'TemplateName',		
            title: 'Template Name',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'StartDate',		
            title: 'Start Date',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'EndDate',		
            title: 'End Date',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            //field: 'NoOfRespondents',		
        	field: 'respCountStatus',		
            title: 'Respondents',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'NoOfRespponsesReceived',		
            title: 'Responses',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'SelfFeedback',		
            title: 'Self Feedback',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'actionType',		
            title: 'Close',		
            align: 'center',		
            valign: 'top',		
            sortable: false		
        }, {		
            field: 'Status',		
            title: 'Status',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }]		
    });		

	 /*var tr;
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
		tr.append('<td class="nr"><button type="button" class="btn btn-danger closeSurveyBtn" data-toggle="tooltip" id="closeSurveyBtn#'+obj.feedBackId+'" data-placement="bottom" title="Close">'+
		'<span class="glyphicon glyphicon-pencil"></span></button></td>');
		tr.append("<td>" + obj.Status+"</td>");
	    tr.append('<td><button type="button" class="btn btn-danger ReminderBtn" data-toggle="tooltip" id="ReminderBtn#'+obj.feedBackId+' data-placement="bottom" title="Remind">'+
				'<span class="glyphicon glyphicon-pencil"></span></button></td>');
	    $('table#dashMasterTable tbody').append(tr);
	    });*/
}


$(document).on("click", "#dashMasterTable button", function(e) {
	$(this).trigger("blur");
	var jsonDataToServer;
	var btn=$(this).attr("id").split("#")[0];
	if(btn==="closeSurveyBtn")
		msg="closeSurvey";
	else if(btn==="reopenSurveyBtn")
		msg="reOpenSurvey";
		
	jsonDataToServer = '{"feedbackid":"' + $(this).attr("id").split("#")[1] + '"}';
	$.ajax({
			type : "POST",
			url : "FeedbackController",
			async : false,
			data : {
				message : msg,
				JDATA : jsonDataToServer
			},
			success : function(data) {
				if(data==="closedTrue"){
					utilObj.showSuccessMessageAlert("Sucess", "Survey Closed Successfully..!");
					getDashBrd();
					}
				else if(data==="reopenedTrue"){
					utilObj.showSuccessMessageAlert("Sucess", "Survey Reopened Successfully..!");
					getDashBrd();
				}
				else if(data==="closedFalse")
					utilObj.showErrorMessageAlert("Error", "Could not Close Survey..!");
				else if(data==="reopenedFalse")
					utilObj.showErrorMessageAlert("Error", "Could not Reopen Survey..!");
				else if(data==="closedError")
					utilObj.showErrorMessageAlert("Error", "Error in Closing Survey..!");
				else if(data==="reopenError")
					utilObj.showErrorMessageAlert("Error", "Error in Reopning a Survey..!");
				else if(data==="closeCriteriaError")
					utilObj.showConfirmClose(function(isConfirm){
						if(isConfirm){
						$.ajax({
							type : "POST",
							url : "FeedbackController",
							async : false,
							data : {
								message : "forceClosure",
								JDATA : jsonDataToServer
							},
							success : function(data) {
								dashBrdJson = data;
								if(data==="closedTrue"){
								utilObj.showSuccessMessageAlert("Closed", "Survey Closed Successfully..!");
								getDashBrd();
								}
								else if(data==="closedFalse")
									utilObj.showErrorMessageAlert("Error", "Could not Close Survey..!");
								else if(data==="closedError")
									utilObj.showErrorMessageAlert("Error", "Error in Closing Survey..!");
//								sessioncheckforpopulatingdata(data);
							},
							error : function(xhr, ajaxOptions, throwError) {
							    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
							}
						});
						}
						else
							swal("Cancelled", "Survey not closed..!", "error");
					});
				else if(data==="reopenCriteriaError")
					utilObj.showErrorMessageAlert("Error", "Survey Already Exists..!");
//				else
//					sessioncheckforpopulatingdata(data);
			},
			error : function(xhr, ajaxOptions, throwError) {
			    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
			}
		});
});

//-------------------------------------------------------------------------
//------------------------report-------------------------------------------

function getReportDash() {
	//alert("in report dash");
	var dashBrdJson = null;
	$.ajax({
		type : "POST",
		url : "DashBoardUIController",
		async : false,
		data : {
			message : "Reports",
			JDATA : ""
		},
		success : function(data) {
			dashBrdJson = data;
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});

	
	var btnHtml = "";		
    var customDashboardItemList = new Array();	
    dashBrdJson  = JSON.parse(dashBrdJson);
    var dis="";
	$.each(dashBrdJson, function( index, reportMasterItem)  {
		if(reportMasterItem.reportFlag && reportMasterItem.closedStatus)
			dis="";
		else
			dis= "disabled";
		
		btnHtml = "<button type='button' "+dis+"  class='btn btn-danger' data-toggle='tooltip' id='ReportBtn#"+reportMasterItem.feedBackId+"' onClick='getReport(1);' data-placement='bottom' title='View Report'><span class='glyphicon glyphicon-eye-open'></span></button>";
		reportMasterItem.SrNo = index + 1;
		reportMasterItem.actionType = btnHtml;
		
		//-----------------Camel case queType-----------------   	
			if(reportMasterItem.Status!=null){
			var str2 = reportMasterItem.Status.replace(/_/g, ' ');	
		    var camelStatus=toTitleCase(str2.toLowerCase());	
            reportMasterItem.Status=camelStatus;
			}
		//--------------------------end----------------- 
		
	    customDashboardItemList.push(reportMasterItem);	                	                		
	});	
	
	$('#reportMasterTable').bootstrapTable("destroy");		
	$('#reportMasterTable').bootstrapTable({		
        method: 'get',		
        data: customDashboardItemList,		
        cache: false,		
        height: 420,		
        striped: true,		
        pagination: true,		
        pageSize: 10,		
        pageList: [10, 15, 20, 25, 50, 100, 200],		
        search: false,		
        showRefresh: false,		
        showToggle: false,		
        clickToSelect: true,		
        columns: [ {		
            field: 'SrNo',		
            title: 'S. No.',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'Name',		
            title: 'Nominee Name',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'TemplateName',		
            title: 'Template Name',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'StartDate',		
            title: 'Start Date',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'EndDate',		
            title: 'End Date',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'NoOfRespondents',		
            title: 'Respondents',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'NoOfRespponsesReceived',		
            title: 'Responses',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'Status',		
            title: 'Status',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'actionType',		
            title: 'View Report',		
            align: 'center',		
            valign: 'top',		
            sortable: false		
        }/*, {		
            field: 'SelfFeedback',		
            title: 'Self Feedback',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }*/]		
    });		
	 /*var tr;
	    $('table#reportMasterTable tbody').empty();
	    $.each(jQuery.parseJSON(dashBrdJson),function(idx, obj) {
	    tr = $('<tr/>');
	    tr.append("<td>" + (idx+1)+ "</td>");
	    tr.append("<td>" + obj.Name+ "</td>");
	    tr.append("<td>" + obj.TemplateName+ "</td>");
	    tr.append("<td>" + obj.StartDate+ "</td>");
	    tr.append("<td>" + obj.EndDate+ "</td>");
	    tr.append("<td>" + obj.NoOfRespondents+ "</td>");       
		tr.append("<td>" + obj.NoOfRespponsesReceived+"</td>");
		//tr.append("<td>" + obj.SelfFeedback+"</td>");
		tr.append('<td><button type="button" class="btn btn-danger editQuestionBtn" data-toggle="tooltip" id="closeSurveyBtn#'+obj.FeedBackId+'" data-placement="bottom" title="Close">'+
		'<span class="glyphicon glyphicon-pencil"></span></button></td>');
		tr.append("<td>" + obj.Status+"</td>");
	    tr.append('<td><button type="button" class="btn btn-danger" data-toggle="tooltip" id="ReportBtn#'+obj.feedBackId+'" onClick="getReport(1);" data-placement="bottom" title="View Report">'+
				'<span class="glyphicon glyphicon-eye-open"></span></button></td>');
	    $('table#reportMasterTable tbody').append(tr);
	    });*/
}
//-----------------------------Report End----------------------------------
//-------------------------------------------------------------------------

//-----------------------------Feedback Extention--------------------------
//----------------------------------------------------------------------------
function getFeedbackExtension() {

	var extensionJson = null;
	$.ajax({
		type : "POST",
		url : "FeedbackController",
		async : false,
		data : {
			message : "fetchFeedbackList",
			JDATA : ""
		},
		success : function(data) {
			extensionJson = data;
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});
	
	var btnHtml = "";		
    var customExtensionItemList = new Array();	
    extensionJson  = JSON.parse(extensionJson);
    
    
	$.each(extensionJson, function( index, extensionItem)  {
	
		if(extensionItem.Status=='COMPLETED')
			dis="disabled";
		else
			dis= "";

		btnHtml = "<button type='button' "+dis+" class='btn btn-danger feedbackextensionBtn' data-toggle='tooltip' id='feedbackextensionBtn#" + extensionItem.feedBackId+ "' data-placement='bottom' title='Extend'><span class='glyphicon glyphicon-pencil'></span></button>";
		extensionItem.SrNo = index + 1;
		extensionItem.actionType = btnHtml;	
		customExtensionItemList.push(extensionItem);	                	                		
	});	
	
	$('#FeedbackExtensionMasterTable').bootstrapTable("destroy");		
	$('#FeedbackExtensionMasterTable').bootstrapTable({		
        method: 'get',		
        data: customExtensionItemList,		
        cache: false,		
        height: 420,		
        striped: true,		
        pagination: true,		
        pageSize: 10,		
        pageList: [10, 15, 20, 25, 50, 100, 200],		
        search: false,		
        showRefresh: false,		
        showToggle: false,		
        clickToSelect: true,		
        columns: [ {		
            field: 'SrNo',		
            title: 'S. No.',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'Name',		
            title: 'Nominee Name',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'TemplateName',		
            title: 'Template Name',		
            align: 'center',		
            valign: 'middle',		
            sortable: true		
        }, {		
            field: 'StartDate',		
            title: 'Start Date',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'EndDate',		
            title: 'End Date',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'NoOfRespondents',		
            title: 'Respondents',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'NoOfRespponsesReceived',		
            title: 'Responses',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'SelfFeedback',		
            title: 'Self Feedback',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }, {		
            field: 'actionType',		
            title: 'Extend',		
            align: 'center',		
            valign: 'top',		
            sortable: false		
        }, {		
            field: 'Status',		
            title: 'Status',		
            align: 'center',		
            valign: 'top',		
            sortable: true		
        }]		
    });
	
	//$('.feedbackextensionBtn').click(function() {
	 $('#FeedbackExtensionMasterTable').on('click',".feedbackextensionBtn",function(){
		$('.editFeedbackExtensionModal').modal('show');
		var jsonDataToServer = '{"feedbackid":"' + $(this).attr("id").split("#")[1] + '"}';
		var userDataJson='';
		$.ajax({
			type : "POST",
			url : "FeedbackController",
			async:false,
			data : {
				message:"fetchFeedback",
				JDATA: jsonDataToServer
			},
			success : function(data) {
				userDataJson = data;
//				sessioncheckforpopulatingdata(data);
			},
			error : function(xhr, ajaxOptions, throwError) {
			    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
			}
		});
		
		userDataJson  = JSON.parse(userDataJson);
		var a= userDataJson.firstName;
		$('#editNomineeName').val(userDataJson.firstName+" "+userDataJson.lastName);
		$('#editstartdatefdext').val(userDataJson.fromDate);
		$('#editenddatefdext').val(userDataJson.toDate);
		$('#feedbackExtId').val($(this).attr("id").split("#")[1]);
		
		
		$('.savefdextBtn').click(function() {
			
			var destination='';
			var editenddatefdext=$('#editenddatefdext').val();
			
			
			if(editenddatefdext=="" || !utilObj.isValidDate("#editenddatefdext")){
				utilObj.showErrorMessageAlert("Error","Enter valid To Date..!");
				return;
			}
			
			var jsonDataToServer = '{"feedbackid":"'+ $('#feedbackExtId').val() +'","endDate":"'+$('#editenddatefdext').val()+'"}';
			$.ajax({
				type : "POST",
				url : "FeedbackController",
				async:false,
				data : {
					message:"extendDate",
					JDATA: jsonDataToServer
				},
				success : function(data) {
					userDataJson = data;
					if(data==='Extended successfully..!')
						utilObj.showSuccessMessageAlert("Success", "Survey Extended successfully.");
					$('#fbReOpenExtn').trigger('click');
//					sessioncheckforpopulatingdata(data);
				},
				error : function(xhr, ajaxOptions, throwError) {
				    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
				}
			});
			$('.editFeedbackExtensionModal').modal('hide');
		});
		
		
		
	});
}
//--------------------------------------------------------------------------
//-----------------------------Feedback Extention-----------------------------

function populateTemplateList(){
	var userDataJson = null;
	$.ajax({
		type : "POST",
		url : "DataController",
		async:false,
		data : {
		     	message:"getTemplateList",
			    JDATA:null
		},
		success : function(data) {
			userDataJson = data;
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});
	
	//alert(userDataJson);
	userDataJson = jQuery.parseJSON(userDataJson);
	var selector = $('.tempSelect');
	selector.empty();
	selector.append("<option value='0'>---Select---</option>");
	$.each(userDataJson, function(idx, obj) {
		selector.append("<option value=" + obj.templateId + ">" + obj.templateId+' - '+obj.templateName+ "</option>");
	});
	
	//createReminderDiv(true);
}
function populatePendingRequestList(){
	var userDataJson = null;
	$.ajax({
		type : "POST",
		url : "DataController",
		async:false,
		data : {
			message:"fetchPendingRequests",
			JDATA:null
		},
		success : function(data) {
			userDataJson = data;
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});
	
	userDataJson = jQuery.parseJSON(userDataJson);
	var selector = $('#selectRequestId');
	selector.empty();
	selector.append("<option value='0'>---Select---</option>");
	$.each(userDataJson, function(idx, obj) {
		selector.append("<option value=" + obj.requestId + ">" + obj.requestId+' - '+obj.feedbackName+ "</option>");
	});
	
}


function createReminderDiv(remFlag){
	$("#reminderDiv").empty();
	var remContent1="<div class='col-md-3 form-group'>"+
					"<span>From Date<strong>*</strong>:</span> <input type='text' class='form-control' id='fromDt' disabled></div>"+
					"<div class='col-md-3 form-group'>"+
					"<span>To Date<strong>*</strong>:</span> <input type='text' class='form-control' id='toDt'></div>";
	
	var remContent2="<div class='col-md-3 form-group'>"+
					"<span>From Date<strong>*</strong>:</span> <input type='text' class='form-control' id='fromDt' disabled></div>"+
					"<div class='col-md-3 form-group'>"+
					"<span>To Date<strong>*</strong>:</span> <input type='text' class='form-control' id='toDt'></div>";
	
	var remContent3="<div class='col-md-3 form-group'>"+
					"Reminder Duration (Days)<strong>*</strong>"+
					"<select class='form-control selectReminder' name='reminder' id='selectReminder'>"+
					"<option id=''>7 </option>"+
					"<option id=''>14</option>"+
					"<option id=''>21</option>"+
					"<option id=''>30</option>"+
					"</select>"+
					"</div>";

	if(remFlag){
		$("#reminderDiv").html(remContent1.concat(remContent3));
	}else{
		$("#reminderDiv").html(remContent2);
	}
	
	$('#fromDt').datepicker({
        minDate: 0,
        onSelect: function (selected) {
            var dt = new Date(selected);
            dt.setDate(dt.getDate() + 1);
            $("#toDt").datepicker("option", "minDate", dt);
            return $(this).trigger('change');
        }
    });
    $('#toDt').datepicker({
        minDate: 1,
        onSelect: function (selected) {
            var dt = new Date(selected);
            dt.setDate(dt.getDate() - 1);
            $("#fromDt").datepicker("option", "maxDate", dt);
            return $(this).trigger('change');
        }
    });
}


/**********************TO GET CATEGORY LIST*****************************************************/
function getCategoryList(modalId){
	var userDataJson = null;
	$.ajax({
		type : "POST",
		url : "DataController",
		async:false,
		data : {
			message:"fetchCategoryList",
			JDATA:null
		},
		success : function(data) {
			userDataJson = data;
			console.log("CATEGORY"+data);
			
			userDataJson = jQuery.parseJSON(userDataJson);
//			var selector = $('#qbAddModalQuestCat');
			var selector = $(modalId);
			console.log(selector);
			selector.empty();
			selector.append("<option value='0'>---Select---</option>");
			$.each(userDataJson, function(idx, obj) {
				selector.append('<option value="' + obj.quesCat +'">' + obj.quesCat +'</option>');
			});
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});
}

/**************************************TO GET SUB CATEGORY LIST************************************************************/
function getSubCategoryList(dataToServer,modalId){
	var userDataJson = null;
	$.ajax({
		type : "POST",
		url : "DataController",
		async:false,
		data : {
			message:"fetchSubCategoryList",
			JDATA: '{"quesCat":"'+dataToServer+'"}'
		},
		success : function(data) {
			userDataJson = data;
			console.log("SUBCATEGORY"+data);
			
			userDataJson = jQuery.parseJSON(userDataJson);
			//var selector = $('#qbAddModalQuestSubCat');
			var selector = $(modalId);
			selector.empty();
			selector.append("<option value='0'>---Select---</option>");
			$.each(userDataJson, function(idx, obj) {
				selector.append("<option value=" + obj.quesCatId + "> " + obj.quesSubCat +"</option>");
			});
//			sessioncheckforpopulatingdata(data);
		},
		error : function(xhr, ajaxOptions, throwError) {
		    utilObj.showErrorMessageAlert("Error", "Error retrieving data.");
		}
	});
}

/**********************************************RESET FORMS**********************************************************************/
function resetAddQuestionForm() {
    $('#qbAddModalQuestCat').val('0');
    $('#qbAddModalQuestSubCat').val('0');
    $('#qbAddModalQuestDesc').val('');
    $('#qbAddModalQuestType').val('0');
    $('#qbAddModalQuestDescTagLine').val('');
    $('#addQuestNegCheck').prop('checked',false);
}


function resetAddTemplateForm() {
    $('input[name="templateStatus"]').prop("checked", false);
    $('input[name="templateStatus"]').parent().removeClass("active");

    $('.addTemplateModal .templateQuestionList li').removeClass("selectedQuestion");
    $(".addTemplateModal .selectAllQuestion").text("Select All");

    var selectedQuestionsCount = $(".addTemplateModal .templateQuestionList .selectedQuestion").length;
    $(".addTemplateModal .selectedQuestionsCount").empty();
    $(".addTemplateModal .selectedQuestionsCount").html(selectedQuestionsCount);

    $("#addTemplateMasterName").val('');
}

function resetEditTemplateForm() {
    $('input[name="editTemplateStatus"]').prop("checked", false);
    $('input[name="editTemplateStatus"]').parent().removeClass("active");

    $('.editTemplateModal .templateQuestionList li').removeClass("selectedQuestion");
    $(".editTemplateModal .selectAllQuestion").text("Select All");

    var selectedQuestionsCount = $(".editTemplateModal .templateQuestionList .selectedQuestion").length;
    $(".editTemplateModal .selectedQuestionsCount").empty();
    $(".editTemplateModal .selectedQuestionsCount").html(selectedQuestionsCount);

    $("#editTemplateMasterName").val('');
}

function resetNominationReqForm() {
    $('#nominationCat').val('0');
    $('#selectRequestId').val('0');
    $('#comAdminSelect').val('0');
    $('#secAdminSelect').val('0');
    $('#fName').val('');
    $('#lName').val('');
    $('#email').val('');
    $('#toDt').val('');
    $('#templateSelectDiv').hide();
    $('#secAdminSelect').hide();
    $('#comAdminSelect').hide();
    $('#empDtlsDiv').hide();
    
    $('#selectSectorDiv').hide();
    $('#selectCompanyDiv').hide();
    $('#reminderDiv').hide();
}

function resetFeedbackExtensionEditForm() {
    $('#editNomineeName').val('');
   }

function bindRespondentStatusDetailsForAdmin(statusDetailsList){
	    var respondentStatusList = new Array();		
	    			  				
		$.each(statusDetailsList, function( index, respondent)  {						
			respondent.fullName = respondent.respondentFName + " " + respondent.respondentLName;				
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
						
		$('#respondantStatusForAdminTable').bootstrapTable("destroy");				
		$('#respondantStatusForAdminTable').bootstrapTable({				
	        method: 'get',				
	        data: respondentStatusList,				
	        cache: false,					       			
	        striped: true,				
	        pagination: true,				
	        pageSize: 10,				
	        pageList: [10,20,50],				
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
		
//		$(".search").addClass("right-inner-addon");
//		$(".search").append("<i class='glyphicon glyphicon-search'></i>");
}
/*function sessioncheckforpopulatingdata(data){
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

};*/
