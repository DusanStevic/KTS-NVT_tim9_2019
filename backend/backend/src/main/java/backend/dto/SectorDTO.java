package backend.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class SectorDTO {
	//private Long id;
	@NotNull(message = "Sector name is mandatory")
	@Length(min=1, message="Sector name is mandatory")
	private String name;
	
	
	
	/*
	 * ne salje se prilikom kreiranja lokacije
	 */
	//private Long hall_id;

	public SectorDTO() {
		super();
	}


	public SectorDTO(String name) {
		super();
		this.name = name;
		
		//this.hall_id = hallId;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	

	

	
}
