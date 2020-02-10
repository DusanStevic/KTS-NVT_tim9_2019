package backend.service;

import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.AddressConstants.pageRequest;
import static backend.constants.EventSectorConstants.DB_EVENTSECTOR_COUNT;
import static backend.constants.EventSectorConstants.DB_EVENTSECTOR_DELETED;
import static backend.constants.EventSectorConstants.DB_EVENTSECTOR_ID;
import static backend.constants.EventSectorConstants.DB_EVENT_DELETED_NAME_FROM_SECTOR;
import static backend.constants.EventSectorConstants.DB_EVENT_NAME_FROM_SECTOR;
import static backend.constants.EventSectorConstants.EVENTSECTOR_ID_NON_EXISTENT;
import static backend.constants.EventSectorConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
import backend.model.EventSector;
import backend.model.EventType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class EventSectorServiceIntegrationTest {

	

	@Autowired
	EventSectorService eventSectorService;
	
	@Test
	public void testFindAll() {
		List<EventSector> found = eventSectorService.findAll();
		assertFalse(found.isEmpty());
		assertEquals(DB_EVENTSECTOR_COUNT, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Page<EventSector> found = eventSectorService.findAll(pageRequest);
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindAllNotDeleted() {
		List<EventSector> found = eventSectorService.findAllNotDeleted();
		assertFalse(found.isEmpty());
		for(EventSector e : found) {
			assertFalse(e.isDeleted());
		}
	}
	
	///////
	
	@Test
	public void testFindAllNotDeletedPageable() {
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<EventSector> found = eventSectorService.findAllNotDeleted(pageRequest);
		for(EventSector e : found) {
			assertFalse(e.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindOne() throws ResourceNotFoundException {
		EventSector found = eventSectorService.findOne(DB_EVENTSECTOR_ID);
		assertNotNull(found);
		assertTrue(DB_EVENTSECTOR_ID == found.getId());
		assertEquals(DB_EVENT_NAME_FROM_SECTOR, found.getEvent().getName());
		//assertEquals(DB_EVENTSECTOR_LOCATION_ID, found.getEvent().getLocation().getId());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		@SuppressWarnings("unused")
		EventSector found = eventSectorService.findOne(EVENTSECTOR_ID_NON_EXISTENT);
	}
	
	@Test
	public void testFindOneDeleted_shouldFindDeletedHall() throws ResourceNotFoundException {
		EventSector found = eventSectorService.findOne(DB_EVENTSECTOR_DELETED);
		assertNotNull(found);
		assertTrue(DB_EVENTSECTOR_DELETED == found.getId());
		assertEquals(DB_EVENT_DELETED_NAME_FROM_SECTOR, found.getEvent().getName());
		assertTrue(found.isDeleted());
	}
	
	//////// *** 
	
	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		EventSector found = eventSectorService.findOneNotDeleted(DB_EVENTSECTOR_ID);
		assertNotNull(found);
		assertTrue(DB_EVENTSECTOR_ID == found.getId());
		//assertEquals(DB_EVENT_NAME, found.getName());
		//assertEquals(DB_EVENT_LOCATION_ID, found.getLocation().getId());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindDeletedHall() throws ResourceNotFoundException {
		eventSectorService.findOneNotDeleted(DB_EVENTSECTOR_DELETED);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindNonExistingHall() throws ResourceNotFoundException {
		eventSectorService.findOneNotDeleted(EVENTSECTOR_ID_NON_EXISTENT);
	}
	
	@Test
	@Transactional
	public void testSave() throws ResourceNotFoundException {
		
		int dbSizeBeforeAdd = eventSectorService.findAll().size();
		System.out.println("AAAAAAAAAAAAAAAAA" + NEW_EVENTSECTOR.toString());
		EventSector found = eventSectorService.save(NEW_EVENTSECTOR);
		//found.getEvent().setId(22L);
		found.getEvent().setName("Novi Event");
		found.getEvent().setDescription("dsasddas");
		found.getEvent().setMaxTickets(25);
		found.getEvent().setEventType(EventType.CONCERT);
		found.getEvent().setStartDate(new Date());
		found.getEvent().setEndDate(new Date());
		//found.getSector().setId(22L);
		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd+1, eventSectorService.findAll().size());
		assertEquals(NEW_EVENTSECTOR.getEvent().getName(), found.getEvent().getName());
		assertFalse(found.isDeleted());
		assertTrue((long) (dbSizeBeforeAdd+1) == found.getId());
		
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		int db_size_before_delete = eventSectorService.findAll().size();
		
		eventSectorService.delete(DB_EVENTSECTOR_TO_BE_DELETED);
		
		int db_size_after_delete = eventSectorService.findAll().size();
		
		EventSector found = eventSectorService.findOne(DB_EVENTSECTOR_TO_BE_DELETED);
		assertEquals(db_size_before_delete, db_size_after_delete); //nije se promenio broj zbog logickog brisanja
		assertNotNull(found); //nije null zbog logickog brisanja
		assertTrue(found.isDeleted()); //samo se indikator deleted postavio na true
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		eventSectorService.delete(DB_EVENTSECTOR_DELETED); //dvorana je vec obrisana i nece biti pronadjena
	}
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		int dbSizeBeforeUpd = eventSectorService.findAll().size();
		EventSector updated = eventSectorService.update1(DB_EVENTSECTOR_TO_BE_UPDATED, 500);
		assertNotNull(updated);
		//Novi Event vs FolkFest
		assertNotEquals(UPD_EVENTSECTOR.getEvent().getName(), updated.getEvent().getName());
		
		EventSector found = eventSectorService.findOneNotDeleted(DB_EVENTSECTOR_TO_BE_UPDATED);
		found.getEvent().setName("Updated Event");
		found.getEvent().setDescription("dsasddas");
		found.getEvent().setMaxTickets(25);
		found.getEvent().setEventType(EventType.CONCERT);
		found.getEvent().setStartDate(new Date());
		found.getEvent().setEndDate(new Date());
		assertNotNull(found);
		assertEquals(dbSizeBeforeUpd, eventSectorService.findAll().size());  //ne bi trebalo da se promeni broj dvorana u bazi
		assertNotEquals(UPD_EVENTSECTOR.getEvent().getName(), found.getEvent().getName());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		/*
		 * nece pronaci eventSector sa prosledjenim id-em pa nije potrebno slati stvarnu cenu 
		 * dovoljno je samo 0
		 */
		eventSectorService.update(DB_EVENTSECTOR_DELETED, 0); 
	} 
	
}
