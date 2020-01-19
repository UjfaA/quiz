package ujfaA.quiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public boolean usernameIsAvaible(String username) {
		return ( ! userRepo.existsUserByUsername(username));
	}
	
	public User getUser(String username) {
		return userRepo.findByUsername(username).get();
	}
	
	public User register(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRole(user.isAdministrator()	? "ADMIN"
											: "USER" );
		return userRepo.save(user);
	}
	
	public User update(User user) {
		return userRepo.save(user);
	}
	
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
	
	public Set<String> getUsersAnswered(Question question, boolean answeredCorrectly) {
		
		if (answeredCorrectly)	
			return userRepo.getUsernamesWhereCorrectlyAnsweredQuestionsContains(question);
		else
			return userRepo.getUsernamesWhereAnsweredQuestionsContains(question);
	}
	
	public Set<String> getUsersAnsweredNumOfQuestions(Integer qNumber, boolean answeredCorrectly) {
		
		if(answeredCorrectly)
			return userRepo.getUsernamesWhereCorrectlyAnsweredQuestionsCountEquals(qNumber); 
		else
			return userRepo.getUsenamesWhereAnsweredQuestionsCountEquals(qNumber);
	}

	public void deleteUser(User user) {
		userRepo.delete(user);
	}
}
