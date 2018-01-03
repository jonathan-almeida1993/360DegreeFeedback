package com.mahindra.database.pojo;

import java.util.ArrayList;

public class TemplatePojo extends BasePojo {

	private String templateName;
	private String templateId;
	private String templateStatus;
	private ArrayList<QuestionPojo> quesTempMap;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	
	public ArrayList<QuestionPojo> getQuesTempMap() {
		return quesTempMap;
	}

	public void setQuesTempMap(ArrayList<QuestionPojo> quesTempMap) {
		this.quesTempMap = quesTempMap;
	}

	public String getTemplateStatus() {
		return templateStatus;
	}

	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
	}

}

