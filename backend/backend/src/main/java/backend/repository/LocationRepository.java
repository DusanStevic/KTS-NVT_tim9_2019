package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
