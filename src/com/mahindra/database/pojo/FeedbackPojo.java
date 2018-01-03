package com.mahindra.database.pojo;

import java.util.ArrayList;

public class FeedbackPojo extends BasePojo {

	private int feedbackid;
	private String emailid;
	private int questionid;
	private String templateid;
	private String questdesc;
	private String questtagline;
	private String questcatid;
	private int respondentid;
	private int response;
	private String freeTextResponse;
	private String freetextinput;
	private String createddatetime;
	private String feedbackstatus;
	private String querytype;
	private boolean isSubmit;
	private String empRelation;
	private String questType;
	private boolean closedStatus;
	private String closedBy;
	private boolean negativeQuest;
	private String endDate;
	private String startDate;
	private String firstName;
	private String lastName;
	

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

	public String getFreeTextResponse() {
		return freeTextResponse;
	}

	public void setFreeTextResponse(String freeTextResponse) {
		this.freeTextResponse = freeTextResponse;
	}

	public String getQuestType() {
		return questType;
	}

	public void setQuestType(String questType) {
		this.questType = questType;
	}

	private int countOfEmp;

	public int getCountOfEmp() {
		return countOfEmp;
	}

	public void setCountOfEmp(int countOfEmp) {
		this.countOfEmp = countOfEmp;
	}

	public String getEmpRelation() {
		return empRelation;
	}

	public void setEmpRelation(String empRelation) {
		this.empRelation = empRelation;
	}

	private ArrayList<FeedbackPojo> capturedRespone;

	public boolean isSubmit() {
		return isSubmit;
	}

	public void setSubmit(boolean isSubmit) {
		this.isSubmit = isSubmit;
	}

	public String getQuerytype() {
		return querytype;
	}

	public void setQuerytype(String querytype) {
		this.querytype = querytype;
	}

	public String getFeedbackstatus() {
		return feedbackstatus;
	}

	public void setFeedbackstatus(String feedbackstatus) {
		this.feedbackstatus = feedbackstatus;
	}

	public int getRespondentid() {
		return respondentid;
	}

	public void setRespondentid(int respondentid) {
		this.respondentid = respondentid;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public String getFreetextinput() {
		return freetextinput;
	}

	public ArrayList<FeedbackPojo> getCapturedRespone() {
		return capturedRespone;
	}

	public void setCapturedRespone(ArrayList<FeedbackPojo> capturedRespone) {
		this.capturedRespone = capturedRespone;
	}

	public void setFreetextinput(String freetextinput) {
		this.freetextinput = freetextinput;
	}

	public String getCreateddatetime() {
		return createddatetime;
	}

	public void setCreateddatetime(String createddatetime) {
		this.createddatetime = createddatetime;
	}

	public int getFeedbackid() {
		return feedbackid;
	}

	public void setFeedbackid(int feedbackid) {
		this.feedbackid = feedbackid;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public int getQuestionid() {
		return questionid;
	}

	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getQuestdesc() {
		return questdesc;
	}

	public void setQuestdesc(String questdesc) {
		this.questdesc = questdesc;
	}

	public String getQuesttagline() {
		return questtagline;
	}

	public void setQuesttagline(String questtagline) {
		this.questtagline = questtagline;
	}

	public String getQuestcatid() {
		return questcatid;
	}

	public void setQuestcatid(String questcatid) {
		this.questcatid = questcatid;
	}

	public boolean isClosedStatus() {
		return closedStatus;
	}

	public void setClosedStatus(boolean closedStatus) {
		this.closedStatus = closedStatus;
	}

	public String getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isNegativeQuest() {
		return negativeQuest;
	}

	public void setNegativeQuest(boolean negativeQuest) {
		this.negativeQuest = negativeQuest;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
