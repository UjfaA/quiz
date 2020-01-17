package ujfaA.quiz.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ujfaA.quiz.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	Optional<Role> findByName(String name);

}