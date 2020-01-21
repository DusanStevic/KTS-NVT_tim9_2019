package backend.repository;

import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.ReservationConstants.*;

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

import backend.model.Reservation;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ReservationRepositoryIntegrationTest {

	@Autowired
	ReservationRepository reservationRepository;
	
	@Test
	public void testFindByIdAndDeleted() {
		Optional<Reservation> found = reservationRepository.findByIdAndCanceled(DB_RESERVATION_ID, false);
		Reservation e = found.get();
		assertNotNull(e);
		assertFalse(e.isCanceled());
		assertTrue(DB_RESERVATION_ID == e.getId());
		//assertEquals(DB_EVENT_NAME, e.getName());
		//assertEquals(DB_EVENT_LOCATION_ID, e.getLocation().getId());
		//assertTrue(e.getEventSectors().isEmpty());
		//assertEquals(6, e.getEventSectors().size());
	}
	
	@Test
	public void testFindByIdAndDeleted_findsDeleted() {
		Optional<Reservation> found = reservationRepository.findByIdAndCanceled(DB_RESERVATION_DELETED, true);
		Reservation e = found.get();
		assertNotNull(e);
		assertTrue(e.isCanceled());
		assertTrue(DB_RESERVATION_DELETED == e.getId());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NonExistent() {
		/*
		 * dvorana sa datim id-em ne postoji
		 * tako da nije bitno sta prosledim za deleted
		 */
		Optional<Reservation> found = reservationRepository.findByIdAndCanceled(RESERVATION_ID_NON_EXISTENT, false);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NotDeleted() {
		/*
		 * trazena dvorana nije obrisana
		 * nece biti pronadjena jer trazim onu koja je obrisana
		 */
		Optional<Reservation> found = reservationRepository.findByIdAndCanceled(DB_RESERVATION_ID, true);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_Deleted() {
		/*
		 * trazena dvorana je obrisana
		 * nece biti pronadjena jer trazim onu koja nije obrisana
		 */
		Optional<Reservation> found = reservationRepository.findByIdAndCanceled(DB_RESERVATION_DELETED, false);
		assertNull(found.get());
	}
	
	
	@Test
	public void testFindAllByDeleted_False() {
		List<Reservation> found = reservationRepository.findAllByCanceled(false); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Reservation e:found) {
			assertFalse(e.isCanceled());
		}
	}
	
	@Test
	public void testFindAllByDeleted_True() {
		List<Reservation> found = reservationRepository.findAllByCanceled(true); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Reservation e: found) {
			assertTrue(e.isCanceled());
		}
	}
	
	@Test
	public void testFindAllByDeletedPageable_False() {
		PageRequest pageRequest = PageRequest.of(0, 5); //prva strana

		Page<Reservation> found = reservationRepository.findAllByCanceled(false, pageRequest);
		for(Reservation e : found.getContent()) {
			assertFalse(e.isCanceled());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	
	@Test
	public void testFindAllByDeletedPageable_True() {
		
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<Reservation> found = reservationRepository.findAllByCanceled(true, pageRequest);
		assertNotNull(found);
		for(Reservation e: found) {
			assertTrue(e.isCanceled());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
}
