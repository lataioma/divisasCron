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
	            JobDetail job = JobBuilder.newJob(Tarea.class).build();
	            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple").withSchedule(
	                    CronScheduleBuilder.cronSchedule("0 0/30 * 1/1 * ? *")
	            ).startNow().build();
	            scheduler.scheduleJob(job, trigger);
	            scheduler.start();
	        } catch (Exception e) {
	            LOG.error("Ocurri\u00f3 un error al calendarizar el trabajo", e);
	        }
	    }
	    @Override
	    public void contextDestroyed(ServletContextEvent sce) {
	        super.contextDestroyed(sce);
	    }
	
}
