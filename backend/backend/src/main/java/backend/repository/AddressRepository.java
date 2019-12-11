package backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findByIdAndDeleted(Long id, boolean deleted);
}
