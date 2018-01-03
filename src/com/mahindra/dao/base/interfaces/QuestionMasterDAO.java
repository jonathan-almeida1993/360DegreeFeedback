package com.mahindra.dao.base.interfaces;

import java.util.ArrayList;

import com.mahindra.dao.base.GenericDAO;
import com.mahindra.database.pojo.QuestionPojo;
import com.mahindra.database.pojo.TemplatePojo;

public interface QuestionMasterDAO extends GenericDAO {

	//public Boolean checkQuestions(QuestionPojo obj) throws Exception;

	public String insertQuestions(QuestionPojo obj);

	public String updateQuestions(QuestionPojo obj) ;

	public String deleteQuestions(QuestionPojo obj) ;

	public ArrayList<QuestionPojo> selectQuestions(QuestionPojo obj) ;
	
	//overloading selectQuestions
	public ArrayList<QuestionPojo> selectQuestions(QuestionPojo obj,TemplatePojo templateId) ;
	
	public ArrayList<QuestionPojo> getCategoryList() ;
	
	public ArrayList<QuestionPojo> getSubCategoryList(QuestionPojo obj) ;
}
