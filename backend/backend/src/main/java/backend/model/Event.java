package backend.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "events")
public class Event {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", nullable = false, length = 70)
	private String name;
	@Column(name = "description", nullable = true, length = 100)
	private String description;
	@Column(name = "eventType", nullable = false)
	private EventType eventType;
	@Column(name = "startDate", nullable = false)
	private Date startDate;
	@Column(name = "endDate", nullable = false)
	private Date endDate;
	@Column(name = "maxTickets", nullable = false)
	private int maxTickets;
	/*@Column(name = "lastDayOfReservation", nullable = false)
	private Date lastDayOfReservation;*/
	@Column(name = "numDays", nullable = false)
	private int numDays; //koliko dana pred event moze da se rezervise
	
	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;
	
	/*@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JsonIgnoreProperties("events")
	private Administrator admin;*/

	@JoinColumn(name = "location_id", unique = false)
	@OneToOne(cascade = CascadeType.ALL)
	private Location location;

	/*
	 * @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade =
	 * CascadeType.ALL)
	 * 
	 * @JsonIgnoreProperties("event") check how to make oneway list
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> imagePaths = new HashSet<>();
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> videoPaths = new HashSet<>();
	
	/*//TO DO: PREBACI SE NA SET 
	@Column(name = "videoPath", nullable = true)
	private String videoPath;*/
	

	@OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("event")
	private Set<EventSector> eventSectors = new HashSet<>();

	@OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("event")
	private Set<EventDay> eventDays = new HashSet<>();

	public Event() {
		super();
	}

	public Event(Long id, String name, String description, EventType type,
			Date startDate, Date endDate, int maxTickets,
			int numDays, Location location,
			Set<String> imagePaths,Set<String> videoPaths, Set<EventSector> eventSectors,
			Set<EventDay> eventDays, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.eventType = type;
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxTickets = maxTickets;
		this.numDays = numDays;
		this.location = location;
		this.imagePaths = imagePaths;
		this.videoPaths = videoPaths;
		this.eventSectors = eventSectors;
		this.eventDays = eventDays;
		this.deleted = deleted;
	}
	
	
	//za unit testiranje servisa
	public Event(Long id, String name, String description, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.deleted = deleted;
	}

	public Event(String string, String description) {
		this.name = string;
		this.description = description;
	}

	public Event(String name, String description, EventType type, int max,Location location) {
		this.name = name;
		this.description = description;
		this.eventType = type;
		this.maxTickets = max;
		this.location = location;
	}
	
	public Event(String name, String description, EventType type, int max) {
		this.name = name;
		this.description = description;
		this.eventType = type;
		this.maxTickets = max;
	}

	public void setEvent(Event e) {
		this.deleted = e.deleted;
		this.name = e.name;
		this.description = e.description;
		this.eventType = e.eventType;
		this.startDate = e.startDate;
		this.endDate = e.endDate;
		this.videoPaths = e.videoPaths;
		this.maxTickets = e.maxTickets;
		this.numDays = e.numDays;
		this.location = e.location;
		this.imagePaths = e.imagePaths;
		//this.eventSectors = e.eventSectors;
		//this.eventDays = e.eventDays;
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

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType type) {
		this.eventType = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public int getMaxTickets() {
		return maxTickets;
	}

	public void setMaxTickets(int maxTickets) {
		this.maxTickets = maxTickets;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Set<String> getImagePaths() {
		return imagePaths;
	}

	public void setImagePaths(Set<String> imagePaths) {
		this.imagePaths = imagePaths;
	}

	public Set<EventSector> getEventSectors() {
		return eventSectors;
	}

	public void setEventSectors(Set<EventSector> eventSectors) {
		this.eventSectors = eventSectors;
	}

	public Set<EventDay> getEventDays() {
		return eventDays;
	}

	public void setEventDays(Set<EventDay> eventDays) {
		this.eventDays = eventDays;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", description="
				+ description + ", type=" + eventType + ", startDate="
				+ startDate + ", endDate=" + endDate + ", videoPath="
				+ videoPaths + ", maxTickets=" + maxTickets + ", location=" + location + ", imagePaths="
				+ imagePaths + ", eventSectors=" + eventSectors
				+ ", eventDays=" + eventDays + "]";
	}

	public int getNumDays() {
		return numDays;
	}

	public void setNumDays(int numDays) {
		this.numDays = numDays;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<String> getVideoPaths() {
		return videoPaths;
	}

	public void setVideoPaths(Set<String> videoPaths) {
		this.videoPaths = videoPaths;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
