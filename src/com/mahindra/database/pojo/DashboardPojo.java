package com.mahindra.database.pojo;

import java.sql.Date;

public class DashboardPojo {
	
	private int feedBackId;
	private String Name;
	private String TemplateName;
	private Date StartDate;
	private Date EndDate;
	private int NoOfRespondents;
	private int NoOfRespponsesReceived;
	private String SelfFeedback;
	private String Status;
	private boolean closedStatus;
	private boolean reportFlag;
	private String EmailId;
	private String empFirstName;
	private String empLastName;
	private int respondentId;
	private String empRelation;
	private String respondentEmailId;
	private String respondentFirstName;
	private String respondentLastName;
	private String respondentFeedbackStatus;
	
	
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getTemplateName() {
		return TemplateName;
	}
	public void setTemplateName(String templateName) {
		TemplateName = templateName;
	}
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public int getNoOfRespondents() {
		return NoOfRespondents;
	}
	public void setNoOfRespondents(int noOfRespondents) {
		NoOfRespondents = noOfRespondents;
	}
	public int getNoOfRespponsesReceived() {
		return NoOfRespponsesReceived;
	}
	public void setNoOfRespponsesReceived(int noOfRespponsesReceived) {
		NoOfRespponsesReceived = noOfRespponsesReceived;
	}
	public String isSelfFeedback() {
		return SelfFeedback;
	}
	public void setSelfFeedback(String selfFeedback) {
		SelfFeedback = selfFeedback;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public int getFeedBackId() {
		return feedBackId;
	}
	public void setFeedBackId(int feedBackId) {
		this.feedBackId = feedBackId;
	}
	public boolean isClosedStatus() {
		return closedStatus;
	}
	public void setClosedStatus(boolean closedStatus) {
		this.closedStatus = closedStatus;
	}
	public boolean isReportFlag() {
		return reportFlag;
	}
	public void setReportFlag(boolean reportFlag) {
		this.reportFlag = reportFlag;
	}
	public String getEmailId() {
		return EmailId;
	}
	public void setEmailId(String emailId) {
		EmailId = emailId;
	}
	public String getEmpFirstName() {
		return empFirstName;
	}
	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}
	public String getEmpLastName() {
		return empLastName;
	}
	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}
	
	public String getEmpRelation() {
		return empRelation;
	}
	public void setEmpRelation(String empRelation) {
		this.empRelation = empRelation;
	}
	public String getRespondentEmailId() {
		return respondentEmailId;
	}
	public void setRespondentEmailId(String respondentEmailId) {
		this.respondentEmailId = respondentEmailId;
	}
	public String getRespondentFirstName() {
		return respondentFirstName;
	}
	public void setRespondentFirstName(String respondentFirstName) {
		this.respondentFirstName = respondentFirstName;
	}
	public String getRespondentLastName() {
		return respondentLastName;
	}
	public void setRespondentLastName(String respondentLastName) {
		this.respondentLastName = respondentLastName;
	}
	public String getRespondentFeedbackStatus() {
		return respondentFeedbackStatus;
	}
	public void setRespondentFeedbackStatus(String respondentFeedbackStatus) {
		this.respondentFeedbackStatus = respondentFeedbackStatus;
	}
	public String getSelfFeedback() {
		return SelfFeedback;
	}
	public int getRespondentId() {
		return respondentId;
	}
	public void setRespondentId(int respondentId) {
		this.respondentId = respondentId;
	}
			

}
