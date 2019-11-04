package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Sector;
import backend.model.User;

public interface SectorRepository extends JpaRepository<Sector, Long> {

	User findByName(String name);
}
