package backend.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.dto.HallDTO;
import backend.dto.SectorDTO;
import backend.dto.SittingSectorDTO;
import backend.dto.StandingSectorDTO;
import backend.model.Hall;
import backend.model.SittingSector;
import backend.model.StandingSector;

@Component
public class HallConverter {
	
	@Autowired
	SectorConverter sectorConverter;
	
	public Hall HallDTO2Hall(HallDTO dto) {
		Hall hall = new Hall();
		hall.setName(dto.getName());

		for(SectorDTO sec_dto : dto.getSectors()) {
			if(sec_dto instanceof SittingSectorDTO) {
				SittingSector sit = sectorConverter.SittingSectorDTO2SittingSector((SittingSectorDTO)sec_dto);
				sit.setHall(hall);
				hall.getSectors().add(sit);
			}else if(sec_dto instanceof StandingSectorDTO) {
				StandingSector stand = sectorConverter.StandingSectorDTO2StandingSector((StandingSectorDTO) sec_dto);
				stand.setHall(hall);
				hall.getSectors().add(stand);
			}else {
				//greska
			}
		}
		
		return hall;
	}
}
