package backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SittingTicketDTO extends TicketDTO{

	@NotNull(message = "Row is mandatory")
	@Min(value=1, message="Row number must be greater than or equal to {value}")
	private int row;
	
	@NotNull(message = "Column is mandatory")
	@Min(value=1, message="Column number must be greater than or equal to {value}")
	private int col;

	public SittingTicketDTO(Long eventSector_id) {
		super(eventSector_id);
	}

	public SittingTicketDTO(Long eventSector_id,
			@NotNull(message = "Row is mandatory") @Min(value = 1, message = "Row number must be greater than or equal to {value}") int row,
			@NotNull(message = "Column is mandatory") @Min(value = 1, message = "Column number must be greater than or equal to {value}") int col) {
		super(eventSector_id);
		this.row = row;
		this.col = col;
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
}
