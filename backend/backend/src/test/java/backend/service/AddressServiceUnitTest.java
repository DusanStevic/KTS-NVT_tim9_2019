package backend.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static backend.constants.AddressConstants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import backend.exceptions.ResourceNotFoundException;
import backend.model.Address;
import backend.repository.AddressRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AddressServiceUnitTest {

	@Autowired
	AddressService addressService;

	@MockBean
	AddressRepository addressRepositoryMocked;

	public static final Long addressId = 1L;
	public static final Long deletedAddressId = 2L;
	public static final Long nonExistentId = 666L;
	public static final Address address = new Address(addressId, "Street", 222, "City", "Country", 22.2, 33.3);

	@Before
	public void setup() {
		Address address_deleted = new Address(deletedAddressId, "Street1", 223, "City", "Country", 22.2, 33.3);
		address_deleted.setDeleted(true);

		List<Address> addresses = new ArrayList<>();
		addresses.add(address);
		addresses.add(address_deleted);

		Page<Address> addressesPage = new PageImpl<>(addresses);

		when(addressRepositoryMocked.findAll()).thenReturn(addresses);
		when(addressRepositoryMocked.findAll(pageRequest)).thenReturn(addressesPage);
		when(addressRepositoryMocked.findAllByDeleted(false)).thenReturn(addresses);
		when(addressRepositoryMocked.findAllByDeleted(false, pageRequest)).thenReturn(addressesPage);
		when(addressRepositoryMocked.findById(addressId)).thenReturn(Optional.of(address));
		when(addressRepositoryMocked.findById(deletedAddressId)).thenReturn(Optional.of(address_deleted));
	}

	@Test
	public void testFindAll() {
		List<Address> found = addressService.findAll();
		assertNotNull(found);
		verify(addressRepositoryMocked, times(1)).findAll();
	}

	@Test
	public void testFindAllPageable() {
		Page<Address> found = addressService.findAll(pageRequest);
		assertNotNull(found);
		verify(addressRepositoryMocked, times(1)).findAll(pageRequest);
	}

	@Test
	public void testFindAllNotDeleted() {
		List<Address> found = addressService.findAllNotDeleted();
		assertNotNull(found);
		verify(addressRepositoryMocked, times(1)).findAllByDeleted(false);
	}

	@Test
	public void testFindAllNotDeletedPageable() {
		Page<Address> found = addressService.findAllNotDeleted(pageRequest);
		assertNotNull(found);
		verify(addressRepositoryMocked, times(1)).findAllByDeleted(false, pageRequest);
	}

	@Test
	public void testFindOne() throws ResourceNotFoundException {
		Address found = addressService.findOne(addressId);
		assertNotNull(found);
		assertTrue(addressId == found.getId());
		assertFalse(found.isDeleted());
		assertEquals(address.getCity(), found.getCity());
		assertEquals(address.getCountry(), found.getCountry());
		assertEquals(address.getStreetName(), found.getStreetName());
		assertEquals(address.getStreetNumber(), found.getStreetNumber());
		assertTrue(address.getLatitude() == found.getLatitude());
		assertTrue(address.getLongitude() == found.getLongitude());
		verify(addressRepositoryMocked, times(1)).findById(addressId);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		Address found = addressService.findOne(nonExistentId);
	}

	@Test
	public void testFindOneDeleted_shouldFindDeletedAddress() throws ResourceNotFoundException {
		Address found = addressService.findOne(deletedAddressId);
		assertNotNull(found);
		assertTrue(deletedAddressId == found.getId());
		assertTrue(found.isDeleted());
		verify(addressRepositoryMocked, times(1)).findById(deletedAddressId);
	}

	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		when(addressRepositoryMocked.findByIdAndDeleted(addressId, false)).thenReturn(Optional.of(address));
		Address found = addressService.findOneNotDeleted(addressId);
		assertNotNull(found);
		assertTrue(addressId == found.getId());
		assertFalse(found.isDeleted());
		assertEquals(address.getCity(), found.getCity());
		assertEquals(address.getCountry(), found.getCountry());
		assertEquals(address.getStreetName(), found.getStreetName());
		assertEquals(address.getStreetNumber(), found.getStreetNumber());
		assertTrue(address.getLatitude() == found.getLatitude());
		assertTrue(address.getLongitude() == found.getLongitude());
		verify(addressRepositoryMocked, times(1)).findByIdAndDeleted(addressId, false);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_nonExistentAddress() throws ResourceNotFoundException {
		Address found = addressService.findOneNotDeleted(nonExistentId);
	}
	
	@Test
	public void testSave() {
		when(addressRepositoryMocked.save(address)).thenReturn(address);
		Address saved = addressService.save(address);
		assertNotNull(saved);
		assertFalse(saved.isDeleted());
		assertEquals(address.getCity(), saved.getCity());
		assertEquals(address.getCountry(), saved.getCountry());
		assertEquals(address.getStreetName(), saved.getStreetName());
		assertEquals(address.getStreetNumber(), saved.getStreetNumber());
		assertTrue(address.getLatitude() == saved.getLatitude());
		assertTrue(address.getLongitude() == saved.getLongitude());
		verify(addressRepositoryMocked, times(1)).save(address);
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		when(addressRepositoryMocked.findByIdAndDeleted(addressId, false)).thenReturn(Optional.of(address));
		addressService.delete(addressId);
		verify(addressRepositoryMocked, times(1)).findByIdAndDeleted(addressId, false);
		verify(addressRepositoryMocked, times(1)).save(address);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		addressService.delete(nonExistentId);
	}
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		when(addressRepositoryMocked.findByIdAndDeleted(addressId, false)).thenReturn(Optional.of(address));
		Address upd = new Address(addressId, "blaa", 666, "mjau", "lego kocke", 66.6, 45.6);
		when(addressRepositoryMocked.save(upd)).thenReturn(upd);
		Address updated = addressService.update(addressId, upd);
		
		assertNotNull(updated);
		assertTrue(addressId == updated.getId());
		assertFalse(updated.isDeleted());
		assertEquals(upd.getCity(), updated.getCity());
		assertEquals(upd.getCountry(), updated.getCountry());
		assertEquals(upd.getStreetName(), updated.getStreetName());
		assertEquals(upd.getStreetNumber(), updated.getStreetNumber());
		assertTrue(upd.getLatitude() == updated.getLatitude());
		assertTrue(upd.getLongitude() == updated.getLongitude());
		verify(addressRepositoryMocked, times(1)).findByIdAndDeleted(addressId, false);
		verify(addressRepositoryMocked, times(1)).save(upd);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		addressService.update(nonExistentId, null);
	}
}
