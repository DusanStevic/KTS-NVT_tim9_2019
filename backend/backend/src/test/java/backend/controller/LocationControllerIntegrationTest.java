package backend.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static backend.constants.AddressConstants.DB_ADDRESS_ID;
import static backend.constants.LocationConstants.*;
import static org.junit.Assert.*;

import backend.model.Address;
import backend.model.Location;
import backend.service.LocationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class LocationControllerIntegrationTest {

	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	LocationService locationService;
	
	@Test
	public void testGetLocation() {
		ResponseEntity<Location> responseEntity = restTemplate.getForEntity("/api/location/"+DB_LOCATION_ID, Location.class);
		Location loc = responseEntity.getBody();
		
		assertNotNull(loc);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void testGetAllLocations() {
		ResponseEntity<Location[]> responseEntity = restTemplate.getForEntity("/api/location", Location[].class);
		Location[] locations = responseEntity.getBody();
		assertNotNull(locations);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
