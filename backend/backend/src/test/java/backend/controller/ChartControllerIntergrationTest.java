package backend.controller;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static backend.constants.ChartConstants.*;
import backend.dto.charts.SystemInformationsDTO;
import backend.model.UserTokenState;
import backend.security.auth.JwtAuthenticationRequest;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ChartControllerIntergrationTest {

	@Autowired
	TestRestTemplate restTemplate;

	private String accessToken;
	private HttpHeaders headers = new HttpHeaders();
	
	
	@Before
	public void login() {
		ResponseEntity<UserTokenState> login = 
				restTemplate.postForEntity("/auth/login", 
						new JwtAuthenticationRequest("sys", "admin"), 
						UserTokenState.class);
		accessToken = login.getBody().getAccessToken();
		headers.add("Authorization", "Bearer "+accessToken);
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
	public void testGetSysInfoBad()
	{
		//Non user
		ResponseEntity<SystemInformationsDTO> responseEntity = restTemplate.getForEntity("/api/charts/sysinfo", SystemInformationsDTO.class);
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		
		//Admin
		ResponseEntity<UserTokenState> login = 
				restTemplate.postForEntity("/auth/login", 
						new JwtAuthenticationRequest("admin", "admin"), 
						UserTokenState.class);
		String accessTokenAdmin = login.getBody().getAccessToken();
		HttpHeaders headersAdmin = new HttpHeaders();
		headersAdmin.add("Authorization", "Bearer "+accessTokenAdmin);

		responseEntity = restTemplate.exchange("/api/charts/sysinfo", 
					HttpMethod.GET, new HttpEntity<Object>(headersAdmin), SystemInformationsDTO.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
		
		//User
		login = restTemplate.postForEntity("/auth/login", 
						new JwtAuthenticationRequest("user", "user"), 
						UserTokenState.class);
		String accessTokenUser = login.getBody().getAccessToken();
		HttpHeaders headersUser = new HttpHeaders();
		headersUser.add("Authorization", "Bearer "+accessTokenUser);

		responseEntity = restTemplate.exchange("/api/charts/sysinfo", 
					HttpMethod.GET, new HttpEntity<Object>(headersUser), SystemInformationsDTO.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
}
