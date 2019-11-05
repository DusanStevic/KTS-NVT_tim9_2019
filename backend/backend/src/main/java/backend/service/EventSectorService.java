package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.model.EventSector;
import backend.repository.EventSectorRepository;

@Service
public class EventSectorService {
	@Autowired
	private EventSectorRepository eventSectorRepository;

	public EventSector save(EventSector b) {
		return eventSectorRepository.save(b);
	}

	public EventSector findOne(Long id) {
		return eventSectorRepository.getOne(id);
	}

	public List<EventSector> findAll() {
		return eventSectorRepository.findAll();
	}

	public Page<EventSector> findAll(Pageable page) {
		return eventSectorRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		eventSectorRepository.deleteById(id);
	}
}
