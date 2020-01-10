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

import backend.dto.EventSectorDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.EventSector;
import backend.service.EventSectorService;
import backend.service.EventService;
import backend.service.SectorService;

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
	
	/* saving event sector */
	/*@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventSector> createEventSector(@Valid @RequestBody EventSectorDTO eventSectorDTO) {
		EventSector eventSector = new EventSector();
		eventSector.setEvent(eventService.findOne(eventSectorDTO.getEvent_id()));
		eventSector.setPrice(eventSectorDTO.getPrice());
		eventSector.setSector(sectorService.findOne(eventSectorDTO.getSector_id()));
		return new ResponseEntity<>(eventSectorService.save(eventSector), HttpStatus.OK);
	}*/

	/* get all event sectors, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EventSector>> getAllEventSectors() {
		return new ResponseEntity<>(eventSectorService.findAllNotDeleted(), HttpStatus.OK);
	}

	/* get an event sector by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getEventSector(
			@PathVariable(value = "id") Long eventSectorId) throws ResourceNotFoundException {
		EventSector eventSector = eventSectorService.findOneNotDeleted(eventSectorId);
		return new ResponseEntity<>(eventSector, HttpStatus.OK);
	}

	/* update event sector by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventSector> updateEventSector(
			@PathVariable(value = "id") Long eventSectorId,
			@Valid @RequestBody EventSectorDTO es) throws ResourceNotFoundException {

		return new ResponseEntity<>(eventSectorService.update(eventSectorId, es.getPrice()), HttpStatus.OK);
		
	}

	/* delete event sector */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteEventSector(
			@PathVariable(value = "id") Long eventSectorId) throws ResourceNotFoundException {
		eventSectorService.delete(eventSectorId);
		logger.info("Deleted " + eventSectorId);
		return new ResponseEntity<>("Successfully deleted a day of an event", HttpStatus.OK);
	}
}
