package backend.dto.charts;

public class ChartIncomeEventsDTO {
	private String eventName;
	private double income;

	public ChartIncomeEventsDTO() {
		super();
	}

	public ChartIncomeEventsDTO(String eventName, double income) {
		super();
		this.eventName = eventName;
		this.income = income;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

}
