package backend.controller;

import static backend.constants.SectorConstants.*;
import static backend.constants.HallConstants.HALL_ID_NON_EXISTENT;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import backend.dto.HallDTO;
import backend.dto.HallUpdateDTO;
import backend.dto.SectorDTO;
import backend.dto.SittingSectorDTO;
import backend.dto.StandingSectorDTO;
import backend.exceptions.ResourceNotFoundException;
import backend.model.Hall;
import backend.model.Sector;
import backend.model.SittingSector;
import backend.model.StandingSector;
import backend.model.UserTokenState;
import backend.security.auth.JwtAuthenticationRequest;
import backend.service.HallService;
import backend.service.SectorService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SectorControllerIntegrationTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	SectorService sectorService;

	@Autowired
	HallService hallService;

	private String accessToken;
	private HttpHeaders headers = new HttpHeaders();

	@Before
	public void login() {
		ResponseEntity<String> login = restTemplate.postForEntity("/auth/login",
				new JwtAuthenticationRequest("admin", "admin"), String.class);
		accessToken = login.getBody();
		headers.add("Authorization", "Bearer " + accessToken);
	}

	@Test
	public void testGetAllSectors() {
		ResponseEntity<Sector[]> responseEntity = restTemplate.getForEntity("/api/sector", Sector[].class);
		Sector[] sectors = responseEntity.getBody();
		StandingSector s0 = (StandingSector) sectors[0];
		SittingSector s2 = (SittingSector) sectors[2];
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(sectors);
		assertNotEquals(0, sectors.length);
		//assertEquals(8, sectors.length);
		
		assertEquals(DB_STAND_SECTOR_ID, s0.getId());
		assertEquals(DB_STAND_SECTOR_NAME, s0.getName());
		assertEquals(DB_STAND_SECTOR_CAPACITY, s0.getCapacity());
		
		assertEquals(DB_STAND_SECTOR_TO_BE_UPDATED, sectors[1].getId());
		
		assertEquals(DB_SIT_SECTOR_ID, s2.getId());
		assertEquals(DB_SIT_SECTOR_NAME, s2.getName());
		assertEquals(DB_SIT_SECTOR_COLS, s2.getNumCols());
		assertEquals(DB_SIT_SECTOR_ROWS, s2.getNumRows());
		
		assertEquals(DB_SIT_SECTOR_TO_BE_UPDATED, sectors[3].getId());
	}

	@Test
	public void testGetSector() {
		ResponseEntity<StandingSector> responseEntity_stand = restTemplate
				.getForEntity("/api/sector/" + DB_STAND_SECTOR_ID, StandingSector.class);
		StandingSector found_stand = responseEntity_stand.getBody();
		assertEquals(HttpStatus.OK, responseEntity_stand.getStatusCode());
		assertNotNull(found_stand);
		assertEquals(DB_STAND_SECTOR_ID, found_stand.getId());
		assertEquals(DB_STAND_SECTOR_NAME, found_stand.getName());
		assertEquals(DB_STAND_SECTOR_CAPACITY, found_stand.getCapacity());
		assertEquals(DB_SECTOR_HALL_ID, found_stand.getHall().getId());

		ResponseEntity<SittingSector> responseEntity_sit = restTemplate.getForEntity("/api/sector/" + DB_SIT_SECTOR_ID,
				SittingSector.class);
		SittingSector found_sit = responseEntity_sit.getBody();
		assertEquals(HttpStatus.OK, responseEntity_sit.getStatusCode());
		assertNotNull(found_sit);
		assertEquals(DB_SIT_SECTOR_ID, found_sit.getId());
		assertEquals(DB_SIT_SECTOR_NAME, found_sit.getName());
		assertEquals(DB_SIT_SECTOR_ROWS, found_sit.getNumRows());
		assertEquals(DB_SIT_SECTOR_COLS, found_sit.getNumCols());
		assertEquals(DB_SECTOR_HALL_ID, found_sit.getHall().getId());
	}

	@Test
	public void testGetSectorNonExistent() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/sector/" + SECTOR_ID_NON_EXISTENT,
				String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Could not find requested sector"));
	}

	@Test
	public void testDelete() throws ResourceNotFoundException {
		int size = sectorService.findAllNotDeleted().size();

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/sector/" + DB_STAND_SECTOR_TO_BE_DELETED2,
				HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);

		assertEquals(size - 1, sectorService.findAllNotDeleted().size());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Successfully deleted sector", responseEntity.getBody());

		Sector deleted = sectorService.findOne(DB_STAND_SECTOR_TO_BE_DELETED2);
		assertEquals(DB_STAND_SECTOR_TO_BE_DELETED2, deleted.getId());
		assertTrue(deleted.isDeleted());
	}

	@Test
	public void testDeleteException() {
		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/sector/" + DB_STAND_SECTOR_DELETED,
				HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested sector"));
	}

	@Test
	@Transactional
	public void testCreate_stand() throws ResourceNotFoundException {
		int size = sectorService.findAllNotDeleted().size();

		HttpEntity<StandingSectorDTO> httpEntity = new HttpEntity<StandingSectorDTO>(NEW_STAND_DTO, headers);

		ResponseEntity<StandingSector> responseEntity = restTemplate.exchange("/api/sector/" + NEW_SECTOR_HALL_ID,
				HttpMethod.POST, httpEntity, StandingSector.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		StandingSector created = responseEntity.getBody();
		assertNotNull(created);
		assertEquals(NEW_STAND_DTO.getName(), created.getName());
		assertEquals(NEW_STAND_DTO.getCapacity(), created.getCapacity());
		assertFalse(created.isDeleted());

		StandingSector found = (StandingSector) sectorService.findOne(created.getId());
		assertNotNull(found);
		assertEquals(NEW_STAND_DTO.getName(), found.getName());
		assertFalse(found.isDeleted());
		assertEquals(NEW_SECTOR_HALL_ID, found.getHall().getId());
		assertEquals(NEW_STAND_DTO.getCapacity(), found.getCapacity());
		assertEquals(size + 1, sectorService.findAllNotDeleted().size());
		for(Sector s : hallService.findOneNotDeleted(NEW_SECTOR_HALL_ID).getSectors()) {
			System.out.println("-"+s.getName());
		}
	}

	@Test
	@Transactional
	public void testCreate_sit() throws ResourceNotFoundException {
		int size = sectorService.findAllNotDeleted().size();

		HttpEntity<SittingSectorDTO> httpEntity = new HttpEntity<SittingSectorDTO>(NEW_SIT_DTO, headers);

		ResponseEntity<SittingSector> responseEntity = restTemplate.exchange("/api/sector/" + NEW_SECTOR_HALL_ID,
				HttpMethod.POST, httpEntity, SittingSector.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		SittingSector created = responseEntity.getBody();
		assertNotNull(created);
		assertEquals(NEW_SIT_DTO.getName(), created.getName());
		assertEquals(NEW_SIT_DTO.getNumCols(), created.getNumCols());
		assertEquals(NEW_SIT_DTO.getNumRows(), created.getNumRows());
		assertFalse(created.isDeleted());

		SittingSector found = (SittingSector) sectorService.findOne(created.getId());
		assertNotNull(found);
		assertEquals(NEW_SIT_DTO.getName(), found.getName());
		assertFalse(found.isDeleted());
		assertEquals(NEW_SECTOR_HALL_ID, found.getHall().getId());
		assertEquals(NEW_SIT_DTO.getNumCols(), found.getNumCols());
		assertEquals(NEW_SIT_DTO.getNumRows(), found.getNumRows());
		assertEquals(size + 1, sectorService.findAllNotDeleted().size());

	}

	@Test
	public void testCreate_HallNotFound() {
		HttpEntity<StandingSectorDTO> httpEntity = new HttpEntity<>(NEW_STAND_DTO, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/sector/" + HALL_ID_NON_EXISTENT,
				HttpMethod.POST, httpEntity, String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested hall"));
	}

	@Test
	public void testUpdate_stand() throws ResourceNotFoundException {
		int size = sectorService.findAll().size();
		HttpEntity<StandingSectorDTO> httpEntity = new HttpEntity<StandingSectorDTO>(UPD_STAND_DTO, headers);

		ResponseEntity<StandingSector> responseEntity = restTemplate.exchange(
				"/api/sector/" + DB_STAND_SECTOR_TO_BE_UPDATED, HttpMethod.PUT, httpEntity, StandingSector.class);
		StandingSector updated = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(updated);
		assertEquals(UPD_STAND_DTO.getName(), updated.getName());
		assertEquals(UPD_STAND_DTO.getCapacity(), updated.getCapacity());
		assertFalse(updated.isDeleted());

		StandingSector found = (StandingSector) sectorService.findOne(DB_STAND_SECTOR_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(UPD_STAND_DTO.getName(), found.getName());
		assertEquals(UPD_STAND_DTO.getCapacity(), found.getCapacity());
		assertFalse(found.isDeleted());
		assertEquals(size, sectorService.findAll().size()); // nije se dodavao novi vec je izmenjen postojeci
	}

	@Test
	public void testUpdate_sit() throws ResourceNotFoundException {
		int size = sectorService.findAll().size();

		HttpEntity<SittingSectorDTO> httpEntity = new HttpEntity<SittingSectorDTO>(UPD_SIT_DTO, headers);

		ResponseEntity<SittingSector> responseEntity = restTemplate.exchange(
				"/api/sector/" + DB_SIT_SECTOR_TO_BE_UPDATED, HttpMethod.PUT, httpEntity, SittingSector.class);
		SittingSector updated = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(updated);
		assertEquals(UPD_SIT_DTO.getName(), updated.getName());
		assertEquals(UPD_SIT_DTO.getNumCols(), updated.getNumCols());
		assertEquals(UPD_SIT_DTO.getNumRows(), updated.getNumRows());
		assertFalse(updated.isDeleted());

		SittingSector found = (SittingSector) sectorService.findOne(DB_SIT_SECTOR_TO_BE_UPDATED);
		assertNotNull(found);
		assertEquals(UPD_SIT_DTO.getName(), found.getName());
		assertEquals(UPD_SIT_DTO.getNumCols(), found.getNumCols());
		assertEquals(UPD_SIT_DTO.getNumRows(), found.getNumRows());
		assertFalse(found.isDeleted());
		assertEquals(size, sectorService.findAll().size()); // nije se dodavao novi vec je izmenjen postojeci
	}

	@Test
	public void testUpdate_NotFoundException_stand() {
		HttpEntity<StandingSectorDTO> httpEntity = new HttpEntity<StandingSectorDTO>(UPD_STAND_DTO, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/sector/" + SECTOR_ID_NON_EXISTENT,
				HttpMethod.PUT, httpEntity, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested sector"));
	}

	@Test
	public void testUpdate_NotFoundException_sit() {
		HttpEntity<SittingSectorDTO> httpEntity = new HttpEntity<SittingSectorDTO>(UPD_SIT_DTO, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("/api/sector/" + SECTOR_ID_NON_EXISTENT,
				HttpMethod.PUT, httpEntity, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertTrue(responseEntity.getBody().contains("Could not find requested sector"));
	}

	
	// @Test
	// @Ignore
	// public void testGetAllAddressesPageable() {
	//
	// }

}
