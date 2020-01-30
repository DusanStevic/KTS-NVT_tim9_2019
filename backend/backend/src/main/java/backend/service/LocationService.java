package backend.service;

import static backend.constants.Constants.FIRST_TIMESTAMP;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

	@Autowired
	AddressService addressService;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = SavingException.class)
	public Location save(Location b) throws SavingException {
		Location loc = null;
		try {
			System.out.println("try save loc");

			loc = locationRepository.save(b);
		} catch (DataIntegrityViolationException e) {
			System.out.println("**************");
			System.out.println("ako je ovde dosao onda je ok");
			System.out.println(e.getMessage());

			throw new SavingException(
					"Could not save location. Check if there is another location on the same address.");
		}
		return loc;

	}

	public Location findOne(Long id) throws ResourceNotFoundException {
		return locationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested location"));
	}

	@javax.transaction.Transactional
	public Location findOneNotDeleted(Long id) throws ResourceNotFoundException {
		return locationRepository.findByIdAndDeleted(id, FIRST_TIMESTAMP)
				.orElseThrow(() -> new ResourceNotFoundException("Could not find requested location"));
	}

	public List<Location> findAllNotDeleted() {
		return locationRepository.findAllByDeleted(FIRST_TIMESTAMP);
	}

	public Page<Location> findAllNotDeleted(Pageable page) {
		return locationRepository.findAllByDeleted(FIRST_TIMESTAMP, page);
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
	public void delete(Long id, Date date) throws SavingException, BadRequestException, ResourceNotFoundException {
		if (!ticketService.findAllByLocationDate(id, date).isEmpty()) {
			throw new BadRequestException("Could not delete location");
		}

		Location loc = findOneNotDeleted(id);
		loc.setDeleted(new Timestamp(System.currentTimeMillis()));
		if (loc.getHalls() != null) {
			for (Hall h : loc.getHalls()) {
				hallService.delete(h.getId());
			}
		}
		
		save(loc);

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = SavingException.class)
	public Location update(Long locationId, Location location) throws SavingException, ResourceNotFoundException {
		Location loc = findOneNotDeleted(locationId);

		loc.setName(location.getName());
		loc.setDescription(location.getDescription());
		loc.setAddress(location.getAddress());
		try {
			return save(loc);
		} catch (DataIntegrityViolationException e) {
			System.out.println("jebemu cole");
			throw new SavingException("upd sav  exc");
		}
	}
}
