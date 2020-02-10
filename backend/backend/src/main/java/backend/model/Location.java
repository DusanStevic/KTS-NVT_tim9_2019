package backend.model;


import static backend.constants.Constants.FIRST_TIMESTAMP;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "locations" )/* izbaci error
, uniqueConstraints = {
		@UniqueConstraint(columnNames = {"address", "deleted"}) })*/
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", nullable = false, length = 80)
	private String name;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "latitude", nullable = false)
	private double latitude;
	
	@Column(name = "longitude", nullable = false)
	private double longitude;
	
	@OneToMany(mappedBy = "location", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference("halls")
	private Set<Hall> halls = new HashSet<>();

//	@JoinColumn(name = "address_id")
//	@OneToOne(cascade = CascadeType.ALL)
//	private Address address;

	@Column(name = "deleted", nullable = false)
	private Timestamp deleted = FIRST_TIMESTAMP;
	
	public Location() {
		super();
	}

	public Location(Long id, String name, String description, Set<Hall> halls, Timestamp deleted,
			String address, double latitude, double longitude) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.halls = halls;
		this.address = address;
		this.deleted = deleted;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Location(String name, String description, Timestamp deleted, 
			String address, double latitude, double longitude) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
		this.deleted = deleted;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Hall> getHalls() {
		return halls;
	}

	public void setHalls(Set<Hall> halls) {
		this.halls = halls;
	}

//	public Address getAddress() {
//		return address;
//	}
//
//	public void setAddress(Address address) {
//		this.address = address;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDeleted() {
		return deleted;
	}

	public void setDeleted(Timestamp deleted) {
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
		Location other = (Location) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + ", description=" + description + ", address=" + address
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", halls=" + halls + ", deleted=" + deleted
				+ "]";
	}

	
}
