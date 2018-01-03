package com.mahindra.database.pojo;

public class RespondentPojo extends BasePojo {

	private int feedbackId;
	private int respondentId;
	private String respondentEmailId;
	private String nominatedEmailId;
	private String nominatedFirstName;
	private String nominatedLastName;
	private String respondentFName;
	private String respondentLName;
	private String empRelation;
	private String respondentStatus;
	private String password;
	private String feedbackStatus;

	public String getRespondentStatus() {
		return respondentStatus;
	}

	public void setRespondentStatus(String respondentStatus) {
		this.respondentStatus = respondentStatus;
	}

	public int getRespondentId() {
		return respondentId;
	}

	public void setRespondentId(int respondentId) {
		this.respondentId = respondentId;
	}

	public String getRespondentEmailId() {
		return respondentEmailId;
	}

	public void setRespondentEmailId(String respondentEmailId) {
		this.respondentEmailId = respondentEmailId;
	}

	public String getRespondentFName() {
		return respondentFName;
	}

	public void setRespondentFName(String respondentFName) {
		this.respondentFName = respondentFName;
	}

	public String getRespondentLName() {
		return respondentLName;
	}

	public void setRespondentLName(String respondentLName) {
		this.respondentLName = respondentLName;
	}

	public String getEmpRelation() {
		return empRelation;
	}

	public void setEmpRelation(String empRelation) {
		this.empRelation = empRelation;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	private String operationType;

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getNominatedEmailId() {
		return nominatedEmailId;
	}

	public void setNominatedEmailId(String nominatedEmailId) {
		this.nominatedEmailId = nominatedEmailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNominatedFirstName() {
		return nominatedFirstName;
	}

	public void setNominatedFirstName(String nominatedFirstName) {
		this.nominatedFirstName = nominatedFirstName;
	}

	public String getNominatedLastName() {
		return nominatedLastName;
	}

	public void setNominatedLastName(String nominatedLastName) {
		this.nominatedLastName = nominatedLastName;
	}

	public String getFeedbackStatus() {
		return feedbackStatus;
	}

	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}

}
