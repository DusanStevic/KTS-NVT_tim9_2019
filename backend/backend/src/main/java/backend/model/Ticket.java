package backend.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "tickets")
public class Ticket {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private boolean hasSeat;
	private double price;
	private int numRow;
	private int numCol;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JsonIgnoreProperties("tickets")
	private EventDay eventDay;
	private Reservation reservation;
	private Sector sector;

	public Ticket() {
		super();
	}

	public Ticket(Long id, boolean hasSeat, double price, int numRow,
			int numCol, EventDay eventDay, Reservation reservation,
			Sector sector) {
		super();
		this.id = id;
		this.hasSeat = hasSeat;
		this.price = price;
		this.numRow = numRow;
		this.numCol = numCol;
		this.eventDay = eventDay;
		this.reservation = reservation;
		this.sector = sector;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNumRow() {
		return numRow;
	}

	public void setNumRow(int numRow) {
		this.numRow = numRow;
	}

	public int getNumCol() {
		return numCol;
	}

	public void setNumCol(int numCol) {
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

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

}
