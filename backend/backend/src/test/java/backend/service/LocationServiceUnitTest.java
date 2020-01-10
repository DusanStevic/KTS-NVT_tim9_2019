package backend.service;

import static backend.constants.AddressConstants.pageRequest;
import static backend.constants.LocationConstants.FIRST_TIMESTAMP;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import backend.exceptions.ResourceNotFoundException;
import backend.model.Address;
import backend.model.Location;
import backend.repository.LocationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LocationServiceUnitTest {

	@Autowired
	LocationService locationService;
	
	@MockBean
	LocationRepository locationRepositoryMocked;
	
	public static final Long locationId = 1L;
	public static final Long deletedLocationId = 2L;
	public static final Long nonExistentId = 666L;
	public static final Location location = new Location(locationId, "Naziv", "Opis", null, null, FIRST_TIMESTAMP);
	public static final Location deletedLocation = new Location(deletedLocationId, "Naziv deleted", "Opis deleted", null, null, null);
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
		Location found = locationService.findOne(nonExistentId);
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

//	@Test(expected = ResourceNotFoundException.class)
//	public void testFindOneNotDeleted_nonExistentAddress() throws ResourceNotFoundException {
//		Address found = addressService.findOneNotDeleted(nonExistentId);
//	}
//	
//	@Test
//	public void testSave() {
//		when(addressRepositoryMocked.save(address)).thenReturn(address);
//		Address saved = addressService.save(address);
//		assertNotNull(saved);
//		assertFalse(saved.isDeleted());
//		assertEquals(address.getCity(), saved.getCity());
//		assertEquals(address.getCountry(), saved.getCountry());
//		assertEquals(address.getStreetName(), saved.getStreetName());
//		assertEquals(address.getStreetNumber(), saved.getStreetNumber());
//		assertTrue(address.getLatitude() == saved.getLatitude());
//		assertTrue(address.getLongitude() == saved.getLongitude());
//		verify(addressRepositoryMocked, times(1)).save(address);
//	}
//	
//	@Test
//	public void testDelete() throws ResourceNotFoundException {
//		when(addressRepositoryMocked.findByIdAndDeleted(addressId, false)).thenReturn(Optional.of(address));
//		addressService.delete(addressId);
//		verify(addressRepositoryMocked, times(1)).findByIdAndDeleted(addressId, false);
//		verify(addressRepositoryMocked, times(1)).save(address);
//	}
//	
//	@Test(expected = ResourceNotFoundException.class)
//	public void testDeleteException() throws ResourceNotFoundException {
//		addressService.delete(nonExistentId);
//	}
//	
//	@Test
//	public void testUpdate() throws ResourceNotFoundException {
//		when(addressRepositoryMocked.findByIdAndDeleted(addressId, false)).thenReturn(Optional.of(address));
//		Address upd = new Address(addressId, "blaa", 666, "mjau", "lego kocke", 66.6, 45.6);
//		when(addressRepositoryMocked.save(upd)).thenReturn(upd);
//		Address updated = addressService.update(addressId, upd);
//		
//		assertNotNull(updated);
//		assertTrue(addressId == updated.getId());
//		assertFalse(updated.isDeleted());
//		assertEquals(upd.getCity(), updated.getCity());
//		assertEquals(upd.getCountry(), updated.getCountry());
//		assertEquals(upd.getStreetName(), updated.getStreetName());
//		assertEquals(upd.getStreetNumber(), updated.getStreetNumber());
//		assertTrue(upd.getLatitude() == updated.getLatitude());
//		assertTrue(upd.getLongitude() == updated.getLongitude());
//		verify(addressRepositoryMocked, times(1)).findByIdAndDeleted(addressId, false);
//		verify(addressRepositoryMocked, times(1)).save(upd);
//	}
//	
//	@Test(expected = ResourceNotFoundException.class)
//	public void testUpdateException() throws ResourceNotFoundException {
//		addressService.update(nonExistentId, null);
//	}
}
