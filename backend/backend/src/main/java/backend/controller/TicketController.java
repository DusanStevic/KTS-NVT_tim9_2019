package backend.controller;
//can copypaste everywhere
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	/* saving address */
	/*@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Ticket createTicket(@Valid @RequestBody TicketDTO ticketDTO) {
		Ticket ticket = new Ticket();
		ticket.setHasSeat(ticketDTO.isHasSeat());
		ticket.setEventDay(eventDayService.findOne(ticketDTO.getEventDay_id()));
		ticket.setEventSector(eventSectorService.findOne(ticketDTO.getEventSector_id()));
		if(ticketDTO.isHasSeat()) {
			ticket.setNumCol(ticketDTO.getNumCol());
			ticket.setNumRow(ticketDTO.getNumRow());
		}
		
		return tisketService.save(ticket);
	}*/

	/* get all addresses, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Ticket> getAllTickets() {
		return tisketService.findAll();
	}

	/* get all addresses, permitted for all */
	@GetMapping(value = "/{ed_id}/{es_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Ticket> getAllTicketsEventDayIDEventSectorID(@PathVariable(value = "ed_id") Long ed_id, @PathVariable(value = "es_id") Long es_id) {
		return tisketService.findAllByEventDayIDEventSectorID(ed_id, es_id);
	}
	
	/* get an address by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> getTicket(
			@PathVariable(value = "id") Long ticketId) throws ResourceNotFoundException {
		Ticket ticket = tisketService.findOne(ticketId);

		if (ticket == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(ticket);
	}

	/* update address by id */
	/*@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> updateTicket(
			@PathVariable(value = "id") Long ticketId,
			@Valid @RequestBody TicketDTO t) {

		Ticket ticket = tisketService.findOne(ticketId);
		if (ticket == null) {
			return ResponseEntity.notFound().build();
		}

		

		Ticket updateTicket = tisketService.save(ticket);
		return ResponseEntity.ok().body(updateTicket);
	}*/

	/* delete Address */
	/*@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> deleteTicket(
			@PathVariable(value = "id") Long ticketId) {
		Ticket t = tisketService.findOne(ticketId);

		if (t != null) {
			tisketService.remove(ticketId);
			logger.info("Address " + ticketId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Address " + ticketId + " not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}*/
}
