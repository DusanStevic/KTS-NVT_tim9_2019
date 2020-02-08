package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LocationListPage {
	private WebDriver driver;

	@FindBy(xpath = "//tr[last()]/td[1]")
	private WebElement lastTdName;

	@FindBy(xpath = "//tr[last()]/td[2]")
	private WebElement lastTdDescription;

	@FindBy(xpath = "//tr[last()]/td[3]")
	private WebElement lastTdAddress;

	@FindBy(xpath = "//tr[last()]/td[4]//button")
	private WebElement lastTdEditBtn;
	
	@FindBy(xpath = "//tr[last()]/td[5]//button")
	private WebElement lastTdDelete;

	public LocationListPage(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public WebElement getLastTdName() {
		return lastTdName;
	}

	public void setLastTdName(WebElement lastTdName) {
		this.lastTdName = lastTdName;
	}

	public WebElement getLastTdDescription() {
		return lastTdDescription;
	}

	public void setLastTdDescription(WebElement lastTdDescription) {
		this.lastTdDescription = lastTdDescription;
	}

	public WebElement getLastTdAddress() {
		return lastTdAddress;
	}

	public void setLastTdAddress(WebElement lastTdAddress) {
		this.lastTdAddress = lastTdAddress;
	}

	public WebElement getLastTdEditBtn() {
		return lastTdEditBtn;
	}

	public void setLastTdEditBtn(WebElement lastTdEditBtn) {
		this.lastTdEditBtn = lastTdEditBtn;
	}

	public WebElement getLastTdDelete() {
		return lastTdDelete;
	}

	public void setLastTdDelete(WebElement lastTdDelete) {
		this.lastTdDelete = lastTdDelete;
	}
	
	public int getLocationsTableSize() {
		return driver.findElements(By.cssSelector("tr")).size();
	}

}
