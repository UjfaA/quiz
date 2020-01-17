package ujfaA.quiz.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.quiz.model.Role;
import ujfaA.quiz.model.User;
import ujfaA.quiz.repository.RoleRepository;
import ujfaA.quiz.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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

	public boolean usernameIsAvaible(String username) {
		return ( ! userRepo.existsUserByUsername(username));
			}
	
	public User getUser(String username) {
		return userRepo.findByUsername(username).get();
	}
		
	public User register(User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		Set<Role> roles = new HashSet<Role>();
		Optional<Role> role;
		role = roleRepo.findByName("USER");
		roles.add( role.orElse(new Role("USER")) );
		if (user.getAdministrator()) {
			role = roleRepo.findByName("ADMIN");
			roles.add( role.orElse(new Role("ADMIN")) );
		}
		user.setRoles(roles);
		return userRepo.save(user);
	}
	
	public User update(User user) {
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

}
