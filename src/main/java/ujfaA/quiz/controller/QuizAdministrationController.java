package ujfaA.quiz.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
					@RequestParam( name = "selection", defaultValue = "all") String questionText,
					@RequestParam( name = "answeredCorrectly", defaultValue = "false") Boolean answeredCorrectly) {
		
		Set<String> usernames;
		
		if (questionText.equals("all"))
			usernames = quizService.getUsersThatAnsweredAll(answeredCorrectly);
		else
			usernames = quizService.getUsersThatAnsweredQuestion(questionText, answeredCorrectly);
		
		model.addAttribute("questions", questionService.GetQuestionsText());
		model.addAttribute("selection", questionText);
		model.addAttribute("checked", answeredCorrectly);
		model.addAttribute("usernames", usernames);
		return "userstats";
	}
	
	@GetMapping("users/rankings")
	public String getRankings(ModelMap model) {
		
		List<User> rankings = userService.getHighestRanked();
		Integer maxScore = questionService.getNumberOfQuestions();
		
		model.addAttribute("rankings", rankings);
		model.addAttribute("maxScore", maxScore);
		return "userrankings";
	}
	
	@GetMapping("/questions")
	public String getQuestions(ModelMap model) {
		model.addAttribute("questions", questionService.listAll());
		return "questions";
	}
	
	@GetMapping("/questions/add")
	public String newQuestion( @RequestParam(name = "numberOfAnswers", defaultValue = "3") int numberOfAnswers,
						//		@ModelAttribute("question") Question question,
								ModelMap model) {

//		if (question == null) question = new Question();
		
		final int MAX_ANSWERS = 5;
		if (numberOfAnswers > MAX_ANSWERS) numberOfAnswers = MAX_ANSWERS;
		

		model.addAttribute(new Question());
		model.addAttribute("numberOfAnswers", numberOfAnswers);
		model.addAttribute("MAX_ANSWERS", MAX_ANSWERS);
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
