package backend.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import backend.controller.AddressControllerIntegrationTest;
import backend.repository.AddressRepositoryIntegrationTest;
import backend.service.AddressServiceIntegrationTest;
import backend.service.AddressServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
	AddressRepositoryIntegrationTest.class,
	AddressServiceUnitTest.class,
	AddressServiceIntegrationTest.class,
	AddressControllerIntegrationTest.class
})
public class AddressSuite {

}
