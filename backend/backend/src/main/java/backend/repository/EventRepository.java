package backend.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import backend.model.Event;
import backend.model.EventType;

public interface EventRepository extends JpaRepository<Event, Long> {

	@Query("select e from Event e where (e.startDate between ?1 and ?2) and (e.endDate between ?1 and ?2) ")
	List<Event> findAllByInterval(Date startDate, Date endDate);
	
	/*@Query("select e from Event e where e.deleted = false")
	public List<Event> findAllActive();*/

	Optional<Event> findByIdAndDeleted(Long id, boolean deleted);
	List<Event> findAllByDeleted(boolean deleted);
	Page<Event> findAllByDeleted(boolean deleted, Pageable pageable);
	
	@Query("SELECT e FROM Event e WHERE e.name like concat('%',?1,'%')"
			+ " and (e.startDate >= ?2 or ?2 = null) and (e.endDate <= ?3 or ?3 = null) and"
			+ " (e.eventType = ?4 or ?4 = null) and (e.location.id = ?5 or ?5 = null)")
	List<Event> search(String name, Date startDate, Date endDate, EventType eventType,
			Long locationId);
	
	
}
