package backend.service;

import static backend.constants.ChartConstants.AVERAGE_NAME;
import static backend.constants.ChartConstants.END_DATE_BAD;
import static backend.constants.ChartConstants.END_DATE_EMPTY;
import static backend.constants.ChartConstants.END_DATE_GOOD;
import static backend.constants.ChartConstants.EVENT1_NAME;
import static backend.constants.ChartConstants.EVENT2_NAME;
import static backend.constants.ChartConstants.INCOME_EVENT1;
import static backend.constants.ChartConstants.INCOME_EVENT2;
import static backend.constants.ChartConstants.INCOME_EVENT_AVERAGE;
import static backend.constants.ChartConstants.INCOME_LOCATION1;
import static backend.constants.ChartConstants.INCOME_LOCATION1_INTERVAL;
import static backend.constants.ChartConstants.INCOME_LOCATION_AVG;
import static backend.constants.ChartConstants.INFO_ALLTIME_INCOME;
import static backend.constants.ChartConstants.INFO_ALLTIME_TICKETS;
import static backend.constants.ChartConstants.INFO_NUM_ADMIN;
import static backend.constants.ChartConstants.INFO_NUM_EVENTS;
import static backend.constants.ChartConstants.INFO_NUM_USERS;
import static backend.constants.ChartConstants.LOCATION1_NAME;
import static backend.constants.ChartConstants.START_DATE_BAD;
import static backend.constants.ChartConstants.START_DATE_EMPTY;
import static backend.constants.ChartConstants.START_DATE_GOOD;
import static backend.constants.ChartConstants.TICKETS_SOLD_AVERAGE;
import static backend.constants.ChartConstants.TICKETS_SOLD_EVENT1;
import static backend.constants.ChartConstants.TICKETS_SOLD_EVENT2;
import static backend.constants.ChartConstants.TICKETS_SOLD_LOCATION1;
import static backend.constants.ChartConstants.TICKETS_SOLD_LOCATION1_INTERVAL;
import static backend.constants.ChartConstants.TICKETS_SOLD_LOCATION_AVG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import backend.dto.charts.ChartEventTicketsSoldDTO;
import backend.dto.charts.ChartIncomeEventsDTO;
import backend.dto.charts.ChartIncomeLocationsDTO;
import backend.dto.charts.ChartLocationTicketsSoldDTO;
import backend.dto.charts.DateIntervalDTO;
import backend.dto.charts.SystemInformationsDTO;
import backend.exceptions.BadRequestException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ChartServiceIntegrationTest {

	@Autowired
	ChartService chartService;

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void testSystemInformations() {
		SystemInformationsDTO info = chartService.systemInformations();
		assertNotNull(info);
		assertEquals(INFO_NUM_EVENTS, info.getNumberOfEvents());
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
		assertTrue(INCOME_EVENT_AVERAGE == info.get(info.size()-1).getIncome());
	}

	@Test
	public void testGetIncomesByEventsGoodInterval() throws ParseException,
			BadRequestException {
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_GOOD), df.parse(END_DATE_GOOD));

		List<ChartIncomeEventsDTO> info = chartService.incomeByEvents(interval);
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
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_EMPTY), df.parse(END_DATE_EMPTY));

		List<ChartIncomeEventsDTO> info = chartService.incomeByEvents(interval);
		assertNotNull(info);
		assertTrue(info.size() == 0);
	}

	@Test(expected = BadRequestException.class)
	public void testGetIncomesByEventsBadInterval() throws ParseException,
			BadRequestException {
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_BAD), df.parse(END_DATE_BAD));
		chartService.incomeByEvents(interval);

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
		assertTrue(TICKETS_SOLD_AVERAGE == info.get(info.size()-1).getTicketsSold());
	}

	@Test
	public void testGetTicketsSoldByEventsGoodInterval() throws ParseException,
			BadRequestException {
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_GOOD), df.parse(END_DATE_GOOD));

		List<ChartEventTicketsSoldDTO> info = chartService
				.soldTicketsByEvents(interval);
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
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_EMPTY), df.parse(END_DATE_EMPTY));

		List<ChartEventTicketsSoldDTO> info = chartService
				.soldTicketsByEvents(interval);
		assertNotNull(info);
		assertTrue(info.size() == 0);
	}

	@Test(expected = BadRequestException.class)
	public void testGetTicketsSoldByEventsBadInterval() throws ParseException,
			BadRequestException {
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_BAD), df.parse(END_DATE_BAD));

		chartService.soldTicketsByEvents(interval);

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
		System.out.println("income  avg" + INCOME_LOCATION_AVG + " " + info.get(info.size()-1).getIncome());
		assertTrue(INCOME_LOCATION_AVG == info.get(info.size() - 1).getIncome());
	}

	@Test
	public void testGetIncomesByLocationsGoodInterval() throws ParseException,
			BadRequestException {
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_GOOD), df.parse(END_DATE_GOOD));

		List<ChartIncomeLocationsDTO> info = chartService
				.incomeByLocations(interval);
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
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_EMPTY), df.parse(END_DATE_EMPTY));

		List<ChartIncomeLocationsDTO> info = chartService
				.incomeByLocations(interval);
		assertNotNull(info);
		assertTrue(info.size() == 0);
	}

	@Test(expected = BadRequestException.class)
	public void testGetIncomesByLocationsBadInterval() throws ParseException,
			BadRequestException {
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_BAD), df.parse(END_DATE_BAD));

		chartService.incomeByLocations(interval);

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
		assertEquals(AVERAGE_NAME.toLowerCase(), info.get(info.size() - 1).getLocationName()
				.toLowerCase());
		System.out.println("Tickets sold avg" + TICKETS_SOLD_LOCATION_AVG + " " + info.get(info.size()-1).getTicketsSold());
		assertTrue(TICKETS_SOLD_LOCATION_AVG == info.get(info.size() - 1).getTicketsSold());
	}

	@Test
	public void testGetTicketsSoldByLocationsGoodInterval()
			throws ParseException, BadRequestException {
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_GOOD), df.parse(END_DATE_GOOD));

		List<ChartLocationTicketsSoldDTO> info = chartService
				.soldTicketsByLocations(interval);
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
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_EMPTY), df.parse(END_DATE_EMPTY));

		List<ChartLocationTicketsSoldDTO> info = chartService
				.soldTicketsByLocations(interval);
		assertNotNull(info);
		assertTrue(info.size() == 0);
	}

	@Test(expected = BadRequestException.class)
	public void testGetTicketsSoldByLocationsBadInterval()
			throws ParseException, BadRequestException {
		DateIntervalDTO interval = new DateIntervalDTO(
				df.parse(START_DATE_BAD), df.parse(END_DATE_BAD));

		chartService.soldTicketsByLocations(interval);
	}

}
