package backend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	//public List<Ticket> findAllByEventDayEventSector(EventDay event_day, EventSector event_sector);
	
	@Query("select t from Ticket t where t.eventDay.id = ?1 and t.eventSector.id = ?2 ")
	public List<Ticket> findAllByEventDayIDEventSectorID(Long ed_id, Long es_id);
	
	@Query("select t from Ticket t where t.eventDay.event.location.id = ?1 and t.eventDay.date >= ?2")
	public List<Ticket> findAllByLocationDate(Long loc_id, Date date);
	
	@Query("select t from Ticket t where t.eventDay.event.location.id = ?1")
	public List<Ticket> findAllByLocation(Long loc_id);
	
	@Query ("select t from Ticket t where t.eventDay.event.id = ?1")
	public List<Ticket> findAllByEvent(Long event_id);
}
