package ujfaA.quiz.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String role;
	
	@Transient
	private boolean administrator;
	
	private String firstName;
	
	private String lastName;	

	private LocalDateTime lastActive;
	
	@ManyToMany(mappedBy = "usersAnswered")
	private Set<Question> answeredQuestions = new HashSet<Question>();
	
	@ManyToMany(mappedBy = "usersAnsweredCorrectly")
	private Set<Question> correctlyAnsweredQuestions = new HashSet<Question>();
	
	
	
	// for Spring
	public boolean getAdministrator() {
		return administrator;
	}
	
	// for readability
	public boolean isAdministrator() {
		return administrator;
	}
	
	//convenience method
	public Integer getScore() {
		return correctlyAnsweredQuestions.size();
	}
	
	public void addAnsweredQuestion(Question q) {
		answeredQuestions.add(q);
		q.getUsersAnswered().add(this);
	}
	
	public void removeFromAnsweredQuestion(Question q) {
		answeredQuestions.remove(q);
		q.getUsersAnswered().remove(this);
	}
	
	public void addAnsweredQuestionCorrectly(Question q) {
		this.addAnsweredQuestion(q);
		correctlyAnsweredQuestions.add(q);
		q.getUsersAnsweredCorrectly().add(this);
	}
	
	public void removeFromAnsweredQuestionCorrectly(Question q) {
		correctlyAnsweredQuestions.remove(q);
		q.getUsersAnsweredCorrectly().remove(this);
	}
	
	@Override
	public boolean equals(Object other) {
		if ( ! (other instanceof User))
			return false;
		User otherU = (User) other;
		return this.username.equals(otherU.username);
	}
	
	@Override
	public int hashCode() {
		return this.username.hashCode();
	}
}
