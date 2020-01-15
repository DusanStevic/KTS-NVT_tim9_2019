package backend.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.dto.ReservationDTO;
import backend.dto.SeatDTO;
import backend.dto.SittingTicketDTO;
import backend.dto.StandingTicketDTO;
import backend.dto.TicketDTO;
import backend.exceptions.BadRequestException;
import backend.exceptions.ResourceNotFoundException;
import backend.model.EventDay;
import backend.model.EventSector;
import backend.model.RegisteredUser;
import backend.model.Reservation;
import backend.model.StandingSector;
import backend.model.Ticket;
import backend.repository.ReservationRepository;

@Service
public class ReservationService {
	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	UserService userService;

	@Autowired
	TicketService ticketService;

	@Autowired
	EventSectorService esService;

	@Autowired
	EventDayService eventDayService;

	public Reservation save(Reservation b) {
		return reservationRepository.save(b);
	}

	public Reservation findOne(Long id) throws ResourceNotFoundException {
		return reservationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested reservation"));
	}

	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	/*
	 * Not needed -> No logical deletion at reservation anymore public
	 * List<Reservation> findAllActive() { return
	 * reservationRepository.findAllActive(); }
	 */

	public Page<Reservation> findAll(Pageable page) {
		return reservationRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		reservationRepository.deleteById(id);
	}

	public Reservation createReservation(ReservationDTO res_dto, String username)
			throws ResourceNotFoundException, BadRequestException {
		Reservation r = new Reservation();
		r.setBuyer((RegisteredUser) userService.findByUsername(username));
		r.setPurchased(res_dto.isPurchased());
		EventDay ed = eventDayService.findOneNotDeleted(res_dto.getEventDay_id());

		for (TicketDTO t : res_dto.getTickets()) {

			EventSector es = esService.findOneNotDeleted(t.getEventSector_id());
			List<Ticket> tickets = ticketService.findAllByEventDayIDEventSectorID(res_dto.getEventDay_id(),
					t.getEventSector_id());

			if (ed.getEvent().getMaxTickets() < res_dto.getTickets().size()) {
				throw new BadRequestException("Max number of tickets exceeded");
			}

			if (t instanceof StandingTicketDTO && es.getSector() instanceof StandingSector) {

				int num_of_stand_tickets = ((StandingTicketDTO) t).getNumOfStandingTickets();

				if (((StandingSector) es.getSector()).getCapacity() < tickets.size() + num_of_stand_tickets) {
					throw new BadRequestException("Not enough room!");
				} else if (num_of_stand_tickets < 1) {
					throw new BadRequestException("There should be at least one ticket");
				} else {
					for (int i = 0; i < num_of_stand_tickets; i++) {
						Ticket ticket = new Ticket();
						ticket.setEventSector(es);
						ticket.setHasSeat(false);
						ticket.setEventDay(ed);
						ticket.setReservation(r);

						r.getTickets().add(ticket);
					}

				}
			} else if (t instanceof SittingTicketDTO && es.getSector() instanceof StandingSector) {

				int row = ((SittingTicketDTO) t).getRow();
				int col = ((SittingTicketDTO) t).getCol();
				// proveriti da li je zauzeto
				for (Ticket tic : tickets) {
					if (tic.getNumRow() == row && tic.getNumCol() == col) {
						throw new BadRequestException(
								"Seat in row: " + row + " and column: " + col + " has already been taken");
					}
				}
				// nije zauzeto
				Ticket ticket = new Ticket();
				ticket.setEventDay(ed);
				ticket.setEventSector(es);
				ticket.setHasSeat(true);
				ticket.setNumCol(col);
				ticket.setNumRow(row);
				ticket.setReservation(r);

				r.getTickets().add(ticket);
			} else {
				throw new BadRequestException("Incompatible types of sector and ticket");
			}
		}

		r.setReservationDate(new Date());
		return save(r);
	}

