package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {
	Optional<Hall> findByIdAndDeleted(Long id, boolean deleted);
	List<Hall> findAllByDeleted(boolean deleted);
	
	Page<Hall> findAllByDeleted(boolean deleted, Pageable pageable);
}
