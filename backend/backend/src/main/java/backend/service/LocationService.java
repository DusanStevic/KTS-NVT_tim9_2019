package backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.model.Location;
import backend.repository.LocationRepository;

@Service
public class LocationService {
	@Autowired
	private LocationRepository locationRepository;

	public Location save(Location b) {
		return locationRepository.save(b);
	}

	public Location findOne(Long id) {
		return locationRepository.getOne(id);
	}

	public List<Location> findAll() {
		return locationRepository.findAll();
	}

	public Page<Location> findAll(Pageable page) {
		return locationRepository.findAll(page);
	}

	@Transactional
	public void remove(Long id) {
		locationRepository.deleteById(id);
	}
}
