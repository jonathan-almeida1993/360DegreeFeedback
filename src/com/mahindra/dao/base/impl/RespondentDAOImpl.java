package com.mahindra.dao.base.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.constants.SqlConstants;
import com.mahindra.common.utils.MailShooter;
import com.mahindra.dao.base.interfaces.RespondentDAO;
import com.mahindra.database.connection.DBConnectionFactory;
import com.mahindra.database.pojo.EmployeeMasterPojo;
import com.mahindra.database.pojo.NominationPojo;
import com.mahindra.database.pojo.RespondentPojo;
import com.mahindra.database.pojo.UserPojo;

public class RespondentDAOImpl implements RespondentDAO {

	Connection connection = null;

	@Override
	public Connection getConnection() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException, NamingException {
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		return connection;
	}
	
		
	@Override
	public RespondentPojo getActiveFeedbackId(String emailId) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		RespondentPojo feedbackId = new RespondentPojo();
		feedbackId.setStatus(CommonConstants.STATUS_JDBC_ERROR);
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.GET_ACTIVE_FEEDBACK_ID);
			preparedStatement.setString(1, emailId);
			resultSet = preparedStatement.executeQuery();
			int count = 0;
			
			while(resultSet.next()){
				feedbackId.setFeedbackId(resultSet.getInt("FeedbackId"));
				count++;
			}
			
			if(count>1){
				feedbackId.setStatus("ERROR-MULTIPLE_SURVEYS_ACTIVE_AT_THE_SAME_TIME");
			}else{
				feedbackId.setStatus(CommonConstants.STATUS_OK);
			}
			
		} catch (Exception e) {
			feedbackId.setStatus(CommonConstants.STATUS_JDBC_ERROR);
			Logger.getLogger(RespondentDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN getFeedbackList()-->"+e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return feedbackId;
	}
	

	public RespondentPojo getNominatedEmpRespondentId(int feedbackId,String nominatedEmpEmailId) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		RespondentPojo respondentId = new RespondentPojo();
		respondentId.setStatus(CommonConstants.STATUS_JDBC_ERROR);
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.GET_NOMINATED_EMP_RESPONDENT_ID);
			preparedStatement.setInt(1, feedbackId);
			preparedStatement.setString(2, nominatedEmpEmailId);
			resultSet = preparedStatement.executeQuery();
			int count = 0;
			
			while(resultSet.next()){
				respondentId.setRespondentId(resultSet.getInt("RespondentId"));
				count++;
			}
			
			if(count==1){
				respondentId.setStatus(CommonConstants.STATUS_OK);
			}else if(count>1){
				respondentId.setStatus("ERROR-MULTIPLE_RESPONDENT_IDS_FOUND");
			}else{
				respondentId.setStatus("ERROR-NOMINATED_EMPLOYEE_W/O_RESPONDENT_ID");
			}
			
		} catch (Exception e) {
			respondentId.setStatus(CommonConstants.STATUS_JDBC_ERROR);
			Logger.getLogger(RespondentDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN getNominatedEmpRespondentId()-->"+e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return respondentId;
	}
	
	public RespondentPojo callNominateRespondent(ArrayList<RespondentPojo> respondentList,int feedbackId,String isSubmit){
		Connection connection = null;
		RespondentDAO respDao = new RespondentDAOImpl();
		RespondentPojo result = new RespondentPojo();
		UserPojo pwdStatus = new UserPojo();
		HashMap<String,String> credentialList = new HashMap<String,String>();
		result.setStatus(CommonConstants.STATUS_JDBC_ERROR); 
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			
			for(RespondentPojo singleRespondent : respondentList ){
				singleRespondent.setFeedbackId(feedbackId);
				pwdStatus = respDao.nominateRespondent(singleRespondent,connection);
				System.out.println("respondentEmailId--"+singleRespondent.getRespondentEmailId()+"  empRelation--"+singleRespondent.getEmpRelation()+"  STATUS:"+pwdStatus.getStatus());
				credentialList.put(singleRespondent.getRespondentEmailId(), pwdStatus.getPassword());
				if("ERROR_OCCURED".equals(pwdStatus.getStatus())||CommonConstants.STATUS_JDBC_ERROR.equals(pwdStatus.getStatus())){
					if("ERROR_OCCURED".equals(pwdStatus.getStatus()))
						result.setStatus("ERROR_OCCURED");
					else
						result.setStatus(CommonConstants.STATUS_JDBC_ERROR);
					throw new Exception();
				}
			}
			
			if("true".equalsIgnoreCase(isSubmit)){
				updateFeedbackStatus(feedbackId,connection);	
				MailShooter shootMail = new MailShooter(feedbackId,credentialList);
				Thread t = new Thread(shootMail);
			    t.start(); 
			}
			connection.commit();
			result.setStatus(CommonConstants.STATUS_OK);
			
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN callNominateRespondent()-->" + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(null, null, connection);
		}
		return result;
	}
	
	//Insert respondents after submit
	public RespondentPojo callNominateRespondentAfterSubmit(ArrayList<RespondentPojo> respondentList,int feedbackId){
		Connection connection = null;
		RespondentDAO respDao = new RespondentDAOImpl();
		RespondentPojo result = new RespondentPojo();
		UserPojo pwdStatus = new UserPojo();
		HashMap<String,String> credentialList = new HashMap<String,String>();
		result.setStatus(CommonConstants.STATUS_JDBC_ERROR); 
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			for(RespondentPojo singleRespondent : respondentList ){
				singleRespondent.setFeedbackId(feedbackId);
				pwdStatus = respDao.nominateRespondent(singleRespondent,connection);
				System.out.println("respondentEmailId--"+singleRespondent.getRespondentEmailId()+"  empRelation--"+singleRespondent.getEmpRelation()+"  STATUS:"+pwdStatus.getStatus());
				credentialList.put(singleRespondent.getRespondentEmailId(), pwdStatus.getPassword());
				
				if("ERROR_OCCURED".equals(pwdStatus.getStatus())||CommonConstants.STATUS_JDBC_ERROR.equals(pwdStatus.getStatus())){
					if("ERROR_OCCURED".equals(pwdStatus.getStatus()))
						result.setStatus("ERROR_OCCURED");
					else
						result.setStatus(CommonConstants.STATUS_JDBC_ERROR);
					throw new Exception();
				}
			}
		connection.commit();
		MailShooter shootMail = new MailShooter(feedbackId,credentialList);
		Thread t = new Thread(shootMail);
	    t.start(); 
		result.setStatus(CommonConstants.STATUS_OK);
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN callNominateRespondentAfterSubmit()-->" + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(null, null, connection);
		}
		return result;
	}
	
	//Insert respondents after submit ends
	public UserPojo nominateRespondent(RespondentPojo obj,Connection connection) {
		CallableStatement callableStatement = null;   
		ResultSet resultSet = null;
		UserPojo pwdStatus = new UserPojo();
		pwdStatus.setStatus(CommonConstants.STATUS_JDBC_ERROR);
		try {
			
			callableStatement = connection.prepareCall(SqlConstants.SP_NOMINATE_RESPONDENT);
			callableStatement.setInt(1,obj.getFeedbackId());
			callableStatement.setString(2, obj.getRespondentEmailId());
			callableStatement.setString(3,obj.getRespondentFName());
			callableStatement.setString(4, obj.getRespondentLName());
			callableStatement.setString(5, obj.getEmpRelation());
			callableStatement.setInt(6, obj.getRespondentId());
			callableStatement.setString(7, obj.getOperationType());
			System.out.println("  fbId-"+obj.getFeedbackId()+"::respEmailId-"+obj.getRespondentEmailId()+"::respID-"+obj.getRespondentId()+"::opType-"+obj.getOperationType());
			callableStatement.registerOutParameter(8, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(9, java.sql.Types.VARCHAR);
			callableStatement.execute();
			pwdStatus.setStatus(callableStatement.getString(8));
			pwdStatus.setPassword(callableStatement.getString(9));
			System.out.println("CLEAR TEXT PASSWORD - "+pwdStatus.getPassword());
			
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN nominateRespondent()-->" + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, callableStatement, null);
		}
		return pwdStatus;
	}

	public ArrayList<RespondentPojo> getRespondents(int feedbackId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<RespondentPojo> respondentList = new ArrayList<RespondentPojo>();
		try {
			connection = getConnection();
			/*RespondentId, FeedbackId, EmpRelation, RespondentEmailId, RespondentFirstName, RespondentLastName,RespondentStatus */
			preparedStatement = connection.prepareStatement(SqlConstants.SELECT_RESPONDENTS);
			preparedStatement.setInt(1,feedbackId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				RespondentPojo respondent = new RespondentPojo();
				respondent.setFeedbackId(resultSet.getInt("FeedbackId"));
				respondent.setRespondentId(resultSet.getInt("RespondentId"));
				respondent.setEmpRelation(resultSet.getString("EmpRelation"));
				respondent.setRespondentEmailId(resultSet.getString("RespondentEmailId"));
				respondent.setRespondentFName(resultSet.getString("RespondentFirstName"));
				respondent.setRespondentLName(resultSet.getString("RespondentLastName"));
				respondent.setRespondentStatus(resultSet.getString("RespondentStatus"));
				respondent.setFeedbackStatus(resultSet.getString("FeedbackStatus"));
				respondentList.add(respondent);
			}
			
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN getRespondents()-->" + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return respondentList;
	}

	public String updateFeedbackStatus(int feedbackId,Connection connect) {
		// TODO Auto-generated method stub
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String status = CommonConstants.STATUS_JDBC_ERROR;
		
		try {
			preparedStatement = connect.prepareStatement(SqlConstants.UPDATE_STATUS_AFTER_RESPONDENT_SELECTION);
			preparedStatement.setInt(1, feedbackId);
			preparedStatement.executeUpdate();
			status = CommonConstants.STATUS_OK;
		} catch (Exception e) {
			status = CommonConstants.STATUS_JDBC_ERROR;
			Logger.getLogger(RespondentDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN updateFeedbackStatus()-->"+e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, null);
		}
		return status;
	}
	
	public ArrayList<RespondentPojo> getRespondentDetailsForMail(int feedbackId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<RespondentPojo> respondentList = new ArrayList<RespondentPojo>();
		try {
			connection = getConnection();
		
			preparedStatement = connection.prepareStatement(SqlConstants.SELECT_RESPONDENTS_FOR_MAIL);
			preparedStatement.setInt(1,feedbackId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				RespondentPojo respondent = new RespondentPojo();
				respondent.setRespondentEmailId(resultSet.getString("RespondentEmailId"));
				respondent.setNominatedEmailId(resultSet.getString("EmailId"));
				respondent.setEmpRelation(resultSet.getString("EmpRelation"));
				respondent.setPassword(resultSet.getString("Password"));
				respondent.setRespondentFName(resultSet.getString("FirstName"));
				respondent.setRespondentLName(resultSet.getString("LastName"));
				respondent.setNominatedFirstName(resultSet.getString("NomEmpFName"));
				respondent.setNominatedLastName(resultSet.getString("NomEmpLName"));
				respondentList.add(respondent);
			}

		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN getRespondentDetailsForMail()-->" + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return respondentList;
	}

	@Override
	public ArrayList<RespondentPojo> getrespondentstatus(int feedbackId) {
		
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<RespondentPojo> countList = new ArrayList<RespondentPojo>();
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.SELECT_QUERY_TO_GET_RESPONDENT_STATUS);
			preparedStatement.setInt(1,feedbackId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				RespondentPojo respondentdetails = new RespondentPojo();
				respondentdetails.setRespondentId(resultSet.getInt("Respondentid"));
				respondentdetails.setEmpRelation(resultSet.getString("EmpRelation"));
				respondentdetails.setRespondentFName(resultSet.getString("RespondentFirstName"));
				respondentdetails.setRespondentLName(resultSet.getString("RespondentLastName"));
				respondentdetails.setRespondentStatus(resultSet.getString("FeedbackStatus"));
				respondentdetails.setRespondentEmailId(resultSet.getString("RespondentEmailId"));
				countList.add(respondentdetails);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return countList;
	}
	
	public ArrayList<RespondentPojo> getRespondentSuggestions(String nominatedEmployeeEmailId,int feedbackId,String feedbackStatus) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<RespondentPojo> respondentSugesstionList = new ArrayList<RespondentPojo>();
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.GET_RESPONDENT_SUGGESTIONS);
			preparedStatement.setString(1,nominatedEmployeeEmailId);
			preparedStatement.setString(2,nominatedEmployeeEmailId);
			preparedStatement.setString(3,nominatedEmployeeEmailId);
			preparedStatement.setString(4,nominatedEmployeeEmailId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				
				RespondentPojo respondent = new RespondentPojo();
				respondent.setFeedbackId(feedbackId);
				respondent.setRespondentId(-1);
				respondent.setEmpRelation(resultSet.getString("EmpRelation"));
				respondent.setRespondentEmailId(resultSet.getString("EmailId"));				
				respondent.setRespondentFName(resultSet.getString("EmpFirstName"));
				respondent.setRespondentLName(resultSet.getString("EmpLastName"));
				respondent.setFeedbackStatus(feedbackStatus);
				respondentSugesstionList.add(respondent);
			}
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN getRespondentSuggestions()-->" + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return respondentSugesstionList;
	}
	
	public String getFeedbackStatus(int feedbackId) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String feedbackStatus = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.GET_FEEDBACK_STATUS);
			preparedStatement.setInt(1,feedbackId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				feedbackStatus = resultSet.getString("FeedbackStatus");
			}
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN getFeedbackStatus()-->" + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return feedbackStatus;
	}


	@Override
	public ArrayList<EmployeeMasterPojo> getEmployeeList(String jData) {
		ArrayList<EmployeeMasterPojo> empList=new ArrayList<EmployeeMasterPojo>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder sb=new StringBuilder();
		try {
			connection = getConnection();
			sb.append(SqlConstants.GET_EMPLOYEE_MASTER_LIST);
			sb.append(" AND EmailId like '"+jData+"%'");
			preparedStatement=connection.prepareStatement(sb.toString());
			resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()){
				EmployeeMasterPojo emp=new EmployeeMasterPojo();
				emp.setFirstName(resultSet.getString("EmpFirstName"));
				emp.setLastName(resultSet.getString("EmpLastName"));
				emp.setEmailId(resultSet.getString("EmailId"));
				empList.add(emp);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		
		return empList;
	}
	
	public void updateMailSentToRespondentFlag(int feedbackId, String emailId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.UPDATE_MAIL_SENT_TO_RESPONDENT_FLAG);
			preparedStatement.setInt(1,feedbackId);
			preparedStatement.setString(2,emailId);
			preparedStatement.execute();
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN updateMailSentToRespondentFlag()-->" + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(null, preparedStatement, connection);
		}
	}
	
}
