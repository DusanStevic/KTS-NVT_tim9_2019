package backend.service;

import static backend.constants.AddressConstants.pageRequest;
import static backend.constants.LocationConstants.DB_DELETED_LOCATION_NAME;
import static backend.constants.LocationConstants.DB_LOCATION_COUNT;
import static backend.constants.LocationConstants.DB_LOCATION_ID;
import static backend.constants.LocationConstants.DB_LOCATION_ID_DELETED;
import static backend.constants.LocationConstants.DB_LOCATION_ID_TO_BE_DELETED;
import static backend.constants.LocationConstants.DB_LOCATION_ID_TO_BE_UPDATED;
import static backend.constants.LocationConstants.DB_LOCATION_NAME;
import static backend.constants.LocationConstants.FIRST_TIMESTAMP;
import static backend.constants.LocationConstants.LOCATION_ID_NON_EXISTENT;
import static backend.constants.LocationConstants.NEW_LOCATION;
import static backend.constants.LocationConstants.PAGE_SIZE;
import static backend.constants.LocationConstants.UPD_LOCATION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import backend.constants.LocationConstants;
import backend.converters.LocationConverter;
import backend.exceptions.BadRequestException;
import backend.exceptions.ResourceNotFoundException;
import backend.exceptions.SavingException;
import backend.model.Location;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class LocationServiceIntegrationTest {

	@Autowired
	LocationService locationService;

	@Autowired
	AddressService addressService;

	@Autowired
	LocationConverter locationConverter;

	@Test
	public void testFindAll() {
		List<Location> found = locationService.findAll();
		assertFalse(found.isEmpty());
		assertEquals(DB_LOCATION_COUNT, found.size());
	}

	@Test
	public void testFindAllPageable() {
		Page<Location> found = locationService.findAll(pageRequest);

		assertEquals(LocationConstants.PAGE_SIZE, found.getSize());
	}

	@Test
	public void testFindAllNotDeleted() {
		List<Location> found = locationService.findAllNotDeleted();
		assertFalse(found.isEmpty());
		for (Location loc : found) {
			assertEquals(FIRST_TIMESTAMP, loc.getDeleted());
		}
	}

	@Test
	public void testFindAllNotDeletedPageable() {
		PageRequest pageRequest = PageRequest.of(1, PAGE_SIZE); // druga strana
		Page<Location> found = locationService.findAllNotDeleted(pageRequest);
		for (Location loc : found) {
			assertEquals(FIRST_TIMESTAMP, loc.getDeleted());
		}
		assertEquals(PAGE_SIZE, found.getSize());
	}

	@Test
	public void testFindOne() throws ResourceNotFoundException {
		Location found = locationService.findOne(DB_LOCATION_ID);
		assertNotNull(found);
		assertTrue(DB_LOCATION_ID == found.getId());
		//assertEquals(DB_LOCATION_ADDRESS_ID, found.getAddress().getId());
		assertEquals(DB_LOCATION_NAME, found.getName());
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		@SuppressWarnings("unused")
		Location found = locationService.findOne(LOCATION_ID_NON_EXISTENT);

	}

	@Test
	public void testFindOneDeleted_shouldFindDeletedAddress() throws ResourceNotFoundException {
		Location found = locationService.findOne(DB_LOCATION_ID_DELETED);
		assertNotNull(found);
		assertTrue(DB_LOCATION_ID_DELETED == found.getId());
		//assertEquals(DB_DELETED_LOCATION_ADDRESS_ID, found.getAddress().getId());
		assertEquals(DB_DELETED_LOCATION_NAME, found.getName());
		assertNotEquals(FIRST_TIMESTAMP, found.getDeleted());
	}

	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		Location found = locationService.findOneNotDeleted(DB_LOCATION_ID);
		assertNotNull(found);
		assertTrue(DB_LOCATION_ID == found.getId());
		//assertEquals(DB_LOCATION_ADDRESS_ID, found.getAddress().getId());
		assertEquals(DB_LOCATION_NAME, found.getName());
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindDeletedLocation() throws ResourceNotFoundException {
		locationService.findOneNotDeleted(DB_LOCATION_ID_DELETED);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindNonExistingLocation() throws ResourceNotFoundException {
		locationService.findOneNotDeleted(LOCATION_ID_NON_EXISTENT);
	}

	@Test
	@Transactional
	public void testSave() throws SavingException, ResourceNotFoundException {
		//NEW_LOCATION.setAddress(addressService.findOneNotDeleted(NEW_LOCATION_ADDRESS_ID));

		int dbSizeBeforeAdd = locationService.findAll().size();
		Location found = locationService.save(NEW_LOCATION);

		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd + 1, locationService.findAll().size());
		assertEquals(NEW_LOCATION.getName(), found.getName());
		//assertEquals(NEW_LOCATION.getAddress().getId(), found.getAddress().getId());
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
		assertNotNull(NEW_LOCATION.getHalls());
		assertTrue((long) (dbSizeBeforeAdd + 1) == found.getId());
	}

	@Test(expected = SavingException.class)
	@Transactional
	@Ignore
	public void testSave_SavingException() throws ResourceNotFoundException, SavingException {

		//NEW_LOCATION.setAddress(addressService.findOneNotDeleted(DB_LOCATION_ADDRESS_ID));
		NEW_LOCATION.setAddress("Street 2 Novi Sad Serbia");
		Location found = locationService.save(NEW_LOCATION);
		
		System.out.println(found.toString());
	}

	@Test
	public void testDelete() throws ResourceNotFoundException, SavingException, BadRequestException {
		int db_size_before_delete = locationService.findAll().size();

		locationService.delete(DB_LOCATION_ID_TO_BE_DELETED, new Date());

		int db_size_after_delete = locationService.findAll().size();

		Location found = locationService.findOne(DB_LOCATION_ID_TO_BE_DELETED);
		assertEquals(db_size_before_delete, db_size_after_delete); // nije se promenio broj zbog logickog brisanja
		assertNotNull(found); // nije null zbog logickog brisanja
		assertNotEquals(FIRST_TIMESTAMP, found.getDeleted()); // samo se indikator deleted postavio na neki timestamp
																// koji nije first
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testDelete_NotFoundException() throws ResourceNotFoundException, SavingException, BadRequestException {
		locationService.delete(DB_LOCATION_ID_DELETED, new Date()); // lokacija je vec obrisana i nece biti pronadjena
	}

	@Test(expected = BadRequestException.class)
	public void testDelete_BadRequestException()
			throws ResourceNotFoundException, SavingException, BadRequestException {
		locationService.delete(DB_LOCATION_ID, new Date()); // postoje ticketi za lokaciju pa nmz da se obrise
	}

	@Test
	@Transactional
	public void testUpdate() throws ResourceNotFoundException, SavingException {
		// lokacija 2L koja je na adresi DB_DELETED_LOCATION_ADDRESS_ID = 4L je logicki
		// obrisana
		// pa zbog toga mogu da stavim neku drugu lokaciju na tu adresu
		UPD_LOCATION.setAddress("Street 3 Novi Sad Serbia");
		int dbSizeBeforeUpd = locationService.findAll().size();
		Location updated = locationService.update(DB_LOCATION_ID_TO_BE_UPDATED, UPD_LOCATION);
		assertNotNull(updated);

		Location found = locationService.findOneNotDeleted(DB_LOCATION_ID_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dbSizeBeforeUpd, locationService.findAll().size()); // ne bi trebalo da se promeni broj lokacija u
																			// bazi
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
		//assertEquals(UPD_LOCATION.getAddress().getId(), found.getAddress().getId());
		assertEquals(UPD_LOCATION.getName(), found.getName());
		assertEquals(DB_LOCATION_ID_TO_BE_UPDATED, found.getId());

	}

	@Test(expected = ResourceNotFoundException.class)
	@Transactional
	public void testUpdate_NotFoundException() throws ResourceNotFoundException, SavingException {
		/*
		 * nece pronaci lokaciju sa prosledjenim id-em pa nije potrebno slati stvarnu
		 * lokaciju dovoljno je samo null
		 */
		locationService.update(DB_LOCATION_ID_DELETED, null);
	}

	@Test(expected = DataIntegrityViolationException.class)
	@Ignore
	public void testUpdate_SavingException() throws ResourceNotFoundException, SavingException {
		// pokusaj izmene adrese lokacije na adresu na kojoj se nalazi neka druga
		// lokacija
		//ocekiva se da ne moze da sacuva jer postoji duplikat address_id + location.deleted
		//sto znaci da ne mogu postojati dve lokacije koje nisu obrisane na istoj adresi
		System.out.println("servis upd sav exc");
		//3L 3L
		UPD_LOCATION.setAddress("Street 2 Novi Sad Serbia");
		Location updated = locationService.update(DB_LOCATION_ID_TO_BE_UPDATED, UPD_LOCATION);
		System.out.println(updated.toString());
	}
}
