package backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import backend.converters.RegistrationConverter;
import backend.dto.RegistrationDTO;
import backend.dto.UserUpdateDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.exceptions.SavingException;
import backend.model.Administrator;
import backend.model.RegisteredUser;
import backend.model.User;
import backend.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private FileUploadService fileUploadService;

	public User findByUsername(String username)
			throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		return u;
	}

	/*
	 * @Override public User pronadjiKorisnika(Long id) throws
	 * UserNotFoundException {
	 * 
	 * User u = userRepository.getOne(id); if (u==null) { throw new
	 * UserNotFoundException(id); } else { return u; }
	 * 
	 * }
	 */

	// OVO RADI
	/*
	 * @Override public User pronadjiKorisnika(Long id) throws
	 * UserNotFoundException { return userRepository.findById(id)
	 * .orElseThrow(() -> new UserNotFoundException(id));
	 * 
	 * 
	 * }
	 */



	public User findById(Long id) throws ResourceNotFoundException {
		User u = userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("User not found!"));

		return u;
	}

	public List<User> findAll() throws AccessDeniedException {
		List<User> result = userRepository.findAll();
		return result;
	}

	public User save(User user) throws SavingException {
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			throw new SavingException("Couldn't save user. Username or email is taken!");
		}
	}

	public RegisteredUser registerUser(RegistrationDTO registrationDTO,MultipartFile file) {
		RegisteredUser registeredUser = RegistrationConverter.RegistrationDTO2RegisteredUser(registrationDTO);
		registeredUser.setImageUrl(fileUploadService.imageUpload(file));
		userRepository.save(registeredUser);
		try {
			emailService.sendRegistrationConfirmationEmail(registeredUser);
		} catch (MailException | InterruptedException e) {
			System.out.println("There was an error while sending an e-mail");
			e.printStackTrace();
		}
		return registeredUser;
	}


	public Administrator registerAdmin(RegistrationDTO registrationDTO) {
		Administrator administrator = RegistrationConverter.RegistrationDTO2Administrator(registrationDTO);
		administrator.setImageUrl("https://res.cloudinary.com/djxkexzcr/image/upload/v1574108111/zbvvptxlxzzhzomjvp2s.jpg");
		userRepository.save(administrator);
		try {
			emailService.sendRegistrationConfirmationEmail(administrator);
		} catch (MailException | InterruptedException e) {
			System.out.println("There was an error while sending an e-mail");
			e.printStackTrace();
		}
		return administrator;

	}

	public User update(UserUpdateDTO userDetails) throws ResourceNotFoundException, SavingException {
		User user = findById(userDetails.getId());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setPhoneNumber(userDetails.getPhoneNumber());
		user.setUsername(userDetails.getUsername());
		user.setEmail(userDetails.getEmail());
		User updateUser = save(user); 
		return updateUser;
	}
}
