package backend.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import backend.controller.AddressControllerIntegrationTest;
import backend.controller.ChartControllerIntergrationTest;
import backend.controller.HallControllerIntegrationTest;
import backend.controller.LocationControllerIntegrationTest;
import backend.controller.SectorControllerIntegrationTest;
import backend.controller.TicketControllerIntegrationTest;
import backend.repository.AddressRepositoryIntegrationTest;
import backend.repository.EventDayRepositoryIntegrationTest;
import backend.repository.EventRepositoryIntegrationTest;
import backend.repository.EventSectorRepositoryIntegrationTest;
import backend.repository.HallRepositoryIntegrationTest;
import backend.repository.LocationRepositoryIntegrationTest;
import backend.repository.SectorRepositoryIntegrationTest;
import backend.repository.TicketRepositoryIntegrationTest;
import backend.service.AddressServiceIntegrationTest;
import backend.service.AddressServiceUnitTest;
import backend.service.ChartServiceIntegrationTest;
import backend.service.ChartServiceUnitTest;
import backend.service.EventDayServiceIntegrationTest;
import backend.service.EventDayServiceUnitTest;
import backend.service.EventSectorServiceIntegrationTest;
import backend.service.EventSectorServiceUnitTest;
import backend.service.EventServiceIntegrationTest;
import backend.service.EventServiceUnitTest;
import backend.service.HallServiceIntegrationTest;
import backend.service.HallServiceUnitTest;
import backend.service.LocationServiceIntegrationTest;
import backend.service.LocationServiceUnitTest;
import backend.service.SectorServiceIntegrationTest;
import backend.service.SectorServiceUnitTest;
import backend.service.TicketServiceIntegrationTest;
import backend.service.TicketServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
	LocationRepositoryIntegrationTest.class,
	LocationServiceUnitTest.class,
	LocationServiceIntegrationTest.class,
	LocationControllerIntegrationTest.class,
	
	AddressRepositoryIntegrationTest.class,
	AddressServiceUnitTest.class,
	AddressServiceIntegrationTest.class,
	AddressControllerIntegrationTest.class,
	
	ChartServiceUnitTest.class,
	ChartServiceIntegrationTest.class,
	ChartControllerIntergrationTest.class,
	
	HallRepositoryIntegrationTest.class,
	HallServiceUnitTest.class,
	HallServiceIntegrationTest.class,
	HallControllerIntegrationTest.class,
	
	TicketRepositoryIntegrationTest.class,
	TicketControllerIntegrationTest.class,
	TicketServiceIntegrationTest.class,
	TicketServiceUnitTest.class,

	SectorRepositoryIntegrationTest.class,
	SectorServiceUnitTest.class,
	SectorServiceIntegrationTest.class,
	SectorControllerIntegrationTest.class,

	EventDayRepositoryIntegrationTest.class,
	EventRepositoryIntegrationTest.class,
	EventSectorRepositoryIntegrationTest.class,
	EventDayServiceIntegrationTest.class,
	EventDayServiceUnitTest.class,
	EventSectorServiceIntegrationTest.class,
	EventSectorServiceUnitTest.class,
	EventServiceIntegrationTest.class,
	EventServiceUnitTest.class
	
	
})
public class SuiteAll {

}
