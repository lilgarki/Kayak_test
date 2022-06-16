package pageobjects;

import org.openqa.selenium.By;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static utilities.BaseUtils.clickOnElement;
import static utilities.BaseUtils.getElementText;
import static utilities.WaitUtils.waitForConditionToBeTrue;
import static utilities.WaitUtils.waitForMonthToUpdate;

public class DatePicker {
    private static final By DEPARTURE_MONTH = By.xpath("(//*[contains(@class, 'monthName')])[1]");
    private static final By ARRIVAL_MONTH = By.xpath("(//*[contains(@class, 'monthName')])[2]");
    private static final By NEXT_MONTH = By.xpath("(//div[contains(@class, '-arrow') and @role='button'])[2]");

    public DatePicker selectDate(String dateInput) {
        String targetMonth;
        Date originalDate;
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", Locale.GERMAN);
        SimpleDateFormat dayFormat = new SimpleDateFormat("d. MMMM yyyy", Locale.GERMAN);
        try {
            originalDate = inputFormat.parse(dateInput);
            targetMonth = monthFormat.format(originalDate).replaceAll("\\s", "");
        } catch (ParseException e) {
            throw new IllegalArgumentException("The provided date is not allowed!");
        }

        waitForConditionToBeTrue(this::isDepartureMonthLoaded, "Month presentations are not yet loaded!");

        while (!targetMonth.equalsIgnoreCase(getElementText(DEPARTURE_MONTH).replaceAll("\\s", "")) && !targetMonth.equalsIgnoreCase(getElementText(ARRIVAL_MONTH).replaceAll("\\s", ""))) {
            clickOnElement(NEXT_MONTH);
            waitForMonthToUpdate();
        }

        clickOnElement(getDayLocator(dayFormat.format(originalDate)));

        return this;
    }

    private boolean isDepartureMonthLoaded() {
        return !"".equals(getElementText(DEPARTURE_MONTH));
    }

    private By getDayLocator(String date) {
        return By.xpath(String.format("//div[not(contains(@class,'isHidden')) and @aria-label='%s']", date));
    }
}
