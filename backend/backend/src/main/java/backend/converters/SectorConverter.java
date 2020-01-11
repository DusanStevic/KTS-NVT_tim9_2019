package backend.converters;

import org.springframework.stereotype.Component;

import backend.dto.SectorDTO;
import backend.dto.SittingSectorDTO;
import backend.dto.StandingSectorDTO;
import backend.model.SittingSector;
import backend.model.StandingSector;

@Component
public class SectorConverter {

	
	
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
}
