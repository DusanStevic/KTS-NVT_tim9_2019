package backend.repository;

import static backend.constants.LocationConstants.*;
import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
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
import backend.model.Location;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class LocationRepositoryIntegrationTest {

	@Autowired
	LocationRepository locationRepository;
	
	private static Timestamp deletedTimestamp;
	@Before
	public void setup() throws ParseException {
		String stringDate = "2019-11-26 01:00:00";
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stringDate);
		deletedTimestamp = new Timestamp(date.getTime());
	}
	@Test
	public void testFindByIdAndDeleted() {
		Optional<Location> found = locationRepository.findByIdAndDeleted(DB_LOCATION_ID, FIRST_TIMESTAMP);
		Location loc = found.get();
		assertNotNull(loc);
		assertEquals(FIRST_TIMESTAMP, loc.getDeleted()); //provera da nije obrisana lokacija
		assertTrue(DB_LOCATION_ID == loc.getId());
		assertEquals(Long.valueOf(DB_LOCATION_ADDRESS_ID), loc.getAddress().getId());
	}
	
	@Test
	public void testFindByIdAndDeleted_findsDeleted() throws ParseException {
		Optional<Location> found = locationRepository.findByIdAndDeleted(DB_LOCATION_ID_DELETED, deletedTimestamp);
		Location loc = found.get();
		assertNotNull(loc);
		assertNotEquals(FIRST_TIMESTAMP, loc.getDeleted());
		assertTrue(DB_LOCATION_ID_DELETED == loc.getId());
	}
	
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NonExistent() {
		/*
		 * lokacija sa datim id-em ne postoji
		 * tako da nije bitno sta prosledim za deleted
		 */
		Optional<Location> found = locationRepository.findByIdAndDeleted(LOCATION_ID_NON_EXISTENT, FIRST_TIMESTAMP);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_NotDeleted() {
		/*
		 * trazena lokacija nije obrisana
		 * nece biti pronadjena jer trazim onu koja je obrisana
		 */
		Optional<Location> found = locationRepository.findByIdAndDeleted(DB_LOCATION_ID, deletedTimestamp);
		assertNull(found.get());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testFindByIdAndDeleted_Deleted() {
		/*
		 * trazena lokacija je obrisana
		 * nece biti pronadjena jer trazim onu koja nije obrisana
		 */
		Optional<Location> found = locationRepository.findByIdAndDeleted(DB_LOCATION_ID_DELETED, FIRST_TIMESTAMP);
		assertNull(found.get());
	}
	
	
	@Test
	public void testFindAllByDeleted_False() {
		List<Location> found = locationRepository.findAllByDeleted(FIRST_TIMESTAMP); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Location loc: found) {
			assertEquals(FIRST_TIMESTAMP, loc.getDeleted());
		}
	}
	
	@Test 
	//ovo pronalazi samo lokaciju koja je za deleted ima deletedTimestamp a ne sve obrisane
	//ali nikad ne koristimo ovu funkciju da bismo pronasli sve obrisane lokacije 
	public void testFindAllByDeleted_True() {
		List<Location> found = locationRepository.findAllByDeleted(deletedTimestamp); 
		assertNotNull(found);
		assertFalse(found.isEmpty());
		for(Location loc: found) {
			assertEquals(deletedTimestamp, loc.getDeleted());
		}
	}
	
//	@Test
//	public void testFindAllByDeletedPageable_False() {
//		/*PageRequest pageRequest = PageRequest.of(1, PAGE_SIZE); //druga strana
//		Page<Address> found = addressRepository.findAllByDeleted(false, pageRequest);
//		assertNotNull(found);
//		for(Address a: found) {
//			System.out.println("*********************************************************");
//			System.out.println(a.getStreetName());
//			System.out.println("*********************************************************");
//			assertFalse(a.isDeleted());
//		}
//		
//		assertEquals(PAGE_SIZE, found.getSize());*/
//		/*
//		 * isto je kao u integr testu za service, samo sto se direktno poziva repository
//		 * medjutim, ne radi
//		 */
//		PageRequest pageRequest = PageRequest.of(1, 5); //druga strana
//		Page<Address> found = addressRepository.findAllByDeleted(false, pageRequest);
//		System.out.println("*********************************************************");
//		for(Address a : found.getContent()) {
//			System.out.println(a.getStreetName());
//			assertFalse(a.isDeleted());
//		}
//		System.out.println("*********************************************************");
//		assertEquals(PAGE_SIZE, found.getSize());
//	}
//	
//	
//	@Test
//	public void testFindAllByDeletedPageable_True() {
//		PageRequest pageRequest = PageRequest.of(1, PAGE_SIZE); //druga strana
//		Page<Address> found = addressRepository.findAllByDeleted(true, pageRequest);
//		assertNotNull(found);
//		for(Address a: found) {
//			System.out.println("*********************************************************");
//			System.out.println(a.getStreetName());
//			System.out.println("*********************************************************");
//			assertTrue(a.isDeleted());
//		}
//		assertEquals(PAGE_SIZE, found.getSize());
//	}
}
