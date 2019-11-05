package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.model.Event;
import backend.repository.EventRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;

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
}
