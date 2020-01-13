package backend.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public Hall findOne(Long id) throws ResourceNotFoundException {
		return hallRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested hall"));
	}

	@javax.transaction.Transactional
	public Hall findOneNotDeleted(Long id) throws ResourceNotFoundException {
		return hallRepository.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested hall"));
	}

	public List<Hall> findAllNotDeleted() {
		return hallRepository.findAllByDeleted(false);
	}

	public Page<Hall> findAllNotDeleted(Pageable page) {
		return hallRepository.findAllByDeleted(false, page);
	}

	public List<Hall> findAll() {
		return hallRepository.findAll();
	}

	public Page<Hall> findAll(Pageable page) {
		return hallRepository.findAll(page);
	}

	/*
	 * @Transactional public void remove(Long id) { hallRepository.deleteById(id); }
	 */

	@Transactional
	public void delete(Long id) throws ResourceNotFoundException {
		Hall h = findOneNotDeleted(id);
		h.setDeleted(true);
		if (h.getSectors() != null) {
			for (Sector s : h.getSectors()) {
				sectorService.delete(s.getId());
			}
		}
		save(h);
	}

	public Hall update(Long id, Hall h) throws ResourceNotFoundException {
		Hall hall = findOneNotDeleted(id);

		if (!h.getName().trim().equals("")) {
			hall.setName(h.getName());
		}
		return save(hall);
	}
}
