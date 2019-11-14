package backend.dto;

import java.util.ArrayList;

public class HallDTO {
	
	private String name;
	private int number_of_sectors;
	private Long location_id;
	private ArrayList<SectorDTO> sectors;
	
	public HallDTO() {
		super();
	}

	public HallDTO(String name, int number_of_sectors, Long location_id, ArrayList<SectorDTO> sectors) {
		super();
		this.name = name;
		this.number_of_sectors = number_of_sectors;
		this.location_id = location_id;
		this.sectors = sectors;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber_of_sectors() {
		return number_of_sectors;
	}

	public void setNumber_of_sectors(int number_of_sectors) {
		this.number_of_sectors = number_of_sectors;
	}

	public Long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}

	public ArrayList<SectorDTO> getSectors() {
		return sectors;
	}

	public void setSectors(ArrayList<SectorDTO> sectors) {
		this.sectors = sectors;
	}

}
