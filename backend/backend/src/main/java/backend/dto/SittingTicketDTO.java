package backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SittingTicketDTO{

	@NotNull(message = "Row is mandatory")
	@Min(value=1, message="Row number must be greater than or equal to {value}")
	private int row;
	
	@NotNull(message = "Column is mandatory")
	@Min(value=1, message="Column number must be greater than or equal to {value}")
	private int col;

	@NotNull(message = "Sector is mandatory")
	@Min(value = 1, message = "Invalid sector")
	private Long eventSectorId;
	
	private String type;
	
	public SittingTicketDTO() {
		
	}

	public SittingTicketDTO(Long eventSectorId,
			@NotNull(message = "Row is mandatory") @Min(value = 1, message = "Row number must be greater than or equal to {value}") int row,
			@NotNull(message = "Column is mandatory") @Min(value = 1, message = "Column number must be greater than or equal to {value}") int col) {
		this.eventSectorId = eventSectorId;
		this.row = row;
		this.col = col;
		this.type = "sittingTicketDTO";
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Long getEventSectorId() {
		return eventSectorId;
	}

	public void setEventSectorId(Long eventSectorId) {
		this.eventSectorId = eventSectorId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
