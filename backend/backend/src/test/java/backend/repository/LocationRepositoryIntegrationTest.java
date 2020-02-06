package backend.repository;

import static backend.constants.LocationConstants.DB_LOCATION_ADDRESS_ID;
import static backend.constants.LocationConstants.DB_LOCATION_ID;
import static backend.constants.LocationConstants.DB_LOCATION_ID_DELETED;
import static backend.constants.LocationConstants.FIRST_TIMESTAMP;
import static backend.constants.LocationConstants.LOCATION_ID_NON_EXISTENT;
import static backend.constants.LocationConstants.PAGE_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
		//assertEquals(Long.valueOf(DB_LOCATION_ADDRESS_ID), loc.getAddress().getId());
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
	
	@Test
	public void testFindAllByDeletedPageable_False() {
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<Location> found = locationRepository.findAllByDeleted(FIRST_TIMESTAMP, pageRequest);
		assertNotNull(found);
		assertFalse(found.isEmpty());
		System.out.println("Deleted false");
		for(Location loc: found) {
			System.out.println(loc.getName());
			assertEquals(FIRST_TIMESTAMP, loc.getDeleted());
		}
		
		assertEquals(PAGE_SIZE, found.getSize());
	}
	
	
	@Test
	public void testFindAllByDeletedPageable_True() {
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE); //prva strana
		Page<Location> found = locationRepository.findAllByDeleted(deletedTimestamp, pageRequest);
		assertNotNull(found);
		System.out.println("Deleted true");
		for(Location loc: found) {
			System.out.println(loc.getName());
			assertEquals(deletedTimestamp, loc.getDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}
}
