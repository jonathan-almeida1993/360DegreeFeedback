package com.mahindra.common.constants;

public interface SqlConstants {

	//LOGIN PAGE QUERIES
	String AUTHENTICATE_USER = "select FirstName,LastName,EmailId,isPwdChanged,Role,t1.SectorId,t1.CompanyId,t2.SectorName,t3.CompName from AuthenticationMasterTbl t1 "+
								"left outer join SectorMasterTbl t2 on t1.SectorId = t2.SectorId "+
								"left outer join CompanyMasterTbl t3 on t1.CompanyId = t3.CompanyId where EmailId=? and Password=?";
	String CHANGE_PWD_REQ = "select FirstName,LastName,Role,Password from AuthenticationMasterTbl  where EmailId = ?";
	String RESET_PWD_REQ = "update AuthenticationMasterTbl set Password = ?,isPwdChanged = ? where EmailId = ?";
	
	
	//QUESTION MASTER QUERIES
	String INSERT_QUERY_FOR_QUESTIONMASTER = "insert into QuestionMasterTbl (QuestCatId,QuestDesc,QuestType,QuestTagLine,OptionValues,NegativeQuestionFlag,Status) values (?,?,?,?,?,?,'Active')";
	String UPDATE_QUERY_FOR_QUESTIONMASTER = "update QuestionMasterTbl set  QuestDesc= ?,NegativeQuestionFlag=?, Status = ? where QuestionId = ?"; 
	String DELETE_QUERY_FOR_QUESTIONMASTER = "update QuestionMasterTbl set status='Inactive' where QuestionId = ?";
	String SELECT_QUERY_FOR_QUESTIONMASTER = "SELECT qt.QuestCatId,qc.QuestCategory,qc.QuestSubCategory,QuestDesc,QuestType,QuestTagLine,NegativeQuestionFlag,"+
											 "OptionValues,Status,QuestionId FROM QuestionMasterTbl  qt "+
											 "inner join QuestionCategoryTbl  qc on qc.QuestCatId = qt.QuestCatId "+
											 "where QuestionId = ?";
	String SELECT_ALL_QUERY_FOR_QUESTIONMASTER = "SELECT qt.QuestCatId,qc.QuestCategory,qc.QuestSubCategory,QuestDesc,QuestType,QuestTagLine,NegativeQuestionFlag,"+
											     "OptionValues,Status,QuestionId FROM QuestionMasterTbl  qt "+
											     "inner join QuestionCategoryTbl  qc on qc.QuestCatId = qt.QuestCatId";

	//get selected question for template
	String SELECT_TAMPLATE_REL_QUE="SELECT qt.QuestCatId,qc.QuestCategory,qc.QuestSubCategory,QuestDesc,QuestType,QuestTagLine,NegativeQuestionFlag,"
			+ "OptionValues,Status,qt.QuestionId FROM QuestionMasterTbl  qt "
			+ "inner join QuestionCategoryTbl  qc on qc.QuestCatId = qt.QuestCatId "
			+ "inner join QuestionTemplateMapTbl questTemp on qt.QuestionId = questTemp.QuestionId "
			+ "where questTemp.TemplateId =?";
	
	
	//TEMPLATE MASTER QUERIES
 	String INSERT_QUERY_FOR_TEMPLATEMASTER = "insert into TemplateMasterTbl (TemplateName,TemplateStatus) values(?,'Active')";
 	String INSERT_QUERY_FOR_QUESTION_TEMPLATE_MAP = "  insert into QuestionTemplateMapTbl (TemplateId,QuestionId)  values (?,?)";
 	String 	FETCH_TEMPLATE_DETAILS = "select tempMast.TemplateName,tempMast.TemplateId,questTemp.QuestionId,tempMast.TemplateStatus "+
 									  "from TemplateMasterTbl tempMast "+
 									  "inner join QuestionTemplateMapTbl questTemp on tempMast.TemplateId = questTemp.TemplateId "+
 									  "where tempMast.TemplateId = ?";
 	
 	String UPDATE_TEMPLATE_STATUS ="update templatemastertbl set templatestatus = ? where templateid = ? ";
 	

