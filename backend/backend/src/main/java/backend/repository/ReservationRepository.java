package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	/*
	@Query("select r from Reservation r where r.deleted = false")
	public List<Reservation> findAllActive();
	*/
	
	@Query("select r from Reservation r JOIN Ticket t ON t.reservation.id = r.id WHERE t.eventDay.event.id = ?1")
	public List<Reservation> findByEvent(Long event_id);

	public Optional<Reservation> findByIdAndCanceled(Long dbReservationId, boolean b);

	public List<Reservation> findAllByCanceled(boolean b);

	public Page<Reservation> findAllByCanceled(boolean b, Pageable pageable);
}
