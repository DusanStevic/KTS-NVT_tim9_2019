package rs.ac.uns.ftn.selenium_e2e_tests.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static rs.ac.uns.ftn.selenium_e2e_tests.constants.SeleniumChromeWebDriverPaths.CHROME_WEB_DRIVER_PATH_ARPAD;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import rs.ac.uns.ftn.selenium_e2e_tests.pages.FormLocationPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageAdministrator;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageUnregistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.LocationListPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.LoginPage;

public class AddAndDeleteLocationTest {
	private WebDriver browser;
	
	HomePageUnregistered homePage;
	LoginPage loginPage;
	HomePageAdministrator homePageAdmin;
	FormLocationPage addLocationPage;
	LocationListPage locationListPage;
	
	@Before
	public void setupSelenium(){
		//System.setProperty("webdriver.chrome.driver",CHROME_WEB_DRIVER_PATH_MARIETA);
		//System.setProperty("webdriver.chrome.driver",CHROME_WEB_DRIVER_PATH_NIKOLA);
		//System.setProperty("webdriver.chrome.driver",CHROME_WEB_DRIVER_PATH_DULE);
		System.setProperty("webdriver.chrome.driver",CHROME_WEB_DRIVER_PATH_ARPAD);
		
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/");

		browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		browser.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

		browser.manage().deleteAllCookies();

		
		homePage = PageFactory.initElements(browser, HomePageUnregistered.class);
		loginPage = PageFactory.initElements(browser, LoginPage.class);
		homePageAdmin = PageFactory.initElements(browser, HomePageAdministrator.class);
		addLocationPage = PageFactory.initElements(browser, FormLocationPage.class);
		locationListPage = PageFactory.initElements(browser, LocationListPage.class);
		
	}

	@Test
	public void addAndDeleteLocationTest() throws InterruptedException{
		homePage.ensureLoginIsDisplayed();
		homePage.getLoginNavBtn().click();
		
		String errorMsg = "Please enter valid data!";
		
		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
		
		//login as sys admin
		loginPage.setUsernameInput("admin");
		loginPage.setPasswordInput("admin");
		
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();
		
		Thread.sleep(1000);
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());
		
		//getting number of locations before addition
		homePageAdmin.ensureLocationsIsDisplayed();
		homePageAdmin.getLocationsNavBtn().click();
		Thread.sleep(500);
		assertEquals("http://localhost:4200/location/all", browser.getCurrentUrl());
		
		int numBeforeAdd = locationListPage.getLocationsTableSize();
		
		homePageAdmin.ensureAddLocationsIsDisplayed();
		homePageAdmin.getAddLocationsNavBtn().click();
		
		Thread.sleep(500);
		assertEquals("http://localhost:4200/location/add", browser.getCurrentUrl());
		
		//empty fields
		addLocationPage.getSubmitLocationInput().click();
		Thread.sleep(500);
		assertEquals(errorMsg, addLocationPage.getHiddentErrorValue());
		
		//one field error
		addLocationPage.setNameInput("TestLoc");
		addLocationPage.getSubmitLocationInput().click();
		assertEquals(errorMsg, addLocationPage.getHiddentErrorValue());
		

		//empty fields
		addLocationPage.setNameInput("TestLoc");
		addLocationPage.getSubmitLocationInput().click();
		assertEquals(errorMsg, addLocationPage.getHiddentErrorValue());
		
		//reset works
		addLocationPage.getResetBtn().click();
		assertEquals("", addLocationPage.getNameInput().getAttribute("value"));
		
		//add Location
		addLocationPage.setNameInput("newLocation");
		addLocationPage.setDescriptionInput("This is a description of a great location");
		addLocationPage.setAddressInput("Kosokvska 23.");
		addLocationPage.setLatitudeInput("45.45");
		addLocationPage.setLongitudeInput("45.45");

		addLocationPage.getSubmitLocationInput().click();
		
		homePageAdmin.ensureLocationsIsDisplayed();
		assertEquals("http://localhost:4200/location/update", browser.getCurrentUrl());
		
		homePageAdmin.getLocationsNavBtn().click();

		
		Thread.sleep(500);
		assertEquals("http://localhost:4200/location/all", browser.getCurrentUrl());

		assertEquals(numBeforeAdd,locationListPage.getLocationsTableSize() - 1);
		assertEquals("newLocation", locationListPage.getLastTdName().getText());
		assertEquals("This is a description of a great location", locationListPage.getLastTdDescription().getText());
		assertEquals("Kosokvska 23.", locationListPage.getLastTdAddress().getText());
		
		locationListPage.getLastTdDelete().click();
		Thread.sleep(500);
		assertEquals(numBeforeAdd, locationListPage.getLocationsTableSize());
		
	}

	@After
	public void closeSelenium() {
		browser.quit();
	}
}
