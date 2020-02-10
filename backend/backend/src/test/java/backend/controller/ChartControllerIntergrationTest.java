package backend.controller;

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

import backend.dto.charts.ChartEventTicketsSoldDTO;
import backend.dto.charts.ChartIncomeEventsDTO;
import backend.dto.charts.ChartIncomeLocationsDTO;
import backend.dto.charts.ChartLocationTicketsSoldDTO;
import backend.dto.charts.DateIntervalDTO;
import backend.dto.charts.SystemInformationsDTO;
import backend.security.auth.JwtAuthenticationRequest;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ChartControllerIntergrationTest {

	@Autowired
	 TestRestTemplate restTemplate;

	private String accessToken;
	private  HttpHeaders headers = new HttpHeaders();
	private SimpleDateFormat df;
	
	@Before
	public void login() {
		ResponseEntity<String> login = 
				restTemplate.postForEntity("/auth/login", 
						new JwtAuthenticationRequest("sys", "admin"), 
						String.class);
		accessToken = login.getBody();
		headers.add("Authorization", "Bearer "+accessToken);
		df = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	@Test
	public void testGetSysInfo()
	{
		ResponseEntity<SystemInformationsDTO> responseEntity = restTemplate.exchange("/api/charts/sysinfo", 
					HttpMethod.GET, new HttpEntity<Object>(headers), SystemInformationsDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		SystemInformationsDTO info = responseEntity.getBody();
		assertNotNull(info);
		assertEquals(INFO_NUM_ADMIN, info.getNumberOfAdmins());
		assertEquals(INFO_NUM_USERS, info.getNumberOfUsers());
		assertEquals(INFO_NUM_EVENTS, info.getNumberOfEvents());
		assertTrue(INFO_ALLTIME_INCOME == info.getAllTimeIncome());
		assertTrue(INFO_ALLTIME_TICKETS == info.getAllTimeTickets());
	}
	
	//Trying to get infos as unregisted and registered user, and as admin
	@Test
	public void testGetSysInfoBadUser()
	{
		//Non user
		ResponseEntity<SystemInformationsDTO> responseEntity = restTemplate.getForEntity("/api/charts/sysinfo", SystemInformationsDTO.class);
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		
		//Admin	i admin moze da
		/*
		ResponseEntity<String> login = 
				restTemplate.postForEntity("/auth/login", 
						new JwtAuthenticationRequest("admin", "admin"), 
						String.class);
		String accessTokenAdmin = login.getBody();
		HttpHeaders headersAdmin = new HttpHeaders();
		headersAdmin.add("Authorization", "Bearer "+accessTokenAdmin);

		responseEntity = restTemplate.exchange("/api/charts/sysinfo", 
					HttpMethod.GET, new HttpEntity<Object>(headersAdmin), SystemInformationsDTO.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
		*/
		//User
		ResponseEntity<String> login = restTemplate.postForEntity("/auth/login", 
						new JwtAuthenticationRequest("user", "user"), 
						String.class);
		String accessTokenUser = login.getBody();
		HttpHeaders headersUser = new HttpHeaders();
		headersUser.add("Authorization", "Bearer "+accessTokenUser);

		responseEntity = restTemplate.exchange("/api/charts/sysinfo", 
					HttpMethod.GET, new HttpEntity<Object>(headersUser), SystemInformationsDTO.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	
	@Test
	public void testGetEventIncomes()
	{
		ResponseEntity<ChartIncomeEventsDTO[]> responseEntity = restTemplate.exchange("/api/charts/event_incomes", 
				HttpMethod.GET, new HttpEntity<Object>(headers), ChartIncomeEventsDTO[].class);
	
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		ChartIncomeEventsDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertTrue(info.length>0);
		assertEquals(EVENT1_NAME.toLowerCase(), info[0].getEventName().toLowerCase());
		assertTrue(INCOME_EVENT1 == info[0].getIncome());
		assertEquals(EVENT2_NAME.toLowerCase(), info[1].getEventName().toLowerCase());
		assertTrue(INCOME_EVENT2 == info[1].getIncome());
		assertEquals(AVERAGE_NAME.toLowerCase(), info[info.length - 1].getEventName().toLowerCase());
		assertTrue(INCOME_EVENT_AVERAGE == info[info.length - 1].getIncome());
	}
	

	@Test
	public void testGetIncomesByEventsGoodInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_GOOD), df.parse(END_DATE_GOOD));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<ChartIncomeEventsDTO[]> responseEntity = restTemplate
				.exchange("/api/charts/event_incomes/interval", HttpMethod.PUT, httpEntity, ChartIncomeEventsDTO[].class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ChartIncomeEventsDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertEquals(EVENT1_NAME.toLowerCase(), info[0].getEventName().toLowerCase());
		assertTrue(INCOME_EVENT1 == info[0].getIncome());
		//average is the same as event1 -> only 1 event
		assertEquals(AVERAGE_NAME.toLowerCase(), info[1].getEventName().toLowerCase());
		assertTrue(INCOME_EVENT1 == info[1].getIncome());
	}
	
	@Test
	public void testGetIncomesByEventsEmptyInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_EMPTY), df.parse(END_DATE_EMPTY));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<ChartIncomeEventsDTO[]> responseEntity = restTemplate
				.exchange("/api/charts/event_incomes/interval", HttpMethod.PUT, httpEntity, ChartIncomeEventsDTO[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		ChartIncomeEventsDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertTrue(info.length == 0);
	}
	
	@Test()
	public void testGetIncomesByEventsBadInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_BAD), df.parse(END_DATE_BAD));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/api/charts/event_tickets_sold/interval", HttpMethod.PUT, httpEntity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Start date must be after end date"));
		
	}
	
	
	@Test
	public void testGetTicketsSoldByEvents()
	{
		ResponseEntity<ChartEventTicketsSoldDTO[]> responseEntity = restTemplate.exchange("/api/charts/event_tickets_sold", 
				HttpMethod.GET, new HttpEntity<Object>(headers), ChartEventTicketsSoldDTO[].class);
	
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ChartEventTicketsSoldDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertTrue(info.length>0);
		assertEquals(EVENT1_NAME.toLowerCase(), info[0].getEventName().toLowerCase());
		assertTrue(TICKETS_SOLD_EVENT1 == info[0].getTicketsSold());
		assertEquals(EVENT2_NAME.toLowerCase(), info[1].getEventName().toLowerCase());
		assertTrue(TICKETS_SOLD_EVENT2 == info[1].getTicketsSold());
		assertEquals(AVERAGE_NAME.toLowerCase(), info[info.length - 1].getEventName().toLowerCase());
		assertTrue(TICKETS_SOLD_AVERAGE == info[info.length - 1].getTicketsSold());
	}
	
	@Test
	public void testGetTicketsSoldByEventsGoodInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_GOOD), df.parse(END_DATE_GOOD));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<ChartEventTicketsSoldDTO[]> responseEntity = restTemplate
				.exchange("/api/charts/event_tickets_sold/interval", HttpMethod.PUT, httpEntity, ChartEventTicketsSoldDTO[].class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ChartEventTicketsSoldDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertEquals(EVENT1_NAME.toLowerCase(), info[0].getEventName().toLowerCase());
		assertTrue(TICKETS_SOLD_EVENT1 == info[0].getTicketsSold());
		//average is the same as event1 -> only 1 event
		assertEquals(AVERAGE_NAME.toLowerCase(), info[1].getEventName().toLowerCase());
		assertTrue(TICKETS_SOLD_EVENT1 == info[1].getTicketsSold());
	}
	
	@Test
	public void testGetTicketsSoldByEventsEmptyInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_EMPTY), df.parse(END_DATE_EMPTY));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<ChartEventTicketsSoldDTO[]> responseEntity = restTemplate
				.exchange("/api/charts/event_tickets_sold/interval", HttpMethod.PUT, httpEntity, ChartEventTicketsSoldDTO[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		ChartEventTicketsSoldDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertTrue(info.length == 0);
	}
	
	@Test()
	public void testGetTicketsSoldByEventsBadInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_BAD), df.parse(END_DATE_BAD));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/api/charts/event_tickets_sold/interval", HttpMethod.PUT, httpEntity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Start date must be after end date"));
		
	}
	
	@Test
	public void testGetLocationIncomes()
	{
		ResponseEntity<ChartIncomeLocationsDTO[]> responseEntity = restTemplate.exchange("/api/charts/location_incomes", 
				HttpMethod.GET, new HttpEntity<Object>(headers), ChartIncomeLocationsDTO[].class);
	
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		ChartIncomeLocationsDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertTrue(info.length>0);
		assertEquals(LOCATION1_NAME.toLowerCase(), info[0].getLocationName().toLowerCase());
		assertTrue(INCOME_LOCATION1 == info[0].getIncome());
		assertEquals(AVERAGE_NAME.toLowerCase(), info[info.length - 1].getLocationName().toLowerCase());
		assertTrue(INCOME_LOCATION_AVG == info[info.length - 1].getIncome());
	}
	

	@Test
	public void testGetIncomesByLocationsGoodInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_GOOD), df.parse(END_DATE_GOOD));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<ChartIncomeLocationsDTO[]> responseEntity = restTemplate
				.exchange("/api/charts/location_incomes/interval", HttpMethod.PUT, httpEntity, ChartIncomeLocationsDTO[].class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ChartIncomeLocationsDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertEquals(LOCATION1_NAME.toLowerCase(), info[0].getLocationName().toLowerCase());
		assertTrue(INCOME_LOCATION1_INTERVAL == info[0].getIncome());
		//average is the same as event1 -> only 1 event
		assertEquals(AVERAGE_NAME.toLowerCase(), info[1].getLocationName().toLowerCase());
		assertTrue(INCOME_LOCATION1_INTERVAL == info[1].getIncome());
	}
	
	@Test
	public void testGetIncomesByLocationsEmptyInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_EMPTY), df.parse(END_DATE_EMPTY));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<ChartIncomeLocationsDTO[]> responseEntity = restTemplate
				.exchange("/api/charts/location_incomes/interval", HttpMethod.PUT, httpEntity, ChartIncomeLocationsDTO[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		ChartIncomeLocationsDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertTrue(info.length == 0);
	}
	
	@Test()
	public void testGetIncomesByLocationsBadInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_BAD), df.parse(END_DATE_BAD));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/api/charts/location_tickets_sold/interval", HttpMethod.PUT, httpEntity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Start date must be after end date"));
		
	}
	
	@Test
	public void testGetTicketsSoldByLocations()
	{
		ResponseEntity<ChartLocationTicketsSoldDTO[]> responseEntity = restTemplate.exchange("/api/charts/location_tickets_sold", 
				HttpMethod.GET, new HttpEntity<Object>(headers), ChartLocationTicketsSoldDTO[].class);
	
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ChartLocationTicketsSoldDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertTrue(info.length>0);
		assertEquals(LOCATION1_NAME.toLowerCase(), info[0].getLocationName().toLowerCase());
		assertTrue(TICKETS_SOLD_LOCATION1 == info[0].getTicketsSold());
		assertEquals(AVERAGE_NAME.toLowerCase(), info[info.length - 1].getLocationName().toLowerCase());
		assertTrue(TICKETS_SOLD_LOCATION_AVG == info[info.length - 1].getTicketsSold());
	}
	

	@Test
	public void testGetTicketsSoldByLocationsGoodInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_GOOD), df.parse(END_DATE_GOOD));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<ChartLocationTicketsSoldDTO[]> responseEntity = restTemplate
				.exchange("/api/charts/location_tickets_sold/interval", HttpMethod.PUT, httpEntity, ChartLocationTicketsSoldDTO[].class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		ChartLocationTicketsSoldDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertEquals(LOCATION1_NAME.toLowerCase(), info[0].getLocationName().toLowerCase());
		assertTrue(TICKETS_SOLD_LOCATION1_INTERVAL == info[0].getTicketsSold());
		//average is the same as event1 -> only 1 event
		assertEquals(AVERAGE_NAME.toLowerCase(), info[1].getLocationName().toLowerCase());
		assertTrue(TICKETS_SOLD_LOCATION1_INTERVAL == info[1].getTicketsSold());
	}
	
	@Test
	public void testGetTicketsSoldByLocationsEmptyInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_EMPTY), df.parse(END_DATE_EMPTY));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<ChartLocationTicketsSoldDTO[]> responseEntity = restTemplate
				.exchange("/api/charts/location_tickets_sold/interval", HttpMethod.PUT, httpEntity, ChartLocationTicketsSoldDTO[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		ChartLocationTicketsSoldDTO[] info = responseEntity.getBody();
		assertNotNull(info);
		assertTrue(info.length == 0);
	}
	
	@Test()
	public void testGetTicketsSoldByLocationsBadInterval() throws ParseException
	{
		DateIntervalDTO interval =  new DateIntervalDTO( df.parse(START_DATE_BAD), df.parse(END_DATE_BAD));

		HttpEntity<DateIntervalDTO> httpEntity = new HttpEntity<DateIntervalDTO>(interval, headers);
		ResponseEntity<String> responseEntity = restTemplate
				.exchange("/api/charts/location_tickets_sold/interval", HttpMethod.PUT, httpEntity, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Start date must be after end date"));
	}
	
}
