package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.model.Sector;
import backend.repository.SectorRepository;

@Service
public class SectorService {
	@Autowired
	private SectorRepository sectorRepository;

	public Sector save(Sector b) {
		return sectorRepository.save(b);
	}

	public Sector findOne(Long id) {
		return sectorRepository.getOne(id);
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
}
