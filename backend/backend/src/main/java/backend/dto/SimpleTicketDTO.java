package backend.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import backend.model.Ticket;

public class SimpleTicketDTO implements Serializable {
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private boolean hasSeat;

	private Integer numRow;
	private Integer numCol;
	private String sectorName;
	private String hallName;
	private String locationName;

	private String nameEvent;
	private String nameED;

	private Long reservationID;
	private Date dateED;
	private Double price;

	public SimpleTicketDTO() {
	}

	public SimpleTicketDTO(Ticket t) {
		super();
		this.hasSeat = t.isHasSeat();
		this.numRow = t.getNumRow();
		this.numCol = t.getNumCol();
		this.sectorName = t.getEventSector().getSector().getName();
		this.hallName = t.getEventSector().getSector().getHall().getName();
		this.locationName = t.getEventSector().getEvent().getLocation()
				.getName();
		this.nameEvent = t.getEventDay().getEvent().getName();
		this.nameED = t.getEventDay().getName();
		this.reservationID = t.getReservation().getId();
		this.dateED = t.getEventDay().getDate();
		this.price = t.getEventSector().getPrice();
	}

	public SimpleTicketDTO(boolean hasSeat, Integer numRow, Integer numCol,
			String sectorName, String hallName, String locationName,
			String nameEvent, String nameED, Long reservationID, Date dateED,
			Double price) {
		super();
		this.hasSeat = hasSeat;
		this.numRow = numRow;
		this.numCol = numCol;
		this.sectorName = sectorName;
		this.hallName = hallName;
		this.locationName = locationName;
		this.nameEvent = nameEvent;
		this.nameED = nameED;
		this.reservationID = reservationID;
		this.dateED = dateED;
		this.price = price;
	}

	public boolean isHasSeat() {
		return hasSeat;
	}

	public void setHasSeat(boolean hasSeat) {
		this.hasSeat = hasSeat;
	}

	public Integer getNumRow() {
		return numRow;
	}

	public void setNumRow(Integer numRow) {
		this.numRow = numRow;
	}

	public Integer getNumCol() {
		return numCol;
	}

	public void setNumCol(Integer numCol) {
		this.numCol = numCol;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
	}

	public String getNameEvent() {
		return nameEvent;
	}

	public void setNameEvent(String nameEvent) {
		this.nameEvent = nameEvent;
	}

	public String getNameED() {
		return nameED;
	}

	public void setNameED(String nameED) {
		this.nameED = nameED;
	}

	public Long getReservationID() {
		return reservationID;
	}

	public void setReservationID(Long reservationID) {
		this.reservationID = reservationID;
	}

	public Date getDateED() {
		return dateED;
	}

	public void setDateED(Date dateED) {
		this.dateED = dateED;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

}
