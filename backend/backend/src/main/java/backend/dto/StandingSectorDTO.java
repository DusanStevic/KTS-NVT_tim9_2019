package backend.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class StandingSectorDTO extends SectorDTO {

	@NotNull(message = "Capacity is mandatory")
	@Min(value = 1, message = "Invalid capacity")
	private int capacity;

	public StandingSectorDTO(
			@NotNull(message = "Sector name is mandatory") @Length(min = 1, message = "Sector name is mandatory") String name,
			@NotNull(message = "Capacity is mandatory") @Min(value = 1, message = "Invalid capacity") int capacity) {
		super(name);
		this.capacity = capacity;
	}

	public StandingSectorDTO() {
		super();
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
