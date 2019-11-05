package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
