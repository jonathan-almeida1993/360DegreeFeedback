package com.mahindra.dao.base.interfaces;

import java.sql.Connection;
import java.util.ArrayList;

import com.mahindra.dao.base.GenericDAO;
import com.mahindra.database.pojo.EmployeeMasterPojo;
import com.mahindra.database.pojo.NominationPojo;
import com.mahindra.database.pojo.RespondentPojo;
import com.mahindra.database.pojo.UserPojo;

public interface RespondentDAO extends GenericDAO {

	public RespondentPojo getActiveFeedbackId(String emailId); 
	
	public UserPojo nominateRespondent(RespondentPojo obj,Connection connection);

	public RespondentPojo callNominateRespondent(ArrayList<RespondentPojo> respondentList,int feedbackId,String isSubmit);
	
	public ArrayList<RespondentPojo> getRespondents(int feedbackId) ;
	
	public RespondentPojo getNominatedEmpRespondentId(int feedbackId,String nominatedEmpEmailId) ;
	
	public ArrayList<RespondentPojo> getRespondentDetailsForMail(int feedbackId);

	public ArrayList<RespondentPojo> getrespondentstatus(int feedbackId) ;
	
	public String getFeedbackStatus(int feedbackId);
	
	public ArrayList<RespondentPojo> getRespondentSuggestions(String nominatedEmployeeEmailId,int feedbackId,String feedbackStatus) ;
	
	public ArrayList<EmployeeMasterPojo> getEmployeeList(String jData) ;
	
	public void updateMailSentToRespondentFlag(int feedbackId, String emailId) ;

	public RespondentPojo callNominateRespondentAfterSubmit(ArrayList<RespondentPojo> respondentList,int feedbackId);
}
