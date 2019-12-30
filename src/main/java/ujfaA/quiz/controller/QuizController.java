package ujfaA.quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class QuizController {
	
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@RequestMapping("/quizStart")
	public String start() {
		return "quizstart";
	}
}
