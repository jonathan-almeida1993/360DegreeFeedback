package com.mahindra.controller;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONObject;

import com.google.gson.Gson;

import com.mahindra.common.utils.CommonUtilities;
import com.mahindra.dao.base.impl.DashboardInfoDaoImpl;
import com.mahindra.database.pojo.DashboardPojo;

public class DashBoardUIController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DashBoardUIController() {
        super();
       
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			DashboardInfoDaoImpl dashServImpl=new DashboardInfoDaoImpl();
	        Gson gson=new Gson();
	        String jsonDataToServer="";
	        String message=request.getParameter("message");
	        JSONObject jsonObj=null;
	        if(message.equals("")){
	        	ArrayList<DashboardPojo> dashInfo= dashServImpl.DashboardInfo(request);
	        	jsonDataToServer = gson.toJson(dashInfo);
	        }
	        else  if(message.equals("Reports")){
	        	ArrayList<DashboardPojo> dashInfo= dashServImpl.DashboardInfoReports(request);
	        	jsonDataToServer = gson.toJson(dashInfo);
	        }
	        else{
	        	jsonObj= new JSONObject(request.getParameter("JDATA"));
	        	String nameSrch=jsonObj.getString("nameSrch");
		        String statSrch=jsonObj.getString("statSrch");
		        String templateSrch=jsonObj.getString("templateSrch");
		        String srchStartDate=jsonObj.getString("srchStartDate");
		        String srchEndDate= jsonObj.getString("srchEndDate");

		        ArrayList<DashboardPojo> dashSrchInfo= dashServImpl.DashboardInfo(message,request,
		        		nameSrch,statSrch,templateSrch,srchStartDate,srchEndDate);
		       
		        if(dashSrchInfo!=null)
	        	jsonDataToServer = gson.toJson(dashSrchInfo);
	        }

	 	    CommonUtilities.SOP(jsonDataToServer);
	 	    response.getWriter().write(jsonDataToServer);
	        
	       
	       
	}

}
