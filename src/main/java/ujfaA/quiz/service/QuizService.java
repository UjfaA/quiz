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
	
	public void userAnswered(String username, Question q, String[] answers) {
		
		User user = userService.getUser(username);
		if (answers.length == 1
			&& answers[0].contentEquals(q.getCorrectAnswer()) ) 
			{
			user.addAnsweredQuestionCorrectly(q);
			}
		else {
			user.addAnsweredQuestion(q);
			user.removeFromAnsweredQuestionCorrectly(q);
		}
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

	public Set<String> collectUsernames(List<String> avaibleStats, int selected, boolean answeredCorrectly) {	
		
		if (selected == 0)  // all questions
			return getUsersThatAnsweredAll(answeredCorrectly);
		else 
			return getUsersThatAnsweredQuestion(avaibleStats.get(selected), answeredCorrectly);
	}
	
	public Set<String> getUsersThatAnsweredAll(boolean answeredCorrectly) {
		
		Integer qNumber = questionService.getNumberOfQuestions();
		if(qNumber == 0)
			return new HashSet<String>();
		else
			return userService.getUsersAnsweredNumOfQuestions(qNumber, answeredCorrectly);		
	}

	private Set<String> getUsersThatAnsweredQuestion(String questionText, boolean answeredCorrectly) {
		
		Set<User> users = questionService.getUsersAnswered(questionText, answeredCorrectly);
		Set<String> usernames = new HashSet<String>();
		users.forEach(user -> usernames.add(user.getUsername()));
		return usernames;
	}

// TODO revisit, (but no iterators! - ConcurrentModificationException)
//TODO: test user.set/Correctly/AnsweredQuestions(new Set<Question>())
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
