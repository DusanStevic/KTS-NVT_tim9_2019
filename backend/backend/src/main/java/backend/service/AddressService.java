package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
