package ujfaA.quiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.quiz.model.User;

@Service
@Transactional
public class QuizService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	
	public List<User> getRankings() {
		return userService.getHighestRanked(5);	
	}

	public List<String> getEndOfQuizMessages(String username, List<User> rankings) {
		
		List<String> messages = new ArrayList<String>();
		User user = userService.getUser(username);
		int score = user.getScore();
		messages.add("Broj tačnih odgovora: " + score);
		if (score == this.getMaxScore()) {
			messages.add("\u2B50 \u2B50 \u2B50");
			messages.add("\nČestitamo, " + user.getFirstName() + "! Osvojili ste maksimalan broj bodova!");
			return messages;
		}
		if (rankings.contains(user)) {
			messages.add("\u2B50 \u2B50 \u2B50");
			messages.add("\nČestitamo, " + user.getFirstName() +"! Ostvareni rezultat je jedan od 5 najboljih!");
			return messages;
		}
		return messages;
	}

	private long getMaxScore() {
		return questionService.getNumberOfQuestions();
	}

	public Set<String> getUsersThatAnsweredAll(boolean correctly) {
		Integer qNumber = questionService.getNumberOfQuestions();
		return userService.getUsersAnswered(qNumber, correctly);		
	}

}
