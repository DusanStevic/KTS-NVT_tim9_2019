package backend.service;

import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.AddressConstants.pageRequest;
import static backend.constants.SectorConstants.*;

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

import backend.exceptions.BadRequestException;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Hall;
import backend.model.Sector;
import backend.model.SittingSector;
import backend.model.StandingSector;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SectorServiceIntegrationTest {

	@Autowired
	SectorService sectorService;
	
	@Autowired
	HallService hallService;
	
	@Test
	public void testFindAll() {
		List<Sector> found = sectorService.findAll();
		assertFalse(found.isEmpty());
		//assertEquals(DB_SECTOR_COUNT, found.size());
		for(int i = 1; i <= 10; i++) {
			assertEquals(Long.valueOf(i), found.get(i-1).getId());
		}
				
	}
	
	@Test
	public void testFindAllPageable() {
		Page<Sector> found = sectorService.findAll(pageRequest);
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindAllNotDeleted() {
		List<Sector> found = sectorService.findAllNotDeleted();
		assertFalse(found.isEmpty());
		for(Sector s : found) {
			assertFalse(s.isDeleted());
		}
	}
	
	@Test
	public void testFindAllNotDeletedPageable() {
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<Sector> found = sectorService.findAllNotDeleted(pageRequest);
		for(Sector s : found) {
			assertFalse(s.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindOne_STAND() throws ResourceNotFoundException {
		StandingSector found = (StandingSector) sectorService.findOne(DB_STAND_SECTOR_ID);
		assertNotNull(found);
		assertTrue(DB_STAND_SECTOR_ID == found.getId());
		assertEquals(DB_STAND_SECTOR_NAME, found.getName());
		assertEquals(DB_SECTOR_HALL_ID, found.getHall().getId());
		assertFalse(found.isDeleted());
		assertEquals(DB_STAND_SECTOR_CAPACITY, found.getCapacity());
	}
	
	@Test
	public void testFindOne_SIT() throws ResourceNotFoundException {
		SittingSector found = (SittingSector) sectorService.findOne(DB_SIT_SECTOR_ID);
		assertNotNull(found);
		assertTrue(DB_SIT_SECTOR_ID == found.getId());
		assertEquals(DB_SIT_SECTOR_NAME, found.getName());
		assertEquals(DB_SECTOR_HALL_ID, found.getHall().getId());
		assertFalse(found.isDeleted());
		assertEquals(DB_SIT_SECTOR_ROWS, found.getNumRows());
		assertEquals(DB_SIT_SECTOR_COLS, found.getNumCols());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		@SuppressWarnings("unused")
		Sector found = sectorService.findOne(SECTOR_ID_NON_EXISTENT);
		
	}
	
	@Test
	public void testFindOneDeleted_shouldFindDeletedSector() throws ResourceNotFoundException {
		Sector found = sectorService.findOne(DB_STAND_SECTOR_DELETED);
		assertNotNull(found);
		assertTrue(DB_STAND_SECTOR_DELETED == found.getId());
		assertEquals(DB_STAND_SECTOR_DELETED_NAME, found.getName());
		assertTrue(found.isDeleted());
	}
	
	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		Sector found = sectorService.findOneNotDeleted(DB_SIT_SECTOR_ID);
		assertNotNull(found);
		assertTrue(DB_SIT_SECTOR_ID == found.getId());
		assertEquals(DB_SIT_SECTOR_NAME, found.getName());
		assertEquals(DB_SECTOR_HALL_ID, found.getHall().getId());
		assertFalse(found.isDeleted());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindDeletedSector() throws ResourceNotFoundException {
		sectorService.findOneNotDeleted(DB_SIT_SECTOR_DELETED);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindNonExistingSector() throws ResourceNotFoundException {
		sectorService.findOneNotDeleted(SECTOR_ID_NON_EXISTENT);
	}
	
	@Test
	@Transactional
	public void testSave_STAND() throws ResourceNotFoundException {
		
		int dbSizeBeforeAdd = sectorService.findAll().size();
		NEW_STAND_SECTOR.setHall(hallService.findOneNotDeleted(DB_SECTOR_HALL_ID));
		StandingSector found = (StandingSector) sectorService.save(NEW_STAND_SECTOR);
		
		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd+1, sectorService.findAll().size());
		assertEquals(NEW_STAND_SECTOR.getName(), found.getName());
		assertEquals(NEW_STAND_SECTOR.getHall().getId(), found.getHall().getId());
		assertEquals(NEW_STAND_SECTOR.getCapacity(), found.getCapacity());
		assertFalse(found.isDeleted());
		assertTrue((long) (dbSizeBeforeAdd+1) == found.getId());
		
	}
		
	@Test
	@Transactional
	public void testSave_SIT() throws ResourceNotFoundException {
		
		int dbSizeBeforeAdd = sectorService.findAll().size();
		NEW_SIT_SECTOR.setHall(hallService.findOneNotDeleted(DB_SECTOR_HALL_ID));
		SittingSector found = (SittingSector) sectorService.save(NEW_SIT_SECTOR);
		
		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd+1, sectorService.findAll().size());
		assertEquals(NEW_SIT_SECTOR.getName(), found.getName());
		assertEquals(NEW_SIT_SECTOR.getHall().getId(), found.getHall().getId());
		assertEquals(NEW_SIT_SECTOR.getNumRows(), found.getNumRows());
		assertEquals(NEW_SIT_SECTOR.getNumCols(), found.getNumCols());
		assertFalse(found.isDeleted());
		assertTrue((long) (dbSizeBeforeAdd+1) == found.getId());
		
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		int db_size_before_delete = sectorService.findAll().size();
		
		sectorService.delete(DB_STAND_SECTOR_TO_BE_DELETED);
		
		int db_size_after_delete = sectorService.findAll().size();
		
		StandingSector found = (StandingSector) sectorService.findOne(DB_STAND_SECTOR_TO_BE_DELETED);
		assertEquals(db_size_before_delete, db_size_after_delete); //nije se promenio broj zbog logickog brisanja
		assertNotNull(found); //nije null zbog logickog brisanja
		assertTrue(found.isDeleted()); //samo se indikator deleted postavio na true
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		sectorService.delete(DB_SIT_SECTOR_DELETED); //sektor je vec obrisan i nece biti pronadjen
	}
	
	@Test
	public void testUpdate_STAND() throws ResourceNotFoundException {
		int dbSizeBeforeUpd = sectorService.findAll().size();
		StandingSector updated = sectorService.update(DB_STAND_SECTOR_TO_BE_UPDATED, UPD_STAND);
		assertNotNull(updated);
		assertEquals(UPD_STAND.getName(), updated.getName());
		assertEquals(UPD_STAND.getCapacity(), updated.getCapacity());
		
		StandingSector found = (StandingSector) sectorService.findOneNotDeleted(DB_STAND_SECTOR_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dbSizeBeforeUpd, sectorService.findAll().size());  //ne bi trebalo da se promeni broj dvorana u bazi
		assertEquals(UPD_STAND.getName(), found.getName());
		assertEquals(UPD_STAND.getCapacity(), found.getCapacity());
		assertFalse(found.isDeleted());
		
	}
	
	@Test
	public void testUpdate_SIT() throws ResourceNotFoundException {
		int dbSizeBeforeUpd = sectorService.findAll().size();
		SittingSector updated = sectorService.update(DB_SIT_SECTOR_TO_BE_UPDATED, UPD_SIT);
		assertNotNull(updated);
		assertEquals(UPD_SIT.getName(), updated.getName());
		assertEquals(UPD_SIT.getNumCols(), updated.getNumCols());
		assertEquals(UPD_SIT.getNumRows(), updated.getNumRows());
		
		SittingSector found = (SittingSector) sectorService.findOneNotDeleted(DB_SIT_SECTOR_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dbSizeBeforeUpd, sectorService.findAll().size());  //ne bi trebalo da se promeni broj dvorana u bazi
		assertEquals(UPD_SIT.getName(), found.getName());
		assertEquals(UPD_SIT.getNumCols(), found.getNumCols());
		assertEquals(UPD_SIT.getNumRows(), found.getNumRows());
		assertFalse(found.isDeleted());
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException_STAND() throws ResourceNotFoundException, BadRequestException {
		/*
		 * nece pronaci sektor sa prosledjenim id-em pa nije potrebno slati stvarni sektor
		 * dovoljno je samo null
		 */
		sectorService.update(DB_SIT_SECTOR_DELETED, new StandingSector()); 
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException_SIT() throws ResourceNotFoundException, BadRequestException {
		/*
		 * nece pronaci sektor sa prosledjenim id-em pa nije potrebno slati stvarni sektor
		 * dovoljno je samo null
		 */
		sectorService.update(DB_SIT_SECTOR_DELETED, new SittingSector()); 
	}
}
