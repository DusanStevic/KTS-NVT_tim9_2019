package backend.service;

import static backend.constants.TicketConstants.DB_COUNT;
import static backend.constants.TicketConstants.EVENT2_ID;
import static backend.constants.TicketConstants.EVENT2_TICKET_COUNT;
import static backend.constants.TicketConstants.EVENT_ID_NONEXISTENT;
import static backend.constants.TicketConstants.LOCATION1_ID;
import static backend.constants.TicketConstants.LOCATION_DATE_EMPTY;
import static backend.constants.TicketConstants.LOCATION_DATE_GOOD;
import static backend.constants.TicketConstants.LOCATION_ID_EMPTY;
import static backend.constants.TicketConstants.LOCATION_ID_NON_EXISTENT;
import static backend.constants.TicketConstants.NEW_HAS_SEAT;
import static backend.constants.TicketConstants.NEW_NUM_COL;
import static backend.constants.TicketConstants.NEW_NUM_ROW;
import static backend.constants.TicketConstants.PAGE_SIZE;
import static backend.constants.TicketConstants.RESERVATION_ID;
import static backend.constants.TicketConstants.RESERVATION_ID_NON_EXISTENT;
import static backend.constants.TicketConstants.RESERVATION_TICKET_COUNT;
import static backend.constants.TicketConstants.TICKET1_COL;
import static backend.constants.TicketConstants.TICKET1_EVENTDAY_ID;
import static backend.constants.TicketConstants.TICKET1_EV_SECTOR_ID;
import static backend.constants.TicketConstants.TICKET1_HAS_SEAT;
import static backend.constants.TicketConstants.TICKET1_ID;
import static backend.constants.TicketConstants.TICKET1_RESERVATION_ID;
import static backend.constants.TicketConstants.TICKET1_ROW;
import static backend.constants.TicketConstants.TICKET3_EV_SECTOR_ID;
import static backend.constants.TicketConstants.TICKET3_ID;
import static backend.constants.TicketConstants.TICKET_ID_NONEXISTENT;
import static backend.constants.TicketConstants.pageRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import backend.exceptions.ResourceNotFoundException;
import backend.model.Ticket;
import backend.repository.TicketRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class TicketServiceUnitTest {
	@Autowired
	TicketService ticketService;
	@MockBean
	TicketRepository ticketRepositoryMocked;
	
	@Autowired
	EventDayService eventDayService;
	@Autowired
	EventSectorService eventSectorService;
	@Autowired
	ReservationService reservationService;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Before
	public void setUp() throws ResourceNotFoundException, ParseException {
		Ticket t1, t2, t3, t4, t5;
		t1 = new Ticket(1L, true, 1, 1, eventDayService.findOne(1L),
				reservationService.findOne(1L), eventSectorService.findOne(2L));
		t2 = new Ticket(2L, true, 1, 1, eventDayService.findOne(1L),
				reservationService.findOne(2L), eventSectorService.findOne(2L));
		t3 = new Ticket(3L, true, 1, 1, eventDayService.findOne(2L),
				reservationService.findOne(3L), eventSectorService.findOne(4L));
		t4 = new Ticket(4L, true, 1, 1, eventDayService.findOne(2L),
				reservationService.findOne(4L), eventSectorService.findOne(4L));
		t5 = new Ticket(5L, true, 1, 1, eventDayService.findOne(2L),
				reservationService.findOne(4L), eventSectorService.findOne(3L));

		
		List<Ticket> all = new ArrayList<Ticket>();
		all.add(t1);
		all.add(t2);
		all.add(t3);
		all.add(t4);
		all.add(t5);
		
		List<Ticket> dayAndSector = new ArrayList<Ticket>();
		dayAndSector.add(t1);
		dayAndSector.add(t2);
		
		List<Ticket> eventTickets = new ArrayList<Ticket>();
		eventTickets.add(t3);
		eventTickets.add(t4);
		eventTickets.add(t5);
		

		List<Ticket> reservationTickets = new ArrayList<Ticket>();
		reservationTickets.add(t1);
		Page<Ticket> page = new PageImpl<Ticket>(all);
		when(ticketRepositoryMocked.findAll()).thenReturn(all);
		when(ticketRepositoryMocked.findAll(pageRequest)).thenReturn(page);
		when(ticketRepositoryMocked.findById(TICKET1_ID)).thenReturn(
				Optional.of(t1));
		//when(ticketRepositoryMocked.findById(TICKET_ID_NONEXISTENT)).thenThrow(ResourceNotFoundException.class);
		doNothing().when(ticketRepositoryMocked).deleteById(TICKET1_ID);
		when(ticketRepositoryMocked.findAllByEventDayIDEventSectorID(
						TICKET1_EVENTDAY_ID, TICKET1_EV_SECTOR_ID)).thenReturn(dayAndSector);
		when(ticketRepositoryMocked.findAllByLocationDate(LOCATION1_ID, df.parse(LOCATION_DATE_GOOD))).thenReturn(all);
		when(ticketRepositoryMocked.findAllByLocation(LOCATION1_ID)).thenReturn(all);
		when(ticketRepositoryMocked.findAllByEvent(EVENT2_ID)).thenReturn(eventTickets);
		when(ticketRepositoryMocked.findAllByReservation(RESERVATION_ID)).thenReturn(reservationTickets);
	}

	@Test
	@Transactional
	public void testSave() throws ResourceNotFoundException {
		Ticket t = new Ticket();
		t.setId(1L);
		t.setHasSeat(NEW_HAS_SEAT);
		t.setNumRow(NEW_NUM_ROW);
		t.setNumCol(NEW_NUM_COL);

		when(ticketRepositoryMocked.save(t)).thenReturn(t);

		Ticket dbTicket = ticketService.save(t);
		assertNotNull(dbTicket);
		assertThat(dbTicket.getId()).isNotNull();
		assertThat(dbTicket.getNumCol()).isEqualTo(NEW_NUM_COL);
		assertThat(dbTicket.getNumRow()).isEqualTo(NEW_NUM_ROW);
		assertThat(dbTicket.isHasSeat()).isTrue();

		verify(ticketRepositoryMocked, times(1)).save(t);
	}

	@Test
	public void testFindOne() throws ResourceNotFoundException {
		Ticket dbTicket = ticketService.findOne(TICKET1_ID);
		verify(ticketRepositoryMocked, times(1)).findById(TICKET1_ID);

		assertThat(dbTicket).isNotNull();
		assertThat(dbTicket.getId()).isEqualTo(TICKET1_ID);
		assertThat(dbTicket.getNumCol()).isEqualTo(TICKET1_COL);
		assertThat(dbTicket.getNumRow()).isEqualTo(TICKET1_ROW);
		assertThat(dbTicket.getReservation().getId()).isEqualTo(
				TICKET1_RESERVATION_ID);
		assertThat(dbTicket.getEventDay().getId()).isEqualTo(
				TICKET1_EVENTDAY_ID);
		assertThat(dbTicket.getEventSector().getId()).isEqualTo(
				TICKET1_EV_SECTOR_ID);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneBadId() throws ResourceNotFoundException {
		ticketService.findOne(TICKET_ID_NONEXISTENT);
	}

	@Test
	public void testFindAll() {
		List<Ticket> tickets = ticketService.findAll();
		assertThat(tickets).hasSize(DB_COUNT);
		verify(ticketRepositoryMocked, times(1)).findAll();
	}

	@Test
	public void testFindAllPagable() {
		Page<Ticket> found = ticketService.findAll(pageRequest);
		List<Ticket> tickets = found.getContent();
		assertThat(tickets).hasSize(PAGE_SIZE);
		verify(ticketRepositoryMocked, times(1)).findAll(pageRequest);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testRemove() throws ResourceNotFoundException {
		ticketService.remove(TICKET1_ID);
		verify(ticketRepositoryMocked, times(1)).deleteById(TICKET1_ID);
	}

	@Test
	public void testFindAllByEventDayIDEventSectorID() {
		List<Ticket> found = ticketService.findAllByEventDayIDEventSectorID(
				TICKET1_EVENTDAY_ID, TICKET1_EV_SECTOR_ID);
		verify(ticketRepositoryMocked, times(1))
				.findAllByEventDayIDEventSectorID(TICKET1_EVENTDAY_ID,
						TICKET1_EV_SECTOR_ID);
		assertNotNull(found);
		assertFalse(found.isEmpty());
		assertTrue(found.size() == 2);
		Ticket t1 = found.get(0);
		assertNotNull(t1);
		assertEquals(TICKET1_ID, t1.getId());
		assertTrue(TICKET1_HAS_SEAT);
		assertEquals(TICKET1_COL, t1.getNumCol());
		assertEquals(TICKET1_ROW, t1.getNumRow());
		assertEquals(TICKET1_EVENTDAY_ID, t1.getEventDay().getId());
		assertEquals(TICKET1_EV_SECTOR_ID, t1.getEventSector().getId());
	}

	@Test
	public void testFindAllByEventDayIDEventSectorIdBadInputs() {
		// non matching eventday and eventsector
		List<Ticket>empty = new ArrayList<Ticket>();
		when(ticketRepositoryMocked.findAllByEventDayIDEventSectorID(TICKET1_EVENTDAY_ID, TICKET3_EV_SECTOR_ID)).thenReturn(empty);
		List<Ticket> found = ticketService.findAllByEventDayIDEventSectorID(
				TICKET1_EVENTDAY_ID, TICKET3_EV_SECTOR_ID);
		assertNotNull(found);
		assertTrue(found.isEmpty());
		verify(ticketRepositoryMocked, times(1))
		.findAllByEventDayIDEventSectorID(TICKET1_EVENTDAY_ID, TICKET3_EV_SECTOR_ID);

		// non existent eventday and eventsector id
		when(ticketRepositoryMocked.findAllByEventDayIDEventSectorID(666L, 666L)).thenReturn(empty);
		found = ticketService.findAllByEventDayIDEventSectorID(666L, 666L);
		assertNotNull(found);
		assertTrue(found.isEmpty());
		verify(ticketRepositoryMocked, times(1))
		.findAllByEventDayIDEventSectorID(666L, 666L);
	}

	@Test
	@Transactional
	public void testFindAllByLocationDate() throws ParseException {
		
		List<Ticket> found = ticketService.findAllByLocationDate(LOCATION1_ID,
				df.parse(LOCATION_DATE_GOOD));
		verify(ticketRepositoryMocked, times(1)).findAllByLocationDate(
				LOCATION1_ID, df.parse(LOCATION_DATE_GOOD));

		assertNotNull(found);
		assertFalse(found.isEmpty());
		assertTrue(found.size() == 5);

		Ticket t1 = found.get(0);
		Ticket t3 = found.get(2);
		assertNotNull(t1);
		assertNotNull(t3);

		assertEquals(TICKET1_ID, t1.getId());
		assertEquals(LOCATION1_ID, t1.getEventDay().getEvent().getLocation()
				.getId());
		assertTrue(df.parse(LOCATION_DATE_GOOD).before(
				t1.getEventDay().getDate()));

		assertEquals(TICKET3_ID, t3.getId());
		assertEquals(LOCATION1_ID, t3.getEventDay().getEvent().getLocation()
				.getId());
		assertTrue(df.parse(LOCATION_DATE_GOOD).before(
				t3.getEventDay().getDate()));
	}

	@Test
	public void testFindAllByLocationDateBadInput() throws ParseException {	
		List<Ticket>empty = new ArrayList<Ticket>();
		// good date, no ticket for location
		when(ticketRepositoryMocked.findAllByLocationDate(LOCATION_ID_EMPTY, df.parse(LOCATION_DATE_GOOD))).thenReturn(empty);
		List<Ticket> found = ticketService.findAllByLocationDate(
				LOCATION_ID_EMPTY, df.parse(LOCATION_DATE_GOOD));
		assertNotNull(found);
		assertTrue(found.isEmpty());
		verify(ticketRepositoryMocked, times(1)).findAllByLocationDate(LOCATION_ID_EMPTY, df.parse(LOCATION_DATE_GOOD));

		// good date, non existent location
		when(ticketRepositoryMocked.findAllByLocationDate(LOCATION_ID_NON_EXISTENT, df.parse(LOCATION_DATE_GOOD))).thenReturn(empty);
		found = ticketService.findAllByLocationDate(LOCATION_ID_NON_EXISTENT,
				df.parse(LOCATION_DATE_GOOD));
		assertNotNull(found);
		assertTrue(found.isEmpty());
		verify(ticketRepositoryMocked, times(1)).findAllByLocationDate(LOCATION_ID_NON_EXISTENT, df.parse(LOCATION_DATE_GOOD));

		// empty date, good location
		when(ticketRepositoryMocked.findAllByLocationDate(LOCATION1_ID, df.parse(LOCATION_DATE_EMPTY))).thenReturn(empty);
		found = ticketService.findAllByLocationDate(LOCATION1_ID,
				df.parse(LOCATION_DATE_EMPTY));
		assertNotNull(found);
		assertTrue(found.isEmpty());
		verify(ticketRepositoryMocked, times(1)).findAllByLocationDate(LOCATION1_ID, df.parse(LOCATION_DATE_EMPTY));

		// empty date, bad location
		when(ticketRepositoryMocked.findAllByLocationDate(LOCATION_ID_NON_EXISTENT, df.parse(LOCATION_DATE_EMPTY))).thenReturn(empty);
		found = ticketService.findAllByLocationDate(LOCATION_ID_NON_EXISTENT,
				df.parse(LOCATION_DATE_EMPTY));
		assertNotNull(found);
		assertTrue(found.isEmpty());
		verify(ticketRepositoryMocked, times(1)).findAllByLocationDate(LOCATION_ID_NON_EXISTENT, df.parse(LOCATION_DATE_EMPTY));
	}

	@Test
	@Transactional
	public void testFindAllByLocation() {
		List<Ticket> found = ticketService.findAllByLocation(LOCATION1_ID);
		verify(ticketRepositoryMocked, times(1))
				.findAllByLocation(LOCATION1_ID);
		assertNotNull(found);
		assertFalse(found.isEmpty());
		assertTrue(found.size() == 5);

		Ticket t1 = found.get(0);
		Ticket t3 = found.get(2);
		assertNotNull(t1);
		assertNotNull(t3);

		assertEquals(TICKET1_ID, t1.getId());
		assertEquals(LOCATION1_ID, t1.getEventDay().getEvent().getLocation()
				.getId());
		assertEquals(TICKET3_ID, t3.getId());
		assertEquals(LOCATION1_ID, t3.getEventDay().getEvent().getLocation()
				.getId());
	}

	@Test
	public void testFindAllByLocationBadId() {
		List<Ticket>empty = new ArrayList<Ticket>();
		
		when(ticketRepositoryMocked.findAllByLocation(LOCATION_ID_EMPTY)).thenReturn(empty);
		List<Ticket> found = ticketService.findAllByLocation(LOCATION_ID_EMPTY);
		assertNotNull(found);
		assertTrue(found.isEmpty());
		verify(ticketRepositoryMocked, times(1)).findAllByLocation(LOCATION_ID_EMPTY);

		when(ticketRepositoryMocked.findAllByLocation(LOCATION_ID_NON_EXISTENT)).thenReturn(empty);
		found = ticketService.findAllByLocation(LOCATION_ID_NON_EXISTENT);
		assertNotNull(found);
		assertTrue(found.isEmpty());
		verify(ticketRepositoryMocked, times(1)).findAllByLocation(LOCATION_ID_NON_EXISTENT);
	}

	@Test
	@Transactional
	public void testFindAllByEvent() {
		List<Ticket> found = ticketService.findAllByEvent(EVENT2_ID);
		verify(ticketRepositoryMocked, times(1)).findAllByEvent(EVENT2_ID);
		assertNotNull(found);
		assertFalse(found.isEmpty());
		assertTrue(EVENT2_TICKET_COUNT == found.size());
		Ticket t3 = found.get(0);
		assertEquals(TICKET3_ID, t3.getId());
		assertEquals(EVENT2_ID, t3.getEventDay().getEvent().getId());
	}

	@Test
	public void testFindAllByEventBadId() {
		when(ticketRepositoryMocked.findAllByEvent(EVENT_ID_NONEXISTENT)).thenReturn(new ArrayList<Ticket>());
		List<Ticket> found = ticketService.findAllByEvent(EVENT_ID_NONEXISTENT);
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}

	@Test
	public void testFindAllByReservation() {
		List<Ticket> found = ticketService.findAllByReservation(RESERVATION_ID);
		verify(ticketRepositoryMocked, times(1)).findAllByReservation(
				RESERVATION_ID);
		assertNotNull(found);
		assertFalse(found.isEmpty());
		assertTrue(RESERVATION_TICKET_COUNT == found.size());

		Ticket t1 = found.get(0);
		assertNotNull(t1);
		assertEquals(TICKET1_ID, t1.getId());
		assertTrue(TICKET1_HAS_SEAT);
		assertEquals(TICKET1_COL, t1.getNumCol());
		assertEquals(TICKET1_ROW, t1.getNumRow());
		assertEquals(TICKET1_EVENTDAY_ID, t1.getEventDay().getId());
		assertEquals(TICKET1_EV_SECTOR_ID, t1.getEventSector().getId());
	}

	@Test
	public void testFindAllByReservationBadId() {
		when(ticketRepositoryMocked.findAllByReservation(RESERVATION_ID_NON_EXISTENT)).thenReturn(new ArrayList<Ticket>());
		List<Ticket> found = ticketService
				.findAllByReservation(RESERVATION_ID_NON_EXISTENT);
		assertNotNull(found);
		assertTrue(found.isEmpty());		
		verify(ticketRepositoryMocked, times(1)).findAllByReservation(RESERVATION_ID_NON_EXISTENT);
	}

	@After
	public void setDown() {
		System.out.println("DataBase count after  "
				+ ticketService.findAll().size());
	}
}
