package backend.controller;

import static backend.constants.TicketConstants.DB_COUNT;
import static backend.constants.TicketConstants.TICKET1_COL;
import static backend.constants.TicketConstants.TICKET1_EVENTDAY_ID;
import static backend.constants.TicketConstants.TICKET1_EV_SECTOR_ID;
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

import backend.dto.SimpleTicketDTO;
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

		ResponseEntity<String> login = restTemplate.postForEntity("/auth/login",
				new JwtAuthenticationRequest("admin", "admin"), String.class);
		accessToken = login.getBody();
		headers.add("Authorization", "Bearer " + accessToken);
	}
	
	@Test
	public void testGetAllTickets()
	{
		ResponseEntity<SimpleTicketDTO[]> responseEntity = restTemplate.getForEntity("/api/ticket/", SimpleTicketDTO[].class);
		SimpleTicketDTO[] tickets = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(tickets);
		assertTrue(DB_COUNT == tickets.length);
		
		SimpleTicketDTO t1 = tickets[0];
		assertThat(t1).isNotNull();
		assertTrue(t1.isHasSeat());
		assertThat(t1.getNumCol()).isEqualTo(TICKET1_COL);
		assertThat(t1.getNumRow()).isEqualTo(TICKET1_ROW);
		assertThat(t1.getReservationID()).isEqualTo(TICKET1_RESERVATION_ID);
	}
	
	@Test
	public void testGetAllTicketsEventDayIDEventSectorID()
	{
		ResponseEntity<SimpleTicketDTO[]> responseEntity = restTemplate.getForEntity("/api/ticket/" + TICKET1_EVENTDAY_ID +  "/" + TICKET1_EV_SECTOR_ID, SimpleTicketDTO[].class);
		SimpleTicketDTO[] found = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(found);
		assertTrue(2 == found.length);
	
		SimpleTicketDTO t1 = found[0];
		assertNotNull(t1);
		assertTrue(t1.isHasSeat());
		assertEquals(TICKET1_COL, t1.getNumCol());
		assertEquals(TICKET1_ROW, t1.getNumRow());
		assertThat(t1.getReservationID()).isEqualTo(TICKET1_RESERVATION_ID);
	}
	
	@Test
	public void testGetTicket()
	{
		ResponseEntity<SimpleTicketDTO> responseEntity = restTemplate.getForEntity("/api/ticket/" + TICKET1_ID, SimpleTicketDTO.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		SimpleTicketDTO t1 = responseEntity.getBody();
		assertNotNull(t1);
		assertTrue(t1.isHasSeat());
		assertEquals(TICKET1_COL, t1.getNumCol());
		assertEquals(TICKET1_ROW, t1.getNumRow());
		assertThat(t1.getReservationID()).isEqualTo(TICKET1_RESERVATION_ID);
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
