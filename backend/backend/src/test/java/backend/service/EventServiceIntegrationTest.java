package backend.service;

import static backend.constants.EventConstants.DB_EVENT_LOCATION_ID;
import static backend.constants.EventConstants.*;
import static backend.constants.AddressConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import backend.exceptions.ResourceNotFoundException;
import backend.model.Event;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class EventServiceIntegrationTest {
	
	@Autowired
	EventService eventService;
	
	@Autowired
	LocationService locationService;
	
	@Test
	public void testFindAll() {
		List<Event> found = eventService.findAll();
		assertFalse(found.isEmpty());
		assertEquals(DB_EVENT_COUNT, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Page<Event> found = eventService.findAll(pageRequest);
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindAllNotDeleted() {
		List<Event> found = eventService.findAllNotDeleted();
		assertFalse(found.isEmpty());
		for(Event e : found) {
			assertFalse(e.isDeleted());
		}
	}
	
	@Test
	public void testFindAllNotDeletedPageable() {
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<Event> found = eventService.findAllNotDeleted(pageRequest);
		for(Event e : found) {
			assertFalse(e.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindOne() throws ResourceNotFoundException {
		Event found = eventService.findOne(DB_EVENT_ID);
		assertNotNull(found);
		assertTrue(DB_EVENT_ID == found.getId());
		assertEquals(DB_EVENT_NAME, found.getName());
		assertEquals(DB_EVENT_LOCATION_ID, found.getLocation().getId());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		@SuppressWarnings("unused")
		Event found = eventService.findOne(EVENT_ID_NON_EXISTENT);
		
	}
	
	@Test
	public void testFindOneDeleted_shouldFindDeletedHall() throws ResourceNotFoundException {
		Event found = eventService.findOne(DB_EVENT_DELETED);
		assertNotNull(found);
		assertTrue(DB_EVENT_DELETED == found.getId());
		assertEquals(DB_EVENT_DELETED_NAME, found.getName());
		assertTrue(found.isDeleted());
	}
	
	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		Event found = eventService.findOneNotDeleted(DB_EVENT_ID);
		assertNotNull(found);
		assertTrue(DB_EVENT_ID == found.getId());
		assertEquals(DB_EVENT_NAME, found.getName());
		assertEquals(DB_EVENT_LOCATION_ID, found.getLocation().getId());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindDeletedHall() throws ResourceNotFoundException {
		eventService.findOneNotDeleted(DB_EVENT_DELETED);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindNonExistingHall() throws ResourceNotFoundException {
		eventService.findOneNotDeleted(EVENT_ID_NON_EXISTENT);
	}
	
	@Test
	@Transactional
	public void testSave() throws ResourceNotFoundException {
		
		int dbSizeBeforeAdd = eventService.findAll().size();
		NEW_EVENT.setStartDate(new Date());
		NEW_EVENT.setEndDate(new Date());
		NEW_EVENT.setLocation(locationService.findOneNotDeleted(DB_EVENT_LOCATION_ID));
		System.out.println("AAAAAAAAAAAAAAAAA" + NEW_EVENT.toString());
		Event found = eventService.save(NEW_EVENT);
		
		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd+1, eventService.findAll().size());
		assertEquals(NEW_EVENT.getName(), found.getName());
		assertEquals(NEW_EVENT.getLocation().getId(), found.getLocation().getId());
		assertFalse(found.isDeleted());
		assertTrue((long) (dbSizeBeforeAdd+1) == found.getId());
		
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		int db_size_before_delete = eventService.findAll().size();
		
		eventService.delete(DB_EVENT_TO_BE_DELETED);
		
		int db_size_after_delete = eventService.findAll().size();
		
		Event found = eventService.findOne(DB_EVENT_TO_BE_DELETED);
		assertEquals(db_size_before_delete, db_size_after_delete); //nije se promenio broj zbog logickog brisanja
		assertNotNull(found); //nije null zbog logickog brisanja
		assertTrue(found.isDeleted()); //samo se indikator deleted postavio na true
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		eventService.delete(DB_EVENT_DELETED); //dvorana je vec obrisana i nece biti pronadjena
	}
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		int dbSizeBeforeUpd = eventService.findAll().size();
		Event updated = eventService.update(DB_EVENT_TO_BE_UPDATED, UPD_EVENT);
		assertNotNull(updated);
		assertEquals(UPD_EVENT.getName(), updated.getName());
		
		Event found = eventService.findOneNotDeleted(DB_EVENT_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dbSizeBeforeUpd, eventService.findAll().size());  //ne bi trebalo da se promeni broj dvorana u bazi
		assertEquals(UPD_EVENT.getName(), found.getName());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		/*
		 * nece pronaci dvoranu sa prosledjenim id-em pa nije potrebno slati stvarnu dvoranu
		 * dovoljno je samo null
		 */
		eventService.update(DB_EVENT_DELETED, null); 
	} 
}
