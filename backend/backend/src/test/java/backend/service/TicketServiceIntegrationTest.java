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
import static backend.constants.TicketConstants.NEW_TICKET_EVENDAY_ID;
import static backend.constants.TicketConstants.NEW_TICKET_EVENTSECTOR_ID;
import static backend.constants.TicketConstants.NEW_TICKET_RESERVATION_ID;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import backend.exceptions.ResourceNotFoundException;
import backend.model.Ticket;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class TicketServiceIntegrationTest {
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	EventDayService eventDayService;
	@Autowired
	EventSectorService eventSectorService;
	@Autowired
	ReservationService reservationService;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Before
	public void setUp(){
		System.out.println("DataBase count before  " + ticketService.findAll().size());
	}

	@Test
    @Transactional
	public void testSave() throws ResourceNotFoundException
	{
		Ticket t = new Ticket();
		t.setHasSeat(NEW_HAS_SEAT);
		t.setNumRow(NEW_NUM_ROW);
		t.setNumCol(NEW_NUM_COL);
		t.setEventDay(eventDayService.findOneNotDeleted(NEW_TICKET_EVENDAY_ID));
		t.setEventSector(eventSectorService.findOne(NEW_TICKET_EVENTSECTOR_ID));
		t.setReservation(reservationService.findOne(NEW_TICKET_RESERVATION_ID));
		
		int dbSizeBeforeAdd = ticketService.findAll().size();
		Ticket dbTicket = ticketService.save(t);
		assertNotNull(dbTicket);
		
		List<Ticket> tickets = ticketService.findAll();
		assertTrue(tickets.size() == dbSizeBeforeAdd + 1);
		dbTicket = tickets.get(tickets.size()-1); //get last ticket
		assertThat(dbTicket.getId()).isNotNull();
		assertThat(dbTicket.getNumCol()).isEqualTo(NEW_NUM_COL);
		assertThat(dbTicket.getNumRow()).isEqualTo(NEW_NUM_ROW);
		assertThat(dbTicket.isHasSeat()).isTrue();
		assertThat(dbTicket.getEventDay().getId()).isEqualTo(NEW_TICKET_EVENDAY_ID);
		assertThat(dbTicket.getEventSector().getId()).isEqualTo(NEW_TICKET_EVENTSECTOR_ID);
		assertThat(dbTicket.getReservation().getId()).isEqualTo(NEW_TICKET_RESERVATION_ID);
		
		ticketService.remove(dbTicket.getId());
	}
	
	@Test
	public void testFindOne() throws ResourceNotFoundException
	{
		Ticket dbTicket = ticketService.findOne(TICKET1_ID);
		assertThat(dbTicket).isNotNull();
		assertThat(dbTicket.getId()).isEqualTo(TICKET1_ID);
		assertThat(dbTicket.getNumCol()).isEqualTo(TICKET1_COL);
		assertThat(dbTicket.getNumRow()).isEqualTo(TICKET1_ROW);
		assertThat(dbTicket.getReservation().getId()).isEqualTo(TICKET1_RESERVATION_ID);
		assertThat(dbTicket.getEventDay().getId()).isEqualTo(TICKET1_EVENTDAY_ID);
		assertThat(dbTicket.getEventSector().getId()).isEqualTo(TICKET1_EV_SECTOR_ID);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFindOneBadId() throws ResourceNotFoundException
	{
		ticketService.findOne(TICKET_ID_NONEXISTENT);
	}
	
	@Test
	public void testFindAll() {
		List<Ticket> tickets = ticketService.findAll();
		assertThat(tickets).hasSize(DB_COUNT);
	}
	
	@Test
	public void testFindAllPagable()
	{
		Page<Ticket> found = ticketService.findAll(pageRequest);
		List<Ticket> tickets = found.getContent();
		assertThat(tickets).hasSize(PAGE_SIZE);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRemove() throws ResourceNotFoundException {
		Ticket t = new Ticket();
		Ticket dbTicket = ticketService.save(t);
		int dbSizeBeforeRemove = ticketService.findAll().size();
		
		ticketService.remove(dbTicket.getId());
		List<Ticket> tickets = ticketService.findAll();		
		assertThat(tickets).hasSize(dbSizeBeforeRemove - 1);		
	}
	
	@Test
	public void testFindAllByEventDayIDEventSectorID()
	{
		List<Ticket> found = ticketService.findAllByEventDayIDEventSectorID(TICKET1_EVENTDAY_ID, TICKET1_EV_SECTOR_ID);
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
	public void testFindAllByEventDayIDEventSectorIdBadInputs()
	{
		//non matching eventday and eventsector
		List<Ticket> found = ticketService.findAllByEventDayIDEventSectorID(TICKET1_EVENTDAY_ID, TICKET3_EV_SECTOR_ID);
		assertNotNull(found);
		assertTrue(found.isEmpty());
		
		//non existent eventday and eventsector id
		found = ticketService.findAllByEventDayIDEventSectorID(666L, 666L);
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}
	
	@Test
	@Transactional
	public void testFindAllByLocationDate() throws ParseException
	{
		List<Ticket> found = ticketService.findAllByLocationDate(LOCATION1_ID, df.parse(LOCATION_DATE_GOOD));

		assertNotNull(found);
		assertFalse(found.isEmpty());
		assertTrue(found.size() == 5);
		
		Ticket t1 = found.get(0);
		Ticket t3 = found.get(2);
		assertNotNull(t1);
		assertNotNull(t3);


		
		assertEquals(TICKET1_ID, t1.getId());
		assertEquals(LOCATION1_ID, t1.getEventDay().getEvent().getLocation().getId());
		assertTrue(df.parse(LOCATION_DATE_GOOD).before(t1.getEventDay().getDate()));
		
		assertEquals(TICKET3_ID, t3.getId());
		assertEquals(LOCATION1_ID, t3.getEventDay().getEvent().getLocation().getId());
		assertTrue(df.parse(LOCATION_DATE_GOOD).before(t3.getEventDay().getDate()));
	}
	

	@Test
	public void testFindAllByLocationDateBadInput() throws ParseException
	{
		//good date, no ticket for location
		List<Ticket> found = ticketService.findAllByLocationDate(LOCATION_ID_EMPTY, df.parse(LOCATION_DATE_GOOD));
		assertNotNull(found);
		assertTrue(found.isEmpty());

		//good date, non existent location
		found = ticketService.findAllByLocationDate(LOCATION_ID_NON_EXISTENT, df.parse(LOCATION_DATE_GOOD));
		assertNotNull(found);
		assertTrue(found.isEmpty());
		
		//empty date, good location
		found = ticketService.findAllByLocationDate(LOCATION1_ID, df.parse(LOCATION_DATE_EMPTY));
		assertNotNull(found);
		assertTrue(found.isEmpty());
		
		//empty date, bad location
		found = ticketService.findAllByLocationDate(LOCATION_ID_NON_EXISTENT, df.parse(LOCATION_DATE_EMPTY));
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}
	
	@Test
	@Transactional
	public void testFindAllByLocation()
	{
		List<Ticket> found = ticketService.findAllByLocation(LOCATION1_ID);
		assertNotNull(found);
		assertFalse(found.isEmpty());
		assertTrue(found.size() == 5);
		
		Ticket t1 = found.get(0);
		Ticket t3 = found.get(2);
		assertNotNull(t1);
		assertNotNull(t3);

		assertEquals(TICKET1_ID, t1.getId());
		assertEquals(LOCATION1_ID, t1.getEventDay().getEvent().getLocation().getId());
		assertEquals(TICKET3_ID, t3.getId());
		assertEquals(LOCATION1_ID, t3.getEventDay().getEvent().getLocation().getId());
	}
	
	@Test
	public void testFindAllByLocationBadId()
	{
		List<Ticket> found = ticketService.findAllByLocation(LOCATION_ID_EMPTY);
		assertNotNull(found);
		assertTrue(found.isEmpty());

		found = ticketService.findAllByLocation(LOCATION_ID_NON_EXISTENT);
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}
	
	@Test
	@Transactional
	public void testFindAllByEvent()
	{
		List<Ticket> found = ticketService.findAllByEvent(EVENT2_ID);
		assertNotNull(found);
		assertFalse(found.isEmpty());
		assertTrue(EVENT2_TICKET_COUNT == found.size());
		Ticket t3 = found.get(0);
		assertEquals(TICKET3_ID, t3.getId());
		assertEquals(EVENT2_ID, t3.getEventDay().getEvent().getId());
	}
	
	@Test
	public void testFindAllByEventBadId()
	{
		List<Ticket> found = ticketService.findAllByEvent(EVENT_ID_NONEXISTENT);
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void testFindAllByReservation()
	{
		List<Ticket> found = ticketService.findAllByReservation(RESERVATION_ID);
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
	public void testFindAllByReservationBadId()
	{
		List<Ticket> found = ticketService.findAllByReservation(RESERVATION_ID_NON_EXISTENT);
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}
	
	@After
	public void setDown(){
		System.out.println("DataBase count after  " + ticketService.findAll().size());
	}

}
