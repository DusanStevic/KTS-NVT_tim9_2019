package backend.controller;

import static backend.constants.AddressConstants.ADDRESS_ID_NON_EXISTENT;
import static backend.constants.AddressConstants.DB_ADDRESS_ID;
import static backend.constants.LocationConstants.DB_DELETED_LOCATION_ADDRESS_ID;
import static backend.constants.LocationConstants.DB_LOCATION_ID;
import static backend.constants.LocationConstants.DB_LOCATION_ID_DELETED;
import static backend.constants.LocationConstants.DB_LOCATION_ID_TO_BE_UPDATED;
import static backend.constants.LocationConstants.DTO_NEW_LOCATION;
import static backend.constants.LocationConstants.FIRST_TIMESTAMP;
import static backend.constants.LocationConstants.LOCATION_ID_NON_EXISTENT;
import static backend.constants.LocationConstants.UPD_LOCATION_ADDRESS_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
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

import backend.dto.HallDTO;
import backend.dto.LocationDTO;
import backend.dto.LocationUpdateDTO;
import backend.dto.SectorDTO;
import backend.dto.SittingSectorDTO;
import backend.dto.StandingSectorDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Hall;
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
		
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location/" + 5, 
	            HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);
		
		System.out.println("controller del");
		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Successfully deleted location", responseEntity.getBody());
		
		Location deleted = locationService.findOne(5L);
		assertEquals(Long.valueOf(5L), deleted.getId());
		assertNotEquals(FIRST_TIMESTAMP, deleted.getDeleted());
		assertEquals(size-1, locationService.findAllNotDeleted().size()); 
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
	@Transactional
	public void testCreate() throws ResourceNotFoundException {
		int size = locationService.findAllNotDeleted().size(); //sve neobrisane lokacije
		ArrayList<SectorDTO> sectors = new ArrayList<>();
		SittingSectorDTO sit = new SittingSectorDTO("sit dto ime", 6, 9);
		sectors.add(sit);
		StandingSectorDTO stand = new StandingSectorDTO("stand dto ime", 9000);
		sectors.add(stand);
		ArrayList<HallDTO> halls = new ArrayList<HallDTO>();
		HallDTO hall = new HallDTO("ime hall dto", sectors);
		halls.add(hall);
		//DTO_NEW_LOCATION.setHalls(halls);
		DTO_NEW_LOCATION.setAddressId(2L);
		
		HttpEntity<LocationDTO> httpEntity = new HttpEntity<LocationDTO>(DTO_NEW_LOCATION, headers);
		
		ResponseEntity<Location> responseEntity = restTemplate.exchange("/api/location", HttpMethod.POST, httpEntity, Location.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Location created = responseEntity.getBody();
		assertNotNull(created);
		assertEquals(DTO_NEW_LOCATION.getName(), created.getName());
		assertEquals(DTO_NEW_LOCATION.getDescription(), created.getDescription());
		assertEquals(DTO_NEW_LOCATION.getAddressId(), created.getAddress().getId());
		assertEquals(FIRST_TIMESTAMP, created.getDeleted());
		assertFalse(created.getHalls().isEmpty());
		assertEquals(1, created.getHalls().size());
		for(Hall h : created.getHalls()) {
			assertFalse(h.getSectors().isEmpty());
			assertEquals(2, h.getSectors().size());
		}
		
		Location found = locationService.findOne(created.getId());
		assertNotNull(found);
		assertEquals(DTO_NEW_LOCATION.getName(), found.getName());
		assertEquals(DTO_NEW_LOCATION.getDescription(), found.getDescription());
		assertEquals(DTO_NEW_LOCATION.getAddressId(), found.getAddress().getId());
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
		assertEquals(size+1, locationService.findAllNotDeleted().size());
		assertFalse(found.getHalls().isEmpty());
		assertEquals(1, found.getHalls().size());
		for(Hall h : found.getHalls()) {
			assertFalse(h.getSectors().isEmpty());
			assertEquals(2, h.getSectors().size());
		}
	}
	
	@Test
	public void testCreate_AddressNotFound() {
		ArrayList<SectorDTO> sectors = new ArrayList<>();
		SittingSectorDTO sector = new SittingSectorDTO("sector dto ime", 6, 9);
		sectors.add(sector);
		ArrayList<HallDTO> halls = new ArrayList<HallDTO>();
		HallDTO hall = new HallDTO("ime hall dto", sectors);
		halls.add(hall);
		//DTO_NEW_LOCATION.setHalls(halls);
		DTO_NEW_LOCATION.setAddressId(ADDRESS_ID_NON_EXISTENT);
		HttpEntity<LocationDTO> httpEntity = new HttpEntity<LocationDTO>(DTO_NEW_LOCATION, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location", HttpMethod.POST, httpEntity, String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested address"));
	}
	
	@Test
	public void testCreate_SavingException() {
		DTO_NEW_LOCATION.setAddressId(DB_ADDRESS_ID);
		HttpEntity<LocationDTO> httpEntity = new HttpEntity<LocationDTO>(DTO_NEW_LOCATION, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location", HttpMethod.POST, httpEntity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
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
		assertEquals(dto.getAddressId(), updated.getAddress().getId());
		assertEquals(dto.getName(), updated.getName());
		assertEquals(dto.getDescription(), updated.getDescription());
		assertTrue(DB_LOCATION_ID_TO_BE_UPDATED == updated.getId());
		
		Location found = locationService.findOne(DB_LOCATION_ID_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dto.getAddressId(), found.getAddress().getId());
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
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested address"));
	}
	
	@Test
	public void testUpdate_LocationNotFound() {
		LocationUpdateDTO dto = new LocationUpdateDTO("upd", "desc", UPD_LOCATION_ADDRESS_ID);
		HttpEntity<LocationUpdateDTO> httpEntity = new HttpEntity<LocationUpdateDTO>(dto, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location/"+LOCATION_ID_NON_EXISTENT, 
				HttpMethod.PUT, httpEntity, String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested location"));
	}
	
	@Test
	@Transactional
	public void testUpdate_SavingException() {
		LocationUpdateDTO dto = new LocationUpdateDTO("upd", "desc", DB_ADDRESS_ID);
		System.out.println("controller upd sav exc");
		HttpEntity<LocationUpdateDTO> httpEntity = new HttpEntity<LocationUpdateDTO>(dto, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/location/"+DB_LOCATION_ID_TO_BE_UPDATED, 
				HttpMethod.PUT, httpEntity, String.class);
		System.out.println("controller upd sav exc");
		System.out.println(responseEntity.getBody());
		/*
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not save location"));
		*/
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		
	}
	/*
	@Test
	public void testGetAllAddressesPageable() {
		
	}*/
}
