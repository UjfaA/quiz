package ujfaA.quiz.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ujfaA.quiz.model.User;
import ujfaA.quiz.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/registration")
	public String registration(ModelMap model) {
	    model.addAttribute(new User());
	    return "registration";
	}
	
//  TODO: post/redirect/get design pattern ?	
	@PostMapping("/registration")
	public String addNewUser(@ModelAttribute("user") 	User newUser,
														ModelMap model,
														HttpSession session) {
		Optional<User> userInDb = userService.getUser(newUser.getUsername());
		if (userInDb.isPresent()) {
			model.addAttribute("message", "Korisničko ime je zauzeto.\nProbajte drugo korisničko ime.");
			return "registration";
		}
		newUser = userService.save(newUser);
		session.setAttribute("userId", newUser.getId());
		session.setAttribute("userFirstName", newUser.getFirstName());
		session.setAttribute("administrator", newUser.isAdministrator());
		if (newUser.isAdministrator())
			return "redirect:/loginSuccess";
		else
			return "redirect:/quizStart";
	}
	
	@GetMapping("/login")
	public String login(ModelMap model) {
		return"login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("username") String username,
						@RequestParam("password") String password,
						HttpSession session, ModelMap model) {
		User user;
		try {
			user = userService.getUser(username).orElseThrow();			
		} catch (Exception e) {
			model.addAttribute("username", username);
			model.addAttribute("message", "Nepostojeće korisničko ime.");
			return "login";
		}
		if ( ! password.contentEquals(user.getPassword()) ) {
			model.addAttribute("username", username);
			model.addAttribute("message", "Pogrešna lozinka.");
			return "login"; 
		}
		session.setAttribute("userId", user.getId());
		session.setAttribute("userFirstName", user.getFirstName());
		session.setAttribute("administrator", user.isAdministrator());
		if (user.isAdministrator())
			return "redirect:/loginSuccess";
		else
			return "redirect:/quizStart";
	}
	

	@GetMapping("/loginSuccess")
	public String loginSuccess(HttpSession session, ModelMap model) {
		
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			model.addAttribute("message", "Morate biti ulogovani da bi pristupili ovoj stranici.");
			return "denied";
		}
		
		Boolean userIsAdmin = (Boolean) session.getAttribute("administrator");
		if (userIsAdmin.equals(Boolean.FALSE)) {
			return "redirect:/quizstart";
		}
		
		return "loginsuccess";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		System.out.println("logged out");
		return "index";
	}
	
	@GetMapping("/users")
	public String listAllUsers(HttpSession session, ModelMap model) {
		
		Boolean userIsAdmin = (Boolean) session.getAttribute("administrator");
		if (userIsAdmin == null || userIsAdmin.equals(Boolean.FALSE)) {
			model.addAttribute("message", "Morate biti ulogovani sa administratorskim nalogom da biste pristupili ovoj stranici.");
			return "denied";
		}
		model.addAttribute("usersList", userService.listAll());
		return "users";
	}
}
