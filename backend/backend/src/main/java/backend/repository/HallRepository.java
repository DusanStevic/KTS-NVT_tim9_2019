package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.model.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {

}
