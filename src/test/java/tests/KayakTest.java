package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobjects.Search;
import pageobjects.SearchResults;
import utilities.DriverManager;

import java.io.File;

public class KayakTest {
    private final static String BASE_URL = "http://kayak.ch/flights";

    @Test(dataProvider = "data-provider")
    public void searchHappyPath(String originAirport,String departureAirport,String departureDate,String arrivalDate,int maxAmount) throws InterruptedException {
        String[] csvArray;
        DriverManager.getDriver().get(BASE_URL);
        Search search = new Search();

        Thread.sleep(2000);
        try {
            DriverManager.getDriver().findElement(By.xpath("//div[contains(text(),'Nein, danke')]")).click();
        } catch (Exception e) {

        }
        Thread.sleep(2000);

        search.enterOriginAirport(originAirport)
                .enterDepartureAirport(departureAirport)
                .enterDepartureDate(departureDate)
                .enterArrivalDate(arrivalDate)
                .submit();
        Thread.sleep(1000);
        SearchResults searchResults = new SearchResults();
        searchResults.closeDialog().setSliderValue(maxAmount);
        //Assert.assertTrue(searchResults.getSearchResultsCount() > 0, "No search results were found!");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(searchResults.getSearchResultsCount() > 0, "No search results were found!");
        //Assert.assertTrue(searchResults.getSearchResultsPrices().stream().allMatch(e -> e < maxAmount));
        softAssert.assertTrue(searchResults.getSearchResultsPrices().stream().allMatch(e -> e < maxAmount));
    }

    @DataProvider(name = "data-provider")
    public Object[][] dpMethod(){
        return new Object[][] {{"LON","ZRH","24.07.2022","30.07.2022",310},
                {"ZRH","LON","04.08.2022","30.08.2022",1390}};
    }
}
