package backend.exceptions;

public class UserNotFoundByIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundByIdException(Long id) {
		super(String.format("User with %s id is not found.", id.toString()));
		// TODO Auto-generated constructor stub
	}
	
	

}
