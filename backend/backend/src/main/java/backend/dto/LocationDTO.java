package backend.dto;

import java.util.ArrayList;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class LocationDTO {
	
	@NotNull(message = "Location name is mandatory")
	@Length(min=1, message="Location name is mandatory")
	private String name;
	
	private String description;
	
	@NotNull(message = "Address is mandatory")
	@Min(value=1, message="Address not valid")
	private Long address_id;
	
	@NotNull(message = "Halls are mandatory")
	@NotEmpty(message="Halls are mandatory")
	private ArrayList<HallDTO> halls;
	
	public LocationDTO() {
		super();
	}

	public LocationDTO(String name, String description, Long address_id,ArrayList<HallDTO> halls) {
		super();
		this.name = name;
		this.description = description;
		this.address_id = address_id;
		this.halls = halls;
	}

	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Long address_id) {
		this.address_id = address_id;
	}

	public ArrayList<HallDTO> getHalls() {
		return halls;
	}

	public void setHalls(ArrayList<HallDTO> halls) {
		this.halls = halls;
	}

}
