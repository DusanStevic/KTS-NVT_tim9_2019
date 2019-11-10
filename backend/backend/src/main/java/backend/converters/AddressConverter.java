package backend.converters;

import org.springframework.stereotype.Component;

import backend.dto.AddressDTO;
import backend.model.Address;

@Component
public class AddressConverter {
	
	public Address AddressDTO2Address(AddressDTO dto) {
		Address a = new Address(dto);
		a.setDeleted(false);
		return a;
	}
	
	public AddressDTO Addres2AddressDTO(Address a) {
		AddressDTO dto = new AddressDTO();
		dto.setCity(a.getCity());
		dto.setCountry(a.getCountry());
		dto.setLatitude(a.getLatitude());
		dto.setLongitude(a.getLongitude());
		dto.setStreetName(a.getStreetName());
		dto.setStreetNumber(a.getStreetNumber());
		
		return dto;
	}
}
