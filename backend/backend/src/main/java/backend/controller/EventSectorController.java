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
@RequestMapping("/api/eventsector")
public class EventSectorController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EventSectorService eventSectorService;

	@Autowired
	EventService eventService;
	
	@Autowired
	SectorService sectorService;
	
	/* saving address */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public EventSector createEventSector(@Valid @RequestBody EventSectorDTO eventSectorDTO) {
		EventSector eventSector = new EventSector();
		eventSector.setEvent(eventService.findOne(eventSectorDTO.getEvent_id()));
		eventSector.setPrice(eventSectorDTO.getPrice());
		eventSector.setSector(sectorService.findOne(eventSectorDTO.getSector_id()));
		return eventSectorService.save(eventSector);
	}

	/* get all addresses, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EventSector> getAllEventSectors() {
		return eventSectorService.findAll();
	}

	/* get an address by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventSector> getEventSector(
			@PathVariable(value = "id") Long eventSectorId) {
		EventSector eventSector = eventSectorService.findOne(eventSectorId);

		if (eventSector == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(eventSector);
	}

	/* update address by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventSector> updateEventSector(
			@PathVariable(value = "id") Long eventSectorId,
			@Valid @RequestBody EventSectorDTO es) {

		EventSector eventSector = eventSectorService.findOne(eventSectorId);
		if (eventSector == null) {
			return ResponseEntity.notFound().build();
		}

		eventSector.setEvent(eventService.findOne(es.getEvent_id()));
		eventSector.setPrice(es.getPrice());
		eventSector.setSector(sectorService.findOne(es.getSector_id()));

		EventSector updateEventSector = eventSectorService.save(eventSector);
		return ResponseEntity.ok().body(updateEventSector);
	}

	/* delete Address */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventSector> deleteEventSector(
			@PathVariable(value = "id") Long eventSectorId) {
		EventSector es = eventSectorService.findOne(eventSectorId);

		if (es != null) {
			eventSectorService.remove(eventSectorId);
			logger.info("Address " + eventSectorId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Address " + eventSectorId + " not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
