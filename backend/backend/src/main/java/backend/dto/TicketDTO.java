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
	private Long eventSectorId;

	public TicketDTO(Long eventSectorId) {
		super();
		this.eventSectorId = eventSectorId;
	}

	public TicketDTO() {
		super();
	}

	public Long getEventSectorId() {
		return eventSectorId;
	}

	public void setEventSectorId(Long eventSectorId) {
		this.eventSectorId = eventSectorId;
	}

}
