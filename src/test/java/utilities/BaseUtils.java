package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static utilities.DriverManager.getDriver;
import static utilities.WaitUtils.waitForElementToBeClickable;

public final class BaseUtils {

    private BaseUtils() {
    }

    public static void clickOnElement(By by) {
        waitForElementToBeClickable(by);
        getDriver().findElement(by).click();
    }

    public static void clickOnElement(WebElement element) {
        element.click();
    }

    public static WebElement getWebElement(By by) {
        return getDriver().findElement(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return getDriver().findElements(by);
    }

    public static void enterText(By by, String text) {
        getWebElement(by).sendKeys(text);
    }

    public static String getElementText(By by) {
        return getWebElement(by).getText();
    }

    public static String getAttribute(By locator, String attribute) {
        return getWebElement(locator).getAttribute(attribute);
    }

    public static void sendSpecialKey(Keys keys) {
        Actions action = new Actions(getDriver());
        action.sendKeys(keys).build().perform();
    }
}
