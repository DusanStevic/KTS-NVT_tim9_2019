package backend.dto.charts;

public class ChartEventTicketsSoldDTO {
	private String eventName;
	private double ticketsSold;

	public ChartEventTicketsSoldDTO() {
		super();
	}

	public ChartEventTicketsSoldDTO(String eventName, double ticketsSold) {
		super();
		this.eventName = eventName;
		this.ticketsSold = ticketsSold;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public double getTicketsSold() {
		return ticketsSold;
	}

	public void setTicketsSold(int ticketsSold) {
		this.ticketsSold = ticketsSold;
	}

}
