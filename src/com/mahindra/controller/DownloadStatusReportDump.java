package com.mahindra.controller;

import java.awt.Font;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mahindra.common.utils.CommonUtilities;
import com.mahindra.dao.base.impl.DashboardInfoDaoImpl;
import com.mahindra.dao.base.interfaces.DashboardInfoDao;
import com.mahindra.database.pojo.DashboardPojo;



public class DownloadStatusReportDump extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		  CommonUtilities.SOP("STATUS REPORT DOWNLOAD REQUEST RECEIVED...");
		  HSSFWorkbook workbook = new HSSFWorkbook(); 
	      HSSFSheet spreadsheet = workbook.createSheet("StatusReport");
	      HSSFRow row=spreadsheet.createRow(0);
	      HSSFCell cell;

	      HSSFDataFormat dataFormat = workbook.createDataFormat();
	      CellStyle cellStyle = workbook.createCellStyle();
	      cellStyle.setDataFormat(dataFormat.getFormat("d-mmm-yy"));
	      
	      cell=row.createCell(0);
	      cell.setCellValue("FeedbackId");
	      cell=row.createCell(1);
	      cell.setCellValue("EmailId");
	      cell=row.createCell(2);
	      cell.setCellValue("FirstName");
	      cell=row.createCell(3);
	      cell.setCellValue("LastName");
	      cell=row.createCell(4);
	      
	      cell.setCellValue("StartDate");
	      cell=row.createCell(5);
	      
	      cell.setCellValue("EndDate");
	      cell=row.createCell(6);
	      cell.setCellValue("FeedbackStatus");
	      cell=row.createCell(7);
	      cell.setCellValue("RespondentId");
	      cell=row.createCell(8);
	      cell.setCellValue("EmpRelation");
	      cell=row.createCell(9);
	      cell.setCellValue("RespondentEmailId");
	      cell=row.createCell(10);
	      cell.setCellValue("RespondentFirstName");
	      cell=row.createCell(11);
	      cell.setCellValue("RespondentLastName");
	      cell=row.createCell(12);
	      cell.setCellValue("FeedbackStatus");

	      DashboardInfoDao reportDao = new DashboardInfoDaoImpl();
	      ArrayList<DashboardPojo> reportRecordList = reportDao.statusReportDump();
	      Iterator<DashboardPojo> iterate = reportRecordList.iterator();
	      
	      int i=1; 
	      while(iterate.hasNext())
	      {
	    	 DashboardPojo singleRecord = iterate.next();
	         row=spreadsheet.createRow(i);
	         cell=row.createCell(0);
	         cell.setCellValue(singleRecord.getFeedBackId());
	         cell=row.createCell(1);
	         cell.setCellValue(singleRecord.getEmailId());
	         cell=row.createCell(2);
	         cell.setCellValue(singleRecord.getEmpFirstName());
	         cell=row.createCell(3);
	         cell.setCellValue(singleRecord.getEmpLastName());
	         cell=row.createCell(4);
	         cell.setCellStyle(cellStyle);
	         cell.setCellValue(singleRecord.getStartDate());
	         cell=row.createCell(5);
	         cell.setCellStyle(cellStyle);
	         cell.setCellValue(singleRecord.getEndDate());
	         cell=row.createCell(6);
	         cell.setCellValue(singleRecord.getStatus());
	         cell=row.createCell(7);
	         cell.setCellValue(singleRecord.getRespondentId());
	         cell=row.createCell(8);
	         cell.setCellValue(singleRecord.getEmpRelation());
	         cell=row.createCell(9);
	         cell.setCellValue(singleRecord.getRespondentEmailId());
	         cell=row.createCell(10);
	         cell.setCellValue(singleRecord.getRespondentFirstName());
	         cell=row.createCell(11);
	         cell.setCellValue(singleRecord.getRespondentLastName());
	         cell=row.createCell(12);
	         cell.setCellValue(singleRecord.getRespondentFeedbackStatus());
	         i++;
	      }
	      
	      spreadsheet.autoSizeColumn(0);
	      spreadsheet.autoSizeColumn(1);
	      spreadsheet.autoSizeColumn(2);
	      spreadsheet.autoSizeColumn(3);
	      spreadsheet.autoSizeColumn(4);
	      spreadsheet.autoSizeColumn(5);
	      spreadsheet.autoSizeColumn(6);
	      spreadsheet.autoSizeColumn(7);
	      spreadsheet.autoSizeColumn(8);
	      spreadsheet.autoSizeColumn(9);
	      spreadsheet.autoSizeColumn(10);
	      spreadsheet.autoSizeColumn(11);
	      spreadsheet.autoSizeColumn(12);
	      
	      
	      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   //get current date time with Date()
		   Date date = new Date();
		   System.out.println(dateFormat.format(date));
	      
	      response.setContentType("application/vnd.ms-excel");
	      response.addHeader("content-disposition",
	                "attachment; filename=360DegreeStatusReportDump"+dateFormat.format(date).replace(" ", "_")+".xls");
	      ServletOutputStream out = response.getOutputStream();
	      workbook.write(out);
	      out.close();

	      CommonUtilities.SOP("STATUS REPORT DOWNLOADED SUCCESSFULLY...");
	}

}



