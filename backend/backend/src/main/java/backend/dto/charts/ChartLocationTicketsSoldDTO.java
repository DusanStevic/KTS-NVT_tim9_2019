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

	public void setTicketsSold(double ticketsSold) {
		this.ticketsSold = ticketsSold;
	}

	@Override
	public String toString() {
		return "ChartLocationTicketsSoldDTO [locationName=" + locationName
				+ ", ticketsSold=" + ticketsSold + "]";
	}

}
