package backend.dto;

import java.util.ArrayList;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReservationDTO {

	@NotNull
	private boolean purchased;

	//@NotNull(message = "Tickets are mandatory")
	//@NotEmpty(message = "Tickets are mandatory")
	private ArrayList<SittingTicketDTO> sittingTickets;

	//@NotNull(message = "Tickets are mandatory")
	//@NotEmpty(message = "Tickets are mandatory")
	private ArrayList<StandingTicketDTO> standingTickets;
		
	@NotNull(message = "Event day is mandatory")
	@Min(value = 1, message = "Invalid event day")
	private Long eventDayId;

	public ReservationDTO() {
		super();
	}

	

	public ReservationDTO(@NotNull boolean purchased, ArrayList<SittingTicketDTO> sittingTickets,
			ArrayList<StandingTicketDTO> standingTickets,
			@NotNull(message = "Event day is mandatory") @Min(value = 1, message = "Invalid event day") Long eventDayId) {
		super();
		this.purchased = purchased;
		this.sittingTickets = sittingTickets;
		this.standingTickets = standingTickets;
		this.eventDayId = eventDayId;
	}



	public boolean isPurchased() {
		return purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}

	

	public Long getEventDayId() {
		return eventDayId;
	}

	public void setEventDayId(Long eventDay_id) {
		this.eventDayId = eventDay_id;
	}



	public ArrayList<SittingTicketDTO> getSittingTickets() {
		return sittingTickets;
	}



	public void setSittingTickets(ArrayList<SittingTicketDTO> sittingTickets) {
		this.sittingTickets = sittingTickets;
	}



	public ArrayList<StandingTicketDTO> getStandingTickets() {
		return standingTickets;
	}



	public void setStandingTickets(ArrayList<StandingTicketDTO> standingTickets) {
		this.standingTickets = standingTickets;
	}

}
