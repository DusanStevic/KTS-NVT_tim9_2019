package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditLocationPage {
	private WebDriver driver;

	// location
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
	private WebElement resetLocationBtn;

	// Hall form
	@FindBy(id = "hall-name-input")
	private WebElement hallNameInput;

	@FindBy(id = "nr-sitting-input")
	private WebElement sittingNrInput;

	@FindBy(id = "nr-standing-input")
	private WebElement standingNrInput;

	@FindBy(id = "submit-hall-btn")
	private WebElement submitHallBtn;

	@FindBy(id = "resetHallBtn")
	private WebElement resetHallBtn;

	// HallTable

	@FindBy(xpath = "//tr[last()]/td[1]")
	private WebElement lastTdName;

	@FindBy(xpath = "//tr[last()]/td[2]")
	private WebElement lastTdSitting;

	@FindBy(xpath = "//tr[last()]/td[3]")
	private WebElement lastTdStanding;

	/*
	 * @FindBy(xpath = "//table[@id='hallTable']/tr[last()]/td[4]//button")
	 * private WebElement lastTdEditBtn;
	 */
	@FindBy(xpath = "//tr[last()]/td[6]")
	private WebElement lastTdDelete;

	@FindBy(id = "hiddenErrorMessage")
	private WebElement hiddentError;

	public EditLocationPage(WebDriver driver) {
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

	public WebElement getResetLocationBtn() {
		return resetLocationBtn;
	}

	public void setResetBtn(WebElement resetBtn) {
		this.resetLocationBtn = resetBtn;
	}

	public WebElement getHiddentError() {
		return hiddentError;
	}

	public WebElement getHallNameInput() {
		return hallNameInput;
	}

	public void setHallNameInput(String value) {
		WebElement e = getHallNameInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getSittingNrInput() {
		return sittingNrInput;
	}

	public void setSittingNrInput(String value) {
		WebElement e = getSittingNrInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getStandingNrInput() {
		return standingNrInput;
	}

	public void setStandingNrInput(String value) {
		WebElement e = getStandingNrInput();
		e.clear();
		e.sendKeys(value);
	}

	public WebElement getSubmitHallBtn() {
		return submitHallBtn;
	}

	public void setSubmitHallBtn(WebElement submitHallBtn) {
		this.submitHallBtn = submitHallBtn;
	}

	public WebElement getResetHallBtn() {
		return resetHallBtn;
	}

	public void setResetHallBtn(WebElement resetHallBtn) {
		this.resetHallBtn = resetHallBtn;
	}

	public WebElement getLastTdName() {
		return lastTdName;
	}

	public void setLastTdName(WebElement lastTdName) {
		this.lastTdName = lastTdName;
	}

	public WebElement getLastTdSitting() {
		return lastTdSitting;
	}

	public void setLastTdSitting(WebElement lastTdSitting) {
		this.lastTdSitting = lastTdSitting;
	}

	public WebElement getLastTdStanding() {
		return lastTdStanding;
	}

	public void setLastTdStanding(WebElement lastTdStanding) {
		this.lastTdStanding = lastTdStanding;
	}

	public void setHiddentError(WebElement hiddentError) {
		this.hiddentError = hiddentError;
	}

	public String getHiddentErrorValue() {
		return hiddentError.getAttribute("value");
	}

	public WebElement getLastTdDelete() {
		return lastTdDelete;
	}

	public void setLastTdDelete(WebElement lastTdDelete) {
		this.lastTdDelete = lastTdDelete;
	}

	public int getHallTableSize() {
		return driver.findElements(By.cssSelector("#hallTable tr")).size() - 1;
	}
}
