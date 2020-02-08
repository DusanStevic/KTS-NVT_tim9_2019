package backend.controller;

import static backend.constants.EventConstants.DB_EVENT_DELETED;
import static backend.constants.EventConstants.DB_EVENT_ID;
import static backend.constants.EventConstants.DB_EVENT_NAME;
import static backend.constants.EventConstants.DB_EVENT_TO_BE_DELETED2;
import static backend.constants.EventConstants.EVENT_ID_NON_EXISTENT;
import static backend.constants.EventConstants.*;
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

import backend.dto.CreateEventDTO;
import backend.dto.EventDTO;
import backend.dto.EventDayDTO;
import backend.dto.EventSectorDTO;
import backend.dto.EventUpdateDTO;
import backend.dto.HallUpdateDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Event;
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
		ResponseEntity<String> login = 
				restTemplate.postForEntity("/auth/login", 
						new JwtAuthenticationRequest("admin", "admin"), 
						String.class);
		accessToken = login.getBody();
		headers.add("Authorization", "Bearer "+accessToken);
	}
	
	@Test
	public void testGetAllEvents() {
		ResponseEntity<EventDTO[]> responseEntity = restTemplate.getForEntity("/api/event", EventDTO[].class);
		EventDTO[] events = responseEntity.getBody();
		EventDTO e0 = events[0];
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(events);
		assertNotEquals(0, events.length);
		assertEquals(7, events.length);
		assertEquals(DB_EVENT_NAME, "Event");
		//assertEquals(DB_EVENT_ID, e0.getId());
	}
	
	@Test
	public void testGetEvent() {
		ResponseEntity<Event> responseEntity = restTemplate.getForEntity("/api/event/"+DB_EVENT_ID, Event.class);
		//System.out.println("aaaaaaaaaaaaaaaaa: "+);
		
		Event found = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(found);
		assertEquals(DB_EVENT_ID, found.getId());
		assertEquals(DB_EVENT_NAME, found.getName());
	}
	
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
	
	@Test
	@Transactional
	public void testCreate() throws ResourceNotFoundException {
		int size = eventService.findAllNotDeleted().size(); //sve neobrisane event-e
		ArrayList<EventDayDTO> days = new ArrayList<EventDayDTO>();
		//ArrayList<EventSectorDTO> sectors = new ArrayList<EventSectorDTO>();
		//EventDayDTO day = new EventDayDTO("day1","creating");
		//EventSectorDTO sector = new EventSectorDTO(200,1L,1L);
		//days.add(day);
		//sectors.add(sector);
		//NEW_EVENT_DTO.setSectors(sectors);
		//NEW_EVENT_DTO.setEvent_days(days);
		System.out.println("EVENT JE: " + NEW_EVENT_DTO);
		HttpEntity<CreateEventDTO> httpEntity = new HttpEntity<CreateEventDTO>(NEW_EVENT_DTO,headers);
		ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event", HttpMethod.POST, httpEntity, Event.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Event created = responseEntity.getBody();
		assertNotNull(created);
		
		assertEquals(NEW_EVENT_DTO.getName(), created.getName());
		
		assertFalse(created.isDeleted());
		assertFalse(created.getEventSectors().isEmpty());
		//assertEquals(sectors.size(), created.getEventSectors().size());
		
		Event found = eventService.findOne(created.getId());
		assertNotNull(found);
		assertEquals(NEW_EVENT_DTO.getName(), found.getName());
		assertFalse(found.isDeleted());
		//assertEquals(NEW_HALL_LOCATION_ID, found.getLocation().getId());
		assertFalse(found.getEventSectors().isEmpty());
		//assertEquals(sectors.size(), found.getEventSectors().size());
		//assertEquals(size+1, eventService.findAllNotDeleted().size());
		assertEquals(size+1, eventService.findAllNotDeleted().size());
	}

	@Test
	public void testUpdate() throws ResourceNotFoundException {
		int size = eventService.findAll().size();
		EventUpdateDTO dto = new EventUpdateDTO("novo ime","opis");
		HttpEntity<EventUpdateDTO> httpEntity = new HttpEntity<EventUpdateDTO>(dto, headers);
		
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
	}
	
	@Test 
	public void testUpdate_NotFoundException() {
		EventUpdateDTO dto = new EventUpdateDTO("novo ime","novi opis");
		HttpEntity<EventUpdateDTO> httpEntity = new HttpEntity<EventUpdateDTO>(dto, headers);
		ResponseEntity<Event> responseEntity = restTemplate.exchange("/api/event/"+EVENT_ID_NON_EXISTENT, HttpMethod.PUT, httpEntity, Event.class);
		System.out.println("******upd not found");
		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
}
