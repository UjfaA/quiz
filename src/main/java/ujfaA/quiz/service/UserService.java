package ujfaA.quiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public List<User> getHighestRanked() {
		return userRepo.findAllOrderByCorrectlyAnsweredQuestions();
	}
	
	public List<User> getHighestRanked(int limit) {
		Page<User> page = userRepo.findAllOrderByCorrectlyAnsweredQuestions(PageRequest.of(0, limit));
		return page.toList();
	}

	public boolean isUsernameAvaible(String username) {
		
		try {
			getUser(username);
		} catch (NoSuchElementException e) {
			return true;
		}
		return false;
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
	
	public Set<String> getUsersAnsweredNumOfQuestions(Integer qNumber, boolean correctly) {
		
		if(correctly)
			return userRepo.findAllUsernamesCorrectlyAnsweredQuestionsCountEquals(qNumber); 
		else
			return userRepo.findAllUsenamesAnsweredQuestionsCountEquals(qNumber);
	}

	public void deleteAnsweredQestions(String username) {
		
//TODO: test user.set/Correctly/AnsweredQuestions(new Set<Question>())		
		
		

		
	}

}
