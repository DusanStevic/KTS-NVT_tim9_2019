package backend.service;

import static backend.constants.AddressConstants.pageRequest;
import static backend.constants.LocationConstants.FIRST_TIMESTAMP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import backend.exceptions.BadRequestException;
import backend.exceptions.ResourceNotFoundException;
import backend.exceptions.SavingException;
import backend.model.Address;
import backend.model.Location;
import backend.model.Ticket;
import backend.repository.LocationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LocationServiceUnitTest {

	@Autowired
	LocationService locationService;
	
	@MockBean
	LocationRepository locationRepositoryMocked;
	
	@MockBean
	TicketService ticketServiceMocked;
	
	public static final Long locationId = 1L;
	public static final Long deletedLocationId = 2L;
	public static final Long nonExistentId = 666L;
	public static final Location location = new Location(locationId, "Naziv", "Opis", null, FIRST_TIMESTAMP, "adr", 0.0, 0.0);
	public static final Location deletedLocation = new Location(deletedLocationId, "Naziv deleted", "Opis deleted", null, null, "", 0, 0);
	public static final Location locationToBeDeleted = new Location(locationId, "Naziv", "Opis", null, FIRST_TIMESTAMP, "", 0, 0);
	@Before
	public void setup() {
		//Address address_deleted = new Address(deletedAddressId, "Street1", 223, "City", "Country", 22.2, 33.3);
		//address_deleted.setDeleted(true);

		List<Location> locations = new ArrayList<>();
		locations.add(location);
		locations.add(deletedLocation);

		Page<Location> locationsPage = new PageImpl<>(locations);

		when(locationRepositoryMocked.findAll()).thenReturn(locations);
		when(locationRepositoryMocked.findAll(pageRequest)).thenReturn(locationsPage);
		when(locationRepositoryMocked.findAllByDeleted(FIRST_TIMESTAMP)).thenReturn(locations);
		when(locationRepositoryMocked.findAllByDeleted(FIRST_TIMESTAMP, pageRequest)).thenReturn(locationsPage);
		when(locationRepositoryMocked.findById(locationId)).thenReturn(Optional.of(location));
		when(locationRepositoryMocked.findById(deletedLocationId)).thenReturn(Optional.of(deletedLocation));
	}

	@Test
	public void testFindAll() {
		List<Location> found = locationService.findAll();
		assertNotNull(found);
		verify(locationRepositoryMocked, times(1)).findAll();
	}

	@Test
	public void testFindAllPageable() {
		Page<Location> found = locationService.findAll(pageRequest);
		assertNotNull(found);
		verify(locationRepositoryMocked, times(1)).findAll(pageRequest);
	}

	@Test
	public void testFindAllNotDeleted() {
		List<Location> found = locationService.findAllNotDeleted();
		assertNotNull(found);
		verify(locationRepositoryMocked, times(1)).findAllByDeleted(FIRST_TIMESTAMP);
	}

	@Test
	public void testFindAllNotDeletedPageable() {
		Page<Location> found = locationService.findAllNotDeleted(pageRequest);
		assertNotNull(found);
		verify(locationRepositoryMocked, times(1)).findAllByDeleted(FIRST_TIMESTAMP, pageRequest);
	}

	@Test
	public void testFindOne() throws ResourceNotFoundException {
		Location found = locationService.findOne(locationId);
		assertNotNull(found);
		assertTrue(locationId == found.getId());
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
		assertEquals(location.getName(), found.getName());
		assertEquals(location.getDescription(), found.getDescription());
		verify(locationRepositoryMocked, times(1)).findById(locationId);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		locationService.findOne(nonExistentId);
	}

	@Test
	public void testFindOneDeleted_shouldFindDeletedLocation() throws ResourceNotFoundException {
		Location found = locationService.findOne(deletedLocationId);
		assertNotNull(found);
		assertTrue(deletedLocationId == found.getId());
		assertNotEquals(FIRST_TIMESTAMP, found.getDeleted());
		verify(locationRepositoryMocked, times(1)).findById(deletedLocationId);
	}

	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		when(locationRepositoryMocked.findByIdAndDeleted(locationId, FIRST_TIMESTAMP)).thenReturn(Optional.of(location));
		Location found = locationService.findOneNotDeleted(locationId);
		assertNotNull(found);
		assertTrue(locationId == found.getId());
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
		assertEquals(location.getName(), found.getName());
		assertEquals(location.getDescription(), found.getDescription());
		verify(locationRepositoryMocked, times(1)).findByIdAndDeleted(locationId, FIRST_TIMESTAMP);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_nonExistentLocation() throws ResourceNotFoundException {
		locationService.findOneNotDeleted(nonExistentId);
	}
	
	@Test
	public void testSave() throws SavingException {
		when(locationRepositoryMocked.save(location)).thenReturn(location);
		Location saved = locationService.save(location);
		assertNotNull(saved);
		assertEquals(location.getId(), saved.getId());
		assertEquals(location.getName(), saved.getName());
		verify(locationRepositoryMocked, times(1)).save(location);
	}
	
	@Test(expected = SavingException.class)
	public void testSave_SavingException() throws SavingException {
		when(locationRepositoryMocked.save(location)).thenThrow(new DataIntegrityViolationException("Could not save location"));
		locationService.save(location);
	}
	@Test
	public void testDelete() throws ResourceNotFoundException, SavingException, BadRequestException {
		when(locationRepositoryMocked.findByIdAndDeleted(locationId, FIRST_TIMESTAMP)).thenReturn(Optional.of(locationToBeDeleted));
		locationService.delete(locationId, new Date());
		verify(locationRepositoryMocked, times(1)).findByIdAndDeleted(locationId, FIRST_TIMESTAMP);
		verify(locationRepositoryMocked, times(1)).save(locationToBeDeleted);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDelete_NotFoundException() throws ResourceNotFoundException, SavingException, BadRequestException {
		locationService.delete(nonExistentId, new Date());
	}
	
	@Test(expected = SavingException.class)
	public void testDelete_SavingException() throws SavingException, BadRequestException, ResourceNotFoundException {
		when(locationRepositoryMocked.findByIdAndDeleted(locationId, FIRST_TIMESTAMP)).thenReturn(Optional.of(locationToBeDeleted));
		when(locationRepositoryMocked.save(locationToBeDeleted)).thenThrow(new DataIntegrityViolationException("Could not save location"));
		locationService.delete(locationId, new Date());
	}
	
	@Test(expected = BadRequestException.class)
	public void testDelete_BadRequestException() throws SavingException, BadRequestException, ResourceNotFoundException {
		List<Ticket> tickets = new ArrayList<>();
		tickets.add(new Ticket());
		Date date = new Date();
		when(ticketServiceMocked.findAllByLocationDate(locationId, date)).thenReturn(tickets);
		locationService.delete(locationId, date);
	}
	@Test
	public void testUpdate() throws ResourceNotFoundException, SavingException {
		when(locationRepositoryMocked.findByIdAndDeleted(locationId, FIRST_TIMESTAMP)).thenReturn(Optional.of(location));
		//Address adr = new Address();
		//adr.setId(1L);
		Location upd = new Location(locationId, "blaa", "mjau", null, null, "", 0, 0);
		when(locationRepositoryMocked.save(upd)).thenReturn(upd);
		Location updated = locationService.update(locationId, upd);
		
		assertNotNull(updated);
		assertTrue(locationId == updated.getId());
		assertEquals(upd.getName(), updated.getName());
		assertEquals(upd.getDescription(), updated.getDescription());
		//assertEquals(upd.getAddress().getId(), updated.getAddress().getId());
		verify(locationRepositoryMocked, times(1)).findByIdAndDeleted(locationId, FIRST_TIMESTAMP);
		verify(locationRepositoryMocked, times(1)).save(upd);
	}
	
	@Test(expected = SavingException.class)
	public void testUpdate_SavingException() throws SavingException, ResourceNotFoundException {
		when(locationRepositoryMocked.save(location)).thenThrow(new DataIntegrityViolationException("Could not save location"));
		when(locationRepositoryMocked.findByIdAndDeleted(locationId, FIRST_TIMESTAMP)).thenReturn(Optional.of(location));
		locationService.update(locationId, location);
	}
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdate_NotFoundException() throws ResourceNotFoundException, SavingException {
		locationService.update(nonExistentId, null);
	}
}
