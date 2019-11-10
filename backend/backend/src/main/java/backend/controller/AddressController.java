package backend.controller;

//can copypaste everywhere
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import backend.model.*;
import backend.service.*;
import backend.converters.AddressConverter;
import backend.dto.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AddressService addressService;

	@Autowired
	AddressConverter addressConverter;
	
	/* saving address */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Address createAddress(@Valid @RequestBody AddressDTO addressDTO) {
		Address address = addressConverter.AddressDTO2Address(addressDTO);
		return addressService.save(address);
	}

	/* get all addresses, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Address> getAllAddresses() {
		return addressService.findAll();
	}

	/* get an address by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> getAddress(
			@PathVariable(value = "id") Long addressId) {
		return addressService.getOneAddress(addressId);
	}

	/* update address by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> updateAddress(
			@PathVariable(value = "id") Long addressId,
			@Valid @RequestBody AddressDTO dto) {

		return addressService.update(addressId, dto);
	}

	/* delete Address */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteAddress(
			@PathVariable(value = "id") Long addressId) {
		return addressService.delete(addressId);
	}
}
