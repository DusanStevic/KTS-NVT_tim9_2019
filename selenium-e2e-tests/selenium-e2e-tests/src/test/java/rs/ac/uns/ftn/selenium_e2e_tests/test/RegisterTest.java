package rs.ac.uns.ftn.selenium_e2e_tests.test;

import static rs.ac.uns.ftn.selenium_e2e_tests.constants.SeleniumChromeWebDriverPaths.CHROME_WEB_DRIVER_PATH_ARPAD;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.PageFactory;

import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageUnregistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.RegisterPage;

public class RegisterTest {
	private WebDriver browser;
	
	HomePageUnregistered homePage;
	RegisterPage registerPage;
	
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
		registerPage = PageFactory.initElements(browser, RegisterPage.class);
	}
	
	@Test
	public void registerTest() throws InterruptedException{
		homePage.ensureRegisterIsDisplayed();
		homePage.getRegisterNavBtn().click();
		
		String errorMsg = "There was an error with your registration. Please try registering again!";
		
		assertEquals("http://localhost:4200/register", browser.getCurrentUrl());
		
		//check if register is disabled for empty fields
		assertFalse(registerPage.getRegisterButton().isEnabled());
		
		//check if registerBtn when a few field is empty
		registerPage.setFirstNameInput("asd");
		assertFalse(registerPage.getRegisterButton().isEnabled());
		
		//test for bad email format
		registerPage.setFirstNameInput("testing");
		registerPage.setLastNameInput("testing");
		registerPage.setEmailInput("testing");
		registerPage.setUsernameInput("testing");
		registerPage.setPasswordInput("testing");

		assertFalse(registerPage.getRegisterButton().isEnabled());

		//test for taken email
		registerPage.setFirstNameInput("testing");
		registerPage.setLastNameInput("testing");
		registerPage.setEmailInput("bob@gmail.com");
		registerPage.setUsernameInput("testing");
		registerPage.setPasswordInput("testing");

		assertTrue(registerPage.getRegisterButton().isEnabled());
		registerPage.getRegisterButton().click();
		
		Thread.sleep(1500);
		assertEquals(errorMsg, registerPage.getHiddentErrorValue());
		
		//test for taken username
		registerPage.setFirstNameInput("testing");
		registerPage.setLastNameInput("testing");
		registerPage.setEmailInput("test@gmail.com");
		registerPage.setUsernameInput("user");
		registerPage.setPasswordInput("testing");

		assertTrue(registerPage.getRegisterButton().isEnabled());
		registerPage.getRegisterButton().click();
		
		Thread.sleep(1500);
		assertEquals(errorMsg, registerPage.getHiddentErrorValue());
		
		//succesful register
		registerPage.setFirstNameInput("testing");
		registerPage.setLastNameInput("testing");
		registerPage.setEmailInput("tirmann25+665@gmail.com");
		registerPage.setUsernameInput("test");
		registerPage.setPasswordInput("testing");

		assertTrue(registerPage.getRegisterButton().isEnabled());
		registerPage.getRegisterButton().click();
		
		Thread.sleep(1500);
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());
		
		
        WebStorage webStorage = (WebStorage) browser;
        LocalStorage localStorage = webStorage.getLocalStorage();
		String token = localStorage.getItem("user");
		assertNull(token);
	}
	
	@After
	public void closeSelenium() {
		browser.quit();
	}
}
