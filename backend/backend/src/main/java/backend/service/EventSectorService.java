package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.exceptions.ResourceNotFoundException;
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
		return eventSectorRepository.findById(id).orElse(null);
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
	
	public void delete(Long ID) throws ResourceNotFoundException {
		EventSector es = findOne(ID);
		if(es != null && !es.isDeleted()) {
			es.setDeleted(true);
			save(es);
		}else {
			throw new ResourceNotFoundException("Could not find requested event sector");
		}
	}
	
	public EventSector update(Long id, double price) throws ResourceNotFoundException {
		EventSector es = findOne(id);
		if(es != null && !es.isDeleted()) {
			es.setPrice(price);
			return save(es);
		}else {
			throw new ResourceNotFoundException("Could not find requested sector of an event");
		}
	}
}
