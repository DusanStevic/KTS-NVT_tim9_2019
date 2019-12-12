package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Sector;
import backend.model.User;

public interface SectorRepository extends JpaRepository<Sector, Long> {

	User findByName(String name);  //??
	
	Optional<Sector> findByIdAndDeleted(Long id, boolean deleted);
	List<Sector> findAllByDeleted(boolean deleted);
	
	Page<Sector> findAllByDeleted(boolean deleted, Pageable pageable);
}
