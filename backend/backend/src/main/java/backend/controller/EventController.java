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
import backend.converters.EventConverter;
import backend.dto.*;

@RestController
@RequestMapping("/api/event")
public class EventController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EventService eventService;

	@Autowired
	EventConverter eventConverter;
	/* saving event */
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Event createEvent(@Valid @RequestBody EventDTO dto) {
		Event event = eventConverter.EventDTO2Event(dto);
		return eventService.save(event);
	}
	

	/* get all eventes, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Event> getAllEventes() {
		return eventService.findAll();
	}

	/* get an event by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Event> getEvent(
			@PathVariable(value = "id") Long eventId) {
		Event event = eventService.findOne(eventId);

		if (event == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(event);
	}
}
