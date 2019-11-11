package backend.exceptions;

public class UserAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(Long id) {
		super(String.format("User with id: %s already exists.", id.toString()));
		// TODO Auto-generated constructor stub
	}
	
	public UserAlreadyExistsException(String username) {
		super(String.format("User with username: %s already exists.", username));
		// TODO Auto-generated constructor stub
	}
	

}
