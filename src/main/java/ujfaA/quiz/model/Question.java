package ujfaA.quiz.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="QUESTIONS")
@Getter @Setter
public class Question{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String questionText;
	private String correctAnswer;
	@Transient
	private int correctAnswerIndex;
	private ArrayList<String> answers;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "QUESTIONS_ANSWERED_BY_USER",
			joinColumns = @JoinColumn(name = "QUESTION_ID", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName="id")
	)
	private Set<User> usersAnswered = new HashSet<User>();
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "QUESTIONS_ANSWERED_CORRECTLY_BY_USER", 
			joinColumns = @JoinColumn(name = "QUESTION_ID", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName="id")
	)
	private Set<User> usersAnsweredCorectly = new HashSet<User>();
	
	public Question() {
	}

	/**
	 * @param answers - Must not be null nor empty. Answer at index 0 is chosen as correct */
	public Question(@NonNull String questionText, @NotEmpty ArrayList<String> answers) {
		this.questionText	= questionText;
		this.correctAnswer	= answers.get(0);
		this.answers		= answers;
	}
	
	@Override
	public String toString() {
		return questionText;
	}
}
