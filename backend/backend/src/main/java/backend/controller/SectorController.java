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
@RequestMapping("/api/sector")
public class SectorController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// Find By hall

	@Autowired
	SectorService sectorService;

	
	 /* saving sector	 */ 
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	 @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE) 
	 public ResponseEntity<Sector> createSector(@Valid@RequestBody SectorDTO sectorDTO) { 
		 Sector retval = sectorService.create(sectorDTO); 
		 if(retval == null){
			return ResponseEntity.badRequest().build();
		 }else{
			 return ResponseEntity.ok().body(retval);
		 }
	 }
	 

	
	 /* get all sectors, permitted for all*/
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Sector> getAllSectores() {
		return sectorService.findAll();
	}


	/* get a sector by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sector> getSector( @PathVariable(value = "id") Long sectorId) {
		
		Sector sector = sectorService.findOne(sectorId);
		if (sector == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(sector);
	}

	/* update sector by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sector> updateSector(
			@PathVariable(value = "id") Long sectorId,
			@Valid @RequestBody SectorDTO s) {

		Sector sector = sectorService.findOne(sectorId);

		if (sector == null) {
			return ResponseEntity.notFound().build();
		}

		if (!s.getName().equals("")) {
			sector.setName(s.getName());
		}

		if (s.getSectorType().trim().toLowerCase().equals("sitting")) {
			SittingSector s1 = (SittingSector) sector;

			if (s.getNumCols() > 0) {
				s1.setNumCols(s.getNumCols());
			}
			if (s.getNumRows() > 0) {
				s1.setNumRows(s.getNumRows());
			}

			Sector updated = sectorService.save(sector);
			return ResponseEntity.ok().body(updated);

		} else if (s.getSectorType().trim().toLowerCase().equals("standing")) {
			StandingSector s2 = (StandingSector) sector;

			if (s.getCapacity() > 0) {
				s2.setCapacity(s.getCapacity());
			}
			Sector updated = sectorService.save(sector);
			return ResponseEntity.ok().body(updated);
		} else {

			return ResponseEntity.badRequest().build();
		}

	}

	/* delete Sector */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sector> deleteSector(
			@PathVariable(value = "id") Long sectorId) {
		Sector s = sectorService.findOne(sectorId);

		if (s != null) {
			sectorService.remove(sectorId);
			logger.info("Sector " + sectorId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Sector " + sectorId + " not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