 	//EMPLOYEE NOMINATION QUERIES
	String SELECT_NOMINATION_CATEGORY = "select * from empnominationtbl where role= ?";
	String SELECT_SECTOR_LIST = "select * from SectorMasterTbl";
	String SELECT_SECTOR_BASED_COMPANY_LIST = "select * from CompanyMasterTbl where sectorId= ?";
	String SELECT_TEMPLATE_LIST = "select * from TemplateMasterTbl where templateStatus='Active'";
	String SELECT_TEMPLATE_LIST_ALL = "select * from TemplateMasterTbl";
	String SP_NOMINATE_EMP = "{call employeeNominationSP (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	String SP_REPORT_GEN = "{call filterDataForReportSP (?,?)}";
	String SELECT_PENDING_REQUESTS = "select t1.RequestId,t3.TemplateName from EmployeeNominationRequestTbl t1 "+
									 "inner join TransactionMasterTbl t2 on t1.RequestId = t2.RequestId "+
									 "left outer join TemplateMasterTbl t3 on t1.TemplateId = t3.TemplateId "+	
									 "where  t1.Status = ? and t2.destination = ? and t2.RequestFwdFlag = 'PENDING' ";
	String EXTEND_SURVEY_DATE= "update feedbackmastertbl set EndDate= ? where feedbackId=? ";

	String GET_ACTIVE_FEEDBACK_ID = "select FeedbackId "
			+ "from FeedbackMasterTbl "
			+ "where EmailId = ? and FeedBackStatus IN ('PENDING_WITH_EMP','IN_PROGRESS') "
			+ "AND (closedStatus is null or closedStatus='0') ";//and (GETDATE() BETWEEN StartDate AND EndDate)
	
	
	String GET_FEEDBACK_FOR_EXTENSION="select amt.FirstName collate SQL_Latin1_General_CP850_CI_AI fName,"
			+ "amt.LastName collate SQL_Latin1_General_CP850_CI_AI lName, "
			+ " fmt.StartDate fromDate,fmt.EndDate toDate from FeedbackMasterTbl fmt left outer join AuthenticationMasterTbl amt"
			+ " on fmt.EmailId collate SQL_Latin1_General_CP850_CI_AI=amt.EmailId where fmt.FeedbackId= ?";
	
	String GET_NOMINATED_EMP_RESPONDENT_ID = "select RespondentId from RespondentFeedbackMapTbl respFb "
			+ "inner join FeedbackMasterTbl fbkMaster "
			+ "on fbkMaster.FeedbackId = respFb.FeedbackId "
			+ "where respFb.FeedbackId = ? and RespondentEmailId = ? and EmpRelation = 'SELF'";

	String SP_NOMINATE_RESPONDENT = "{call RespondentSelectionSP (?,?,?,?,?,?,?,?,?)} ";

	String SELECT_RESPONDENTS = "select RespondentId, resp.FeedbackId, EmpRelation, RespondentEmailId, RespondentFirstName, RespondentLastName,RespondentStatus,fmaster.FeedbackStatus "+
								"from RespondentFeedbackMapTbl resp "+
								"left outer join  FeedbackMasterTbl fmaster on resp.feedbackId = fmaster.feedbackId "+
								"where resp.feedbackId = ? and RespondentStatus = 'ACTIVE' and EmpRelation<>'SELF' ";
	
	String GET_RESPONDENT_SUGGESTIONS ="select EmpFirstName,EmpLastName,EmailId,'peer' as EmpRelation from EmployeeMaster "+
						"where MangerNo = (select MangerNo from EmployeeMaster where EmailId = ?) AND EmailId <>? "+
						"union "+
						"select EmpFirstName,EmpLastName,EmailId,'senior' as EmpRelation from EmployeeMaster "+
						"where EmployeeNo IN (select MangerNo from EmployeeMaster where EmailId = ?) "+
						"union "+
						"select EmpFirstName,EmpLastName,EmailId,'subordinate' as EmpRelation from EmployeeMaster "+ 
						"where MangerNo IN (select EmployeeNo from EmployeeMaster where EmailId = ?)";
	
