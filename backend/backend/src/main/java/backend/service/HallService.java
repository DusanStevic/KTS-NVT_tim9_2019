package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.model.Hall;
import backend.model.Sector;
import backend.repository.HallRepository;

@Service
public class HallService {

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	SectorService sectorService;
	
	public Hall save(Hall b) {
		return hallRepository.save(b);
	}

	public Hall findOne(Long id) {
		return hallRepository.getOne(id);
	}

	public List<Hall> findAll() {
		return hallRepository.findAll();
	}

	public Page<Hall> findAll(Pageable page) {
		return hallRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		hallRepository.deleteById(id);
	}
	
	public ResponseEntity<String> delete(Long id) {
		Hall h = findOne(id);
		if(!h.equals(null) && !h.isDeleted()) {
			h.setDeleted(true);
			for(Sector s : h.getSectors()) {
				sectorService.delete(s.getId());
			}
			save(h);
			return ResponseEntity.ok().body("Successfully deleted");
		}else {
			return ResponseEntity.badRequest().body("Could not find requested hall");
		}
	}
}
