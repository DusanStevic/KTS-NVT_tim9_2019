package backend.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.dto.EventSectorDTO;
import backend.model.EventSector;
import backend.service.SectorService;

@Component
public class EventSectorConverter {
	
	@Autowired
	SectorService sectorService;
	
	public EventSector EventSectorDTO2EventSector(EventSectorDTO dto) {
		EventSector es = new EventSector();
		es.setPrice(dto.getPrice());
		es.setSector(sectorService.findOne(dto.getSector_id()));
		
		return es;
	}
}
