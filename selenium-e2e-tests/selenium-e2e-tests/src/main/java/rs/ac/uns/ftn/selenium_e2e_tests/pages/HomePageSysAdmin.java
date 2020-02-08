package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageSysAdmin {

	private WebDriver driver;

	@FindBy(id = "add-admin-btn")
	private WebElement addAdminNavBtn;

	@FindBy(id = "myProfileNav")
	private WebElement profileNavBtn;

	@FindBy(id = "chartNavBtn")
	private WebElement chartNavBtn;

	@FindBy(id = "logout-btn")
	private WebElement logoutNavBtn;

	public HomePageSysAdmin(WebDriver driver) {
		super();
		this.driver = driver;
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

	public WebElement getAddAdminNavBtn() {
		return addAdminNavBtn;
	}

	public void setAddAdminNavBtn(WebElement addAdminNavBtn) {
		this.addAdminNavBtn = addAdminNavBtn;
	}

	public WebElement getChartNavBtn() {
		return chartNavBtn;
	}

	public void setChartNavBtn(WebElement chartNavBtn) {
		this.chartNavBtn = chartNavBtn;
	}

	public void ensureProfileIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(profileNavBtn));
	}

	public void ensureLogoutIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(logoutNavBtn));
	}

	public void ensureAddAdminIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(addAdminNavBtn));
	}

	public void ensureChartIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(chartNavBtn));
	}
}
