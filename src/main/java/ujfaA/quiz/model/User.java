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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@Getter@Setter@ToString
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	private Boolean administrator;
	private String firstName;
	private String lastName;
	private String email;
//	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime lastActive;
//  not used
//	private Integer score;
	
	@ManyToMany(mappedBy = "usersAnswered")
	private Set<Question> answeredQuestions = new HashSet<Question>();
	
	@ManyToMany(mappedBy = "usersAnsweredCorectly")
	private Set<Question> correctlyAnsweredQuestions = new HashSet<Question>();
	
	public Boolean isAdministrator() {
		return administrator;
	}
	
	//convenience method for HTML
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
		q.getUsersAnsweredCorectly().add(this);
	}
	
	public void removeFromAnsweredQuestionCorrectly(Question q) {
		correctlyAnsweredQuestions.remove(q);
		q.getUsersAnsweredCorectly().remove(this);
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
