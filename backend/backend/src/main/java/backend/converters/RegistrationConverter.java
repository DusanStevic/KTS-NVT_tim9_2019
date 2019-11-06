package backend.converters;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import backend.dto.RegistrationDTO;
import backend.model.Administrator;
import backend.model.Authority;
import backend.model.RegisteredUser;
import backend.model.Role;

public class RegistrationConverter {
	
	static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	//konvertuje RegistrationDTO u RegisteredUser
	public static RegisteredUser RegistrationDTOToRegisteredUser(RegistrationDTO registrationDTO){
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
		return registeredUser;
	}
	//konvertuje RegistrationDTO u Administrator
	public static Administrator RegistrationDTOToAdministrator(RegistrationDTO registrationDTO){
		Administrator administrator = new Administrator();
		administrator.setUsername(registrationDTO.getUsername());
		administrator.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
		//account je aktiviran
		administrator.setEnabled(true);
		//prvi put se prijavljuje na sistem
		administrator.setFirstTime(true);
		administrator.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
		administrator.setFirstName(registrationDTO.getFirstName());
		administrator.setLastName(registrationDTO.getLastName());
		administrator.setEmail(registrationDTO.getEmail());
		List<Authority> authorities = new ArrayList<>();
		Authority a = new Authority();
		a.setRole(Role.ROLE_ADMIN);
		authorities.add(a);
		administrator.setAuthorities(authorities);
		return administrator;
	}
}
