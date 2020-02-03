package ujfaA.quiz.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ujfaA.quiz.model.User;
import ujfaA.quiz.service.QuestionService;
import ujfaA.quiz.service.QuizService;


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
	
	@GetMapping("/quiz")
	public String start() {
		return "quizstart";
	}
	
	@GetMapping("/resetAndStart")
	public String resetAndStart( Principal principal, RedirectAttributes redirectAttr) {

		if (questionService.getNumberOfQuestions() == 0) {
			redirectAttr.addAttribute("errorMessage",
				"Kviz ne sadrži ni jedno pitanje. Potrebno je da administrator konstruiše bar jedno pitanje.");
			return "redirect:/epage";
		}
		
		quizService.resetScore(principal.getName());
		redirectAttr.addAttribute("qIndex", 0);
		return "redirect:/showQuestion";
	}
	
	@GetMapping("/showQuestion")
	public String showquestion( @RequestParam("qIndex") int qIndex, ModelMap model)  {

		model.addAttribute("qIndex", qIndex);
		model.addAttribute("numberOfQuestions", questionService.getNumberOfQuestions());
		model.addAttribute("question", questionService.getQuestionByIndex(qIndex));
		return "showquestion";
	}
	
	@PostMapping("/submitAnswer")
	public String submitAnswer( Principal principal,
								ModelMap model,
								RedirectAttributes redirectAttr,
								@RequestParam("qIndex") int qIndex,
								@RequestParam(value ="answers", defaultValue = "") String[] answers) {
		
		quizService.userAnswered(principal.getName(), qIndex, answers);
		qIndex += 1;
		if(qIndex < questionService.getNumberOfQuestions()) {
			redirectAttr.addAttribute("qIndex", qIndex);
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

	@GetMapping("/epage")
	public String showError(ModelMap model,
							@RequestParam(name = "errorMessage", required = false) String errorMessage) {
		model.addAttribute("errorMessage", errorMessage);
		return "epage";
	}
}