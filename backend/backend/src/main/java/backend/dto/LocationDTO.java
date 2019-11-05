package backend.dto;

public class LocationDTO {
	private Long id;
	private String name;
	private String description;
	private Long address_id;

	public LocationDTO() {
		super();
	}

	public LocationDTO(Long id, String name, String description, Long address_id) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address_id = address_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
