package backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class StandingTicketDTO{

	@NotNull(message = "Number of standing tickets is mandatory")
	@Min(value=1, message="Number of standing tickets must be greater than or equal to {value}")
	private int numOfStandingTickets;

	@NotNull(message = "Sector is mandatory")
	@Min(value = 1, message = "Invalid sector")
	private Long eventSectorId;
	
	private String type;
	
	public StandingTicketDTO() {
	}

	public StandingTicketDTO(Long eventSectorId,
			@NotNull(message = "Number of standing tickets is mandatory") @Min(value = 1, message = "Number of standing tickets must be greater than or equal to {value}") int numOfStandingTickets) {
		this.eventSectorId = eventSectorId;
		this.type = "standingTicketDTO";
		this.numOfStandingTickets = numOfStandingTickets;
	}

	public int getNumOfStandingTickets() {
		return numOfStandingTickets;
	}

	public void setNumOfStandingTickets(int numOfStandingTickets) {
		this.numOfStandingTickets = numOfStandingTickets;
	}

	public Long getEventSectorId() {
		return eventSectorId;
	}

	public void setEventSectorId(Long eventSectorId) {
		this.eventSectorId = eventSectorId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
