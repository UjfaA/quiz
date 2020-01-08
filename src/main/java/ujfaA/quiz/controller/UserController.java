package ujfaA.quiz.controller;

import java.time.LocalDateTime;

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
		
	@PostMapping("/registration")
	public String addNewUser(@ModelAttribute("user") 	User newUser,
														ModelMap model,
														HttpSession session) {
		boolean usernameAvaible = userService.isUsernameAvaible(newUser.getUsername());
		if(usernameAvaible) {
			newUser = userService.save(newUser);
			session.setAttribute("username", newUser.getUsername());
			session.setAttribute("userFirstName", newUser.getFirstName());
			session.setAttribute("administrator", newUser.isAdministrator());
			return "redirect:/loginSuccess";
		}
		else {
			model.addAttribute("user", newUser);
			model.addAttribute("message", "Korisničko ime je zauzeto.\nProbajte drugo korisničko ime.");
			return "registration";
		}
	}
	
	@GetMapping("/login")
	public String login() {
		return"login";
	}
	
	@PostMapping("/login")
	public String login( HttpSession session, ModelMap model,
						@RequestParam("username") String username,
						@RequestParam("password") String password) {
		User user;
		try {
			user = userService.getUser(username);			
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
		session.setAttribute("username", user.getUsername());
		session.setAttribute("userFirstName", user.getFirstName());
		session.setAttribute("administrator", user.isAdministrator());
			return "redirect:/loginSuccess";
	}
	

	@GetMapping("/loginSuccess")
	public String loginSuccess(HttpSession session, ModelMap model) {
		
		String username = (String) session.getAttribute("username");
		if (username == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		
		User user = userService.getUser(username);
		user.setLastActive(LocalDateTime.now());
		userService.save(user);
		
		Boolean userIsAdmin = (Boolean) session.getAttribute("administrator");
		if (userIsAdmin.equals(Boolean.FALSE)) {
			return "redirect:/quizStart";
		}
		
		return "loginsuccess";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
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
