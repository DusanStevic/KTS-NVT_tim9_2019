package rs.ac.uns.ftn.selenium_e2e_tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReservationDetailedPage {

	private WebDriver driver;

	@FindBy(id = "cancelBtn")
	WebElement cancelBtn;

	@FindBy(id = "notCancelingRes")
	WebElement decline;

	@FindBy(id = "cancelReservation")
	WebElement approve;

	public ReservationDetailedPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getCancelBtn() {
		return cancelBtn;
	}

	public void setCancelBtn(WebElement cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	public WebElement getDecline() {
		return decline;
	}

	public void setDecline(WebElement decline) {
		this.decline = decline;
	}

	public WebElement getApprove() {
		return approve;
	}

	public void setApprove(WebElement approve) {
		this.approve = approve;
	}

	public void ensureCancelResIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(cancelBtn));
	}

	public void ensureDeclineIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(decline));
	}

	public void ensureApproveIsDisplayed() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.elementToBeClickable(approve));
	}

}
