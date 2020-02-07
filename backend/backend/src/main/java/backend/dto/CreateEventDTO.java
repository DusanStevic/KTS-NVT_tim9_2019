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
	private int event_type;
	
	@NotNull(message = "Event starting date is mandatory")
	private Date start_date;
	
	@NotNull(message = "Event ending date is mandatory")
	private Date end_date;
	
	@NotNull(message = "Maximum number of tickets per reservation is mandatory")
	@Min(value=1, message="Maximum number of tickets per reservation must be greater than or equal to {value}")
	private int max_tickets; //max tickets per reservation
	
	@NotNull(message = "Number of days is mandatory")
	private int num_days; //koliko dana pred manifestaciju vazi rezervacija
	
	@NotNull(message = "Location is mandatory")
	@Min(value=1, message="Invalid location id")
	private Long location_id;
	
	@NotNull(message = "Sectors are mandatory")
	@NotEmpty(message="Sectors are mandatory")
	private ArrayList<EventSectorDTO> sectors;

	public CreateEventDTO(
			@NotNull(message = "Event name is mandatory") @Length(min = 1, message = "Event name is mandatory") String name,
			String description,
			@NotNull(message = "Event type is mandatory") @Min(value = 0, message = "Invalid type of an event") @Max(value = 4, message = "Invalid type of an event") int event_type,
			@NotNull(message = "Event starting date is mandatory") Date start_date,
			@NotNull(message = "Event ending date is mandatory") Date end_date,
			@NotNull(message = "Maximum number of tickets per reservation is mandatory") @Min(value = 1, message = "Maximum number of tickets per reservation must be greater than or equal to {value}") int max_tickets,
			@NotNull(message = "Number of days is mandatory") int num_days,
			@NotNull(message = "Location is mandatory") @Min(value = 1, message = "Invalid location id") Long location_id,
			@NotNull(message = "Sectors are mandatory") @NotEmpty(message = "Sectors are mandatory") ArrayList<EventSectorDTO> sectors) {
		super();
		this.name = name;
		this.description = description;
		this.event_type = event_type;
		this.start_date = start_date;
		this.end_date = end_date;
		this.max_tickets = max_tickets;
		this.num_days = num_days;
		this.location_id = location_id;
		this.sectors = sectors;
	}

	public CreateEventDTO() {
		super();
	}

	public CreateEventDTO(String string) {
		this.name = string;
	}

	@Override
	public String toString() {
		return "CreateEventDTO [name=" + name + ", description=" + description + ", event_type=" + event_type
				+ ", start_date=" + start_date + ", end_date=" + end_date + ", max_tickets=" + max_tickets
				+ ", num_days=" + num_days + ", location_id=" + location_id + ", sectors=" + sectors + "]";
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

	public int getEvent_type() {
		return event_type;
	}

	public void setEvent_type(int event_type) {
		this.event_type = event_type;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public int getMax_tickets() {
		return max_tickets;
	}

	public void setMax_tickets(int max_tickets) {
		this.max_tickets = max_tickets;
	}

	public int getNum_days() {
		return num_days;
	}

	public void setNum_days(int num_days) {
		this.num_days = num_days;
	}

	public Long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}

	public ArrayList<EventSectorDTO> getSectors() {
		return sectors;
	}

	public void setSectors(ArrayList<EventSectorDTO> sectors) {
		this.sectors = sectors;
	}
}
