package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.exceptions.ResourceNotFoundException;
import backend.model.Sector;
import backend.model.SittingSector;
import backend.model.StandingSector;
import backend.repository.SectorRepository;

@Service
public class SectorService {
	@Autowired
	private SectorRepository sectorRepository;

	@Autowired
	private HallService hallService;

	public Sector save(Sector b) {
		return sectorRepository.save(b);
	}

	public Sector findOne(Long id) throws ResourceNotFoundException {
		return sectorRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested sector"));
	}

	public Sector findOneNotDeleted(Long id) throws ResourceNotFoundException {
		return sectorRepository.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested sector"));
	}

	public List<Sector> findAllNotDeleted() {
		return sectorRepository.findAllByDeleted(false);
	}

	public Page<Sector> findAllNotDeleted(Pageable page) {
		return sectorRepository.findAllByDeleted(false, page);
	}

	public List<Sector> findAll() {
		return sectorRepository.findAll();
	}

	public Page<Sector> findAll(Pageable page) {
		return sectorRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		sectorRepository.deleteById(id);
	}

	

	public void delete(Long id) throws ResourceNotFoundException {
		Sector s = findOneNotDeleted(id);
		s.setDeleted(true);
		s.setHall(null);
		save(s);
	}

	public StandingSector update(Long id, StandingSector s) throws ResourceNotFoundException {
		StandingSector sector = (StandingSector) findOneNotDeleted(id);

		if (!s.getName().equals("")) {
			sector.setName(s.getName());
		}
		sector.setCapacity(s.getCapacity());
		return (StandingSector) save(sector);
	}
	
	public SittingSector update(Long id, SittingSector s) throws ResourceNotFoundException {
		SittingSector sector = (SittingSector) findOneNotDeleted(id);

		if (!s.getName().equals("")) {
			sector.setName(s.getName());
		}
		sector.setNumCols(s.getNumCols());
		sector.setNumRows(s.getNumRows());
		return (SittingSector) save(sector);
	}
}
