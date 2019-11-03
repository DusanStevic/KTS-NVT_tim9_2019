package backend.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("user")
public class RegisteredUser extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
