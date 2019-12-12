package backend.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findByIdAndDeleted(Long id, boolean deleted);
	List<Address> findAllByDeleted(boolean deleted);
	
	Page<Address> findAllByDeleted(boolean deleted, Pageable pageable);
}
