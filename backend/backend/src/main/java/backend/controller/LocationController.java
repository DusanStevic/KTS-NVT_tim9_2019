package backend.controller;


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

import backend.converters.LocationConverter;
import backend.dto.LocationDTO;
import backend.exceptions.BadRequestException;
import backend.exceptions.ResourceNotFoundException;
import backend.exceptions.SavingException;
import backend.model.Location;
import backend.service.AddressService;
import backend.service.LocationService;

@RestController
@RequestMapping("/api/location")
public class LocationController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LocationService locationService;
	
	@Autowired
	AddressService addressService;

	@Autowired
	LocationConverter locationConverter;
	
	/* saving location */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createLocation(@Valid @RequestBody LocationDTO loc) throws SavingException{
		Location location = locationConverter.LocationDTO2Location(loc);
		return new ResponseEntity<>(locationService.save(location), HttpStatus.OK);
		
	}

	/* get all locations, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Location> getAllLocationes() {
		return locationService.findAll();
	}

	/* get an location by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Location> getLocation(
			@PathVariable(value = "id") Long locationId) {
		Location location = locationService.findOne(locationId);

		if (location == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(location);
	}

	/* update location by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Location> updateLocation(
			@PathVariable(value = "id") Long locationId,
			@Valid @RequestBody LocationDTO loc) throws Exception{

		Location location = locationService.findOne(locationId);
		if (location == null) {
			return ResponseEntity.notFound().build();
		}

		
		location.setName(loc.getName());
		location.setDescription(loc.getDescription());
		location.setAddress(addressService.findOne(loc.getAddress_id()));

		Location updateLocation = locationService.save(location);
		return ResponseEntity.ok().body(updateLocation);
	}

	/* delete Location */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteLocation(
			@PathVariable(value = "id") Long locId) throws SavingException, BadRequestException, ResourceNotFoundException {
		logger.info("Deleting location id " + locId);
		locationService.delete(locId);
		return new ResponseEntity<>("Successfully deleted location", HttpStatus.OK);
	}
}
