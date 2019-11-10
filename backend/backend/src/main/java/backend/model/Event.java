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
import javax.persistence.ManyToOne;
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
	@Column(name = "videoPath", nullable = true)
	private String videoPath;
	@Column(name = "maxTickets", nullable = false)
	private int maxTickets;
	/*@Column(name = "lastDayOfReservation", nullable = false)
	private Date lastDayOfReservation;*/
	@Column(name = "numDays", nullable = false)
	private int numDays; //koliko dana pred event moze da se rezervise
	
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
	@ElementCollection(fetch = FetchType.LAZY)
	private Set<String> imagePaths = new HashSet<>();

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("event")
	private Set<EventSector> eventSectors = new HashSet<>();

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("event")
	private Set<EventDay> eventDays = new HashSet<>();

	public Event() {
		super();
	}

	public Event(Long id, String name, String description, EventType type,
			Date startDate, Date endDate, String videoPath, int maxTickets,
			int numDays, Location location,
			Set<String> imagePaths, Set<EventSector> eventSectors,
			Set<EventDay> eventDays) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.eventType = type;
		this.startDate = startDate;
		this.endDate = endDate;
		this.videoPath = videoPath;
		this.maxTickets = maxTickets;
		this.numDays = numDays;
		this.location = location;
		this.imagePaths = imagePaths;
		this.eventSectors = eventSectors;
		this.eventDays = eventDays;
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

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
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
				+ videoPath + ", maxTickets=" + maxTickets + ", location=" + location + ", imagePaths="
				+ imagePaths + ", eventSectors=" + eventSectors
				+ ", eventDays=" + eventDays + "]";
	}

	public int getNumDays() {
		return numDays;
	}

	public void setNumDays(int numDays) {
		this.numDays = numDays;
	}

}
