package backend.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("sitting")
public class SittingSector extends Sector {
	private static final long serialVersionUID = -1180010136577643690L;

	@Column(name = "num_rows", nullable = true)
	private int numRows;
	@Column(name = "num_cols", nullable = true)
	private int numCols;

	public SittingSector() {
		super();
		this.numRows = 0;
		this.numCols = 0;
	}

	public SittingSector(Long id, String name, int rows, int cols) {
		super(id, name);
		this.numRows = rows;
		this.numCols = cols;
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

	@Override
	public String toString() {
		return "SittingSector [" + super.toString() + "numRows=" + numRows
				+ ", numCols=" + numCols + "]";
	}

}
