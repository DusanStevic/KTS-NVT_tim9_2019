package backend.dto.charts;

import java.util.Date;

public class DateIntervalDTO {
	private Date startDate;
	private Date endDate;

	public DateIntervalDTO() {
		super();
	}

	public DateIntervalDTO(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
