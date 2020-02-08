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

import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageRegistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageUnregistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.LoginPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.UserProfilePage;

public class EditProfileTest {
	private WebDriver browser;
	
	HomePageUnregistered homePage;
	LoginPage loginPage;
	HomePageRegistered homePageRegistered;
	UserProfilePage userProfilePage;

	
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
		homePageRegistered = PageFactory.initElements(browser, HomePageRegistered.class);;
		userProfilePage = PageFactory.initElements(browser, UserProfilePage.class);;
	}
	@Test
	public void editProfileTest() throws InterruptedException{
		String invalidData = "Data is not valid";
		String takenData = "Username or email is taken!";
		
		homePage.ensureLoginIsDisplayed();
		homePage.getLoginNavBtn().click();
		
		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

		loginPage.setUsernameInput("user2");
		loginPage.setPasswordInput("user");
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();
		
		Thread.sleep(1000);
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());
		
		homePageRegistered.ensureProfileIsDisplayed();
		homePageRegistered.getProfileNavBtn().click();

		Thread.sleep(500);
		assertEquals("http://localhost:4200/profile", browser.getCurrentUrl());
		
		//error when field empty
		userProfilePage.getFirstNameInput().clear();
		userProfilePage.getFirstNameInput().click();
		userProfilePage.getLastNameInput().clear();
		userProfilePage.getLastNameInput().click();
		
		//test revert changes
		userProfilePage.getCancelButton().click();
		assertEquals("Arpad", userProfilePage.getFirstNameInput().getAttribute("value"));
		assertEquals("Varga Somodji", userProfilePage.getLastNameInput().getAttribute("value"));

		Thread.sleep(500);
		//test bad email format
		userProfilePage.setEmailInput("asd");
		userProfilePage.getUpdateUserBtn().click();

		Thread.sleep(1000);
		assertEquals(invalidData, userProfilePage.getHiddentErrorValue());
		
		//test taken email
		userProfilePage.setEmailInput("bob@gmail.com");
		userProfilePage.getUpdateUserBtn().click();

		Thread.sleep(1000);
		assertEquals(takenData, userProfilePage.getHiddentErrorValue());
		
		//reset all data
		userProfilePage.getCancelButton().click();
		Thread.sleep(500);
		
		//test too short username
		userProfilePage.setUsernameInput("as");
		userProfilePage.getUpdateUserBtn().click();
		Thread.sleep(1000);

		assertEquals(invalidData, userProfilePage.getHiddentErrorValue());
		
		//test taken username
		userProfilePage.setUsernameInput("admin");
		userProfilePage.getUpdateUserBtn().click();

		Thread.sleep(1500);
		assertEquals(takenData, userProfilePage.getHiddentErrorValue());

		//reset all data
		userProfilePage.getCancelButton().click();
		
		//succesful data update
		userProfilePage.setFirstNameInput("testing");
		userProfilePage.setLastNameInput("testing");
		userProfilePage.setEmailInput("testing@gmail.com");
		userProfilePage.setUsernameInput("testing");
		userProfilePage.setPhoneNumberInput("666-999");

		userProfilePage.getUpdateUserBtn().click();
		
		Thread.sleep(1500);
		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
		
		loginPage.setUsernameInput("testing");
		loginPage.setPasswordInput("user");
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();
		
		Thread.sleep(1000);
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());
		
		homePageRegistered.ensureProfileIsDisplayed();
		homePageRegistered.getProfileNavBtn().click();

		Thread.sleep(500);
		assertEquals("http://localhost:4200/profile", browser.getCurrentUrl());
		
		assertEquals("testing", userProfilePage.getFirstNameInput().getAttribute("value"));
		assertEquals("testing", userProfilePage.getLastNameInput().getAttribute("value"));
		assertEquals("testing@gmail.com", userProfilePage.getEmailInput().getAttribute("value"));
		assertEquals("testing", userProfilePage.getUsernameInput().getAttribute("value"));
		assertEquals("666-999", userProfilePage.getPhoneNumberInput().getAttribute("value"));
		
		//reset all data to original
		userProfilePage.setFirstNameInput("Arpad");
		userProfilePage.setLastNameInput("Varga Somodji");
		userProfilePage.setEmailInput("tirmann25+10@gmail.com");
		userProfilePage.setUsernameInput("user2");
		userProfilePage.setPhoneNumberInput("0658845455");
		
		userProfilePage.getUpdateUserBtn().click();
		
		Thread.sleep(1000);
		
		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
	}

	@After
	public void closeSelenium() {
		browser.quit();
	}
}
