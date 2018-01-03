package com.mahindra.dao.base.interfaces;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.mahindra.dao.base.GenericDAO;
import com.mahindra.database.pojo.DashboardPojo;

public interface DashboardInfoDao extends GenericDAO{
	
	public ArrayList<DashboardPojo> DashboardInfo(HttpServletRequest request);

	public ArrayList<DashboardPojo> feedbackDashList(HttpServletRequest request);
	
	public ArrayList<DashboardPojo> DashboardInfo(String message,
			HttpServletRequest request,String nameSrch,String statSrch, String templateSrch,
			String srchStartDate, String srchEndDate);
	
	public ArrayList<DashboardPojo> DashboardInfoReports(HttpServletRequest request);

	public ArrayList<DashboardPojo> statusReportDump() ;


}
