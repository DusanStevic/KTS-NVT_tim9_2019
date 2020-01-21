package backend.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import backend.controller.LocationControllerIntegrationTest;
import backend.repository.LocationRepositoryIntegrationTest;
import backend.service.LocationServiceIntegrationTest;
import backend.service.LocationServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
	LocationRepositoryIntegrationTest.class,
	LocationServiceUnitTest.class,
	LocationServiceIntegrationTest.class,
	LocationControllerIntegrationTest.class
})
public class LocationSuite {

}
