package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.model.Hall;
import backend.repository.HallRepository;

@Service
public class HallService {

	@Autowired
	private HallRepository hallRepository;

	public Hall save(Hall b) {
		return hallRepository.save(b);
	}

	public Hall findOne(Long id) {
		return hallRepository.getOne(id);
	}

	public List<Hall> findAll() {
		return hallRepository.findAll();
	}

	public Page<Hall> findAll(Pageable page) {
		return hallRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		hallRepository.deleteById(id);
	}
}
