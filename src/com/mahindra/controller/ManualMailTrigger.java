package com.mahindra.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mahindra.common.DailyPendingMainJob;
import com.mahindra.common.utils.MailShooter;

/**
 * Author: Jonathan Almeida
 */
public class ManualMailTrigger extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
/*		MailShooter manualMailTrigger = new MailShooter(26);
		Thread t = new Thread(manualMailTrigger);
		t.start();
		
*/		/*System.out.println("testing...");
		DailyPendingMainJob manualMail = new DailyPendingMainJob();	
		manualMail.start();
		System.out.println("testing...");*/
	}

}
