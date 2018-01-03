package com.mahindra.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.utils.CommonUtilities;
import com.mahindra.common.utils.EmailUtil;
import com.mahindra.dao.base.impl.LoginDAOImpl;
import com.mahindra.dao.base.impl.RespondentDAOImpl;
import com.mahindra.dao.base.interfaces.LoginDAO;
import com.mahindra.dao.base.interfaces.RespondentDAO;
import com.mahindra.database.pojo.NominationPojo;
import com.mahindra.database.pojo.RespondentPojo;
import com.mahindra.database.pojo.UserPojo;

/*
 * Author: Jonathan Almeida
 */

public class LoginController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String message = null;
		String jsonData = null;
		message = request.getParameter("message");
		jsonData = request.getParameter("JDATA");
		LoginDAO dao = null;
		System.out.println("\n\nLoginController:doPost @@@@@ message :: " + message + " jsonData :: " + jsonData);
		
		if (null != message && CommonConstants.OP_VALIDATE_USER.equalsIgnoreCase(message)) {
			
			UserPojo loginDetails = new UserPojo();
			dao = new LoginDAOImpl();
			loginDetails.setEmailId(request.getParameter("emailId"));
			loginDetails.setPassword(request.getParameter("password"));
			CommonUtilities.SOP("EMAIL_ID - "+loginDetails.getEmailId());
			CommonUtilities.SOP("PASSWORD - "+loginDetails.getPassword());
			CommonUtilities.SOP("VALIDATING USER LOGIN...");
			UserPojo userDetails = dao.validateUserLogin(loginDetails);
			
			CommonUtilities.SOP("STATUS - "+userDetails.getStatus());
			CommonUtilities.SOP("FIRSTNAME - "+userDetails.getFirstName());
			CommonUtilities.SOP("LASTNAME - "+userDetails.getLastName());
			CommonUtilities.SOP("EMAIL ID - "+userDetails.getEmailId());
			CommonUtilities.SOP("PWD CHANGED FLAG - "+userDetails.isPwdChanged());
			CommonUtilities.SOP("ROLE - "+userDetails.getRole());
			CommonUtilities.SOP("SECTOR_ID - "+userDetails.getSectorId());
			CommonUtilities.SOP("COMPANY_ID - "+userDetails.getCompanyId());
			CommonUtilities.SOP("SECTOR_NAME - "+userDetails.getSectorName());
			CommonUtilities.SOP("COMPANY_NAME - "+userDetails.getCompanyName());
			
			if(CommonConstants.STATUS_AUTH_SUCCESS.equals(userDetails.getStatus())){
				
				createSession(request, userDetails);//Check session and add authentication filter
				
				if("EMP".equals(userDetails.getRole())){
					//add feedbackId to session --temporary code
					RespondentDAO respondentDao = new RespondentDAOImpl();
					RespondentPojo feedbackId = respondentDao.getActiveFeedbackId(userDetails.getEmailId());
					
					if(!CommonConstants.STATUS_OK.equals(feedbackId.getStatus())){
						response.sendRedirect("login.html?status="+feedbackId.getStatus());
					}else{
						
						System.out.println("FeedbackId for "+userDetails.getEmailId()+" = "+feedbackId.getFeedbackId());
						HttpSession session = request.getSession(false);
						session.setAttribute(CommonConstants.SESSION_FEEDBACK_ID, feedbackId.getFeedbackId());
						
						if(feedbackId.getFeedbackId()==0){
							session.setAttribute(CommonConstants.SESSION_ROLE, "RESPONDENT");
							CommonUtilities.SOP("ROLE - RESPONDENT");
						}else{
							RespondentPojo respondentId = respondentDao.getNominatedEmpRespondentId(feedbackId.getFeedbackId(),userDetails.getEmailId());
							CommonUtilities.SOP("RespondentId for "+userDetails.getEmailId()+" = "+respondentId.getRespondentId());
							session.setAttribute(CommonConstants.SESSION_RESPONDENT_ID, respondentId.getRespondentId());
						}
						
						response.sendRedirect("user.html");
					}
					
				}else{
					response.sendRedirect("index.html");
				}
			}else{
				response.sendRedirect("login.html?status="+userDetails.getStatus());
			}
		}else if(null != message && CommonConstants.OP_GET_USERNAME.equalsIgnoreCase(message)){
			
			HttpSession session = request.getSession(false);
			String userName = String.valueOf(session.getAttribute(CommonConstants.SESSION_USERNAME));
			response.getWriter().write(userName);
			
		}else if(null != message && CommonConstants.OP_FORGOT_PWD.equalsIgnoreCase(message)){
			
			dao = new LoginDAOImpl();
			CommonUtilities.SOP("CHANGE PASSWORD REQUEST FOR - "+jsonData);
			UserPojo userDetails = dao.changePasswordRequest(jsonData); 
			CommonUtilities.SOP("STATUS - "+userDetails.getStatus());
			
			if(userDetails.getStatus().equals("OK")){				
				try {
					
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					String clearTextPwd = (String.valueOf(UUID.randomUUID())).substring(0, 8);
					System.out.println("NEW CEAR TEXT PWD - "+clearTextPwd);
					md.update(clearTextPwd.getBytes());
					byte byteData[] = md.digest();
					//convert the byte to hex format method 1
					StringBuffer encryptedPwd = new StringBuffer();
					
					for (int i = 0; i < byteData.length; i++) {
						encryptedPwd.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
					}
					
					System.out.println("Encrypted Password - "+encryptedPwd);
					dao.resetPassword(jsonData, 0,encryptedPwd.toString());
					userDetails.setPassword(clearTextPwd);
					
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.getWriter().write("ERROR OCURRED");
					return;
				}
				
				EmailUtil.triggerEmail(jsonData, userDetails);
				CommonUtilities.SOP("MAIL SENT...");
				response.getWriter().write(CommonConstants.PWD_MAIL_SEND);
				
			}else{
				response.getWriter().write(userDetails.getStatus());
			}
		}
		
		System.out.println("LoginController:doPost Exiting...");
	}

	private void createSession(HttpServletRequest request, UserPojo userDetails) {
		
		HttpSession session = request.getSession();
		session.setAttribute(CommonConstants.SESSION_USERNAME, userDetails.getFirstName()+" "+userDetails.getLastName());
		session.setAttribute(CommonConstants.SESSION_EMAIL_ID, userDetails.getEmailId());
		session.setAttribute(CommonConstants.SESSION_ROLE, userDetails.getRole());
		session.setAttribute(CommonConstants.SESSION_SECTOR_ID, userDetails.getSectorId());
		session.setAttribute(CommonConstants.SESSION_COMPANY_ID, userDetails.getCompanyId());
		session.setAttribute(CommonConstants.SESSION_PWD_CHANGED_FLAG, userDetails.isPwdChanged());
		session.setAttribute(CommonConstants.SESSION_SECTOR_NAME, userDetails.getSectorName());
		session.setAttribute(CommonConstants.SESSION_COMPANY_NAME, userDetails.getCompanyName());
		session.setAttribute(CommonConstants.SESSION_USERPOJO, userDetails);
		
		String sessionId=session.getId();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if(ipAddress==null)
            ipAddress = request.getRemoteAddr();
        CommonUtilities.sessionValue.put(sessionId, ipAddress);
		session.setMaxInactiveInterval(60*61);// 1 hrs
		//if role is 'EMP' then also store FeedbackId into Session.
	}
}
