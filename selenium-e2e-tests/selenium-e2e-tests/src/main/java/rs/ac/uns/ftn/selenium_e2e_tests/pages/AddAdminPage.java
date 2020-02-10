package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddAdminPage {
	private WebDriver driver;

	@FindBy(id = "firstname-input")
	private WebElement firstNameInput;

	@FindBy(id = "lastname-input")
	private WebElement lastNameInput;

	@FindBy(id = "email-input")
	private WebElement emailInput;

	@FindBy(id = "username-input")
	private WebElement usernameInput;

	@FindBy(id = "password-input")
	private WebElement passwordInput;

	@FindBy(id = "submitAddAdminBtn")
	private WebElement adminAddButton;

	@FindBy(id = "hiddenErrorMessage")
	private WebElement hiddentError;

	public AddAdminPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getFirstNameInput() {
		return firstNameInput;
	}

	public void setFirstNameInput(String value) {
		WebElement e = getFirstNameInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getLastNameInput() {
		return lastNameInput;
	}

	public void setLastNameInput(String value) {
		WebElement e = getLastNameInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getEmailInput() {
		return emailInput;
	}

	public void setEmailInput(String value) {
		WebElement e = getEmailInput();
		e.clear();
		e.sendKeys(value);
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

	public WebElement getAdminAddButton() {
		return adminAddButton;
	}

	public void setAdminAddButton(WebElement adminAddButton) {
		this.adminAddButton = adminAddButton;
	}

	public WebElement getHiddentError() {
		return hiddentError;
	}

	public void setHiddentError(WebElement hiddentError) {
		this.hiddentError = hiddentError;
	}

	public void ensureAddAddminBtnButtonDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(adminAddButton));
	}

	public String getHiddentErrorValue() {
		return hiddentError.getAttribute("value");
	}

}
