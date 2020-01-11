package backend.controller;

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
import org.springframework.transaction.annotation.Transactional;

import static backend.constants.AddressConstants.ADDRESS_ID_NON_EXISTENT;
import static backend.constants.AddressConstants.DB_ADDRESS_ID;
import static backend.constants.AddressConstants.DB_ADDRESS_ID_DELETED;
import static backend.constants.AddressConstants.DB_ADDRESS_ID_TO_BE_DELETED;
import static backend.constants.AddressConstants.DB_ADDRESS_ID_TO_BE_UPDATED;
import static backend.constants.AddressConstants.NEW_ADDRESS_DTO;
import static backend.constants.AddressConstants.UPD_ADDRESS;
import static backend.constants.LocationConstants.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;

import backend.dto.AddressDTO;
import backend.dto.HallDTO;
import backend.dto.LocationDTO;
import backend.dto.LocationUpdateDTO;
import backend.dto.SectorDTO;
import backend.dto.SittingSectorDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Address;
import backend.model.Location;
import backend.model.UserTokenState;
import backend.security.auth.JwtAuthenticationRequest;
import backend.service.LocationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class LocationControllerIntegrationTest {

	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	LocationService locationService;
	
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
	public void testGetLocation() {
		ResponseEntity<Location> responseEntity = restTemplate.getForEntity("/api/location/"+DB_LOCATION_ID, Location.class);
		Location loc = responseEntity.getBody();
		
		assertNotNull(loc);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void testGetAllLocations() {
		ResponseEntity<Location[]> responseEntity = restTemplate.getForEntity("/api/location", Location[].class);
		Location[] locations = responseEntity.getBody();
		assertNotNull(locations);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void testGetLocationNonExistent() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/location/"+LOCATION_ID_NON_EXISTENT, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Could not find requested location"));
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		int size = locationService.findAllNotDeleted().size();
		
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location/" + DB_LOCATION_ID_TO_BE_DELETED, 
	            HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);
		
		
		assertEquals(size-1, locationService.findAllNotDeleted().size()); 
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Successfully deleted location", responseEntity.getBody());
		
		Location deleted = locationService.findOne(DB_LOCATION_ID_TO_BE_DELETED);
		assertEquals(DB_LOCATION_ID_TO_BE_DELETED, deleted.getId());
		assertNotEquals(FIRST_TIMESTAMP, deleted.getDeleted());
	}
	
	@Test
	public void testDelete_NotFoundException() {
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location/"+DB_LOCATION_ID_DELETED,
				HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested location"));
	}
	
	@Test
	public void testDelete_BadRequestException() {
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location/"+DB_LOCATION_ID, 
				HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not delete location"));
	}
	
	@Test
	public void testCreate() throws ResourceNotFoundException {
		int size = locationService.findAllNotDeleted().size(); //sve neobrisane lokacije
		ArrayList<SectorDTO> sectors = new ArrayList<>();
		SittingSectorDTO sector = new SittingSectorDTO("sector dto ime", 6, 9);
		sectors.add(sector);
		ArrayList<HallDTO> halls = new ArrayList<HallDTO>();
		HallDTO hall = new HallDTO("ime hall dto", sectors);
		halls.add(hall);
		DTO_NEW_LOCATION.setHalls(halls);
		DTO_NEW_LOCATION.setAddress_id(NEW_LOCATION_ADDRESS_ID);
		
		HttpEntity<LocationDTO> httpEntity = new HttpEntity<LocationDTO>(DTO_NEW_LOCATION, headers);
		
		ResponseEntity<Location> responseEntity = restTemplate.exchange("/api/location", HttpMethod.POST, httpEntity, Location.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Location created = responseEntity.getBody();
		assertNotNull(created);
		assertEquals(DTO_NEW_LOCATION.getName(), created.getName());
		assertEquals(DTO_NEW_LOCATION.getDescription(), created.getDescription());
		assertEquals(DTO_NEW_LOCATION.getAddress_id(), created.getAddress().getId());
		assertEquals(FIRST_TIMESTAMP, created.getDeleted());
		
		Location found = locationService.findOne(created.getId());
		assertNotNull(found);
		assertEquals(DTO_NEW_LOCATION.getName(), found.getName());
		assertEquals(DTO_NEW_LOCATION.getDescription(), found.getDescription());
		assertEquals(DTO_NEW_LOCATION.getAddress_id(), found.getAddress().getId());
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
		assertEquals(size+1, locationService.findAllNotDeleted().size());
	}
	
	@Test
	public void testCreate_AddressNotFound() {
		ArrayList<SectorDTO> sectors = new ArrayList<>();
		SittingSectorDTO sector = new SittingSectorDTO("sector dto ime", 6, 9);
		sectors.add(sector);
		ArrayList<HallDTO> halls = new ArrayList<HallDTO>();
		HallDTO hall = new HallDTO("ime hall dto", sectors);
		halls.add(hall);
		DTO_NEW_LOCATION.setHalls(halls);
		DTO_NEW_LOCATION.setAddress_id(ADDRESS_ID_NON_EXISTENT);
		HttpEntity<LocationDTO> httpEntity = new HttpEntity<LocationDTO>(DTO_NEW_LOCATION, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location", HttpMethod.POST, httpEntity, String.class);
		System.out.println("addr not found");
		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		System.out.println(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Could not find requested address"));
	}
	
	@Test
	public void testCreate_SavingException() {
		DTO_NEW_LOCATION.setAddress_id(DB_ADDRESS_ID);
		HttpEntity<LocationDTO> httpEntity = new HttpEntity<LocationDTO>(DTO_NEW_LOCATION, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location", HttpMethod.POST, httpEntity, String.class);
		System.out.println("sav exc");
		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		System.out.println(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Could not save location"));
	}
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		int size = locationService.findAll().size();
		LocationUpdateDTO dto = new LocationUpdateDTO("upd", "desc", DB_DELETED_LOCATION_ADDRESS_ID);
		HttpEntity<LocationUpdateDTO> httpEntity = new HttpEntity<LocationUpdateDTO>(dto, headers);
		
		ResponseEntity<Location> responseEntity = restTemplate.exchange("/api/location/"+DB_LOCATION_ID_TO_BE_UPDATED, 
				HttpMethod.PUT, httpEntity, Location.class);
		Location updated = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(updated);
		assertEquals(dto.getAddress_id(), updated.getAddress().getId());
		assertEquals(dto.getName(), updated.getName());
		assertEquals(dto.getDescription(), updated.getDescription());
		assertTrue(DB_LOCATION_ID_TO_BE_UPDATED == updated.getId());
		
		Location found = locationService.findOne(DB_LOCATION_ID_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dto.getAddress_id(), found.getAddress().getId());
		assertEquals(dto.getName(), found.getName());
		assertEquals(dto.getDescription(), found.getDescription());
		assertEquals(size, locationService.findAll().size()); //nije se dodavao novi vec je izmenjen postojeci
	}
	
	@Test
	public void testUpdate_AddressNotFound() {
		LocationUpdateDTO dto = new LocationUpdateDTO("upd", "desc", ADDRESS_ID_NON_EXISTENT);
		HttpEntity<LocationUpdateDTO> httpEntity = new HttpEntity<LocationUpdateDTO>(dto, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location/"+DB_LOCATION_ID_TO_BE_UPDATED, 
				HttpMethod.PUT, httpEntity, String.class);
		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested address"));
	}
	
	@Test
	public void testUpdate_LocationNotFound() {
		LocationUpdateDTO dto = new LocationUpdateDTO("upd", "desc", UPD_LOCATION_ADDRESS_ID);
		HttpEntity<LocationUpdateDTO> httpEntity = new HttpEntity<LocationUpdateDTO>(dto, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location/"+LOCATION_ID_NON_EXISTENT, 
				HttpMethod.PUT, httpEntity, String.class);
		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested location"));
	}
	
	@Test
	public void testUpdate_SavingException() {
		LocationUpdateDTO dto = new LocationUpdateDTO("upd", "desc", DB_LOCATION_ADDRESS_ID);
		System.out.println("sav exception update");
		HttpEntity<LocationUpdateDTO> httpEntity = new HttpEntity<LocationUpdateDTO>(dto, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location/"+DB_LOCATION_ID_TO_BE_UPDATED, 
				HttpMethod.PUT, httpEntity, String.class);
		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not save location"));
	}
	
	@Test
	@Ignore
	public void testGetAllAddressesPageable() {
		
	}
}
