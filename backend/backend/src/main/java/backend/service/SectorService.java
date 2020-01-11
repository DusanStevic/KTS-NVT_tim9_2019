package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.dto.SectorDTO;
import backend.dto.SittingSectorDTO;
import backend.dto.StandingSectorDTO;
import backend.exceptions.BadRequestException;
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

	/*
	 * public Sector create(SectorDTO sectorDTO) { Sector retVal; switch
	 * (sectorDTO.getSectorType().trim().toLowerCase()) { case "sitting":
	 * SittingSector s1 = new SittingSector(); s1.setName(sectorDTO.getName());
	 * s1.setHall(hallService.findOne(sectorDTO.getHall_id()));
	 * s1.setNumCols(sectorDTO.getNumCols()); s1.setNumRows(sectorDTO.getNumRows());
	 * retVal = s1; break; case "standing": StandingSector s2 = new
	 * StandingSector(); s2.setName(sectorDTO.getName());
	 * s2.setHall(hallService.findOne(sectorDTO.getHall_id()));
	 * s2.setCapacity(sectorDTO.getCapacity()); retVal = s2; break; default: return
	 * null; }
	 * 
	 * return save(retVal); }
	 */

	public void delete(Long id) throws ResourceNotFoundException {
		Sector s = findOneNotDeleted(id);
		s.setDeleted(true);
		save(s);
	}

	public Sector update(Long id, SectorDTO s) throws BadRequestException, ResourceNotFoundException {
		Sector sector = findOneNotDeleted(id);

		if (!s.getName().equals("")) {
			sector.setName(s.getName());
		} 

		if (s instanceof SittingSectorDTO) {
			SittingSector s1 = (SittingSector) sector;

			if (((SittingSectorDTO) s).getNumCols() > 0) {
				s1.setNumCols(((SittingSectorDTO) s).getNumCols());
			}
			if (((SittingSectorDTO) s).getNumRows() > 0) {
				s1.setNumRows(((SittingSectorDTO) s).getNumRows());
			}

			Sector updated = save(sector);
			return updated;

		} else if (s instanceof StandingSectorDTO) {
			StandingSector s2 = (StandingSector) sector;

			if (((StandingSectorDTO) s).getCapacity() > 0) {
				s2.setCapacity(((StandingSectorDTO) s).getCapacity());
			}
			Sector updated = save(sector);
			return updated;
		} else {
			throw new BadRequestException("Invalid sector type");
		}
	}
}
