package ujfaA.quiz.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
@Entity
@Table(name="questions")
public class Question {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	private String questionText;
	@NonNull
	private String correctAnswer;
	// Useful for JSP form
	@Transient
	private int correctAnswerIndex;
	@NotEmpty
	private ArrayList<String> answers;
	
	public Question() {
	}

	/**
	 * @param answers - Must not be null nor empty. Answer at index 0 is chosen as correct */
	public Question(@NonNull String questionText, @NotEmpty ArrayList<String> answers) {
		this.questionText	= questionText;
		this.correctAnswer	= answers.get(0);
		this.answers		= answers;
	}
}
