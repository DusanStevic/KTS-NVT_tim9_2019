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

import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageAdministrator;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageSysAdmin;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageUnregistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.LoginPage;

public class ChartsTest {
	private WebDriver browser;

	Map<String, Object> vars;
	JavascriptExecutor js;

	HomePageUnregistered homePage;
	LoginPage loginPage;
	HomePageAdministrator homePageAdmin;
	HomePageSysAdmin homePageSysAdmin;

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

		homePage = PageFactory
				.initElements(browser, HomePageUnregistered.class);
		loginPage = PageFactory.initElements(browser, LoginPage.class);
		homePageAdmin = PageFactory.initElements(browser,
				HomePageAdministrator.class);
		homePageSysAdmin = PageFactory.initElements(browser,
				HomePageSysAdmin.class);
	}

	@Test
	public void chartTest() throws InterruptedException {
		homePage.ensureLoginIsDisplayed();
		homePage.getLoginNavBtn().click();

		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

		// login as sys admin
		loginPage.setUsernameInput("sys");
		loginPage.setPasswordInput("admin");

		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();

		Thread.sleep(500);
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());

		homePageSysAdmin.ensureChartIsDisplayed();
		homePageSysAdmin.getChartNavBtn().click();

		Thread.sleep(2500);
		// check if charts accessed
		assertEquals("http://localhost:4200/charts", browser.getCurrentUrl());

		homePageSysAdmin.getLogoutNavBtn().click();

		homePage.ensureLoginIsDisplayed();
		homePage.getLoginNavBtn().click();

		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

		// login as admin
		loginPage.setUsernameInput("admin");
		loginPage.setPasswordInput("admin");

		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();

		Thread.sleep(500);
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());

		homePageAdmin.ensureChartIsDisplayed();
		homePageAdmin.getChartNavBtn().click();

		Thread.sleep(2500);
		// check if charts accessed
		assertEquals("http://localhost:4200/charts", browser.getCurrentUrl());

	}

	@After
	public void closeSelenium() {
		browser.quit();
	}

}
