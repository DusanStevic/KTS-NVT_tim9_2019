package backend.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("standing")
public class StandingSector extends Sector {
	private static final long serialVersionUID = -1933085622082987606L;

	@Column(name = "capacity", nullable = true)
	private int capacity;

	public StandingSector() {
		super();
		this.capacity = 0;
	}

	public StandingSector(Long id, String name, int capacity, Hall h) {
		super(id, name, h);
		this.capacity = capacity;
	}

	public StandingSector(Long id, String name, int capacity, Hall h, boolean deleted) {
		super(id, name, h, deleted);
		this.capacity = capacity;
	}
	
	public StandingSector(String name, int capacity) {
		super(name);
		this.capacity = capacity;
	}
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "StandingSector [" + super.toString() + "capacity=" + capacity
				+ "]";
	}

}
