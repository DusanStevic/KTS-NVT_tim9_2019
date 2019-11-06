package backend.dto;

public class EventSectorDTO {
	private double price;
	private Long event_id;
	private Long sector_id;
	
	public EventSectorDTO(double price, Long event_id, Long sector_id) {
		super();
		this.price = price;
		this.event_id = event_id;
		this.sector_id = sector_id;
	}
	public EventSectorDTO() {
		super();
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Long getEvent_id() {
		return event_id;
	}
	public void setEvent_id(Long event_id) {
		this.event_id = event_id;
	}
	public Long getSector_id() {
		return sector_id;
	}
	public void setSector_id(Long sector_id) {
		this.sector_id = sector_id;
	}
	
	
}
