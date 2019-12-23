package backend.dto;

import java.util.ArrayList;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ReservationDTO {
	@NotNull(message = "Event day is mandatory")
	@Min(value=1, message="Invalid event day")
	private Long eventDay_id;
	
	@NotNull(message = "Sector is mandatory")
	@Min(value=1, message="Invalid sector")
	/*
	 * id event sektora
	 */
	private Long sector_id;
	
	@NotNull
	private boolean purchased;
	
	private ArrayList<SeatDTO> sedista;
	
	private int numOfStandingTickets;
	
	public ReservationDTO(Long eventDay_id, Long sector_id, ArrayList<SeatDTO> sedista,boolean purchased) {
		super();
		this.eventDay_id = eventDay_id;
		this.sector_id = sector_id;
		this.sedista = sedista;
		this.purchased = purchased;
	}
	public ReservationDTO() {
		super();
	}
	public Long getEventDay_id() {
		return eventDay_id;
	}
	public void setEventDay_id(Long eventDay_id) {
		this.eventDay_id = eventDay_id;
	}
	public Long getSector_id() {
		return sector_id;
	}
	public void setSector_id(Long sector_id) {
		this.sector_id = sector_id;
	}
	public ArrayList<SeatDTO> getSedista() {
		return sedista;
	}
	public void setSedista(ArrayList<SeatDTO> sedista) {
		this.sedista = sedista;
	}
	public boolean isPurchased() {
		return purchased;
	}
	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}
	public int getNumOfStandingTickets() {
		return numOfStandingTickets;
	}
	public void setNumOfStandingTickets(int numOfStandingTickets) {
		this.numOfStandingTickets = numOfStandingTickets;
	}
	
	
}
