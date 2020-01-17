package ujfaA.quiz.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import ujfaA.quiz.model.Question;
import ujfaA.quiz.model.User;


public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
	
	public Page<Question> findByOrderById(Pageable pageable);
	
	@Query("SELECT q.questionText FROM Question q")
	public Set<String> findAllQuestionTexts();
	
	@Query("SELECT q.usersAnswered FROM Question q WHERE q.questionText = ?1")
	public Set<User> getUsersThatAnsweredQuestion(String questionText);
	
	@Query("SELECT q.usersAnsweredCorectly FROM Question q WHERE q.questionText = ?1")
	public Set<User> getUsersThatAnsweredQuestionCorrectly(String questionText);
}
