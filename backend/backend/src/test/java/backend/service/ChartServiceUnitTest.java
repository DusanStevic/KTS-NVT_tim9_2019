package backend.service;

import static backend.constants.ChartConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import backend.dto.charts.ChartEventTicketsSoldDTO;
import backend.dto.charts.ChartIncomeEventsDTO;
import backend.dto.charts.ChartIncomeLocationsDTO;
import backend.dto.charts.ChartLocationTicketsSoldDTO;
import backend.dto.charts.DateIntervalDTO;
import backend.dto.charts.SystemInformationsDTO;
import backend.exceptions.BadRequestException;
import backend.model.Address;
import backend.model.Administrator;
import backend.model.Authority;
import backend.model.Event;
import backend.model.EventDay;
import backend.model.EventSector;
import backend.model.EventStatus;
import backend.model.EventType;
import backend.model.Hall;
import backend.model.Location;
import backend.model.RegisteredUser;
import backend.model.Reservation;
import backend.model.Role;
import backend.model.Sector;
import backend.model.SittingSector;
import backend.model.StandingSector;
import backend.model.SysAdmin;
import backend.model.Ticket;
import backend.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ChartServiceUnitTest {
	@Autowired
	ChartService chartService;

	@MockBean
	UserService userServiceMocked;

	@MockBean
	TicketService ticketServiceMocked;

	@MockBean
	EventService eventServiceMocked;

	@MockBean
	LocationService locationServiceMocked;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static DateIntervalDTO intervalGood;
	private static DateIntervalDTO intervalEmpty;
	private static DateIntervalDTO intervalBad;

	@Before
	public void setup() throws ParseException
	// mocking the whole database
	{
		Authority regAuth = new Authority();
		regAuth.setRole(Role.ROLE_REGISTERED_USER);
		Authority adminAuth = new Authority();
		adminAuth.setRole(Role.ROLE_ADMIN);
		Authority sysAuth = new Authority();
		sysAuth.setRole(Role.ROLE_SYS_ADMIN);

		List<Authority> registered = new ArrayList<Authority>();
		registered.add(regAuth);
		List<Authority> admins = new ArrayList<Authority>();
		admins.add(adminAuth);
		List<Authority> syss = new ArrayList<Authority>();
		syss.add(sysAuth);

		RegisteredUser user1 = new backend.model.RegisteredUser(), user2 = new RegisteredUser();
		Administrator admin = new Administrator();
		SysAdmin sys = new SysAdmin();
		user1.setAuthorities(registered);
		user2.setAuthorities(registered);
		admin.setAuthorities(admins);
		sys.setAuthorities(syss);

		Location location;
		Hall h1;
		Sector s1, s2;
		Event event1 = null, event2 = null;
		EventDay day1, day2;
		EventSector es1, es2, es3, es4;
		Reservation r1, r2, r3, r4;
		Ticket t1, t2, t3, t4, t5;
		Set<Sector> sectors = new HashSet<Sector>();
		Set<EventSector> eventSectors1 = new HashSet<EventSector>();
		Set<EventSector> eventSectors2 = new HashSet<EventSector>();
		Set<Hall> halls = new HashSet<Hall>();
		Set<EventDay> eventDays1 = new HashSet<EventDay>();
		Set<EventDay> eventDays2 = new HashSet<EventDay>();
		Set<Ticket> tick1 = new HashSet<Ticket>();
		Set<Ticket> tick2 = new HashSet<Ticket>();

		Set<Ticket> res1 = new HashSet<Ticket>();
		Set<Ticket> res2 = new HashSet<Ticket>();
		Set<Ticket> res3 = new HashSet<Ticket>();
		Set<Ticket> res4 = new HashSet<Ticket>();

		location = new Location(1L, "SPENS NS", "", halls, new Address(),
				new Timestamp(15L));

		h1 = new Hall(1L, "Main hall", sectors, location, false);

		s1 = new StandingSector(1L, "S1_1", 500, h1);
		s2 = new SittingSector(2L, "S2_2", 10, 10, h1);
		sectors.add(s1);
		sectors.add(s2);

		es1 = new EventSector(1L, 500, event1, s1, false);
		es2 = new EventSector(2L, 700, event1, s2, false);
		es3 = new EventSector(3L, 400, event2, s1, false);
		es4 = new EventSector(4L, 250, event2, s2, false);
		eventSectors1.add(es1);
		eventSectors1.add(es2);
		eventSectors2.add(es3);
		eventSectors2.add(es4);

		day1 = new EventDay(1L, "Uniparty d1.", "", df.parse("2020-03-03"),
				EventStatus.ACTIVE, tick1, event1, false);
		day2 = new EventDay(2L, "Fol d1.", "", df.parse("2020-05-03"),
				EventStatus.ACTIVE, tick2, event2, false);
		eventDays1.add(day1);
		eventDays2.add(day2);

		event1 = new Event(1L, "UNIPARTY", "", EventType.CONCERT,
				df.parse("2020-03-03"), df.parse("2020-03-03"), 5, 3, location,
				new HashSet<String>(), new HashSet<String>(), eventSectors1,
				eventDays1, false);
		event2 = new Event(2L, "FolkFest", "", EventType.CULTURE,
				df.parse("2020-05-03"), df.parse("2020-05-03"), 5, 3, location,
				new HashSet<String>(), new HashSet<String>(), eventSectors2,
				eventDays2, false);

		r1 = new Reservation(1L, false, df.parse("2019-11-05"), res1, user1);
		r2 = new Reservation(2L, false, df.parse("2019-11-05"), res2, user1);
		r3 = new Reservation(3L, false, df.parse("2019-11-05"), res3, user2);
		r4 = new Reservation(4L, false, df.parse("2019-11-05"), res4, user2);

		t1 = new Ticket(1L, true, 1, 1, day1, r1, es2);
		t2 = new Ticket(2L, true, 1, 2, day1, r2, es2);
		t3 = new Ticket(3L, true, 1, 1, day2, r3, es4);
		t4 = new Ticket(4L, true, 1, 2, day2, r4, es4);
		t5 = new Ticket(5L, false, 0, 0, day2, r4, es3);

		tick1.add(t1);
		tick1.add(t2);
		tick2.add(t3);
		tick2.add(t4);
		tick2.add(t5);

		res1.add(t1);
		res2.add(t2);
		res3.add(t3);
		res4.add(t4);
		res4.add(t5);

		List<User> allUsers = new ArrayList<User>();
		allUsers.add(user1);
		allUsers.add(user2);
		allUsers.add(admin);
		allUsers.add(sys);

		List<Event> allEvents = new ArrayList<Event>();
		allEvents.add(event1);
		allEvents.add(event2);

		List<Event> goodIntervalEvents = new ArrayList<Event>();
		goodIntervalEvents.add(event1);

		List<Location> allLocations = new ArrayList<Location>();
		allLocations.add(location);

		List<Ticket> allTickets = new ArrayList<Ticket>();
		allTickets.add(t1);
		allTickets.add(t2);
		allTickets.add(t3);
		allTickets.add(t4);
		allTickets.add(t5);

		intervalGood = new DateIntervalDTO(df.parse(START_DATE_GOOD),
				df.parse(END_DATE_GOOD));
		intervalEmpty = new DateIntervalDTO(df.parse(START_DATE_EMPTY),
				df.parse(END_DATE_EMPTY));
		intervalBad = new DateIntervalDTO(df.parse(START_DATE_BAD),
				df.parse(END_DATE_BAD));

		List<Ticket> ticketsEvent1 = new ArrayList<Ticket>();
		ticketsEvent1.addAll(tick1);

		List<Ticket> ticketsEvent2 = new ArrayList<Ticket>();
		ticketsEvent2.addAll(tick2);

		when(userServiceMocked.findAll()).thenReturn(allUsers);
		when(ticketServiceMocked.findAll()).thenReturn(allTickets);
		when(ticketServiceMocked.findAllByEvent(1L)).thenReturn(ticketsEvent1);
		when(ticketServiceMocked.findAllByEvent(2L)).thenReturn(ticketsEvent2);
		when(ticketServiceMocked.findAllByLocation(1L)).thenReturn(allTickets);
		when(eventServiceMocked.findAll()).thenReturn(allEvents);
		when(eventServiceMocked.findByInterval(intervalGood)).thenReturn(
				goodIntervalEvents);
		when(eventServiceMocked.findByInterval(intervalEmpty)).thenReturn(
				new ArrayList<Event>());
		when(eventServiceMocked.findByInterval(intervalBad)).thenReturn(
				new ArrayList<Event>());
		when(locationServiceMocked.findAll()).thenReturn(allLocations);

	}

	@Test
	public void testSystemInformations() {
		SystemInformationsDTO info = chartService.systemInformations();
		assertNotNull(info);
		assertEquals(INFO_NUM_EVENTS_UNIT, info.getNumberOfEvents());
		assertEquals(INFO_NUM_ADMIN, info.getNumberOfAdmins());
		assertEquals(INFO_NUM_USERS, info.getNumberOfUsers());
		assertTrue(INFO_ALLTIME_INCOME == info.getAllTimeIncome());
		assertTrue(INFO_ALLTIME_TICKETS == info.getAllTimeTickets());
	}

	@Test
	public void testIncomeByEvents() {
		List<ChartIncomeEventsDTO> info = chartService.incomeByEvents();
		assertNotNull(info);
		assertTrue(info.size()>0);
		assertEquals(EVENT1_NAME.toLowerCase(), info.get(0).getEventName()
				.toLowerCase());
		assertTrue(INCOME_EVENT1 == info.get(0).getIncome());
		assertEquals(EVENT2_NAME.toLowerCase(), info.get(1).getEventName()
				.toLowerCase());
		assertTrue(INCOME_EVENT2 == info.get(1).getIncome());
		assertEquals(AVERAGE_NAME.toLowerCase(), info.get(info.size()-1).getEventName()
				.toLowerCase());
		assertTrue(INCOME_EVENT_AVERAGE_UNIT == info.get(info.size()-1).getIncome());
	}

	@Test
	public void testGetIncomesByEventsGoodInterval() throws ParseException,
			BadRequestException {

		List<ChartIncomeEventsDTO> info = chartService
				.incomeByEvents(intervalGood);
		assertNotNull(info);
		assertEquals(EVENT1_NAME.toLowerCase(), info.get(0).getEventName()
				.toLowerCase());
		assertTrue(INCOME_EVENT1 == info.get(0).getIncome());
		// average is the same as event1 -> only 1 event
		assertEquals(AVERAGE_NAME.toLowerCase(), info.get(1).getEventName()
				.toLowerCase());
		assertTrue(INCOME_EVENT1 == info.get(1).getIncome());
	}

	@Test
	public void testGetIncomesByEventsEmptyInterval() throws ParseException,
			BadRequestException {
		intervalEmpty = new DateIntervalDTO(df.parse(START_DATE_EMPTY),
				df.parse(END_DATE_EMPTY));

		List<ChartIncomeEventsDTO> info = chartService
				.incomeByEvents(intervalEmpty);
		assertNotNull(info);
		assertTrue(info.size() == 0);
	}

	@Test(expected = BadRequestException.class)
	public void testGetIncomesByEventsBadInterval() throws ParseException,
			BadRequestException {
		intervalBad = new DateIntervalDTO(df.parse(START_DATE_BAD),
				df.parse(END_DATE_BAD));
		chartService.incomeByEvents(intervalBad);

	}

	@Test
	public void testSoldTicketsByEvent() {
		List<ChartEventTicketsSoldDTO> info = chartService
				.soldTicketsByEvents();
		assertNotNull(info);
		assertTrue(info.size()>0);
		assertEquals(EVENT1_NAME.toLowerCase(), info.get(0).getEventName()
				.toLowerCase());
		assertTrue(TICKETS_SOLD_EVENT1 == info.get(0).getTicketsSold());
		assertEquals(EVENT2_NAME.toLowerCase(), info.get(1).getEventName()
				.toLowerCase());
		assertTrue(TICKETS_SOLD_EVENT2 == info.get(1).getTicketsSold());
		assertEquals(AVERAGE_NAME.toLowerCase(), info.get(info.size()-1).getEventName()
				.toLowerCase());
		assertTrue(TICKETS_SOLD_AVERAGE_UNIT == info.get(info.size()-1).getTicketsSold());
	}

	@Test
	public void testGetTicketsSoldByEventsGoodInterval() throws ParseException,
			BadRequestException {

		List<ChartEventTicketsSoldDTO> info = chartService
				.soldTicketsByEvents(intervalGood);
		assertNotNull(info);
		assertEquals(EVENT1_NAME.toLowerCase(), info.get(0).getEventName()
				.toLowerCase());
		assertTrue(TICKETS_SOLD_EVENT1 == info.get(0).getTicketsSold());
		// average is the same as event1 -> only 1 event
		assertEquals(AVERAGE_NAME.toLowerCase(), info.get(1).getEventName()
				.toLowerCase());
		assertTrue(TICKETS_SOLD_EVENT1 == info.get(1).getTicketsSold());
	}

	@Test
	public void testGetTicketsSoldByEventsEmptyInterval()
			throws ParseException, BadRequestException {
		intervalEmpty = new DateIntervalDTO(df.parse(START_DATE_EMPTY),
				df.parse(END_DATE_EMPTY));

		List<ChartEventTicketsSoldDTO> info = chartService
				.soldTicketsByEvents(intervalEmpty);
		assertNotNull(info);
		assertTrue(info.size() == 0);
	}

	@Test(expected = BadRequestException.class)
	public void testGetTicketsSoldByEventsBadInterval() throws ParseException,
			BadRequestException {
		intervalBad = new DateIntervalDTO(df.parse(START_DATE_BAD),
				df.parse(END_DATE_BAD));

		chartService.soldTicketsByEvents(intervalBad);

	}

	@Test
	public void testIncomeByLocations() {
		List<ChartIncomeLocationsDTO> info = chartService.incomeByLocations();
		assertNotNull(info);
		assertTrue(info.size()>0);
		assertEquals(LOCATION1_NAME.toLowerCase(), info.get(0)
				.getLocationName().toLowerCase());
		assertTrue(INCOME_LOCATION1 == info.get(0).getIncome());
		assertEquals(AVERAGE_NAME.toLowerCase(), info.get(info.size() - 1).getLocationName()
				.toLowerCase());
		assertTrue(INCOME_LOCATION1 == info.get(info.size() - 1).getIncome());
	}

	@Test
	public void testGetIncomesByLocationsGoodInterval() throws ParseException,
			BadRequestException {

		List<ChartIncomeLocationsDTO> info = chartService
				.incomeByLocations(intervalGood);
		assertNotNull(info);
		assertEquals(LOCATION1_NAME.toLowerCase(), info.get(0)
				.getLocationName().toLowerCase());
		assertTrue(INCOME_LOCATION1_INTERVAL == info.get(0).getIncome());
		// average is the same as event1 -> only 1 event
		assertEquals(AVERAGE_NAME.toLowerCase(), info.get(1).getLocationName()
				.toLowerCase());
		assertTrue(INCOME_LOCATION1_INTERVAL == info.get(1).getIncome());
	}

	@Test
	public void testGetIncomesByLocationsEmptyInterval() throws ParseException,
			BadRequestException {
		intervalEmpty = new DateIntervalDTO(df.parse(START_DATE_EMPTY),
				df.parse(END_DATE_EMPTY));

		List<ChartIncomeLocationsDTO> info = chartService
				.incomeByLocations(intervalEmpty);
		assertNotNull(info);
		assertTrue(info.size() == 0);
	}

	@Test(expected = BadRequestException.class)
	public void testGetIncomesByLocationsBadInterval() throws ParseException,
			BadRequestException {
		intervalBad = new DateIntervalDTO(df.parse(START_DATE_BAD),
				df.parse(END_DATE_BAD));

		chartService.incomeByLocations(intervalBad);

	}

	@Test
	public void testSoldTicketsByLocations() {

		List<ChartLocationTicketsSoldDTO> info = chartService
				.soldTicketsByLocations();
		assertNotNull(info);
		assertTrue(info.size()>0);
		assertEquals(LOCATION1_NAME.toLowerCase(), info.get(0)
				.getLocationName().toLowerCase());
		assertTrue(TICKETS_SOLD_LOCATION1 == info.get(0).getTicketsSold());
		assertEquals(AVERAGE_NAME.toLowerCase(), info.get(info.size()-1).getLocationName()
				.toLowerCase());
		assertTrue(TICKETS_SOLD_LOCATION1 == info.get(info.size()-1).getTicketsSold());
	}

	@Test
	public void testGetTicketsSoldByLocationsGoodInterval()
			throws ParseException, BadRequestException {

		List<ChartLocationTicketsSoldDTO> info = chartService
				.soldTicketsByLocations(intervalGood);
		assertNotNull(info);
		assertEquals(LOCATION1_NAME.toLowerCase(), info.get(0)
				.getLocationName().toLowerCase());
		assertTrue(TICKETS_SOLD_LOCATION1_INTERVAL == info.get(0)
				.getTicketsSold());
		// average is the same as event1 -> only 1 event
		assertEquals(AVERAGE_NAME.toLowerCase(), info.get(1).getLocationName()
				.toLowerCase());
		assertTrue(TICKETS_SOLD_LOCATION1_INTERVAL == info.get(1)
				.getTicketsSold());
	}

	@Test
	public void testGetTicketsSoldByLocationsEmptyInterval()
			throws ParseException, BadRequestException {
		intervalEmpty = new DateIntervalDTO(df.parse(START_DATE_EMPTY),
				df.parse(END_DATE_EMPTY));

		List<ChartLocationTicketsSoldDTO> info = chartService
				.soldTicketsByLocations(intervalEmpty);
		assertNotNull(info);
		assertTrue(info.size() == 0);
	}

	@Test(expected = BadRequestException.class)
	public void testGetTicketsSoldByLocationsBadInterval()
			throws ParseException, BadRequestException {
		intervalBad = new DateIntervalDTO(df.parse(START_DATE_BAD),
				df.parse(END_DATE_BAD));

		chartService.soldTicketsByLocations(intervalBad);
	}

}
