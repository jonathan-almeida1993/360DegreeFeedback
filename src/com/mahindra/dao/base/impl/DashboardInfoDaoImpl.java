package com.mahindra.dao.base.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.constants.SqlConstants;
import com.mahindra.dao.base.interfaces.DashboardInfoDao;
import com.mahindra.database.connection.DBConnectionFactory;
import com.mahindra.database.pojo.DashboardPojo;
import com.mahindra.database.pojo.FeedbackPojo;

public class DashboardInfoDaoImpl implements DashboardInfoDao{
	
	Connection connection=null;
	
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
	public ArrayList<DashboardPojo> DashboardInfo(String message,HttpServletRequest request,String nameSrch,String statSrch,
			String templateSrch, String srchStartDate, String srchEndDate) {
		
		Connection connection=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		StringBuilder sb= new StringBuilder();
		sb.append((SqlConstants.DASHBOARDSRCH).toString());
        HttpSession session = request.getSession(false);
        String role=String.valueOf(session.getAttribute(CommonConstants.SESSION_ROLE));
        String sectorId=String.valueOf(session.getAttribute(CommonConstants.SESSION_SECTOR_ID));
        String companyId=String.valueOf(session.getAttribute(CommonConstants.SESSION_COMPANY_ID));
        		
		ArrayList<DashboardPojo> dashPojoList=new ArrayList<DashboardPojo>();
		
		if(!role.equals("EMP")){
		try{
			connection=getConnection();
		
			if(!nameSrch.trim().equals("")){
				sb.append(" AND (amt.FirstName Like '"+nameSrch+"%' OR amt.LastName Like '"+nameSrch+"%')");
			}
			if(!statSrch.trim().equals("")){
				sb.append(" AND fmt.FeedbackStatusToDisplay like '"+statSrch+"%'");
			}
			if(!templateSrch.trim().equals("")){
				sb.append(" AND tmt.TemplateName like '"+templateSrch+"%'");
			}
			
			if(role.equals("SEA")){
				sb.append(" AND amt.SectorId='"+sectorId+"'");
			}
			else if(role.equals("COA")){
				sb.append(" AND amt.CompanyId='"+companyId+"'");
			}
			
			if(!srchStartDate.equals("")){
				sb.append(" AND fmt.StartDate='"+srchStartDate+"'");
			}
			else if(!srchEndDate.equals("")){
				sb.append(" AND fmt.EndDate='"+srchEndDate+"'");
			}
			else if(!srchEndDate.equals("") && !srchStartDate.equals("")){
				sb.append(" AND fmt.StartDate AND fmt.EndDate='"+srchEndDate+"'");
			}
			sb.append(" AND fmt.FeedBackStatus not IN ('PENDING_WITH_COA','PENDING_WITH_SEA')");
			
			sb.append(" group by fmt.FeedbackId,tmt.TemplateName,fmt.StartDate,fmt.EndDate,fmt.FeedbackStatusToDisplay,amt.FirstName,amt.LastName,fmt.closedStatus");
			st = connection.prepareStatement(sb.toString());
			
			
			System.out.println(st);
			rs=st.executeQuery();
			while(rs.next()){
				
				DashboardPojo dashPojo=new DashboardPojo();
				
				if(rs.getString("Fname")!=null || rs.getString("Lname")!=null)
				dashPojo.setName(rs.getString("Fname")+" "+rs.getString("Lname"));
				
				if(rs.getInt("id")>0)
				dashPojo.setFeedBackId(rs.getInt("id"));
				
				if(rs.getString("templateName")!=null)
				dashPojo.setTemplateName(rs.getString("templateName"));
				
				if(rs.getDate("startDate")!=null)
				dashPojo.setStartDate(rs.getDate("startDate"));
				
				if(rs.getDate("EndDate")!=null)
				dashPojo.setEndDate(rs.getDate("EndDate"));
				
				if(rs.getInt("NoOfRespondents")>=0)
				dashPojo.setNoOfRespondents(rs.getInt("NoOfRespondents"));
				
				if(rs.getInt("NoOfResponses")>=0)
				dashPojo.setNoOfRespponsesReceived(rs.getInt("NoOfResponses"));
				
				if(rs.getInt("selfFeedback")>=0)
				dashPojo.setSelfFeedback(rs.getInt("selfFeedback")==1?"Yes":"No");
				
				if(rs.getString("status")!=null)
				dashPojo.setStatus(rs.getString("status"));
				
				if(rs.getString("closedStatus")!=null)
					dashPojo.setClosedStatus(rs.getBoolean("closedStatus"));
				
				dashPojoList.add(dashPojo);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			DBConnectionFactory.close(rs, st, connection);
		}
		
		}
		return dashPojoList;
	}

		
		
	@Override
	public ArrayList<DashboardPojo> DashboardInfo(HttpServletRequest request) {
		
		Connection connection=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		String str=" group by fmt.FeedbackId,tmt.TemplateName,fmt.StartDate,fmt.EndDate,"
				+ "fmt.FeedbackStatusToDisplay,amt.FirstName,amt.LastName,fmt.closedStatus";
		StringBuilder sb= new StringBuilder();
        HttpSession session = request.getSession(false);
        String role=String.valueOf(session.getAttribute(CommonConstants.SESSION_ROLE));
        String sectorId=String.valueOf(session.getAttribute(CommonConstants.SESSION_SECTOR_ID));
        String companyId=String.valueOf(session.getAttribute(CommonConstants.SESSION_COMPANY_ID));
        		
		ArrayList<DashboardPojo> dashPojoList=new ArrayList<DashboardPojo>();
		
		try{
			connection=getConnection();
			sb.append(SqlConstants.DASHBOARDINFO);
			sb.append(" where fmt.FeedBackStatus not IN ('PENDING_WITH_COA','PENDING_WITH_SEA') ");
	    
			if(role.equals("SEA")){
	        	sb.append(" AND SectorId="+sectorId);
	        }else if(role.equals("COA")){
	        	sb.append(" AND CompanyId="+companyId);
	        }
		
	        sb.append(str);
			st = connection.prepareStatement(sb.toString());
			System.out.println(st);
			rs=st.executeQuery();
			
			while(rs.next()){
				
				DashboardPojo dashPojo=new DashboardPojo();
				
				if(rs.getString("Fname")!=null || rs.getString("Lname")!=null)
				dashPojo.setName(rs.getString("Fname")+" "+rs.getString("Lname"));
				
				if(rs.getInt("id")>0)
				dashPojo.setFeedBackId(rs.getInt("id"));
				
				if(rs.getString("templateName")!=null)
				dashPojo.setTemplateName(rs.getString("templateName"));
				
				if(rs.getDate("startDate")!=null)
				dashPojo.setStartDate(rs.getDate("startDate"));
				
				if(rs.getDate("EndDate")!=null)
				dashPojo.setEndDate(rs.getDate("EndDate"));
				
				if(rs.getInt("NoOfRespondents")>=0)
				dashPojo.setNoOfRespondents(rs.getInt("NoOfRespondents"));
				
				if(rs.getInt("NoOfResponses")>=0)
				dashPojo.setNoOfRespponsesReceived(rs.getInt("NoOfResponses"));
				
				if(rs.getInt("selfFeedback")>=0)
				dashPojo.setSelfFeedback(rs.getInt("selfFeedback")==1?"Yes":"No");
				
				if(rs.getString("status")!=null)
				dashPojo.setStatus(rs.getString("status"));
				
				if(rs.getString("closedStatus")!=null)
					dashPojo.setClosedStatus(rs.getBoolean("closedStatus"));
				
			
				dashPojoList.add(dashPojo);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			DBConnectionFactory.close(rs, st, connection);
		}
	


		return dashPojoList;
	}
		
		
	@Override
	public ArrayList<DashboardPojo> feedbackDashList(HttpServletRequest request) {
		Connection connection=null;
		PreparedStatement st=null;
		ResultSet rs=null;
		
		
        HttpSession session = request.getSession(false);
        String role=String.valueOf(session.getAttribute(CommonConstants.SESSION_ROLE));
        String sectorId=String.valueOf(session.getAttribute(CommonConstants.SESSION_SECTOR_ID));
        String companyId=String.valueOf(session.getAttribute(CommonConstants.SESSION_COMPANY_ID));
        StringBuilder sb= new StringBuilder();
		ArrayList<DashboardPojo> dashPojoList=new ArrayList<DashboardPojo>();
		sb.append(SqlConstants.DASHBOARDINFO);
		sb.append(" where 1=1 ");
		try{
			connection=getConnection();
			
			if(role.equals("SEA")){
	        	sb.append(" and amt.SectorId='"+sectorId+"'");
	        }
	        else if(role.equals("COA")){
	        	sb.append(" and amt.CompanyId='"+companyId+"'");
	        }
			sb.append(" AND (fmt.closedStatus is null or fmt.closedStatus='0')");
			
			sb.append(" group by fmt.FeedbackId,tmt.TemplateName,fmt.StartDate,fmt.EndDate,fmt.FeedbackStatusToDisplay,amt.FirstName,amt.LastName,fmt.closedStatus");
			st=connection.prepareStatement(sb.toString());
		
			System.out.println(st);
			rs=st.executeQuery();
			while(rs.next()){
				
				DashboardPojo dashPojo=new DashboardPojo();
				
				if(rs.getString("Fname")!=null || rs.getString("Lname")!=null)
				dashPojo.setName(rs.getString("Fname")+" "+rs.getString("Lname"));
				
				if(rs.getInt("id")>0)
				dashPojo.setFeedBackId(rs.getInt("id"));
				
				if(rs.getString("templateName")!=null)
				dashPojo.setTemplateName(rs.getString("templateName"));
				
				if(rs.getDate("startDate")!=null)
				dashPojo.setStartDate(rs.getDate("startDate"));
				
				if(rs.getDate("EndDate")!=null)
				dashPojo.setEndDate(rs.getDate("EndDate"));
				
				if(rs.getInt("NoOfRespondents")>=0)
				dashPojo.setNoOfRespondents(rs.getInt("NoOfRespondents"));
				
				if(rs.getInt("NoOfResponses")>=0)
				dashPojo.setNoOfRespponsesReceived(rs.getInt("NoOfResponses"));
				
				if(rs.getInt("selfFeedback")>=0)
				dashPojo.setSelfFeedback(rs.getInt("selfFeedback")==1?"Yes":"No");
				
				if(rs.getString("status")!=null)
				dashPojo.setStatus(rs.getString("status"));
				
				dashPojoList.add(dashPojo);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			DBConnectionFactory.close(rs, st, connection);
		}
	


		return dashPojoList;
	}

	@Override
	public ArrayList<DashboardPojo> DashboardInfoReports(
			HttpServletRequest request) {
		
		Connection connection=null;
		PreparedStatement st=null;
		Statement stmt=null;
		ResultSet rs=null,rs1=null;
		String str=" group by fmt.FeedbackId,tmt.TemplateName,fmt.StartDate,fmt.EndDate,"
				+ "fmt.FeedbackStatusToDisplay,amt.FirstName,amt.LastName,fmt.closedStatus,rprtStat.SELF,rprtStat.SENIOR";
		StringBuilder sb=new StringBuilder();
        HttpSession session = request.getSession(false);
        String role=String.valueOf(session.getAttribute(CommonConstants.SESSION_ROLE));
        String sectorId=String.valueOf(session.getAttribute(CommonConstants.SESSION_SECTOR_ID));
        String companyId=String.valueOf(session.getAttribute(CommonConstants.SESSION_COMPANY_ID));
        		
		ArrayList<DashboardPojo> dashPojoList=new ArrayList<DashboardPojo>();
		
		
		try{
			
			connection=getConnection();
			sb.append(SqlConstants.DASHBOARDINFOREPORTS);
			sb.append(" where fmt.FeedBackStatus not IN ('PENDING_WITH_COA','PENDING_WITH_SEA') ");
			
			if(role.equals("SEA")){
		       	sb.append(" AND SectordId="+sectorId);
		    }else if(role.equals("COA")){
		       	sb.append(" AND CompanyId="+companyId);
		    }
			  
			sb.append(str);
			st=connection.prepareStatement(sb.toString());
			System.out.println(st);
			rs=st.executeQuery();
		
			while(rs.next()){
				
				DashboardPojo dashPojo=new DashboardPojo();
				
				if(rs.getString("Fname")!=null || rs.getString("Lname")!=null)
				dashPojo.setName(rs.getString("Fname")+" "+rs.getString("Lname"));
				
				if(rs.getInt("id")>0)
				dashPojo.setFeedBackId(rs.getInt("id"));
				
				if(rs.getString("templateName")!=null)
				dashPojo.setTemplateName(rs.getString("templateName"));
				
				if(rs.getDate("startDate")!=null)
				dashPojo.setStartDate(rs.getDate("startDate"));
				
				if(rs.getDate("EndDate")!=null)
				dashPojo.setEndDate(rs.getDate("EndDate"));
				
				if(rs.getInt("NoOfRespondents")>=0)
				dashPojo.setNoOfRespondents(rs.getInt("NoOfRespondents"));
				
				if(rs.getInt("NoOfResponses")>=0)
				dashPojo.setNoOfRespponsesReceived(rs.getInt("NoOfResponses"));
				
				if(rs.getInt("selfFeedback")>=0)
				dashPojo.setSelfFeedback(rs.getInt("selfFeedback")==1?"Yes":"No");
				
				if(rs.getString("status")!=null)
				dashPojo.setStatus(rs.getString("status"));
				
				if(rs.getString("closedStatus")!=null)
					dashPojo.setClosedStatus(rs.getBoolean("closedStatus"));
				
				Boolean flag=false;
				if(rs.getInt("SELF")==1 && rs.getInt("SENIOR")>=1){
					flag=true;
				}else
					flag=false;
					
				dashPojo.setReportFlag(flag);
				dashPojoList.add(dashPojo);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			DBConnectionFactory.close(rs, st, connection);
		}
	


		return dashPojoList;
	}

	
	public ArrayList<DashboardPojo> statusReportDump() {

		ArrayList<DashboardPojo> recordList= new ArrayList<DashboardPojo>();
		Connection connect= null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connect = getConnection();
			preparedStatement = connect.prepareStatement(SqlConstants.GET_STATUS_REPORT_DUMP);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				DashboardPojo singleRecord = new DashboardPojo();
				singleRecord.setFeedBackId(resultSet.getInt("FeedbackId"));
				singleRecord.setEmailId(resultSet.getString("EmailId"));
				singleRecord.setEmpFirstName(resultSet.getString("FirstName"));
				singleRecord.setEmpLastName(resultSet.getString("LastName"));
				singleRecord.setStartDate(resultSet.getDate("StartDate"));
				singleRecord.setEndDate(resultSet.getDate("EndDate"));
				singleRecord.setStatus(resultSet.getString("FeedBackStatus"));
				singleRecord.setRespondentId(resultSet.getInt("RespondentId"));
				singleRecord.setEmpRelation(resultSet.getString("EmpRelation"));
				singleRecord.setRespondentEmailId(resultSet.getString("RespondentEmailId"));
				singleRecord.setRespondentFirstName(resultSet.getString("RespondentFirstName"));
				singleRecord.setRespondentLastName(resultSet.getString("RespondentlastName"));
				singleRecord.setRespondentFeedbackStatus(resultSet.getString("FeedbackStatus"));
				recordList.add(singleRecord);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(DashboardInfoDaoImpl.class.getName()).log(Level.SEVERE, "ERROR OCCURED IN statusReportDump()-->"+e.getMessage());
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connect);
		}
		
		return recordList;
	}
}
