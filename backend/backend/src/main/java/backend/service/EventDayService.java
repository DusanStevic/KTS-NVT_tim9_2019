package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.exceptions.ResourceNotFoundException;
import backend.model.Address;
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
		return eventDayRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested event day"));
	}

	public EventDay findOneNotDeleted(Long id) throws ResourceNotFoundException {
		return eventDayRepository.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested event day"));
	}

	public List<EventDay> findAll() {
		return eventDayRepository.findAll();
	}

	public List<EventDay> findAllNotDeleted() {
		return eventDayRepository.findAllByDeleted(false);
	}

	public Page<EventDay> findAllNotDeleted(Pageable page){
		return eventDayRepository.findAllByDeleted(false, page);
	}
	
	public Page<EventDay> findAll(Pageable page) {
		return eventDayRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		eventDayRepository.deleteById(id);
	}

	public void delete(Long ID) throws ResourceNotFoundException {
		EventDay ed = findOneNotDeleted(ID);
		ed.setDeleted(true);
		save(ed);
	}

	public EventDay update(Long id, EventDay e) throws ResourceNotFoundException {
		EventDay eventDay = findOneNotDeleted(id);
		eventDay.setDescription(e.getDescription());
		eventDay.setName(e.getName());
		// eventDay.setStatus(e.getStatus());
		// eventDay.setDate(e.getDate());
		return save(eventDay);

	}

}
