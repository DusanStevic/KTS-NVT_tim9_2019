package backend.dto;

import java.util.ArrayList;

public class LocationDTO {
	private String name;
	private String description;
	private Long address_id;
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
