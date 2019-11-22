package backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dto.charts.ChartEventTicketsSoldDTO;
import backend.dto.charts.ChartIncomeEventsDTO;
import backend.dto.charts.DateIntervalDTO;
import backend.dto.charts.SystemInformationsDTO;
import backend.model.Authority;
import backend.model.Event;
import backend.model.Ticket;
import backend.model.User;

@Service
@Transactional
public class ChartService {

	@Autowired
	UserService userService;

	@Autowired
	TicketService ticketService;

	@Autowired
	EventService eventService;

	public SystemInformationsDTO systemInformations() {
		SystemInformationsDTO info = new SystemInformationsDTO();

		List<User> users = userService.findAll();
		List<Ticket> tickets = ticketService.findAll();
		List<Event> events = eventService.findAll();

		int adminCount = 0;
		int userCount = 0;
		for (User user : users) {
			List<String> roles = user
					.getAuthorities()
					.stream()
					.map(authority -> ((Authority) authority).getRole()
							.toString()).collect(Collectors.toList());
			if (roles.contains("ROLE_REGISTERED_USER")) {
				userCount++;
			} else if (roles.contains("ROLE_ADMIN")) {
				adminCount++;
			}
		}

		double[] income = { 0.0 };
		tickets.stream().forEach(
				c -> income[0] += c.getEventSector().getPrice());

		info.setNumberOfAdmins(adminCount);
		info.setNumberOfUsers(userCount);
		info.setNumberOfEvents(events.size());
		info.setAllTimeTickets(tickets.size());
		info.setAllTimeIncome(income[0]);

		return info;

	}

	public List<ChartIncomeEventsDTO> incomeByEvents() {
		// List of eventName + event income, PLUS average + average_event_income
		ArrayList<ChartIncomeEventsDTO> info = new ArrayList<ChartIncomeEventsDTO>();

		List<Event> events = eventService.findAll();

		double sum = 0;
		for (Event event : events) {
			double[] income = { 0.0 };
			List<Ticket> eventTickets = ticketService.findAllByEvent(event
					.getId());
			eventTickets.stream().forEach(
					c -> income[0] += c.getEventSector().getPrice());
			sum += income[0];
			info.add(new ChartIncomeEventsDTO(event.getName(), income[0]));
		}
		info.add(new ChartIncomeEventsDTO("Average", sum / events.size()));
		return info;
	}

	public List<ChartEventTicketsSoldDTO> soldTicketsByEvents() {
		// List of eventName + event_tickets_sold, PLUS average +
		// average_event_tickets_sold
		ArrayList<ChartEventTicketsSoldDTO> info = new ArrayList<ChartEventTicketsSoldDTO>();

		List<Event> events = eventService.findAll();

		int sum = 0;
		for (Event event : events) {
			int soldTickets = ticketService.findAllByEvent(event.getId())
					.size();
			sum += soldTickets;
			info.add(new ChartEventTicketsSoldDTO(event.getName(), soldTickets));
		}
		info.add(new ChartEventTicketsSoldDTO("Average", sum / events.size()));
		return info;
	}

	// Only interested in events in given interval
	public List<ChartIncomeEventsDTO> incomeByEvents(
			@Valid DateIntervalDTO interval) {
		ArrayList<ChartIncomeEventsDTO> info = new ArrayList<ChartIncomeEventsDTO>();

		List<Event> events = eventService.findByInterval(interval);

		if (events.isEmpty()) {
			return info;
		}

		double sum = 0;
		for (Event event : events) {
			double[] income = { 0.0 };
			List<Ticket> eventTickets = ticketService.findAllByEvent(event
					.getId());
			eventTickets.stream().forEach(
					c -> income[0] += c.getEventSector().getPrice());
			sum += income[0];
			info.add(new ChartIncomeEventsDTO(event.getName(), income[0]));
		}
		info.add(new ChartIncomeEventsDTO("Average", sum / events.size()));
		return info;
	}

	public List<ChartEventTicketsSoldDTO> soldTicketsByEvents(
			@Valid DateIntervalDTO interval) {
		ArrayList<ChartEventTicketsSoldDTO> info = new ArrayList<ChartEventTicketsSoldDTO>();

		List<Event> events = eventService.findByInterval(interval);

		if (events.isEmpty()) {
			return info;
		}

		int sum = 0;
		for (Event event : events) {
			int soldTickets = ticketService.findAllByEvent(event.getId())
					.size();
			sum += soldTickets;
			info.add(new ChartEventTicketsSoldDTO(event.getName(), soldTickets));
		}
		info.add(new ChartEventTicketsSoldDTO("Average", sum / events.size()));
		return info;
	}

}