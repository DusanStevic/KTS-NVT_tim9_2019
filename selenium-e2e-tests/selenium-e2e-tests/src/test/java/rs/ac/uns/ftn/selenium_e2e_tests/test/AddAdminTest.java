package rs.ac.uns.ftn.selenium_e2e_tests.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static rs.ac.uns.ftn.selenium_e2e_tests.constants.SeleniumChromeWebDriverPaths.CHROME_WEB_DRIVER_PATH_ARPAD;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import rs.ac.uns.ftn.selenium_e2e_tests.pages.AddAdminPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageSysAdmin;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageUnregistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.LoginPage;

public class AddAdminTest {
	private WebDriver browser;
	
	HomePageUnregistered homePage;
	LoginPage loginPage;
	HomePageSysAdmin homePageSysAdmin;
	AddAdminPage addAdminPage;
	
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
		homePageSysAdmin = PageFactory.initElements(browser, HomePageSysAdmin.class);
		addAdminPage = PageFactory.initElements(browser, AddAdminPage.class);
	}
	
	@Test
	public void loginTest() throws InterruptedException{
		homePage.ensureLoginIsDisplayed();
		homePage.getLoginNavBtn().click();
		String errorMsg = "There was an error adding the administrator! Try again!";
		
		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
		
		//login as sys admin
		loginPage.setUsernameInput("sys");
		loginPage.setPasswordInput("admin");
		
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();
		
		Thread.sleep(1000);
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());
		
		homePageSysAdmin.ensureAddAdminIsDisplayed();
		homePageSysAdmin.getAddAdminNavBtn().click();
		
		Thread.sleep(1000);
		assertEquals("http://localhost:4200/admin/add", browser.getCurrentUrl());
		
		//check if register is disabled for empty fields
		assertFalse(addAdminPage.getAdminAddButton().isEnabled());
		
		//check if registerBtn when a few field is empty
		addAdminPage.setFirstNameInput("asd");
		assertFalse(addAdminPage.getAdminAddButton().isEnabled());
		
		//test for bad email format
		addAdminPage.setFirstNameInput("testing2");
		addAdminPage.setLastNameInput("testing2");
		addAdminPage.setEmailInput("testing2");
		addAdminPage.setUsernameInput("testing2");
		addAdminPage.setPasswordInput("testing2");

		assertFalse(addAdminPage.getAdminAddButton().isEnabled());

		//test for taken email
		addAdminPage.setFirstNameInput("testing2");
		addAdminPage.setLastNameInput("testing2");
		addAdminPage.setEmailInput("bob@gmail.com");
		addAdminPage.setUsernameInput("testing2");
		addAdminPage.setPasswordInput("testing2");

		assertTrue(addAdminPage.getAdminAddButton().isEnabled());
		addAdminPage.getAdminAddButton().click();
		
		Thread.sleep(1500);
		assertEquals(errorMsg, addAdminPage.getHiddentErrorValue());
		
		//test for taken username
		addAdminPage.setFirstNameInput("testing");
		addAdminPage.setLastNameInput("testing");
		addAdminPage.setEmailInput("test@gmail.com");
		addAdminPage.setUsernameInput("user");
		addAdminPage.setPasswordInput("testing");

		assertTrue(addAdminPage.getAdminAddButton().isEnabled());
		addAdminPage.getAdminAddButton().click();
		
		Thread.sleep(1500);
		assertEquals(errorMsg, addAdminPage.getHiddentErrorValue());
		
		//succesful register
		addAdminPage.setFirstNameInput("testing");
		addAdminPage.setLastNameInput("testing");
		addAdminPage.setEmailInput("tirmann25+999@gmail.com");
		addAdminPage.setUsernameInput("test234");
		addAdminPage.setPasswordInput("testing");

		assertTrue(addAdminPage.getAdminAddButton().isEnabled());
		addAdminPage.getAdminAddButton().click();
		
		Thread.sleep(1500);
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());
		

	}
	
	@After
	public void closeSelenium() {
		browser.quit();
	}
}
