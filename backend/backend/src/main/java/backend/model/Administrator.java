package backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DiscriminatorValue("admin")
public class Administrator extends User {
	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("admin")
	private Set<Event> events = new HashSet<>();

	public Administrator() {
		super();
	}

	public Administrator(Set<Event> events) {
		super();
		this.events = events;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

}
