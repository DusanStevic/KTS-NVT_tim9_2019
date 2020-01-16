package backend.service;
import static backend.constants.UserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class CustomUserDetailsServiceIntegrationTest {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void loadUserByUsername_registeredUserFound() {
		UserDetails u = customUserDetailsService.loadUserByUsername(DB_REGISTERED_USER_USERNAME);
		assertEquals(DB_REGISTERED_USER_USERNAME, u.getUsername());
		assertTrue(passwordEncoder.matches(DB_REGISTERED_USER_PASSWORD, u.getPassword())); 
	}
	
	@Test
	public void loadUserByUsername_adminFound() {
		UserDetails u = customUserDetailsService.loadUserByUsername(DB_ADMIN_USERNAME);
		assertEquals(DB_ADMIN_USERNAME, u.getUsername());
		assertTrue(passwordEncoder.matches(DB_ADMIN_PASSWORD, u.getPassword())); 
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsername_registeredUserNotFound() {
		customUserDetailsService.loadUserByUsername(DB_REGISTERED_USER_USERNAME_NOT_FOUND);
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsername_adminNotFound() {
		customUserDetailsService.loadUserByUsername(DB_ADMIN_USERNAME_NOT_FOUND);
	}


}
