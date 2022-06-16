package tests;


import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utilities.DriverManager;

public class SetupDriver {
    public SetupDriver() {
    }

    @BeforeTest
    public void prepare() {
        DriverManager.getDriver("chrome");
    }

    @AfterTest
    public void tearDown() {
        DriverManager.quitWebDriver();
    }
}
