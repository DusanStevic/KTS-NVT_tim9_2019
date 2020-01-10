package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.EventSector;

public interface EventSectorRepository extends JpaRepository<EventSector, Long> {

	Optional<EventSector> findByIdAndDeleted(Long id, boolean deleted);
	List<EventSector> findAllByDeleted(boolean deleted);
	Page<EventSector> findAllByDeleted(boolean deleted, Pageable pageable);
}
