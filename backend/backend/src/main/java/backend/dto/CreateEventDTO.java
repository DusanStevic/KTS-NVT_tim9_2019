package backend.dto;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class CreateEventDTO {

	@NotNull(message = "Event name is mandatory")
	@Length(min=1, message="Event name is mandatory")
	private String name;
	
	private String description;
	
	@NotNull(message = "Event type is mandatory")
	@Min(value=0, message="Invalid type of an event")
	@Max(value=4, message="Invalid type of an event")
	private int eventType;
	
	@NotNull(message = "Event starting date is mandatory")
	private Date startDate;
	
	@NotNull(message = "Event ending date is mandatory")
	private Date endDate;
	
	@NotNull(message = "Maximum number of tickets per reservation is mandatory")
	@Min(value=1, message="Maximum number of tickets per reservation must be greater than or equal to {value}")
	private int maxTickets; //max tickets per reservation
	
	@NotNull(message = "Number of days is mandatory")
	private int numDays; //koliko dana pred manifestaciju vazi rezervacija
	
	@NotNull(message = "Location is mandatory")
	@Min(value=1, message="Invalid location id")
	private Long locationId;
	
	@NotNull(message = "Hall is mandatory")
	@Min(value=1, message="Invalid hall id")
	private Long hallId;
	/*@NotNull(message = "Sectors are mandatory")
	@NotEmpty(message="Sectors are mandatory")
	private ArrayList<EventSectorDTO> sectors;*/


	public CreateEventDTO() {
		super();
	}

	public CreateEventDTO(
			@NotNull(message = "Event name is mandatory") @Length(min = 1, message = "Event name is mandatory") String name,
			String description,
			@NotNull(message = "Event type is mandatory") @Min(value = 0, message = "Invalid type of an event") @Max(value = 4, message = "Invalid type of an event") int eventType,
			@NotNull(message = "Event starting date is mandatory") Date startDate,
			@NotNull(message = "Event ending date is mandatory") Date endDate,
			@NotNull(message = "Maximum number of tickets per reservation is mandatory") @Min(value = 1, message = "Maximum number of tickets per reservation must be greater than or equal to {value}") int maxTickets,
			@NotNull(message = "Number of days is mandatory") int numDays,
			@NotNull(message = "Location is mandatory") @Min(value = 1, message = "Invalid location id") Long locationId,
			@NotNull(message = "Hall is mandatory") @Min(value = 1, message = "Invalid hall id") Long hallId) {
		super();
		this.name = name;
		this.description = description;
		this.eventType = eventType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxTickets = maxTickets;
		this.numDays = numDays;
		this.locationId = locationId;
		this.hallId = hallId;
	}

	public CreateEventDTO(String string) {
		this.name = string;
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

	/*public ArrayList<EventSectorDTO> getSectors() {
		return sectors;
	}

	public void setSectors(ArrayList<EventSectorDTO> sectors) {
		this.sectors = sectors;
	}*/


	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
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

	public int getNumDays() {
		return numDays;
	}

	public void setNumDays(int numDays) {
		this.numDays = numDays;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getHallId() {
		return hallId;
	}

	public void setHallId(Long hallId) {
		this.hallId = hallId;
	}

	@Override
	public String toString() {
		return "CreateEventDTO [name=" + name + ", description=" + description + ", eventType=" + eventType
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", maxTickets=" + maxTickets + ", numDays="
				+ numDays + ", locationId=" + locationId + ", hallId=" + hallId + "]";
	}
}
