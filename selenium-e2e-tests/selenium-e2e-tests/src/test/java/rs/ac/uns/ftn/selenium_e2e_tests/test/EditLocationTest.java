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

import rs.ac.uns.ftn.selenium_e2e_tests.pages.EditLocationPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.FormLocationPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageAdministrator;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageUnregistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.LocationListPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.LoginPage;

public class EditLocationTest {
	private WebDriver browser;
	
	HomePageUnregistered homePage;
	LoginPage loginPage;
	HomePageAdministrator homePageAdmin;
	FormLocationPage addLocationPage;
	LocationListPage locationListPage;
	EditLocationPage editLocationPage;
	
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
		editLocationPage = PageFactory.initElements(browser, EditLocationPage.class);
	}
	

	@Test
	public void editLocationTest() throws InterruptedException{
		homePage.ensureLoginIsDisplayed();
		homePage.getLoginNavBtn().click();
		
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
		
		//add LocationFor Test
		addLocationPage.setNameInput("TEST");
		addLocationPage.setDescriptionInput("TEST");
		addLocationPage.setAddressInput("TEST 23.");
		addLocationPage.setLatitudeInput("55.55");
		addLocationPage.setLongitudeInput("55.55");

		addLocationPage.getSubmitLocationInput().click();
			
		Thread.sleep(500);
		assertEquals("http://localhost:4200/location/update", browser.getCurrentUrl());

		//test edit
		editLocationPage.setNameInput("EDITED");
		editLocationPage.getSubmitLocationInput().click();

		Thread.sleep(500);
		assertEquals("http://localhost:4200/location/all", browser.getCurrentUrl());

		assertEquals(numBeforeAdd,locationListPage.getLocationsTableSize() - 1);
		assertEquals("EDITED", locationListPage.getLastTdName().getText());
		
		locationListPage.getLastTdEditBtn().click();
		
		Thread.sleep(500);
		assertEquals("http://localhost:4200/location/update", browser.getCurrentUrl());
		
		int numberHalls = editLocationPage.getHallTableSize();
		
		editLocationPage.setHallNameInput("TestingHall");
		editLocationPage.setSittingNrInput("2");
		editLocationPage.setStandingNrInput("1");
		
		editLocationPage.getSubmitHallBtn().click();

		Thread.sleep(500);
		assertEquals("http://localhost:4200/location/update", browser.getCurrentUrl());
		
		//added hall u table
		assertEquals(numberHalls, editLocationPage.getHallTableSize() - 1);
		assertEquals("TestingHall", editLocationPage.getLastTdName().getText());
		assertEquals("2", editLocationPage.getLastTdSitting().getText());
		assertEquals("1", editLocationPage.getLastTdStanding().getText());
		
		editLocationPage.getLastTdDelete().click();
		Thread.sleep(500);
		assertEquals("http://localhost:4200/location/update", browser.getCurrentUrl());
		assertEquals(numberHalls, editLocationPage.getHallTableSize());
		
	}
	
	@After
	public void closeSelenium() {
		browser.quit();
	}
}
