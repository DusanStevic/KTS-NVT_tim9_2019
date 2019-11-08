package backend.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.converters.RegistrationConverter;
import backend.dto.RegistrationDTO;
import backend.model.Administrator;
import backend.model.Authority;
import backend.model.RegisteredUser;
import backend.model.Role;
import backend.model.User;
import backend.repository.UserRepository;
import backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}

	public User findById(Long id) throws AccessDeniedException {
		User u = userRepository.findById(id).get();
		return u;
	}

	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public RegisteredUser registerUser(RegistrationDTO registrationDTO) {
		RegisteredUser registeredUser = RegistrationConverter.RegistrationDTOToRegisteredUser(registrationDTO);
		userRepository.save(registeredUser);
		return registeredUser;
	}

	@Override
	public Administrator registerAdmin(RegistrationDTO registrationDTO) {
		Administrator administrator = RegistrationConverter.RegistrationDTOToAdministrator(registrationDTO);
		userRepository.save(administrator);
		return administrator;
		
	}
}
