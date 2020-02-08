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
	
	/*@NotNull(message = "Sectors are mandatory")
	@NotEmpty(message="Sectors are mandatory")
	private ArrayList<EventSectorDTO> sectors;*/

	public CreateEventDTO(
			@NotNull(message = "Event name is mandatory") @Length(min = 1, message = "Event name is mandatory") String name,
			String description,
			@NotNull(message = "Event type is mandatory") @Min(value = 0, message = "Invalid type of an event") @Max(value = 4, message = "Invalid type of an event") int event_type,
			@NotNull(message = "Event starting date is mandatory") Date start_date,
			@NotNull(message = "Event ending date is mandatory") Date end_date,
			@NotNull(message = "Maximum number of tickets per reservation is mandatory") @Min(value = 1, message = "Maximum number of tickets per reservation must be greater than or equal to {value}") int max_tickets,
			@NotNull(message = "Number of days is mandatory") int num_days,
			@NotNull(message = "Location is mandatory") @Min(value = 1, message = "Invalid location id") Long location_id) {
		super();
		this.name = name;
		this.description = description;
		this.eventType = event_type;
		this.startDate = start_date;
		this.endDate = end_date;
		this.maxTickets = max_tickets;
		this.numDays = num_days;
		this.locationId = location_id;
		//this.sectors = sectors;
	}

	public CreateEventDTO() {
		super();
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

	@Override
	public String toString() {
		return "CreateEventDTO [name=" + name + ", description=" + description + ", eventType=" + eventType
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", maxTickets=" + maxTickets + ", numDays="
				+ numDays + ", locationId=" + locationId;
	}

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
}
