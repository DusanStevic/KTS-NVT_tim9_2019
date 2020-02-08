package rs.ac.uns.ftn.selenium_e2e_tests.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import rs.ac.uns.ftn.selenium_e2e_tests.test.AddAdminTest;
import rs.ac.uns.ftn.selenium_e2e_tests.test.CancelReservationTest;
import rs.ac.uns.ftn.selenium_e2e_tests.test.LoginTest;
import rs.ac.uns.ftn.selenium_e2e_tests.test.PurchaseReservationTest;
import rs.ac.uns.ftn.selenium_e2e_tests.test.RegisterTest;
@RunWith(Suite.class)
@SuiteClasses({
	LoginTest.class,
	RegisterTest.class,
	PurchaseReservationTest.class,
	CancelReservationTest.class,
	AddAdminTest.class,
})
public class SuiteAll {

}
