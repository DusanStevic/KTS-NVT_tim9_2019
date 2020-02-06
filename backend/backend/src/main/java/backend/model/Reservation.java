package backend.model;

import java.util.Date;
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
@Table(name = "reservations")
public class Reservation {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "purchased", nullable = false)
	private boolean purchased;
	@Column(name = "reservationDate", nullable = false)
	private Date reservationDate;

	@OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference("ticketsForReservation")
	private Set<Ticket> tickets = new HashSet<>();

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference("buyer")
	private RegisteredUser buyer;


	@Column(name = "canceled", nullable = false)
	private boolean canceled = false;
	

	public Reservation() {
		super();
	}

	public Reservation(Long id, boolean purchased, Date reservationDate,
			Set<Ticket> tickets, RegisteredUser buyer) {
		super();
		this.id = id;
		this.purchased = purchased;
		this.reservationDate = reservationDate;
		this.tickets = tickets;
		this.buyer = buyer;
	}
	
	public Reservation(Long id, boolean purchased, Date reservationDate, Set<Ticket> tickets, RegisteredUser buyer,
			boolean canceled) {
		super();
		this.id = id;
		this.purchased = purchased;
		this.reservationDate = reservationDate;
		this.tickets = tickets;
		this.buyer = buyer;
		this.canceled = canceled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isPurchased() {
		return purchased;
	}

	public void setPurchased(boolean purchased) {
		this.purchased = purchased;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	public RegisteredUser getBuyer() {
		return buyer;
	}

	public void setBuyer(RegisteredUser buyer) {
		this.buyer = buyer;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", purchased=" + purchased
				+ ", reservationDate=" + reservationDate + ", tickets="
				+ tickets + ", buyer=" + buyer + "]";
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	


}
