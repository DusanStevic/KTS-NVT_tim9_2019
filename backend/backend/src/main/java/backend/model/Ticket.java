package backend.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "", nullable = true)
	private boolean hasSeat;

	@Column(name = "", nullable = true)
	private Integer numRow;
	@Column(name = "", nullable = true)
	private Integer numCol;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JsonBackReference
	private EventDay eventDay;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	//@JsonIgnoreProperties("tickets")
	@JsonBackReference
	private Reservation reservation;

	@ManyToOne
	@JoinColumn(name = "sector_id")
	private EventSector eventSector;

	public Ticket() {
		super();
	}

	public Ticket(Long id, boolean hasSeat, Integer numRow, Integer numCol,
			EventDay eventDay, Reservation reservation, EventSector sector) {
		super();
		this.id = id;
		this.hasSeat = hasSeat;
		this.numRow = numRow;
		this.numCol = numCol;
		this.eventDay = eventDay;
		this.reservation = reservation;
		this.eventSector = sector;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public EventDay getEventDay() {
		return eventDay;
	}

	public void setEventDay(EventDay eventDay) {
		this.eventDay = eventDay;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public EventSector getEventSector() {
		return eventSector;
	}

	public void setEventSector(EventSector sector) {
		this.eventSector = sector;
	}

}
