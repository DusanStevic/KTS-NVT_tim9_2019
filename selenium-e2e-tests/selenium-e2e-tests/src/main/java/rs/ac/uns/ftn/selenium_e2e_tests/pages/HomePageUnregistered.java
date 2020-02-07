package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageUnregistered {

	private WebDriver driver;
	
	@FindBy(id = "login-btn")
	private WebElement loginNavBtn;

	@FindBy(id = "register-btn")
	private WebElement registerNavBtn;

	public HomePageUnregistered(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public WebElement getLoginNavBtn() {
		return loginNavBtn;
	}

	public void setLoginNavBtn(WebElement loginNavBtn) {
		this.loginNavBtn = loginNavBtn;
	}

	public WebElement getRegisterNavBtn() {
		return registerNavBtn;
	}

	public void setRegisterNavBtn(WebElement registerNavBtn) {
		this.registerNavBtn = registerNavBtn;
	}
	
	public void ensureLoginIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(loginNavBtn));
	}

	public void ensureRegisterIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(registerNavBtn));
	}
	

}
