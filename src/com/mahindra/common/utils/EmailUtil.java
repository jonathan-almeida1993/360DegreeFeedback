package com.mahindra.common.utils;


import java.util.Date; 
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
import com.mahindra.database.pojo.UserPojo;

public class EmailUtil {
	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	private static void sendEmail(Session session, String toEmail, String subject, String body) {
		try {
			
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(CommonConstants.SUPER_ADMIN_EMAIL_ID, "360FEEDBACK"));

			msg.setSubject(subject);

			msg.setContent(body, "text/html");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			System.out.println("MESSAGE READY...");
			Transport.send(msg);

			System.out.println("EMAIL SENT SUCCESSFULLY...!!");
			
		} catch (Exception e) {
			
			Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE, "ERROR IN sendEmail function--"+e.getMessage());
			
		}
	}
	
	public static void triggerEmail(String toEmailID,UserPojo userDetails) {
		
		Properties props = System.getProperties();

		props.put("mail.smtp.host", CommonConstants.SMTP_HOST_SERVER_IP);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props, new Authenticator() {
			
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CommonConstants.SMTP_AUTH_USER_ID, CommonConstants.SMTP_AUTH_PWD);
            }
		});
			
		try {
			EmailUtil.sendEmail(session, toEmailID, "360DegreeFeedback - Forgot Password", 
					"<p>Dear "+userDetails.getFirstName()+" "+userDetails.getLastName()+",</p>"+
					"<p>Please use the following details to login to the system.</p>"+
					"<p>User ID: "+toEmailID+"</p>"+
					"<p>Password: "+userDetails.getPassword()+"</p>"+
					"<p>If you have not triggered this request, please contact the administrator for password change.</p>"+
					"<p>Thank You.</p>"+
					"<p><small>This is a system generated email. Kindly do not reply to this email.</small></p>");
		} catch (Exception e) {
			Logger.getLogger(EmailUtil.class.getName()).log(Level.SEVERE, "ERROR IN triggerEmail function--"+e.getMessage());
		}

	} 
}