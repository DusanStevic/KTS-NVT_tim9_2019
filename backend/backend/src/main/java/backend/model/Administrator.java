package backend.model;

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
	private Event events;

	public Administrator() {
		super();
	}

	public Administrator(Event events) {
		super();
		this.events = events;
	}

	public Event getEvents() {
		return events;
	}

	public void setEvents(Event events) {
		this.events = events;
	}

}