	//SELF-FEEDBACK QUERIES 
	String GET_FEEDBACK_STATUS = "select FeedbackStatus from FeedbackMasterTbl where feedbackId = ?";
	String SELECT_QUERY_FOR_SELF_FEEDBACK = "select q.questionid,q1.questdesc,q1.questtagline,q1.questtype,q1.NegativeQuestionFlag "+
												"from feedbackmastertbl f inner join respondentfeedbackmaptbl r "+
												"on f.feedbackid = r.feedbackid "+
												"inner join  questiontemplatemaptbl q "+
												"on f.templateid = q.templateid "+ 
												"inner join questionmastertbl q1 on "+ 
												"q.questionid = q1.questionid "+
												"where f.feedbackid=? and r.respondentid= ? "+
												"order by q1.questtype,q.questionid ";
		
	String INSERT_QUERY_TO_CAPTURE_RESPONSE = "insert into FeedbackResponseTbl (FeedbackId,QuestionId,Response,RespondentId,CreatedDateTime) values(?,?,?,?,GETDATE())";
		
	String UPDATE_QUERY_TO_CAPTURE_RESPONSE = "update FeedbackResponseTbl set response=?, UpdatedDateTime=GETDATE() where feedbackid=? and questionid = ? and respondentId=?";
		
		
	String SELECT_QUERY_FOR_DISPLAY_PARTIALLY_SAVED_RESPONSE = "select q.questionid,q1.questdesc,q1.negativeQuestionFlag,q1.questtagline,q1.questtype,f1.response,f1.freetextresponse,r.feedbackstatus "
			+ "from feedbackmastertbl f inner join  questiontemplatemaptbl q on f.templateid = q.templateid "
			+ "inner join questionmastertbl q1 on q.questionid = q1.questionid "
			+ "left outer join FeedbackResponseTbl f1 on f.feedbackid = f1.feedbackid and q1.questionid = f1.questionid "
			+ "inner join respondentfeedbackmaptbl r on f.feedbackid = r.feedbackid and r.respondentId = f1.respondentId "
			+ "where f.feedbackid=? and r.respondentid = ? "
			+ "order by q1.questtype,q.questionid";		
		
	String SELECT_QUERY_TO_GET_FEEDBACK_STATUS = "select f.feedbackid,r.feedbackstatus from respondentfeedbackmaptbl r inner join feedbackmastertbl f "
												+ "on f.feedbackid = r.feedbackid "
												+  "where r.respondentid=? and f.feedbackid=?";
	String UPDATE_RESPONDENT_STATUS = "update RespondentFeedbackMapTbl set FeedbackStatus = ? , UpdatedDateTime=GETDATE() WHERE RespondentId = ?";


	String SELECT_EMPLOYEES ="select respFb.RespondentId,respFb.FeedbackId,respFb.FeedbackStatus,fmaster.EmailId,"
			+ "fmaster.closedStatus,fmaster.closedBy,authmaster.FirstName,authmaster.LastName,fmaster.StartDate, fmaster.EndDate "
			+ "from RespondentFeedbackMapTbl respFb "
			+ "inner join FeedbackMasterTbl fmaster on fmaster.FeedbackId = respFb.FeedbackId"
			+ "	Inner join AuthenticationMasterTbl authmaster on fmaster.EmailId collate Latin1_General_CI_AI = authmaster.EmailId"
			+ "	where respFb.RespondentStatus = 'ACTIVE' and respFb.EmpRelation<>'SELF' "
			+ "and respFb.RespondentEmailId=? and fmaster.FeedBackStatus IN ('IN_PROGRESS','COMPLETED');";
		
		
		
	String INSERT_RESPONSES = "{call responseCaptureSP (?,?)} ";


	String COUNT_FOR_EMPRELATION = "select EmpRelation,respfb.FeedbackStatus,count(EmpRelation) as Count "
			+"FROM RespondentFeedbackMapTbl respfb "
			+"inner join FeedbackMasterTbl fbmaster on respfb.feedbackId = fbmaster.feedbackId "
			+"group by EmpRelation,respfb.FeedbackStatus,respondentemailid,fbmaster.FeedbackStatus " 
			+"having respondentemailid=? and fbmaster.FeedbackStatus = 'IN_PROGRESS' ";


