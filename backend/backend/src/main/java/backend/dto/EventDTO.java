package backend.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import backend.model.Event;

public class EventDTO {
	private Long id;
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
	
	private ArrayList<String> image_paths;
	private ArrayList<String> video_paths;
	
	@NotNull(message = "Sectors are mandatory")
	@NotEmpty(message="Sectors are mandatory")
	private ArrayList<EventSectorDTO> sectors;
	
	@NotNull(message = "Event days are mandatory")
	@NotEmpty(message="Event days are mandatory")
	private ArrayList<EventDayDTO> event_days;
	
	public EventDTO(Event event){
		this.id = event.getId();
		this.name = event.getName();
		this.description =event.getDescription();
		this.event_type = event.getEventType().ordinal();
		this.start_date = event.getStartDate();
		this.end_date = event.getEndDate();
		this.max_tickets = event.getMaxTickets();
		this.num_days = event.getNumDays();
		this.location_id = event.getLocation().getId();
		this.image_paths = (ArrayList<String>) event.getImagePaths().stream().map(temp->temp).collect(Collectors.toList());
		this.video_paths = (ArrayList<String>) event.getVideoPaths().stream().map(temp->temp).collect(Collectors.toList());
		this.sectors = (ArrayList<EventSectorDTO>) event.getEventSectors().stream().map(temp->{
			EventSectorDTO obj = new EventSectorDTO();
			obj.setPrice(temp.getPrice());
			obj.setEvent_id(temp.getEvent().getId());
			obj.setSector_id(temp.getSector().getId());
			return obj;
		}).collect(Collectors.toList());
		this.event_days = (ArrayList<EventDayDTO>) event.getEventDays().stream().map(temp->{
			EventDayDTO obj = new EventDayDTO();
			obj.setName(temp.getName());
			obj.setDescription(temp.getDescription());
			obj.setDate(temp.getDate());
			obj.setStatus(temp.getStatus().ordinal());
			obj.setEvent_id(temp.getEvent().getId());
			
			return obj;
		}).collect(Collectors.toList());
		
	
	}
	
	public EventDTO(String name, String description, int event_type, Date start_date, Date end_date,
			int max_tickets, int num_days, Long location_id, ArrayList<String> image_paths,ArrayList<String> video_paths,
			ArrayList<EventSectorDTO> sectors, ArrayList<EventDayDTO> event_days) {
		super();
		this.name = name;
		this.description = description;
		this.event_type = event_type;
		this.start_date = start_date;
		this.end_date = end_date;
		
		this.max_tickets = max_tickets;
		this.num_days = num_days;
		this.location_id = location_id;
		this.image_paths = image_paths;
		this.video_paths = video_paths;
		this.sectors = sectors;
		this.event_days = event_days;
	}
	public EventDTO() {
		super();
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
	public ArrayList<String> getImage_paths() {
		return image_paths;
	}
	public void setImage_paths(ArrayList<String> image_paths) {
		this.image_paths = image_paths;
	}
	public ArrayList<EventSectorDTO> getSectors() {
		return sectors;
	}
	public void setSectors(ArrayList<EventSectorDTO> sectors) {
		this.sectors = sectors;
	}
	public ArrayList<EventDayDTO> getEvent_days() {
		return event_days;
	}
	public void setEvent_days(ArrayList<EventDayDTO> event_days) {
		this.event_days = event_days;
	}

	

	public ArrayList<String> getVideo_paths() {
		return video_paths;
	}

	public void setVideo_paths(ArrayList<String> video_paths) {
		this.video_paths = video_paths;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
}
