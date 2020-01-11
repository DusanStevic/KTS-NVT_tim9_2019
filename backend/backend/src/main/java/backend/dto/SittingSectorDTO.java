package backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class SittingSectorDTO extends SectorDTO {

	@Min(value = 1, message = "Invalid number of rows")
	private int numRows;

	@Min(value = 1, message = "Invalid number of columns")
	private int numCols;

	public SittingSectorDTO(
			@NotNull(message = "Sector name is mandatory") @Length(min = 1, message = "Sector name is mandatory") String name,
			@Min(value = 1, message = "Invalid number of rows") int numRows,
			@Min(value = 1, message = "Invalid number of columns") int numCols) {
		super(name);
		this.numRows = numRows;
		this.numCols = numCols;
	}

	public SittingSectorDTO() {
		super();
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

}
