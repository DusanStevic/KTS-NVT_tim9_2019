package backend.service;

import java.util.List;

import backend.dto.RegistrationDTO;
import backend.exceptions.UserNotFoundException;
import backend.model.Administrator;
import backend.model.RegisteredUser;
import backend.model.User;



public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    User pronadjiKorisnika(String username) throws UserNotFoundException;
    List<User> findAll ();
    RegisteredUser registerUser(RegistrationDTO registrationDTO);
    Administrator registerAdmin(RegistrationDTO registrationDTO);
    User save(User user);
}
