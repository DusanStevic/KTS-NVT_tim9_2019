package backend.dto;

public class HallUpdateDTO {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HallUpdateDTO(String name) {
		super();
		this.name = name;
	}

	public HallUpdateDTO() {
		super();
	}
	
	
}
