package backend.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.dto.SectorDTO;
import backend.dto.SittingSectorDTO;
import backend.dto.StandingSectorDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Sector;
import backend.model.SittingSector;
import backend.model.StandingSector;
import backend.service.HallService;

@Component
public class SectorConverter {

	@Autowired
	HallService hallService;
	
	public StandingSector StandingSectorDTO2StandingSector(StandingSectorDTO dto) {
		StandingSector sec = new StandingSector();
		sec.setCapacity(dto.getCapacity());
		sec.setName(dto.getName());
		
		return sec;
	}
	
	public SittingSector SittingSectorDTO2SittingSector(SittingSectorDTO dto) {
		SittingSector sec = new SittingSector();
		sec.setNumCols(dto.getNumCols());
		sec.setNumRows(dto.getNumRows());
		sec.setName(dto.getName());
		
		return sec;
	}
	
	public StandingSector StandingSectorDTO2Sector(StandingSectorDTO dto, Long hallId) throws ResourceNotFoundException {
		StandingSector sec = new StandingSector();
		sec.setName(dto.getName());
		sec.setCapacity(dto.getCapacity());
		sec.setHall(hallService.findOneNotDeleted(hallId));
		return sec;
	}
	
	public SittingSector SittingSectorDTO2Sector(SittingSectorDTO dto, Long hallId) throws ResourceNotFoundException {
		SittingSector sec = new SittingSector();
		sec.setName(dto.getName());
		sec.setNumCols(dto.getNumCols());
		sec.setNumRows(dto.getNumRows());
		sec.setHall(hallService.findOneNotDeleted(hallId));
		return sec;
	}
}
