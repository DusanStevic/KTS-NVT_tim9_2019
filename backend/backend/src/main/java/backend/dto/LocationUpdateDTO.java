package backend.dto;

public class LocationUpdateDTO {

	private String name;
	private String description;
	private Long address_id;
	public LocationUpdateDTO(String name, String description, Long address_id) {
		super();
		this.name = name;
		this.description = description;
		this.address_id = address_id;
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
	public Long getAddress_id() {
		return address_id;
	}
	public void setAddress_id(Long address_id) {
		this.address_id = address_id;
	}
	
	
}
