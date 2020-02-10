package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FormLocationPage {
	@SuppressWarnings("unused")
	private WebDriver driver;

	@FindBy(id = "name-input")
	private WebElement nameInput;

	@FindBy(id = "description-input")
	private WebElement descriptionInput;

	@FindBy(id = "address-input")
	private WebElement addressInput;

	@FindBy(id = "latitude-input")
	private WebElement latitudeInput;

	@FindBy(id = "longitude-input")
	private WebElement longitudeInput;

	@FindBy(id = "submit-location-btn")
	private WebElement submitLocationInput;

	@FindBy(id = "reset-btn")
	private WebElement resetBtn;

	@FindBy(id = "hiddenErrorMessage")
	private WebElement hiddentError;

	public FormLocationPage(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public WebElement getNameInput() {
		return nameInput;
	}

	public void setNameInput(String value) {
		WebElement e = getNameInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getDescriptionInput() {
		return descriptionInput;
	}

	public void setDescriptionInput(String value) {
		WebElement e = getDescriptionInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getAddressInput() {
		return addressInput;
	}

	public void setAddressInput(String value) {
		WebElement e = getAddressInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getLatitudeInput() {
		return latitudeInput;
	}

	public void setLatitudeInput(String value) {
		WebElement e = getLatitudeInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getLongitudeInput() {
		return longitudeInput;
	}

	public void setLongitudeInput(String value) {
		WebElement e = getLongitudeInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getSubmitLocationInput() {
		return submitLocationInput;
	}

	public void setSubmitLocationInput(WebElement submitLocationInput) {
		this.submitLocationInput = submitLocationInput;
	}

	public WebElement getResetBtn() {
		return resetBtn;
	}

	public void setResetBtn(WebElement resetBtn) {
		this.resetBtn = resetBtn;
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
