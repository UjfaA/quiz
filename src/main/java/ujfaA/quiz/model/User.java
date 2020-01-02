package ujfaA.quiz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
//	TODO: remove password from class
	private String password;
	private Boolean administrator;
	private String firstName;
	private String lastName;
	private String email;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastActive;
	
	public Boolean isAdministrator() {
		return administrator;
	}
}
