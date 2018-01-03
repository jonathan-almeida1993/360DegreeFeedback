package com.mahindra.dao.base.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import com.mahindra.dao.base.interfaces.QuestionMasterDAO;
import com.mahindra.database.connection.DBConnectionFactory;
import com.mahindra.database.pojo.QuestionPojo;
import com.mahindra.database.pojo.TemplatePojo;

public class QuestionMasterDAOImpl implements QuestionMasterDAO {

	Connection connection = null;

	@Override
	public Connection getConnection() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException, NamingException {
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		return connection;
	}

	
	@Override
	public String insertQuestions(QuestionPojo obj) {
		// TODO Auto-generated method stub
		String status = CommonConstants.STATUS_INSERTION_FAILURE;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connect = getConnection();
			//QuestDesc,QuestType,QuestTagLine,OptionValues
			preparedStatement = connect.prepareStatement(SqlConstants.INSERT_QUERY_FOR_QUESTIONMASTER);
			preparedStatement.setString(1, obj.getQuesCatId());
			preparedStatement.setString(2, obj.getQuesDesc());
			preparedStatement.setString(3, obj.getQuesType());
			preparedStatement.setString(4, obj.getQuesTagLine());
			preparedStatement.setString(5, obj.getQuesOptValues());
			preparedStatement.setBoolean(6, obj.isNegativeQuest());
			System.out.println("------->"+preparedStatement);
			int executeUpdate = preparedStatement.executeUpdate();

			if (executeUpdate > 0) {
				status = CommonConstants.STATUS_INSERTION_SUCCESSFULL;
			}

		} catch (Exception e) {
			status = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
			Logger.getLogger(QuestionMasterDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN insertQuestions()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return status;
	}

	@Override
	public ArrayList<QuestionPojo> selectQuestions(QuestionPojo obj) {
		// TODO Auto-generated method stub

		ArrayList<QuestionPojo> list = new ArrayList<QuestionPojo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
			if (obj!= null && obj.getQuesId() != null && !obj.getQuesId().equals("")) {
				System.out.println("@--------------- select single ------------@");
				pstmt = conn.prepareStatement(SqlConstants.SELECT_QUERY_FOR_QUESTIONMASTER);
				pstmt.setString(1, obj.getQuesId());

			} else {
				System.out.println("@--------------- select all------------@");
				//pstmt = conn.prepareStatement(SqlConstants.SELECT_ALL_QUERY_FOR_QUESTIONMASTER);
				pstmt = conn.prepareStatement(SqlConstants.SELECT_TAMPLATE_REL_QUE);
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {

				QuestionPojo appPojo = new QuestionPojo();
				appPojo.setQuesId(String.valueOf((rs.getInt("QuestionId"))));
				appPojo.setQuesCatId((rs.getString("QuestCatId") != null ? rs.getString("QuestCatId") : ""));
				appPojo.setQuesDesc((rs.getString("QuestDesc") != null ? rs.getString("QuestDesc") : ""));
				appPojo.setQuesType((rs.getString("QuestType") != null ? rs.getString("QuestType") : ""));
				appPojo.setQuesTagLine((rs.getString("QuestTagLine") != null ? rs.getString("QuestTagLine") : ""));
				appPojo.setQuesOptValues((rs.getString("OptionValues") != null ? rs.getString("OptionValues") : ""));
				appPojo.setQuesStatus((rs.getString("Status") != null ? rs.getString("Status") : ""));
				appPojo.setQuesCat((rs.getString("QuestCategory") != null ? rs.getString("QuestCategory") : ""));
				appPojo.setQuesSubCat((rs.getString("QuestSubCategory") != null ? rs.getString("QuestSubCategory") : ""));
				appPojo.setNegativeQuest(rs.getBoolean("NegativeQuestionFlag"));
				list.add(appPojo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(QuestionMasterDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN selectQuestions()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(rs,pstmt,conn);
		}
		return list;
	}

	@Override
	public ArrayList<QuestionPojo> selectQuestions(QuestionPojo obj,TemplatePojo templateId) {
		// TODO Auto-generated method stub

		ArrayList<QuestionPojo> list = new ArrayList<QuestionPojo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			
			if (obj!= null && obj.getQuesId() != null && !obj.getQuesId().equals("")) {
				System.out.println("@--------------- select single ------------@");
				pstmt = conn.prepareStatement(SqlConstants.SELECT_QUERY_FOR_QUESTIONMASTER);
				pstmt.setString(1, obj.getQuesId());

			}else if(templateId!= null && templateId.getTemplateId() != null && !templateId.getTemplateId().equals("")){
				System.out.println("@--------------- select all------------@");
				pstmt = conn.prepareStatement(SqlConstants.SELECT_TAMPLATE_REL_QUE);
				pstmt.setString(1, templateId.getTemplateId());
			}else{
				System.out.println("@--------------- select all------------@");
				pstmt = conn.prepareStatement(SqlConstants.SELECT_ALL_QUERY_FOR_QUESTIONMASTER);
			}
			
			rs = pstmt.executeQuery();

			while (rs.next()) {

				QuestionPojo appPojo = new QuestionPojo();
				appPojo.setQuesId(String.valueOf((rs.getInt("QuestionId"))));
				appPojo.setQuesCatId((rs.getString("QuestCatId") != null ? rs.getString("QuestCatId") : ""));
				appPojo.setQuesDesc((rs.getString("QuestDesc") != null ? rs.getString("QuestDesc") : ""));
				appPojo.setQuesType((rs.getString("QuestType") != null ? rs.getString("QuestType") : ""));
				appPojo.setQuesTagLine((rs.getString("QuestTagLine") != null ? rs.getString("QuestTagLine") : ""));
				appPojo.setQuesOptValues((rs.getString("OptionValues") != null ? rs.getString("OptionValues") : ""));
				appPojo.setQuesStatus((rs.getString("Status") != null ? rs.getString("Status") : ""));
				appPojo.setQuesCat((rs.getString("QuestCategory") != null ? rs.getString("QuestCategory") : ""));
				appPojo.setQuesSubCat((rs.getString("QuestSubCategory") != null ? rs.getString("QuestSubCategory") : ""));
				appPojo.setNegativeQuest(rs.getBoolean("NegativeQuestionFlag"));
				list.add(appPojo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(QuestionMasterDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN selectQuestions()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(rs,pstmt,conn);
		}
		return list;
	}
	
	@Override
	public String deleteQuestions(QuestionPojo obj){
		// TODO Auto-generated method stub
		String retFlag = CommonConstants.STATUS_DELETE_FAILURE;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.DELETE_QUERY_FOR_QUESTIONMASTER);
			preparedStatement.setString(1, obj.getQuesId());

			int executeUpdate = preparedStatement.executeUpdate();

			if (executeUpdate > 0) {
				retFlag = CommonConstants.STATUS_DELETE_SUCCESSFULL;
			}
		} catch (Exception e) {
			retFlag = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
			Logger.getLogger(QuestionMasterDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN deleteQuestions()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return retFlag;
	}


	@Override
	public String updateQuestions(QuestionPojo obj) {
		// TODO Auto-generated method stub
		String retFlag = CommonConstants.STATUS_EDIT_FAILURE;
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.UPDATE_QUERY_FOR_QUESTIONMASTER);
			preparedStatement.setString(1, obj.getQuesDesc());
			preparedStatement.setBoolean(2, obj.isNegativeQuest());
			preparedStatement.setString(3, obj.getQuesStatus());
			preparedStatement.setString(4, obj.getQuesId());
			int executeUpdate = preparedStatement.executeUpdate();

			if (executeUpdate > 0) {
				retFlag = CommonConstants.STATUS_EDIT_SUCCESSFULL;
			}

		} catch (Exception e) {
			retFlag = CommonConstants.STATUS_JDBC_ERROR;
			e.printStackTrace();
			Logger.getLogger(QuestionMasterDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN editQuestions()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		return retFlag;
	}
	
public ArrayList<QuestionPojo> getCategoryList() {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<QuestionPojo> categoryList = new ArrayList<QuestionPojo>();
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.SELECT_QUERY_FOR_CATEGORY_LIST);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				
				QuestionPojo category = new QuestionPojo(); 
				
				category.setQuesCat(resultSet.getString("questcategory"));
				
				System.out.println("Category ->" +category.getQuesCat());
	
				categoryList.add(category);
			}
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN validateUserLogin()-->" + e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return categoryList;
	}

	@Override
	public ArrayList<QuestionPojo> getSubCategoryList(QuestionPojo obj) {
		
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<QuestionPojo> subcategoryList = new ArrayList<QuestionPojo>();
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.SELECT_QUERY_FOR_SUBCATEGRY_LIST);
			preparedStatement.setString(1,(obj.getQuesCat()));
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				
				QuestionPojo subcategory = new QuestionPojo(); 
				
				subcategory.setQuesSubCat(resultSet.getString("questsubcategory"));
				subcategory.setQuesCatId(resultSet.getString("questCatid"));
	
				subcategoryList.add(subcategory);
			}
			
		} catch (Exception e) {
			Logger.getLogger(NominationDAOImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN validateUserLogin()-->" + e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}
		return subcategoryList;
	}

}
