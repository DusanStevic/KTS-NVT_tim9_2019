package backend.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;






import backend.common.DeviceProvider;
import backend.model.Administrator;
import backend.model.Role;
import backend.model.SysAdmin;
import backend.model.User;
import backend.model.UserTokenState;
import backend.security.JwtAuthenticationRequest;
import backend.security.TokenUtils;
import backend.service.impl.CustomUserDetailsService;



//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private DeviceProvider deviceProvider;

	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response, Device device) throws AuthenticationException, IOException {
		System.out.println("ULETEO SAM U LOGOVANJE");
		if (device == null) {
			System.out.println("PUKLA DETEKCIJA");
		}
		if (device.isNormal()) {
			System.out.println("KACIS SE PREKO KOMPA");
		} else if (device.isTablet()) {
			System.out.println("KACIS SE PREKO TABLETA");
		} else if (device.isMobile()) {
			System.out.println("KACIS SE PREKO MOBILNOG");
		}
		
		
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		// Ubaci username + password u kontext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token
		User user = (User) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername(), device);
		int expiresIn = tokenUtils.getExpiredIn(device);
		Role userType = null;
		//zajedno sa tokenom salje se i uloga na front pa u zavisnosti od tipa korisnika
		//na frontu ce ce otvarati posebno strana za usera, admina i sys-admina
	
		if (user instanceof Administrator) {
			userType = Role.ROLE_ADMIN;
		}
		else if (user instanceof SysAdmin) {
			userType = Role.ROLE_SYS_ADMIN;
		}
		else {
			userType = Role.ROLE_REGISTERED_USER;
		}


		// Vrati token kao odgovor na uspesno autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, userType));
	}

	@PostMapping(value = "/refresh")
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
	    User user = (User) this.userDetailsService.loadUserByUsername(username);
	    
	    Role userType = null;
		//zajedno sa tokenom salje se i uloga na front pa u zavisnosti od tipa korisnika
		//na frontu ce ce otvarati posebno strana za usera, admina i sys-admina
	
		if (user instanceof Administrator) {
			userType = Role.ROLE_ADMIN;
		}
		else if (user instanceof SysAdmin) {
			userType = Role.ROLE_SYS_ADMIN;
		}
		else {
			userType = Role.ROLE_REGISTERED_USER;
		}

		Device device = deviceProvider.getCurrentDevice(request);

		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = tokenUtils.refreshToken(token, device);
			int expiresIn = tokenUtils.getExpiredIn(device);

			return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn,userType));
		} else {
			UserTokenState userTokenState = new UserTokenState();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}

	@PostMapping(value = "/change-password")
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER', 'ROLE_ADMIN', 'ROLE_SYS_ADMIN')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
}