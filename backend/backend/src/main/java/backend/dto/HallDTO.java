package backend.dto;

public class HallDTO {
	private Long id;
	private String name;
	private int number_of_sectors;
	private Long location_id;

	public HallDTO() {
		super();
	}

	public HallDTO(Long id, String name, int number_of_sectors, Long location_id) {
		super();
		this.id = id;
		this.name = name;
		this.number_of_sectors = number_of_sectors;
		this.location_id = location_id;
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

}
