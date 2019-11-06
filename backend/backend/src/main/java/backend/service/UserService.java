package backend.service;

import java.util.List;

import backend.dto.RegistrationDTO;
import backend.model.Administrator;
import backend.model.RegisteredUser;
import backend.model.User;



public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();
    RegisteredUser registerUser(RegistrationDTO registrationDTO);
    Administrator registerAdmin(RegistrationDTO registrationDTO);
}
