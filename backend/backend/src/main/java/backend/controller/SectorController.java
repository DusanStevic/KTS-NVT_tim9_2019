package backend.controller;

//can copypaste everywhere
import java.util.List;

import javax.validation.Valid;

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

import backend.dto.SectorDTO;
import backend.exceptions.BadRequestException;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Sector;
import backend.service.SectorService;

@RestController
@RequestMapping("/api/sector")
public class SectorController {
	//private Logger logger = LoggerFactory.getLogger(this.getClass());

	// Find By hall

	@Autowired
	SectorService sectorService;

	
	 /* saving sector	 */ 
	/* @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	 @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE) 
	 public ResponseEntity<Sector> createSector(@Valid@RequestBody SectorDTO sectorDTO) { 
		 Sector retval = sectorService.create(sectorDTO); 
		 if(retval == null){
			return ResponseEntity.badRequest().build();
		 }else{
			 return ResponseEntity.ok().body(retval);
		 }
	 }*/
	 

	
	 /* get all sectors, permitted for all*/
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Sector> getAllSectores() {
		return sectorService.findAllNotDeleted();
	}


	/* get a sector by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSector( @PathVariable(value = "id") Long sectorId) throws ResourceNotFoundException {
		Sector sector = sectorService.findOneNotDeleted(sectorId);
		return new ResponseEntity<>(sector, HttpStatus.OK);
	}

	/* update sector by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sector> updateSector(
			@PathVariable(value = "id") Long sectorId,
			@Valid @RequestBody SectorDTO s) throws BadRequestException, ResourceNotFoundException {
		return new ResponseEntity<>(sectorService.update(sectorId, s), HttpStatus.OK);

	}

	/* delete Sector */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteSector(
			@PathVariable(value = "id") Long sectorId) throws ResourceNotFoundException {
		sectorService.delete(sectorId);
		return new ResponseEntity<>("Successfully deleted sector", HttpStatus.OK);
	}
}
