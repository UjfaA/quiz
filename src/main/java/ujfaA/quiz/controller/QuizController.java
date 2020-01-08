package ujfaA.quiz.controller;

import java.util.List;

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

/*
 * Nakon implementacije Spring Security potpisi metoda i same metode bi trebalo da budu kraći i čitljiviji.
*/

@Controller
public class QuizController {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuizService quizService;
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/quizStart")
	public String start(HttpSession session, ModelMap model) {
		
		String username = (String) session.getAttribute("username");
		if (username == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		return "quizstart";
	}
	
	@GetMapping("/resetAndStart")
	public String resetAndStart(HttpSession session, ModelMap model,
								RedirectAttributes redirectAttr,
								@RequestParam("qIndex") int qIndex) {
		
		String username = (String) session.getAttribute("username");
		if (username == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		
		if (questionService.getNumberOfQuestions() == 0) {
			redirectAttr.addAttribute("errorMessage",
				"Kviz ne sadrži ni jedno pitanje. Potrebno je da administrator konstruiše bar jedno pitanje.");
			return "redirect:/epage";
		}
		
		quizService.resetScore(username);
		redirectAttr.addAttribute("qIndex", Integer.valueOf(qIndex));
		return "redirect:/showQuestion";
	}
	
	@GetMapping("/showQuestion")
	public String showquestion(HttpSession session, ModelMap model,
						@RequestParam("qIndex") int qIndex) {
		
		String username = (String) session.getAttribute("username");
		if (username == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		model.addAttribute("qIndex", qIndex);
		model.addAttribute("numberOfQuestions", questionService.getNumberOfQuestions());
		model.addAttribute("question", questionService.getQuestionByIndex(qIndex));
		return "showquestion";
	}
	
	@PostMapping("/submit")
	public String submitAnswer(HttpSession session, ModelMap model, RedirectAttributes redirectAttr,
								@RequestParam("qIndex") int qIndex,
								@RequestParam(value ="checked", defaultValue = "") String[] checked) {
		
		String username = (String) session.getAttribute("username");
		if (username == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
// TODO move to questionService		
		Question q = questionService.getQuestionByIndex(qIndex);
		quizService.userAnswered(username, q, checked);
		
		qIndex += 1;
		if(qIndex < questionService.getNumberOfQuestions()) {
			redirectAttr.addAttribute("qIndex", Integer.valueOf(qIndex));
			return "redirect:/showQuestion";
		}
		else
			return "redirect:/completed";
	}
	
	@GetMapping("/completed")
	public String completed(HttpSession session, ModelMap model) {
		
		String username = (String) session.getAttribute("username");
		if (username == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		List<User> rankings = quizService.getTopUsers();
		List<String> messages = quizService.getEndOfQuizMessages((String) session.getAttribute("username"), rankings);
		model.addAttribute("rankings", rankings);
		model.addAttribute("messages", messages);
		return "/quizcompleted";
	}
	
	@GetMapping("/skipQuestion")
	public String skipQuestion(HttpSession session, ModelMap model, RedirectAttributes redirectAttr,
								@RequestParam("qIndex") int qIndex) {
		
		String username = (String) session.getAttribute("username");
		if (username == null) {
			model.addAttribute("message", "Morate biti ulogovani da biste pristupili ovoj stranici.");
			return "denied";
		}
		
		qIndex += 1;
		if(qIndex < questionService.getNumberOfQuestions()) {
			redirectAttr.addAttribute("qIndex", Integer.valueOf(qIndex));
			return "redirect:/showQuestion";
		}
		else
			return "redirect:/completed";
	}
	
	@GetMapping("/abandon")
	public String abandon(HttpSession session) {
		String username = (String) session.getAttribute("username");
		quizService.resetScore(username);
		return "redirect:/loginSuccess";
	}
	
	@GetMapping("/epage")
	public String showError(ModelMap model,
							@RequestParam(name = "errorMessage", required = false) String errorMessage) {
		model.addAttribute("errorMessage", errorMessage);
		return "epage";
	}
}