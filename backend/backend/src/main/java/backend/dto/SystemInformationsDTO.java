package backend.dto;

public class SystemInformationsDTO {
	private int numberOfEvents;
	private int numberOfAdmins;
	private int numberOfUsers;
	private double allTimeIncome;
	private int allTimeTickets;

	public SystemInformationsDTO() {
		super();
	}

	public SystemInformationsDTO(int numberOfEvents, int numberOfAdmins,
			int numberOfUsers, double allTimeIncome, int allTimeTickets) {
		super();
		this.numberOfEvents = numberOfEvents;
		this.numberOfAdmins = numberOfAdmins;
		this.numberOfUsers = numberOfUsers;
		this.allTimeIncome = allTimeIncome;
		this.allTimeTickets = allTimeTickets;
	}

	public int getNumberOfEvents() {
		return numberOfEvents;
	}

	public void setNumberOfEvents(int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}

	public int getNumberOfAdmins() {
		return numberOfAdmins;
	}

	public void setNumberOfAdmins(int numberOfAdmins) {
		this.numberOfAdmins = numberOfAdmins;
	}

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public double getAllTimeIncome() {
		return allTimeIncome;
	}

	public void setAllTimeIncome(double allTimeIncome) {
		this.allTimeIncome = allTimeIncome;
	}

	public int getAllTimeTickets() {
		return allTimeTickets;
	}

	public void setAllTimeTickets(int allTimeTickets) {
		this.allTimeTickets = allTimeTickets;
	}

}
