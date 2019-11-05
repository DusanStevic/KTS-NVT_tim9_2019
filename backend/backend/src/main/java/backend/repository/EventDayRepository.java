package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.EventDay;

public interface EventDayRepository extends JpaRepository<EventDay, Long> {

}
