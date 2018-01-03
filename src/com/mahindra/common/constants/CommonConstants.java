package com.mahindra.common.constants;

public interface CommonConstants {
	
	//STATUS FLAGS
	String STATUS_JDBC_ERROR = "JDBC_ERROR";
	String STATUS_OK = "OK";
	String STATUS_INSERTION_SUCCESSFULL = "insertSuccessfull";
	String STATUS_INSERTION_FAILURE = "insertFailed";
	String STATUS_DELETE_SUCCESSFULL = "deleteSuccessfull";
	String STATUS_DELETE_FAILURE = "deleteFailed";
	String STATUS_EDIT_SUCCESSFULL = "editSuccessfull";
	String STATUS_EDIT_FAILURE = "editFailed";
	String PWD_MAIL_SEND = "PWD_STATUS_OK";
	String STATUS_EXTENSION="dateExtended";
	//MESSAGE BOXES
	String STATUS_SAVED="Saved successfully.";
	
	//USER_LOGIN_AUTHENTICATION_STATUS FLAGS
	String STATUS_AUTH_SUCCESS = "AUTHENTICATION_SUCCESSFULL";
	String STATUS_AUTH_FAILED = "AUTHENTICATION_FAILED";
	String STATUS_INVALID_EMAIL_ID = "INVALID_EMAIL_ID";
	

	//OPERATION CODES
	String OP_EXTEND_SURVEY_DATE="extendDate";
	String OP_VALIDATE_USER = "validateLogin";
	String OP_GET_USERNAME = "getUserName";
	String OP_LOGOUT = "logout";
	String OP_FORGOT_PWD = "forgotPassword";
	String OP_CHANGE_PWD = "changePassword";
	String OP_INSERT_QUESTION= "insertQuestion";
	String OP_FETCH_QUESTIONS= "fetchQuestions";
	String OP_DELETE_QUESTIONS= "deleteQuestion";
	String OP_EDIT_QUESTIONS= "editQuestion";
	String OP_INSERT_TEMPLATE= "insertTemplate";
	String OP_EDIT_TEMPLATE= "updateTemplate";
	String OP_FETCH_TEMPLATE_DETAILS = "fetchTemplate";
	String OP_GET_ADMIN_LIST = "getAdminList";// GET ADMIN LIST FOR NOMINATION
	String OP_GET_SECTOR_LIST = "getSectorList";// GET SECTOR LIST FOR NOMINATION
	String OP_GET_COMPANY_LIST = "getCompanyList";// GET COMPANY LIST FOR NOMINATION
	String OP_GET_TEMPLATE_LIST = "getTemplateList";// GET ACTIVE TEMPLATE LIST FOR NOMINATION
	String OP_GET_TEMPLATE_LIST_ALL = "getTemplateListAll";// GET ALL TEMPLATE LIST FOR NOMINATION
	String OP_NOMINATE_EMP = "nominateEmployee";
	String OP_FETCH_PENDING_REQUESTS = "fetchPendingRequests";
	String OP_FETCH_RESPONDENTS = "fetchRespondents";
	String OP_INSERT_RESPONDENTS = "insertRespondents";	
	String OP_INSERT_RESPONDENTS_AFTER_SUBMIT = "insertRespondentsAfterSubmit";	
	String OP_INSERT_FEEDBACK_RESPONSE = "feedbackResponse";        //GET RESPONSE FROM USER
    String OP_FETCH_PARTIALLY_SAVED_FEEDBACK = "partiallysavedreponse";
    String OP_FETCH_EMPLOYEES = "fetchEmployees";
    String OP_FETCH_COUNT_OF_EMPLOYEE_RELATION = "countOfEmployeeRelation";
    String OP_GET_CATEGORY_LIST = "fetchCategoryList";
    String OP_GET_SUBCATEGORY = "fetchSubCategoryList";
    String OP_GET_RESPONDENT_STATUS = "fetchrespondentstatus";
    String OP_GET_RESPONDENT_STATUS_FOR_ADMIN = "fetchrespondentstatusforadmin";
    String OP_FETCH_FEEDBACK= "fetchFeedback";
    String OP_FETCH_FEEDBACK_DETAILS_LIST= "fetchFeedbackList";
    String EmployeeMstrList= "employeeMstrList";


	
	
	
	//SESSION VARIABLE
	String SESSION_USERNAME = "username";
	String SESSION_EMAIL_ID = "emailId";
	String SESSION_ROLE = "role";
	String SESSION_SECTOR_ID = "sectorId";
	String SESSION_COMPANY_ID = "companyId";
	String SESSION_SECTOR_NAME = "sectorName";
 	String SESSION_COMPANY_NAME= "companyName";
 	String SESSION_FEEDBACK_ID= "feedbackId";
 	String SESSION_RESPONDENT_ID= "respondentId";
 	String SESSION_PWD_CHANGED_FLAG = "pwdChangedFlag";
 	String SESSION_USERPOJO = "userPojo";

	
	// EMAIL PROPERTIES
	String SMTP_HOST_SERVER_IP = "";
	String SMTP_HOST_SERVER_PORT = "25";
	String SMTP_AUTH_USER_ID = "";
	String SMTP_AUTH_PWD = "";
	String URL = "";
	String SUPER_ADMIN_EMAIL_ID = "";
}
