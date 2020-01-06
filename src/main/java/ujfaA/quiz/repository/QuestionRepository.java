package ujfaA.quiz.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ujfaA.quiz.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {
	
	public List<Question> findByOrderById();
	
	@Query("SELECT q.questionText FROM Question q")
	public Set<String> findAllquestionTexts();
}
