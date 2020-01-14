package backend.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import backend.controller.SectorControllerIntegrationTest;
import backend.repository.SectorRepositoryIntegrationTest;
import backend.service.SectorServiceIntegrationTest;
import backend.service.SectorServiceUnitTest;



@RunWith(Suite.class)
@SuiteClasses({
	SectorRepositoryIntegrationTest.class,
	SectorServiceUnitTest.class,
	SectorServiceIntegrationTest.class,
	SectorControllerIntegrationTest.class
})
public class SectorSuite {

}