	String UPDATE_STATUS_AFTER_RESPONDENT_SELECTION = "update FeedbackMasterTbl set FeedbackStatus = 'IN_PROGRESS' where FeedbackId = ?";


	String SELECT_QUERY_FOR_CATEGORY_LIST = "select distinct(questcategory) from questioncategorytbl ";
	
	String SELECT_QUERY_FOR_SUBCATEGRY_LIST = "select questCatid,questsubcategory from questioncategorytbl where questCategory = ? ";

	
	String SELECT_QUERY_TO_GET_RESPONDENT_STATUS = "select fm.feedbackid,rm.respondentid,rm.emprelation,rm.respondentfirstname,rm.respondentlastname,"
			+"rm.respondentemailid,rm.feedbackstatus from feedbackmastertbl fm inner join "
			+"respondentfeedbackmaptbl rm on fm.feedbackid = rm.feedbackid "
			+"where fm.feedbackid=? ";
	
	
	
	//Mail Trigger Queries	
	String SELECT_EMP_DETAILS = "select FirstName,LastName,Password from AuthenticationMasterTbl where EmailId = ?";

	String SELECT_RESPONDENTS_FOR_MAIL = "select respFB.RespondentEmailId,auth.password ,fbmaster.EmailId,respFB.EmpRelation,auth.FirstName,auth.LastName,"+
		"auth1.FirstName as NomEmpFName,auth1.LastName  as NomEmpLName "+
		"from RespondentFeedbackMapTbl respFB "+
		"inner join AuthenticationMasterTbl auth "+ 
		"on auth.EmailId collate Latin1_General_CI_AI = respFB.RespondentEmailId "+
		"inner join FeedbackMasterTbl fbmaster on fbmaster.FeedbackId = respFB.FeedbackId "+
		"left outer join AuthenticationMasterTbl auth1 on auth1.EmailId collate Latin1_General_CI_AI = fbmaster.EmailId "+
		"where respFB.FeedbackId = ? and respFB.EmpRelation<>'SELF' and respFB.RespondentStatus = 'ACTIVE' and respFB.MailToRespondentSent = 0";
	
	String DASHBOARDINFOREPORTS="select amt.FirstName collate SQL_Latin1_General_CP850_CI_AI Fname,"
			+ " amt.LastName collate SQL_Latin1_General_CP850_CI_AI Lname, fmt.FeedbackId id,"+
			" tmt.TemplateName templateName,fmt.StartDate startDate,fmt.EndDate EndDate,"+
			" sum(case when fmt.FeedbackId = rfmt.FeedbackId then 1 else 0 end)-1 NoOfRespondents,"+
			" sum(case when rfmt.FeedbackStatus='COMPLETED' then 1 else 0 end)-1 NoOfResponses,"+
			" MAX(case when (rfmt.FeedbackStatus='COMPLETED' and rfmt.EmpRelation='SELF') then 1 else 0 end) selfFeedback,"+
			" fmt.FeedbackStatusToDisplay status, fmt.closedStatus closedStatus, rprtStat.SELF SELF, rprtStat.SENIOR SENIOR"+
			" from FeedbackMasterTbl fmt"+
			" left outer join TemplateMasterTbl tmt on fmt.TemplateId=tmt.TemplateId"+
			" left outer join RespondentFeedbackMapTbl rfmt on fmt.FeedbackId=rfmt.FeedbackId"+
			" left outer join AuthenticationMasterTbl amt on fmt.EmailId collate SQL_Latin1_General_CP850_CI_AI = amt.EmailId "+
			" left outer join (select feedbackId,SUM(CASE when EmpRelation='SELF' then 1 else 0 end) SELF,"
			+ " SUM(CASE when EmpRelation='SENIOR' then 1 else 0 end) SENIOR "
			+ " from RespondentDetailsForReport group by feedbackId) as rprtStat on rprtStat.feedbackId=fmt.FeedbackId";
			
