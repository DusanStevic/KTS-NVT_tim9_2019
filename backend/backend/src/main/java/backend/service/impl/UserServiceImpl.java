package backend.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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
		RegisteredUser registeredUser = new RegisteredUser();
		registeredUser.setUsername(registrationDTO.getUsername());
		registeredUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
		//account je aktiviran
		registeredUser.setEnabled(true);
		//prvi put se prijavljuje na sistem
		registeredUser.setFirstTime(true);
		registeredUser.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
		registeredUser.setFirstName(registrationDTO.getFirstName());
		registeredUser.setLastName(registrationDTO.getLastName());
		registeredUser.setEmail(registrationDTO.getEmail());
		List<Authority> authorities = new ArrayList<>();
		Authority a = new Authority();
		a.setRole(Role.ROLE_REGISTERED_USER);
		authorities.add(a);
		registeredUser.setAuthorities(authorities);
		
		userRepository.save(registeredUser);
		return registeredUser;
	}

	@Override
	public Administrator registerAdmin(RegistrationDTO registrationDTO) {
		Administrator administrator = new Administrator();
		
		
		userRepository.save(administrator);
		return administrator;
	}
}
