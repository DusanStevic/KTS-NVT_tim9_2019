package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.dto.HallDTO;
import backend.exceptions.ResourceNotFoundException;
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
		return hallRepository.findById(id).orElse(null);
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
	
	public void delete(Long id) throws ResourceNotFoundException {
		Hall h = findOne(id);
		if(h != null && !h.isDeleted()) {
			h.setDeleted(true);
			for(Sector s : h.getSectors()) {
				sectorService.delete(s.getId());
			}
			save(h);
			//return ResponseEntity.ok().body("Successfully deleted");
		}else {
			throw new ResourceNotFoundException("Could not find requested hall");
		}
	}
	
	public Hall update(Long id, HallDTO h) throws ResourceNotFoundException {
		Hall hall = findOne(id);
		if(hall == null || hall.isDeleted()) 
			throw new ResourceNotFoundException("Could not find requested hall");
		
		if (!h.getName().trim().equals("")) {
			hall.setName(h.getName());
		}
		return save(hall);
	}
}
