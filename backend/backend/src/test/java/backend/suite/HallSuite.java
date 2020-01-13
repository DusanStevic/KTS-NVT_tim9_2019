package backend.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import backend.controller.HallControllerIntegrationTest;
import backend.repository.HallRepositoryIntegrationTest;
import backend.service.HallServiceIntegrationTest;
import backend.service.HallServiceUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
	HallRepositoryIntegrationTest.class,
	HallServiceUnitTest.class,
	HallServiceIntegrationTest.class,
	HallControllerIntegrationTest.class
})
public class HallSuite {

}
