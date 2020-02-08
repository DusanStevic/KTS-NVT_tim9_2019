package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChangePasswordPage {
	
	private WebDriver driver;

	@FindBy(id = "oldPassword")
	private WebElement oldPasswordInput;

	@FindBy(id = "newPassword")
	private WebElement newPasswordInput;

	@FindBy(id = "confirmPassword")
	private WebElement confirmPasswordInput;

	@FindBy(id = "changePasswordBtn")
	private WebElement changePasswordButton;

	@FindBy(id = "hiddenErrorMessage")
	private WebElement hiddentError;

	public ChangePasswordPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getOldPasswordInput() {
		return oldPasswordInput;
	}

	public void setOldPasswordInput(String value) {
		WebElement e = getOldPasswordInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getNewPasswordInput() {
		return newPasswordInput;
	}

	public void setNewPasswordInput(String value) {
		WebElement e = getNewPasswordInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getConfirmPasswordInput() {
		return confirmPasswordInput;
	}

	public void setConfirmPasswordInput(String value) {
		WebElement e = getConfirmPasswordInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getChangePasswordButton() {
		return changePasswordButton;
	}

	public void setChangePasswordButton(WebElement changePasswordButton) {
		this.changePasswordButton = changePasswordButton;
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
	
	public void ensureChangePasswordButtonISDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(changePasswordButton));
	}

}
