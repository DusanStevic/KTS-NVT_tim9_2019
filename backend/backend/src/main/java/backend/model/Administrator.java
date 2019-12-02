package backend.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
