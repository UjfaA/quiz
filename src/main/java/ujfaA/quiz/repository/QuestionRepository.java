package ujfaA.quiz.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ujfaA.quiz.model.Question;
import ujfaA.quiz.model.User;

public interface QuestionRepository extends CrudRepository<Question, Long> {
	
	public List<Question> findByOrderById();
	
	@Query("SELECT q.questionText FROM Question q")
	public Set<String> findAllquestionTexts();
	
	@Query("SELECT q.usersAnswered FROM Question q WHERE q.questionText = ?1")
	public Set<User> getUsersThatAnsweredQuestion(String questionText);
	
	@Query("SELECT q.usersAnsweredCorectly FROM Question q WHERE q.questionText = ?1")
	public Set<User> getUsersThatAnsweredQuestionCorrectly(String questionText);
}
