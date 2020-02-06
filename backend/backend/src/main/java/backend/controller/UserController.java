package backend.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.UserUpdateDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.exceptions.SavingException;
import backend.model.User;
import backend.service.FileUploadService;
import backend.service.UserService;

@RestController
@RequestMapping(value = "api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileUploadService fileUploadService;

	// Za pristup ovoj metodi neophodno je da ulogovani korisnik ima ADMIN ulogu
	// Ukoliko nema, server ce vratiti gresku 403 Forbidden
	// Korisnik jeste autentifikovan, ali nije autorizovan da pristupi resursu
	/*
	 * @RequestMapping(method = GET, value = "/user/{userId}")
	 * 
	 * @PreAuthorize("hasRole('ROLE_ADMIN')") public User loadById(@PathVariable
	 * Long userId) { return this.userService.findById(userId); }
	 */

	@RequestMapping(method = GET, value = "/user/all")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<User> loadAll() {
		return this.userService.findAll();
	}

	@RequestMapping("/whoami")
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER', 'ROLE_SYS_ADMIN', 'ROLE_ADMIN')")
	public User user(Principal user) {
		if (user == null) {
			System.out.println("principal is null");
		}

		return this.userService.findByUsername(user.getName());
	}

	/*
	 * @RequestMapping(method = GET, value = "/user/{userId}")
	 * 
	 * @PreAuthorize("hasRole('ROLE_ADMIN')") public ResponseEntity<?>
	 * loadById(@PathVariable Long userId) throws UserNotFoundException{ User
	 * korisnik = userService.pronadjiKorisnika(userId);
	 * 
	 * if (korisnik==null) { throw new UserNotFoundException(userId); } return
	 * new ResponseEntity<>(korisnik, HttpStatus.OK);
	 * 
	 * 
	 * return repository.findById(userId) .orElseThrow(() -> new
	 * BookNotFoundException(id));
	 * 
	 * 
	 * }
	 */

	/*
	 * @GetMapping("/user/{id}")
	 * 
	 * @PreAuthorize("hasRole('ROLE_ADMIN')") User findOne(@PathVariable Long
	 * id) throws UserNotFoundException{ return repository.findById(id)
	 * .orElseThrow(() -> new UserNotFoundException(id)); }
	 */

	/*
	 * Drugi nacin greska se hvata na nivou GlobalExceptioinHandler vrsi se
	 * propagacija greske navise sa controllera na GlobalExceptionHandler +
	 * imamo CustomErrorREsponse takodje ispravan nacin rada
	 */
	// OVO RADI
	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	User findOne(@PathVariable Long userId) throws ResourceNotFoundException {
		return this.userService.findById(userId);

	}

	/* ovo je primer query param */
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User loadById2(@RequestParam Long userId)
			throws ResourceNotFoundException {
		return this.userService.findById(userId);
	}

	/* update korisnika po id-u */
	@PreAuthorize("hasAnyRole('ROLE_SYS_ADMIN', 'ROLE_ADMIN', 'ROLE_REGISTERED_USER')")
	@PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(
			@Valid @RequestBody UserUpdateDTO userDetails, Principal loggedin)
			throws ResourceNotFoundException, SavingException {

		User principal = userService.findByUsername(loggedin.getName());

		// ulogovan korisnik moze da updatuje samo svoje podatke
		if (userDetails.getId() != principal.getId()) {
			return ResponseEntity.badRequest().build();
		}

		User user = userService.update(userDetails);

		if (user == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(user);

	}

}
