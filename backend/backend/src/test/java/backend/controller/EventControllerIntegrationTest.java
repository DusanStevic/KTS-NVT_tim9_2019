package backend.controller;

import static backend.constants.EventConstants.DB_EVENT_DELETED;
import static backend.constants.EventConstants.DB_EVENT_ID;
import static backend.constants.EventConstants.DB_EVENT_NAME;
import static backend.constants.EventConstants.DB_EVENT_TO_BE_DELETED2;
import static backend.constants.EventConstants.EVENT_ID_NON_EXISTENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
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

import backend.exceptions.ResourceNotFoundException;
import backend.model.Event;
import backend.model.UserTokenState;
import backend.security.auth.JwtAuthenticationRequest;
import backend.service.EventService;
import backend.service.LocationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class EventControllerIntegrationTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	EventService eventService;
	
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
	/*
	@Test
	public void testGetAllEvents() {
		ResponseEntity<Event[]> responseEntity = restTemplate.getForEntity("/api/event", Event[].class);
		Event[] events = responseEntity.getBody();
		Event e0 = events[0];
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(events);
		assertNotEquals(0, events.length);
		assertEquals(7, events.length);
		assertEquals(DB_EVENT_ID, e0.getId());
		assertEquals(DB_EVENT_NAME, e0.getName());
	}
	*/
	/*
	@Test
	public void testGetEvent() {
		ResponseEntity<Event> responseEntity = restTemplate.getForEntity("/api/event/"+DB_EVENT_ID, Event.class);
		System.out.println("aaaaaaaaaaaaaaaaa: "+responseEntity.getBody());
		Event found = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(found);
		assertEquals(DB_EVENT_ID, found.getId());
		assertEquals(DB_EVENT_NAME, found.getName());
	}
	 */
	
	@Test
	public void testGetAddressNonExistent() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/event/"+EVENT_ID_NON_EXISTENT, String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Could not find requested event"));
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		int size = eventService.findAllNotDeleted().size();
		
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/event/" + DB_EVENT_TO_BE_DELETED2, 
	            HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);
		
		assertEquals(size-1, eventService.findAllNotDeleted().size()); 
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Successfully deleted event", responseEntity.getBody());
		
		Event deleted = eventService.findOne(DB_EVENT_TO_BE_DELETED2);
		assertEquals(DB_EVENT_TO_BE_DELETED2, deleted.getId());
		assertTrue(deleted.isDeleted());
	}
	
	@Test
	public void testDeleteException() {
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/event/"+DB_EVENT_DELETED,
				HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested event"));
	}
	/*
	@Test
	@Transactional
	public void testCreate() throws ResourceNotFoundException {
		int size = eventService.findAllNotDeleted().size(); //sve neobrisane dvorane
		ArrayList<SectorDTO> sectors = new ArrayList<>();
		SittingSectorDTO sit = new SittingSectorDTO("sit123", 9, 12);
		StandingSectorDTO stand = new StandingSectorDTO("stand123", 1000);
		sectors.add(sit);
		sectors.add(stand);
		NEW_HALL_DTO.setSectors(sectors);
		System.out.println(NEW_HALL_DTO.getSectors().size());
		HttpEntity<HallDTO> httpEntity = new HttpEntity<HallDTO>(NEW_HALL_DTO, headers);
		
		ResponseEntity<Hall> responseEntity = restTemplate.exchange("/api/hall/"+NEW_HALL_LOCATION_ID, HttpMethod.POST, httpEntity, Hall.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Hall created = responseEntity.getBody();
		assertNotNull(created);
		assertEquals(NEW_HALL_DTO.getName(), created.getName());
		
		assertFalse(created.isDeleted());
		assertFalse(created.getSectors().isEmpty());
		assertEquals(sectors.size(), created.getSectors().size());
		
		Hall found = hallService.findOne(created.getId());
		assertNotNull(found);
		assertEquals(NEW_HALL_DTO.getName(), found.getName());
		assertFalse(found.isDeleted());
		assertEquals(NEW_HALL_LOCATION_ID, found.getLocation().getId());
		assertFalse(found.getSectors().isEmpty());
		assertEquals(sectors.size(), found.getSectors().size());
		assertEquals(size+1, hallService.findAllNotDeleted().size());
		
		
	}
	
	@Test
	public void testCreate_LocationNotFound() {
		ArrayList<SectorDTO> sectors = new ArrayList<>();
		SittingSectorDTO sit = new SittingSectorDTO("sit123", 9, 12);
		StandingSectorDTO stand = new StandingSectorDTO("stand123", 1000);
		sectors.add(sit);
		sectors.add(sit);
		NEW_HALL_DTO.setSectors(sectors);
		HttpEntity<HallDTO> httpEntity = new HttpEntity<HallDTO>(NEW_HALL_DTO, headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/hall/"+LOCATION_ID_NON_EXISTENT, HttpMethod.POST, httpEntity, String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested location"));
	}*/

	/*
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		int size = eventService.findAll().size();
		HallUpdateDTO dto = new HallUpdateDTO("novo ime");
		HttpEntity<HallUpdateDTO> httpEntity = new HttpEntity<HallUpdateDTO>(dto, headers);
		
		ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event/"+DB_EVENT_TO_BE_UPDATED, HttpMethod.PUT, httpEntity, Event.class);
		Event updated = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(updated);
		assertEquals(dto.getName(), updated.getName());
		assertFalse(updated.isDeleted());
		
		Event found = eventService.findOne(DB_EVENT_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dto.getName(), found.getName());
		assertFalse(found.isDeleted());
		assertEquals(size, eventService.findAll().size()); //nije se dodavao novi vec je izmenjen postojeci
	}*/
	/*
	@Test 
	public void testUpdate_NotFoundException() {
		HallUpdateDTO dto = new HallUpdateDTO("novo ime");
		HttpEntity<HallUpdateDTO> httpEntity = new HttpEntity<HallUpdateDTO>(dto, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/event/"+EVENT_ID_NON_EXISTENT, HttpMethod.PUT, httpEntity, String.class);
		System.out.println("******upd not found");
		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested hall"));
	}*/
}
