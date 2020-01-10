package backend.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.exceptions.ResourceNotFoundException;
import backend.model.Ticket;
import backend.repository.TicketRepository;

@Service
public class TicketService {
	@Autowired
	private TicketRepository ticketRepository;

	public Ticket save(Ticket b) {
		return ticketRepository.save(b);
	}

	public Ticket findOne(Long id) throws ResourceNotFoundException {
		return ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested address"));
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

	public List<Ticket> findAllByEventDayIDEventSectorID(Long ed_id, Long es_id) {
		return ticketRepository.findAllByEventDayIDEventSectorID(ed_id, es_id);
	}

	public List<Ticket> findAllByLocationDate(Long id, Date date) {
		return ticketRepository.findAllByLocationDate(id, date);
	}

	public List<Ticket> findAllByLocation(Long id) {
		return ticketRepository.findAllByLocation(id);
	}

	/*public void delete(Long ID) {
		// Arpad: Izbacio sam logicko brisanje, nema smisla kod ticketa
		ticketRepository.deleteById(ID);

	}*/

	public List<Ticket> findAllByEvent(Long event_id) {
		return ticketRepository.findAllByEvent(event_id);
	}
}