	String DASHBOARDINFO="select amt.FirstName collate SQL_Latin1_General_CP850_CI_AI Fname,"
			+ " amt.LastName collate SQL_Latin1_General_CP850_CI_AI Lname, fmt.FeedbackId id,"+
			" tmt.TemplateName templateName,fmt.StartDate startDate,fmt.EndDate EndDate,"+
			" sum(case when fmt.FeedbackId = rfmt.FeedbackId then 1 else 0 end)-1 NoOfRespondents,"+
			" sum(case when rfmt.FeedbackStatus='COMPLETED' then 1 else 0 end)-1 NoOfResponses,"+
			" MAX(case when (rfmt.FeedbackStatus='COMPLETED' and rfmt.EmpRelation='SELF') then 1 else 0 end) selfFeedback,"+
			" fmt.FeedbackStatusToDisplay status, fmt.closedStatus closedStatus"+
			" from FeedbackMasterTbl fmt"+
			" left outer join TemplateMasterTbl tmt on fmt.TemplateId=tmt.TemplateId"+
			" left outer join RespondentFeedbackMapTbl rfmt on fmt.FeedbackId=rfmt.FeedbackId"+
			" left outer join AuthenticationMasterTbl amt on fmt.EmailId collate SQL_Latin1_General_CP850_CI_AI = amt.EmailId ";
	
			
	String DASHBOARDSRCH="select amt.FirstName collate SQL_Latin1_General_CP850_CI_AI Fname,"
			+ "amt.LastName collate SQL_Latin1_General_CP850_CI_AI Lname, fmt.FeedbackId id,"+
			" tmt.TemplateName templateName,fmt.StartDate startDate,fmt.EndDate EndDate,"+
			" sum(case when fmt.FeedbackId = rfmt.FeedbackId then 1 else 0 end)-1 NoOfRespondents,"+
			" sum(case when rfmt.FeedbackStatus='COMPLETED' then 1 else 0 end)-1 NoOfResponses,"+
			" MAX(case when (rfmt.FeedbackStatus='COMPLETED' and rfmt.EmpRelation='SELF') then 1 else 0 end) selfFeedback,"+
			" fmt.FeedbackStatusToDisplay status, fmt.closedStatus closedStatus"+
			" from FeedbackMasterTbl fmt"+
			" left outer join TemplateMasterTbl tmt on fmt.TemplateId=tmt.TemplateId"+
			" left outer join RespondentFeedbackMapTbl rfmt on fmt.FeedbackId=rfmt.FeedbackId"+
			" left outer join AuthenticationMasterTbl amt on fmt.EmailId collate SQL_Latin1_General_CP850_CI_AI = amt.EmailId "
			+ "Where 1=1 ";
			
	String FEEDBACKCLOSURE="Update FeedbackMasterTbl SET closureDate=GETDATE(), closedBy=? , closedStatus='true' where FeedbackId=?";
	
	String FEEDBACKREOPEN="Update FeedbackMasterTbl SET reOpenDate=GETDATE(), reOpenedBy=?, closedStatus='false' where FeedbackId=?";
	
	String CLOSURECONDITION="select SUM(CASE when EmpRelation='PEER' then 1 else 0 end) PEER, SUM"
			+ "(CASE when EmpRelation='SENIOR' then 1 else 0 end) SENIOR, SUM(CASE when EmpRelation='SUBORDINATE'"
			+ " then 1 else 0 end) SUBORDINATE, SUM(CASE when EmpRelation='OTHER' then 1 else 0 end)"
			+ " OTHER, SUM(CASE when EmpRelation='SELF' then 1 else 0 end) SELF from RespondentFeedbackMapTbl"
			+ " where FeedbackStatus='COMPLETED' and RespondentStatus='Active' and FeedbackId=? ";
	
