package com.mahindra.dao.base.interfaces;

import com.mahindra.dao.base.GenericDAO;
import com.mahindra.database.pojo.TemplatePojo;

public interface TemplateMasterDAO extends GenericDAO {

	public String insertTemplate(TemplatePojo obj);
	
	public String updateTemplate(TemplatePojo obj);
	
	public TemplatePojo fetchTemplateDetails(TemplatePojo obj); 

}
