package ujfaA.quiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ujfaA.quiz.model.User;
import ujfaA.quiz.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public User save(User user) {
		return userRepo.save(user);
	}
	
	public List<User> listAll() {
		List<User> list = new ArrayList<User>();
		userRepo.findAll().forEach(list::add);
		return list;
	}
	
	public Optional<User> getUser(String username) {
		return userRepo.findByUsername(username);
	}
	
	public void deleteUser(String username) {
		userRepo.deleteById(username);
	}
	
	public void deleteUser(User user) {
		userRepo.delete(user);
	}
}
