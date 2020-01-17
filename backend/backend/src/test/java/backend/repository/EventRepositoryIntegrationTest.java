package backend.repository;

import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.EventConstants.DB_EVENT_NAME;
import static backend.constants.EventConstants.DB_EVENT_DELETED;
import static backend.constants.EventConstants.DB_EVENT_ID;
import static backend.constants.EventConstants.DB_EVENT_LOCATION_ID;
import static backend.constants.EventConstants.EVENT_ID_NON_EXISTENT;
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

import backend.model.Event;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class EventRepositoryIntegrationTest {
	
	@Autowired
	EventRepository eventRepository;
	
	@Test
	public void testFindByIdAndDeleted() {
		Optional<Event> found = eventRepository.findByIdAndDeleted(DB_EVENT_ID, false);
		Event e = found.get();
		assertNotNull(e);
		assertFalse(e.isDeleted());
		assertTrue(DB_EVENT_ID == e.getId());
		assertEquals(DB_EVENT_NAME, e.getName());
		assertEquals(DB_EVENT_LOCATION_ID, e.getLocation().getId());
		assertTrue(e.getEventSectors().isEmpty());
		//assertEquals(6, e.getEventSectors().size());
	}
	
	@Test
	public void testFindByIdAndDeleted_findsDeleted() {
		Optional<Event> found = eventRepository.findByIdAndDeleted(DB_EVENT_DELETED, true);
		Event e = found.get();
		assertNotNull(e);
		assertTrue(e.isDeleted());
		assertTrue(DB_EVENT_DELETED == e.getId());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NonExistent() {
		/*
		 * dvorana sa datim id-em ne postoji
		 * tako da nije bitno sta prosledim za deleted
		 */
		Optional<Event> found = eventRepository.findByIdAndDeleted(EVENT_ID_NON_EXISTENT, false);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NotDeleted() {
		/*
		 * trazena dvorana nije obrisana
		 * nece biti pronadjena jer trazim onu koja je obrisana
		 */
		Optional<Event> found = eventRepository.findByIdAndDeleted(DB_EVENT_ID, true);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_Deleted() {
		/*
		 * trazena dvorana je obrisana
		 * nece biti pronadjena jer trazim onu koja nije obrisana
		 */
		Optional<Event> found = eventRepository.findByIdAndDeleted(DB_EVENT_DELETED, false);
		assertNull(found.get());
	}
	
	
	@Test
	public void testFindAllByDeleted_False() {
		List<Event> found = eventRepository.findAllByDeleted(false); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Event e:found) {
			assertFalse(e.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeleted_True() {
		List<Event> found = eventRepository.findAllByDeleted(true); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Event e: found) {
			assertTrue(e.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeletedPageable_False() {
		PageRequest pageRequest = PageRequest.of(0, 5); //prva strana

		Page<Event> found = eventRepository.findAllByDeleted(false, pageRequest);
		for(Event e : found.getContent()) {
			assertFalse(e.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	
	@Test
	public void testFindAllByDeletedPageable_True() {
		
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<Event> found = eventRepository.findAllByDeleted(true, pageRequest);
		assertNotNull(found);
		for(Event e: found) {
			assertTrue(e.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
}
