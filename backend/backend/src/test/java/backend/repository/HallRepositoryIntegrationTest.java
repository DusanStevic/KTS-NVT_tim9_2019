package backend.repository;

import static backend.constants.HallConstants.*;
import static backend.constants.AddressConstants.PAGE_SIZE;
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

import backend.model.Address;
import backend.model.Hall;
import backend.model.StandingSector;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class HallRepositoryIntegrationTest {

	@Autowired
	HallRepository hallRepository;

	
	@Test
	public void testFindByIdAndDeleted() {
		Optional<Hall> found = hallRepository.findByIdAndDeleted(DB_HALL_ID, false);
		Hall h = found.get();
		assertNotNull(h);
		assertFalse(h.isDeleted());
		assertTrue(DB_HALL_ID == h.getId());
		assertEquals(DB_HALL_NAME, h.getName());
		assertEquals(DB_HALL_LOCATION_ID, h.getLocation().getId());
		assertTrue(!h.getSectors().isEmpty());
		assertEquals(2, h.getSectors().size());
	}
	
	@Test
	public void testFindByIdAndDeleted_findsDeleted() {
		Optional<Hall> found = hallRepository.findByIdAndDeleted(DB_HALL_DELETED, true);
		Hall h = found.get();
		assertNotNull(h);
		assertTrue(h.isDeleted());
		assertTrue(DB_HALL_DELETED == h.getId());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NonExistent() {
		/*
		 * dvorana sa datim id-em ne postoji
		 * tako da nije bitno sta prosledim za deleted
		 */
		Optional<Hall> found = hallRepository.findByIdAndDeleted(HALL_ID_NON_EXISTENT, false);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NotDeleted() {
		/*
		 * trazena dvorana nije obrisana
		 * nece biti pronadjena jer trazim onu koja je obrisana
		 */
		Optional<Hall> found = hallRepository.findByIdAndDeleted(DB_HALL_ID, true);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_Deleted() {
		/*
		 * trazena dvorana je obrisana
		 * nece biti pronadjena jer trazim onu koja nije obrisana
		 */
		Optional<Hall> found = hallRepository.findByIdAndDeleted(DB_HALL_DELETED, false);
		assertNull(found.get());
	}
	
	
	@Test
	public void testFindAllByDeleted_False() {
		List<Hall> found = hallRepository.findAllByDeleted(false); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Hall h: found) {
			assertFalse(h.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeleted_True() {
		List<Hall> found = hallRepository.findAllByDeleted(true); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Hall h: found) {
			assertTrue(h.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeletedPageable_False() {
		PageRequest pageRequest = PageRequest.of(0, 5); //prva strana

		Page<Hall> found = hallRepository.findAllByDeleted(false, pageRequest);
		for(Hall h : found.getContent()) {
			assertFalse(h.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	
	@Test
	public void testFindAllByDeletedPageable_True() {
		
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<Hall> found = hallRepository.findAllByDeleted(true, pageRequest);
		assertNotNull(found);
		for(Hall h: found) {
			assertTrue(h.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
}
