package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.BooleanSupplier;

import static utilities.DriverManager.getDriver;

public class WaitUtils {
    static final WebDriverWait wait = new WebDriverWait(getDriver(), getDriver().manage().timeouts().getImplicitWaitTimeout());

    public static void waitForConditionToBeTrue(BooleanSupplier method, String errorMessage) {
        waitForConditionToBeTrue(method, errorMessage, getDriver().manage().timeouts().getImplicitWaitTimeout());
    }

    public static void waitForConditionToBeTrue(BooleanSupplier method, String errorMessage, Duration timeout) {
        wait.withTimeout(timeout);

        try {
            wait.until((ExpectedCondition<Boolean>) driver -> method.getAsBoolean());
        } catch (TimeoutException e) {
            throw new TimeoutException(errorMessage);
        }
    }

    public static void waitForMonthToUpdate() {
        waitForTime(Duration.ofMillis(500));
    }

    private static void waitForTime(Duration time) {
        try {
            Thread.sleep(time.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForElementToBeClickable(By locator) {
        new WebDriverWait(getDriver(), Duration.ofMillis(3000)).until(ExpectedConditions.elementToBeClickable(locator));
    }
}
