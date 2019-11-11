package backend.exceptions;

public class UserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long id) {
		super(String.format("User with id: %s is not found.", id.toString()));
		// TODO Auto-generated constructor stub
	}
	
	public UserNotFoundException(String username) {
		super(String.format("User with username: %s is not found.", username));
		// TODO Auto-generated constructor stub
	}
	
	

}
