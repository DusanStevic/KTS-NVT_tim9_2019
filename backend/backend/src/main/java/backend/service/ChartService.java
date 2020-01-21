package backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dto.charts.ChartEventTicketsSoldDTO;
import backend.dto.charts.ChartIncomeEventsDTO;
import backend.dto.charts.ChartIncomeLocationsDTO;
import backend.dto.charts.ChartLocationTicketsSoldDTO;
import backend.dto.charts.DateIntervalDTO;
import backend.dto.charts.SystemInformationsDTO;
import backend.exceptions.BadRequestException;
import backend.model.Authority;
import backend.model.Event;
import backend.model.Location;
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

	@Autowired
	LocationService locationService;

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

		if (events == null || events.isEmpty()) {
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
		 BigDecimal bd = new BigDecimal(sum / (double) events.size()).setScale(2, RoundingMode.HALF_UP);
		info.add(new ChartIncomeEventsDTO("Average", bd.doubleValue()));
		return info;
	}

	public List<ChartEventTicketsSoldDTO> soldTicketsByEvents() {
		// List of eventName + event_tickets_sold, PLUS average +
		// average_event_tickets_sold
		ArrayList<ChartEventTicketsSoldDTO> info = new ArrayList<ChartEventTicketsSoldDTO>();

		List<Event> events = eventService.findAll();

		if (events == null || events.isEmpty()) {
			return info;
		}

		double sum = 0.0;
		for (Event event : events) {
			double soldTickets = (double) ticketService.findAllByEvent(
					event.getId()).size();
			sum += soldTickets;
			info.add(new ChartEventTicketsSoldDTO(event.getName(), soldTickets));
		}
		BigDecimal bd = new BigDecimal(sum/(double) events.size()).setScale(2, RoundingMode.HALF_UP);
		info.add(new ChartEventTicketsSoldDTO("Average", bd.doubleValue()));
		return info;
	}

	// Only interested in events in given interval
	public List<ChartIncomeEventsDTO> incomeByEvents(
			@Valid DateIntervalDTO interval) throws BadRequestException {
		ArrayList<ChartIncomeEventsDTO> info = new ArrayList<ChartIncomeEventsDTO>();

		if (interval.getStartDate().after(interval.getEndDate())) {
			throw new BadRequestException("Start date must be after end date!");
		}

		List<Event> events = eventService.findByInterval(interval);

		if (events == null || events.isEmpty()) {
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
		info.add(new ChartIncomeEventsDTO("Average", sum / (double) events.size()));
		return info;
	}

	public List<ChartEventTicketsSoldDTO> soldTicketsByEvents(
			@Valid DateIntervalDTO interval) throws BadRequestException {
		ArrayList<ChartEventTicketsSoldDTO> info = new ArrayList<ChartEventTicketsSoldDTO>();

		if (interval.getStartDate().after(interval.getEndDate())) {
			throw new BadRequestException("Start date must be after end date!");
		}

		List<Event> events = eventService.findByInterval(interval);

		if (events == null || events.isEmpty()) {
			return info;
		}

		double sum = 0;
		for (Event event : events) {
			double soldTickets = ticketService.findAllByEvent(event.getId())
					.size();
			sum += soldTickets;
			info.add(new ChartEventTicketsSoldDTO(event.getName(), soldTickets));
		}
		info.add(new ChartEventTicketsSoldDTO("Average", sum / (double) events.size()));
		return info;
	}

	public List<ChartIncomeLocationsDTO> incomeByLocations() {
		ArrayList<ChartIncomeLocationsDTO> info = new ArrayList<ChartIncomeLocationsDTO>();

		List<Location> locations = locationService.findAll();

		if (locations == null || locations.isEmpty()) {
			return info;
		}

		double sum = 0;
		for (Location location : locations) {
			List<Ticket> locationTickets = ticketService
					.findAllByLocation(location.getId());

			double inc = locationTickets.stream()
					.mapToDouble(c -> c.getEventSector().getPrice()).sum();

			sum += inc;
			info.add(new ChartIncomeLocationsDTO(location.getName(), inc));
		}
		info.add(new ChartIncomeLocationsDTO("Average", sum / (double) locations.size()));
		return info;
	}

	public List<ChartLocationTicketsSoldDTO> soldTicketsByLocations() {
		ArrayList<ChartLocationTicketsSoldDTO> info = new ArrayList<ChartLocationTicketsSoldDTO>();

		List<Location> locations = locationService.findAll();

		if (locations == null || locations.isEmpty()) {
			return info;
		}

		double sum = 0;
		for (Location location : locations) {
			double soldTickets = ticketService.findAllByLocation(location.getId())
					.size();
			sum += soldTickets;
			info.add(new ChartLocationTicketsSoldDTO(location.getName(),
					soldTickets));
		}
		info.add(new ChartLocationTicketsSoldDTO("Average", sum
				/ (double) locations.size()));
		return info;
	}

	public List<ChartIncomeLocationsDTO> incomeByLocations(
			DateIntervalDTO interval) throws BadRequestException {
		ArrayList<ChartIncomeLocationsDTO> info = new ArrayList<ChartIncomeLocationsDTO>();
		// List of locations that had events at given interval
		HashMap<Location, Double> locations = new HashMap<Location, Double>();

		if (interval.getStartDate().after(interval.getEndDate())) {
			throw new BadRequestException("Start date must be after end date!");
		}

		List<Event> events = eventService.findByInterval(interval);

		// if there wasn't any event then no location made profit
		if (events == null || events.isEmpty()) {
			return info;
		}

		double sum = 0;
		for (Event e : events) {
			if (!locations.containsKey(e.getLocation())) {
				List<Ticket> locationTickets = ticketService.findAllByEvent(e
						.getId());

				double inc = locationTickets.stream()
						.mapToDouble(c -> c.getEventSector().getPrice()).sum();

				sum += inc;
				locations.put(e.getLocation(), inc);
			} else {
				List<Ticket> locationTickets = ticketService.findAllByEvent(e
						.getId());

				double inc = locationTickets.stream()
						.mapToDouble(c -> c.getEventSector().getPrice()).sum();

				sum += inc;
				locations.put(e.getLocation(), locations.get(e.getLocation())
						+ inc);
			}
		}
		for (Location l : locations.keySet()) {
			info.add(new ChartIncomeLocationsDTO(l.getName(), locations.get(l)));
		}
		info.add(new ChartIncomeLocationsDTO("Average", sum / (double) locations.size()));
		return info;
	}

	public List<ChartLocationTicketsSoldDTO> soldTicketsByLocations(
			DateIntervalDTO interval) throws BadRequestException {
		ArrayList<ChartLocationTicketsSoldDTO> info = new ArrayList<ChartLocationTicketsSoldDTO>();
		// List of locations that had events at given interval
		HashMap<Location, Double> locations = new HashMap<Location, Double>();

		if (interval.getStartDate().after(interval.getEndDate())) {
			throw new BadRequestException("Start date must be after end date!");
		}

		List<Event> events = eventService.findByInterval(interval);

		// if there wasn't any event then no location made profit
		if (events == null || events.isEmpty()) {
			return info;
		}

		double sum = 0;
		for (Event e : events) {
			if (!locations.containsKey(e.getLocation())) {
				double count = ticketService.findAllByEvent(e.getId()).size();

				sum += count;
				locations.put(e.getLocation(), count);
			} else {
				double count = ticketService.findAllByEvent(e.getId()).size();

				sum += count;
				locations.put(e.getLocation(), locations.get(e.getLocation())
						+ count);
			}
		}
		for (Location l : locations.keySet()) {
			info.add(new ChartLocationTicketsSoldDTO(l.getName(), locations
					.get(l)));
		}
		info.add(new ChartLocationTicketsSoldDTO("Average", sum
				/ (double) locations.size()));
		return info;
	}

}
