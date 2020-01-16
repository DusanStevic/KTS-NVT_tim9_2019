package backend.service;

import static backend.constants.HallConstants.*;
import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.AddressConstants.pageRequest;
import static org.junit.Assert.*;

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
import backend.model.Hall;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class HallServiceIntegrationTest {

	@Autowired
	HallService hallService;
	
	@Autowired
	LocationService locationService;
	
	@Test
	public void testFindAll() {
		List<Hall> found = hallService.findAll();
		assertFalse(found.isEmpty());
		assertEquals(DB_HALL_COUNT, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Page<Hall> found = hallService.findAll(pageRequest);
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindAllNotDeleted() {
		List<Hall> found = hallService.findAllNotDeleted();
		assertFalse(found.isEmpty());
		for(Hall h : found) {
			assertFalse(h.isDeleted());
		}
	}
	
	@Test
	public void testFindAllNotDeletedPageable() {
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<Hall> found = hallService.findAllNotDeleted(pageRequest);
		for(Hall h : found) {
			assertFalse(h.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindOne() throws ResourceNotFoundException {
		Hall found = hallService.findOne(DB_HALL_ID);
		assertNotNull(found);
		assertTrue(DB_HALL_ID == found.getId());
		assertEquals(DB_HALL_NAME, found.getName());
		assertEquals(DB_HALL_LOCATION_ID, found.getLocation().getId());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		@SuppressWarnings("unused")
		Hall found = hallService.findOne(HALL_ID_NON_EXISTENT);
		
	}
	
	@Test
	public void testFindOneDeleted_shouldFindDeletedHall() throws ResourceNotFoundException {
		Hall found = hallService.findOne(DB_HALL_DELETED);
		assertNotNull(found);
		assertTrue(DB_HALL_DELETED == found.getId());
		assertEquals(DB_HALL_DELETED_NAME, found.getName());
		assertTrue(found.isDeleted());
	}
	
	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		Hall found = hallService.findOneNotDeleted(DB_HALL_ID);
		assertNotNull(found);
		assertTrue(DB_HALL_ID == found.getId());
		assertEquals(DB_HALL_NAME, found.getName());
		assertEquals(DB_HALL_LOCATION_ID, found.getLocation().getId());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindDeletedHall() throws ResourceNotFoundException {
		hallService.findOneNotDeleted(DB_HALL_DELETED);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindNonExistingHall() throws ResourceNotFoundException {
		hallService.findOneNotDeleted(HALL_ID_NON_EXISTENT);
	}
	
	@Test
	@Transactional
	public void testSave() throws ResourceNotFoundException {
		
		int dbSizeBeforeAdd = hallService.findAll().size();
		NEW_HALL.setLocation(locationService.findOneNotDeleted(DB_HALL_LOCATION_ID));
		Hall found = hallService.save(NEW_HALL);
		
		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd+1, hallService.findAll().size());
		assertEquals(NEW_HALL.getName(), found.getName());
		assertEquals(NEW_HALL.getLocation().getId(), found.getLocation().getId());
		assertFalse(found.isDeleted());
		assertTrue((long) (dbSizeBeforeAdd+1) == found.getId());
		
	}
	

	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		int db_size_before_delete = hallService.findAll().size();
		
		hallService.delete(DB_HALL_TO_BE_DELETED);
		
		int db_size_after_delete = hallService.findAll().size();
		
		Hall found = hallService.findOne(DB_HALL_TO_BE_DELETED);
		assertEquals(db_size_before_delete, db_size_after_delete); //nije se promenio broj zbog logickog brisanja
		assertNotNull(found); //nije null zbog logickog brisanja
		assertTrue(found.isDeleted()); //samo se indikator deleted postavio na true
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		hallService.delete(DB_HALL_DELETED); //dvorana je vec obrisana i nece biti pronadjena
	}
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		int dbSizeBeforeUpd = hallService.findAll().size();
		Hall updated = hallService.update(DB_HALL_TO_BE_UPDATED, UPD_HALL);
		assertNotNull(updated);
		assertEquals(UPD_HALL.getName(), updated.getName());
		
		Hall found = hallService.findOneNotDeleted(DB_HALL_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dbSizeBeforeUpd, hallService.findAll().size());  //ne bi trebalo da se promeni broj dvorana u bazi
		assertEquals(UPD_HALL.getName(), found.getName());
		assertFalse(found.isDeleted());
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		/*
		 * nece pronaci dvoranu sa prosledjenim id-em pa nije potrebno slati stvarnu dvoranu
		 * dovoljno je samo null
		 */
		hallService.update(DB_HALL_DELETED, null); 
	}
}
