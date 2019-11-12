package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.converters.UserConverter;
import backend.dto.UserDTO;
import backend.exceptions.UserNotFoundException;
import backend.model.User;
import backend.service.UserService;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private UserService userService;

	// Za pristup ovoj metodi neophodno je da ulogovani korisnik ima ADMIN ulogu
	// Ukoliko nema, server ce vratiti gresku 403 Forbidden
	// Korisnik jeste autentifikovan, ali nije autorizovan da pristupi resursu
	@RequestMapping(method = GET, value = "/user/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User loadById(@PathVariable Long userId) {
		return this.userService.findById(userId);
	}
	
	/*@RequestMapping(method = GET, value = "/user")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User loadById2(@RequestParam Long userId) {
		return this.userService.findById(userId);
	}*/
	

	@RequestMapping(method = GET, value = "/user/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> loadAll() {
		return this.userService.findAll();
	}

	@RequestMapping("/whoami")
	//@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public User user(Principal user) {
		return this.userService.findByUsername(user.getName());
	}
	
	@RequestMapping("/kosamja")
	@PreAuthorize("hasRole('ROLE_REGISTERED_USER')")
	public ResponseEntity<User> kosamja(Principal user) {
		User korisnik;
		try {
			korisnik = userService.pronadjiKorisnika(user.getName());
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		return new ResponseEntity<>(korisnik, HttpStatus.OK);
	}
	
	
	
	
}
