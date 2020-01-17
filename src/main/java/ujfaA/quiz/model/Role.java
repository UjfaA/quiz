package ujfaA.quiz.model;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter@Setter
public class Role {
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;
	
	public Role() {}
	
	public Role(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object other) {
		if ( ! (other instanceof Role))
			return false;
		Role otherR = (Role) other;
		return this.name.equals(otherR.name);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	
}
