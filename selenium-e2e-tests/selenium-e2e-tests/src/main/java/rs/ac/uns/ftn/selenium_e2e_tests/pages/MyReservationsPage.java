package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyReservationsPage {

	private WebDriver driver;
	
	@FindBy(id = "reservationDetails1")
	private WebElement resForPurchaseBtn;

	@FindBy(id = "reservationDetails2")
	private WebElement resForCancelBtn;

	public MyReservationsPage(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public WebElement getResForPurchaseBtn() {
		return resForPurchaseBtn;
	}

	public void setResForPurchaseBtn(WebElement resForPurchaseBtn) {
		this.resForPurchaseBtn = resForPurchaseBtn;
	}

	public WebElement getResForCancelBtn() {
		return resForCancelBtn;
	}

	public void setResForCancelBtn(WebElement resForCancelBtn) {
		this.resForCancelBtn = resForCancelBtn;
	}
	
	public void ensurePurchaseResIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(resForPurchaseBtn));
	}

	public void ensureCancelResIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(resForCancelBtn));
	}
	
	public int getReservationTableSize() {
		return driver.findElements(By.cssSelector("tr")).size();
	}
	
}
