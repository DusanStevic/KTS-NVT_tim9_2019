package rs.ac.uns.ftn.selenium_e2e_tests.test;

import static org.junit.Assert.assertEquals;
import static rs.ac.uns.ftn.selenium_e2e_tests.constants.SeleniumChromeWebDriverPaths.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.SingInPage;




public class LoginTest {

	private WebDriver browser;

	HomePage homePage;
	SingInPage singInPage;

	@Before
	public void setupSelenium() {
		// instantiate browser
		System.setProperty("webdriver.chrome.driver",CHROME_WEB_DRIVER_PATH_DULE);
		browser = new ChromeDriver();
		// maximize window
		browser.manage().window().maximize();
		// navigate
		browser.navigate().to("http://automationpractice.com/index.php");

		homePage = PageFactory.initElements(browser, HomePage.class);
		singInPage = PageFactory.initElements(browser, SingInPage.class);
	}

	@Test
	public void singInTest() {
		homePage.ensureSingInIsDisplayed();
		homePage.getSingInLink().click();

		assertEquals("http://automationpractice.com/index.php?controller=authentication&back=my-account",
				browser.getCurrentUrl());

		// all fields empty
		singInPage.ensureSubmitButtonIsDisplayed();
		singInPage.getSubmitButton().click();
		String errorMessage1 = singInPage.getAlertDivText().getText();
		assertEquals("An email address required.", errorMessage1);

		// set invalid email
		singInPage.setEmailInput("aaa");
		singInPage.getSubmitButton().click();
		String errorMessage2 = singInPage.getAlertDivText().getText();
		assertEquals("Invalid email address.", errorMessage2);

		// set email
		singInPage.setEmailInput("test@kts.com");
		singInPage.getSubmitButton().click();
		String errorMessage3 = singInPage.getAlertDivText().getText();
		assertEquals("Password is required.", errorMessage3);

		// set wrong pass
		singInPage.setPasswordInput("wrong");
		singInPage.getSubmitButton().click();
		String errorMessage4 = singInPage.getAlertDivText().getText();
		assertEquals("Authentication failed.", errorMessage4);

		// set correct pass
		singInPage.setPasswordInput("johnsmith");
		singInPage.getSubmitButton().click();

		assertEquals("http://automationpractice.com/index.php?controller=my-account", browser.getCurrentUrl());

	}

	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
}
