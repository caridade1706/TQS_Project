package ua.deti.tqs;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;
import java.util.Map;

public class StaffInterfaceSteps {

    ChromeDriver driver = new ChromeDriver();
    private static final Logger logger = getLogger(lookup().lookupClass());
    static String bookingId = "";

    @Given("I am on the CliniConnect Staff homepage on {string}")
    public void i_am_on_the_CliniConnect_Staff_homepage(String url) {
        logger.info("Opening browser at {}", url);
        driver.get(url);
        driver.manage().window().setSize(new Dimension(1440, 900));
    }

    @When("I click on the Sign Up button")
    public void i_click_on_the_Sign_up_button() {

        WebElement elementSingUpButton = driver.findElement(By.cssSelector("a:nth-child(2) > .box-button"));
        logger.info("Clicking on the Sign Up button");
        elementSingUpButton.click();
    }

    @And("I fill in the form with the following information:")
    public void i_fill_in_the_form_with_the_following_information(Map<String, String> info) {
        logger.info("Filling the info in the form: {}.", info);

        WebElement element = driver.findElement(By.name("Name"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("Name"));

        element = driver.findElement(By.name("Date of Birth"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("DOB"));

        element = driver.findElement(By.name("email"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("Email"));

        element = driver.findElement(By.name("password"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("Password"));

        element = driver.findElement(By.name("phone"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("Phone"));

        element = driver.findElement(By.name("address"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("Address"));

        element = driver.findElement(By.name("city"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("City"));

        element = driver.findElement(By.name("city"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("City"));

        element = driver.findElement(By.name("Department"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("Departament"));

        element = driver.findElement(By.name("Task"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("Task"));
    }

    @And("I click on the Register Button")
    public void i_click_on_the_Register_Button() {
        logger.info("Clicking on the Register Button");
        WebElement element = driver.findElement(By.cssSelector(".register-button"));
        element.click();
    }

    @Then("I should get an alert saying {string} and accpet it")
    public void i_should_get_an_alert_saying_and_accpet_it(String message) {
        logger.info("Waiting for alert with message: {}", message);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals(message, alert.getText());
        alert.accept();
        logger.info("Alert with message '{}' accepted.", message);
    }

    @Given("I am on the CliniConnect Staff homepage on {string} and I am not logged in")
    public void i_am_on_the_CliniConnect_Staff_homepage_on_and_I_am_logged_in(String url) {
        logger.info("Opening browser at {}", url);
        driver.get(url);
        driver.manage().window().setSize(new Dimension(1440, 900));
    }    

    @When("I click on the HomePgae Login button")
    public void i_click_on_the_Log_In_button() {
        logger.info("Clicking on the Log In button");
        WebElement element = driver.findElement(By.cssSelector("a:nth-child(1) > .box-button"));
        element.click();
    }

    @And("I fill in the form with the following login information:")
    public void i_fill_in_the_form_with_the_following_information2(Map<String, String> info) {
        logger.info("Filling the info in the form: {}.", info);

        WebElement element = driver.findElement(By.name("email"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("Email"));

        element = driver.findElement(By.name("password"));
        assertTrue(element.isEnabled() && element.getAttribute("readonly") == null);
        element.click();
        element.sendKeys(info.get("Password"));
    }

    @And("I click on the Login Button")
    public void i_click_on_the_Log_In_Button() {
        logger.info("Clicking on the Log In Button");
        WebElement element = driver.findElement(By.cssSelector(".login-button"));
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Then("I should be redirected to the Staff homepage") 
    public void i_should_be_redirected_to_the_Staff_homepage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("http://deti-tqs-09.ua.pt:3000/staffpage"));
        logger.info("Checking if the current URL is the Staff homepage");
    }

    @io.cucumber.java.After
    public void tearDown() {
        driver.quit();
    }
}