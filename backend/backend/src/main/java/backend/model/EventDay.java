package backend.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import backend.dto.EventDayDTO;

@Entity
@Table(name = "event_days")
public class EventDay {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 70)
	private String name;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "day_date", nullable = false)
	private Date date;

	@Column(name = "status", nullable = true)
	private EventStatus status;

	@OneToMany(mappedBy = "eventDay", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference("tickets")
	private Set<Ticket> tickets = new HashSet<>();

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference("days")
	private Event event;

	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;
	
	public EventDay() {
		super();
	}

	public EventDay(EventDayDTO e) {
		this.name = e.getName();
		this.description = e.getDescription();
		this.date = e.getDate();
	}
	public EventDay(Long id, String name, String description, Date date,
			EventStatus status, Set<Ticket> tickets, Event event, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
		this.status = status;
		this.tickets = tickets;
		this.event = event;
		this.deleted = deleted;
	}

	public EventDay(Long eventid, String name, String description, boolean b) {
		this.id=eventid;
		this.name = name;
		this.description = description;
		this.deleted = b;
	}

	public EventDay(String name, String description) {
		this.name = name; 
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "EventDay [id=" + id + ", name=" + name + ", description="
				+ description + ", date=" + date + ", status=" + status
				+ ", tickets=" + tickets + ", event=" + event + "]";
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventDay other = (EventDay) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
