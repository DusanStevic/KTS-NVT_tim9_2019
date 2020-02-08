package rs.ac.uns.ftn.selenium_e2e_tests.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import rs.ac.uns.ftn.selenium_e2e_tests.test.AddAdminTest;
import rs.ac.uns.ftn.selenium_e2e_tests.test.AddLocationTest;
import rs.ac.uns.ftn.selenium_e2e_tests.test.CancelReservationTest;
import rs.ac.uns.ftn.selenium_e2e_tests.test.ChangePasswordTest;
import rs.ac.uns.ftn.selenium_e2e_tests.test.ChartsTest;
import rs.ac.uns.ftn.selenium_e2e_tests.test.EditProfileTest;
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
	ChartsTest.class,
	EditProfileTest.class,
	ChangePasswordTest.class,
	AddLocationTest.class
})
public class SuiteAll {

}
