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

	public Event findOne(Long id) throws ResourceNotFoundException {
		return eventRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested event"));
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

	public void delete(Long eventID) throws ResourceNotFoundException {
		Event e = findOneNotDeleted(eventID);
		e.setDeleted(true);
		if (e.getEventDays() != null) {
			for (EventDay ed : e.getEventDays()) {
				eventDayService.delete(ed.getId());
			}
		}

		if (e.getEventSectors() != null) {
			for (EventSector es : e.getEventSectors()) {
				eventSectorService.delete(es.getId());
			}
		}
		save(e);
	}

	public Event update(Long eventId, Event event) throws ResourceNotFoundException {
		Event e = findOneNotDeleted(eventId);
		// e.setEvent(event);
		e.setDescription(event.getDescription());
		e.setName(event.getName());
		e.setEventType(event.getEventType());
		//e.setMaxTickets(event.getMaxTickets());
		// lokacija?
		return save(e);

	}

	public List<Event> findByInterval(@Valid DateIntervalDTO interval) {
		return eventRepository.findAllByInterval(interval.getStartDate(), interval.getEndDate());
	}

	public Event findOneNotDeleted(Long id) throws ResourceNotFoundException {
		Event e = eventRepository.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested event"));
		return e;
	}

	public List<Event> findAllNotDeleted() {
		return eventRepository.findAllByDeleted(false);
	}

	public Page<Event> findAllNotDeleted(Pageable page) {
		return eventRepository.findAllByDeleted(false, page);
	}
}
