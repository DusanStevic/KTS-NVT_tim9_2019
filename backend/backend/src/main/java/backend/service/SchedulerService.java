package backend.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import backend.model.Event;
import backend.model.Reservation;

@Service
public class SchedulerService {

	@Autowired
	EventService eventService;

	@Autowired
	ReservationService reservationService;

	@Autowired
	UserService userService;

	@Autowired
	EmailService emailService;

	// Logically delete all event
	public void logicalDeleteExpiredEvents() {
		try {
			List<Event> activeEvents = eventService.findAllNotDeleted();
			Date today = new Date();
			for (Event event : activeEvents) {
				if (event.getEndDate().before(today)) {
					eventService.delete(event.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Checking if there is unbough reservation after event-deadline
	public void deleteUnboughtReservations() {
		try {
			List<Event> activeEvents = eventService.findAllNotDeleted();
			Date today = new Date();

			for (Event event : activeEvents) {
				// setting calendar on deadline
				Calendar c = Calendar.getInstance();
				c.setTime(event.getStartDate());
				int beforeStart = event.getNumDays();
				c.add(Calendar.DATE, -beforeStart);
				Date deadline = c.getTime();

				// ako smo posle deadlina-a
				if (today.after(deadline)) {
					List<Reservation> expired_reservations = reservationService
							.findByEvent(event.getId());

					// Deleting unbought reservations
					for (Reservation r : expired_reservations) {
						if (!r.isPurchased()) {
							reservationService.delete(r.getId());
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendEventReminders() throws MailException, InterruptedException {
		List<Event> activeEvents = eventService.findAllNotDeleted();
		Date today = new Date();
		Calendar cToday = Calendar.getInstance();
		cToday.setTime(today);

		for (Event event : activeEvents) {
			// setting calendar on the day before event start
			Calendar cDayBefore = Calendar.getInstance();
			cDayBefore.setTime(event.getStartDate());
			cDayBefore.add(Calendar.DATE, -1);
			cDayBefore.getTime();

			if (cToday.get(Calendar.YEAR) == cDayBefore.get(Calendar.YEAR)
					&& cToday.get(Calendar.MONTH) == cDayBefore
							.get(Calendar.MONTH)
					&& cToday.get(Calendar.DAY_OF_MONTH) == cDayBefore
							.get(Calendar.DAY_OF_MONTH)) {
				List<Reservation> rezervacije = reservationService
						.findByEvent(event.getId());
				for (Reservation reservation : rezervacije) {
					emailService.sendEventReminder(reservation.getBuyer(),
							event);
				}

			}
		}
	}

	public void sendBuyingReminders() {
		List<Event> activeEvents = eventService.findAllNotDeleted();
		Date today = new Date();
		Calendar cToday = Calendar.getInstance();
		cToday.setTime(today);

		for (Event event : activeEvents) {
			// setting calendar on the day before event start
			Calendar cDayBefore = Calendar.getInstance();
			cDayBefore.setTime(event.getStartDate());
			cDayBefore.add(Calendar.DATE, -1);

			if (cToday.get(Calendar.YEAR) == cDayBefore.get(Calendar.YEAR)
					&& cToday.get(Calendar.MONTH) == cDayBefore
							.get(Calendar.MONTH)
					&& cToday.get(Calendar.DAY_OF_MONTH) == cDayBefore
							.get(Calendar.DAY_OF_MONTH)) {
				List<Reservation> rezervacije = reservationService
						.findByEvent(event.getId());
				for (Reservation reservation : rezervacije) {
					//if tickets not bought send reminder
					if (!reservation.isPurchased()) {
						try {
							emailService.sendBuyingReminder(
									reservation.getBuyer(), event);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			}
		}
	}

}
