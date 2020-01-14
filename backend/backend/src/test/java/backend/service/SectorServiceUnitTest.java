package backend.service;

import static backend.constants.AddressConstants.pageRequest;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
import backend.model.Hall;
import backend.model.Sector;
import backend.model.SittingSector;
import backend.model.StandingSector;
import backend.repository.SectorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SectorServiceUnitTest {

	@Autowired
	SectorService sectorService;

	@MockBean
	SectorRepository sectorRepositoryMocked;

	public static final Long stand_sectorId = 1L;
	public static final Long deletedStandSectorId = 2L;
	public static final Long sit_sectorId = 3L;
	public static final Long deletedSitSectorId = 4L;
	public static final Long nonExistentId = 666L;
	public static final StandingSector stand_sector = new StandingSector(stand_sectorId, "stand sector", 400, null, false);
	public static final StandingSector stand_sector_deleted = new StandingSector(deletedStandSectorId, "deleted stand sec", 600, null, true);
	public static final SittingSector sit_sector = new SittingSector(sit_sectorId, "sit sector", 6, 7, null, false);
	public static final SittingSector sit_sector_deleted = new SittingSector(deletedSitSectorId, "sit sector deleted", 16, 17, null, true);
	@Before
	public void setup() {
		List<Sector> sectors = new ArrayList<>();
		sectors.add(stand_sector);
		sectors.add(stand_sector_deleted);
		sectors.add(sit_sector);
		sectors.add(sit_sector_deleted);
		
		Page<Sector> sectorsPage = new PageImpl<>(sectors);

		when(sectorRepositoryMocked.findAll()).thenReturn(sectors);
		when(sectorRepositoryMocked.findAll(pageRequest)).thenReturn(sectorsPage);
		when(sectorRepositoryMocked.findAllByDeleted(false)).thenReturn(sectors);
		when(sectorRepositoryMocked.findAllByDeleted(false, pageRequest)).thenReturn(sectorsPage);
		when(sectorRepositoryMocked.findById(stand_sectorId)).thenReturn(Optional.of(stand_sector));
		when(sectorRepositoryMocked.findById(sit_sectorId)).thenReturn(Optional.of(sit_sector));
		when(sectorRepositoryMocked.findById(deletedStandSectorId)).thenReturn(Optional.of(stand_sector_deleted));
		when(sectorRepositoryMocked.findById(deletedSitSectorId)).thenReturn(Optional.of(sit_sector_deleted));
	}

	@Test
	public void testFindAll() {
		List<Sector> found = sectorService.findAll();
		assertNotNull(found);
		verify(sectorRepositoryMocked, times(1)).findAll();
	}

	@Test
	public void testFindAllPageable() {
		Page<Sector> found = sectorService.findAll(pageRequest);
		assertNotNull(found);
		verify(sectorRepositoryMocked, times(1)).findAll(pageRequest);
	}

	@Test
	public void testFindAllNotDeleted() {
		List<Sector> found = sectorService.findAllNotDeleted();
		assertNotNull(found);
		verify(sectorRepositoryMocked, times(1)).findAllByDeleted(false);
	}

	@Test
	public void testFindAllNotDeletedPageable() {
		Page<Sector> found = sectorService.findAllNotDeleted(pageRequest);
		assertNotNull(found);
		verify(sectorRepositoryMocked, times(1)).findAllByDeleted(false, pageRequest);
	}

	@Test
	public void testFindOne_STAND() throws ResourceNotFoundException {
		StandingSector found = (StandingSector) sectorService.findOne(stand_sectorId);
		assertNotNull(found);
		assertTrue(stand_sectorId == found.getId());
		assertFalse(found.isDeleted());
		verify(sectorRepositoryMocked, times(1)).findById(stand_sectorId);
	}
	
	@Test
	public void testFindOne_SIT() throws ResourceNotFoundException {
		SittingSector found = (SittingSector) sectorService.findOne(sit_sectorId);
		assertNotNull(found);
		assertTrue(sit_sectorId == found.getId());
		assertFalse(found.isDeleted());
		verify(sectorRepositoryMocked, times(1)).findById(sit_sectorId);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		sectorService.findOne(nonExistentId);
	}

	@Test
	public void testFindOneDeleted_shouldFindDeletedSector_STAND() throws ResourceNotFoundException {
		StandingSector found = (StandingSector) sectorService.findOne(deletedStandSectorId);
		assertNotNull(found);
		assertTrue(deletedStandSectorId == found.getId());
		assertTrue(found.isDeleted());
		verify(sectorRepositoryMocked, times(1)).findById(deletedStandSectorId);
	}

	@Test
	public void testFindOneDeleted_shouldFindDeletedSector_SIT() throws ResourceNotFoundException {
		SittingSector found = (SittingSector) sectorService.findOne(deletedSitSectorId);
		assertNotNull(found);
		assertTrue(deletedSitSectorId == found.getId());
		assertTrue(found.isDeleted());
		verify(sectorRepositoryMocked, times(1)).findById(deletedSitSectorId);
	}
	
	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		when(sectorRepositoryMocked.findByIdAndDeleted(stand_sectorId, false)).thenReturn(Optional.of(stand_sector));
		StandingSector found = (StandingSector) sectorService.findOneNotDeleted(stand_sectorId);
		assertNotNull(found);
		assertTrue(stand_sectorId == found.getId());
		assertFalse(found.isDeleted());
		assertEquals(stand_sector.getName(), found.getName());
		verify(sectorRepositoryMocked, times(1)).findByIdAndDeleted(stand_sectorId, false);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_nonExistentSector() throws ResourceNotFoundException {
		sectorService.findOneNotDeleted(nonExistentId);
	}
	
	@Test
	public void testSave_STAND() {
		when(sectorRepositoryMocked.save(stand_sector)).thenReturn(stand_sector);
		StandingSector saved = (StandingSector) sectorService.save(stand_sector);
		assertNotNull(saved);
		assertFalse(saved.isDeleted());
		assertEquals(stand_sector.getName(), saved.getName());
		assertEquals(stand_sector.getCapacity(), saved.getCapacity());
		verify(sectorRepositoryMocked, times(1)).save(stand_sector);
	}
	
	@Test
	public void testSave_SIT() {
		when(sectorRepositoryMocked.save(sit_sector)).thenReturn(sit_sector);
		SittingSector saved = (SittingSector) sectorService.save(sit_sector);
		assertNotNull(saved);
		assertFalse(saved.isDeleted());
		assertEquals(sit_sector.getName(), saved.getName());
		assertEquals(sit_sector.getNumRows(), saved.getNumRows());
		assertEquals(sit_sector.getNumCols(), saved.getNumCols());
		verify(sectorRepositoryMocked, times(1)).save(sit_sector);
	}
	
	@Test
	public void testDelete_STAND() throws ResourceNotFoundException {
		when(sectorRepositoryMocked.findByIdAndDeleted(stand_sectorId, false)).thenReturn(Optional.of(stand_sector));
		System.out.println(stand_sector.isDeleted());
		sectorService.delete(stand_sectorId);
		System.out.println(stand_sector.isDeleted());
		verify(sectorRepositoryMocked, times(1)).findByIdAndDeleted(stand_sectorId, false);
		verify(sectorRepositoryMocked, times(1)).save(stand_sector);
		//ponistavanje izmena
		stand_sector.setDeleted(false);
	}
	
	@Test
	public void testDelete_SIT() throws ResourceNotFoundException {
		when(sectorRepositoryMocked.findByIdAndDeleted(sit_sectorId, false)).thenReturn(Optional.of(sit_sector));
		System.out.println(sit_sector.isDeleted());
		sectorService.delete(sit_sectorId);
		System.out.println(sit_sector.isDeleted());
		verify(sectorRepositoryMocked, times(1)).findByIdAndDeleted(sit_sectorId, false);
		verify(sectorRepositoryMocked, times(1)).save(sit_sector);
		//ponistavanje izmena
		sit_sector.setDeleted(false);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		sectorService.delete(nonExistentId);
	}
	
	@Test
	public void testUpdate_STAND() throws ResourceNotFoundException {
		when(sectorRepositoryMocked.findByIdAndDeleted(stand_sectorId, false)).thenReturn(Optional.of(stand_sector));
		StandingSector upd = new StandingSector(stand_sectorId, "blaa", 666, null);
		when(sectorRepositoryMocked.save(upd)).thenReturn(upd);
		StandingSector updated = sectorService.update(stand_sectorId, upd);
		
		verify(sectorRepositoryMocked, times(1)).findByIdAndDeleted(stand_sectorId, false);
		verify(sectorRepositoryMocked, times(1)).save(upd);
		
		assertNotNull(updated);
		assertTrue(stand_sectorId == updated.getId());
		assertFalse(updated.isDeleted());
		assertEquals(upd.getName(), updated.getName());
		assertEquals(upd.getCapacity(), updated.getCapacity());
	}
	
	@Test
	public void testUpdate_SIT() throws ResourceNotFoundException {
		when(sectorRepositoryMocked.findByIdAndDeleted(sit_sectorId, false)).thenReturn(Optional.of(sit_sector));
		SittingSector upd = new SittingSector(sit_sectorId, "blaa", 3, 4, null);
		when(sectorRepositoryMocked.save(upd)).thenReturn(upd);
		SittingSector updated = sectorService.update(sit_sectorId, upd);
		
		verify(sectorRepositoryMocked, times(1)).findByIdAndDeleted(sit_sectorId, false);
		verify(sectorRepositoryMocked, times(1)).save(upd);
		
		assertNotNull(updated);
		assertTrue(sit_sectorId == updated.getId());
		assertFalse(updated.isDeleted());
		assertEquals(upd.getName(), updated.getName());
		assertEquals(upd.getNumCols(), updated.getNumCols());
		assertEquals(upd.getNumRows(), updated.getNumRows());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException_STAND() throws ResourceNotFoundException {
		sectorService.update(nonExistentId, new StandingSector());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException_SIT() throws ResourceNotFoundException {
		sectorService.update(nonExistentId, new SittingSector());
	}
}
