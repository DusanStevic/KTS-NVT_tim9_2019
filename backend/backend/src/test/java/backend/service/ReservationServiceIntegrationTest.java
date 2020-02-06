package backend.service;

import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.AddressConstants.pageRequest;
import static backend.constants.ReservationConstants.*;
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

import backend.dto.ReservationDetailedDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Reservation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ReservationServiceIntegrationTest {



	@Autowired
	ReservationService reservationService;
	
	@Test
	public void testFindAll() {
		List<Reservation> found = reservationService.findAll();
		assertFalse(found.isEmpty());
		assertEquals(DB_RESERVATION_COUNT, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		Page<Reservation> found = reservationService.findAll(pageRequest);
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindAllNotDeleted() {
		List<Reservation> found = reservationService.findAllNotCanceled();
		assertFalse(found.isEmpty());
		for(Reservation r : found) {
			assertFalse(r.isCanceled());
		}
	}
	////// 1. 
	@Test
	public void testFindAllNotDeletedPageable() {
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<Reservation> found = reservationService.findAllNotCanceled(pageRequest);
		for(Reservation e : found) {
			assertFalse(e.isCanceled());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindOne() throws ResourceNotFoundException {
		Reservation found = reservationService.findOne(DB_RESERVATION_ID);
		assertNotNull(found);
		assertTrue(DB_RESERVATION_ID == found.getId());
		assertFalse(found.isCanceled());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		@SuppressWarnings("unused")
		Reservation found = reservationService.findOne(RESERVATION_ID_NON_EXISTENT);
	}
	
	@Test
	public void testFindOneDeleted_shouldFindDeletedHall() throws ResourceNotFoundException {
		Reservation found = reservationService.findOne(DB_RESERVATION_DELETED);
		assertNotNull(found);
		assertTrue(DB_RESERVATION_DELETED == found.getId());
		//assertEquals(DB_EVENT_DELETED_NAME_FROM_SECTOR, found.getEvent().getName());
		assertTrue(found.isCanceled());
	}
	
	////// 2.
	
	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		Reservation found = reservationService.findOneNotCanceled(DB_RESERVATION_ID);
		assertNotNull(found);
		assertTrue(DB_RESERVATION_ID == found.getId());
		//assertEquals(DB_EVENT_NAME, found.getName());
		//assertEquals(DB_EVENT_LOCATION_ID, found.getLocation().getId());
		assertFalse(found.isCanceled());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindDeletedHall() throws ResourceNotFoundException {
		reservationService.findOneNotCanceled(DB_RESERVATION_DELETED);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindNonExistingHall() throws ResourceNotFoundException {
		reservationService.findOneNotCanceled(RESERVATION_ID_NON_EXISTENT);
	}
	
	@Test
	@Transactional
	public void testSave() throws ResourceNotFoundException {
		
		int dbSizeBeforeAdd = reservationService.findAll().size();
		System.out.println("AAAAAAAAAAAAAAAAA" + NEW_RESERVATION.toString());
		Reservation found = reservationService.save(NEW_RESERVATION);
		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd+1, reservationService.findAll().size());
		//assertEquals(NEW_EVENTSECTOR.getEvent().getName(), found.getEvent().getName());
		assertFalse(found.isCanceled());
		assertTrue((long) (dbSizeBeforeAdd+1) == found.getId());
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		int db_size_before_delete = reservationService.findAll().size();
		
		reservationService.delete1(DB_RESERVATION_TO_BE_DELETED);
		
		int db_size_after_delete = reservationService.findAll().size();
		
		Reservation found = reservationService.findOne(DB_RESERVATION_TO_BE_DELETED);
		assertEquals(db_size_before_delete, db_size_after_delete); //nije se promenio broj zbog logickog brisanja
		assertNotNull(found); //nije null zbog logickog brisanja
		assertTrue(found.isCanceled()); //samo se indikator deleted postavio na true
	}
	/*
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		reservationService.delete1(DB_RESERVATION_DELETED); //rezervacija je vec obrisana i nece biti pronadjena
	}*/
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		int dbSizeBeforeUpd = reservationService.findAll().size();
		Reservation updated = reservationService.update(DB_RESERVATION_TO_BE_UPDATED, UPD_RESERVATION);
		assertNotNull(updated);
		
		Reservation found = reservationService.findOneNotCanceled(DB_RESERVATION_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dbSizeBeforeUpd, reservationService.findAll().size());  //ne bi trebalo da se promeni broj dvorana u bazi
		assertFalse(found.isCanceled());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		/*
		 * nece pronaci eventSector sa prosledjenim id-em pa nije potrebno slati stvarnu cenu 
		 * dovoljno je samo 0
		 */
		reservationService.update(DB_RESERVATION_DELETED, null); 
	} 
	
	@Test
	@Transactional
	public void testFindMyReservations() {
		List<ReservationDetailedDTO> found = reservationService.findMyReservations(DB_PRINCIPAL_USER_USERNAME);
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(ReservationDetailedDTO e: found) {
			assertTrue(e.getUsername().equals(DB_PRINCIPAL_USER_USERNAME));
		}
	}
	
}
