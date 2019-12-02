package backend.converters;

import org.springframework.stereotype.Component;

import backend.dto.SectorDTO;
import backend.model.SittingSector;
import backend.model.StandingSector;

@Component
public class SectorConverter {

	
	
	public StandingSector SectorDTO2StandingSector(SectorDTO dto) {
		StandingSector sec = new StandingSector();
		sec.setCapacity(dto.getCapacity());
		sec.setName(dto.getName());
		
		return sec;
	}
	
	public SittingSector SectorDTO2SittingSector(SectorDTO dto) {
		SittingSector sec = new SittingSector();
		sec.setNumCols(dto.getNumCols());
		sec.setNumRows(dto.getNumRows());
		sec.setName(dto.getName());
		
		return sec;
	}
}
