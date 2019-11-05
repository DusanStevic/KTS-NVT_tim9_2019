package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.model.Reservation;
import backend.repository.ReservationRepository;

@Service
public class ReservationService {
	@Autowired
	private ReservationRepository reservationRepository;

	public Reservation save(Reservation b) {
		return reservationRepository.save(b);
	}

	public Reservation findOne(Long id) {
		return reservationRepository.getOne(id);
	}

	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	public Page<Reservation> findAll(Pageable page) {
		return reservationRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		reservationRepository.deleteById(id);
	}

}
