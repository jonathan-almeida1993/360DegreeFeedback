package com.mahindra.scheduler;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.mahindra.dao.base.impl.FeedbackDAOImpl;
import com.mahindra.dao.base.interfaces.FeedbackDAO;
import com.mahindra.database.pojo.FeedbackPojo;

public class FeedbackClosureScheduler implements Runnable{

	@Override
	public void run() {
		 TimerTask task = new TimerTask() {
			  @Override
			  public void run() {
			    
				try{
			    	System.out.println("<--------Feedback Closure Scheduler-------->");
			    	FeedbackDAO feedbackdao=new FeedbackDAOImpl();
			    	ArrayList<FeedbackPojo> feedBackList = feedbackdao.getFeedbackList();
			    	for(FeedbackPojo feedBack:feedBackList){
			    		feedbackdao.feedbackClosure(feedBack,"System");
			    	}
			    }catch(Exception ex){
			    	
			    }finally{
	
			    }
			  }
		 };
		    
	    Timer timer = new Timer();
	    long delay = 0;
	    long intevalPeriod =1000 * 1*60*60*24; 
	    
	    // schedules the task to be run in an interval 
	    timer.scheduleAtFixedRate(task, delay,intevalPeriod);
	}
}