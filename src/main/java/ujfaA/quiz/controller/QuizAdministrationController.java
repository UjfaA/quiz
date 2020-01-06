package ujfaA.quiz.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ujfaA.quiz.model.Question;
import ujfaA.quiz.service.QuestionService;
import ujfaA.quiz.service.QuizService;
import ujfaA.quiz.service.UserService;

@Controller
public class QuizAdministrationController {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	@Autowired
	private QuizService quizService;
	
	@GetMapping("/questions")
	public String getQuestions( HttpSession session, ModelMap model) {
		
		Boolean userIsAdmin = (Boolean) session.getAttribute("administrator");
		if (userIsAdmin == null || userIsAdmin.equals(Boolean.FALSE)) {
			model.addAttribute("message", "Morate biti ulogovani da bi pristupili ovoj stranici.");
			return "denied";
		}
		model.addAttribute("options", this.getOptions());
		model.addAttribute("questions", questionService.listAll());
		return "questions";
	}

	private List<Integer> getOptions() {
		int MAXIMUM_NUMBER_OF_ANSWERS_FOR_A_QUESTIONS = 10; // iz nekog config fajla na pr. ?
		List<Integer> list = new ArrayList<Integer>();
		for ( int i = 1; i <= MAXIMUM_NUMBER_OF_ANSWERS_FOR_A_QUESTIONS; i++ ) {
			list.add(i);
		}
		return list;
	}
	
	@PostMapping("/questions")
	public String saveQuestion(HttpSession session, ModelMap model,
							@ModelAttribute("question") Question question) {
		
		Boolean userIsAdmin = (Boolean) session.getAttribute("administrator");
		if (userIsAdmin == null || userIsAdmin.equals(Boolean.FALSE)) {
			model.addAttribute("message", "Morate biti ulogovani da bi pristupili ovoj stranici.");
			return "denied";
		}
		questionService.save(question);
		return "redirect:/questions";
	}
	
	@PostMapping("/questions/add")
	public String newQuestion(HttpSession session, ModelMap model,
					@RequestParam("numberOfAnswers") Integer numberOfAnswers) {
		
		Boolean userIsAdmin = (Boolean) session.getAttribute("administrator");
		if (userIsAdmin == null || userIsAdmin.equals(Boolean.FALSE)) {
			model.addAttribute("message", "Morate biti ulogovani da bi pristupili ovoj stranici.");
			return "denied";
		}
		session.setAttribute("previouslySelectedOption", numberOfAnswers);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("question", new Question());
		return "addquestion";
	}
	
	@PostMapping("/questions/delete")
	public String deletequestion(HttpSession session, ModelMap model,
							@RequestParam("id") Long id) {
		
		Boolean userIsAdmin = (Boolean) session.getAttribute("administrator");
		if (userIsAdmin == null || userIsAdmin.equals(Boolean.FALSE)) {
			model.addAttribute("message", "Morate biti ulogovani da bi pristupili ovoj stranici.");
			return "denied";
		}
		questionService.delete(id);
		return "redirect:/questions";
	}
	
	@GetMapping("/userStats")
	public String showStats(HttpSession session, ModelMap model,
							@RequestParam( name = "selected", defaultValue = "0") Integer selected,
							@RequestParam( name = "answeredCorrectly", defaultValue = "false") boolean answeredCorrectly) {
		
		quizService.getUsersThatAnsweredAll(false);
		Boolean userIsAdmin = (Boolean) session.getAttribute("administrator");
		if (userIsAdmin == null || userIsAdmin.equals(Boolean.FALSE)) {
			model.addAttribute("message", "Morate biti ulogovani da bi pristupili ovoj stranici.");
			return "denied";
		}
		
		List<String> avaibleStats = this.getAvaibleStatsForUser();
		
		model.addAttribute("avaibleStats", avaibleStats);
		model.addAttribute("selected", selected);
		model.addAttribute("usernames", quizService.getUsersThatAnsweredAll(true));
		return "userstats";
	}

	private List<String> getAvaibleStatsForUser() {
		List<String> avaibleStats = new ArrayList<String>();
		avaibleStats.add("sva pitanja");
		avaibleStats.addAll(questionService.GetQuestionsText());
		System.out.println(avaibleStats);
		return avaibleStats;
	}
	
}
