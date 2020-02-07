package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;

	@FindBy(id = "username")
	private WebElement usernameInput;

	@FindBy(id = "password")
	private WebElement passwordInput;

	@FindBy(id = "loginInputBtn")
	private WebElement loginButton;

	/*
	 * @FindBy(css = "toast-message ng-star-inserted") private WebElement
	 * toastrNotification;
	 */

	@FindBy(id = "hiddenErrorMessage")
	private WebElement hiddentError;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getUsernameInput() {
		return usernameInput;
	}

	public void setUsernameInput(String value) {
		WebElement e = getUsernameInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getPasswordInput() {
		return passwordInput;
	}

	public void setPasswordInput(String value) {
		WebElement e = getPasswordInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(WebElement loginButton) {
		this.loginButton = loginButton;
	}

	public void ensureLoginButtonDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(loginButton));
	}

	public void ensureUsernameInputIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(usernameInput));
	}

	public void ensurePasswordInputIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(passwordInput));
	}

	public WebElement getHiddentError() {
		return hiddentError;
	}

	public void setHiddentError(WebElement hiddentError) {
		this.hiddentError = hiddentError;
	}

	public String getHiddentErrorValue() {
		return hiddentError.getAttribute("value");
	}

}
