package backend.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import backend.controller.ChartControllerIntergrationTest;
import backend.service.ChartServiceIntegrationTest;
import backend.service.ChartServiceUnitTest;



@RunWith(Suite.class)
@SuiteClasses({
	ChartServiceUnitTest.class,
	ChartServiceIntegrationTest.class,
	ChartControllerIntergrationTest.class
})
public class SuiteChart {

}
