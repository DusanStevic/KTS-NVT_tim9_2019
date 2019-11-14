package backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import backend.converters.RegistrationConverter;
import backend.dto.RegistrationDTO;
import backend.exceptions.UserNotFoundException;
import backend.model.Administrator;
import backend.model.RegisteredUser;
import backend.model.User;
import backend.repository.UserRepository;
import backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private EmailService emailService;
	
	

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}
	
	
	/*@Override
	public User pronadjiKorisnika(Long id) throws UserNotFoundException {
		
		User u = userRepository.getOne(id);
		if (u==null) {
			throw new UserNotFoundException(id);
		} else {
			return u;
		}
		
	}*/
	
	
	
	
	//OVO RADI
	@Override
	public User pronadjiKorisnika(Long id) throws UserNotFoundException {
		return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
	
		
	}
	
	
	
	
	
	
	
	
	


	
	@Override
	public User findById(Long id) throws AccessDeniedException{
		//return userRepository.getOne(id);
		return userRepository.findById(id).get();
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
		try {
			emailService.sendRegistrationConfirmationEmail(registeredUser);
		} catch (MailException | InterruptedException e) {
			System.out.println("There was an error while sending an e-mail");
			e.printStackTrace();
		} 
		return registeredUser;
	}

	@Override
	public Administrator registerAdmin(RegistrationDTO registrationDTO) {
		Administrator administrator = RegistrationConverter.RegistrationDTOToAdministrator(registrationDTO);
		userRepository.save(administrator);
		try {
			emailService.sendRegistrationConfirmationEmail(administrator);
		} catch (MailException|InterruptedException e) {
			System.out.println("There was an error while sending an e-mail");
			e.printStackTrace();
		}
		return administrator;
		
	}
}
