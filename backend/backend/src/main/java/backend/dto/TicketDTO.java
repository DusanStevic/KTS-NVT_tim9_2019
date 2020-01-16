package backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "type")
		@JsonSubTypes({ 
		  @Type(value = SittingTicketDTO.class, name = "sittingTicketDTO"), 
		  @Type(value = StandingTicketDTO.class, name = "standingTicketDTO") 
		})
public abstract class TicketDTO {

	@NotNull(message = "Sector is mandatory")
	@Min(value = 1, message = "Invalid sector")
	private Long eventSector_id;

	public TicketDTO(Long eventSector_id) {
		super();
		this.eventSector_id = eventSector_id;
	}

	public TicketDTO() {
		super();
	}

	public Long getEventSector_id() {
		return eventSector_id;
	}

	public void setEventSector_id(Long eventSector_id) {
		this.eventSector_id = eventSector_id;
	}

}
