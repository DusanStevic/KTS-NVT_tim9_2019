package backend.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.dto.charts.DateIntervalDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Event;
import backend.model.EventDay;
import backend.model.EventSector;
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
		return eventRepository.findById(id).orElse(null);
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

	public Event getOneEvent(Long eventId) throws ResourceNotFoundException{
		Event e = findOne(eventId);
		if(e == null){
			throw new ResourceNotFoundException("Could not find requested event");
		}
		
		return e;
	}
	
	public void delete(Long eventID) throws ResourceNotFoundException {
		Event e = findOne(eventID);
		if (e != null && !e.isDeleted()) {
			e.setDeleted(true);
			for(EventDay ed : e.getEventDays()) {
				eventDayService.delete(ed.getId());
			}
			for(EventSector es : e.getEventSectors()) {
				eventSectorService.delete(es.getId());
			}
			
			save(e);
		} else {
			throw new ResourceNotFoundException("Could not find requested event");
		}
	}

	public Event update(Long eventId, Event event) throws ResourceNotFoundException {
		Event e = findOne(eventId);
		if (e != null && !e.isDeleted()) {
			e.setEvent(event);
			return save(e);
		} else {
			throw new ResourceNotFoundException("Could not find requested event");
		}
	}

	public List<Event> findByInterval(@Valid DateIntervalDTO interval) {
		return eventRepository.findAllByInterval(interval.getStartDate(),
				interval.getEndDate());
	}

	public List<Event> findAllActive() {
		return eventRepository.findAllActive();
	}
}
