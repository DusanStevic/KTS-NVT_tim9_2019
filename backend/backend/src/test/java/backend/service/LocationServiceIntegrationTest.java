package backend.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static backend.constants.LocationConstants.*;
import static backend.constants.AddressConstants.*;
import static backend.constants.AddressConstants.PAGE_SIZE;
import static backend.constants.AddressConstants.pageRequest;
import static backend.constants.LocationConstants.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;

import backend.converters.LocationConverter;
import backend.exceptions.BadRequestException;
import backend.exceptions.ResourceNotFoundException;
import backend.exceptions.SavingException;
import backend.model.Address;
import backend.model.Hall;
import backend.model.Location;
import backend.repository.AddressRepository;

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

		assertEquals(PAGE_SIZE, found.getSize());
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
		assertEquals(DB_LOCATION_ADDRESS_ID, found.getAddress().getId());
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
		assertEquals(DB_DELETED_LOCATION_ADDRESS_ID, found.getAddress().getId());
		assertEquals(DB_DELETED_LOCATION_NAME, found.getName());
		assertNotEquals(FIRST_TIMESTAMP, found.getDeleted());
	}

	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		Location found = locationService.findOneNotDeleted(DB_LOCATION_ID);
		assertNotNull(found);
		assertTrue(DB_LOCATION_ID == found.getId());
		assertEquals(DB_LOCATION_ADDRESS_ID, found.getAddress().getId());
		assertEquals(DB_LOCATION_NAME, found.getName());
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindDeletedLocation() throws ResourceNotFoundException {
		Location found = locationService.findOneNotDeleted(DB_LOCATION_ID_DELETED);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_shouldNotFindNonExistingLocation() throws ResourceNotFoundException {
		Location found = locationService.findOneNotDeleted(LOCATION_ID_NON_EXISTENT);
	}

	@Test
	@Transactional
	public void testSave() throws SavingException, ResourceNotFoundException {
		NEW_LOCATION.setAddress(addressService.findOneNotDeleted(NEW_LOCATION_ADDRESS_ID));

		int dbSizeBeforeAdd = locationService.findAll().size();
		Location found = locationService.save(NEW_LOCATION);

		assertNotNull(found);
		assertEquals(dbSizeBeforeAdd + 1, locationService.findAll().size());
		assertEquals(NEW_LOCATION.getName(), found.getName());
		assertEquals(NEW_LOCATION.getAddress().getId(), found.getAddress().getId());
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
		assertNotNull(NEW_LOCATION.getHalls());
		assertTrue((long) (dbSizeBeforeAdd + 1) == found.getId());
	}

	@Test(expected = SavingException.class)
	@Transactional
	public void testSave_SavingException() throws ResourceNotFoundException, SavingException {

		NEW_LOCATION.setAddress(addressService.findOneNotDeleted(DB_LOCATION_ADDRESS_ID));
		Location found = locationService.save(NEW_LOCATION);
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
	public void testUpdate() throws ResourceNotFoundException, SavingException {
		// lokacija 2L koja je na adresi DB_DELETED_LOCATION_ADDRESS_ID = 4L je logicki
		// obrisana
		// pa zbog toga mogu da stavim neku drugu lokaciju na tu adresu
		UPD_LOCATION.setAddress(addressService.findOneNotDeleted(DB_DELETED_LOCATION_ADDRESS_ID));
		int dbSizeBeforeUpd = locationService.findAll().size();
		Location updated = locationService.update(DB_LOCATION_ID_TO_BE_UPDATED, UPD_LOCATION);
		assertNotNull(updated);

		Location found = locationService.findOneNotDeleted(DB_LOCATION_ID_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(dbSizeBeforeUpd, locationService.findAll().size()); // ne bi trebalo da se promeni broj lokacija u
																			// bazi
		assertEquals(FIRST_TIMESTAMP, found.getDeleted());
		assertEquals(UPD_LOCATION.getAddress().getId(), found.getAddress().getId());
		assertEquals(UPD_LOCATION.getName(), found.getName());
		assertEquals(DB_LOCATION_ID_TO_BE_UPDATED, found.getId());

	}

	@Test(expected = ResourceNotFoundException.class)
	public void testUpdate_NotFoundException() throws ResourceNotFoundException, SavingException {
		/*
		 * nece pronaci lokaciju sa prosledjenim id-em pa nije potrebno slati stvarnu
		 * lokaciju dovoljno je samo null
		 */
		locationService.update(DB_LOCATION_ID_DELETED, null);
	}

	@Test
	public void testUpdate_SavingException() throws ResourceNotFoundException, SavingException {
		// pokusaj izmene adrese lokacije na adresu na kojojs se nalazi neka druga
		// lokacija
		UPD_LOCATION.setAddress(addressService.findOneNotDeleted(DB_LOCATION_ADDRESS_ID));
		Location updated = locationService.update(DB_LOCATION_ID_TO_BE_UPDATED, UPD_LOCATION);
	}
}
