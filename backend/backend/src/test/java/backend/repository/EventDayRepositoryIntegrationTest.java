package backend.repository;

import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.EventDayConstants.DB_EVENTDAY_DELETED;
import static backend.constants.EventDayConstants.DB_EVENTDAY_ID;
import static backend.constants.EventDayConstants.DB_EVENTDAY_NAME;
import static backend.constants.EventDayConstants.EVENTDAY_ID_NON_EXISTENT;
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

import backend.model.EventDay;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class EventDayRepositoryIntegrationTest {


	@Autowired
	EventDayRepository eventRepository;
	
	@Test
	public void testFindByIdAndDeleted() {
		Optional<EventDay> found = eventRepository.findByIdAndDeleted(DB_EVENTDAY_ID, false);
		EventDay e = found.get();
		assertNotNull(e);
		assertFalse(e.isDeleted());
		assertTrue(DB_EVENTDAY_ID == e.getId());
		assertEquals(DB_EVENTDAY_NAME, e.getName());
		assertTrue(e.getTickets().isEmpty());
	}
	
	@Test
	public void testFindByIdAndDeleted_findsDeleted() {
		Optional<EventDay> found = eventRepository.findByIdAndDeleted(DB_EVENTDAY_DELETED, true);
		EventDay e = found.get();
		assertNotNull(e);
		assertTrue(e.isDeleted());
		assertTrue(DB_EVENTDAY_DELETED == e.getId());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NonExistent() {
		/*
		 * dvorana sa datim id-em ne postoji
		 * tako da nije bitno sta prosledim za deleted
		 */
		Optional<EventDay> found = eventRepository.findByIdAndDeleted(EVENTDAY_ID_NON_EXISTENT, false);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NotDeleted() {
		/*
		 * trazena dvorana nije obrisana
		 * nece biti pronadjena jer trazim onu koja je obrisana
		 */
		Optional<EventDay> found = eventRepository.findByIdAndDeleted(DB_EVENTDAY_ID, true);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_Deleted() {
		/*
		 * trazena dvorana je obrisana
		 * nece biti pronadjena jer trazim onu koja nije obrisana
		 */
		Optional<EventDay> found = eventRepository.findByIdAndDeleted(DB_EVENTDAY_DELETED, false);
		assertNull(found.get());
	}
	
	
	@Test
	public void testFindAllByDeleted_False() {
		List<EventDay> found = eventRepository.findAllByDeleted(false); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(EventDay e:found) {
			assertFalse(e.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeleted_True() {
		List<EventDay> found = eventRepository.findAllByDeleted(true); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(EventDay e: found) {
			assertTrue(e.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeletedPageable_False() {
		PageRequest pageRequest = PageRequest.of(0, 5); //prva strana

		Page<EventDay> found = eventRepository.findAllByDeleted(false, pageRequest);
		for(EventDay e : found.getContent()) {
			assertFalse(e.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	
	@Test
	public void testFindAllByDeletedPageable_True() {
		
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<EventDay> found = eventRepository.findAllByDeleted(true, pageRequest);
		assertNotNull(found);
		for(EventDay e: found) {
			assertTrue(e.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
}
