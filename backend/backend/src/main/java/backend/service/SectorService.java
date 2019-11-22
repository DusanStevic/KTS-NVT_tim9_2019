package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.dto.SectorDTO;
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

	public Sector create(SectorDTO sectorDTO) {
		Sector retVal;
		switch (sectorDTO.getSectorType().trim().toLowerCase()) {
		case "sitting":
			SittingSector s1 = new SittingSector();
			s1.setName(sectorDTO.getName());
			s1.setHall(hallService.findOne(sectorDTO.getHall_id()));
			s1.setNumCols(sectorDTO.getNumCols());
			s1.setNumRows(sectorDTO.getNumRows());
			retVal = s1;
			break;
		case "standing":
			StandingSector s2 = new StandingSector();
			s2.setName(sectorDTO.getName());
			s2.setHall(hallService.findOne(sectorDTO.getHall_id()));
			s2.setCapacity(sectorDTO.getCapacity());
			retVal = s2;
			break;
		default:
			return null;
		}

		return save(retVal);
	}
	
	public ResponseEntity<String> delete(Long id) {
		Sector s = findOne(id);
		if(!s.equals(null) && !s.isDeleted()) {
			s.setDeleted(true);
			save(s);
			return ResponseEntity.ok().body("Successfully deleted");
		}else {
			return ResponseEntity.badRequest().body("Could not find requested sector");
		}
	}
}
