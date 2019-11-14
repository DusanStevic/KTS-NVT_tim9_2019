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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "event_sectors")
public class EventSector {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "price", nullable = false)
	private double price;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JsonIgnoreProperties("eventSectors")
	private Event event;

	@JoinColumn(name = "sector_id", unique = false)
	@OneToOne(cascade = CascadeType.ALL)
	private Sector sector;

	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;
	
	public EventSector() {
		super();
	}

	public EventSector(Long id, double price, Event event, Sector sector, boolean deleted ) {
		super();
		this.id = id;
		this.price = price;
		this.event = event;
		this.sector = sector;
		this.deleted = deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	@Override
	public String toString() {
		return "EventSector [id=" + id + ", price=" + price + ", event="
				+ event.toString() + ", sector=" + sector.toString() + "]";
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
