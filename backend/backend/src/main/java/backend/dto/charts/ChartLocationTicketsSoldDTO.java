package backend.dto.charts;

public class ChartLocationTicketsSoldDTO {
	private String locationName;
	private double ticketsSold;

	public ChartLocationTicketsSoldDTO() {
		super();
	}

	public ChartLocationTicketsSoldDTO(String locationName, double ticketsSold) {
		super();
		this.locationName = locationName;
		this.ticketsSold = ticketsSold;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String eventName) {
		this.locationName = eventName;
	}

	public double getTicketsSold() {
		return ticketsSold;
	}

	public void setTicketsSold(int ticketsSold) {
		this.ticketsSold = ticketsSold;
	}
}
