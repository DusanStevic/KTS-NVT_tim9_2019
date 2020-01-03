package backend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.dto.AddressDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Address;
import backend.repository.AddressRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	public Address save(Address b) {
		return addressRepository.save(b);
	}

	/* DULE BUDZI */
	public Address findOne(Long id) throws ResourceNotFoundException {
		// forsiram da mi vrati null umesto entity not found exception ili da mi vrati
		// address not found exception kao kod usera sto sam napravio
		Address a = addressRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested address"));
		return a;
	}

	public Address findOneNotDeleted(Long id) throws ResourceNotFoundException {
		return addressRepository.findByIdAndDeleted(id, false)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested address"));
	}

	public List<Address> findAllNotDeleted(){
		return addressRepository.findAllByDeleted(false);
	}
	
	public Page<Address> findAllNotDeleted(Pageable page){
		return addressRepository.findAllByDeleted(false, page);
	}
	
	public List<Address> findAll() {
		return addressRepository.findAll();
	}
	
	public Page<Address> findAll(Pageable page) {
		return addressRepository.findAll(page);
	}

	/*@Transactional
	public void remove(Long id) {
		addressRepository.deleteById(id);
	}*/

	public void delete(Long addressID) throws ResourceNotFoundException {
		Address a = findOneNotDeleted(addressID);
		a.setDeleted(true);
		save(a);
	}

	public Address update(Long addressId, Address address) throws ResourceNotFoundException {
		Address a = findOneNotDeleted(addressId);
		a.setStreetName(address.getStreetName());
		a.setStreetNumber(address.getStreetNumber());
		a.setCity(address.getCity());
		a.setCountry(address.getCountry());
		a.setLatitude(address.getLatitude());
		a.setLongitude(address.getLongitude());

		return save(a);
	}

}
