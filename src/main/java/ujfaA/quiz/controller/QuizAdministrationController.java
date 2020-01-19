package ujfaA.quiz.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import ujfaA.quiz.model.Question;
import ujfaA.quiz.model.User;
import ujfaA.quiz.service.QuestionService;
import ujfaA.quiz.service.QuizService;
import ujfaA.quiz.service.UserService;

@Controller
@RequestMapping("/overview")
public class QuizAdministrationController {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private UserService userService;
	@Autowired
	private QuizService quizService;
	
	@GetMapping("/")
	public String getAdminIndexPage() {
		return "overview";
	}
	
	@GetMapping("/users/details")
	public String listAllUsers(ModelMap model) {
		model.addAttribute("usersList", userService.listAll());
		return "users";
	}
	
	@GetMapping("/users/stats")
	public String showStats( ModelMap model,
					@RequestParam( name = "selected", defaultValue = "0") Integer selected,
					@RequestParam( name = "answeredCorrectly", defaultValue = "false") Boolean answeredCorrectly) {
		
		Set<String> usernames;
		List<String> options = this.getAvaibleOptions();
		
		if (options.get(selected).equals("sva pitanja"))
			usernames = quizService.getUsersThatAnsweredAll(answeredCorrectly);
		else
			usernames = quizService.getUsersThatAnsweredQuestion(options.get(selected), answeredCorrectly);
		
		model.addAttribute("options", options);
		model.addAttribute("selected", selected);
		model.addAttribute("checked", answeredCorrectly);
		model.addAttribute("usernames", usernames);
		return "userstats";
	}
	
	private List<String> getAvaibleOptions() {
		
		List<String> avaibleStats = new ArrayList<String>();
		avaibleStats.add("sva pitanja");
		avaibleStats.addAll(questionService.GetQuestionsText());
		return avaibleStats;
	}
	
	@GetMapping("users/rankings")
	public String getRankings(ModelMap model) {
		
		List<User> rankings = userService.getHighestRanked();
		Integer maxScore = questionService.getNumberOfQuestions();
		
		model.addAttribute("rankings", rankings);
		model.addAttribute("maxScore", maxScore);
		return "rankings";
	}
	
	@GetMapping("/questions")
	public String getQuestions(ModelMap model) {
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
	
	@GetMapping("/questions/add")
	public String newQuestion(HttpSession session, ModelMap model,
					@RequestParam("numberOfAnswers") Integer numberOfAnswers) {

		session.setAttribute("previouslySelectedOption", numberOfAnswers);
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		if(model.getAttribute("question") == null)
			model.addAttribute(new Question());
		return "addquestion";
	}
	
	@PostMapping("/questions/add")
	public String saveQuestion(@ModelAttribute("question") Question question, RedirectAttributes redirectAttrs) {
		
		if (hasDuplicateAnswers(question)) {
			redirectAttrs.addAttribute("numberOfAnswers", question.getAnswers().size());			
			redirectAttrs.addFlashAttribute("message", "Identični odgovori nisu dozvoljeni.");
			return"redirect:/overview/questions/add";
		}
		questionService.save(question);
		return "redirect:/overview/questions";
	}
	
	private boolean hasDuplicateAnswers(Question question) {
		Set<String> set = new HashSet<String>(question.getAnswers());
		return set.size() != question.getAnswers().size();
	}

	@PostMapping("/questions/delete")
	public String deletequestion(@RequestParam("id") Long id) {
		questionService.delete(id);
		return "redirect:/overview/questions";
	}
	
	@GetMapping("/questions/rankings")
	public String getQuestionStats(ModelMap model) {
		List<Question> questions = questionService.listAllOrderedByCorrectnes();
		model.addAttribute("questions", questions);
		return "questionrankings";
	}
}
