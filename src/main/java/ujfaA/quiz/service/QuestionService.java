package ujfaA.quiz.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.quiz.model.Question;
import ujfaA.quiz.model.User;
import ujfaA.quiz.repository.QuestionRepository;

@Service
@Transactional
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepo;
	
	public List<Question> listAll() {
		List<Question> list = new ArrayList<Question>();
		questionRepo.findAll().forEach(list::add);
		return list;
	}
	
	public Optional<Question> getQuestionById(Long id) {
		return questionRepo.findById(id);
	}
	
	public int getNumberOfQuestions() {
		return (int) questionRepo.count();
	}
	
	public Question getQuestionByIndex(int qindex) {
		List<Question> qList = questionRepo.findByOrderById();
		return qList.get(qindex);
	}
	
	public Question save(Question q) {
		List<String> ans = q.getAnswers();
		String correctAnswer = ans.get(q.getCorrectAnswerIndex());  
		q.setCorrectAnswer(correctAnswer);
		return questionRepo.save(q);
	}
	
	public void delete(Long id) {
		questionRepo.deleteById(id);
	}
	
	public Collection<String> GetQuestionsText() {
		return questionRepo.findAllquestionTexts();
	}
	
	public Set<User> getUsersAnswered(String questionText, boolean answeredCorrectly) {
		if (answeredCorrectly)
			return questionRepo.getUsersThatAnsweredQuestionCorrectly(questionText);
		else
			return questionRepo.getUsersThatAnsweredQuestion(questionText);
	}
	
	//
	public Set<Question> getTestQuestions() {
		Set<Question> set = new HashSet<Question>();
		set.add(getQuestionByIndex(0));
		set.add(getQuestionByIndex(1));		
		return set;
	}

	public List<Question> listAllOrderedByCorrectnes() {
		List<Question> questions = this.listAll();
		questions.sort((q1, q2) -> {
			return Double.compare(	q2.getCorrectnesstPercent(),
									q1.getCorrectnesstPercent());
		});
		return questions;
	}
}
