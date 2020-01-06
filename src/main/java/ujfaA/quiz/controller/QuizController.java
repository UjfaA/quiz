package ujfaA.quiz.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ujfaA.quiz.model.Question;
import ujfaA.quiz.model.User;
import ujfaA.quiz.service.QuestionService;
import ujfaA.quiz.service.QuizService;
import ujfaA.quiz.service.UserService;

/*
 * Nakon implementacije Spring Security potpisi metoda bi trebali da budu čitljiviji.
*/

@Controller
public class QuizController {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	@Autowired
	private QuizService quizService;
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/quizStart")
	public String start(HttpSession session, ModelMap model) {
		
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		return "quizstart";
	}
	
	@GetMapping("/showQuestion")
	public String showquestion(HttpSession session, ModelMap model,
						@RequestParam("qindex") int qindex) {
		
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		model.addAttribute("qindex", qindex);
		model.addAttribute("numberOfQuestions", questionService.getNumberOfQuestions());
		model.addAttribute("question", questionService.getQuestionByIndex(qindex));
		return "showquestion";
	}
	
	@PostMapping("/submit")
	public String submitAnswer(HttpSession session, ModelMap model, RedirectAttributes redirectAttr,
								@RequestParam("qindex") int qindex,
								@RequestParam(value ="checked", defaultValue = "") String[] checked) {
		
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		
		Question q= questionService.getQuestionByIndex(qindex);
		userService.userAnswered(userId, q, checked);
		
		qindex += 1;
		if(qindex < questionService.getNumberOfQuestions()) {
			redirectAttr.addAttribute("qindex", Integer.valueOf(qindex));
			return "redirect:/showQuestion";
		}
		else
			return "redirect:/completed";
	}
	
	@GetMapping("/completed")
	public String completed(HttpSession session, ModelMap model) {
		
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		List<User> rankings = quizService.getRankings();
		List<String> messages = quizService.getEndOfQuizMessages((String) session.getAttribute("username"), rankings);
		model.addAttribute("rankings", rankings);
		model.addAttribute("messages", messages);
		return "/quizcompleted";
	}
	
	@GetMapping("/skipQuestion")
	public String skipQuestion(HttpSession session, ModelMap model, RedirectAttributes redirectAttr,
								@RequestParam("qindex") int qindex) {
		
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		
		qindex += 1;
		if(qindex < questionService.getNumberOfQuestions()) {
			redirectAttr.addAttribute("qindex", Integer.valueOf(qindex));
			return "redirect:/showQuestion";
		}
		else
			return "redirect:/completed";
	}
	
	@GetMapping("/debug")
	public String debug() {
		
		User user = userService.getUser("tester");
		Set<Question> questions = user.getAnsweredQuestions();
		System.out.println("Answered q: " + questions.iterator().next().getQuestionText());
//		System.out.println("User answered: ");
		questions.forEach(q -> {
			System.out.println(q.getUsersAnswered().iterator().next().getUsername());
		});
		userService.getUserAnswered(questions.iterator().next());
		
		System.out.println("SET");
//		userService.getUserAnsweredSetOf(questionService.getTestQuestions());
		
		return "redirect:/";
	}
	
	
}