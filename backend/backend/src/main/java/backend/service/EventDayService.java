package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.exceptions.ResourceNotFoundException;
import backend.model.EventDay;
import backend.repository.EventDayRepository;

@Service
public class EventDayService {
	@Autowired
	private EventDayRepository eventDayRepository;

	public EventDay save(EventDay b) {
		return eventDayRepository.save(b);
	}

	public EventDay findOne(Long id) throws ResourceNotFoundException {
		return eventDayRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Could not find requested event day"));
	}

	public List<EventDay> findAll() {
		return eventDayRepository.findAll();
	}

	public Page<EventDay> findAll(Pageable page) {
		return eventDayRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		eventDayRepository.deleteById(id);
	}
	
	public void delete(Long ID) throws ResourceNotFoundException {
		EventDay ed = findOne(ID);
		if(ed != null && !ed.isDeleted()) {
			ed.setDeleted(true);
			save(ed);
		}else {
			throw new ResourceNotFoundException("Could not find event day");
		}
	}
	
	public EventDay update(Long id, EventDay e) throws ResourceNotFoundException {
		EventDay eventDay = findOne(id);
		if(eventDay != null && !eventDay.isDeleted()) {
			eventDay.setDescription(e.getDescription());
			eventDay.setName(e.getName());
			//eventDay.setStatus(e.getStatus());
			//eventDay.setDate(e.getDate());
			return save(eventDay);
		}else {
			throw new ResourceNotFoundException("Could not find event day");
		}
		
	}

}
