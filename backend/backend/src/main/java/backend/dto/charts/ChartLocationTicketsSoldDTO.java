package backend.dto.charts;

public class ChartLocationTicketsSoldDTO {
	private String locationName;
	private int ticketsSold;

	public ChartLocationTicketsSoldDTO() {
		super();
	}

	public ChartLocationTicketsSoldDTO(String eventName, int ticketsSold) {
		super();
		this.locationName = eventName;
		this.ticketsSold = ticketsSold;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String eventName) {
		this.locationName = eventName;
	}

	public int getTicketsSold() {
		return ticketsSold;
	}

	public void setTicketsSold(int ticketsSold) {
		this.ticketsSold = ticketsSold;
	}
}
