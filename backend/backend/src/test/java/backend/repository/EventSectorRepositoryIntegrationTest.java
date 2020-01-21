package backend.repository;

import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.EventConstants.DB_EVENT_DELETED;
import static backend.constants.EventConstants.DB_EVENT_ID;
import static backend.constants.EventConstants.EVENT_ID_NON_EXISTENT;
import static backend.constants.EventSectorConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.assertj.core.util.diff.Delta;
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

import backend.model.Event;
import backend.model.EventSector;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class EventSectorRepositoryIntegrationTest {

	@Autowired
	EventSectorRepository eventSectorRepository;
	
	@Test
	public void testFindByIdAndDeleted() {
		Optional<EventSector> found = eventSectorRepository.findByIdAndDeleted(DB_EVENTSECTOR_ID, false);
		EventSector e = found.get();
		assertNotNull(e);
		assertFalse(e.isDeleted());
		assertTrue(DB_EVENTSECTOR_ID == e.getId());
		assertEquals(DB_EVENTSECTOR_PRICE,e.getPrice(),2);
	}
	
	@Test
	public void testFindByIdAndDeleted_findsDeleted() {
		Optional<EventSector> found = eventSectorRepository.findByIdAndDeleted(DB_EVENTSECTOR_DELETED, true);
		EventSector e = found.get();
		assertNotNull(e);
		assertTrue(e.isDeleted());
		assertTrue(DB_EVENTSECTOR_DELETED == e.getId());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NonExistent() {
		/*
		 * EventSector sa datim id-em ne postoji
		 * tako da nije bitno sta prosledim za deleted
		 */
		Optional<EventSector> found = eventSectorRepository.findByIdAndDeleted(EVENTSECTOR_ID_NON_EXISTENT, false);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NotDeleted() {
		/*
		 * trazena dvorana nije obrisana
		 * nece biti pronadjena jer trazim onu koja je obrisana
		 */
		Optional<EventSector> found = eventSectorRepository.findByIdAndDeleted(DB_EVENTSECTOR_ID, true);
		assertNull(found.get());
	}
	
	//////////
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_Deleted() {
		/*
		 * trazena dvorana je obrisana
		 * nece biti pronadjena jer trazim onu koja nije obrisana
		 */
		Optional<EventSector> found = eventSectorRepository.findByIdAndDeleted(DB_EVENTSECTOR_DELETED, false);
		assertNull(found.get());
	}
	
	
	@Test
	public void testFindAllByDeleted_False() {
		List<EventSector> found = eventSectorRepository.findAllByDeleted(false); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(EventSector e:found) {
			assertFalse(e.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeleted_True() {
		List<EventSector> found = eventSectorRepository.findAllByDeleted(true); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(EventSector e: found) {
			assertTrue(e.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeletedPageable_False() {
		PageRequest pageRequest = PageRequest.of(0, 5); //prva strana

		Page<EventSector> found = eventSectorRepository.findAllByDeleted(false, pageRequest);
		for(EventSector e : found.getContent()) {
			assertFalse(e.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	
	@Test
	public void testFindAllByDeletedPageable_True() {
		
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<EventSector> found = eventSectorRepository.findAllByDeleted(true, pageRequest);
		assertNotNull(found);
		for(EventSector e: found) {
			assertTrue(e.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	
}
