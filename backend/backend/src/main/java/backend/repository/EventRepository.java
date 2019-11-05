package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
