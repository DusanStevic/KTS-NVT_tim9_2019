package backend.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import backend.controller.AddressControllerIntegrationTest;
import backend.controller.ChartControllerIntergrationTest;
import backend.controller.LocationControllerIntegrationTest;
import backend.repository.AddressRepositoryIntegrationTest;
import backend.repository.LocationRepositoryIntegrationTest;
import backend.service.AddressServiceIntegrationTest;
import backend.service.AddressServiceUnitTest;
import backend.service.ChartServiceIntegrationTest;
import backend.service.ChartServiceUnitTest;
import backend.service.LocationServiceIntegrationTest;
import backend.service.LocationServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
	AddressRepositoryIntegrationTest.class,
	AddressServiceUnitTest.class,
	AddressServiceIntegrationTest.class,
	AddressControllerIntegrationTest.class,
	LocationRepositoryIntegrationTest.class,
	LocationServiceUnitTest.class,
	LocationServiceIntegrationTest.class,
	LocationControllerIntegrationTest.class,
	ChartServiceUnitTest.class,
	ChartServiceIntegrationTest.class,
	ChartControllerIntergrationTest.class
})
public class SuiteAll {

}
