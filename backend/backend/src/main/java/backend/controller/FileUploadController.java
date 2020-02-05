package backend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.converters.UserConverter;
import backend.dto.UserDTO;
import backend.model.User;
import backend.service.FileUploadService;
import backend.service.UserService;

@RestController
@RequestMapping(value = "/media-files", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin()
public class FileUploadController {
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private UserService userService;
	
	 //update slike usera razbijanje requesta na multipart i json deo
		@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_REGISTERED_USER')")
		@PutMapping(value = "/image",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<User> updateImage(@RequestParam("file")MultipartFile file, Principal principal) {
			User user = userService.findByUsername(principal.getName());
			user.setImageUrl(fileUploadService.imageUpload(file));
			userService.save(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	
	@PostMapping(value = "/update-profile-image", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER', 'ROLE_ADMIN', 'ROLE_SYS_ADMIN')")
	public ResponseEntity<UserDTO> updateProfileImage(@RequestParam("file") MultipartFile file,Principal principal) {
		User user = userService.findByUsername(principal.getName());
		user.setImageUrl(fileUploadService.imageUpload(file));
		userService.save(user);
		return new ResponseEntity<>(UserConverter.UserToUserDTO(user), HttpStatus.OK);
    }

}
