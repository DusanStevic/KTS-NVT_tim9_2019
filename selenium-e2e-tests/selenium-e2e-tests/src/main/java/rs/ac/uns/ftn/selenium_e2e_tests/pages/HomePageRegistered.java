package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageRegistered {

	private WebDriver driver;

	@FindBy(id = "eventsBtnNav")
	private WebElement eventsNavBtn;

	@FindBy(id = "myReservationsNav")
	private WebElement myReservationsNavBtn;

	@FindBy(id = "myProfileNav")
	private WebElement profileNavBtn;

	@FindBy(id = "logout-btn")
	private WebElement logoutNavBtn;

	public HomePageRegistered(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getEventsNavBtn() {
		return eventsNavBtn;
	}

	public void setEventsNavBtn(WebElement eventsNavBtn) {
		this.eventsNavBtn = eventsNavBtn;
	}

	public WebElement getMyReservationsNavBtn() {
		return myReservationsNavBtn;
	}

	public void setMyReservationsNavBtn(WebElement myReservationsNavBtn) {
		this.myReservationsNavBtn = myReservationsNavBtn;
	}

	public WebElement getProfileNavBtn() {
		return profileNavBtn;
	}

	public void setProfileNavBtn(WebElement profileNavBtn) {
		this.profileNavBtn = profileNavBtn;
	}

	public WebElement getLogoutNavBtn() {
		return logoutNavBtn;
	}

	public void setLogoutNavBtn(WebElement logoutNavBtn) {
		this.logoutNavBtn = logoutNavBtn;
	}

	public void ensureEventsBtnIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(eventsNavBtn));
	}

	public void ensureMyReservationsIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(myReservationsNavBtn));
	}

	public void ensureProfileIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(profileNavBtn));
	}

	public void ensureLogoutIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(logoutNavBtn));
	}

}
