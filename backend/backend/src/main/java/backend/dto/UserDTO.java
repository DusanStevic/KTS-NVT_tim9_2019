package backend.dto;

import java.util.List;
import java.util.stream.Collectors;

import backend.model.Authority;
import backend.model.User;

public class UserDTO {
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> authorities;
	
	public UserDTO(User user){
		this.id = user.getId();
		this.username = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.authorities = user.getAuthorities().stream().map(authority -> ((Authority) authority).getRole().toString()).collect(Collectors.toList());
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	

}
