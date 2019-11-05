package backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "locations", uniqueConstraints = {
		@UniqueConstraint(columnNames = "id"),
		@UniqueConstraint(columnNames = "name") })
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", unique = true, nullable = false, length = 80)
	private String name;

	@Column(name = "description", nullable = true)
	private String description;

	@OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("location")
	@JsonBackReference
	private Set<Hall> halls = new HashSet<>();

	@JoinColumn(name = "address_id", unique = true)
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	public Location() {
		super();
	}

	public Location(Long id, String name, String description, Set<Hall> halls,
			Address address) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.halls = halls;
		this.address = address;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

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

	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + ", description="
				+ description + ", halls=" + halls.toString() + ", address="
				+ address.toString() + "]";
	}

}
