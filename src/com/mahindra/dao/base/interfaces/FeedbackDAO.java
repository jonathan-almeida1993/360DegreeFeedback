package com.mahindra.dao.base.interfaces;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.mahindra.dao.base.GenericDAO;
import com.mahindra.database.pojo.FeedbackPojo;
import com.mahindra.database.pojo.NominationPojo;

public interface FeedbackDAO extends GenericDAO {
	
	
	public ArrayList<FeedbackPojo> selectQuestions(FeedbackPojo obj) ;
	
	public  String insertCapturedResponse(FeedbackPojo responseObj);

	public ArrayList<FeedbackPojo> getPartiallySavedQuestionResponse(FeedbackPojo obj) ;
	
	public String getFeedbackStatus(FeedbackPojo obj);
	
	public void fetchQuestions(FeedbackPojo obj);
	
	public String operationType(ArrayList<FeedbackPojo> obj,HttpServletRequest request);
	
	public String updateCapturedResponse(FeedbackPojo userResponse) ;
	
	public ArrayList<FeedbackPojo> selectEmployees(FeedbackPojo obj) ;
	
	public ArrayList<FeedbackPojo> countOfRespondents(FeedbackPojo obj) ;
	
	public String feedbackClosure(FeedbackPojo obj,String role) ;
	
	public String feedbackForceClosure(FeedbackPojo obj,String role);

	public ArrayList<FeedbackPojo> getFeedbackList();
	
	public NominationPojo getFeedbackForExtension(FeedbackPojo obj);
	
	public String reOpen(FeedbackPojo obj,String role);
	
	public FeedbackPojo getSurveyStartEndDates(int feedbackId);
}
