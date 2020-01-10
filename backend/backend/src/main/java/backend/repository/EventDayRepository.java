package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.EventDay;

public interface EventDayRepository extends JpaRepository<EventDay, Long> {

	Optional<EventDay> findByIdAndDeleted(Long id, boolean deleted);
	List<EventDay> findAllByDeleted(boolean deleted);
	Page<EventDay> findAllByDeleted(boolean deleted, Pageable pageable);
}
