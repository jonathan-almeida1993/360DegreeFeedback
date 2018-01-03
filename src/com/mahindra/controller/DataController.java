package com.mahindra.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.utils.CommonUtilities;
import com.mahindra.common.utils.EmailUtil;
import com.mahindra.common.utils.MailShooter;
import com.mahindra.dao.base.impl.FeedbackDAOImpl;
import com.mahindra.dao.base.impl.NominationDAOImpl;
import com.mahindra.dao.base.impl.QuestionMasterDAOImpl;
import com.mahindra.dao.base.impl.TemplateMasterDAOImpl;
import com.mahindra.dao.base.interfaces.FeedbackDAO;
import com.mahindra.dao.base.interfaces.NominationDAO;
import com.mahindra.dao.base.interfaces.QuestionMasterDAO;
import com.mahindra.dao.base.interfaces.TemplateMasterDAO;
import com.mahindra.database.pojo.CompanyPojo;
import com.mahindra.database.pojo.FeedbackPojo;
import com.mahindra.database.pojo.NominationPojo;
import com.mahindra.database.pojo.QuestionPojo;
import com.mahindra.database.pojo.SectorPojo;
import com.mahindra.database.pojo.TemplatePojo;
import com.mahindra.database.pojo.UserPojo;

public class DataController extends HttpServlet {


	private static final long serialVersionUID = 5110437350830085024L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("\n\nChecking Session...");
		HttpSession session = request.getSession();
		System.out.println("Test if session exists - "+session);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String message = null;
		String jsonData = null;
		message = request.getParameter("message");
		jsonData = request.getParameter("JDATA");
		QuestionMasterDAO questDao = null;
		TemplateMasterDAO templateDao = null;
		NominationDAO nomDao = null;
		
		
		System.out.println("\n\nDataController:doPost @@@@@ message :: " + message + " jsonData :: " + jsonData);
		
