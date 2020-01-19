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
			inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName="id") )
	private Set<User> usersAnswered = new HashSet<User>();
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "QUESTIONS_ANSWERED_CORRECTLY_BY_USER", 
			joinColumns = @JoinColumn(name = "QUESTION_ID", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName="id") )
	private Set<User> usersAnsweredCorectly = new HashSet<User>();
	
	public Question() {
	}
/*	
	public void removeFromUsersAnswered(User u) {
		this.getUsersAnswered().remove(u);
		u.getAnsweredQuestions().remove(this);
	}
	
	public void removeFromUsersAnsweredCorectly(User u) {
		this.getUsersAnsweredCorectly().remove(u);
		u.getCorrectlyAnsweredQuestions().remove(this);
	}
*/	
	public double getCorrectnesstPercent() {
		if (usersAnswered.size() == 0) 
			return 0.0;
		else
			return (usersAnsweredCorectly.size() * 1.0) / usersAnswered.size();
	}
	
	@Override
	public String toString() {
		return questionText;
	}
	
	@Override
	public boolean equals(Object other) {
		if ( ! (other instanceof Question))
			return false;
		Question otherQ = (Question) other;
		return this.questionText.equals(otherQ.questionText);
	}
	
	@Override
	public int hashCode() {
		return this.questionText.hashCode();
	}
}
