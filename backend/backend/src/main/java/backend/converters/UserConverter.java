package backend.converters;

import backend.dto.UserDTO;
import backend.model.User;

public class UserConverter {
	//konvertuje Usera u UserDTO
	public static UserDTO UserToUserDTO(User user) {
		return new UserDTO(user);
	}
	
	
	

}
