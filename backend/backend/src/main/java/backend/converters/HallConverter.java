package backend.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.dto.HallDTO;
import backend.dto.SectorDTO;
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
		hall.setNumberOfSectors(dto.getNumber_of_sectors());
		for(SectorDTO sec_dto : dto.getSectors()) {
			if(sec_dto.getSector_type().toLowerCase().equals("sitting")) {
				SittingSector sit = sectorConverter.SectorDTO2SittingSector(sec_dto);
				sit.setHall(hall);
				hall.getSectors().add(sit);
			}else if(sec_dto.getSector_type().toLowerCase().equals("standing")) {
				StandingSector stand = sectorConverter.SectorDTO2StandingSector(sec_dto);
				stand.setHall(hall);
				hall.getSectors().add(stand);
			}else {
				//greska
			}
		}
		
		return hall;
	}
}
