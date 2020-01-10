package backend.repository;

import static backend.constants.AddressConstants.ADDRESS_ID_NON_EXISTENT;
import static backend.constants.AddressConstants.DB_ADDRESS_ID;
import static backend.constants.AddressConstants.DB_ADDRESS_ID_DELETED;
import static backend.constants.AddressConstants.DB_ADDRESS_STREET;
import static backend.constants.AddressConstants.PAGE_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import backend.model.Address;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AddressRepositoryIntegrationTest {
	
	@Autowired
	AddressRepository addressRepository;

	
	@Test
	public void testFindByIdAndDeleted() {
		Optional<Address> found = addressRepository.findByIdAndDeleted(DB_ADDRESS_ID, false);
		Address a = found.get();
		assertNotNull(a);
		assertFalse(a.isDeleted());
		assertTrue(DB_ADDRESS_ID == a.getId());
		assertEquals(DB_ADDRESS_STREET, a.getStreetName());
	}
	
	@Test
	public void testFindByIdAndDeleted_findsDeleted() {
		Optional<Address> found = addressRepository.findByIdAndDeleted(DB_ADDRESS_ID_DELETED, true);
		Address a = found.get();
		assertNotNull(a);
		assertTrue(a.isDeleted());
		assertTrue(DB_ADDRESS_ID_DELETED == a.getId());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NonExistent() {
		/*
		 * adresa sa datim id-em ne postoji
		 * tako da nije bitno sta prosledim za deleted
		 */
		Optional<Address> found = addressRepository.findByIdAndDeleted(ADDRESS_ID_NON_EXISTENT, false);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NotDeleted() {
		/*
		 * trazena adresa nije obrisana
		 * nece biti pronadjena jer trazim onu koja je obrisana
		 */
		Optional<Address> found = addressRepository.findByIdAndDeleted(DB_ADDRESS_ID, true);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_Deleted() {
		/*
		 * trazena adresa je obrisana
		 * nece biti pronadjena jer trazim onu koja nije obrisana
		 */
		Optional<Address> found = addressRepository.findByIdAndDeleted(DB_ADDRESS_ID_DELETED, false);
		assertNull(found.get());
	}
	
	
	@Test
	public void testFindAllByDeleted_False() {
		List<Address> found = addressRepository.findAllByDeleted(false); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Address a: found) {
			assertFalse(a.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeleted_True() {
		List<Address> found = addressRepository.findAllByDeleted(true); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Address a: found) {
			assertTrue(a.isDeleted());
		}
	}
	
	@Test
	public void testFindAllByDeletedPageable_False() {
		PageRequest pageRequest = PageRequest.of(0, 5); //prva strana
		Page<Address> found = addressRepository.findAllByDeleted(false, pageRequest);
		System.out.println("*********************************************************");
		for(Address a : found.getContent()) {
			System.out.println(a.getStreetName());
			assertFalse(a.isDeleted());
		}
		System.out.println("*********************************************************");
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	
	@Test
	public void testFindAllByDeletedPageable_True() {
		PageRequest pageRequest = PageRequest.of(1, PAGE_SIZE); //druga strana
		Page<Address> found = addressRepository.findAllByDeleted(true, pageRequest);
		assertNotNull(found);
		for(Address a: found) {
			System.out.println("*********************************************************");
			System.out.println(a.getStreetName());
			System.out.println("*********************************************************");
			assertTrue(a.isDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
}
