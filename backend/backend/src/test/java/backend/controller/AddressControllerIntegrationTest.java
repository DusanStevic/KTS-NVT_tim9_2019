package backend.controller;

import static backend.constants.AddressConstants.ADDRESS_ID_NON_EXISTENT;
import static backend.constants.AddressConstants.DB_ADDRESS_ID;
import static backend.constants.AddressConstants.DB_ADDRESS_ID_DELETED;
import static backend.constants.AddressConstants.DB_ADDRESS_ID_TO_BE_DELETED;
import static backend.constants.AddressConstants.DB_ADDRESS_ID_TO_BE_UPDATED;
import static backend.constants.AddressConstants.DB_ADDRESS_STREET;
import static backend.constants.AddressConstants.NEW_ADDRESS_DTO;
import static backend.constants.AddressConstants.UPD_ADDRESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import backend.converters.AddressConverter;
import backend.dto.AddressDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Address;
import backend.model.UserTokenState;
import backend.security.auth.JwtAuthenticationRequest;
import backend.service.AddressService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AddressControllerIntegrationTest {
	
	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	AddressService addressService;
	
	@Autowired
	AddressConverter addressConverter;
	
	private String accessToken;
	private HttpHeaders headers = new HttpHeaders();
	
	@Before
	public void login() {
		ResponseEntity<UserTokenState> login = 
				restTemplate.postForEntity("/auth/login", 
						new JwtAuthenticationRequest("admin", "admin"), 
						UserTokenState.class);
		accessToken = login.getBody().getAccessToken();
		headers.add("Authorization", "Bearer "+accessToken);
	}
	@Test
	public void testGetAllAddresses() {
		ResponseEntity<Address[]> responseEntity = restTemplate.getForEntity("/api/address", Address[].class);
		Address[] addresses = responseEntity.getBody();
		Address a0 = addresses[0];
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(addresses);
		assertNotEquals(0, addresses.length);
		assertEquals(5, addresses.length);
		assertEquals(DB_ADDRESS_ID, a0.getId());
		assertEquals(DB_ADDRESS_STREET, a0.getStreetName());
	}
	
	
	@Test
	public void testGetAddress() {
		ResponseEntity<Address> responseEntity = restTemplate.getForEntity("/api/address/"+DB_ADDRESS_ID, Address.class);
		Address found = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(found);
		assertEquals(DB_ADDRESS_ID, found.getId());
		assertEquals(DB_ADDRESS_STREET, found.getStreetName());
	}
	
	@Test
	public void testGetAddressNonExistent() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/address/"+ADDRESS_ID_NON_EXISTENT, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Could not find requested address"));
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		int size = addressService.findAllNotDeleted().size();
		
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/address/" + DB_ADDRESS_ID_TO_BE_DELETED, 
	            HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);
		
		
		assertEquals(size-1, addressService.findAllNotDeleted().size()); 
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Successfully deleted address", responseEntity.getBody());
		
		Address deleted = addressService.findOne(DB_ADDRESS_ID_TO_BE_DELETED);
		assertEquals(DB_ADDRESS_ID_TO_BE_DELETED, deleted.getId());
		assertTrue(deleted.isDeleted());
	}
	
	@Test
	public void testDeleteException() {
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/address/"+DB_ADDRESS_ID_DELETED,
				HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested address"));
	}
	
	@Test
	public void testCreate() throws ResourceNotFoundException {
		int size = addressService.findAllNotDeleted().size(); //sve neobrisane adrese
		HttpEntity<AddressDTO> httpEntity = new HttpEntity<AddressDTO>(NEW_ADDRESS_DTO, headers);
		
		ResponseEntity<Address> responseEntity = restTemplate.exchange("/api/address", HttpMethod.POST, httpEntity, Address.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Address created = responseEntity.getBody();
		assertNotNull(created);
		assertEquals(NEW_ADDRESS_DTO.getStreetName(), created.getStreetName());
		assertEquals(NEW_ADDRESS_DTO.getCity(), created.getCity());
		assertEquals(NEW_ADDRESS_DTO.getCountry(), created.getCountry());
		assertTrue(NEW_ADDRESS_DTO.getStreetNumber() == created.getStreetNumber());
		assertTrue(NEW_ADDRESS_DTO.getLatitude() == created.getLatitude());
		assertTrue(NEW_ADDRESS_DTO.getLongitude() == created.getLongitude());
		Address found = addressService.findOne(created.getId());
		assertNotNull(found);
		assertEquals(NEW_ADDRESS_DTO.getStreetName(), found.getStreetName());
		assertTrue(NEW_ADDRESS_DTO.getStreetNumber() == found.getStreetNumber());
		assertEquals(NEW_ADDRESS_DTO.getCountry(), found.getCountry());
		assertEquals(NEW_ADDRESS_DTO.getCity(), found.getCity());
		assertTrue(NEW_ADDRESS_DTO.getLatitude() == found.getLatitude());
		assertTrue(NEW_ADDRESS_DTO.getLongitude() == found.getLongitude());
		assertEquals(size+1, addressService.findAllNotDeleted().size());
	}
	
	@Test
	public void testCreateInvalidParameters() {
		AddressDTO dto = new AddressDTO(null, null, null, null, null, null);
		int size = addressService.findAll().size();
		HttpEntity<AddressDTO> httpEntity = new HttpEntity<AddressDTO>(dto, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/address", HttpMethod.POST, httpEntity, String.class);
		String message = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(message);
		System.out.println(message);
		assertTrue(message.contains("Street is mandatory"));
		assertTrue(message.contains("City is mandatory"));
		assertTrue(message.contains("Country is mandatory"));
		assertTrue(message.contains("Street number is mandatory"));
		assertTrue(message.contains("Latitude is mandatory"));
		assertTrue(message.contains("Longitude is mandatory"));
		assertEquals(size, addressService.findAll().size());
		
		dto = new AddressDTO("", 0, "", "", -91., -181.);
		httpEntity = new HttpEntity<AddressDTO>(dto, headers);
		responseEntity = restTemplate.exchange("/api/address", HttpMethod.POST, httpEntity, String.class);
		message = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(message);
		System.out.println(message);
		assertTrue(message.contains("Street is mandatory"));
		assertTrue(message.contains("City is mandatory"));
		assertTrue(message.contains("Country is mandatory"));
		assertTrue(message.contains("Street number must be greater than or equal to 1"));
		assertTrue(message.contains("Latitude must be greater than or equal to -90"));
		assertTrue(message.contains("Longitude must be greater than or equal to -180"));
		assertEquals(size, addressService.findAll().size());
		
		dto.setStreetNumber(-1);
		dto.setLatitude(91.);
		dto.setLongitude(181.);
		httpEntity = new HttpEntity<AddressDTO>(dto, headers);
		responseEntity = restTemplate.exchange("/api/address", HttpMethod.POST, httpEntity, String.class);
		message = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertTrue(message.contains("Street number must be greater than or equal to 1"));
		assertTrue(message.contains("Latitude must be less than or equal to 90"));
		assertTrue(message.contains("Longitude must be less than or equal to 180"));
		assertEquals(size, addressService.findAll().size());
		
	}
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		int size = addressService.findAll().size();
		AddressDTO dto = addressConverter.Addres2AddressDTO(UPD_ADDRESS);
		HttpEntity<AddressDTO> httpEntity = new HttpEntity<AddressDTO>(dto, headers);
		
		ResponseEntity<Address> responseEntity = restTemplate.exchange("/api/address/"+DB_ADDRESS_ID_TO_BE_UPDATED, HttpMethod.PUT, httpEntity, Address.class);
		Address updated = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(updated);
		assertEquals(dto.getStreetName(), updated.getStreetName());
		assertEquals(dto.getCity(), updated.getCity());
		assertEquals(dto.getCountry(), updated.getCountry());
		assertTrue(dto.getStreetNumber() == updated.getStreetNumber());
		assertTrue(dto.getLatitude() == updated.getLatitude());
		assertTrue(dto.getLongitude() == updated.getLongitude());
		
		Address found = addressService.findOne(DB_ADDRESS_ID_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dto.getStreetName(), found.getStreetName());
		assertEquals(dto.getCity(), found.getCity());
		assertEquals(dto.getCountry(), found.getCountry());
		assertTrue(dto.getStreetNumber() == found.getStreetNumber());
		assertTrue(dto.getLatitude() == found.getLatitude());
		assertTrue(dto.getLongitude() == found.getLongitude());
		assertEquals(size, addressService.findAll().size()); //nije se dodavao novi vec je izmenjen postojeci
	}
	
	@Test 
	public void testUpdate_NotFoundException() {
		AddressDTO dto = addressConverter.Addres2AddressDTO(UPD_ADDRESS);
		HttpEntity<AddressDTO> httpEntity = new HttpEntity<AddressDTO>(dto, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/address/"+ADDRESS_ID_NON_EXISTENT, HttpMethod.PUT, httpEntity, String.class);
		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested address"));
	}
	@Test
	public void testUpdateInvalidParameters() {
		
	}
	
	@Test
	@Ignore
	public void testGetAllAddressesPageable() {
		
	}
	
	
}
