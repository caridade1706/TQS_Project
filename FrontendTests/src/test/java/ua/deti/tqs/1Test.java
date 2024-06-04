// // Generated by Selenium IDE
// import org.junit.Test;
// import org.junit.Before;
// import org.junit.After;
// import static org.junit.Assert.*;
// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.core.IsNot.not;
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.remote.RemoteWebDriver;
// import org.openqa.selenium.remote.DesiredCapabilities;
// import org.openqa.selenium.Dimension;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.interactions.Actions;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;
// import org.openqa.selenium.JavascriptExecutor;
// import org.openqa.selenium.Alert;
// import org.openqa.selenium.Keys;
// import java.util.*;
// import java.net.MalformedURLException;
// import java.net.URL;
// public class 1Test {
//   private WebDriver driver;
//   private Map<String, Object> vars;
//   JavascriptExecutor js;
//   @Before
//   public void setUp() {
//     driver = new ChromeDriver();
//     js = (JavascriptExecutor) driver;
//     vars = new HashMap<String, Object>();
//   }
//   @After
//   public void tearDown() {
//     driver.quit();
//   }
//   @Test
//   public void 1() {
  
//     driver.findElement(By.name("name")).click();
//     driver.findElement(By.name("name")).sendKeys("Aveiro Hospital ");
//     driver.findElement(By.name("address")).sendKeys("Rua ");
//     driver.findElement(By.name("name")).click();
//     driver.findElement(By.name("name")).sendKeys("Aveiro Hospital");
//     driver.findElement(By.name("address")).click();
//     driver.findElement(By.name("address")).sendKeys("Rua da Pega");
//     driver.findElement(By.name("city")).sendKeys("Aveiro");
//     driver.findElement(By.cssSelector(".add-hospital-btn")).click();
//     assertThat(driver.switchTo().alert().getText(), is("Hospital added successfully!"));
//     driver.findElement(By.name("hospital")).click();
//     driver.findElement(By.cssSelector(".form-d-container > input:nth-child(1)")).click();
//     driver.findElement(By.cssSelector(".form-d-container > input:nth-child(1)")).sendKeys("Tiago Gomes");
//     driver.findElement(By.name("dob")).sendKeys("0002-11-10");
//     driver.findElement(By.name("dob")).sendKeys("0020-11-10");
//     driver.findElement(By.name("dob")).sendKeys("0200-11-10");
//     driver.findElement(By.name("dob")).sendKeys("2002-11-10");
//     driver.findElement(By.name("email")).sendKeys("tg@ua.pt");
//     driver.findElement(By.name("phone")).sendKeys("23443567");
//     driver.findElement(By.cssSelector("input:nth-child(5)")).sendKeys("Fundevila");
//     driver.findElement(By.cssSelector("input:nth-child(6)")).sendKeys("Sago");
//     driver.findElement(By.name("speciality")).sendKeys("Cardiologista");
//     driver.findElement(By.name("hospital")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("hospital"));
//       dropdown.findElement(By.xpath("//option[. = 'Aveiro Hospital']")).click();
//     }
//     driver.findElement(By.cssSelector(".add-doctor-btn")).click();
//     assertThat(driver.switchTo().alert().getText(), is("Doctor added successfully!"));
//     driver.findElement(By.cssSelector("a:nth-child(1) > .navbar-button")).click();
//     {
//       WebElement element = driver.findElement(By.cssSelector("a:nth-child(1) > .navbar-button"));
//       Actions builder = new Actions(driver);
//       builder.moveToElement(element).perform();
//     }
//     {
//       WebElement element = driver.findElement(By.tagName("body"));
//       Actions builder = new Actions(driver);
//       builder.moveToElement(element, 0, 0).perform();
//     }
//     driver.findElement(By.cssSelector(".new-consultation-btn")).click();
//     {
//       WebElement element = driver.findElement(By.cssSelector(".new-consultation-btn"));
//       Actions builder = new Actions(driver);
//       builder.moveToElement(element).perform();
//     }
//     {
//       WebElement element = driver.findElement(By.tagName("body"));
//       Actions builder = new Actions(driver);
//       builder.moveToElement(element, 0, 0).perform();
//     }
//     driver.findElement(By.name("patientName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("patientName"));
//       dropdown.findElement(By.xpath("//option[. = 'Matilde']")).click();
//     }
//     driver.findElement(By.name("date")).click();
//     driver.findElement(By.name("date")).sendKeys("2024-06-04");
//     driver.findElement(By.name("time")).click();
//     driver.findElement(By.name("time")).sendKeys("10:00");
//     driver.findElement(By.cssSelector(".form-container")).click();
//     driver.findElement(By.name("doctorName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("doctorName"));
//       dropdown.findElement(By.xpath("//option[. = 'Tiago Gomes']")).click();
//     }
//     driver.findElement(By.name("doctorName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("doctorName"));
//       dropdown.findElement(By.xpath("//option[. = 'Tiago Gomes']")).click();
//     }
//     driver.findElement(By.name("doctorName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("doctorName"));
//       dropdown.findElement(By.xpath("//option[. = 'Tiago Gomes']")).click();
//     }
//     driver.findElement(By.name("doctorName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("doctorName"));
//       dropdown.findElement(By.xpath("//option[. = 'Tiago Gomes']")).click();
//     }
//     driver.findElement(By.name("type")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("type"));
//       dropdown.findElement(By.xpath("//option[. = 'Select a Specialty']")).click();
//     }
//     driver.findElement(By.name("doctorName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("doctorName"));
//       dropdown.findElement(By.xpath("//option[. = 'Tiago Gomes']")).click();
//     }
//     driver.findElement(By.name("doctorName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("doctorName"));
//       dropdown.findElement(By.xpath("//option[. = 'Select a Doctor']")).click();
//     }
//     driver.findElement(By.name("type")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("type"));
//       dropdown.findElement(By.xpath("//option[. = 'Cardiologista']")).click();
//     }
//     driver.findElement(By.name("doctorName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("doctorName"));
//       dropdown.findElement(By.xpath("//option[. = 'Tiago Gomes']")).click();
//     }
//     driver.findElement(By.name("hospitalName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("hospitalName"));
//       dropdown.findElement(By.xpath("//option[. = 'Aveiro\'s Hospital']")).click();
//     }
//     driver.findElement(By.cssSelector(".add-consultation-btn")).click();
//     assertThat(driver.switchTo().alert().getText(), is("Failed to add consultation"));
//     driver.findElement(By.cssSelector(".close-button")).click();
//     driver.findElement(By.cssSelector("a:nth-child(2) > .navbar-button")).click();
//     driver.findElement(By.cssSelector(".form-d-container > input:nth-child(1)")).click();
//     driver.findElement(By.cssSelector(".form-d-container > input:nth-child(1)")).sendKeys("Rafael Guerreiro");
//     driver.findElement(By.name("email")).click();
//     driver.findElement(By.name("email")).sendKeys("rg@ua.pt");
//     driver.findElement(By.name("phone")).sendKeys("2345645");
//     driver.findElement(By.cssSelector("input:nth-child(5)")).sendKeys("Rua da Pega 23");
//     driver.findElement(By.cssSelector("input:nth-child(6)")).click();
//     driver.findElement(By.cssSelector("input:nth-child(6)")).sendKeys("Aveiro");
//     driver.findElement(By.name("speciality")).sendKeys("Ginecologista");
//     driver.findElement(By.name("hospital")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("hospital"));
//       dropdown.findElement(By.xpath("//option[. = 'Aveiro\'s Hospital']")).click();
//     }
//     driver.findElement(By.cssSelector(".add-doctor-btn")).click();
//     driver.findElement(By.name("dob")).click();
//     driver.findElement(By.name("dob")).click();
//     driver.findElement(By.name("dob")).sendKeys("2000-04-03");
//     driver.findElement(By.name("dob")).sendKeys("2000-04-30");
//     driver.findElement(By.cssSelector(".add-doctor-btn")).click();
//     assertThat(driver.switchTo().alert().getText(), is("Doctor added successfully!"));
//     driver.findElement(By.cssSelector("a:nth-child(1) > .navbar-button")).click();
//     {
//       WebElement element = driver.findElement(By.cssSelector("a:nth-child(1) > .navbar-button"));
//       Actions builder = new Actions(driver);
//       builder.moveToElement(element).perform();
//     }
//     {
//       WebElement element = driver.findElement(By.tagName("body"));
//       Actions builder = new Actions(driver);
//       builder.moveToElement(element, 0, 0).perform();
//     }
//     driver.findElement(By.cssSelector(".new-consultation-btn")).click();
//     {
//       WebElement element = driver.findElement(By.cssSelector(".new-consultation-btn"));
//       Actions builder = new Actions(driver);
//       builder.moveToElement(element).perform();
//     }
//     {
//       WebElement element = driver.findElement(By.tagName("body"));
//       Actions builder = new Actions(driver);
//       builder.moveToElement(element, 0, 0).perform();
//     }
//     driver.findElement(By.name("doctorName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("doctorName"));
//       dropdown.findElement(By.xpath("//option[. = 'Rafael Guerreiro']")).click();
//     }
//     driver.findElement(By.cssSelector(".modal-content > div")).click();
//     driver.findElement(By.name("patientName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("patientName"));
//       dropdown.findElement(By.xpath("//option[. = 'Matilde']")).click();
//     }
//     driver.findElement(By.name("date")).click();
//     driver.findElement(By.name("date")).sendKeys("2024-06-04");
//     driver.findElement(By.cssSelector(".form-row:nth-child(2)")).click();
//     driver.findElement(By.name("time")).click();
//     driver.findElement(By.name("time")).click();
//     driver.findElement(By.name("time")).click();
//     driver.findElement(By.name("time")).click();
//     driver.findElement(By.name("time")).sendKeys("16:00");
//     driver.findElement(By.name("hospitalName")).click();
//     {
//       WebElement dropdown = driver.findElement(By.name("hospitalName"));
//       dropdown.findElement(By.xpath("//option[. = 'Aveiro Hospital']")).click();
//     }
//     driver.findElement(By.cssSelector(".form-container")).click();
//     driver.findElement(By.cssSelector(".add-consultation-btn")).click();
//     {
//       WebElement element = driver.findElement(By.cssSelector(".add-consultation-btn"));
//       Actions builder = new Actions(driver);
//       builder.moveToElement(element).perform();
//     }
//     driver.findElement(By.cssSelector("html")).click();
//     driver.findElement(By.cssSelector(".btn-checkin")).click();
//     driver.findElement(By.cssSelector(".card-category:nth-child(1) > .call-next-button")).click();
//     driver.findElement(By.cssSelector(".btn-pay")).click();
//     driver.findElement(By.cssSelector(".navbar-button:nth-child(4)")).click();
//     driver.findElement(By.cssSelector(".home-page")).click();
//   }
// }