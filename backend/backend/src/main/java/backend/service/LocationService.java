package backend.service;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.exceptions.BadRequestException;
import backend.exceptions.DeletingException;
import backend.exceptions.ResourceNotFoundException;
import backend.exceptions.SavingException;
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
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = SavingException.class)
	public Location save(Location b) throws SavingException{
		try {
			return locationRepository.save(b);
		}catch (DataIntegrityViolationException e) {
			throw new SavingException("Could not save location. Check if there is another location on the same address.");
		}
		
	}
	public Location findOne(Long id) {
		return locationRepository.findById(id).orElse(null);
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

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DeletingException.class)
	public void delete(Long id) throws SavingException, BadRequestException, ResourceNotFoundException{
		if (!ticketService.findAllByLocationDate(id, new Date()).isEmpty()) {
			throw new BadRequestException("Could not delete location");
		}
		Location loc = findOne(id);
		if (loc != null && loc.getDeleted().equals(new Timestamp(0L))) {
			loc.setDeleted(new Timestamp(System.currentTimeMillis()));
			for (Hall h : loc.getHalls()) {
				hallService.delete(h.getId());
			}
			save(loc);
			
		} else {
			throw new ResourceNotFoundException("Could not find requested location");
		}

	}

	}


