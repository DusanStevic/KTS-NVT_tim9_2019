package backend.dto;

public class SeatDTO {
	private int row;
	private int col;
	public SeatDTO(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	public SeatDTO() {
		super();
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
