package backend.controller;
//can copypaste everywhere
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.converters.EventDayConverter;
import backend.dto.EventDayDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.EventDay;
import backend.model.EventStatus;
import backend.service.EventDayService;
import backend.service.EventService;

@RestController
@RequestMapping("/api/eventday")
public class EventDayController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EventDayService eventDayService;

	@Autowired
	EventService eventService;
	
	@Autowired
	EventDayConverter eventDayConverter;
	
	/* creating event day */
	/*@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public EventDay createAddress(@Valid @RequestBody EventDayDTO eventDayDTO) {
		EventDay eventDay = new EventDay(eventDayDTO);
		eventDay.setEvent(eventService.findOne(eventDayDTO.getEvent_id()));
		eventDay.setStatus(EventStatus.values()[eventDayDTO.getStatus()]);
		return eventDayService.save(eventDay);
	}*/

	/* get all event days, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EventDay>> getAllEventDays() {
		return new ResponseEntity<>(eventDayService.findAll(), HttpStatus.OK);
	}

	/* get event day by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEventDay(
			@PathVariable(value = "id") Long eventDayId) throws ResourceNotFoundException {
		EventDay eventDay = eventDayService.findOne(eventDayId);

		if (eventDay == null) {
			return new ResponseEntity<>("Could not find requested event day", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eventDay, HttpStatus.OK);
	}

	/* update eventDays by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDay> updateEventDay(
			@PathVariable(value = "id") Long eventDayId,
			@Valid @RequestBody EventDayDTO e) throws ResourceNotFoundException {
		EventDay eventDay = eventDayConverter.EventDayDTO2EventDay(e);
		return new ResponseEntity<>(eventDayService.update(eventDayId, eventDay), HttpStatus.OK);
		
	}

	/* delete event day */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteEventDay(
			@PathVariable(value = "id") Long eventDayId) throws ResourceNotFoundException {
		eventDayService.delete(eventDayId);
		return new ResponseEntity<>("Successfully deleted a day of an event", HttpStatus.OK);
	}
}
