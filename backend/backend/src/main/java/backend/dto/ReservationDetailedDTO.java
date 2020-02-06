package backend.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import backend.model.Reservation;

public class ReservationDetailedDTO {
	private Long id;
	private String eventName;
	private boolean purchased;
	private List<SimpleTicketDTO> tickets;
	private double fullPrice;
	private String username;
	private Date reservationDate;

	public ReservationDetailedDTO() {
		super();
	}

	public ReservationDetailedDTO(Long id, String eventName, boolean purchased,
			List<SimpleTicketDTO> tickets, double fullPrice, String username,
			Date reservationDate) {
		super();
		this.id = id;
		this.eventName = eventName;
		this.purchased = purchased;
		this.tickets = tickets;
		this.fullPrice = fullPrice;
		this.username = username;
		this.reservationDate = reservationDate;
	}

	public ReservationDetailedDTO(Reservation r) {
		this.id = r.getId();
		this.eventName = r.getTickets().iterator().next().getEventDay()
				.getEvent().getName();
		this.purchased = r.isPurchased();
		ArrayList<SimpleTicketDTO> tickets = new ArrayList<SimpleTicketDTO>();
		r.getTickets().forEach((t) -> {
			tickets.add(new SimpleTicketDTO(t));
		});
		this.tickets = tickets;
		this.fullPrice = tickets.stream().mapToDouble(x -> x.getPrice()).sum();
		this.username = r.getBuyer().getUsername();
		this.reservationDate = r.getReservationDate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public boolean isPurchased() {
		return purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}

	public List<SimpleTicketDTO> getTickets() {
		return tickets;
	}

	public void setTickets(List<SimpleTicketDTO> tickets) {
		this.tickets = tickets;
	}

	public double getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(double fullPrice) {
		this.fullPrice = fullPrice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

}
