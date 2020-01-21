package backend.controller;
//can copypaste everywhere
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.SimpleTicketDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Ticket;
import backend.service.EventDayService;
import backend.service.EventSectorService;
import backend.service.TicketService;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
	//private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TicketService tisketService;
	
	@Autowired
	EventDayService eventDayService;
	
	@Autowired
	EventSectorService eventSectorService;


	/* get all tickets, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SimpleTicketDTO> getAllTickets() {
		List<Ticket>tickets =  tisketService.findAll();
		List<SimpleTicketDTO> ticketsDTO = new ArrayList<SimpleTicketDTO>();
		for (Ticket ticket : tickets) {
			SimpleTicketDTO dto = new SimpleTicketDTO(ticket);
			ticketsDTO.add(dto);
		}
		return ticketsDTO;
	}

	/* get all tickets, permitted for all */
	@GetMapping(value = "/{ed_id}/{es_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SimpleTicketDTO> getAllTicketsEventDayIDEventSectorID(@PathVariable(value = "ed_id") Long ed_id, @PathVariable(value = "es_id") Long es_id) {
		List<Ticket>tickets = tisketService.findAllByEventDayIDEventSectorID(ed_id, es_id);
		List<SimpleTicketDTO> ticketsDTO = new ArrayList<SimpleTicketDTO>();
		for (Ticket ticket : tickets) {
			SimpleTicketDTO dto = new SimpleTicketDTO(ticket);
			ticketsDTO.add(dto);
		}
		return ticketsDTO;
	}
	
	/* get an tickets by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SimpleTicketDTO> getTicket(
			@PathVariable(value = "id") Long ticketId) throws ResourceNotFoundException {
		Ticket ticket = tisketService.findOne(ticketId);

		if (ticket == null) {
			return ResponseEntity.notFound().build();
		}
		SimpleTicketDTO dto = new SimpleTicketDTO(ticket);
		return ResponseEntity.ok().body(dto);
	}

}
