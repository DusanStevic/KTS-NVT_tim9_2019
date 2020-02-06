package backend.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class EventUpdateDTO {

	@NotNull(message = "Event name is mandatory")
	@Length(min=1, message="Event name is mandatory")
	private String name;
	
	@NotNull(message = "Description is mandatory")
	@Length(min=1, message="Description is mandatory")
	private String description;
	
	@NotNull(message = "Event type is mandatory")
	@Min(value=0, message="Invalid type of an event")
	@Max(value=4, message="Invalid type of an event")
	private int type;
	
	/*@NotNull(message = "Maximum number of tickets per reservation is mandatory")
	@Min(value=1, message="Maximum number of tickets per reservation must be greater than or equal to {value}")
	private int max_tickets;*/
	
	public EventUpdateDTO() {
		super();
	}
	public EventUpdateDTO(String name, String description, int type) {
		super();
		this.name = name;
		this.description = description;
		this.type = type;
		//this.max_tickets = max_tickets;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
//	public int getMax_tickets() {
//		return max_tickets;
//	}
//	public void setMax_tickets(int max_tickets) {
//		this.max_tickets = max_tickets;
//	}
	
	
}
