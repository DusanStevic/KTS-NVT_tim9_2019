package backend.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class LocationDTO {

	@NotNull(message = "Location name is mandatory")
	@Length(min = 1, message = "Location name is mandatory")
	private String name;

	private String description;

	@NotNull(message = "Address is mandatory")
	@Length(min = 1, message = "Address is mandatory")
	private String address;
	
	@NotNull(message = "Latitude is mandatory")
	@Min(value=-90, message="Latitude must be greater than or equal to {value}")
	@Max(value=90, message="Latitude must be less than or equal to {value}")
	private Double latitude;
	
	@NotNull(message="Longitude is mandatory")
	@Min(value=-180, message="Longitude must be greater than or equal to {value}")
	@Max(value=180, message="Longitude must be less than or equal to {value}")
	private Double longitude;

	/*
	 * @NotNull(message = "Halls are mandatory")
	 * 
	 * @NotEmpty(message="Halls are mandatory") private ArrayList<HallDTO> halls;
	 */

	public LocationDTO() {
		super();
	}

	public LocationDTO(String name, String description, String address, Double latitude, Double longitude) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	

}
