package backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "halls")
public class Hall {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 60)
	private String name;

	/*@Column(name = "number_of_sectors", nullable = true)
	private int numberOfSectors;*/

	@OneToMany(mappedBy = "hall", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference("hall")
	private Set<Sector> sectors = new HashSet<>();

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JsonBackReference("halls")
	private Location location;
	
	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;

	public Hall() {
		super();
	}

	
	public Hall(Long id, String name, Set<Sector> sectors,
			Location location, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		//this.numberOfSectors = numberOfSectors;
		this.sectors = sectors;
		this.location = location;
		this.deleted = deleted;
	}

	public Hall(String name, Location location) {
		super();
		this.name = name;
		this.location = location;
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

	
	public Set<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(Set<Sector> sectors) {
		this.sectors = sectors;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Hall [id=" + id + ", name=" + name  + ", sectors=" + sectors + ", location="
				 + "]";
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
		Hall other = (Hall) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
