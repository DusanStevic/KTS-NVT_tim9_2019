package backend.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.dto.HallDTO;
import backend.dto.HallUpdateDTO;
import backend.dto.SectorDTO;
import backend.dto.SittingSectorDTO;
import backend.dto.StandingSectorDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Hall;
import backend.model.Location;
import backend.model.SittingSector;
import backend.model.StandingSector;
import backend.service.LocationService;

@Component
public class HallConverter {
	
	@Autowired
	SectorConverter sectorConverter;
	
	@Autowired
	LocationService locationService;
	
	public Hall HallDTO2Hall(HallDTO dto) {
		Hall hall = new Hall();
		hall.setName(dto.getName());
		
		/*for(SectorDTO sec_dto : dto.getSectors()) {
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
		}*/
		
		return hall;
	}
	
	public Hall HallDTO2Hall(HallUpdateDTO dto) {
		Hall hall = new Hall();
		hall.setName(dto.getName());
		return hall;
	}
	
	public Hall HallDTO2Hall(HallDTO dto, Long locationId) throws ResourceNotFoundException {
		Hall hall = new Hall();
		hall.setName(dto.getName());
		Location loc = locationService.findOneNotDeleted(locationId);
		loc.getHalls().add(hall);
		hall.setLocation(loc);
		for(int i = 1; i <= dto.getSittingNr(); i++) {
			SittingSector sit = new SittingSector();
			sit.setName("Sitting Sector "+i);
			sit.setDeleted(false);
			sit.setHall(hall);
			hall.getSectors().add(sit);
		}
		
		for(int i = 1; i <= dto.getStandingNr(); i++) {
			StandingSector stand = new StandingSector();
			stand.setName("Standing Sector "+i);
			stand.setDeleted(false);
			stand.setHall(hall);
			hall.getSectors().add(stand);
		}
		/*for(SectorDTO sec_dto : dto.getSectors()) {
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
				System.out.println("nije ni jedan od tipova??");
				System.out.println(sec_dto.getClass());
			}
		}*/
		return hall;
	}
}
