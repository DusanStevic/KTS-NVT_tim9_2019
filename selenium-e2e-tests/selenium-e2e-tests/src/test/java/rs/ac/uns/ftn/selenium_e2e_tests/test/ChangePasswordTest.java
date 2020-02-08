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

import rs.ac.uns.ftn.selenium_e2e_tests.pages.ChangePasswordPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageRegistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.HomePageUnregistered;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.LoginPage;
import rs.ac.uns.ftn.selenium_e2e_tests.pages.UserProfilePage;

public class ChangePasswordTest {
	private WebDriver browser;
	
	HomePageUnregistered homePage;
	LoginPage loginPage;
	HomePageRegistered homePageRegistered;
	UserProfilePage userProfilePage;
	ChangePasswordPage changePasswordPage;
	
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
		homePageRegistered = PageFactory.initElements(browser, HomePageRegistered.class);
		userProfilePage = PageFactory.initElements(browser, UserProfilePage.class);
		changePasswordPage = PageFactory.initElements(browser, ChangePasswordPage.class);
	}

	@Test
	public void changePasswordTest() throws InterruptedException {
		String invalidData = "Please follow the instructions";
		String sameOldNew = "New password is identical to old password!";
		String badOldPw = "The entered old password is not correct!";
		
		homePage.ensureLoginIsDisplayed();
		homePage.getLoginNavBtn().click();
		
		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

		loginPage.setUsernameInput("user2");
		loginPage.setPasswordInput("user");
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();
		
		Thread.sleep(1000);
		
		homePageRegistered.ensureProfileIsDisplayed();
		
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());
		homePageRegistered.getProfileNavBtn().click();

		Thread.sleep(500);
		assertEquals("http://localhost:4200/profile", browser.getCurrentUrl());
		
		userProfilePage.getPasswordChangeButton().click();

		Thread.sleep(500);
		assertEquals("http://localhost:4200/changePassword", browser.getCurrentUrl());
		
		//empty fields
		changePasswordPage.getChangePasswordButton().click();
		assertEquals(invalidData, changePasswordPage.getHiddentErrorValue());
		
		//only old pw
		changePasswordPage.setOldPasswordInput("user");
		changePasswordPage.getChangePasswordButton().click();
		assertEquals(invalidData, changePasswordPage.getHiddentErrorValue());
		

		//short new pw
		changePasswordPage.setOldPasswordInput("user");
		changePasswordPage.setNewPasswordInput("as");
		changePasswordPage.setConfirmPasswordInput("as");
		changePasswordPage.getChangePasswordButton().click();
		assertEquals(invalidData, changePasswordPage.getHiddentErrorValue());
		

		//short same old and new pw
		changePasswordPage.setOldPasswordInput("asdasd");
		changePasswordPage.setNewPasswordInput("asdasd");
		changePasswordPage.setConfirmPasswordInput("asdasd");
		changePasswordPage.getChangePasswordButton().click();
		assertEquals(sameOldNew, changePasswordPage.getHiddentErrorValue());
		

		//short same new and confirm not same
		changePasswordPage.setOldPasswordInput("asd");
		changePasswordPage.setNewPasswordInput("asdasd");
		changePasswordPage.setConfirmPasswordInput("asdasdasd");
		changePasswordPage.getChangePasswordButton().click();
		assertEquals(invalidData, changePasswordPage.getHiddentErrorValue());
		

		//badoldpw
		changePasswordPage.setOldPasswordInput("asd");
		changePasswordPage.setNewPasswordInput("asdasd");
		changePasswordPage.setConfirmPasswordInput("asdasd");
		changePasswordPage.getChangePasswordButton().click();
		Thread.sleep(1000);
		assertEquals(badOldPw, changePasswordPage.getHiddentErrorValue());

		//succesful change
		changePasswordPage.setOldPasswordInput("user");
		changePasswordPage.setNewPasswordInput("asdasdasd");
		changePasswordPage.setConfirmPasswordInput("asdasdasd");
		changePasswordPage.getChangePasswordButton().click();

		Thread.sleep(1000);

		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());

		loginPage.setUsernameInput("user2");
		loginPage.setPasswordInput("asdasdasd");
		assertTrue(loginPage.getLoginButton().isEnabled());
		loginPage.getLoginButton().click();
		
		Thread.sleep(1000);
		assertEquals("http://localhost:4200/events", browser.getCurrentUrl());
		
		homePageRegistered.ensureProfileIsDisplayed();
		homePageRegistered.getProfileNavBtn().click();

		Thread.sleep(500);
		assertEquals("http://localhost:4200/profile", browser.getCurrentUrl());
		
		userProfilePage.getPasswordChangeButton().click();

		Thread.sleep(500);
		assertEquals("http://localhost:4200/changePassword", browser.getCurrentUrl());
		

		changePasswordPage.setOldPasswordInput("asdasdasd");
		changePasswordPage.setNewPasswordInput("user");
		changePasswordPage.setConfirmPasswordInput("user");
		changePasswordPage.getChangePasswordButton().click();
		Thread.sleep(1000);

		homePage.ensureLoginIsDisplayed();
		assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
		
	}

	@After
	public void closeSelenium() {
		browser.quit();
	}
}
