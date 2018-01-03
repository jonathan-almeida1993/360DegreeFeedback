package com.mahindra.scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


public class SchedulerListner implements ServletContextListener{
	FeedbackClosureScheduler fcs= new FeedbackClosureScheduler();
	Thread t= new Thread(fcs);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	t.stop();
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	
		t.start();
	}

}
