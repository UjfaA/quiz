package ujfaA.quiz.repository;

import org.springframework.data.repository.CrudRepository;

import ujfaA.quiz.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {

}