	// public Reservation createReservation_staro(ReservationDTO dto, String
	// username)
	// throws BadRequestException, ResourceNotFoundException {
	//
	// System.out.println("NEW RESERVATION");
	// Reservation r = new Reservation();
	// r.setReservationDate(new Date());
	// r.setBuyer((RegisteredUser) userService.findByUsername(username));
	// r.setPurchased(dto.isPurchased());
	// List<Ticket> tickets =
	// ticketService.findAllByEventDayIDEventSectorID(dto.getEventDay_id(),
	// dto.getSector_id());
	// System.out.println(tickets.size());
	// EventSector es = esService.findOne(dto.getSector_id());
	// if (es.getSector() instanceof StandingSector) {
	// StandingSector stand = (StandingSector) es.getSector();
	//
	// if (stand.getCapacity() < tickets.size() + dto.getNumOfStandingTickets()) {
	// // not ok, nema dovoljno mesta
	// throw new BadRequestException("Not enough room!");
	// } else if (dto.getNumOfStandingTickets() < 1) {
	// throw new BadRequestException("There should be at least one ticket");
	// } else {
	// // ok
	// for (int i = 0; i < dto.getNumOfStandingTickets(); i++) {
	// Ticket t = new Ticket();
	// t.setEventSector(es);
	// t.setHasSeat(false);
	// t.setEventDay(eventDayService.findOne(dto.getEventDay_id()));
	// t.setReservation(r);
	//
	// r.getTickets().add(t);
	// }
	//
	// return save(r);
	// }
	// } else {
	// // sitting sector
	// if (dto.getSedista().isEmpty()) {
	// // not ok, ako je sitting sector onda mora biti bar jedno sediste
	// throw new BadRequestException("No seats selected in a sitting sector!");
	// }
	// // sta je ovo joojj
	// List<SeatDTO> taken_seats = tickets.stream().map(t -> new
	// SeatDTO(t.getNumRow(), t.getNumCol()))
	// .filter(s -> dto.getSedista().contains(s)).collect(Collectors.toList());
	// if (taken_seats.isEmpty()) {
	// // ok
	// for (SeatDTO seat : dto.getSedista()) {
	// /*
	// * if(dto.getSedista().contains(seat)) { return
	// * ResponseEntity.badRequest().body(null); WTF?? ideja je bila da ne sme dva
	// * ista sedista, nz kako je ovo radilo??? }
	// */
	//
	// Ticket t = new Ticket();
	// t.setEventDay(eventDayService.findOne(dto.getEventDay_id()));
	// t.setEventSector(es);
	// t.setHasSeat(true);
	// t.setNumCol(seat.getCol());
	// t.setNumRow(seat.getRow());
	// t.setReservation(r);
	//
	// r.getTickets().add(t);
	// }
	//
	// return save(r);
	//
	// } else {
	// // not ok, postoji bar jedno zauzeto sediste
	// throw new BadRequestException("A seat has already been taken!");
	// }
	// }
	// }

	public void delete(Long ID) throws ResourceNotFoundException {
		Reservation r = findOne(ID);
		System.out.println(r.toString());
		remove(ID);
	}

	public Reservation cancelReservation(Long id) throws BadRequestException, ResourceNotFoundException {
		Reservation r = findOne(id);
		if (r.isCanceled()) {
			throw new BadRequestException("Reservation has already been canceled.");
		} else {
			r.setCanceled(true);

			return save(r);
		}

	}

	public Reservation purchaseReservation(Long id) throws BadRequestException, ResourceNotFoundException {
		Reservation r = findOne(id);
		if (r.isCanceled()) {
			throw new BadRequestException("Could not purchase canceled reservation.");
		} else if (r.isPurchased()) {
			throw new BadRequestException("Reservation has already been purchased.");
		} else {
			r.setPurchased(true);

			return save(r);
		}

	}

	public List<Reservation> findByEvent(Long event_id) {
		return reservationRepository.findByEvent(event_id);
	}

}
