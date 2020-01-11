package backend.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import backend.controller.AddressControllerIntegrationTest;
import backend.controller.ChartControllerIntergrationTest;
import backend.repository.AddressRepositoryIntegrationTest;
import backend.service.AddressServiceIntegrationTest;
import backend.service.AddressServiceUnitTest;
import backend.service.ChartServiceIntegrationTest;
import backend.service.ChartServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
	AddressRepositoryIntegrationTest.class,
	AddressServiceUnitTest.class,
	AddressServiceIntegrationTest.class,
	AddressControllerIntegrationTest.class,
	ChartServiceUnitTest.class,
	ChartServiceIntegrationTest.class,
	ChartControllerIntergrationTest.class
})
public class SuiteAll {

}
