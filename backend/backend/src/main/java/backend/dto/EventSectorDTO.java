package backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EventSectorDTO {
	@NotNull(message = "Price is mandatory")
	@Min(value=0, message="Price must be greater than or equal to {value}")
	private Double price;
	
	/*
	 * ne prosledjuje se prilikom kreiranja eventa
	 */
	private Long event_id;
	
	@NotNull(message = "Sector is mandatory")
	@Min(value=1, message="Invalid sector")
	private Long sectorId;
	
	public EventSectorDTO(double price, Long event_id, Long sector_id) {
		super();
		this.price = price;
		this.event_id = event_id;
		this.sectorId = sector_id;
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
	public Long getEventId() {
		return event_id;
	}
	public void setEventId(Long event_id) {
		this.event_id = event_id;
	}
	public Long getSectorId() {
		return sectorId;
	}
	public void setSectorId(Long sector_id) {
		this.sectorId = sector_id;
	}
	
	
}
