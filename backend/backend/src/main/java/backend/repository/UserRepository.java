package backend.repository;

import backend.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByUsername(String username);

	User findByUsernameAndPassword(String username, String password);

	@Query("select k from User k where k.id = ?1")
	User findOneID(Long id);

	@Query("select k from User k where k.id in ?1")
	ArrayList<User> findKorisniciIds(List<Long> ids);
	
	@Query("select k from User k where k.id in ?1")
	Set<User> findKorisniciIdsSet(List<Long> ids);
}
