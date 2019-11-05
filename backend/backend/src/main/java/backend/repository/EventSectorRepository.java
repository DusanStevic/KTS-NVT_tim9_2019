package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.EventSector;

public interface EventSectorRepository extends JpaRepository<EventSector, Long> {

}
