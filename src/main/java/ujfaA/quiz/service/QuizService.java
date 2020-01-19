package ujfaA.quiz.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.quiz.model.Question;
import ujfaA.quiz.model.User;

@Service
@Transactional
public class QuizService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;

	
	public List<User> getTopUsers() {
		return userService.getHighestRanked(5);	
	}
	
	public void userAnswered(String username, int questionIndex, String[] UsersAnswers) {
		
		Question q = questionService.getQuestionByIndex(questionIndex);
		User user = userService.getUser(username);
		
		if (UsersAnswers.length == 1 && UsersAnswers[0].contentEquals(q.getCorrectAnswer()) ) 		
			user.addAnsweredQuestionCorrectly(q);
		else
			user.addAnsweredQuestion(q);
		
		userService.update(user);
	}
	
	public List<String> getEndOfQuizMessages(String username, List<User> rankings) {
		
		List<String> messages = new ArrayList<String>();
		User user = userService.getUser(username);
		int score = user.getScore();
		messages.add("Broj tačnih odgovora: " + score);
		if (score == this.getMaxScore()) {
			messages.add("\u2B50 \u2B50 \u2B50\n");
			messages.add("Čestitamo, " + user.getFirstName() + "! Osvojili ste maksimalan broj bodova!");
			return messages;
		}
		if (rankings.isEmpty() || user.equals(rankings.get(0)) ) {
			messages.add("\u2B50 \u2B50 \u2B50\n");
			messages.add("Čestitamo, " + user.getFirstName() +"! Osvojili ste prvo mesto!");
			return messages;
		}
		if (rankings.contains(user)) {
			messages.add("\u2B50 \u2B50 \u2B50\n");
			messages.add("Čestitamo, " + user.getFirstName() +"! Ostvareni rezultat je jedan od 5 najboljih!");
			return messages;
		}
		return messages;
	}

	private long getMaxScore() {
		return questionService.getNumberOfQuestions();
	}

	public Set<String> getUsersThatAnsweredAll(boolean answeredCorrectly) {
		
		Integer qCount = questionService.getNumberOfQuestions();
		if(qCount == 0)
			return new HashSet<String>();
		else
			return userService.getUsersAnsweredNumOfQuestions(qCount, answeredCorrectly);		
	}

	public Set<String> getUsersThatAnsweredQuestion(String questionText, boolean answeredCorrectly) {		
		Question q = questionService.getQuestionByQuestionText(questionText);
		return userService.getUsersAnswered( q, answeredCorrectly);
	}

// TODO revisit, (but no iterators! - ConcurrentModificationException)
// TODO test user.set/Correctly/AnsweredQuestions(new Set<Question>())
	public void resetScore(String username) {
		
		User user = userService.getUser(username);
		Object[] questions = user.getAnsweredQuestions().toArray();
		for (int i = 0; i < questions.length; i++) {
			user.removeFromAnsweredQuestionCorrectly((Question) questions[i]);			
			user.removeFromAnsweredQuestion((Question) questions[i]);
		}
		userService.update(user);
	}
}
