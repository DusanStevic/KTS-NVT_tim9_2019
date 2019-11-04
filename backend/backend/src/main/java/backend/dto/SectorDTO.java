package backend.dto;

import backend.model.SittingSector;
import backend.model.StandingSector;

public class SectorDTO {
	private Long id;
	private String name;
	private int capacity;
	private int numRows;
	private int numCols;
	// Type of sector -> 'sitting' or 'standing'
	private String type;

	public SectorDTO() {
		super();
	}
	
	//fast convert from class to DTO
	public SectorDTO(SittingSector s) {
		this.id = s.getId();
		this.name = s.getName();
		this.numRows = s.getNumRows();
		this.numCols = s.getNumCols();
		this.type = "sitting";
	}

	//fast convert from class to DTO
	public SectorDTO(StandingSector s) {
		this.id = s.getId();
		this.name = s.getName();
		this.capacity = s.getCapacity();
		this.type = "standing";
	}

	public SectorDTO(Long id, String name, int capacity, int numRows,
			int numCols, String type) {
		super();
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.numRows = numRows;
		this.numCols = numCols;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
