package backend.controller;
//can copypaste everywhere
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import backend.model.*;
import backend.service.*;
import backend.dto.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TicketService tisketService;
	
	@Autowired
	EventDayService eventDayService;
	
	@Autowired
	EventSectorService eventSectorService;

	/* saving address */
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
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
	}

	/* get all addresses, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Ticket> getAllTickets() {
		return tisketService.findAll();
	}

	/* get an address by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> getTicket(
			@PathVariable(value = "id") Long ticketId) {
		Ticket ticket = tisketService.findOne(ticketId);

		if (ticket == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(ticket);
	}

	/* update address by id */
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
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
	}

	/* delete Address */
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
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
	}
}