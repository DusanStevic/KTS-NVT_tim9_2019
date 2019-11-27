package backend.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	EmailService emailService;

	// Logically delete all event
	public void logicalDeleteExpiredEvents() {
		try {
			List<Event> activeEvents = eventService.findAllActive();
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
			List<Event> activeEvents = eventService.findAllActive();
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

}
