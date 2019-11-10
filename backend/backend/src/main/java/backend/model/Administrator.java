package backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DiscriminatorValue("admin")
public class Administrator extends User {
	private static final long serialVersionUID = 1L;
	/*@OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("admin")
	@JsonBackReference
	private Set<Event> events = new HashSet<>();*/

	public Administrator() {
		super();
	}

	

}
