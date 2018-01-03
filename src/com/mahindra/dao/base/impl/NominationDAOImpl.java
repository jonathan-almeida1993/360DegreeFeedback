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

import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.constants.SqlConstants;
import com.mahindra.common.utils.CommonUtilities;
import com.mahindra.dao.base.interfaces.NominationDAO;
import com.mahindra.database.connection.DBConnectionFactory;
import com.mahindra.database.pojo.CompanyPojo;
import com.mahindra.database.pojo.FeedbackPojo;
import com.mahindra.database.pojo.NominationPojo;
import com.mahindra.database.pojo.SectorPojo;
import com.mahindra.database.pojo.TemplatePojo;
import com.mahindra.database.pojo.UserPojo;

public class NominationDAOImpl implements NominationDAO {

	Connection connection = null;

	@Override
	public Connection getConnection() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException, NamingException {
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		return connection;
	}

	public ArrayList<SectorPojo> getSectorList() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<SectorPojo> sectorList = new ArrayList<SectorPojo>();
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.SELECT_SECTOR_LIST);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				SectorPojo sectorDtls = new SectorPojo();
				sectorDtls.setSectorId(resultSet.getString("sectorId") != null ? resultSet.getString("sectorId") : "");
				sectorDtls.setSectorName(resultSet.getString("sectorName") != null ? resultSet.getString("sectorName") : "");
				sectorDtls.setSecAdminEmailId(resultSet.getString("sectorAdminEmailId") != null ? resultSet.getString("sectorAdminEmailId") : "");

				sectorList.add(sectorDtls);
			}
			
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN validateUserLogin()-->" + e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return sectorList;
	}

	public ArrayList<CompanyPojo> getCompanyList(String nominationCat) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<CompanyPojo> compList = new ArrayList<CompanyPojo>();
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.SELECT_SECTOR_BASED_COMPANY_LIST);
			preparedStatement.setString(1, nominationCat);
			resultSet = preparedStatement.executeQuery();
		
			while (resultSet.next()) {
				CompanyPojo sectorDtls = new CompanyPojo();
				sectorDtls.setCompanyId(resultSet.getString("companyId") != null ? resultSet.getString("companyId") : "");
				sectorDtls.setCompanyName(resultSet.getString("compName") != null ? resultSet.getString("compName") : "");
				sectorDtls.setComAdminEmailId(resultSet.getString("compAdminEmailId") != null ? resultSet.getString("compAdminEmailId") : "");

				compList.add(sectorDtls);
			}
			
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN validateUserLogin()-->" + e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return compList;
	}

	public ArrayList<TemplatePojo> getTemplateList() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<TemplatePojo> compList = new ArrayList<TemplatePojo>();
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.SELECT_TEMPLATE_LIST);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				TemplatePojo sectorDtls = new TemplatePojo();
				sectorDtls.setTemplateId(resultSet.getString("templateId") != null ? resultSet.getString("templateId") : "");
				sectorDtls.setTemplateName(resultSet.getString("templateName") != null ? resultSet.getString("templateName") : "");
				sectorDtls.setTemplateStatus(resultSet.getString("templateStatus") != null ? resultSet.getString("templateStatus") : "");

				compList.add(sectorDtls);
			}
			
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN validateUserLogin()-->" + e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return compList;
	}
	
	public ArrayList<TemplatePojo> getTemplateListAll() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<TemplatePojo> compList = new ArrayList<TemplatePojo>();
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.SELECT_TEMPLATE_LIST_ALL);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				TemplatePojo sectorDtls = new TemplatePojo();
				sectorDtls.setTemplateId(resultSet.getString("templateId") != null ? resultSet.getString("templateId") : "");
				sectorDtls.setTemplateName(resultSet.getString("templateName") != null ? resultSet.getString("templateName") : "");
				sectorDtls.setTemplateStatus(resultSet.getString("templateStatus") != null ? resultSet.getString("templateStatus") : "");

				compList.add(sectorDtls);
			}
			
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN validateUserLogin()-->" + e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return compList;
	}
	
	public UserPojo nominateEmployee(NominationPojo obj) {
		Connection connection = null;
		CallableStatement callableStatement = null;
		ResultSet resultSet = null;
		UserPojo statusAndPwd = new UserPojo();
		statusAndPwd.setStatus(CommonConstants.STATUS_JDBC_ERROR);
		
		try {
			connection = getConnection();
			
			callableStatement = connection.prepareCall(SqlConstants.SP_NOMINATE_EMP);
			callableStatement.setString(1,obj.getSource());
			callableStatement.setString(2, obj.getDestination());
			callableStatement.setInt(3,obj.getTemplateId());
			callableStatement.setString(4, CommonUtilities.formatDate(obj.getFromDate()));
			callableStatement.setString(5, CommonUtilities.formatDate(obj.getToDate()));
			callableStatement.setInt(6, obj.getReminderDuration());
			callableStatement.setInt(7, obj.getRequestId());
			callableStatement.setString(8, obj.getDestinationDesc());
			callableStatement.setString(9, obj.getFirstName());
			callableStatement.setString(10, obj.getLastName());
			callableStatement.setInt(11, obj.getSectorId());
			callableStatement.setInt(12, obj.getCompanyId());
			callableStatement.registerOutParameter(13, java.sql.Types.VARCHAR);
			callableStatement.registerOutParameter(14, java.sql.Types.VARCHAR);
			callableStatement.execute();
			
			System.out.print("SOURCE-"+obj.getSource()+"  DESTINATION-"+obj.getDestination()+"  TEMPLATE_ID-"+obj.getTemplateId());
			System.out.print("\nFROM_DATE-"+CommonUtilities.formatDate(obj.getFromDate())+"  TO_DATE-"+CommonUtilities.formatDate(obj.getToDate()));
			System.out.print("\nREMINDER_DURATION-"+obj.getReminderDuration()+"  REQUEST_ID-"+obj.getRequestId());
			System.out.print("\nDESTINATION_DESC-"+obj.getDestinationDesc()+"  FIRST_NAME-"+obj.getFirstName()+"  LAST_NAME-"+obj.getLastName());
			System.out.print("\nSECTOR_ID-"+obj.getSectorId()+"  COMPANY_ID-"+obj.getCompanyId());
			
			statusAndPwd.setStatus(callableStatement.getString(13)); 
			statusAndPwd.setPassword(callableStatement.getString(14)); 
			
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN nominateEmployee()-->" + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, callableStatement, connection);
		}
		return statusAndPwd;
	}
	
	public String extendSurvey(FeedbackPojo obj) {
		Connection connection = null;
		PreparedStatement preparedStatemnet = null;
		ResultSet resultSet = null;
		int stat=0;
		String status = CommonConstants.STATUS_JDBC_ERROR;
		
		try {
			connection = getConnection();
			preparedStatemnet=connection.prepareStatement(SqlConstants.EXTEND_SURVEY_DATE);
			preparedStatemnet.setString(1, obj.getEndDate());
			preparedStatemnet.setInt(2, obj.getFeedbackid());
			stat= preparedStatemnet.executeUpdate();
			if(stat>0)
				status="Extended successfully..!";
			else
				status="Some Error Occured..!";
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN nominateEmployee()-->" + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatemnet, connection);
		}
		return status;
	}
	
	public ArrayList<NominationPojo> fetchPendingRequest(UserPojo obj) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<NominationPojo> requestList = new ArrayList<NominationPojo>();
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.SELECT_PENDING_REQUESTS);
			preparedStatement.setString(1, "PENDING_WITH_"+obj.getRole());
			preparedStatement.setString(2, obj.getEmailId());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				NominationPojo requestDetails = new NominationPojo();
				requestDetails.setFeedbackName(resultSet.getString("TemplateName") );
				requestDetails.setRequestId(Integer.parseInt(resultSet.getString("RequestId")));
				requestList.add(requestDetails);
			}
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN fetchPendingRequest()-->" + e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return requestList;
	}

	public UserPojo fetchDetailsForEmp(NominationPojo obj) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UserPojo empDetails = new UserPojo();
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.SELECT_EMP_DETAILS);
			preparedStatement.setString(1, obj.getDestination());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				empDetails.setFirstName(resultSet.getString("FirstName"));
				empDetails.setLastName(resultSet.getString("LastName"));
				empDetails.setPassword(resultSet.getString("Password"));
			}
			
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN fetchDetailsForEMP()-->" + e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return empDetails;
	}
}
