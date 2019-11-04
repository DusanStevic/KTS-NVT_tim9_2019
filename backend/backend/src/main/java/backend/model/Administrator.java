package backend.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Administrator extends User {
	private static final long serialVersionUID = 1L;

	// TODO add reference to event..

}
