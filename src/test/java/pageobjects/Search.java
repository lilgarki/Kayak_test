package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import utilities.DriverManager;

import java.rmi.server.ExportException;

import static utilities.WaitUtils.*;
import static utilities.BaseUtils.*;

public class Search {
    private static final By BLUR_CLICK = By.xpath("//*[@class='primary-content']");
    private static final By EXISTING_ITEM = By.xpath("//*[contains(@class, '-item-button')]");
    private static final By PRESENTATION_ITEMS = By.xpath("//*[contains(@class,'pres-horizon')]");
    private static final By FROM_FIELD = By.xpath("//*[contains(@class, 'haOe-origin')]");
    private static final By FIELD_INPUT = By.xpath("//*[contains(@class, 'k_my-input')]");
    private static final By TO_FIELD = By.xpath("//*[contains(@class, 'haOe-destination')]");
    private static final By DATES_FIELD = By.xpath("(//*[contains(@class,'-dates')]//*[contains(@class,'-input')])[1]");
    private static final By END_DATE_FIELD = By.xpath("(//*[contains(@class,'-dates')]//*[contains(@class,'-input')])[2]");
    private static final By SUBMIT_BUTTON = By.xpath("//*[contains(@class, '-submit')]");
    private static final By POPOVER = By.xpath("//div[contains(@class,'Popover')]");

    public Search enterOriginAirport(String airportInput) throws InterruptedException {
        Thread.sleep(500);
        DriverManager.getDriver().findElement(By.xpath("//*[contains(@class, '-item-button')]")).click();
        DriverManager.getDriver().findElement(By.xpath("//input[@placeholder='Von?']")).sendKeys(airportInput);
        Thread.sleep(1000);
        DriverManager.getDriver().findElement(By.xpath("//input[@placeholder='Von?']")).sendKeys(Keys.TAB);
        Thread.sleep(500);
        blur();
        return this;
    }

    public Search enterDepartureAirport(String airportInput) throws InterruptedException {
        Thread.sleep(500);
        try {
            DriverManager.getDriver().findElement(By.xpath("(//*[contains(@class, '-item-button')])[2]")).click();
        } catch (Exception e) {

        }
        DriverManager.getDriver().findElement(By.xpath("//input[@placeholder='Nach?']")).sendKeys(airportInput);
        Thread.sleep(1000);
        DriverManager.getDriver().findElement(By.xpath("//input[@placeholder='Nach?']")).sendKeys(Keys.TAB);
        Thread.sleep(500);
        blur();
        return this;
    }

    public Search enterDepartureDate(String dateInput) {
        clickOnElement(DATES_FIELD);
        new DatePicker().selectDate(dateInput);
        blur();
        return this;
    }

    public Search enterArrivalDate(String dateInput) {
        clickOnElement(END_DATE_FIELD);
        new DatePicker().selectDate(dateInput);
        blur();
        return this;
    }

    public SearchResults submit() {
        clickOnElement(SUBMIT_BUTTON);
        return new SearchResults();
    }

    private void blur() {
        clickOnElement(BLUR_CLICK);
    }

    private boolean isPopoverHidden() {
        return !(getWebElements(POPOVER).size() > 0);
    }
}
