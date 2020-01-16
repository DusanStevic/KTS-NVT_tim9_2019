package backend.dto;

import java.util.ArrayList;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ReservationDTO {

	@NotNull
	private boolean purchased;

	@NotNull(message = "Tickets are mandatory")
	@NotEmpty(message = "Tickets are mandatory")
	private ArrayList<TicketDTO> tickets;

	@NotNull(message = "Event day is mandatory")
	@Min(value = 1, message = "Invalid event day")
	private Long eventDay_id;

	public ReservationDTO() {
		super();
	}

	public ReservationDTO(@NotNull boolean purchased,
			@NotNull(message = "Tickets are mandatory") @NotEmpty(message = "Tickets are mandatory") ArrayList<TicketDTO> tickets,
			@NotNull(message = "Event day is mandatory") @Min(value = 1, message = "Invalid event day") Long eventDay_id) {
		super();
		this.purchased = purchased;
		this.tickets = tickets;
		this.eventDay_id = eventDay_id;
	}

	public boolean isPurchased() {
		return purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}

	public ArrayList<TicketDTO> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<TicketDTO> tickets) {
		this.tickets = tickets;
	}

	public Long getEventDay_id() {
		return eventDay_id;
	}

	public void setEventDay_id(Long eventDay_id) {
		this.eventDay_id = eventDay_id;
	}

}
