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
import java.util.Date;
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
import backend.model.EventDay;
import backend.repository.EventDayRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EventDayServiceUnitTest {
	
	@Autowired
	EventDayService eventService;

	@MockBean
	EventDayRepository eventRepositoryMocked;

	public static final Long eventId = 3L;
	public static final Long deletedEventDayId = 4L;
	public static final Long nonExistentId = 666L;
	public static final EventDay event = new EventDay(eventId, "naziv", "opis",new Date(),null,null,null, false);
	public static final EventDay event_deleted = new EventDay(deletedEventDayId, "naziv deleted","opis deleted", true);
	@Before
	public void setup() {
		List<EventDay> events = new ArrayList<>();
		events.add(event);
		events.add(event_deleted);

		Page<EventDay> eventsPage = new PageImpl<>(events);

		when(eventRepositoryMocked.findAll()).thenReturn(events);
		when(eventRepositoryMocked.findAll(pageRequest)).thenReturn(eventsPage);
		when(eventRepositoryMocked.findAllByDeleted(false)).thenReturn(events);
		when(eventRepositoryMocked.findAllByDeleted(false, pageRequest)).thenReturn(eventsPage);
		when(eventRepositoryMocked.findById(eventId)).thenReturn(Optional.of(event));
		when(eventRepositoryMocked.findById(deletedEventDayId)).thenReturn(Optional.of(event_deleted));
	}

	@Test
	public void testFindAll() {
		List<EventDay> found = eventService.findAll();
		assertNotNull(found);
		verify(eventRepositoryMocked, times(1)).findAll();
	}

	@Test
	public void testFindAllPageable() {
		Page<EventDay> found = eventService.findAll(pageRequest);
		assertNotNull(found);
		verify(eventRepositoryMocked, times(1)).findAll(pageRequest);
	}

	@Test
	public void testFindAllNotDeleted() {
		List<EventDay> found = eventService.findAllNotDeleted();
		assertNotNull(found);
		verify(eventRepositoryMocked, times(1)).findAllByDeleted(false);
	}

	@Test
	public void testFindAllNotDeletedPageable() {
		Page<EventDay> found = eventService.findAllNotDeleted(pageRequest);
		assertNotNull(found);
		verify(eventRepositoryMocked, times(1)).findAllByDeleted(false, pageRequest);
	}

	@Test
	public void testFindOne() throws ResourceNotFoundException {
		EventDay found = eventService.findOne(eventId);
		assertNotNull(found);
		assertTrue(eventId == found.getId());
		assertFalse(found.isDeleted());
		verify(eventRepositoryMocked, times(1)).findById(eventId);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNonExistent() throws ResourceNotFoundException {
		eventService.findOne(nonExistentId);
	}

	@Test
	public void testFindOneDeleted_shouldFindDeletedAddress() throws ResourceNotFoundException {
		EventDay found = eventService.findOne(deletedEventDayId);
		assertNotNull(found);
		assertTrue(deletedEventDayId == found.getId());
		assertTrue(found.isDeleted());
		verify(eventRepositoryMocked, times(1)).findById(deletedEventDayId);
	}

	@Test
	public void testFindOneNotDeleted() throws ResourceNotFoundException {
		when(eventRepositoryMocked.findByIdAndDeleted(eventId, false)).thenReturn(Optional.of(event));
		EventDay found = eventService.findOneNotDeleted(eventId);
		assertNotNull(found);
		assertTrue(eventId == found.getId());
		assertFalse(found.isDeleted());
		assertEquals(event.getName(), found.getName());
		verify(eventRepositoryMocked, times(1)).findByIdAndDeleted(eventId, false);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneNotDeleted_nonExistentAddress() throws ResourceNotFoundException {
		eventService.findOneNotDeleted(nonExistentId);
	}
	
	@Test
	public void testSave() {
		when(eventRepositoryMocked.save(event)).thenReturn(event);
		EventDay saved = eventService.save(event);
		assertNotNull(saved);
		assertFalse(saved.isDeleted());
		assertEquals(event.getName(), saved.getName());
		verify(eventRepositoryMocked, times(1)).save(event);
	}
	
	@Test
	public void testDelete() throws ResourceNotFoundException {
		when(eventRepositoryMocked.findByIdAndDeleted(eventId, false)).thenReturn(Optional.of(event));
		System.out.println(event.isDeleted());
		eventService.delete(eventId);
		System.out.println(event.isDeleted());
		verify(eventRepositoryMocked, times(1)).findByIdAndDeleted(eventId, false);
		verify(eventRepositoryMocked, times(1)).save(event);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteException() throws ResourceNotFoundException {
		eventService.delete(nonExistentId);
	}
	
	@Test
	public void testUpdate() throws ResourceNotFoundException {
		when(eventRepositoryMocked.findByIdAndDeleted(eventId, false)).thenReturn(Optional.of(event));
		EventDay upd = new EventDay(eventId, "blablaa", "opisBLAA",new Date(),null,null,null, false);
		when(eventRepositoryMocked.save(upd)).thenReturn(upd);
		EventDay updated = eventService.update(eventId, upd);
		
		verify(eventRepositoryMocked, times(1)).findByIdAndDeleted(eventId, false);
		verify(eventRepositoryMocked, times(1)).save(upd); //ovde nesto ne radi, kaze da su razliciti argumenti
		
		assertNotNull(updated);
		assertTrue(eventId == updated.getId());
		assertFalse(updated.isDeleted());
		assertEquals(upd.getName(), updated.getName());
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testUpdateException() throws ResourceNotFoundException {
		eventService.update(nonExistentId, null);
	}
}