		if (null != message && CommonConstants.OP_GET_USERNAME.equalsIgnoreCase(message)) {
		
			HttpSession session = request.getSession(false);
			String userName = String.valueOf(session.getAttribute(CommonConstants.SESSION_USERNAME));
			String role = String.valueOf(session.getAttribute(CommonConstants.SESSION_ROLE));
			boolean isPwdChanged = (Boolean) session.getAttribute(CommonConstants.SESSION_PWD_CHANGED_FLAG);
			
			//Changes to fetch start date and end date
			FeedbackDAO feedbackDao = new FeedbackDAOImpl();
			FeedbackPojo surveyDates = null;
			if(("EMP").equals(role)){
				surveyDates = feedbackDao.getSurveyStartEndDates(Integer.parseInt(session.getAttribute(CommonConstants.SESSION_FEEDBACK_ID).toString()));
			}
			
			String emailId = String.valueOf(session.getAttribute(CommonConstants.SESSION_EMAIL_ID));
			String userDataJson ="";
			
			if(surveyDates==null){
				userDataJson = "{\"userName\":\"" + userName + "\",\"isPwdChanged\":\""+isPwdChanged+"\",\"role\":\"" + role + "\",\"emailId\":\""+emailId+"\"}";
			}else{
				userDataJson = "{\"userName\":\"" + userName + "\",\"isPwdChanged\":\""+isPwdChanged+"\",\"role\":\"" + role + "\",\"emailId\":\""+emailId+"\",\"startDate\":\""+surveyDates.getStartDate()+
						"\",\"endDate\":\""+surveyDates.getEndDate()+"\"}";
			}
			CommonUtilities.SOP("USER DATA FROM SESSION - " + userDataJson);
			
			//Changes to fetch start date and end date	
			response.getWriter().write(userDataJson);

		} else if (null != message && CommonConstants.OP_LOGOUT.equalsIgnoreCase(message)) {
			
			request.getSession().invalidate();
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			rd.forward(request, response);
			CommonUtilities.SOP("SIGNING OUT...");

		} else if (null != message && CommonConstants.OP_INSERT_QUESTION.equalsIgnoreCase(message)) { //QUESTION MASTER STARTS

			questDao = new QuestionMasterDAOImpl();
			Gson gson = new Gson();
			QuestionPojo newQuestion = gson.fromJson(jsonData, QuestionPojo.class);
			CommonUtilities.SOP("INSERTING QUESTION...");
			String status = questDao.insertQuestions(newQuestion);
			CommonUtilities.SOP("STATUS-->" + status);
			response.getWriter().write(status);

		} else if (null != message && CommonConstants.OP_FETCH_QUESTIONS.equalsIgnoreCase(message)) {

			questDao = new QuestionMasterDAOImpl();
			QuestionPojo fetchQuestions = null;
			TemplatePojo templateId=null;
			
			Gson gson = new Gson();
			
			if (!"".equals(jsonData)){
				fetchQuestions = gson.fromJson(jsonData, QuestionPojo.class);
				templateId = gson.fromJson(jsonData, TemplatePojo.class);
			}
			
			CommonUtilities.SOP("FETCHING QUESTIONS...");
			ArrayList<QuestionPojo> questionList = questDao.selectQuestions(fetchQuestions,templateId);
			String jsonDataToServer = gson.toJson(questionList);
			CommonUtilities.SOP("QUESTION LIST-->" + jsonDataToServer);
			response.getWriter().write(jsonDataToServer);

		} else if (null != message && CommonConstants.OP_DELETE_QUESTIONS.equalsIgnoreCase(message)) {

			questDao = new QuestionMasterDAOImpl();
			Gson gson = new Gson();
			QuestionPojo questionId = gson.fromJson(jsonData, QuestionPojo.class);
			CommonUtilities.SOP("DELETING QUESTIONS...");
			String status = questDao.deleteQuestions(questionId);
			CommonUtilities.SOP("STATUS-->" + status);
			response.getWriter().write(status);

		} else if (null != message && CommonConstants.OP_EDIT_QUESTIONS.equalsIgnoreCase(message)) {

			questDao = new QuestionMasterDAOImpl();
			Gson gson = new Gson();
			QuestionPojo questionId = gson.fromJson(jsonData, QuestionPojo.class);
			CommonUtilities.SOP("EDITING QUESTIONS...");
			String status = questDao.updateQuestions(questionId);
			CommonUtilities.SOP("STATUS-->" + status);
			response.getWriter().write(status);                               						//QUESTION MASTER ENDS

		} else if (null != message && CommonConstants.OP_GET_SECTOR_LIST.equalsIgnoreCase(message)) {//EMP NOMINATION STARTS

			nomDao = new NominationDAOImpl();
			CommonUtilities.SOP("FETCHING SECTOR LIST...");
			ArrayList<SectorPojo> sectorList = nomDao.getSectorList();
			Gson gson = new Gson();
			String retStr = gson.toJson(sectorList);
			response.getWriter().write(retStr);
			System.out.println("RETURN STRING-->" + retStr);
			
		} else if (null != message && CommonConstants.OP_GET_COMPANY_LIST.equalsIgnoreCase(message)) {

			nomDao = new NominationDAOImpl();
			CommonUtilities.SOP("FETCHING COMPANY LIST AGAINST SELECTED SECTOR...");
			ArrayList<CompanyPojo> compList = null;
			
			if(jsonData!=null&&"SEA".equals(jsonData)){
				HttpSession session = request.getSession(false);
				String sectorId = String.valueOf(session.getAttribute(CommonConstants.SESSION_SECTOR_ID));
				compList = nomDao.getCompanyList(sectorId);
			}else{
				compList = nomDao.getCompanyList(jsonData);
			}
			
			Gson gson = new Gson();
			String retStr = gson.toJson(compList);
			response.getWriter().write(retStr);
			System.out.println("RETURN STRING-->" + retStr);										
			
		}else if(null != message && CommonConstants.OP_NOMINATE_EMP.equalsIgnoreCase(message)){
			
			HttpSession session = request.getSession(false);
			nomDao = new NominationDAOImpl();
			Gson gson = new Gson();
			NominationPojo newNominationRequest = gson.fromJson(jsonData, NominationPojo.class);
			newNominationRequest.setSource(session.getAttribute(CommonConstants.SESSION_EMAIL_ID).toString());
			
			//Ankush
			String role=session.getAttribute(CommonConstants.SESSION_ROLE).toString();
			int sectorId=Integer.parseInt(session.getAttribute(CommonConstants.SESSION_SECTOR_ID).toString());
			int companyId=Integer.parseInt(session.getAttribute(CommonConstants.SESSION_COMPANY_ID).toString());
			
			if(role.equals("SEA"))
			{
				newNominationRequest.setSectorId(sectorId);
			}
			else if(role.equals("COA")){
				newNominationRequest.setSectorId(sectorId);
				newNominationRequest.setCompanyId(companyId);
			}
			//Ankush
			
			CommonUtilities.SOP("INSERTING RECORDS FOR NEW NOMINATION REQUEST...");
			UserPojo statusPwd = nomDao.nominateEmployee(newNominationRequest);
			String status = statusPwd.getStatus();
			
			if(CommonConstants.STATUS_SAVED.equals(status)){
				newNominationRequest.setPassword(statusPwd.getPassword());
				MailShooter mailShooter = new MailShooter(newNominationRequest);
			    Thread t = new Thread(mailShooter);
			    t.start(); 
			}
			
			CommonUtilities.SOP("STATUS-->"+status);
			CommonUtilities.SOP("CLEAR TEXT PASSWORD-->"+statusPwd.getPassword());
			response.getWriter().write(status);

		}else if(null != message && CommonConstants.OP_FETCH_PENDING_REQUESTS.equalsIgnoreCase(message)){
			
			nomDao = new NominationDAOImpl();
			Gson gson = new Gson();
			UserPojo loggedInUserDetails = new UserPojo();
			HttpSession session = request.getSession(false);
			loggedInUserDetails.setEmailId(session.getAttribute(CommonConstants.SESSION_EMAIL_ID).toString());
			loggedInUserDetails.setRole(session.getAttribute(CommonConstants.SESSION_ROLE).toString());
			CommonUtilities.SOP("FETCHING PENDING REQUESTS...");
			ArrayList<NominationPojo> pendingRequests = nomDao.fetchPendingRequest(loggedInUserDetails);
			String retStr = gson.toJson(pendingRequests);
			response.getWriter().write(retStr);
			System.out.println("RETURN STRING-->" + retStr);
																										//EMP NOMINATION ENDS
		}else if (null != message && CommonConstants.OP_GET_TEMPLATE_LIST.equalsIgnoreCase(message)) {

			nomDao = new NominationDAOImpl();
			CommonUtilities.SOP("FETCHING TEMPLATE LIST...");
			ArrayList<TemplatePojo> templateList = nomDao.getTemplateList();
			System.out.println("-========================================");
			System.out.println(templateList);
			Gson gson = new Gson();
			String retStr = gson.toJson(templateList);
			response.getWriter().write(retStr);
			System.out.println("RETURN STRING-->" + retStr);
			
		}else if (null != message && CommonConstants.OP_GET_TEMPLATE_LIST_ALL.equalsIgnoreCase(message)) {

			nomDao = new NominationDAOImpl();
			CommonUtilities.SOP("FETCHING TEMPLATE LIST...");
			ArrayList<TemplatePojo> templateList = nomDao.getTemplateListAll();
			System.out.println("-========================================");
			System.out.println(templateList);
			Gson gson = new Gson();
			String retStr = gson.toJson(templateList);
			response.getWriter().write(retStr);
			System.out.println("RETURN STRING-->" + retStr);
			
		}else if(null != message && CommonConstants.OP_INSERT_TEMPLATE.equalsIgnoreCase(message)){//TEMPLATE MASTER STARTS
			
			templateDao = new TemplateMasterDAOImpl();
			Gson gson = new Gson();
			TemplatePojo newTemplate = gson.fromJson(jsonData, TemplatePojo.class);
			CommonUtilities.SOP("INSERTING TEMPLATE...");
			String status = templateDao.insertTemplate(newTemplate);
			CommonUtilities.SOP("STATUS-->"+status);
			response.getWriter().write(status);
			
		}else if(null != message && CommonConstants.OP_EDIT_TEMPLATE.equalsIgnoreCase(message)){//TEMPLATE MASTER STARTS
			
			templateDao = new TemplateMasterDAOImpl();
			Gson gson = new Gson();
			TemplatePojo newTemplate = gson.fromJson(jsonData, TemplatePojo.class);
			CommonUtilities.SOP("UPDATING TEMPLATE...");
			String status = templateDao.updateTemplate(newTemplate);
			CommonUtilities.SOP("STATUS-->"+status);
			response.getWriter().write(status);
			
		}else if(null != message && CommonConstants.OP_FETCH_TEMPLATE_DETAILS.equalsIgnoreCase(message)){
			
			templateDao = new TemplateMasterDAOImpl();
			Gson gson = new Gson();
			TemplatePojo templateId = gson.fromJson(jsonData, TemplatePojo.class);
			CommonUtilities.SOP("FETCHING TEMPLATE DETAILS...");
			TemplatePojo templateDetails = templateDao.fetchTemplateDetails(templateId);
			String temp = gson.toJson(templateDetails);
			CommonUtilities.SOP("TEMPLATE DETAILS-->"+temp);
			response.getWriter().write(temp);
			
		}else if (null != message && CommonConstants.OP_GET_CATEGORY_LIST.equalsIgnoreCase(message)) {

			questDao = new QuestionMasterDAOImpl();
			Gson gson = new Gson();
			CommonUtilities.SOP("FETCHING CATEGORY LIST...");
			ArrayList<QuestionPojo> categoryList = questDao.getCategoryList();
			String jsonDataToServer = gson.toJson(categoryList);
			CommonUtilities.SOP("CATEGORY_LIST-->" + jsonDataToServer);
			response.getWriter().write(jsonDataToServer);
		
		}else if (null != message && CommonConstants.OP_GET_SUBCATEGORY.equalsIgnoreCase(message)) {

			questDao = new QuestionMasterDAOImpl();
			Gson gson = new Gson();
			QuestionPojo questcategory = gson.fromJson(jsonData, QuestionPojo.class);
			CommonUtilities.SOP("FETCHING SUBCATEGORY LIST...");
			ArrayList<QuestionPojo> subcategoryList = questDao.getSubCategoryList(questcategory);
			String jsonDataToServer = gson.toJson(subcategoryList);
			CommonUtilities.SOP("SUBCATEGORY_LIST-->" + jsonDataToServer);
			response.getWriter().write(jsonDataToServer);
		
		}
		System.out.println("DataController:doPost Exiting...");
	}
}
