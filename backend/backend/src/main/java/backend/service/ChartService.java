package backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import backend.dto.SystemInformationsDTO;
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

}
