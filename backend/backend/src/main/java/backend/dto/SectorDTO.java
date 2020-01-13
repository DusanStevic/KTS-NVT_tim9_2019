package backend.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import backend.model.SittingSector;
import backend.model.StandingSector;

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "type")
		@JsonSubTypes({ 
		  @Type(value = SittingSectorDTO.class, name = "sittingDTO"), 
		  @Type(value = StandingSectorDTO.class, name = "standingDTO") 
		})
public abstract class SectorDTO {
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
