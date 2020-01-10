package backend.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;


@Validated
public class AddressDTO {
	//private Long id;
	@NotNull(message = "Street is mandatory")
	@Length(min=1, message="Street is mandatory")
	private String streetName;
	
	@NotNull(message = "Street number is mandatory")
	@Min(value=1, message="Street number must be greater than or equal to {value}")
	private Integer streetNumber;
	
	@NotNull(message = "City is mandatory")
	@Length(min=1, message="City is mandatory")
	private String city;
	
	@NotNull(message = "Country is mandatory")
	@Length(min=1, message="Country is mandatory")
	private String country;
	
	@NotNull(message = "Latitude is mandatory")
	@Min(value=-90, message="Latitude must be greater than or equal to {value}")
	@Max(value=90, message="Latitude must be less than or equal to {value}")
	private Double latitude;
	
	@NotNull(message="Longitude is mandatory")
	@Min(value=-180, message="Longitude must be greater than or equal to {value}")
	@Max(value=180, message="Longitude must be less than or equal to {value}")
	private Double longitude;

	public AddressDTO() {
		super();
	}

	public AddressDTO(String streetName, Integer streetNumber,
			String city, String country, Double latitude, Double longitude) {
		super();
		//this.id = id;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public Integer getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(Integer streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
