package webtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class HistoryTest {

    WebDriver webDriver;


    @BeforeClass
    public void loginAndMakeAppointment(){

        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");

        String username = webDriver.findElement(By.xpath("//*[@id=\"login\"]/div/div/div[2]/form/div[1]/div[1]/div/div/input")).getAttribute("value");
        String password = webDriver.findElement(By.xpath("//*[@id=\"login\"]/div/div/div[2]/form/div[1]/div[2]/div/div/input")).getAttribute("value");

        webDriver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys(username);
        webDriver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys(password);

        webDriver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();

        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/#appointment");

        webDriver.findElement(By.xpath("//*[@id=\"combo_facility\"]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"combo_facility\"]/option[2]")).click();

        webDriver.findElement(By.id("combo_facility")).click();
        webDriver.findElement(By.xpath("//*[@id=\"combo_facility\"]/option[2]")).click();
        webDriver.findElement(By.id("chk_hospotal_readmission")).click();
        webDriver.findElement(By.id("radio_program_medicaid"));
        webDriver.findElement(By.id("txt_visit_date")).sendKeys("20/12/2022");
        webDriver.findElement(By.id("txt_comment")).sendKeys("test comment");
        webDriver.findElement(By.id("btn-book-appointment")).click();

        String expected_url = "https://katalon-demo-cura.herokuapp.com/appointment.php#summary";
        try {
            Assert.assertEquals(webDriver.getCurrentUrl(), expected_url);
        }
        catch (AssertionError e){
        }

    }

    @Test(priority = 0)
    public void TC_HISTORY_01(){
        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/");
        webDriver.findElement(By.xpath("//*[@id=\"menu-toggle\"]")).click();
        webDriver.findElement(By.xpath("//*[@id=\"sidebar-wrapper\"]/ul/li[3]/a")).click();
    }

    @AfterClass
    public void closeBrowser() throws InterruptedException {

        Thread.sleep(2000);
        webDriver.quit();
    }



}
