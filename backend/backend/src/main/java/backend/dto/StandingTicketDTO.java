package backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class StandingTicketDTO extends TicketDTO{

	@NotNull(message = "Number of standing tickets is mandatory")
	@Min(value=1, message="Number of standing tickets must be greater than or equal to {value}")
	private int numOfStandingTickets;

	public StandingTicketDTO(Long eventSector_id) {
		super(eventSector_id);
	}

	public StandingTicketDTO(Long eventSector_id,
			@NotNull(message = "Number of standing tickets is mandatory") @Min(value = 1, message = "Number of standing tickets must be greater than or equal to {value}") int numOfStandingTickets) {
		super(eventSector_id);
		this.numOfStandingTickets = numOfStandingTickets;
	}

	public int getNumOfStandingTickets() {
		return numOfStandingTickets;
	}

	public void setNumOfStandingTickets(int numOfStandingTickets) {
		this.numOfStandingTickets = numOfStandingTickets;
	}
}
