package backend.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import backend.repository.EventDayRepositoryIntegrationTest;
import backend.repository.EventRepositoryIntegrationTest;
import backend.repository.EventSectorRepositoryIntegrationTest;
import backend.service.EventDayServiceIntegrationTest;
import backend.service.EventDayServiceUnitTest;
import backend.service.EventSectorServiceIntegrationTest;
import backend.service.EventSectorServiceUnitTest;
import backend.service.EventServiceIntegrationTest;
import backend.service.EventServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
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
public class EventSuite {

}
