package listeners;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Jobs.Tarea;
import Jobs.TareaLibor;









/**
 * Application Lifecycle Listener implementation class QuartzListener
 *
 */

@WebListener
public class QuartzListener extends QuartzInitializerListener implements ServletContextListener{
	
	 private static final Logger LOG = LoggerFactory.getLogger(QuartzListener.class);
       
	 @Override
	    public void contextInitialized(ServletContextEvent sce) {
	        super.contextInitialized(sce);
	        ServletContext ctx = sce.getServletContext();
	        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);
	        try {
	            Scheduler scheduler = factory.getScheduler();
	            
	            JobDetail jobDivisa = JobBuilder.newJob(Tarea.class)
	            		.withIdentity("job1","group1")
	            		.build();
	            		
	            Trigger triggerDivisa =  TriggerBuilder.newTrigger()
	            		.withIdentity("trigger1","group1")
	            		.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/1 1/1 * ? *"))	            		
	            		.startNow()	            			
	            		.build();
	           
	            scheduler.scheduleJob(jobDivisa, triggerDivisa);
	            scheduler.start();
	            
	            
	            LOG.info("INICIAMOS SCHEDULER PRIMERA TAREA");
	            
	            
	            JobDetail jobLibor = JobBuilder.newJob(TareaLibor.class)
	            		.withIdentity("job2","group2")
	            		.build();
	            		
	            Trigger triggerLibor =  TriggerBuilder.newTrigger()
	            		.withIdentity("trigger2","group2")
	            		.withSchedule(CronScheduleBuilder.cronSchedule("0 30 18 1/1 * ? *"))	            		
	            		.startNow()	            			
	            		.build();
	            
	            
	            scheduler.scheduleJob(jobLibor, triggerLibor);
	            scheduler.start();
	            LOG.debug("INICIAMOS SEGUNDA TAREA");
	            
	          //cron minuto = 0 0/1 * 1/1 * ? *
	            //cron hora = 0 0 0/1 1/1 * ? *
	            //cron diario a las 18:30 = 0 30 18 1/1 * ? *

	        } catch (Exception e) {
	            LOG.error("Ocurri\u00f3 un error al calendarizar el trabajo", e);
	        }
	    }
	    @Override
	    public void contextDestroyed(ServletContextEvent sce) {
	        super.contextDestroyed(sce);
	    }
	
}
