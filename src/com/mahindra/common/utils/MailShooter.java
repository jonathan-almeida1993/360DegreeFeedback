package com.mahindra.common.utils;

import java.util.ArrayList; 
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mahindra.common.constants.CommonConstants;
import com.mahindra.dao.base.impl.NominationDAOImpl;
import com.mahindra.dao.base.impl.RespondentDAOImpl;
import com.mahindra.dao.base.interfaces.NominationDAO;
import com.mahindra.dao.base.interfaces.RespondentDAO;
import com.mahindra.database.pojo.NominationPojo;
import com.mahindra.database.pojo.RespondentPojo;
import com.mahindra.database.pojo.UserPojo;

/*
 * Author: Jonathan Almeida
 */
public class MailShooter implements Runnable {

	NominationPojo emailDetails = null;
	int feedbackId = 0;
	String username;
	String password;
	String email;
	String firstName;
	String mailType;
	String firstName1;
	HashMap<String, String> credentialList;

	public MailShooter(NominationPojo emailDetails) {
		this.emailDetails = emailDetails;
	}

	public MailShooter(int feedbackId, HashMap<String, String> credentialList) {
		this.feedbackId = feedbackId;
		this.credentialList = credentialList;
	}

	public MailShooter(Map<String, String> map) {
		username = map.get("username");
		password = map.get("password");
		email = map.get("email");
		firstName = map.get("firstName");
		mailType = map.get("status");
		firstName1 = map.get("firstName1");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		Properties props = System.getProperties();

		props.put("mail.smtp.host", CommonConstants.SMTP_HOST_SERVER_IP);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props, new Authenticator() {
			
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CommonConstants.SMTP_AUTH_USER_ID, CommonConstants.SMTP_AUTH_PWD);
            }
            
		});
		
		if (feedbackId == 0 && emailDetails != null) {
			mailToNominatedEmp(session);
		} else if (emailDetails == null && feedbackId != 0) {
			mailToRespondents(session);
		} else if (mailType == "E") {
			mailToDefaulterEmployee(session);
		} else if (mailType == "ES") {
			mailToDefaulterEmployeeSelf(session);
		} else if (mailType == "ENS") {
			mailToDefaulterEmployeeNotSelf(session);
		}

	}

	private void mailToNominatedEmp(Session session) {
		String from = "";
		String to = "";
		String subject = "";
		String content = "";
		NominationDAO dao = new NominationDAOImpl();
		if ("EMP".equals(emailDetails.getDestinationDesc())) {
			
			// fetch id and password,source role,source name and last name
			System.out.println("FETCHING EMP CREDENTIALS FOR SENDING MAIL...");
			UserPojo empCredentials = dao.fetchDetailsForEmp(emailDetails);
			to = emailDetails.getDestination();
			from = CommonConstants.SUPER_ADMIN_EMAIL_ID;
			subject = "Rise 360 degree feedback survey";
			
			content = "<p>Dear "
					+ emailDetails.getFirstName()
					+ " "
					+ emailDetails.getLastName()
					+ ",</p>"
					+ "<p>Welcome to the Rise 360 Degree Feedback!</p>"
					+ "<p>This feedback process will help you gain insights in to how others see you living the three pillars of RISE and the characteristics of a Mahindra leader. </p>"
					+ "<p>The report generated as part of the 360 degree feedback will help you to have meaningful reflective conversations with the participants and help you to make your own development plan. </p> "
					+ "<p>In order to complete survey, would request you to follow the steps given below: </p>"
					+ "<p>1. Click on the link: "+CommonConstants.URL+"</p>"
					+ "<p>2. Login using the following credentials:</p>"
					+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;Username: "+ emailDetails.getDestination();
					
					if(!(emailDetails.getPassword()==null||emailDetails.getPassword().equalsIgnoreCase("NULL"))){
						content = content + "&nbsp;&nbsp;&nbsp;&nbsp;Password: "+emailDetails.getPassword()+"</p>";
					}else{
						content = content + "</p>";
					}
					
					content = content 
					+ "<p>3. Click on the <b>“Enroll – Select Your Observers”</b> link, as seen on the navigation panel to enroll your list of observers. "
					+ "In order to receive a holistic feedback, please enroll observers across categories. However, it is mandatory to nominate at least 1 Superior and 1 Subordinate and 3 people in Peers and Others category. "
					+ "After the observers are added, review the list carefully for email address accuracy. When satisfied with your list, "
					+ "click the submit button.</p>"
					
					+ "<p>The nominated observers will automatically receive an email informing them on how to complete the survey</p>"
					+ "<p><b>Please note:</b> Once you have submitted the list, you will not be able to remove the observers.</p>"
					
					+ "<p>4. Use <b>“Complete Surveys”</b> link to complete your survey(s)</p>"
					+ "<p>5. To review the completion status of the surveys, choose the <b>“Survey Status”</b> link</p>"
					+ "<p>Anonymity of the respondents shall be strictly maintained. </p>"
					+ "<p>Wish you all the best and a delightful experience!!</p>"
					+ "<p>Please use google Chrome or higher versions of Internet Explorer (10.0 and above) for completing the survey.</p>"
					+ "<p>DO NOT REPLY TO THIS EMAIL ID. In case of any query, please write in to <a href='mailto:jha.bagisha@mahindra.com'>jha.bagisha@mahindra.com</a> or <a href='mailto:rathore.supriya@mahindra.com'>rathore.supriya@mahindra.com</a> .</p>"
					+ "<p></p>"
					+ "<p>Best Regards,</p>"
					+ "<p>Adminsitrator</p>";
		} else {
			// fetch sector or company name,source role
		}
		try {
			
			MailShooter.sendEmail(session, from, to, subject, content);
			
		} catch (Exception e) {
			
			Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE,
					"ERROR IN triggerEmail function--" + e.getMessage());
			
		}
	}

	private void mailToRespondents(Session session) {
		String from = "";
		String to = "";
		String subject = "";
		String content = "";
		RespondentDAO respDao = new RespondentDAOImpl();
		ArrayList<RespondentPojo> respondentList = respDao
				.getRespondentDetailsForMail(feedbackId);

		for (RespondentPojo singleResp : respondentList) {
			System.out.println("\nSENDING MAIL TO - "
					+ singleResp.getRespondentEmailId());
			
			from = CommonConstants.SUPER_ADMIN_EMAIL_ID;
			to = singleResp.getRespondentEmailId();
			subject = singleResp.getNominatedFirstName() + " "
					+ singleResp.getNominatedLastName()
					+ " requests your feedback";
			
			content = "<p>Dear "
					+ singleResp.getRespondentFName()
					+ " "
					+ singleResp.getRespondentLName()
					+ ",</p>"
					+ "<p>"
					+ singleResp.getNominatedFirstName()
					+ " "
					+ singleResp.getNominatedLastName()
					+ " has the opportunity to participate in the Rise 360 degree feedback process. Your feedback will help measure how "+singleResp.getNominatedFirstName()+" "+singleResp.getNominatedLastName()
					+ " lives the 3 pillars of RISE and demonstrates the characteristics of a Mahindra Leader.  </p>"
					+ "<p>Your response will be treated with complete confidentiality and individual responses will not be identified in the report.  </p>"
					+ "<p>Would request you to kindly click on the link given below to start the survey. Please use google Chrome or higher versions of Internet Explorer (10.0 and above) for completing the survey. The survey will take 15 to 20 minutes for completion.   </p>"
					+ "<p>URL: "+CommonConstants.URL+"</p>"
					+ "<p>UserId: "+singleResp.getRespondentEmailId()+"</p>";
					System.out.println("SENDING MAIL to "+singleResp.getRespondentEmailId()+" ::Password - "+credentialList.get(singleResp.getRespondentEmailId()));
					
					if(!(credentialList.get(singleResp.getRespondentEmailId())==null||credentialList.get(singleResp.getRespondentEmailId()).equalsIgnoreCase("NULL"))){
						content = content + "<p>Password:"+credentialList.get(singleResp.getRespondentEmailId())+"</p>";
					}
					
					content = content 
					+ "<p>DO NOT REPLY TO THIS EMAIL ID. In case of any query, please write in to <a href='mailto:jha.bagisha@mahindra.com'>jha.bagisha@mahindra.com</a> or <a href='mailto:rathore.supriya@mahindra.com'>rathore.supriya@mahindra.com</a> .</p>"
					+ "<p>Thank you.</p>";
			try {
				
				if(MailShooter.sendEmail(session, from, to, subject, content)){
					respDao.updateMailSentToRespondentFlag(feedbackId, to);
				}
				
			} catch (Exception e) {
				Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE,
						"ERROR IN triggerEmail function--" + e.getMessage());
			}

		}

	}

	/*
	 * Author: Ajay Dubey 
	 */
	
	private void mailToDefaulterEmployee(Session session) {
		String from = "";
		String to = "";
		String subject = "";
		String content = "";
		from = CommonConstants.SUPER_ADMIN_EMAIL_ID;
		to = email;
		subject = "Rise 360 degree feedback survey";
		
		content = "<p>Dear "
				+ firstName +","
				+ " </p>"
				+ "<p>Your organization has registered you to participate in the Rise 360 degree feedback process. This is a"
				+ "survey that helps to measure personal attributes that are required to live the Rise Pillars which guide our"
				+ "thoughts and actions each day, at the workplace and outside.</p>"
				+ "<p>The major purpose of the survey is to collect data comparing your self-evaluation of these attributes with"
				+ "the evaluations of others. The resulting profile provides detailed information so you can see a pattern of "
				+ "Rise leadership strengths and possible concerns as seen by yourself versus other .</p>"
				+ "<p>An overview of the survey process and your unique logon information follow.</p>"
				+ "<p><b>OBSERVER LIST</b></p>"
				+ "<p>Log onto the website and select 'Enroll - Select Your Observers' and add observers to the list. In order to "
				+ "receive valuable feedback, choose people who have first-hand knowledge of your performance. Your list of "
				+ "observers should include at least 1 Superior and 3-4 people in each of the other categories. You can choose"
				+ "up to 15 observers to complete the survey.</p>"
				+ "<p><b>SURVEY COMPLETION</b></p>"
				+ "<p>After the Observer List is submitted, complete your self-survey by selecting 'Complete Survey' from the "
				+ "Main Menu."
				+ "<p><b>DATA COLLECTION AND SUMMARY REPORT</b></p>"
				+ "<p>The data collected from your observers will be electronically input into a database. A report that "
				+ "summarizes the data will be generated and will be shared with you.</p>"
				+ "<p>PLEASE LOG ONTO THE WEBSITE IMMEDIATELY TO SUBMIT YOUR OBSERVER LIST, AND COMPLETE "
				+ "YOUR SURVEY.</p>"
				+ "<p>To begin the process, please go to: URL "
				+ CommonConstants.URL
				+ "</p>"
				+ "<p>Use the following information once the site is contacted:</p>"
				+ "<p>User ID: </p>"
				+ email
				+ "<p>Password: </p>"
				+ password
				+ "<p><b>PLEASE SAVE THIS MESSAGE SO THAT YOU HAVE A RECORD OF YOUR USER ID AND PASSWORD.</b></p>"
				+ "<p>This is a secured website, which means that any data transferred to or from the site is encrypted to"
				+ " ensure confidentiality.<p>"
				+ "<p>Please use google Chrome or higher versions of Internet Explorer (10.0 and above) for completing the survey.</p>"
				+ "<p><b>HELP</b></p>"
				+ "<p>DO NOT REPLY TO THIS EMAIL ID. In case of any query, please write in to <a href='mailto:jha.bagisha@mahindra.com'>jha.bagisha@mahindra.com</a> or <a href='mailto:rathore.supriya@mahindra.com'>rathore.supriya@mahindra.com</a> .</p>"
				+ "<p>Thank you.</p>";

		try {
			
			MailShooter.sendEmail(session, from, to, subject, content);
			
		} catch (Exception e) {
			Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE,
					"ERROR IN triggerEmail function--" + e.getMessage());
		}

	}

	/*
	 * Author: Ajay Dubey
	 */
	// Ajay mail self defaullter starts
	private void mailToDefaulterEmployeeSelf(Session session) {
		String from = "";
		String to = "";
		String subject = "";
		String content = "";

		from = CommonConstants.SUPER_ADMIN_EMAIL_ID;
		to = email;
		subject = "Rise 360 degree feedback Survey Reminder";
		content = "<p>Dear "
				+ firstName +","
				+ " </p>"
				+ "<p>You have completed the Observer List for the Rise 360 degree feedback process, but your self-survey has not been received."
				+ "<p><b>PLEASE LOG ONTO THE WEBSITE TO COMPLETE AND SUBMIT THE SURVEY AS SOON AS YOU CAN. </b></p>"
				+ "<p>It will take 15 - 20 minutes to complete.  </p>"
				+ "<p>If you believe the survey was completed, please note that you must click the 'Submit' button at the end. A "
				+ "confirmation message will be displayed when it is complete.  After submitting the survey, you will not be able to access it. </p>"
				+ "<p>To complete the survey, please go to: URL </p>"
				+ CommonConstants.URL
				+ ""
				+ "<p>User ID: </p>"
				+ email
				+ "<p>Password: </p>"
				+ password
				+ "<p>This is a secured website, which means that any data transferred to or from the site is encrypted to"
				+ " ensure confidentiality.<p>"
				+ "<p>Please use google Chrome or higher versions of Internet Explorer (10.0 and above) for completing the survey.</p>"
				+ "<p><b>HELP</b></p>"
				+ "<p>DO NOT REPLY TO THIS EMAIL ID. In case of any query, please write in to <a href='mailto:jha.bagisha@mahindra.com'>jha.bagisha@mahindra.com</a> or <a href='mailto:rathore.supriya@mahindra.com'>rathore.supriya@mahindra.com</a> .</p>"
				+ "<p>Thank you.</p>";

		try {
			MailShooter.sendEmail(session, from, to, subject, content);
		} catch (Exception e) {
			Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE,
					"ERROR IN triggerEmail function--" + e.getMessage());
		}

	}

	// Ajay mail non self defaullter starts
	private void mailToDefaulterEmployeeNotSelf(Session session) {
		String from = "";
		String to = "";
		String subject = "";
		String content = "";
		from = CommonConstants.SUPER_ADMIN_EMAIL_ID;
		to = email;
		subject = "Rise 360 degree feedback Survey Reminder";
		content = "<p>Dear "
				+ firstName +","
				+ " </p>"
				+ "<p>This is a reminder to complete and submit the Rise 360 feedback survey for "
				+ firstName1
				+ ". This process will "
				+ "provide useful feedback that helps measure personal attributes that are required to live the Rise Pillars"
				+ "which guide our thoughts and actions each day, at the workplace and outside.</p>"
				+ "<p><b>PLEASE LOG ONTO THE WEBSITE TO COMPLETE AND SUBMIT THE SURVEY AS SOON AS YOU CAN. </b></p>"
				+ "<p>It will take 15 - 20 minutes to complete.  </p>"
				+ "<p>If you believe the survey was completed, please note that you must click the 'Submit' button at the end. A </p>"
				+ "<p>confirmation message will be displayed when it is complete.  After submitting the survey, you will not be able to access it. </p>"
				+ "<p>To complete the survey, please go to: URL "
				+ CommonConstants.URL
				+ "</p>"
				+ "<p>User ID: </p>"
				+ email
				+ "<p>Password: </p>"
				+ password
				+ "<p>This is a secured website, which means that any data transferred to or from the site is encrypted to</p>"
				+ "<p>ensure confidentiality.<p>"
				+ "<p>Please use google Chrome or higher versions of Internet Explorer (10.0 and above) for completing the survey.</p>"
				+ "<p><b>HELP</b></p>"
				+ "<p>DO NOT REPLY TO THIS EMAIL ID. In case of any query, please write in to <a href='mailto:jha.bagisha@mahindra.com'>jha.bagisha@mahindra.com</a> or <a href='mailto:rathore.supriya@mahindra.com'>rathore.supriya@mahindra.com</a> .</p>"				
				+ "<p>Thank you.</p>";

		try {
			MailShooter.sendEmail(session, from, to, subject, content);
		} catch (Exception e) {
			Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE,
					"ERROR IN triggerEmail function--" + e.getMessage());
		}

	}

	private static boolean sendEmail(Session session, String fromEmail,
			String toEmail, String subject, String body) {
		boolean successFlag = false;
		try {
			
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			
			msg.setFrom(new InternetAddress(fromEmail, "360 FEEDBACK"));
				
			msg.setSubject(subject);
			
			msg.setContent(body, "text/html; charset=utf-8");
			
			msg.setSentDate(new Date());
			
			msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmail, false));
			System.out.println("MESSAGE READY...");
			Transport.send(msg);
			
			System.out.println("EMAIL SENT SUCCESSFULLY...!!");
			successFlag = true;
		} catch (Exception e) {
			Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE,
					"ERROR IN sendEmail function--" + e.getMessage());
			e.printStackTrace();
		}
		return successFlag;
	}

}
