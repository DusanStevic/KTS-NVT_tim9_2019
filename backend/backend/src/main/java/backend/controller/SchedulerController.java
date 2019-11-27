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
		} catch (Exception e) {
			logger.error("Error while deleting expired events");
		}
	}
}
