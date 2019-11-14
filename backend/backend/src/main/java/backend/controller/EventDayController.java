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
@RequestMapping("/api/eventday")
public class EventDayController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EventDayService eventDayService;

	@Autowired
	EventService eventService;
	
	
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
	public List<EventDay> getAllEventDays() {
		return eventDayService.findAll();
	}

	/* get an address by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDay> getEventDays(
			@PathVariable(value = "id") Long eventDayId) {
		EventDay eventDay = eventDayService.findOne(eventDayId);

		if (eventDay == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(eventDay);
	}

	/* update eventDays by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDay> updateEventDay(
			@PathVariable(value = "id") Long eventDayId,
			@Valid @RequestBody EventDayDTO e) {

		EventDay eventDay = eventDayService.findOne(eventDayId);
		if (eventDay == null) {
			return ResponseEntity.notFound().build();
		}

		eventDay.setName(e.getName());
		eventDay.setDescription(e.getDescription());
		eventDay.setDate(e.getDate());
		eventDay.setEvent(eventService.findOne(e.getEvent_id()));
		eventDay.setStatus(EventStatus.values()[e.getStatus()]);
		EventDay updateEventDay = eventDayService.save(eventDay);
		return ResponseEntity.ok().body(updateEventDay);
	}

	/* delete Address */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDay> deleteEventDay(
			@PathVariable(value = "id") Long eventDayId) {
		EventDay a = eventDayService.findOne(eventDayId);

		if (a != null) {
			eventDayService.remove(eventDayId);
			logger.info("Address " + eventDayId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Address " + eventDayId + " not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
