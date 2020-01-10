package backend.service;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import backend.exceptions.ResourceNotFoundException;
import backend.model.Address;
import backend.repository.AddressRepository;

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
		Page<Address> found = addressService.findAll(pageRequest);
		for(Address a : found) {
			System.out.println(a.getStreetName());
			
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	@Test
	public void testFindAllNotDeleted() {
		List<Address> found = addressService.findAllNotDeleted();
		assertFalse(found.isEmpty());
		for(Address a : found) {
			assertFalse(a.isDeleted());
		}
	}
	
	@Test
	public void testFindAllNotDeletedPageable() {
		System.out.println("Pageable");
		PageRequest pageRequest = PageRequest.of(1, PAGE_SIZE); //druga strana
		Page<Address> found = addressService.findAllNotDeleted(pageRequest);
		for(Address a : found) {
			System.out.println(a.getStreetName());
			assertFalse(a.isDeleted());
		}
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
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		@SuppressWarnings("unused")
		Address found = addressService.findOne(ADDRESS_ID_NON_EXISTENT);
		
	}
	
	@Test
	public void testFindOneDeleted_shouldFindDeletedAddress() throws ResourceNotFoundException {
		Address found = addressService.findOne(DB_ADDRESS_ID_DELETED);
		assertNotNull(found);
		assertTrue(DB_ADDRESS_ID_DELETED == found.getId());
		assertEquals(DB_ADDRESS_DELETED_STREET, found.getStreetName());
		assertEquals(DB_ADDRESS_STREET_NUM, found.getStreetNumber());
		assertEquals(DB_ADDRESS_CITY, found.getCity());
		assertEquals(DB_ADDRESS_COUNTRY, found.getCountry());
		assertTrue(found.isDeleted());
		assertTrue(DB_ADDRESS_LAT == found.getLatitude());
		assertTrue(DB_ADDRESS_LONG == found.getLongitude());
	}
	
	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		Address found = addressService.findOneNotDeleted(DB_ADDRESS_ID);
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
	public void testFindOneNotDeleted_shouldNotFindDeletedAddress() throws ResourceNotFoundException {
		Address found = addressService.findOneNotDeleted(DB_ADDRESS_ID_DELETED);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindNonExistingAddress() throws ResourceNotFoundException {
		Address found = addressService.findOneNotDeleted(ADDRESS_ID_NON_EXISTENT);
	}
	
	@Test
	//@Transactional
    //@Rollback(true)
	public void testSave() {
		/*Address a = new Address();
		a.setStreetName(NEW_ADDRESS_STREET);
		a.setStreetNumber(NEW_ADDRESS_STREET_NUM);
		a.setDeleted(NEW_ADDRESS_DELETED);
		a.setCity(NEW_ADDRESS_CITY);
		a.setCountry(NEW_ADDRESS_COUNTRY);
		a.setLatitude(NEW_ADDRESS_LAT);
		a.setLongitude(NEW_ADDRESS_LONG);*/
		
		int dbSizeBeforeAdd = addressService.findAll().size();
		Address found = addressService.save(NEW_ADDRESS);
		
		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd+1, addressService.findAll().size());
		assertEquals(NEW_ADDRESS.getStreetName(), found.getStreetName());
		assertEquals(NEW_ADDRESS.getStreetNumber(), found.getStreetNumber());
		assertFalse(found.isDeleted());
		assertEquals(NEW_ADDRESS.getCity(), found.getCity());
		assertEquals(NEW_ADDRESS.getCountry(), found.getCountry());
		assertTrue(NEW_ADDRESS.getLatitude() == found.getLatitude());
		assertTrue(NEW_ADDRESS.getLongitude() == found.getLongitude());
		assertTrue((long) (dbSizeBeforeAdd+1) == found.getId());
		
		/*
		 * jer rollback ne radi, privremeno resenje, lose jer ako pukne neki assert, nece se izvrsiti brisanje
		 */
		//addressRepository.deleteById(found.getId()); //ne obrise ako je ukljucena transakcija i rollback
		
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
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		int db_size_before_delete = addressService.findAll().size();
		
		addressService.delete(DB_ADDRESS_ID_TO_BE_DELETED);
		
		int db_size_after_delete = addressService.findAll().size();
		
		Address found = addressService.findOne(DB_ADDRESS_ID_TO_BE_DELETED);
		assertEquals(db_size_before_delete, db_size_after_delete); //nije se promenio broj zbog logickog brisanja
		assertNotNull(found); //nije null zbog logickog brisanja
		assertTrue(found.isDeleted()); //samo se indikator deleted postavio na true
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		addressService.delete(DB_ADDRESS_ID_DELETED); //adresa je vec obrisana i nece biti pronadjena
	}
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		int dbSizeBeforeUpd = addressService.findAll().size();
		Address updated = addressService.update(DB_ADDRESS_ID_TO_BE_UPDATED, UPD_ADDRESS);
		assertNotNull(updated);
		
		Address found = addressService.findOneNotDeleted(DB_ADDRESS_ID_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dbSizeBeforeUpd, addressService.findAll().size());  //ne bi trebalo da se promeni broj adresa u bazi
		assertEquals(UPD_ADDRESS.getStreetName(), found.getStreetName());
		assertEquals(UPD_ADDRESS.getStreetNumber(), found.getStreetNumber());
		assertFalse(found.isDeleted());
		assertEquals(UPD_ADDRESS.getCity(), found.getCity());
		assertEquals(UPD_ADDRESS.getCountry(), found.getCountry());
		assertTrue(UPD_ADDRESS.getLatitude() == found.getLatitude());
		assertTrue(UPD_ADDRESS.getLongitude() == found.getLongitude());
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		/*
		 * nece pronaci adresu sa prosledjenim id-em pa nije potrebno slati stvarnu adresu
		 * dovoljno je samo null
		 */
		addressService.update(DB_ADDRESS_ID_DELETED, null); 
	}
	
}
