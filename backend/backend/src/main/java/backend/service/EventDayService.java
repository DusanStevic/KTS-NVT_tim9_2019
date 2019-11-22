package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.model.EventDay;
import backend.repository.EventDayRepository;

@Service
public class EventDayService {
	@Autowired
	private EventDayRepository eventDayRepository;

	public EventDay save(EventDay b) {
		return eventDayRepository.save(b);
	}

	public EventDay findOne(Long id) {
		return eventDayRepository.getOne(id);
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
	
	public ResponseEntity<String> delete(Long ID) {
		EventDay ed = findOne(ID);
		if(!ed.equals(null) && !ed.isDeleted()) {
			ed.setDeleted(true);
			save(ed);
			return ResponseEntity.ok().body("Successfully deleted");
		}else {
			return ResponseEntity.badRequest().body("Could not find requested event day");
		}
	}

}