	String REOPENCONDITION="SELECT TOP 1 EmailId FROM FeedbackMasterTbl WHERE EmailId=(select EmailId from FeedbackMasterTbl where FeedbackId=?) AND (closedStatus is null or closedStatus='0')";
	
	
	String GET_ACTIVE_FEEDBACK_POJO = "select * from (select fmt.FeedbackId, "
			+ "sum(case when fmt.FeedbackId = rfmt.FeedbackId then 1 else 0 end) NoOfRespondents,"
			+ " sum(case when rfmt.FeedbackStatus='COMPLETED' then 1 else 0 end) NoOfResponses"
			+ " from FeedbackMasterTbl fmt left outer join RespondentFeedbackMapTbl rfmt on fmt.FeedbackId=rfmt.FeedbackId"
			+ " where fmt.FeedbackId=rfmt.FeedbackId "
			+ "group by fmt.FeedbackId) feedbackResMap where NoOfResponses=NoOfRespondents";
	
	
	String GET_EMPLOYEE_MASTER_LIST="Select TOP 10 EmpFirstName,EmpLastName,EmailId from EmployeeMaster where EmailId is not null";
	
	String GET_DEFAULTER_EMPLOYEE="select  fmt.EmailId ,amt.Password,fmt.EmailId,amt.FirstName,amt.LastName  from FeedbackMasterTbl fmt, AuthenticationMasterTbl amt where fmt.EmailId=amt.Emailid and FeedBackStatus='PENDING_WITH_EMP' and dbo.CalculateNumberOFWorkDays( GETDATE(), dbo.fn_AddBusinessDays(StartDate,7)) <4 and dbo.CalculateNumberOFWorkDays( GETDATE(), dbo.fn_AddBusinessDays(StartDate,7))>0";
	
	String GET_DEFAULTER_EMPLOYEESELF="select fmt.EmailId,amt.Password,fmt.EmailId,amt.FirstName,amt.LastName from FeedbackMasterTbl fmt ,RespondentFeedbackMapTbl rsmt	,AuthenticationMasterTbl amt	where amt.EmailId=rsmt.RespondentEmailId and  rsmt.FeedbackStatus IN('PENDING','INPROGRESS') and rsmt.FeedbackId=fmt.FeedbackId and rsmt.EmpRelation='SELF' and dbo.CalculateNumberOFWorkDays( GETDATE(), dbo.fn_AddBusinessDays(StartDate,7)) IN (0,-3,-6,-9,-12,-15,-18,-21,-24,-27) ";
	
	String GET_DEFAULTER_EMPLOYEE_NOTSELF="select rsmt.RespondentEmailId,amt.Password,fmt.EmailId,amt.FirstName,amt.LastName,(select FirstName from AuthenticationMasterTbl amtbl where amtbl.EmailId=fmt.EmailId) As First,(select LastName from AuthenticationMasterTbl amtbl where amtbl.EmailId=fmt.EmailId) As LastName from FeedbackMasterTbl fmt ,RespondentFeedbackMapTbl rsmt ,AuthenticationMasterTbl amt	where amt.EmailId=rsmt.RespondentEmailId and rsmt.FeedbackStatus IN('PENDING','INPROGRESS') and rsmt.FeedbackId=fmt.FeedbackId and rsmt.EmpRelation !='SELF' and dbo.CalculateNumberOFWorkDays( GETDATE(), dbo.fn_AddBusinessDays(StartDate,7)) IN (0,-3,-6,-9,-12,-15,-18,-21,-24,-27)";
    
	String UPDATE_MAIL_SENT_TO_RESPONDENT_FLAG = "update RespondentFeedbackMapTbl set MailToRespondentSent = 1 where feedbackId = ? and RespondentEmailId = ?";

	String GET_STATUS_REPORT_DUMP = "select fmaster.FeedbackId, fmaster.EmailId,auth.FirstName,auth.LastName, fmaster.StartDate,fmaster.EndDate,fmaster.CreatedDateTime, fmaster.FeedBackStatus, "+ 
					"respfb.RespondentId,respfb.EmpRelation,respfb.RespondentEmailId,respfb.RespondentFirstName,respfb.RespondentLastName,respfb.FeedbackStatus "+
					"from FeedbackMasterTbl fmaster "+
					"inner join AuthenticationMasterTbl auth on auth.EmailId = fmaster.EmailId "+
					"left outer join RespondentFeedbackMapTbl respfb on fmaster.FeedbackId = respfb.FeedbackId ";

	String GET_SURVEY_DATES = "select StartDate,EndDate from FeedbackMasterTbl where FeedbackId = ?";
}
