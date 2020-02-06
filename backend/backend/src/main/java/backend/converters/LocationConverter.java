package backend.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.dto.LocationDTO;
import backend.dto.LocationUpdateDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Location;
import backend.service.AddressService;

@Component
public class LocationConverter {

	@Autowired
	AddressService addressService;
	
	@Autowired
	HallConverter hallConverter;
	
	public Location LocationDTO2Location(LocationDTO dto) throws ResourceNotFoundException {
		Location loc = new Location();
		loc.setDescription(dto.getDescription());
		loc.setName(dto.getName());
		loc.setAddress(dto.getAddress());
		loc.setLatitude(dto.getLatitude());
		loc.setLongitude(dto.getLongitude());
		//loc.setAddress(addressService.findOneNotDeleted(dto.getAddressId()));
		/*for(HallDTO hall_dto : dto.getHalls()) {
			Hall hall = hallConverter.HallDTO2Hall(hall_dto);
			hall.setLocation(loc);
			loc.getHalls().add(hall);
		}*/
		return loc;
	}
	
//	public Location LocationUpdateDTO2Location(LocationUpdateDTO dto ) throws ResourceNotFoundException {
//		Location loc = new Location();
//		loc.setName(dto.getName());
//		loc.setDescription(dto.getDescription());
//		//loc.setAddress(addressService.findOneNotDeleted(dto.getAddressId()));
//		return loc;
//	}
}
