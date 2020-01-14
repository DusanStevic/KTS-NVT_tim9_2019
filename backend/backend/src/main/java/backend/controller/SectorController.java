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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.converters.SectorConverter;
import backend.dto.SectorDTO;
import backend.dto.SittingSectorDTO;
import backend.dto.StandingSectorDTO;
import backend.exceptions.BadRequestException;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Sector;
import backend.model.SittingSector;
import backend.model.StandingSector;
import backend.service.SectorService;

@RestController
@RequestMapping("/api/sector")
public class SectorController {
	
	@Autowired
	SectorService sectorService;

	@Autowired
	SectorConverter sectorConverter;
	
	/* saving sector */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sector> createSector(@PathVariable(value = "id") Long hallId,@Valid @RequestBody SectorDTO sectorDTO) throws ResourceNotFoundException {
		if(sectorDTO instanceof StandingSectorDTO) {
			StandingSector sec = sectorConverter.StandingSectorDTO2Sector((StandingSectorDTO)sectorDTO, hallId); 
			return new ResponseEntity<>(sectorService.save(sec), HttpStatus.OK);
		}else{
			SittingSector sec = sectorConverter.SittingSectorDTO2Sector((SittingSectorDTO)sectorDTO, hallId); 
			return new ResponseEntity<>(sectorService.save(sec), HttpStatus.OK);
		}
	}

	/* get all sectors, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Sector> getAllSectors() {
		return sectorService.findAllNotDeleted();
	}

	/* get a sector by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSector(@PathVariable(value = "id") Long sectorId) throws ResourceNotFoundException {
		Sector sector = sectorService.findOneNotDeleted(sectorId);
		return new ResponseEntity<>(sector, HttpStatus.OK);
	}

	/* update sector by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sector> updateSector(@PathVariable(value = "id") Long sectorId,
			@Valid @RequestBody SectorDTO sectorDTO) throws BadRequestException, ResourceNotFoundException {
		if(sectorDTO instanceof StandingSectorDTO) {
			StandingSector sec = sectorConverter.StandingSectorDTO2StandingSector((StandingSectorDTO)sectorDTO); 
			return new ResponseEntity<>(sectorService.update(sectorId, sec), HttpStatus.OK);
		}else if(sectorDTO instanceof SittingSectorDTO) {
			SittingSector sec = sectorConverter.SittingSectorDTO2SittingSector((SittingSectorDTO)sectorDTO); 
			return new ResponseEntity<>(sectorService.update(sectorId, sec), HttpStatus.OK);
		}else {
			throw new BadRequestException("Invalid sector type");
		}

	}

	/* delete Sector */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteSector(@PathVariable(value = "id") Long sectorId)
			throws ResourceNotFoundException {
		sectorService.delete(sectorId);
		return new ResponseEntity<>("Successfully deleted sector", HttpStatus.OK);
	}
}
