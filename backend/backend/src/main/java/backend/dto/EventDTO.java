package backend.dto;

import java.util.ArrayList;
import java.util.Date;

public class EventDTO {
	private String name;
	private String description;
	private int event_type;
	private Date start_date;
	private Date end_date;
	private String video_path;
	private int max_tickets;
	//private Date last_day_of_reservation;
	private int num_days; //koliko dana pred manifestaciju vazi rezervacija
	private Long location_id;
	private ArrayList<String> image_paths;
	private ArrayList<EventSectorDTO> sectors;
	private ArrayList<EventDayDTO> event_days;
	
	public EventDTO(String name, String description, int event_type, Date start_date, Date end_date, String video_path,
			int max_tickets, int num_days, Long location_id, ArrayList<String> image_paths,
			ArrayList<EventSectorDTO> sectors, ArrayList<EventDayDTO> event_days) {
		super();
		this.name = name;
		this.description = description;
		this.event_type = event_type;
		this.start_date = start_date;
		this.end_date = end_date;
		this.video_path = video_path;
		this.max_tickets = max_tickets;
		this.num_days = num_days;
		this.location_id = location_id;
		this.image_paths = image_paths;
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
	public String getVideo_path() {
		return video_path;
	}
	public void setVideo_path(String video_path) {
		this.video_path = video_path;
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
	
	
}
