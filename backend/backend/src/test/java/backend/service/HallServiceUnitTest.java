package backend.service;

import static backend.constants.AddressConstants.pageRequest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import backend.model.Address;
import backend.model.Hall;
import backend.model.Location;
import backend.repository.AddressRepository;
import backend.repository.HallRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HallServiceUnitTest {

	@Autowired
	HallService hallService;

	@MockBean
	HallRepository hallRepositoryMocked;

	public static final Long hallId = 1L;
	public static final Long deletedHallId = 2L;
	public static final Long nonExistentId = 666L;
	public static final Hall hall = new Hall(hallId, "naziv", null, null, false);
	public static final Hall hall_deleted = new Hall(deletedHallId, "naziv deleted", null, null, true);
	@Before
	public void setup() {
		List<Hall> halls = new ArrayList<>();
		halls.add(hall);
		halls.add(hall_deleted);

		Page<Hall> hallsPage = new PageImpl<>(halls);

		when(hallRepositoryMocked.findAll()).thenReturn(halls);
		when(hallRepositoryMocked.findAll(pageRequest)).thenReturn(hallsPage);
		when(hallRepositoryMocked.findAllByDeleted(false)).thenReturn(halls);
		when(hallRepositoryMocked.findAllByDeleted(false, pageRequest)).thenReturn(hallsPage);
		when(hallRepositoryMocked.findById(hallId)).thenReturn(Optional.of(hall));
		when(hallRepositoryMocked.findById(deletedHallId)).thenReturn(Optional.of(hall_deleted));
	}

	@Test
	public void testFindAll() {
		List<Hall> found = hallService.findAll();
		assertNotNull(found);
		verify(hallRepositoryMocked, times(1)).findAll();
	}

	@Test
	public void testFindAllPageable() {
		Page<Hall> found = hallService.findAll(pageRequest);
		assertNotNull(found);
		verify(hallRepositoryMocked, times(1)).findAll(pageRequest);
	}

	@Test
	public void testFindAllNotDeleted() {
		List<Hall> found = hallService.findAllNotDeleted();
		assertNotNull(found);
		verify(hallRepositoryMocked, times(1)).findAllByDeleted(false);
	}

	@Test
	public void testFindAllNotDeletedPageable() {
		Page<Hall> found = hallService.findAllNotDeleted(pageRequest);
		assertNotNull(found);
		verify(hallRepositoryMocked, times(1)).findAllByDeleted(false, pageRequest);
	}

	@Test
	public void testFindOne() throws ResourceNotFoundException {
		Hall found = hallService.findOne(hallId);
		assertNotNull(found);
		assertTrue(hallId == found.getId());
		assertFalse(found.isDeleted());
		verify(hallRepositoryMocked, times(1)).findById(hallId);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		hallService.findOne(nonExistentId);
	}

	@Test
	public void testFindOneDeleted_shouldFindDeletedAddress() throws ResourceNotFoundException {
		Hall found = hallService.findOne(deletedHallId);
		assertNotNull(found);
		assertTrue(deletedHallId == found.getId());
		assertTrue(found.isDeleted());
		verify(hallRepositoryMocked, times(1)).findById(deletedHallId);
	}

	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		when(hallRepositoryMocked.findByIdAndDeleted(hallId, false)).thenReturn(Optional.of(hall));
		Hall found = hallService.findOneNotDeleted(hallId);
		assertNotNull(found);
		assertTrue(hallId == found.getId());
		assertFalse(found.isDeleted());
		assertEquals(hall.getName(), found.getName());
		verify(hallRepositoryMocked, times(1)).findByIdAndDeleted(hallId, false);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_nonExistentAddress() throws ResourceNotFoundException {
		hallService.findOneNotDeleted(nonExistentId);
	}
	
	@Test
	public void testSave() {
		when(hallRepositoryMocked.save(hall)).thenReturn(hall);
		Hall saved = hallService.save(hall);
		assertNotNull(saved);
		assertFalse(saved.isDeleted());
		assertEquals(hall.getName(), saved.getName());
		verify(hallRepositoryMocked, times(1)).save(hall);
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		when(hallRepositoryMocked.findByIdAndDeleted(hallId, false)).thenReturn(Optional.of(hall));
		System.out.println(hall.isDeleted());
		hallService.delete(hallId);
		System.out.println(hall.isDeleted());
		verify(hallRepositoryMocked, times(1)).findByIdAndDeleted(hallId, false);
		verify(hallRepositoryMocked, times(1)).save(hall);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		hallService.delete(nonExistentId);
	}
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		when(hallRepositoryMocked.findByIdAndDeleted(hallId, false)).thenReturn(Optional.of(hall));
		Hall upd = new Hall(hallId, "blaa", null, null, false);
		when(hallRepositoryMocked.save(upd)).thenReturn(upd);
		Hall updated = hallService.update(hallId, upd);
		
		verify(hallRepositoryMocked, times(1)).findByIdAndDeleted(hallId, false);
		verify(hallRepositoryMocked, times(1)).save(upd);
		
		assertNotNull(updated);
		assertTrue(hallId == updated.getId());
		assertFalse(updated.isDeleted());
		assertEquals(upd.getName(), updated.getName());
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		hallService.update(nonExistentId, null);
	}
}
