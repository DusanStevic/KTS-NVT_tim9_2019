package backend.converters;

import org.springframework.stereotype.Component;

import backend.dto.ReservationDTO;
import backend.model.Reservation;

@Component
public class ReservationConverter {

	public Reservation ReservationDTO2Reservation(ReservationDTO dto) {
		Reservation r = new Reservation();
		
		
		return r;
		
	}
}
