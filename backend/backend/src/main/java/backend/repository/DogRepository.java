package backend.repository;

import org.springframework.data.repository.CrudRepository;

import backend.model.Dog;

public interface DogRepository extends CrudRepository<Dog, Long> {
}