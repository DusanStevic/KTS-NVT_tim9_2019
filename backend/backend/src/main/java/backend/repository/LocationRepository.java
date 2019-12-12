package backend.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

	Optional<Location> findByIdAndDeleted(Long id, Timestamp deleted);
	List<Location> findAllByDeleted(Timestamp deleted);
	
	Page<Location> findAllByDeleted(Timestamp deleted, Pageable pageable);
}
