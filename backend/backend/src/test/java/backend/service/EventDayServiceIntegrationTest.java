package backend.service;

import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.AddressConstants.pageRequest;
import static backend.constants.EventDayConstants.DB_EVENTDAY_COUNT;
import static backend.constants.EventDayConstants.DB_EVENTDAY_DELETED;
import static backend.constants.EventDayConstants.DB_EVENTDAY_DELETED_NAME;
import static backend.constants.EventDayConstants.DB_EVENTDAY_ID;
import static backend.constants.EventDayConstants.DB_EVENTDAY_NAME;
import static backend.constants.EventDayConstants.DB_EVENTDAY_TO_BE_DELETED;
import static backend.constants.EventDayConstants.DB_EVENTDAY_TO_BE_UPDATED;
import static backend.constants.EventDayConstants.EVENTDAY_ID_NON_EXISTENT;
import static backend.constants.EventDayConstants.NEW_EVENTDAY;
import static backend.constants.EventDayConstants.UPD_EVENTDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import backend.model.EventDay;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class EventDayServiceIntegrationTest {

	@Autowired
	EventDayService eventService;
	
	@Autowired
	LocationService locationService;
	
	@Test
	public void testFindAll() {
		List<EventDay> found = eventService.findAll();
		assertFalse(found.isEmpty());
		assertEquals(DB_EVENTDAY_COUNT, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Page<EventDay> found = eventService.findAll(pageRequest);
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindAllNotDeleted() {
		List<EventDay> found = eventService.findAllNotDeleted();
		assertFalse(found.isEmpty());
		for(EventDay e : found) {
			assertFalse(e.isDeleted());
		}
	}
	
	@Test
	public void testFindAllNotDeletedPageable() {
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<EventDay> found = eventService.findAllNotDeleted(pageRequest);
		for(EventDay e : found) {
			assertFalse(e.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindOne() throws ResourceNotFoundException {
		EventDay found = eventService.findOne(DB_EVENTDAY_ID);
		assertNotNull(found);
		assertTrue(DB_EVENTDAY_ID == found.getId());
		assertEquals(DB_EVENTDAY_NAME, found.getName());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		@SuppressWarnings("unused")
		EventDay found = eventService.findOne(EVENTDAY_ID_NON_EXISTENT);
		
	}
	
	@Test
	public void testFindOneDeleted_shouldFindDeletedHall() throws ResourceNotFoundException {
		EventDay found = eventService.findOne(DB_EVENTDAY_DELETED);
		assertNotNull(found);
		assertTrue(DB_EVENTDAY_DELETED == found.getId());
		assertEquals(DB_EVENTDAY_DELETED_NAME, found.getName());
		assertTrue(found.isDeleted());
	}
	
	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		EventDay found = eventService.findOneNotDeleted(DB_EVENTDAY_ID);
		assertNotNull(found);
		assertTrue(DB_EVENTDAY_ID == found.getId());
		assertEquals(DB_EVENTDAY_NAME, found.getName());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindDeletedHall() throws ResourceNotFoundException {
		eventService.findOneNotDeleted(DB_EVENTDAY_DELETED);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindNonExistingHall() throws ResourceNotFoundException {
		eventService.findOneNotDeleted(EVENTDAY_ID_NON_EXISTENT);
	}
	
	@Test
	@Transactional
	public void testSave() throws ResourceNotFoundException {
		
		int dbSizeBeforeAdd = eventService.findAll().size();
		EventDay found = eventService.save(NEW_EVENTDAY);
		
		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd+1, eventService.findAll().size());
		assertEquals(NEW_EVENTDAY.getName(), found.getName());
		assertFalse(found.isDeleted());
		assertTrue((long) (dbSizeBeforeAdd+1) == found.getId());
		
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		int db_size_before_delete = eventService.findAll().size();
		
		eventService.delete(DB_EVENTDAY_TO_BE_DELETED);
		
		int db_size_after_delete = eventService.findAll().size();
		
		EventDay found = eventService.findOne(DB_EVENTDAY_TO_BE_DELETED);
		assertEquals(db_size_before_delete, db_size_after_delete); //nije se promenio broj zbog logickog brisanja
		assertNotNull(found); //nije null zbog logickog brisanja
		assertTrue(found.isDeleted()); //samo se indikator deleted postavio na true
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		eventService.delete(DB_EVENTDAY_DELETED); //dvorana je vec obrisana i nece biti pronadjena
	}
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		int dbSizeBeforeUpd = eventService.findAll().size();
		EventDay updated = eventService.update(DB_EVENTDAY_TO_BE_UPDATED, UPD_EVENTDAY);
		assertNotNull(updated);
		assertEquals(UPD_EVENTDAY.getName(), updated.getName());
		
		EventDay found = eventService.findOneNotDeleted(DB_EVENTDAY_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dbSizeBeforeUpd, eventService.findAll().size());  //ne bi trebalo da se promeni broj dvorana u bazi
		assertEquals(UPD_EVENTDAY.getName(), found.getName());
		assertFalse(found.isDeleted());
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		/*
		 * nece pronaci dvoranu sa prosledjenim id-em pa nije potrebno slati stvarnu dvoranu
		 * dovoljno je samo null
		 */
		eventService.update(DB_EVENTDAY_DELETED, null); 
	} 
}
