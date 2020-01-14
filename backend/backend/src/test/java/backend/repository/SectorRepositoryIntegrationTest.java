package backend.repository;

import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.SectorConstants.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import backend.model.Hall;
import backend.model.Sector;
import backend.model.SittingSector;
import backend.model.StandingSector;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class SectorRepositoryIntegrationTest {

	@Autowired
	SectorRepository sectorRepository;

	
	@Test
	public void testFindByIdAndDeleted_StandingSector() {
		Optional<Sector> found = sectorRepository.findByIdAndDeleted(DB_STAND_SECTOR_ID, false);
		StandingSector s = (StandingSector) found.get();
		assertNotNull(s);
		assertFalse(s.isDeleted());
		assertTrue(DB_STAND_SECTOR_ID == s.getId());
		assertEquals(DB_STAND_SECTOR_NAME, s.getName());
		assertEquals(DB_SECTOR_HALL_ID, s.getHall().getId());
		assertEquals(DB_STAND_SECTOR_CAPACITY, s.getCapacity());
	}
	
	@Test
	public void testFindByIdAndDeleted_SittingSector() {
		Optional<Sector> found = sectorRepository.findByIdAndDeleted(DB_SIT_SECTOR_ID, false);
		SittingSector s = (SittingSector) found.get();
		assertNotNull(s);
		assertFalse(s.isDeleted());
		assertTrue(DB_SIT_SECTOR_ID == s.getId());
		assertEquals(DB_SIT_SECTOR_NAME, s.getName());
		assertEquals(DB_SECTOR_HALL_ID, s.getHall().getId());
		assertEquals(DB_SIT_SECTOR_ROWS, s.getNumRows());
		assertEquals(DB_SIT_SECTOR_COLS, s.getNumCols());
	}
	
	@Test
	public void testFindByIdAndDeleted_findsDeleted_STAND() {
		Optional<Sector> found = sectorRepository.findByIdAndDeleted(DB_STAND_SECTOR_DELETED, true);
		StandingSector s = (StandingSector) found.get();
		assertNotNull(s);
		assertTrue(s.isDeleted());
		assertTrue(DB_STAND_SECTOR_DELETED == s.getId());
	}
	
	@Test
	public void testFindByIdAndDeleted_findsDeleted_SIT() {
		Optional<Sector> found = sectorRepository.findByIdAndDeleted(DB_SIT_SECTOR_DELETED, true);
		SittingSector s = (SittingSector) found.get();
		assertNotNull(s);
		assertTrue(s.isDeleted());
		assertTrue(DB_SIT_SECTOR_DELETED == s.getId());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NonExistent() {
		/*
		 * sektor sa datim id-em ne postoji
		 * tako da nije bitno sta prosledim za deleted
		 */
		Optional<Sector> found = sectorRepository.findByIdAndDeleted(SECTOR_ID_NON_EXISTENT, false);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NotDeleted() {
		/*
		 * trazeni sektor nije obrisan
		 * nece biti pronadjen jer trazim onog koji je obrisan
		 */
		Optional<Sector> found = sectorRepository.findByIdAndDeleted(DB_STAND_SECTOR_ID, true);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_Deleted() {
		/*
		 * trazen sektor je obrisan
		 * nece biti pronadjen jer trazim onaj koji nije obrisan
		 */
		Optional<Sector> found = sectorRepository.findByIdAndDeleted(DB_STAND_SECTOR_DELETED, false);
		assertNull(found.get());
	}
	
	
	@Test
	public void testFindAllByDeleted_False() {
		List<Sector> found = sectorRepository.findAllByDeleted(false); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Sector s: found) {
			assertFalse(s.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeleted_True() {
		List<Sector> found = sectorRepository.findAllByDeleted(true); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Sector s: found) {
			assertTrue(s.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeletedPageable_False() {
		PageRequest pageRequest = PageRequest.of(0, 5); //prva strana

		Page<Sector> found = sectorRepository.findAllByDeleted(false, pageRequest);
		for(Sector s : found.getContent()) {
			assertFalse(s.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	
	@Test
	public void testFindAllByDeletedPageable_True() {
		
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<Sector> found = sectorRepository.findAllByDeleted(true, pageRequest);
		assertNotNull(found);
		for(Sector s: found) {
			assertTrue(s.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
}
