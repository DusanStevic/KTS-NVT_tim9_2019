package backend.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
//can copypaste everywhere
import java.util.List;
import java.util.Set;

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
@RequestMapping("/api/reservation")
public class ReservationController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ReservationService reservationService;

	

	/* saving address */
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reservation> createReservation(@Valid @RequestBody ReservationDTO reservationDTO, Principal user) {
		// provere: max selektovanih sedista, validnost podataka iz dto
		// validno sediste
		
		return reservationService.createReservation(reservationDTO, user);
	}

	/* get all addresses, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Reservation> getAllReservations() {
		return reservationService.findAll();
	}

	/* get all addresses, permitted for all */
	@GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Reservation> getAllActiveReservations() {
		return reservationService.findAllActive();
	}
	
	/* get an address by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reservation> getReservation(@PathVariable(value = "id") Long reservationId) {
		Reservation reservation = reservationService.findOne(reservationId);

		if (reservation == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(reservation);
	}

	/* update address by id */
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reservation> updateReservation(@PathVariable(value = "id") Long reservationId,
			@Valid @RequestBody ReservationDTO r) {

		Reservation reservation = reservationService.findOne(reservationId);
		if (reservation == null) {
			return ResponseEntity.notFound().build();
		}

		/*
		 * 
		 */

		Reservation updateReservation = reservationService.save(reservation);
		return ResponseEntity.ok().body(updateReservation);
	}

	/* delete reservation */
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteReservation(@PathVariable(value = "id") Long reservationId) {
		return reservationService.delete(reservationId);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYS_ADMIN')")
	@PutMapping(value = "cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reservation> cancelReservation(@PathVariable(value = "id") Long reservationId) throws Exception {
		return new ResponseEntity<>(reservationService.cancelReservation(reservationId), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PutMapping(value = "purchase/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reservation> purchaseReservation(@PathVariable(value = "id") Long reservationId) throws Exception {
		return new ResponseEntity<>(reservationService.purchaseReservation(reservationId), HttpStatus.OK);
	}
	
}
