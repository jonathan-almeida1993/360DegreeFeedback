package com.mahindra.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.utils.CommonUtilities;
import com.mahindra.dao.base.impl.DashboardInfoDaoImpl;
import com.mahindra.dao.base.impl.FeedbackDAOImpl;
import com.mahindra.dao.base.impl.NominationDAOImpl;
import com.mahindra.dao.base.impl.QuestionMasterDAOImpl;
import com.mahindra.dao.base.interfaces.FeedbackDAO;
import com.mahindra.database.pojo.DashboardPojo;
import com.mahindra.database.pojo.FeedbackPojo;
import com.mahindra.database.pojo.NominationPojo;
import com.mahindra.database.pojo.QuestionPojo;

public class FeedbackController extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String message = null;
		String jsonData = null;
		message = request.getParameter("message");
		jsonData = request.getParameter("JDATA");

		FeedbackDAO feedbackdao = null;
		System.out.println("\n\nFeedbackController:doPost @@@@@ message :: " + message + " jsonData :: " + jsonData);

		if (null != message && CommonConstants.OP_FETCH_QUESTIONS.equalsIgnoreCase(message)) {

			feedbackdao = new FeedbackDAOImpl();
			FeedbackPojo feedbackDetails = null;
			Gson gson = new Gson();
			HttpSession session = request.getSession(false);
			String role = session.getAttribute(CommonConstants.SESSION_ROLE).toString();
			System.out.println("LOGGED IN AS - "+role);
			feedbackDetails = gson.fromJson(jsonData, FeedbackPojo.class);
			
			if(feedbackDetails.getFeedbackid()==0&&feedbackDetails.getRespondentid()==0){
				feedbackDetails.setFeedbackid(Integer.parseInt(session.getAttribute(CommonConstants.SESSION_FEEDBACK_ID).toString()));
				feedbackDetails.setRespondentid(Integer.parseInt(session.getAttribute(CommonConstants.SESSION_RESPONDENT_ID).toString()));
			}

			//GET STATUS FOR THE RESPONDENT
			String status = feedbackdao.getFeedbackStatus(feedbackDetails);										
			
			System.out.println("STATUS FOR RESPONDENT:"+feedbackDetails.getRespondentid()+"-->"+status);
			if(status.equalsIgnoreCase("PENDING")){   
				
				CommonUtilities.SOP("FETCHING QUESTIONS FOR UNSAVED RESPONSES...");
				//FETCH QUESTIONS FOR UNSAVED RESPONSES
				ArrayList<FeedbackPojo> questionList = feedbackdao.selectQuestions(feedbackDetails);
				String jsonDataToServer = gson.toJson(questionList);
				CommonUtilities.SOP("QUESTION LIST-->" + jsonDataToServer);
				response.getWriter().write(jsonDataToServer);
				
			}else if(status.equalsIgnoreCase("INPROGRESS")){	
				
				CommonUtilities.SOP("FETCHING QUESTIONS PARTIALLY SAVED...");
				ArrayList<FeedbackPojo> questionList = feedbackdao.getPartiallySavedQuestionResponse(feedbackDetails);
				String jsonDataToServer = gson.toJson(questionList);
				CommonUtilities.SOP("QUESTION LIST-->" + jsonDataToServer);
				response.getWriter().write(jsonDataToServer);
				
			}else if(status.equalsIgnoreCase("COMPLETED")){	
				
				CommonUtilities.SOP("FETCHING QUESTIONS COMPLETELY SAVED...");
				ArrayList<FeedbackPojo> questionList = feedbackdao.getPartiallySavedQuestionResponse(feedbackDetails);
				String jsonDataToServer = gson.toJson(questionList);
				CommonUtilities.SOP("QUESTION LIST-->" + jsonDataToServer);
				response.getWriter().write(jsonDataToServer);
				
			}
		} else if (null != message && CommonConstants.OP_INSERT_FEEDBACK_RESPONSE.equalsIgnoreCase(message)){

			feedbackdao = new FeedbackDAOImpl();
			Gson gson = new Gson();
			Type temp = new TypeToken<ArrayList<FeedbackPojo>>(){}.getType();
			ArrayList<FeedbackPojo> userResponse = gson.fromJson(jsonData, temp);
			CommonUtilities.SOP("INSERTING QUESTION AND RESPONSE...");
			HttpSession session = request.getSession(false);
			String status = feedbackdao.operationType(userResponse,request); 
			CommonUtilities.SOP("STATUS-->" + status);
			response.getWriter().write(status);
		
		}
		else if (null != message && CommonConstants.OP_FETCH_EMPLOYEES.equalsIgnoreCase(message)){

			feedbackdao = new FeedbackDAOImpl();
			Gson gson = new Gson();
			FeedbackPojo respondentDetails = new FeedbackPojo();
			HttpSession session = request.getSession(false);
			respondentDetails.setEmailid(session.getAttribute(CommonConstants.SESSION_EMAIL_ID).toString());
			CommonUtilities.SOP("FETCHING EMPLOYEES...");
			ArrayList<FeedbackPojo> employeeList = feedbackdao.selectEmployees(respondentDetails); 
			String jsonDataToServer = gson.toJson(employeeList);
			CommonUtilities.SOP("EMPLOYEE_LIST-->" + jsonDataToServer);
			response.getWriter().write(jsonDataToServer);
		
		}
		else if (null != message && CommonConstants.OP_FETCH_COUNT_OF_EMPLOYEE_RELATION.equalsIgnoreCase(message)){
			
			feedbackdao = new FeedbackDAOImpl();
			Gson gson = new Gson();
			FeedbackPojo respondentDetails = new FeedbackPojo();
			HttpSession session = request.getSession(false);
			respondentDetails.setEmailid(session.getAttribute(CommonConstants.SESSION_EMAIL_ID).toString());
			CommonUtilities.SOP("FETCHING EMPLOYEES COUNT...");
			ArrayList<FeedbackPojo> countlist = feedbackdao.countOfRespondents(respondentDetails); 
			String jsonDataToServer = gson.toJson(countlist);
			CommonUtilities.SOP("EMPLOYEE_LIST-->" + jsonDataToServer);
			response.getWriter().write(jsonDataToServer);	
	
		}else if (null != message && message.equals("closeSurvey")){
			
			HttpSession session = request.getSession(false);
			String role=session.getAttribute(CommonConstants.SESSION_ROLE).toString();
			feedbackdao = new FeedbackDAOImpl();
			Gson gson = new Gson();
			FeedbackPojo closureDet = new FeedbackPojo();
			closureDet=gson.fromJson(jsonData, FeedbackPojo.class);
			String status=feedbackdao.feedbackClosure(closureDet,role);
			response.getWriter().write(status);
			
		}else if (null != message && message.equals("forceClosure")){
			
			HttpSession session = request.getSession(false);
			String role=session.getAttribute(CommonConstants.SESSION_ROLE).toString();
			feedbackdao = new FeedbackDAOImpl();
			Gson gson = new Gson();
			FeedbackPojo closureDet = new FeedbackPojo();
			closureDet=gson.fromJson(jsonData, FeedbackPojo.class);
			String status=feedbackdao.feedbackForceClosure(closureDet, role);
			response.getWriter().write(status);
			
		}else if (null != message && message.equals("reOpenSurvey")){
			
			HttpSession session = request.getSession(false);
			String role=session.getAttribute(CommonConstants.SESSION_ROLE).toString();
			feedbackdao = new FeedbackDAOImpl();
			Gson gson = new Gson();
			FeedbackPojo closureDet = new FeedbackPojo();
			closureDet=gson.fromJson(jsonData, FeedbackPojo.class);
			String status=feedbackdao.reOpen(closureDet,role);
			response.getWriter().write(status);
			
		}else if(null != message && CommonConstants.OP_EXTEND_SURVEY_DATE.equalsIgnoreCase(message)){
			
			Gson gson = new Gson();
			NominationDAOImpl nomDao = new NominationDAOImpl();
			FeedbackPojo extensionRequest = gson.fromJson(jsonData, FeedbackPojo.class);
			CommonUtilities.SOP("UPDATING RECORD IN NOMINATION REQUEST...");
			String status = nomDao.extendSurvey(extensionRequest);
			CommonUtilities.SOP("STATUS-->"+status);
			response.getWriter().write(status);

		}else if(null != message && CommonConstants.OP_FETCH_FEEDBACK.equalsIgnoreCase(message)){
			
			Gson gson = new Gson();
			FeedbackDAOImpl feedbackDao= new FeedbackDAOImpl();
			FeedbackPojo extensionRequest = gson.fromJson(jsonData, FeedbackPojo.class);
			CommonUtilities.SOP("FETCHING FEEDBACK FOR EXTENSION...");
			NominationPojo nomineeDet= feedbackDao.getFeedbackForExtension(extensionRequest);
			String jsonDataToServer = gson.toJson(nomineeDet);
			response.getWriter().write(jsonDataToServer);
			
		}else if(null != message && CommonConstants.OP_FETCH_FEEDBACK.equalsIgnoreCase(message)){
			
			Gson gson = new Gson();
			FeedbackDAOImpl feedbackDao= new FeedbackDAOImpl();
			FeedbackPojo extensionRequest = gson.fromJson(jsonData, FeedbackPojo.class);
			CommonUtilities.SOP("FETCHING FEEDBACK FOR EXTENSION...");
			NominationPojo nomineeDet= feedbackDao.getFeedbackForExtension(extensionRequest);
			String jsonDataToServer = gson.toJson(nomineeDet);
			response.getWriter().write(jsonDataToServer);
			
		}else  if(null != message && message.equalsIgnoreCase("fetchFeedbackList")){
			
			Gson gson = new Gson();
			DashboardInfoDaoImpl feedbackDash= new DashboardInfoDaoImpl();
        	ArrayList<DashboardPojo> feedbackList= feedbackDash.feedbackDashList(request);
        	String jsonDataToServer = gson.toJson(feedbackList);
        	response.getWriter().write(jsonDataToServer);
        }
		
		
		System.out.println("FeedbackController:doPost Exiting...");
	}  //doPost ends

}
