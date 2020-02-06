package backend.repository;

import static backend.constants.TicketConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import backend.model.Ticket;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TicketRepositoryIntegrationTest {

	@Autowired
	TicketRepository ticketRepository;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	public void testFindAllByEventDayIDEventSectorID()
	{
		List<Ticket> found = ticketRepository.findAllByEventDayIDEventSectorID(TICKET1_EVENTDAY_ID, TICKET1_EV_SECTOR_ID);
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
		List<Ticket> found = ticketRepository.findAllByEventDayIDEventSectorID(TICKET1_EVENTDAY_ID, TICKET3_EV_SECTOR_ID);
		assertNotNull(found);
		assertTrue(found.isEmpty());
		
		//non existent eventday and eventsector id
		found = ticketRepository.findAllByEventDayIDEventSectorID(666L, 666L);
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void testFindAllByLocationDate() throws ParseException
	{
		List<Ticket> found = ticketRepository.findAllByLocationDate(LOCATION1_ID, df.parse(LOCATION_DATE_GOOD));

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
		List<Ticket> found = ticketRepository.findAllByLocationDate(LOCATION_ID_EMPTY, df.parse(LOCATION_DATE_GOOD));
		assertNotNull(found);
		assertTrue(found.isEmpty());

		//good date, non existent location
		found = ticketRepository.findAllByLocationDate(LOCATION_ID_NON_EXISTENT, df.parse(LOCATION_DATE_GOOD));
		assertNotNull(found);
		assertTrue(found.isEmpty());
		
		//empty date, good location
		found = ticketRepository.findAllByLocationDate(LOCATION1_ID, df.parse(LOCATION_DATE_EMPTY));
		assertNotNull(found);
		assertTrue(found.isEmpty());
		
		//empty date, bad location
		found = ticketRepository.findAllByLocationDate(LOCATION_ID_NON_EXISTENT, df.parse(LOCATION_DATE_EMPTY));
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void testFindAllByLocation()
	{
		List<Ticket> found = ticketRepository.findAllByLocation(LOCATION1_ID);
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
		List<Ticket> found = ticketRepository.findAllByLocation(LOCATION_ID_EMPTY);
		assertNotNull(found);
		assertTrue(found.isEmpty());

		found = ticketRepository.findAllByLocation(LOCATION_ID_NON_EXISTENT);
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void testFindAllByEvent()
	{
		List<Ticket> found = ticketRepository.findAllByEvent(EVENT2_ID);
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
		List<Ticket> found = ticketRepository.findAllByEvent(EVENT_ID_NONEXISTENT);
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void testFindAllByReservation()
	{
		List<Ticket> found = ticketRepository.findAllByReservation(RESERVATION_ID);
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
		List<Ticket> found = ticketRepository.findAllByReservation(RESERVATION_ID_NON_EXISTENT);
		assertNotNull(found);
		assertTrue(found.isEmpty());
	}
}
