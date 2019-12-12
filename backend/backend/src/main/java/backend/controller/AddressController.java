package backend.controller;

//can copypaste everywhere
import java.util.List;

import javax.validation.Valid;

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

import backend.converters.AddressConverter;
import backend.dto.AddressDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Address;
import backend.service.AddressService;

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
	public ResponseEntity<Address> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
		Address address = addressConverter.AddressDTO2Address(addressDTO);
		return new ResponseEntity<>(addressService.save(address), HttpStatus.OK);
	}

	/* get all addresses, permitted for all */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Address>> getAllAddresses() {
		return new ResponseEntity<>(addressService.findAllNotDeleted(), HttpStatus.OK);
	}

	/* get an address by id, permitted for all */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> getAddress(
			@PathVariable(value = "id") Long addressId) throws ResourceNotFoundException {
		return new ResponseEntity<>(addressService.findOneNotDeleted(addressId), HttpStatus.OK);
	}

	/* update address by id */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> updateAddress(
			@PathVariable(value = "id") Long addressId,
			@Valid @RequestBody AddressDTO dto) throws ResourceNotFoundException {

		return new ResponseEntity<>(addressService.update(addressId, dto), HttpStatus.OK);
	}

	/* delete Address */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteAddress(
			@PathVariable(value = "id") Long addressId) throws ResourceNotFoundException {
		//sta kada id nije prosledjen ??
		logger.info("Deleting address " + addressId);
		addressService.delete(addressId);
		return new ResponseEntity<>("Successfully deleted address", HttpStatus.OK);
	}
}
