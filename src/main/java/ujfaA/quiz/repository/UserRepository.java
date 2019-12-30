package ujfaA.quiz.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ujfaA.quiz.model.User;

public interface UserRepository extends CrudRepository<User, String> {
	
	public Optional<User> findByUsername(String username);
}
