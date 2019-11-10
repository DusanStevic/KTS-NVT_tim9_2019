package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dto.AddressDTO;
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

	public Address findOne(Long id) {
		return addressRepository.getOne(id);
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
	
	public ResponseEntity<String> delete(Long addressID) {
		Address a = findOne(addressID);
		if(!a.equals(null) && !a.isDeleted()) {
			a.setDeleted(true);
			save(a);
			return ResponseEntity.ok().body("Successfully deleted");
		}else {
			return ResponseEntity.badRequest().body("Could not find requested address");
		}
	}
	
	public ResponseEntity<Address> update(Long addressId, AddressDTO dto){
		Address a = findOne(addressId);
		if(!a.equals(null) && !a.isDeleted()) {
			a.setStreetName(dto.getStreetName());
			a.setStreetNumber(dto.getStreetNumber());
			a.setCity(dto.getCity());
			a.setCountry(dto.getCountry());
			a.setLatitude(dto.getLatitude());
			a.setLongitude(dto.getLongitude());
			
			return ResponseEntity.ok().body(save(a));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	public ResponseEntity<Address> getOneAddress(Long addressId){
		Address a = findOne(addressId);
		if(!a.equals(null) && !a.isDeleted()) {
			return ResponseEntity.ok().body(a);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
