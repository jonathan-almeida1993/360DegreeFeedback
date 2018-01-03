package com.mahindra.dao.base.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.constants.SqlConstants;
import com.mahindra.dao.base.interfaces.FeedbackDAO;
import com.mahindra.database.connection.DBConnectionFactory;
import com.mahindra.database.pojo.FeedbackPojo;
import com.mahindra.database.pojo.NominationPojo;

public class FeedbackDAOImpl implements FeedbackDAO {


	Connection connection = null;

	@Override
	public Connection getConnection() throws SQLException,
	FileNotFoundException, ClassNotFoundException, IOException,
	NamingException {
		
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		return connection;
	}


	@Override
	public void fetchQuestions(FeedbackPojo obj){
		try{
			
			if(obj !=null && obj.getFeedbackstatus().equalsIgnoreCase("PENDING")){
				selectQuestions(obj);											    //Display all questions 					
			}else{
				getPartiallySavedQuestionResponse(obj);									//Display partially saved questions with response
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public String getFeedbackStatus(FeedbackPojo obj){
		
		Connection connect= null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String status = null;
		
		try{
			connect= getConnection();

			if(obj != null){

				preparedStatement = connect.prepareStatement(SqlConstants.SELECT_QUERY_TO_GET_FEEDBACK_STATUS);
				preparedStatement.setInt(1,(obj.getRespondentid()));
				preparedStatement.setInt(2,(obj.getFeedbackid()));
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					status = resultSet.getString("FeedbackStatus");
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(FeedbackDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN getFeedbackStatus()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return status;
	}



	@Override
	public ArrayList<FeedbackPojo> selectQuestions(FeedbackPojo obj) {

		ArrayList<FeedbackPojo> questionList= new ArrayList<FeedbackPojo>();
		Connection connect= null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connect = getConnection();
			System.out.println("FEEDBACK_ID - "+obj.getFeedbackid());
			System.out.println("RESPONDENT_ID - "+obj.getRespondentid());
				
			preparedStatement = connect.prepareStatement(SqlConstants.SELECT_QUERY_FOR_SELF_FEEDBACK);
			preparedStatement.setInt(1,(obj.getFeedbackid()));
			preparedStatement.setInt(2,(obj.getRespondentid()));
			resultSet = preparedStatement.executeQuery();
		
			while(resultSet.next()){
				FeedbackPojo singleQuestion = new FeedbackPojo();
				singleQuestion.setQuestionid(resultSet.getInt("QuestionId"));
				singleQuestion.setQuestdesc(resultSet.getString("QuestDesc"));
				singleQuestion.setQuesttagline(resultSet.getString("questtagline"));
				singleQuestion.setQuestType(resultSet.getString("QuestType"));
				singleQuestion.setNegativeQuest(resultSet.getBoolean("NegativeQuestionFlag"));
				questionList.add(singleQuestion);
			}

		}catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(FeedbackDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN selectQuestions()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return questionList;
	}


	@Override
	public ArrayList<FeedbackPojo> getPartiallySavedQuestionResponse(FeedbackPojo obj) {

		ArrayList<FeedbackPojo> questionResponseList = new ArrayList<FeedbackPojo>();
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.SELECT_QUERY_FOR_DISPLAY_PARTIALLY_SAVED_RESPONSE);
			preparedStatement.setInt(1,(obj.getFeedbackid()));
			preparedStatement.setInt(2,(obj.getRespondentid()));
			resultSet= preparedStatement.executeQuery();
		
			while(resultSet.next()){
				FeedbackPojo singleQuestionResponse = new FeedbackPojo();
				singleQuestionResponse.setQuestionid(resultSet.getInt("QuestionId"));
				singleQuestionResponse.setQuestdesc(resultSet.getString("QuestDesc"));
				singleQuestionResponse.setQuesttagline(resultSet.getString("QuestTagLine"));
				singleQuestionResponse.setResponse(resultSet.getInt("Response"));
				singleQuestionResponse.setFreeTextResponse(resultSet.getString("FreeTextResponse"));
				singleQuestionResponse.setFeedbackstatus(resultSet.getString("FeedbackStatus"));
				singleQuestionResponse.setQuestType(resultSet.getString("QuestType"));
				singleQuestionResponse.setNegativeQuest(resultSet.getBoolean("NegativeQuestionFlag"));
				questionResponseList.add(singleQuestionResponse);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(FeedbackDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN getPartiallySavedQuestionResponse()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return questionResponseList;
	}
	
	@Override
	public String operationType(ArrayList<FeedbackPojo> obj,HttpServletRequest request) {
		Connection connect = null;                          
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		String status= CommonConstants.STATUS_JDBC_ERROR;

		try {
			HttpSession session = request.getSession(false);
			String role = session.getAttribute(CommonConstants.SESSION_ROLE).toString();
			int nomEmpRespondentId = 0;
			int nomEmpFeedbackId = 0;
			if("EMP".equals(role)){
				nomEmpRespondentId = Integer.parseInt(session.getAttribute(CommonConstants.SESSION_RESPONDENT_ID).toString());
				nomEmpFeedbackId = Integer.parseInt(session.getAttribute(CommonConstants.SESSION_FEEDBACK_ID).toString());
			}
			
			String xmlToDB = "<responses>";
			for (FeedbackPojo temp : obj) {
				
				if(temp.getFeedbackid()==0&&temp.getRespondentid()==0){
					temp.setFeedbackid(nomEmpFeedbackId);
					temp.setRespondentid(nomEmpRespondentId);
				}
				
				String xmlTemp = "<resp>";
				Gson gson = new Gson();
				String tempJsonString = gson.toJson(temp);
				JSONObject jsonObj = new JSONObject(tempJsonString); 
				xmlTemp = xmlTemp + org.json.XML.toString(jsonObj)+"</resp>"; 
				xmlToDB = xmlToDB + xmlTemp;
			}
			xmlToDB = xmlToDB+"</responses>";
			System.out.println("XML to DB--"+xmlToDB);
			
			connect = getConnection();
			callableStatement = connect.prepareCall(SqlConstants.INSERT_RESPONSES);
			callableStatement.setString(1, xmlToDB);
			callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
			callableStatement.execute();
			status = callableStatement.getString(2);
			
		}catch (Exception e) {
			status = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, callableStatement, connect);
		}
		return status;
	}


	@Override
	public  String insertCapturedResponse(FeedbackPojo responseObj) {

		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String status= null;

		try {
			connect = getConnection();
			connect.setAutoCommit(false);
			System.out.println("FEEDBACK_ID    --> "+ responseObj.getFeedbackid());
			System.out.println("RESPONDENT_ID  --> "+ responseObj.getRespondentid());
			System.out.println("QUESTION_ID    --> "+ responseObj.getQuestionid());
			System.out.println("RESPONSE       --> "+ responseObj.getResponse());
			System.out.println("OPERATION_TYPE --> "+ responseObj.getQuerytype());

			preparedStatement = connect.prepareStatement(SqlConstants.INSERT_QUERY_TO_CAPTURE_RESPONSE);
			preparedStatement.setInt(1, responseObj.getFeedbackid());
			preparedStatement.setInt(2, responseObj.getQuestionid());
			preparedStatement.setInt(3, responseObj.getResponse());
			preparedStatement.setInt(4, responseObj.getRespondentid());
			int executeUpdate = preparedStatement.executeUpdate();

			if (executeUpdate > 0) {
				status = CommonConstants.STATUS_INSERTION_SUCCESSFULL;
				DBConnectionFactory.close(resultSet, preparedStatement, null);
				preparedStatement = connect.prepareStatement(SqlConstants.UPDATE_RESPONDENT_STATUS);
				preparedStatement.setString(1, "INPROGRESS");
				preparedStatement.setInt(2, responseObj.getRespondentid());//add dynamic respondentId
				preparedStatement.executeUpdate();
				connect.commit();
			}
			
			

		} catch (Exception e) {
			status = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return status;
	}


	@Override
	public String updateCapturedResponse(FeedbackPojo userResponse) {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String status= null;
		
		try {
			connect = getConnection();
			System.out.println("FEEDBACK_ID    --> "+ userResponse.getFeedbackid());
			System.out.println("RESPONDENT_ID  --> "+ userResponse.getRespondentid());
			System.out.println("QUESTION_ID    --> "+ userResponse.getQuestionid());
			System.out.println("RESPONSE       --> "+ userResponse.getResponse());
			System.out.println("OPERATION_TYPE --> "+ userResponse.getQuerytype());

			preparedStatement = connect.prepareStatement(SqlConstants.UPDATE_QUERY_TO_CAPTURE_RESPONSE);
			preparedStatement.setInt(1, userResponse.getResponse());
			preparedStatement.setInt(2, userResponse.getFeedbackid());
			preparedStatement.setInt(3, userResponse.getQuestionid());
			preparedStatement.setInt(4, userResponse.getRespondentid());
			int executeUpdate = preparedStatement.executeUpdate();
			if (executeUpdate > 0) {
				status = CommonConstants.STATUS_INSERTION_SUCCESSFULL;
			}

		} catch (Exception e) {
			status = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return status;
	}
	
	@Override
	public ArrayList<FeedbackPojo> selectEmployees(FeedbackPojo obj) {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<FeedbackPojo> employeeList = new ArrayList<FeedbackPojo>();
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.SELECT_EMPLOYEES);
			preparedStatement.setString(1, obj.getEmailid());
			resultSet = preparedStatement.executeQuery();
		
			while(resultSet.next()){
				FeedbackPojo singleEmployee = new FeedbackPojo();
				singleEmployee.setRespondentid(resultSet.getInt("RespondentId"));
				singleEmployee.setFeedbackid(resultSet.getInt("FeedbackId"));
				singleEmployee.setFeedbackstatus(resultSet.getString("FeedbackStatus"));
				singleEmployee.setEmailid(resultSet.getString("EmailId"));
				singleEmployee.setClosedStatus(resultSet.getBoolean("closedStatus"));
				singleEmployee.setClosedBy(resultSet.getString("closedBy"));
				singleEmployee.setFirstName(resultSet.getString("FirstName"));
				singleEmployee.setLastName(resultSet.getString("LastName"));
				singleEmployee.setStartDate(resultSet.getString("StartDate"));
				singleEmployee.setEndDate(resultSet.getString("EndDate"));
				employeeList.add(singleEmployee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return employeeList;
	}
	
	
	@Override
	public ArrayList<FeedbackPojo> countOfRespondents(FeedbackPojo obj) {

		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<FeedbackPojo> countList = new ArrayList<FeedbackPojo>();
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.COUNT_FOR_EMPRELATION);
			preparedStatement.setString(1, obj.getEmailid());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				
				FeedbackPojo countresultOfRespondent = new FeedbackPojo();
				countresultOfRespondent.setEmpRelation(resultSet.getString("EmpRelation"));
				countresultOfRespondent.setCountOfEmp(resultSet.getInt("Count"));
				countresultOfRespondent.setFeedbackstatus(resultSet.getString("FeedbackStatus"));
				countList.add(countresultOfRespondent);
								
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return countList;
		
	}


	@Override
	public String feedbackClosure(FeedbackPojo obj,String role) {
		int self,peer,subordinate,other,senior;
		Connection connect = null;
		CallableStatement callable=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String status="";
		try {
			connect = getConnection();
			
			preparedStatement = connect.prepareStatement(SqlConstants.CLOSURECONDITION);
			preparedStatement.setInt(1, obj.getFeedbackid());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				self=resultSet.getInt("SELF");
				peer=resultSet.getInt("PEER");
				subordinate=resultSet.getInt("SUBORDINATE");
				other=resultSet.getInt("OTHER");
				senior=resultSet.getInt("SENIOR");
				
				if(self==1 && senior>=1){
					
					callable=connect.prepareCall(SqlConstants.SP_REPORT_GEN);
					callable.setInt(1,obj.getFeedbackid());
					callable.registerOutParameter(2, java.sql.Types.VARCHAR);
					callable.execute();
				
					if(callable.getString(2).trim().toLowerCase().equals("OK".trim().toLowerCase())){
						preparedStatement = connect.prepareStatement(SqlConstants.FEEDBACKCLOSURE);
						preparedStatement.setString(1, role);
						preparedStatement.setInt(2, obj.getFeedbackid());
						int success=preparedStatement.executeUpdate();
						if(success>0)
							status="closedTrue";
						else
							status="closedFalse";
					}else
						status=callable.getString(2);
					
					}else
						status="closeCriteriaError";
			}
		}catch(Exception ex){
			status="closedError";
			ex.printStackTrace();
		}finally{
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return status;
	}
	
	
	@Override
	public String feedbackForceClosure(FeedbackPojo obj,String role) {
		
		Connection connect = null;
		CallableStatement callable=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String status="";
		
		try {
			connect = getConnection();
			callable=connect.prepareCall(SqlConstants.SP_REPORT_GEN);
			callable.setInt(1,obj.getFeedbackid());
			callable.registerOutParameter(2, java.sql.Types.VARCHAR);
			callable.execute();
			
			if(callable.getString(2).trim().toLowerCase().equals("OK".trim().toLowerCase())){
						preparedStatement = connect.prepareStatement(SqlConstants.FEEDBACKCLOSURE);
						preparedStatement.setString(1, role);
						preparedStatement.setInt(2, obj.getFeedbackid());
						int success=preparedStatement.executeUpdate();
						if(success>0)
							status="closedTrue";
						else
							status="closedFalse";
			}else
				status=callable.getString(2);
			
		}catch(Exception ex){
			status="closedError";
			ex.printStackTrace();
		}finally{
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return status;
	}
	
	
	@Override
	public String reOpen(FeedbackPojo obj,String role) {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String status="";
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.REOPENCONDITION);
			preparedStatement.setInt(1,obj.getFeedbackid());
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()){
				preparedStatement.close();
				preparedStatement = connect.prepareStatement(SqlConstants.FEEDBACKREOPEN);
				preparedStatement.setString(1, role);
				preparedStatement.setInt(2, obj.getFeedbackid());
				int success=preparedStatement.executeUpdate();
				if(success>0)
					status="reopenedTrue";
				else
					status="reopenedFalse";
			}
			else
				status="reopenCriteriaError";
		}catch(Exception ex){
			status="reopenError";
			ex.printStackTrace();
		}
		finally{
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return status;
	}
	
	
	@Override
	public ArrayList<FeedbackPojo> getFeedbackList() {

		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<FeedbackPojo> feedbackList = new ArrayList<FeedbackPojo>();
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.GET_ACTIVE_FEEDBACK_POJO);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				
				FeedbackPojo feedBack = new FeedbackPojo();
				feedBack.setFeedbackid(resultSet.getInt("FeedbackId"));
				feedbackList.add(feedBack);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return feedbackList;
		
	}


	@Override
	public NominationPojo getFeedbackForExtension(FeedbackPojo obj) {
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		NominationPojo nomineeDet= new NominationPojo();
	
		try{
			connect= getConnection();
			preparedStatement=connect.prepareStatement(SqlConstants.GET_FEEDBACK_FOR_EXTENSION);
			preparedStatement.setInt(1, obj.getFeedbackid());
			
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				nomineeDet.setFirstName(resultSet.getString("fName"));
				nomineeDet.setLastName(resultSet.getString("lName"));
				nomineeDet.setFromDate(resultSet.getString("fromDate"));
				nomineeDet.setToDate(resultSet.getString("toDate"));
			}
			
		}
		catch(Exception ex){
			
		}finally{
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return nomineeDet;
	}
	
	public FeedbackPojo getSurveyStartEndDates(int feedbackId){
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		FeedbackPojo surveyDates = new FeedbackPojo();
		try{
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.GET_SURVEY_DATES);
			preparedStatement.setInt(1, feedbackId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				surveyDates.setStartDate(resultSet.getString("StartDate"));
				surveyDates.setEndDate(resultSet.getString("EndDate"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return surveyDates;
	}
	
}//end of the class





