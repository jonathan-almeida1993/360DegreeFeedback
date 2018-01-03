package com.mahindra.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.utils.CommonUtilities;
import com.mahindra.dao.base.impl.LoginDAOImpl;
import com.mahindra.dao.base.interfaces.LoginDAO;
import com.mahindra.database.pojo.UserPojo;

/*
 * Author: Jonathan Almeida
 */
public class ChangePwdController extends HttpServlet {
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
		LoginDAO dao = null;
		
		System.out.println("\n\nChangePwdController:doPost @@@@@ message :: " + message + " jsonData :: " + jsonData);
		
		if (null != message && CommonConstants.OP_CHANGE_PWD.equalsIgnoreCase(message)) {
			
			HttpSession session = request.getSession(false);
			UserPojo userDetails = new UserPojo();
			userDetails.setEmailId(String.valueOf(session.getAttribute(CommonConstants.SESSION_EMAIL_ID)));
			userDetails.setPassword(request.getParameter("oldPassword"));
			userDetails.setNewpassword(request.getParameter("newpassword")); 
			
			CommonUtilities.SOP("EMAIL_ID - "+userDetails.getEmailId());
			CommonUtilities.SOP("OLD PASSWORD - "+userDetails.getPassword());
			CommonUtilities.SOP("NEW PASSWORD - "+userDetails.getNewpassword());
			CommonUtilities.SOP("VALIDATING USER CREDENTIALS FOR PWD CHANGE...");
			
			dao = new LoginDAOImpl();
			UserPojo status = dao.validateUserLogin(userDetails);
			CommonUtilities.SOP("STATUS - "+status.getStatus());
			
			if(CommonConstants.STATUS_AUTH_SUCCESS.equals(status.getStatus())){
				
				CommonUtilities.SOP("CREDENTIALS FOUND TO BE VALID...NOW CHANGING PASSWORD");
				dao.resetPassword(userDetails.getEmailId(),1,userDetails.getNewpassword());
				response.sendRedirect("changePwd.html?status=PWD_CHANGE_SUCCESSFULL");
				
			}else{
				
				CommonUtilities.SOP("INVALID PASSWORD");
				response.sendRedirect("changePwd.html?status=INVALID_OLD_PWD");
				
			}
		}
		
		System.out.println("LoginController:doPost Exiting...");
	}
}

