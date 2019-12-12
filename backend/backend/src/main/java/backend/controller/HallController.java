package backend.controller;

//can copypaste everywhere
import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

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
import backend.exceptions.ResourceNotFoundException;
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
	/*@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
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
	}*/

	/* get all halls, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hall> getAllHalles() {
		return hallService.findAllNotDeleted();
	}

	/* get an hall by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hall> getHall(@PathVariable(value = "id") Long hallId) throws ResourceNotFoundException {
		Hall hall = hallService.findOneNotDeleted(hallId);

		return new ResponseEntity<>(hall, HttpStatus.OK);
	}

	/* update hall by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hall> updateHall(
			@PathVariable(value = "id") Long hallId,
			@Valid @RequestBody HallDTO h) throws ResourceNotFoundException {

		return new ResponseEntity<>(hallService.update(hallId, h), HttpStatus.OK);
	}

	/* delete Hall */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteHall(
			@PathVariable(value = "id") Long hallId) throws ResourceNotFoundException {
		hallService.delete(hallId);
		return new ResponseEntity<>("Successfully deleted hall", HttpStatus.OK);
	}

}
