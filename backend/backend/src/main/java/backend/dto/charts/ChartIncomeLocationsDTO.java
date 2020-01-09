package backend.dto.charts;

public class ChartIncomeLocationsDTO {
	private String locationName;
	private double income;

	public ChartIncomeLocationsDTO() {
		super();
	}

	public ChartIncomeLocationsDTO(String locationName, double income) {
		super();
		this.locationName = locationName;
		this.income = income;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	@Override
	public String toString() {
		return "ChartIncomeLocationsDTO [locationName=" + locationName
				+ ", income=" + income + "]";
	}

}
