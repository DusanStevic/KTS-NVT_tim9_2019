package backend.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.dto.ReservationDTO;
import backend.dto.SeatDTO;
import backend.model.Address;
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

	public Reservation findOne(Long id) {
		return reservationRepository.getOne(id);
	}

	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	public List<Reservation> findAllActive() {
		return reservationRepository.findAllActive();
	}

	public Page<Reservation> findAll(Pageable page) {
		return reservationRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		reservationRepository.deleteById(id);
	}

	public ResponseEntity<Reservation> createReservation(ReservationDTO dto, Principal user) {
		System.out.println("NEW RESERVATION");
		Reservation r = new Reservation();
		r.setReservationDate(new Date());
		r.setBuyer((RegisteredUser) userService.findByUsername(user.getName()));
		r.setDeleted(false);
		r.setPurchased(dto.isPurchased());
		List<Ticket> tickets = ticketService.findAllByEventDayIDEventSectorID(dto.getEventDay_id(), dto.getSector_id());
		System.out.println(tickets.size());
		EventSector es = esService.findOne(dto.getSector_id());
		if (es.getSector() instanceof StandingSector) {
			StandingSector stand = (StandingSector) es.getSector();
			if(stand.getCapacity() < tickets.size() + dto.getNumOfStandingTickets()) {
				//not ok, nema dovoljno mesta
				return ResponseEntity.badRequest().body(null);
			}else {
				//ok
				for(int i=0; i<dto.getNumOfStandingTickets(); i++) {
					Ticket t = new Ticket();
					t.setEventSector(es);
					t.setHasSeat(false);
					t.setEventDay(eventDayService.findOne(dto.getEventDay_id()));
					t.setReservation(r);
					
					r.getTickets().add(t);
				}
				
				return ResponseEntity.ok().body(save(r));
			}
		} else {
			//sitting sector
			if(dto.getSedista().isEmpty()) {
				//not ok, ako je sitting sector onda mora biti bar jedno sediste
				return ResponseEntity.badRequest().body(null);
			}
			List<SeatDTO> seats = tickets.stream().map(t -> new SeatDTO(t.getNumRow(), t.getNumCol()))
					.filter(s -> dto.getSedista().contains(s)).collect(Collectors.toList());
			if (seats.isEmpty()) {
				// ok
				for(SeatDTO seat : dto.getSedista()) {
					if(dto.getSedista().contains(seat)) {
						return ResponseEntity.badRequest().body(null);
					}
					Ticket t = new Ticket();
					t.setEventDay(eventDayService.findOne(dto.getEventDay_id()));
					t.setEventSector(es);
					t.setHasSeat(true);
					t.setNumCol(seat.getCol());
					t.setNumRow(seat.getRow());
					t.setReservation(r);
					
					r.getTickets().add(t);
				}
				
				return ResponseEntity.ok().body(save(r));
			} else {
				// not ok, postoji bar jedno zauzeto sediste
				return ResponseEntity.badRequest().body(null);
			}
		}
	}
	
	public ResponseEntity<String> delete(Long ID) {
		Reservation r = findOne(ID);
		if(!r.equals(null) && !r.isDeleted()) {
			r.setDeleted(true);
			for(Ticket t : r.getTickets()) {
				ticketService.delete(t.getId());
			}
			save(r);
			return ResponseEntity.ok().body("Successfully deleted");
		}else {
			return ResponseEntity.badRequest().body("Could not find requested reservation");
		}
	}

}
