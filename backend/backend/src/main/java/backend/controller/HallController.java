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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.HallDTO;
import backend.model.Hall;
import backend.service.HallService;
import backend.service.LocationService;

@RestController
@RequestMapping("/api/hall")
public class HallController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LocationService locationService;

	@Autowired
	HallService hallService;

	/* saving hall */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Hall createHall(@Valid @RequestBody HallDTO hallDTO) {
		Hall hall = new Hall();


		if (!hallDTO.getName().trim().equals("")) {
			hall.setName(hallDTO.getName());
		}
		if (hallDTO.getNumber_of_sectors() > 0) {
		hall.setNumberOfSectors(hallDTO.getNumber_of_sectors());
		}
		hall.setLocation(locationService.findOne(hallDTO.getLocation_id()));
		
		return hallService.save(hall);
	}

	/* get all halls, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hall> getAllHalles() {
		return hallService.findAll();
	}

	/* get an hall by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hall> getHall(@PathVariable(value = "id") Long hallId) {
		Hall hall = hallService.findOne(hallId);

		if (hall == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(hall);
	}

	/* update hall by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hall> updateHall(
			@PathVariable(value = "id") Long hallId,
			@Valid @RequestBody HallDTO h) {

		Hall hall = hallService.findOne(hallId);
		if (hall == null) {
			return ResponseEntity.notFound().build();
		}

		// maybe makes no sense because hall cant go to different location, but
		// it can stay
		if (h.getLocation_id() != null) {
			hall.setLocation(locationService.findOne(h.getLocation_id()));
		}
		if (!h.getName().trim().equals("")) {
			hall.setName(h.getName());
		}

		if (h.getNumber_of_sectors() > 0) {
			hall.setNumberOfSectors(h.getNumber_of_sectors());
		}

		Hall updateHall = hallService.save(hall);
		return ResponseEntity.ok().body(updateHall);
	}

	/* delete Hall */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hall> deleteHall(
			@PathVariable(value = "id") Long hallId) {
		Hall h = hallService.findOne(hallId);

		if (h != null) {
			hallService.remove(hallId);
			logger.info("Hall " + hallId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Hall " + hallId + " not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
