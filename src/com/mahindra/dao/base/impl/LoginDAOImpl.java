package com.mahindra.dao.base.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.constants.SqlConstants;
import com.mahindra.dao.base.interfaces.LoginDAO;
import com.mahindra.database.connection.DBConnectionFactory;
import com.mahindra.database.pojo.UserPojo;

public class LoginDAOImpl implements LoginDAO {

	Connection connection = null;

	@Override
	public Connection getConnection() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException, NamingException {
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		return connection;
	}
	
	public UserPojo validateUserLogin(UserPojo loginDetails){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UserPojo userDetails = new UserPojo();
		userDetails.setStatus(CommonConstants.STATUS_JDBC_ERROR);
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.AUTHENTICATE_USER);
			//FirstName,LastName,EmailId,Role 
			preparedStatement.setString(1, loginDetails.getEmailId());
			preparedStatement.setString(2, loginDetails.getPassword());
			resultSet = preparedStatement.executeQuery();
			int count = 0;
		
			while(resultSet.next()){
				userDetails.setFirstName(resultSet.getString("FirstName"));
				userDetails.setLastName(resultSet.getString("LastName"));
				userDetails.setEmailId(resultSet.getString("EmailId"));
				userDetails.setPwdChanged(resultSet.getBoolean("isPwdChanged"));
				System.out.println("PWD changed flag - "+userDetails.isPwdChanged());
				userDetails.setRole(resultSet.getString("Role"));
				userDetails.setSectorId(resultSet.getInt("SectorId"));
				userDetails.setCompanyId(resultSet.getInt("CompanyId"));
				userDetails.setSectorName(resultSet.getString("SectorName")==null?"":resultSet.getString("SectorName"));
				userDetails.setCompanyName(resultSet.getString("CompName")==null?"":resultSet.getString("CompName"));
				count++;
			}
			
			if(count == 0){
				userDetails.setStatus(CommonConstants.STATUS_AUTH_FAILED);
			}else if(count == 1){
				userDetails.setStatus(CommonConstants.STATUS_AUTH_SUCCESS);
			}
			
		} catch (Exception e) {
			userDetails.setStatus(CommonConstants.STATUS_JDBC_ERROR);
			Logger.getLogger(LoginDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN validateUserLogin()-->"+e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return userDetails;
	}
	public UserPojo changePasswordRequest(String emailId){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UserPojo userDetails = new UserPojo();
		userDetails.setStatus(CommonConstants.STATUS_JDBC_ERROR);
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.CHANGE_PWD_REQ);
			//FirstName,LastName,Role,Password
			preparedStatement.setString(1, emailId);
			resultSet = preparedStatement.executeQuery();
			int count = 0;
			
			while(resultSet.next()){
				userDetails.setFirstName(resultSet.getString("FirstName"));
				userDetails.setLastName(resultSet.getString("LastName"));
				userDetails.setRole(resultSet.getString("Role"));
				userDetails.setPassword(resultSet.getString("Password"));
				count++;
			}
			
			if(count == 0){
				userDetails.setStatus(CommonConstants.STATUS_INVALID_EMAIL_ID);
			}else if(count == 1){
				userDetails.setStatus(CommonConstants.STATUS_OK);
			}
		} catch (Exception e) {
			userDetails.setStatus(CommonConstants.STATUS_JDBC_ERROR);
			Logger.getLogger(LoginDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN changePasswordRequest()-->"+e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		
		return userDetails;
	}
	
	public void resetPassword(String emailId,int pwdChangedFlag,String encryptedPwd){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.RESET_PWD_REQ);
			//FirstName,LastName,Role,Password
			preparedStatement.setString(1, encryptedPwd);
			preparedStatement.setInt(2, pwdChangedFlag);
			preparedStatement.setString(3, emailId);
			preparedStatement.execute();
			
		} catch (Exception e) {
			Logger.getLogger(LoginDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN resetPassword()-->"+e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
	}
	
}
