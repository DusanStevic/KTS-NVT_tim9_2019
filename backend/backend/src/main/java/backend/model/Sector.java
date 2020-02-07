package backend.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Table(name = "sectors")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "sector_type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = SittingSector.class, name = "sitting"),
		@Type(value = StandingSector.class, name = "standing") })
public abstract class Sector implements Serializable {
	private static final long serialVersionUID = -4864142627042418170L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	// sector must have a unique name
	@Column(name = "name", nullable = false, length = 30)
	private String name;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JsonBackReference("hall")
	private Hall hall;

	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;

	public Sector() {
		super();
	}

	public Sector(Long id, String name, Hall hall) {
		super();
		this.id = id;
		this.name = name;
		this.hall = hall;
	}

	public Sector(Long id, String name, Hall hall, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.hall = hall;
		this.deleted = deleted;
	}
	
	public Sector(String name) {
		this.name = name;
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

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	@Override
	public String toString() {
		return "Sector [id=" + id + ", name=" + name + "]";
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sector other = (Sector) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
