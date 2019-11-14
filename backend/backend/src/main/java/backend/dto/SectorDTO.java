package backend.dto;

public class SectorDTO {
	//private Long id;
	private String name;
	private int capacity;
	private int numRows;
	private int numCols;
	// Type of sector -> 'sitting' or 'standing'
	private String sector_type;
	private Long hall_id;

	public SectorDTO() {
		super();
	}

	/*
	 * //fast convert from class to DTO public SectorDTO(SittingSector s) {
	 * this.id = s.getId(); this.name = s.getName(); this.numRows =
	 * s.getNumRows(); this.numCols = s.getNumCols(); this.sector_type =
	 * "sitting"; }
	 * 
	 * //fast convert from class to DTO public SectorDTO(StandingSector s) {
	 * this.id = s.getId(); this.name = s.getName(); this.capacity =
	 * s.getCapacity(); this.sector_type = "standing"; }
	 */

	public SectorDTO(String name, int capacity, int numRows,
			int numCols, String type, Long hallId) {
		super();
		//this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.numRows = numRows;
		this.numCols = numCols;
		this.sector_type = type;
		this.hall_id = hallId;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
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

	public String getSectorType() {
		return sector_type;
	}

	public void setSectorType(String type) {
		this.sector_type = type;
	}

	public String getSector_type() {
		return sector_type;
	}

	public void setSector_type(String sector_type) {
		this.sector_type = sector_type;
	}

	public Long getHall_id() {
		return hall_id;
	}

	public void setHall_id(Long hall_id) {
		this.hall_id = hall_id;
	}

}
