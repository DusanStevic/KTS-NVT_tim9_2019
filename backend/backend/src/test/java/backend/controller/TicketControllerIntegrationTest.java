package backend.controller;

import static backend.constants.TicketConstants.DB_COUNT;
import static backend.constants.TicketConstants.TICKET1_COL;
import static backend.constants.TicketConstants.TICKET1_EVENTDAY_ID;
import static backend.constants.TicketConstants.TICKET1_EV_SECTOR_ID;
import static backend.constants.TicketConstants.TICKET1_HAS_SEAT;
import static backend.constants.TicketConstants.TICKET1_ID;
import static backend.constants.TicketConstants.TICKET1_RESERVATION_ID;
import static backend.constants.TicketConstants.TICKET1_ROW;
import static backend.constants.TicketConstants.TICKET_ID_NONEXISTENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import backend.model.Ticket;
import backend.model.UserTokenState;
import backend.security.auth.JwtAuthenticationRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class TicketControllerIntegrationTest {
	@Autowired
	TestRestTemplate restTemplate;

	private String accessToken;
	private  HttpHeaders headers = new HttpHeaders();
	
	@Before
	public void login() {

		ResponseEntity<UserTokenState> login = restTemplate.postForEntity("/auth/login",
				new JwtAuthenticationRequest("admin", "admin"), UserTokenState.class);
		accessToken = login.getBody().getAccessToken();
		headers.add("Authorization", "Bearer " + accessToken);
	}
	
	@Test
	public void testGetAllTickets()
	{
		ResponseEntity<Ticket[]> responseEntity = restTemplate.getForEntity("/api/ticket/", Ticket[].class);
		Ticket[] tickets = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(tickets);
		assertTrue(DB_COUNT == tickets.length);
		
		Ticket t1 = tickets[0];
		assertThat(t1).isNotNull();
		assertThat(t1.getId()).isEqualTo(TICKET1_ID);
		assertThat(t1.getNumCol()).isEqualTo(TICKET1_COL);
		assertThat(t1.getNumRow()).isEqualTo(TICKET1_ROW);
		assertThat(t1.getReservation().getId()).isEqualTo(TICKET1_RESERVATION_ID);
		assertThat(t1.getEventDay().getId()).isEqualTo(TICKET1_EVENTDAY_ID);
		assertThat(t1.getEventSector().getId()).isEqualTo(TICKET1_EV_SECTOR_ID);
	}
	
	@Test
	public void testGetAllTicketsEventDayIDEventSectorID()
	{
		ResponseEntity<Ticket[]> responseEntity = restTemplate.getForEntity("/api/ticket/" + TICKET1_EVENTDAY_ID +  "/" + TICKET1_EV_SECTOR_ID, Ticket[].class);
		Ticket[] found = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(found);
		assertTrue(2 == found.length);
	
		Ticket t1 = found[0];
		assertNotNull(t1);
		assertEquals(TICKET1_ID, t1.getId());
		assertTrue(TICKET1_HAS_SEAT);
		assertEquals(TICKET1_COL, t1.getNumCol());
		assertEquals(TICKET1_ROW, t1.getNumRow());
		assertEquals(TICKET1_EVENTDAY_ID, t1.getEventDay().getId());
		assertEquals(TICKET1_EV_SECTOR_ID, t1.getEventSector().getId());
	}
	
	@Test
	public void testGetTicket()
	{
		ResponseEntity<Ticket> responseEntity = restTemplate.getForEntity("/api/ticket/" + TICKET1_ID, Ticket.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Ticket t1 = responseEntity.getBody();
		assertNotNull(t1);
		assertEquals(TICKET1_ID, t1.getId());
		assertTrue(TICKET1_HAS_SEAT);
		assertEquals(TICKET1_COL, t1.getNumCol());
		assertEquals(TICKET1_ROW, t1.getNumRow());
		assertEquals(TICKET1_EVENTDAY_ID, t1.getEventDay().getId());
		assertEquals(TICKET1_EV_SECTOR_ID, t1.getEventSector().getId());
	}
	
	@Test
	public void testGetTicketNonExistent()
	{
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("/api/ticket/" + TICKET_ID_NONEXISTENT, String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertTrue(responseEntity.getBody().contains("Could not find requested ticket"));
	}
}
