package backend.service;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import backend.exceptions.ResourceNotFoundException;
import backend.model.Address;
import static backend.constants.AddressConstants.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AddressServiceIntegrationTest {

	@Autowired
	AddressService addressService;
	
	@Test
	public void testFindAll() {
		List<Address> found = addressService.findAll();
		assertFalse(found.isEmpty());
		assertEquals(DB_ADDRESS_COUNT, found.size());
	}
	
	@Test
	public void testFindAllPageable() {
		PageRequest pageRequest = PageRequest.of(1, PAGE_SIZE); //druga strana
		Page<Address> found = addressService.findAll(pageRequest);
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindOne() throws ResourceNotFoundException {
		Address found = addressService.findOne(DB_ADDRESS_ID);
		assertNotNull(found);
		assertTrue(DB_ADDRESS_ID == found.getId());
		assertEquals(DB_ADDRESS_STREET, found.getStreetName());
		assertEquals(DB_ADDRESS_STREET_NUM, found.getStreetNumber());
		assertEquals(DB_ADDRESS_CITY, found.getCity());
		assertEquals(DB_ADDRESS_COUNTRY, found.getCountry());
		assertFalse(found.isDeleted());
		assertTrue(DB_ADDRESS_LAT == found.getLatitude());
		assertTrue(DB_ADDRESS_LONG == found.getLongitude());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotFound() throws ResourceNotFoundException {
		Address found = addressService.findOne(DB_ADDRESS_ID_NON_EXISTENT);
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testSave() {
		Address a = new Address();
		a.setStreetName(NEW_ADDRESS_STREET);
		a.setStreetNumber(NEW_ADDRESS_STREET_NUM);
		a.setDeleted(NEW_ADDRESS_DELETED);
		a.setCity(NEW_ADDRESS_CITY);
		a.setCountry(NEW_ADDRESS_COUNTRY);
		a.setLatitude(NEW_ADDRESS_LAT);
		a.setLongitude(NEW_ADDRESS_LONG);
		
		int dbSizeBeforeAdd = addressService.findAll().size();
		Address found = addressService.save(a);
		
		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd+1, addressService.findAll().size());
		assertEquals(NEW_ADDRESS_STREET, found.getStreetName());
		assertEquals(NEW_ADDRESS_STREET_NUM, found.getStreetNumber());
		assertFalse(found.isDeleted());
		assertEquals(NEW_ADDRESS_CITY, found.getCity());
		assertEquals(NEW_ADDRESS_COUNTRY, found.getCountry());
		assertTrue(NEW_ADDRESS_LAT == found.getLatitude());
		assertTrue(NEW_ADDRESS_LONG == found.getLongitude());
		assertTrue((long) (DB_ADDRESS_COUNT+1) == found.getId());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	@Ignore
	/*
	 * prethodna test metoda nije radila rollback
	 */
	public void testFindOne1() throws ResourceNotFoundException {
		Address found = addressService.findOne((long) (DB_ADDRESS_COUNT+1));
		System.out.println(found.getStreetName());
	}
}
