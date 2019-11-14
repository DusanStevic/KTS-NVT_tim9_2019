package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.model.Reservation;
import backend.model.Ticket;
import backend.repository.TicketRepository;

@Service
public class TicketService {
	@Autowired
	private TicketRepository ticketRepository;

	public Ticket save(Ticket b) {
		return ticketRepository.save(b);
	}

	public Ticket findOne(Long id) {
		return ticketRepository.getOne(id);
	}

	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	public Page<Ticket> findAll(Pageable page) {
		return ticketRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		ticketRepository.deleteById(id);
	}
	
	public List<Ticket> findAllByEventDayIDEventSectorID(Long ed_id, Long es_id){
		return ticketRepository.findAllByEventDayIDEventSectorID(ed_id, es_id);
	}
	
	public List<Ticket> findAllByLocation(Long id){
		return ticketRepository.findAllByLocation(id);
	}
	
	public ResponseEntity<String> delete(Long ID) {
		Ticket t = findOne(ID);
		if(!t.equals(null) && !t.isDeleted()) {
			t.setDeleted(true);
			save(t);
			return ResponseEntity.ok().body("Successfully deleted");
		}else {
			return ResponseEntity.badRequest().body("Could not find requested ticket");
		}
	}
}
