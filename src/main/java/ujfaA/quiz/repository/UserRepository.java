package ujfaA.quiz.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ujfaA.quiz.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	public Optional<User> findByUsername(String username);
	
	public Boolean existsUserByUsername(String username);

//	public Set<User> findByAnsweredQuestionsContains(Question q);
	
	@Query("SELECT u.username FROM User u where size(u.correctlyAnsweredQuestions) = ?1")
	public Set<String> findAllUsernamesCorrectlyAnsweredQuestionsCountEquals(Integer qNumber);
	
	@Query("SELECT u.username FROM User u where size(u.answeredQuestions) = ?1")
	public Set<String> findAllUsenamesAnsweredQuestionsCountEquals(Integer number);
	
	@Query("SELECT u FROM User u ORDER BY size(u.correctlyAnsweredQuestions) DESC")
	public List<User> findAllOrderByCorrectlyAnsweredQuestions();
	
	@Query("SELECT u FROM User u ORDER BY size(u.correctlyAnsweredQuestions) DESC")
	public Page<User> findAllOrderByCorrectlyAnsweredQuestions(Pageable pageable);
}
