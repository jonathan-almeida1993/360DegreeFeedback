package com.mahindra.database.pojo;

public class QuestionPojo extends BasePojo {

	private String quesId;
	private String quesCatId;
	private String quesCat;
	private String quesSubCat;
	private String quesDesc;
	private String quesType;
	private String quesTagLine;
	private String quesOptValues;
	private String quesStatus;
	private String quesSequenceNo;
	private String quesPageNo;
	private boolean negativeQuest;

	public String getQuesId() {
		return quesId;
	}

	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}

	public String getQuesCatId() {
		return quesCatId;
	}

	public void setQuesCatId(String quesCatId) {
		this.quesCatId = quesCatId;
	}

	public String getQuesDesc() {
		return quesDesc;
	}

	public void setQuesDesc(String quesDesc) {
		this.quesDesc = quesDesc;
	}

	public String getQuesType() {
		return quesType;
	}

	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}

	public String getQuesTagLine() {
		return quesTagLine;
	}

	public void setQuesTagLine(String quesTagLine) {
		this.quesTagLine = quesTagLine;
	}

	public String getQuesOptValues() {
		return quesOptValues;
	}

	public void setQuesOptValues(String quesOptValues) {
		this.quesOptValues = quesOptValues;
	}


	public String getQuesCat() {
		return quesCat;
	}

	public void setQuesCat(String quesCat) {
		this.quesCat = quesCat;
	}

	public String getQuesSubCat() {
		return quesSubCat;
	}

	public void setQuesSubCat(String quesSubCat) {
		this.quesSubCat = quesSubCat;
	}

	public String getQuesStatus() {
		return quesStatus;
	}

	public void setQuesStatus(String quesStatus) {
		this.quesStatus = quesStatus;
	}

	public String getQuesSequenceNo() {
		return quesSequenceNo;
	}

	public void setQuesSequenceNo(String quesSequenceNo) {
		this.quesSequenceNo = quesSequenceNo;
	}

	public String getQuesPageNo() {
		return quesPageNo;
	}

	public void setQuesPageNo(String quesPageNo) {
		this.quesPageNo = quesPageNo;
	}

	public boolean isNegativeQuest() {
		return negativeQuest;
	}

	public void setNegativeQuest(boolean negativeQuest) {
		this.negativeQuest = negativeQuest;
	}

}
