package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.dto.AddressDTO;
import backend.model.Address;
import backend.model.Event;
import backend.repository.EventRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	EventDayService eventDayService;
	
	@Autowired
	EventSectorService eventSectorService;
	
	public Event save(Event b) {
		return eventRepository.save(b);
	}

	public Event findOne(Long id) {
		return eventRepository.getOne(id);
	}

	public List<Event> findAll() {
		return eventRepository.findAll();
	}

	public Page<Event> findAll(Pageable page) {
		return eventRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		eventRepository.deleteById(id);
	}
	
	public ResponseEntity<String> delete(Long eventID) {
		Event e = findOne(eventID);
		if(!e.equals(null) && !e.isDeleted()) {
			e.setDeleted(true);
			e.getEventDays().forEach(ed -> eventDayService.delete(ed.getId()));
			e.getEventSectors().forEach(es -> eventSectorService.delete(es.getId()));
			save(e);
			return ResponseEntity.ok().body("Successfully deleted");
		}else {
			return ResponseEntity.badRequest().body("Could not find requested event");
		}
	}
	
	public ResponseEntity<Event> update(Long eventId, Event event){
		Event e = findOne(eventId);
		if(!e.equals(null) && !e.isDeleted()) {
			e.setEvent(event);
			
			return ResponseEntity.ok().body(save(e));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
