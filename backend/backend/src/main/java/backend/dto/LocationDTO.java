package backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class LocationDTO {

	@NotNull(message = "Location name is mandatory")
	@Length(min = 1, message = "Location name is mandatory")
	private String name;

	private String description;

	@NotNull(message = "Address is mandatory")
	@Min(value = 1, message = "Address not valid")
	private Long addressId;

	/*
	 * @NotNull(message = "Halls are mandatory")
	 * 
	 * @NotEmpty(message="Halls are mandatory") private ArrayList<HallDTO> halls;
	 */

	public LocationDTO() {
		super();
	}

	public LocationDTO(String name, String description, Long addressId) {
		super();
		this.name = name;
		this.description = description;
		this.addressId = addressId;
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

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	

}
