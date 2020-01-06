package ujfaA.quiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.quiz.model.Question;
import ujfaA.quiz.model.User;
import ujfaA.quiz.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public List<User> listAll() {
		List<User> list = new ArrayList<User>();
		userRepo.findAll().forEach(list::add);
		return list;
	}
	
	public List<User> getHighestRanked(int limit) {
		Page<User> page = userRepo.findAllOrderByCorrectlyAnsweredQuestions(PageRequest.of(0, limit));
		return page.toList();
	}
	
	public User getUser(String username) {
		return userRepo.findByUsername(username).get();
	}
	
	public User save(User user) {
		return userRepo.save(user);
	}
	
	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	public void userAnswered(Long userId, Question q, String[] answers) {
		
		System.out.println("user service. user answered");
		User user = userRepo.findById(userId).get();
		if (answers.length == 1
			&& answers[0].contentEquals(q.getCorrectAnswer())
			)
			user.addAnsweredQuestionCorrectly(q);
		else
			user.addAnsweredQuestion(q);
		userRepo.save(user);
	}
	
	public Set<String> getUsersAnswered(Integer qNumber, boolean correctly) {
		
		if(correctly)
			return userRepo.findAllUsernamesCorrectlyAnsweredQuestionsCountEquals(qNumber); 
		else
			return userRepo.findAllUsenamesAnsweredQuestionsCountEquals(qNumber);
	}

// test
	public void getUserAnswered(Question q) {
		System.out.println("TEST\n");
		Set<User> users = userRepo.findByAnsweredQuestionsContains(q);
		System.out.println(users);
	}

	public void getUserAnsweredSetOf(Set<Question> testQuestions) {
	
		System.out.println("TEST SET\n");
//		Set<User> users = userRepo.findByAnsweredQuestionsContains(testQuestions);
//		System.out.println(users);
		
	}

}
