package ujfaA.quiz.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import ch.qos.logback.core.CoreConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="USERS")
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
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastActive;
//	@Transient
//	private long score;
	
	@ManyToMany(mappedBy = "usersAnswered")
	private Set<Question> answeredQuestions = new HashSet<Question>();
	
	@ManyToMany(mappedBy = "usersAnsweredCorectly")
	private Set<Question> correctlyAnsweredQuestions = new HashSet<Question>();
	
	public Boolean isAdministrator() {
		return administrator;
	}
	
	//convenience method for HTML
	public int getScore() {
		return correctlyAnsweredQuestions.size();
	}
	
	public void addAnsweredQuestion(Question q) {
		answeredQuestions.add(q);
		q.getUsersAnswered().add(this);
	}
	
	public void removeAnsweredQuestion(Question q) {
		answeredQuestions.remove(q);
		q.getUsersAnswered().remove(this);
	}
	
	public void addAnsweredQuestionCorrectly(Question q) {
		this.addAnsweredQuestion(q);
		correctlyAnsweredQuestions.add(q);
		q.getUsersAnsweredCorectly().add(this);
	}
	
	public void removeAnsweredQuestionCorrectly(Question q) {
		this.removeAnsweredQuestion(q);
		correctlyAnsweredQuestions.remove(q);
		q.getUsersAnsweredCorectly().remove(this);
	}
}
