package backend.controller;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.converters.EventConverter;
import backend.dto.CreateEventDTO;
import backend.dto.EventDTO;
import backend.dto.EventUpdateDTO;
import backend.dto.UrlDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Event;
import backend.service.EventService;
import backend.service.FileUploadService;


@RestController
@RequestMapping("/api/event")
public class EventController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EventService eventService;
	
	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	EventConverter eventConverter;
	/* saving event */
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Event> createEvent(@Valid @RequestBody CreateEventDTO dto) throws ResourceNotFoundException {
		Event event = eventConverter.CreateEventDTO2Event(dto);
		return new ResponseEntity<>(eventService.save(event), HttpStatus.OK);
	}
	

	/* get all events, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Event>> getAllEventes() {
		return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
	}
	
	/* get all active events, permitted for all */
	@GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Event>> getAllActiveEvents() {
		return new ResponseEntity<>(eventService.findAllNotDeleted(), HttpStatus.OK);
	}

	/* get an event by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Event> getEvent(
			@PathVariable(value = "id") Long eventId) throws ResourceNotFoundException {
		Event e = eventService.findOneNotDeleted(eventId);
		return new ResponseEntity<>(e, HttpStatus.OK);
	}
	
	/* update event by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Event> updateEvent(
			@PathVariable(value = "id") Long eventId,
			@Valid @RequestBody EventUpdateDTO dto) throws ResourceNotFoundException {
		Event e = eventConverter.EventUpdateDTO2Event(dto);
		
		return new ResponseEntity<>(eventService.update(eventId, e), HttpStatus.OK);
	}
	

	
	/*add image to event*/
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/addImage/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<EventDTO> addImage(@PathVariable("id") Long eventId,@RequestParam("file")MultipartFile file) throws ResourceNotFoundException {
		Event event = eventService.findOneNotDeleted(eventId);
		event.getImagePaths().add(fileUploadService.imageUpload(file));
		eventService.save(event);
		return new ResponseEntity<>(EventConverter.Event2EventDTO(event), HttpStatus.OK);
		
	}
	
	/*delete image from event*/
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/deleteImage/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDTO> deleteImage(@PathVariable("id") Long eventId,@Valid @RequestBody UrlDTO dto) throws IOException, ResourceNotFoundException {
		Event event = eventService.findOneNotDeleted(eventId);
		//brisanje url-a slike iz baze
		event.getImagePaths().remove(dto.getUrl());
		//brisanje slike(stvarnog fajla) sa cloud-a
		fileUploadService.imageDelete(dto.getUrl());
		eventService.save(event);
		return new ResponseEntity<>(EventConverter.Event2EventDTO(event), HttpStatus.OK);
		
	}
	
	/*add video to event*/
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/addVideo/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<EventDTO> addVideo(@PathVariable("id") Long eventId,@RequestParam("file")MultipartFile file) throws ResourceNotFoundException {
		Event event = eventService.findOneNotDeleted(eventId);
		event.getVideoPaths().add(fileUploadService.videoUpload(file));
		eventService.save(event);
		return new ResponseEntity<>(EventConverter.Event2EventDTO(event), HttpStatus.OK);
		
	}
	
	/*delete image from event*/
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/deleteVideo/{id}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDTO> deleteVideo(@PathVariable("id") Long eventId,@Valid @RequestBody UrlDTO dto) throws IOException, ResourceNotFoundException {
		Event event = eventService.findOneNotDeleted(eventId);
		//brisanje url-a videa iz baze
		event.getVideoPaths().remove(dto.getUrl());
		//brisanje videa(stvarnog fajla) sa cloud-a
		fileUploadService.videoDelete(dto.getUrl());
		eventService.save(event);
		return new ResponseEntity<>(EventConverter.Event2EventDTO(event), HttpStatus.OK);
		
	}
	
	
	

	/* delete event */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteEvent(
			@PathVariable(value = "id") Long eventId) throws ResourceNotFoundException {
		logger.debug("Deleted" + eventId);
		eventService.delete(eventId);
		return new ResponseEntity<>("Successfully deleted event", HttpStatus.OK);
	}
}
