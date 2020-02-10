package backend.service;

import static backend.constants.AddressConstants.pageRequest;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import backend.dto.ReservationDetailedDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Reservation;
import backend.repository.ReservationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReservationServiceUnitTest {

	@Autowired
	ReservationService reservationService;

	@MockBean
	ReservationRepository reservationRepositoryMocked;


	@MockBean
	ReservationDetailedDTO dtoMocked;
	
	public static final Long reservationId = 5L;
	public static final Long canceledReservationId = 6L;
	public static final Long nonExistentReservationId = 666L;
	public static final Reservation reservation = new Reservation(
			reservationId, false, new Date(), null, null, false);
	public static final Reservation reservation_canceled = new Reservation(
			canceledReservationId, false, new Date(), null, null, true);

	@Before
	public void setup() {
		List<Reservation> reservations = new ArrayList<>();
		reservations.add(reservation);
		reservations.add(reservation_canceled);

		Page<Reservation> reservationsPage = new PageImpl<>(reservations);
		System.out.println("aaaaaaaaaaaaaaaaa" + reservationsPage.getSize());

		when(reservationRepositoryMocked.findAll()).thenReturn(reservations);
		when(reservationRepositoryMocked.findAll(pageRequest)).thenReturn(
				reservationsPage);
		when(reservationRepositoryMocked.findAllByCanceled(false)).thenReturn(
				reservations);
		when(reservationRepositoryMocked.findAllByCanceled(false, pageRequest))
				.thenReturn(reservationsPage);
		when(reservationRepositoryMocked.findById(reservationId)).thenReturn(
				Optional.of(reservation));
		when(reservationRepositoryMocked.findById(canceledReservationId))
				.thenReturn(Optional.of(reservation_canceled));

	}

	@Test
	public void testFindAll() {
		List<Reservation> found = reservationService.findAll();
		assertNotNull(found);
		verify(reservationRepositoryMocked, times(1)).findAll();
	}

	@Test
	public void testFindAllPageable() {
		Page<Reservation> found = reservationService.findAll(pageRequest);
		assertNotNull(found);
		verify(reservationRepositoryMocked, times(1)).findAll(pageRequest);
	}

	@Test
	public void testFindAllNotDeleted() {
		List<Reservation> found = reservationService.findAllNotCanceled();
		assertNotNull(found);
		verify(reservationRepositoryMocked, times(1)).findAllByCanceled(false);
	}

	@Test
	public void testFindAllNotDeletedPageable() {
		Page<Reservation> found = reservationService
				.findAllNotCanceled(pageRequest);
		assertNotNull(found);
		verify(reservationRepositoryMocked, times(1)).findAllByCanceled(false,
				pageRequest);
	}

	// /////// 1.

	@Test
	public void testFindOne() throws ResourceNotFoundException {
		Reservation found = reservationService.findOne(reservationId);
		assertNotNull(found);
		assertTrue(reservationId == found.getId());
		assertFalse(found.isCanceled());
		verify(reservationRepositoryMocked, times(1)).findById(reservationId);
	}

	// /

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		reservationService.findOne(nonExistentReservationId);
	}

	@Test
	public void testFindOneDeleted_shouldFindDeletedAddress()
			throws ResourceNotFoundException {
		Reservation found = reservationService.findOne(canceledReservationId);
		assertNotNull(found);
		assertTrue(canceledReservationId == found.getId());
		assertTrue(found.isCanceled());
		verify(reservationRepositoryMocked, times(1)).findById(
				canceledReservationId);
	}

	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		when(
				reservationRepositoryMocked.findByIdAndCanceled(reservationId,
						false)).thenReturn(Optional.of(reservation));
		Reservation found = reservationService
				.findOneNotCanceled(reservationId);
		assertNotNull(found);
		assertTrue(reservationId == found.getId());
		assertFalse(found.isCanceled());
		// assertEquals(reservation.getPrice(), found.getPrice(),2);
		verify(reservationRepositoryMocked, times(1)).findByIdAndCanceled(
				reservationId, false);
	}

	// //// 2.
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_nonExistentAddress()
			throws ResourceNotFoundException {
		reservationService.findOneNotCanceled(nonExistentReservationId);
	}

	@Test
	public void testSave() {
		when(reservationRepositoryMocked.save(reservation)).thenReturn(
				reservation);
		Reservation saved = reservationService.save(reservation);
		assertNotNull(saved);
		assertFalse(saved.isCanceled());
		verify(reservationRepositoryMocked, times(1)).save(reservation);
	}

	@Test
	public void testDelete() throws ResourceNotFoundException {
		when(
				reservationRepositoryMocked.findByIdAndCanceled(reservationId,
						false)).thenReturn(Optional.of(reservation));
		System.out.println(reservation.isCanceled());
		reservationService.delete1(reservationId);
		System.out.println(reservation.isCanceled());
		// verify(reservationRepositoryMocked,
		// times(1)).findByIdAndCanceled(reservationId, false);
		// verify(reservationRepositoryMocked, times(1)).save(reservation);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		reservationService.delete(nonExistentReservationId);
	}

	@Test
	public void testUpdate() throws ResourceNotFoundException {
		when(
				reservationRepositoryMocked.findByIdAndCanceled(reservationId,
						false)).thenReturn(Optional.of(reservation));
		// (reservationId, false, new Date(),null, null,false)
		Reservation upd = new Reservation(reservationId, false, new Date(),
				null, null, false);
		when(reservationRepositoryMocked.save(upd)).thenReturn(upd);
		Reservation updated = reservationService.update(reservationId, upd);

		verify(reservationRepositoryMocked, times(1)).findByIdAndCanceled(
				reservationId, false);
		verify(reservationRepositoryMocked, times(1)).save(upd);
		assertNotNull(updated);
		assertTrue(reservationId == updated.getId());
		assertFalse(updated.isCanceled());

	}

	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		reservationService.update(nonExistentReservationId, null);
	}

}
