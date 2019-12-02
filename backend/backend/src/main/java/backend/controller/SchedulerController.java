package backend.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import backend.service.SchedulerService;

@Controller
public class SchedulerController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
			.ofPattern("HH:mm:ss");

	@Autowired
	SchedulerService schedulerService;

	@Scheduled(cron = "${scheduler.cron}")
	public void scheduledTasks() {
		logger.info("Daily Task :: Execution Time - {}",
				dateTimeFormatter.format(LocalDateTime.now()));
		try {
			schedulerService.logicalDeleteExpiredEvents();
			logger.info("Deleting expired events COMPLETED");
		} catch (Exception e) {
			logger.error("ERROR while deleting expired events!");
		}
		
		try {
			schedulerService.deleteUnboughtReservations();
			logger.info("Deletion of unbought tickets and reservations COMPLETED");
		} catch (Exception e) {
			logger.error("ERROR while deleting unbought reservations!");
		}
		
		try {
			schedulerService.sendEventReminders();
			logger.info("Sending event reminders for users COMPLETED!");
		} catch (Exception e) {
			logger.error("ERROR while sending event reminders to users!");
		}
		
		try {
			schedulerService.sendBuyingReminders();
			logger.info("Sending ticket buying reminders for users COMPLETED!");
		} catch (Exception e) {
			logger.error("ERROR while sending reservation buying reminders to users!");
		}
	}
}
