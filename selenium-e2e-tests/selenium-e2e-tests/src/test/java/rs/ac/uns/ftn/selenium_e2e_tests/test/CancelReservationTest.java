package rs.ac.uns.ftn.selenium_e2e_tests.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static rs.ac.uns.ftn.selenium_e2e_tests.constants.SeleniumChromeWebDriverPaths.CHROME_WEB_DRIVER_PATH_ARPAD;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageRegistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageUnregistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.LoginPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.MyReservationsPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.ReservationDetailedPage;

public class CancelReservationTest {
	private WebDriver browser;

	Map<String, Object> vars;
	JavascriptExecutor js;

	HomePageUnregistered homePage;
	LoginPage loginPage;
	HomePageRegistered homePageRegistered;
	MyReservationsPage myReservationsPage;
	ReservationDetailedPage reservationDetailedPage;

	@Before
	public void setupSelenium() {
		// System.setProperty("webdriver.chrome.driver",CHROME_WEB_DRIVER_PATH_MARIETA);
		// System.setProperty("webdriver.chrome.driver",CHROME_WEB_DRIVER_PATH_NIKOLA);
		// System.setProperty("webdriver.chrome.driver",CHROME_WEB_DRIVER_PATH_DULE);
		System.setProperty("webdriver.chrome.driver",
				CHROME_WEB_DRIVER_PATH_ARPAD);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-popup-blocking");

		browser = new ChromeDriver(options);
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/");

		browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		browser.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

		browser.manage().deleteAllCookies();
		js = (JavascriptExecutor) browser;

		homePage = PageFactory
				.initElements(browser, HomePageUnregistered.class);
		loginPage = PageFactory.initElements(browser, LoginPage.class);
		homePageRegistered = PageFactory.initElements(browser,
				HomePageRegistered.class);
		myReservationsPage = PageFactory.initElements(browser,
				MyReservationsPage.class);
		reservationDetailedPage = PageFactory.initElements(browser,
				ReservationDetailedPage.class);
	}

	@Test
	public void purchaseTest() throws InterruptedException {
		homePage.ensureLoginIsDisplayed();
		homePage.getLoginNavBtn().click();

		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

		// logging in as user
		loginPage.setUsernameInput("user");
		loginPage.setPasswordInput("user");
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();

		Thread.sleep(500);

		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());

		homePageRegistered.getMyReservationsNavBtn().click();
		Thread.sleep(500);
		assertEquals("http://localhost:4200/myReservations",
				browser.getCurrentUrl());

		int sizeBefore = myReservationsPage.getReservationTableSize();

		myReservationsPage.ensureCancelResIsDisplayed();
		myReservationsPage.getResForCancelBtn().click();

		Thread.sleep(500);
		
		assertEquals("http://localhost:4200/reservationDetail/2",
				browser.getCurrentUrl());
		js.executeScript("window.scrollBy(0,500)");

		//click on cancel
		reservationDetailedPage.ensureCancelResIsDisplayed();
		reservationDetailedPage.getCancelBtn().click();


		//decline
		reservationDetailedPage.ensureDeclineIsDisplayed();;
		reservationDetailedPage.getDecline().click();


		// check that still on profile -> no changes
		assertEquals("http://localhost:4200/reservationDetail/2",
				browser.getCurrentUrl());

		//click on cancel again
		reservationDetailedPage.ensureCancelResIsDisplayed();
		reservationDetailedPage.getCancelBtn().click();

		//click on approve
		reservationDetailedPage.ensureApproveIsDisplayed();
		reservationDetailedPage.getApprove().click();;
		
		Thread.sleep(1000);

		int sizeAfterCancel = myReservationsPage.getReservationTableSize();
		assertEquals("http://localhost:4200/myReservations",
				browser.getCurrentUrl());
		assertTrue(sizeAfterCancel == sizeBefore - 1);
	}
	
	@After
	public void closeSelenium() {
		browser.quit();
	}
	
}
