package com.mahindra.dao.base.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.constants.SqlConstants;
import com.mahindra.dao.base.interfaces.TemplateMasterDAO;
import com.mahindra.database.connection.DBConnectionFactory;
import com.mahindra.database.pojo.QuestionPojo;
import com.mahindra.database.pojo.TemplatePojo;

public class TemplateMasterDAOImpl implements TemplateMasterDAO {

	Connection connection = null;

	@Override
	public Connection getConnection() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException, NamingException {
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		return connection;
	}

	@Override
	public String insertTemplate(TemplatePojo obj) {
		// TODO Auto-generated method stub
		String status = CommonConstants.STATUS_INSERTION_FAILURE;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connect = getConnection();
			connect.setAutoCommit(false);
			//TemplateName,TemplateStatus
			preparedStatement = connect.prepareStatement(SqlConstants.INSERT_QUERY_FOR_TEMPLATEMASTER,PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, obj.getTemplateName());
			preparedStatement.executeUpdate();
			int autoGenPrimaryKey = -1;
			resultSet = preparedStatement.getGeneratedKeys();
			
			while(resultSet.next()){
				autoGenPrimaryKey = resultSet.getInt(1);
			}
			DBConnectionFactory.close(resultSet, preparedStatement, null);
			//TemplateId,QuestionId,SequenceNo,PageNo
			Iterator<QuestionPojo> iterator = obj.getQuesTempMap().iterator();
			preparedStatement = connect.prepareStatement(SqlConstants.INSERT_QUERY_FOR_QUESTION_TEMPLATE_MAP);
			
			while(iterator.hasNext()){
				QuestionPojo singleQuest = iterator.next();
				preparedStatement.setInt(1, autoGenPrimaryKey);
				preparedStatement.setInt(2, Integer.parseInt(singleQuest.getQuesId()));
				preparedStatement.executeUpdate();
			}
			connect.commit();
			status = CommonConstants.STATUS_INSERTION_SUCCESSFULL;

		} catch (Exception e) {
			status = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
			Logger.getLogger(TemplateMasterDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN insertTemplate()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return status;
	}
	
	@Override
	public String updateTemplate(TemplatePojo obj) {
		// TODO Auto-generated method stub
		String status = CommonConstants.STATUS_INSERTION_FAILURE;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connect = getConnection();
			connect.setAutoCommit(false);
			//TemplateName,TemplateStatus
			preparedStatement = connect.prepareStatement(SqlConstants.UPDATE_TEMPLATE_STATUS,PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, obj.getTemplateStatus());
			preparedStatement.setInt(2, Integer.parseInt(obj.getTemplateId()));
			preparedStatement.executeUpdate();

			connect.commit();
			status = CommonConstants.STATUS_EDIT_SUCCESSFULL;

		} catch (Exception e) {
			status = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
			Logger.getLogger(TemplateMasterDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN insertTemplate()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return status;
	}

	public TemplatePojo fetchTemplateDetails(TemplatePojo obj) {
		// TODO Auto-generated method stub
		obj.setStatus(CommonConstants.STATUS_JDBC_ERROR);
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.FETCH_TEMPLATE_DETAILS);
			preparedStatement.setString(1, obj.getTemplateId());
			resultSet = preparedStatement.executeQuery();
			ArrayList<QuestionPojo> questIdList = new ArrayList<QuestionPojo>();
			
			while(resultSet.next()){
				obj.setTemplateName(resultSet.getString("TemplateName"));
				obj.setTemplateStatus(resultSet.getString("TemplateStatus"));
				QuestionPojo singleQuestId = new QuestionPojo();
				singleQuestId.setQuesId(Integer.valueOf(resultSet.getInt("QuestionId")).toString());
				questIdList.add(singleQuestId);
			}
			obj.setQuesTempMap(questIdList);
			obj.setStatus(CommonConstants.STATUS_OK);
			
		} catch (Exception e) {
			obj.setStatus(CommonConstants.STATUS_JDBC_ERROR);
			e.printStackTrace();
			Logger.getLogger(TemplateMasterDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN fetchTemplateDetails()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return obj;
	}
}