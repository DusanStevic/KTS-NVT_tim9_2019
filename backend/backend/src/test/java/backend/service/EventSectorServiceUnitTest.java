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
import backend.model.EventSector;
import backend.repository.EventSectorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EventSectorServiceUnitTest {

	@Autowired
	EventSectorService eventSectorService;

	@MockBean
	EventSectorRepository eventSectorRepositoryMocked;

	public static final Long eventSectorId = 5L;
	public static final Long deletedEventSectorId = 6L;
	public static final Long nonExistentSectorId = 666L;
	public static final EventSector eventSector = new EventSector(eventSectorId, 300, null,null, false);
	public static final EventSector event_deleted = new EventSector(deletedEventSectorId, 350,null,null, true);
	
	@Before
	public void setup() {
		List<EventSector> events = new ArrayList<>();
		events.add(eventSector);
		events.add(event_deleted);

		Page<EventSector> eventsPage = new PageImpl<>(events);
		System.out.println("aaaaaaaaaaaaaaaaa" + eventsPage.getSize());
		
		when(eventSectorRepositoryMocked.findAll()).thenReturn(events);
		when(eventSectorRepositoryMocked.findAll(pageRequest)).thenReturn(eventsPage);
		when(eventSectorRepositoryMocked.findAllByDeleted(false)).thenReturn(events);
		when(eventSectorRepositoryMocked.findAllByDeleted(false, pageRequest)).thenReturn(eventsPage);
		when(eventSectorRepositoryMocked.findById(eventSectorId)).thenReturn(Optional.of(eventSector));
		when(eventSectorRepositoryMocked.findById(deletedEventSectorId)).thenReturn(Optional.of(event_deleted));
	}
	
	@Test
	public void testFindAll() {
		List<EventSector> found = eventSectorService.findAll();
		assertNotNull(found);
		verify(eventSectorRepositoryMocked, times(1)).findAll();
	}
	
	@Test
	public void testFindAllPageable() {
		Page<EventSector> found = eventSectorService.findAll(pageRequest);
		assertNotNull(found);
		verify(eventSectorRepositoryMocked, times(1)).findAll(pageRequest);
	}
	@Test
	public void testFindAllNotDeleted() {
		List<EventSector> found = eventSectorService.findAllNotDeleted();
		assertNotNull(found);
		verify(eventSectorRepositoryMocked, times(1)).findAllByDeleted(false);
	}

	@Test
	public void testFindAllNotDeletedPageable() {
		Page<EventSector> found = eventSectorService.findAllNotDeleted(pageRequest);
		assertNotNull(found);
		verify(eventSectorRepositoryMocked, times(1)).findAllByDeleted(false, pageRequest);
	}

	@Test
	public void testFindOne() throws ResourceNotFoundException {
		EventSector found = eventSectorService.findOne(eventSectorId);
		assertNotNull(found);
		assertTrue(eventSectorId == found.getId());
		assertFalse(found.isDeleted());
		verify(eventSectorRepositoryMocked, times(1)).findById(eventSectorId);
	}
	///

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		eventSectorService.findOne(nonExistentSectorId);
	}

	@Test
	public void testFindOneDeleted_shouldFindDeletedAddress() throws ResourceNotFoundException {
		EventSector found = eventSectorService.findOne(deletedEventSectorId);
		assertNotNull(found);
		assertTrue(deletedEventSectorId == found.getId());
		assertTrue(found.isDeleted());
		verify(eventSectorRepositoryMocked, times(1)).findById(deletedEventSectorId);
	}

	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		when(eventSectorRepositoryMocked.findByIdAndDeleted(eventSectorId, false)).thenReturn(Optional.of(eventSector));
		EventSector found = eventSectorService.findOneNotDeleted(eventSectorId);
		assertNotNull(found);
		assertTrue(eventSectorId == found.getId());
		assertFalse(found.isDeleted());
		assertEquals(eventSector.getPrice(), found.getPrice(),2);
		verify(eventSectorRepositoryMocked, times(1)).findByIdAndDeleted(eventSectorId, false);
	}
	
	/////// **** 
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_nonExistentAddress() throws ResourceNotFoundException {
		eventSectorService.findOneNotDeleted(nonExistentSectorId);
	}
	
	@Test
	public void testSave() {
		when(eventSectorRepositoryMocked.save(eventSector)).thenReturn(eventSector);
		EventSector saved = eventSectorService.save(eventSector);
		assertNotNull(saved);
		assertFalse(saved.isDeleted());
		verify(eventSectorRepositoryMocked, times(1)).save(eventSector);
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		when(eventSectorRepositoryMocked.findByIdAndDeleted(eventSectorId, false)).thenReturn(Optional.of(eventSector));
		System.out.println(eventSector.isDeleted());
		eventSectorService.delete(eventSectorId);
		System.out.println(eventSector.isDeleted());
		verify(eventSectorRepositoryMocked, times(1)).findByIdAndDeleted(eventSectorId, false);
		verify(eventSectorRepositoryMocked, times(1)).save(eventSector);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		eventSectorService.delete(nonExistentSectorId);
	}
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		when(eventSectorRepositoryMocked.findByIdAndDeleted(eventSectorId, false)).thenReturn(Optional.of(eventSector));
		EventSector upd = new EventSector(eventSectorId, 400,null, null, false);
		when(eventSectorRepositoryMocked.save(upd)).thenReturn(upd);
		EventSector updated = eventSectorService.update(eventSectorId, upd.getPrice());
		
		verify(eventSectorRepositoryMocked, times(1)).findByIdAndDeleted(eventSectorId, false);
		verify(eventSectorRepositoryMocked, times(1)).save(upd); //ovde nesto ne radi, kaze da su razliciti argumenti
		assertNotNull(updated);
		assertTrue(eventSectorId == updated.getId());
		assertFalse(updated.isDeleted());
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		eventSectorService.update(nonExistentSectorId, 0);
	}
}
