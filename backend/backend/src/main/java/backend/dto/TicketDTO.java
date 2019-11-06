package backend.dto;

public class TicketDTO {
	private int numRow;
	private int numCol;
	private boolean hasSeat;
	private Long eventDay_id;
	private Long eventSector_id;
	public TicketDTO(int numRow, int numCol, boolean hasSeat, Long eventDay_id, Long eventSector_id) {
		super();
		this.numRow = numRow;
		this.numCol = numCol;
		this.hasSeat = hasSeat;
		this.eventDay_id = eventDay_id;
		this.eventSector_id = eventSector_id;
	}
	public TicketDTO() {
		super();
	}
	public int getNumRow() {
		return numRow;
	}
	public void setNumRow(int numRow) {
		this.numRow = numRow;
	}
	public int getNumCol() {
		return numCol;
	}
	public void setNumCol(int numCol) {
		this.numCol = numCol;
	}
	public boolean isHasSeat() {
		return hasSeat;
	}
	public void setHasSeat(boolean hasSeat) {
		this.hasSeat = hasSeat;
	}
	public Long getEventDay_id() {
		return eventDay_id;
	}
	public void setEventDay_id(Long eventDay_id) {
		this.eventDay_id = eventDay_id;
	}
	public Long getEventSector_id() {
		return eventSector_id;
	}
	public void setEventSector_id(Long eventSector_id) {
		this.eventSector_id = eventSector_id;
	}
	
	
	
	
}
