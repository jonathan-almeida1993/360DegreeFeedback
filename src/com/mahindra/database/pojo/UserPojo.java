package com.mahindra.database.pojo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class UserPojo extends BasePojo implements HttpSessionBindingListener {

	private String firstName;
	private String lastName;
	private String password;
	private String newpassword;
	private boolean isPwdChanged;
	private String emailId;
	private String role;
	private int sectorId;
	private int companyId;
	private String sectorName;
	private String companyName;
	private String nominatedEmpFeedbackId;
	private String nominatedEmpRespondentId;
	private String status;

	public String getNominatedEmpFeedbackId() {
		return nominatedEmpFeedbackId;
	}

	public void setNominatedEmpFeedbackId(String nominatedEmpFeedbackId) {
		this.nominatedEmpFeedbackId = nominatedEmpFeedbackId;
	}


	public String getNominatedEmpRespondentId() {
		return nominatedEmpRespondentId;
	}

	public void setNominatedEmpRespondentId(String nominatedEmpRespondentId) {
		this.nominatedEmpRespondentId = nominatedEmpRespondentId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public boolean isPwdChanged() {
		return isPwdChanged;
	}

	public void setPwdChanged(boolean isPwdChanged) {
		this.isPwdChanged = isPwdChanged;
	}

	
	private static Map<UserPojo, HttpSession> logins= new HashMap<UserPojo, HttpSession>();

	@Override
	  public boolean equals(Object other) {
		System.out.println(getEmailId());
	    return (other instanceof UserPojo) && (getEmailId() != null) ? 
	    		getEmailId().equals(((UserPojo) other).getEmailId()) : (other == this);
	  }

	  @Override
	  public int hashCode() {
		  System.out.println("hashcode"+getEmailId());
	    return (getEmailId() != null) ? 
	     (this.getClass().hashCode() + getEmailId().hashCode()) : super.hashCode();
	  }

	  @Override
	  public void valueBound(HttpSessionBindingEvent event){
	    HttpSession oldSession = logins.remove(this);
	    if (oldSession != null) {
	    	oldSession.invalidate();
	    }
	    System.out.println("In value Bond");
	    logins.put(this,event.getSession());
	    //Note: you can comment above code and remove comments from below code. removing comments from 
	 //below code will remove old session of user and let the user log-in from new session.

	    //HttpSession session = logins.remove(this);
	    //if (session != null) {
	    //  session.invalidate();
	   //}
	   //logins.put(this, event.getSession());  
	  }

	  @Override
	  public void valueUnbound(HttpSessionBindingEvent event) {

		    System.out.println("In value UnBond");
	    logins.remove(this);
	  }
	
	
	
	
}
