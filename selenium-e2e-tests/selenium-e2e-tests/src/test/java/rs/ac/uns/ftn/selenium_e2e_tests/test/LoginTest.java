package rs.ac.uns.ftn.selenium_e2e_tests.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static rs.ac.uns.ftn.selenium_e2e_tests.constants.SeleniumChromeWebDriverPaths.*;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.PageFactory;

import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageUnregistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.LoginPage;

public class LoginTest {
	private WebDriver browser;
	
	HomePageUnregistered homePage;
	LoginPage loginPage;
	
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
	}
	
	@Test
	public void loginTest() throws InterruptedException{
		homePage.ensureLoginIsDisplayed();
		homePage.getLoginNavBtn().click();
		
		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
		
		//check if login is disabled for empty fields
		assertFalse(loginPage.getLoginButton().isEnabled());

		//check if login is disabled when one field is empty
		loginPage.getUsernameInput().sendKeys("asd");
		assertFalse(loginPage.getLoginButton().isEnabled());
		
		//set invalid uname+pw
		loginPage.setUsernameInput("asd");
		loginPage.setPasswordInput("asd");
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();
		
		Thread.sleep(1000);
		String message = loginPage.getHiddentError().getAttribute("value");
		assertEquals("Incorrect username or password!", message.trim());
		
		//set correct username
		loginPage.setUsernameInput("user");
		loginPage.setPasswordInput("asd");
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();

		message = loginPage.getHiddentError().getAttribute("value");
		assertEquals("Incorrect username or password!", message.trim());

		//set correct password
		loginPage.setUsernameInput("asd");
		loginPage.setPasswordInput("user");
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();

		message = loginPage.getHiddentErrorValue();
		assertEquals("Incorrect username or password!", message.trim());
		
		//succesful login
		loginPage.setUsernameInput("user");
		loginPage.setPasswordInput("user");
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();
		
		Thread.sleep(2000);
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());
		
        WebStorage webStorage = (WebStorage) browser;
        LocalStorage localStorage = webStorage.getLocalStorage();
		String token = localStorage.getItem("user");
		System.out.println(token);
		assertNotNull(token);
		assertNotEquals("", token);
	}
	
	@After
	public void closeSelenium() {
		browser.quit();
	}

}
