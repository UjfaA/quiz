package ujfaA.quiz.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ujfaA.quiz.model.User;
import ujfaA.quiz.service.QuestionService;
import ujfaA.quiz.service.QuizService;
import ujfaA.quiz.service.UserService;


@Controller
public class QuizController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuizService quizService;
	
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/registration")
	public String registration(ModelMap model) {
	    model.addAttribute(new User());
	    return "registration";
	}
		
	@PostMapping("/registration")
	public String addNewUser( ModelMap model, @ModelAttribute("user") User newUser) {

// TODO auto login when previously logged in  // Cannot perform login for 'admin2', already authenticated as 'user'
//			request.login(newUser.getUsername(), newUser.getPassword());
		
		if( userService.usernameIsAvaible(newUser.getUsername()) ) {
			newUser = userService.register(newUser);
			return "redirect:/login";
		}
		else {
			model.addAttribute("user", newUser);
			model.addAttribute("message", "Korisničko ime je zauzeto.\nProbajte drugo korisničko ime.");
			return "registration";
		}
	}
	
	@GetMapping("/login")
	public String login( ModelMap model, @RequestParam(defaultValue = "false") boolean error,
										@RequestParam(required = false) String message) {
		if (error == true)
			message = "Pogrešno korisničko ime i/ili lozinka.";
		model.addAttribute("message", message);
		return"login";
	}

	
	@GetMapping("/quiz")
	public String start() {
		return "quizstart";
	}
	
	@GetMapping("/resetAndStart")
	public String resetAndStart( Principal principal,
								 RedirectAttributes redirectAttr,
								 @RequestParam("qIndex") int qIndex) {

		if (questionService.getNumberOfQuestions() == 0) {
			redirectAttr.addAttribute("errorMessage",
				"Kviz ne sadrži ni jedno pitanje. Potrebno je da administrator konstruiše bar jedno pitanje.");
			return "redirect:/epage";
		}
		
		quizService.resetScore(principal.getName());
		redirectAttr.addAttribute("qIndex", Integer.valueOf(qIndex));
		return "redirect:/showQuestion";
	}
	
	@GetMapping("/showQuestion")
	public String showquestion( ModelMap model,
								@RequestParam("qIndex") int qIndex) {

		model.addAttribute("qIndex", qIndex);
		model.addAttribute("numberOfQuestions", questionService.getNumberOfQuestions());
		model.addAttribute("question", questionService.getQuestionByIndex(qIndex));
		return "showquestion";
	}
	
	@PostMapping("/submit")
	public String submitAnswer( Principal principal,
								ModelMap model,
								RedirectAttributes redirectAttr,
								@RequestParam("qIndex") int qIndex,
								@RequestParam(value ="checked", defaultValue = "") String[] usersAnswers) {
		
		quizService.userAnswered(principal.getName(), qIndex, usersAnswers);
		qIndex += 1;
		if(qIndex < questionService.getNumberOfQuestions()) {
			redirectAttr.addAttribute("qIndex", Integer.valueOf(qIndex));
			return "redirect:/showQuestion";
		}
		else
			return "redirect:/completed";
	}
	
	@GetMapping("/completed")
	public String completed(Principal principal, ModelMap model) {

		List<User> rankings = quizService.getTopUsers();
		List<String> messages = quizService.getEndOfQuizMessages(principal.getName(), rankings);
		model.addAttribute("rankings", rankings);
		model.addAttribute("messages", messages);
		return "/quizcompleted";
	}
	
	@GetMapping("/skipQuestion")
	public String skipQuestion( ModelMap model,
								RedirectAttributes redirectAttr,
								@RequestParam("qIndex") int qIndex) {
		
		qIndex += 1;
		if(qIndex < questionService.getNumberOfQuestions()) {
			redirectAttr.addAttribute("qIndex", Integer.valueOf(qIndex));
			return "redirect:/showQuestion";
		}
		else
			return "redirect:/completed";
	}
	
	@GetMapping("/abandon")
	public String abandon(Principal principal) {
		quizService.resetScore(principal.getName());
		return "redirect:/quiz";
	}
	
// TODO  Spring Security logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/epage")
	public String showError(ModelMap model,
							@RequestParam(name = "errorMessage", required = false) String errorMessage) {
		model.addAttribute("errorMessage", errorMessage);
		return "epage";
	}
}