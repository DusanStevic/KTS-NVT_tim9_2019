package backend.dto;

public class LocationUpdateDTO {

	private String name;
	private String description;
	private Long addressId;
	public LocationUpdateDTO(String name, String description, Long addressId) {
		super();
		this.name = name;
		this.description = description;
		this.addressId = addressId;
	}
	public LocationUpdateDTO() {
		super();
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
