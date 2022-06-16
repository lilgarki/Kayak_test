package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utilities.DriverManager;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static utilities.BaseUtils.*;
import static utilities.WaitUtils.*;

public class SearchResults {
    private static final By CLOSE_DIALOG = By.xpath("//*[contains(@id, '-dialog-close')]");
    private static final By SEARCH_RESULTS = By.xpath("//*[@id='searchResultsList']//*[contains(@class,'Flights-Results-FlightResultItem')]");
    private static final By SEARCH_RESULTS_PRICES = By.xpath("//*[contains(@id,'price-bookingSection')]//*[contains(@id, 'price-text')]");
    private static final By PRICE_ACCORDION = By.xpath("//*[contains(@id, '-price-title')]");
    private static final By PRICE_SLIDER = By.xpath("//*[contains(@id, '-price-slider-sliderWidget-handle-0')]");
    private static final By GREEN_LIGHT = By.xpath("//*[contains(@id,'-advice')]");
    private static final By BODY = By.xpath("//body");
    private static final By LOADING_MESSAGE = By.xpath("//*[@class='resultsContainer']//*[contains(@class,'resultsListCover')]");
    private static final String GREEN_LIGHT_TEXT = "Jetzt buchen";

    public SearchResults() {
        waitForConditionToBeTrue(this::isBookingAllowed, "Booking is not allowed yet!", Duration.ofMillis(45000));
    }

    private boolean isBookingAllowed() {
        return GREEN_LIGHT_TEXT.equals(getElementText(GREEN_LIGHT));
    }

    private boolean isResultsListUpdated() {
        return !getAttribute(LOADING_MESSAGE, "class").contains("loading");
    }

    public SearchResults closeDialog() {
        if (getWebElements(CLOSE_DIALOG).size() > 0) {
            clickOnElement(BODY);
        }
        return this;
    }

    public Integer getSearchResultsCount() {
        return getWebElements(SEARCH_RESULTS).size();
    }

    public List<Integer> getSearchResultsPrices() {
        return getWebElements(SEARCH_RESULTS_PRICES).stream().map(e -> e.getText().replaceAll("[^\\d]", "")).map(Integer::parseInt).collect(Collectors.toList());
    }

    public SearchResults setSliderValue(int inputValue) {
        clickOnElement(PRICE_ACCORDION);
        waitForMonthToUpdate();
        WebElement slider = getWebElement(PRICE_SLIDER);
        Actions actions = new Actions(DriverManager.getDriver());
        do {
            actions.dragAndDropBy(slider, -10, 0).build().perform();
        } while (getMaxPriceValue() > inputValue);

        waitForConditionToBeTrue(this::isResultsListUpdated, "Results list is not yet updated!", Duration.ofMillis(20000));

        return this;
    }

    private Integer getMaxPriceValue() {
        return Integer.parseInt(getAttribute(PRICE_SLIDER, "aria-valuenow"));
    }
}
