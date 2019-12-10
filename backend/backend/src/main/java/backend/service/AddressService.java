package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dto.AddressDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Address;
import backend.repository.AddressRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	public Address save(Address b) {
		return addressRepository.save(b);
	}

	
	/*DULE BUDZI*/
	public Address findOne(Long id) {
		//forsiram da mi vrati null umesto entity not found exception ili da mi vrati 
		// address not found exception kao kod usera sto sam napravio
		Address a = addressRepository.findById(id).orElse(null);
		return a;
	}
	

	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	public Page<Address> findAll(Pageable page) {
		return addressRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		addressRepository.deleteById(id);
	}
	
	public void delete(Long addressID) throws ResourceNotFoundException {
		Address a = findOne(addressID);
		if(a != null && !a.isDeleted()) {
			a.setDeleted(true);
			save(a);
		}else {
			throw new ResourceNotFoundException("Could not find requested address");
		}
	}
	
	public Address update(Long addressId, AddressDTO dto) throws ResourceNotFoundException{
		Address a = findOne(addressId);
		if(a != null && !a.isDeleted()) {
			a.setStreetName(dto.getStreetName());
			a.setStreetNumber(dto.getStreetNumber());
			a.setCity(dto.getCity());
			a.setCountry(dto.getCountry());
			a.setLatitude(dto.getLatitude());
			a.setLongitude(dto.getLongitude());
			
			return save(a);
		}else {
			throw new ResourceNotFoundException("Could not find requested address");
		}
	}
	
	
	//dule budzi
	public Address getOneAddress(Long addressId) throws ResourceNotFoundException{
		Address a = findOne(addressId);
		if(a == null){
			throw new ResourceNotFoundException("Could not find requested address");
		}
		
		return a;
	}
}
