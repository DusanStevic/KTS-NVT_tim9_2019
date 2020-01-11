package backend.service;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import backend.model.Authority;
import backend.model.RegisteredUser;
import backend.model.Role;
import backend.model.User;
import backend.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomUserDetailsServiceUnitTest {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@MockBean
	private UserRepository userRepositoryMocked;
	
	
/*	@Test
	public void loadUserByUsername_userFound() {
		//kreiramo korisnika koga cemo mockovati

		List<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		//authority.setUserType(Role.ROLE_REGISTERED_USER);
		authority.setRole(Role.ROLE_REGISTERED_USER);
		authorities.add(authority);
		//User user = new User("user1", "123", "Pera", "Peric", "user1@gmail.com", authorities);
		RegisteredUser ru = new RegisteredUser();
		ru.setAuthorities(authorities);
		ru.setUsername("user1");
		ru.setPassword("123");
		ru.setFirstName("Pera");
		ru.setLastName("Peric");
		ru.setEmail("user1@gmail.com");
		Mockito.when(userRepositoryMocked.findByUsername(ru.getUsername())).thenReturn(ru);
		UserDetails u = customUserDetailsService.loadUserByUsername("user1");

		assertEquals("user1", u.getUsername());
		assertEquals("123", u.getPassword());
	}*/
	
	@Test
	public void loadUserByUsername_userSuccessfull() {
		User user = new RegisteredUser();
		user.setUsername("USERNAME");
		when(userRepositoryMocked.findByUsername("USERNAME")).thenReturn(user);
		UserDetails userDetails = customUserDetailsService.loadUserByUsername("USERNAME");
		verify(userRepositoryMocked).findByUsername("USERNAME");
		assertEquals(user.getUsername(), userDetails.getUsername());
	}
	 
	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsername_userNotFound() {
		
		customUserDetailsService.loadUserByUsername("user11");
	}


	
}
