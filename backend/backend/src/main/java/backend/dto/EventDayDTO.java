package backend.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class EventDayDTO {
	@NotNull(message = "Name of event day is mandatory")
	@Length(min=1, message="Name of event day is mandatory")
	private String name;
	
	private String description;
	
	//@NotNull(message = "Date is mandatory")
	private Date date;
	
	//@Min(value=1, message="Street number must be greater than or equal to {value}")
	//private Long event_status_id;
	
	//@NotNull(message = "Event is mandatory")
	//@Min(value=1, message="Id of an event must be greater than or equal to {value}")
	private Long event_id;
	
	private int status;
	
	public EventDayDTO() {
		super();
	}

	
	public EventDayDTO(String name, String description, Date date, Long event_status_id, Long event_id, int status) {
		super();
		this.name = name;
		this.description = description;
		this.date = date;
		//this.event_status_id = event_status_id;
		this.event_id = event_id;
		this.status = status;
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


	/*public Long getEvent_status_id() {
		return event_status_id;
	}


	public void setEvent_status_id(Long event_status_id) {
		this.event_status_id = event_status_id;
	}*/


	public Long getEvent_id() {
		return event_id;
	}


	public void setEvent_id(Long event_id) {
		this.event_id = event_id;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
