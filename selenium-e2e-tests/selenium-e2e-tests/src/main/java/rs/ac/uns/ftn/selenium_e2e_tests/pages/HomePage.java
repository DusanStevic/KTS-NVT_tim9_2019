package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	private WebDriver driver;

	
	@FindBy(css = "a[title=\"Contact Us\"]")
	//@FindBy(css = "a[title='Contact Us']")
	//@FindBy(css = "a[title='Contact Us']")
	//@FindBy(id = "#contact-link > a")
	//@FindBy(xpath = "//*[@id=\"contact-link\"]/a")
	private WebElement contactUsLink;

	//@FindBy(css = "a[class='login']")
	@FindBy(css = "a[title='Log in to your customer account']")
	//@FindBy(xpath = "//*[@id=\"header\"]/div[2]/div/div/nav/div[1]/a")
	//@FindBy(className = "login")
	private WebElement singInLink;

	@FindBy(id = "search_query_top")
	//@FindBy(xpath = "//*[@id=\"search_query_top\"]")
	//@FindBy(id="#search_query_top")
	//@FindBy(css = "input[placeholder='Search']")
	//@FindBy(css = "input[name='search_query']")
	private WebElement searchInput;

	@FindBy(name = "submit_search")
	private WebElement submitSearchButton;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getContactUsLink() {
		return contactUsLink;
	}

	public void setContactUsLink(WebElement contactUsLink) {
		this.contactUsLink = contactUsLink;
	}

	public WebElement getSingInLink() {
		return singInLink;
	}

	public void setSingInLink(WebElement singInLink) {
		this.singInLink = singInLink;
	}

	public WebElement getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(String value) {
		WebElement el = getSearchInput();
		el.clear();
		el.sendKeys(value);
	}

	public WebElement getSubmitSearchButton() {
		return submitSearchButton;
	}

	public void ensureContactUsIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(contactUsLink));
	}

	public void ensureSingInIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(singInLink));
	}
	
	public void ensureSubmitSearchButtonDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(submitSearchButton));
	}
}
