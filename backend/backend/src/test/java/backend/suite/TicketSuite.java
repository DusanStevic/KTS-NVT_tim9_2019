package backend.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import backend.repository.TicketRepositoryIntegrationTest;
import backend.controller.TicketControllerIntegrationTest;
import backend.service.TicketServiceIntegrationTest;
import backend.service.TicketServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
	TicketRepositoryIntegrationTest.class,
	TicketControllerIntegrationTest.class,
	TicketServiceIntegrationTest.class,
	TicketServiceUnitTest.class
	
})
public class TicketSuite {

}
