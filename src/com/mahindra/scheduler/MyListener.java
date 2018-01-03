package com.mahindra.scheduler;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mahindra.common.DailyPendingMainJob;

public class MyListener implements ServletContextListener {

	private static final int INTERVAL = 60 * 60 * 1000;

	/** the interval to wait before starting up the service - 10 seconds */
	private static final int STARTUP_WAIT = 10 * 1000;
	private ScheduledExecutorService scheduler;
	private Timer myTimer = new Timer();

	public void contextDestroyed(ServletContextEvent sce) {
//		 service.shutdown();
	}

	public void contextInitialized(ServletContextEvent sce) {

		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 15);
		today.set(Calendar.MINUTE, 15);
		today.set(Calendar.SECOND, 0);
		
		System.out.println("CRON STARTED" +today.getTime());
		System.out.println("CRON ENDS...");
	}
}
