package com.mahindra.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.utils.CommonUtilities;
import com.mahindra.dao.base.impl.QuestionMasterDAOImpl;
import com.mahindra.dao.base.impl.RespondentDAOImpl;
import com.mahindra.dao.base.interfaces.NominationDAO;
import com.mahindra.dao.base.interfaces.QuestionMasterDAO;
import com.mahindra.dao.base.interfaces.RespondentDAO;
import com.mahindra.dao.base.interfaces.TemplateMasterDAO;
import com.mahindra.database.pojo.QuestionPojo;
import com.mahindra.database.pojo.RespondentPojo;

/*
 * Author: Jonathan Almeida
 */
public class RespondentController extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	
    	String message = null;
		String jsonData = null;
		message = request.getParameter("message");
		jsonData = request.getParameter("JDATA");
		
		
		System.out.println("\n\nRespondentController:doPost @@@@@ message :: " + message + " jsonData :: " + jsonData);
		
		if(null != message && CommonConstants.OP_INSERT_RESPONDENTS.equalsIgnoreCase(message)){

			Gson gson = new Gson();
			RespondentDAO respDao = new RespondentDAOImpl();
			Type temp = new TypeToken<ArrayList<RespondentPojo>>(){}.getType();
			ArrayList<RespondentPojo> respondentList = gson.fromJson(jsonData, temp);
			String isSubmitFlag = request.getParameter("isSubmit");
			CommonUtilities.SOP("SAVING RESPONDENTS...");
			HttpSession session = request.getSession(false);
			int feedbackId = Integer.parseInt(session.getAttribute(CommonConstants.SESSION_FEEDBACK_ID).toString());

			RespondentPojo status = respDao.callNominateRespondent(respondentList,feedbackId,isSubmitFlag);
			CommonUtilities.SOP("STATUS-->" + status.getStatus());
			response.getWriter().write(status.getStatus());
			
		}else if(null != message && CommonConstants.OP_INSERT_RESPONDENTS_AFTER_SUBMIT.equalsIgnoreCase(message)){

			Gson gson = new Gson();
			RespondentDAO respDao = new RespondentDAOImpl();
			Type temp = new TypeToken<ArrayList<RespondentPojo>>(){}.getType();
			ArrayList<RespondentPojo> respondentList = gson.fromJson(jsonData, temp);
			CommonUtilities.SOP("SAVING RESPONDENTS AFTER SUBMIT...");
			HttpSession session = request.getSession(false);
			int feedbackId = Integer.parseInt(session.getAttribute(CommonConstants.SESSION_FEEDBACK_ID).toString());

			RespondentPojo status = respDao.callNominateRespondentAfterSubmit(respondentList,feedbackId);
			CommonUtilities.SOP("STATUS-->" + status.getStatus());
			response.getWriter().write(status.getStatus());
		}else if (null != message && CommonConstants.OP_GET_RESPONDENT_STATUS.equalsIgnoreCase(message)){
			
			RespondentDAO respDao = new RespondentDAOImpl();
			Gson gson = new Gson();
			RespondentPojo respondentDetails = new RespondentPojo();
			HttpSession session = request.getSession(false);
			CommonUtilities.SOP("FETCHING RESPONDENT STATUS...");
			int feedbackId = Integer.parseInt(session.getAttribute(CommonConstants.SESSION_FEEDBACK_ID).toString());
			ArrayList<RespondentPojo> countlist = respDao.getrespondentstatus(feedbackId); 
			String jsonDataToServer = gson.toJson(countlist);
			CommonUtilities.SOP("RESPONDENT STATUS LIST-->" + jsonDataToServer);
			response.getWriter().write(jsonDataToServer);	
	
		}else if (null != message && CommonConstants.OP_GET_RESPONDENT_STATUS_FOR_ADMIN.equalsIgnoreCase(message)){
			
			RespondentDAO respDao = new RespondentDAOImpl();
			Gson gson = new Gson();
			RespondentPojo feedbackId = gson.fromJson(jsonData, RespondentPojo.class);
			CommonUtilities.SOP("FETCHING RESPONDENT STATUS FOR ADMIN...");
			ArrayList<RespondentPojo> countlist = respDao.getrespondentstatus(feedbackId.getFeedbackId()); 
			String jsonDataToServer = gson.toJson(countlist);
			CommonUtilities.SOP("RESPONDENT STATUS LIST-->" + jsonDataToServer);
			response.getWriter().write(jsonDataToServer);	
	
		}else if(null != message && CommonConstants.OP_FETCH_RESPONDENTS.equalsIgnoreCase(message)){

			Gson gson = new Gson();
			RespondentDAO respDao = new RespondentDAOImpl();
			CommonUtilities.SOP("FETCHING RESPONDENTS...");
			HttpSession session = request.getSession(false);
			int feedbackId = Integer.parseInt(session.getAttribute(CommonConstants.SESSION_FEEDBACK_ID).toString());
			
			//check status of feedback
			String feedbackStatus = respDao.getFeedbackStatus(feedbackId);
			System.out.println("Feedback status - "+feedbackStatus);
			String respondentList = "";
			
			if("PENDING_WITH_EMP".equalsIgnoreCase(feedbackStatus)){
				String nominatedEmployeeEmailId = session.getAttribute(CommonConstants.SESSION_EMAIL_ID).toString();
				System.out.println("GETTING RESPONDENT SUGGESSTIONS...");
				respondentList = gson.toJson(respDao.getRespondentSuggestions(nominatedEmployeeEmailId,feedbackId,feedbackStatus));
			}else{
				respondentList = gson.toJson(respDao.getRespondents(feedbackId));
			}
			CommonUtilities.SOP("RespondentList-->"+respondentList);
			response.getWriter().write(respondentList);
		}else if(null != message && CommonConstants.EmployeeMstrList.equalsIgnoreCase(message)){
			
			RespondentDAOImpl resDaoImpl= new RespondentDAOImpl();
			Gson gson = new Gson();
			String EmployeeMstrJson=gson.toJson(resDaoImpl.getEmployeeList(jsonData));
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(EmployeeMstrJson);
			System.out.println(EmployeeMstrJson);
			
		}

    	
	}

}