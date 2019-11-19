package backend.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.model.Address;
import backend.model.Hall;
import backend.model.Location;
import backend.repository.LocationRepository;

@Service
@Transactional
public class LocationService {
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	HallService hallService;
	
	@Autowired
	TicketService ticketService;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Location save(Location b) throws Exception{
		try {
			return locationRepository.save(b);
		}catch (DataIntegrityViolationException e) {
			// TODO: handle exception
			throw new Exception();
		}
		
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
	
	/*public ResponseEntity<String> delete(Long id) {
		if(!ticketService.findAllByLocation(id).isEmpty()) {
			return ResponseEntity.badRequest().body("Could not delete location");
		}
		Location loc = findOne(id);
		if(!loc.equals(null) && !loc.isDeleted()) {
			loc.setDeleted(true);
			for(Hall h : loc.getHalls()) {
				hallService.delete(h.getId());
			}
			save(loc);
			return ResponseEntity.ok().body("Successfully deleted");
		}else {
			return ResponseEntity.badRequest().body("Could not find requested location");
		}
	}*/
}
