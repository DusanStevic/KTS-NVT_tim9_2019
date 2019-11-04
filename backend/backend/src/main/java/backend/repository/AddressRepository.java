package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import backend.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
