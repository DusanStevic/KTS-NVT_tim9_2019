package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
