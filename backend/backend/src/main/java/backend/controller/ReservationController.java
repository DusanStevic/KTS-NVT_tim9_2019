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

	@Autowired
	UserService userService;

	@Autowired
	EventDayService eventDayService;

	@Autowired
	EventSectorService eventSectorService;

	/* saving address */
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Reservation createReservation(@Valid @RequestBody ReservationDTO reservationDTO, Principal user) {
		// provere: max selektovanih sedista, validnost podataka iz dto, kapacitet, lista sedista
		// validno sediste
		Reservation reservation = new Reservation();
		reservation.setPurchased(reservationDTO.isPurchased());/////////// ???
		reservation.setBuyer((RegisteredUser) userService.findByUsername(user.getName()));
		reservation.setReservationDate(new Date());
		Set<Ticket> tickets = new HashSet<>();
		List<Reservation> reservations = reservationService.findAll();
		boolean flag = false;
		EventSector sec = eventSectorService.findOne(reservationDTO.getSector_id());

		if (sec.getSector() instanceof SittingSector) {
			for (Reservation r : reservations) {
				for (Ticket t : r.getTickets()) {
					if (t.getEventDay().getId().equals(reservationDTO.getEventDay_id())
							&& t.getEventSector().getId().equals(reservationDTO.getSector_id())) {

						for (SeatDTO s : reservationDTO.getSedista()) {
							
							if (s.getRow() == t.getNumRow() && s.getCol() == t.getNumCol()) {
								flag = true;
								return null;
							}
						}

					}
				}
			}
		}//else provera kapaciteta
		if (!flag) {
			// System.out.println(reservationDTO.getSedista().size());
			if (!reservationDTO.getSedista().isEmpty()) {
				for (SeatDTO s : reservationDTO.getSedista()) {
					

					Ticket ticket = new Ticket();

					ticket.setEventSector(sec);
					if (sec.getSector() instanceof SittingSector) {
						ticket.setHasSeat(true);
						ticket.setNumRow(s.getRow());
						ticket.setNumCol(s.getCol());
					} else {
						ticket.setHasSeat(false);
					}
					ticket.setReservation(reservation);
					ticket.setEventDay(eventDayService.findOne(reservationDTO.getEventDay_id()));
					reservation.getTickets().add(ticket);

				}
			}else {
				Ticket ticket = new Ticket();

				ticket.setEventSector(sec);
				ticket.setHasSeat(false);
				
				ticket.setReservation(reservation);
				ticket.setEventDay(eventDayService.findOne(reservationDTO.getEventDay_id()));
				reservation.getTickets().add(ticket);
			}
			// reservation.setTickets(tickets);
		}
		return reservationService.save(reservation);
	}

	/* get all addresses, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Reservation> getAllReservations() {
		return reservationService.findAll();
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

	/* delete Address */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reservation> deleteReservation(@PathVariable(value = "id") Long reservationId) {
		Reservation r = reservationService.findOne(reservationId);

		if (r != null) {
			reservationService.remove(reservationId);
			logger.info("Address " + reservationId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Address " + reservationId + " not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
