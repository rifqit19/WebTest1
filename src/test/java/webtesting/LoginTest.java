package webtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTest {

    WebDriver webDriver;

    @BeforeClass
    public void openBrowser(){

        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterMethod
    public void logout() throws InterruptedException {
        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/authenticate.php?logout");
        Thread.sleep(1000);
    }

    @Test(priority = 0)
    public void TC_LOGIN_01(){
        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");

        String username = webDriver.findElement(By.xpath("//*[@id=\"login\"]/div/div/div[2]/form/div[1]/div[1]/div/div/input")).getAttribute("value");
        String password = webDriver.findElement(By.xpath("//*[@id=\"login\"]/div/div/div[2]/form/div[1]/div[2]/div/div/input")).getAttribute("value");

        webDriver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys(username);
        webDriver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys(password);

        webDriver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();

        Assert.assertEquals(webDriver.findElement(By.xpath("//*[@id=\"appointment\"]/div/div/div/h2")).getText(), "Make Appointment");

    }

    @Test(priority = 1)
    public void TC_LOGIN_02(){
        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");

        webDriver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys("username palsu");
        webDriver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys("ThisIsNotAPassword");

        webDriver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();

        Assert.assertEquals(webDriver.findElement(By.xpath("//*[@id=\"login\"]/div/div/div[1]/p[2]")).getText(), "Login failed! Please ensure the username and password are valid.");

    }

    @Test(priority = 2)
    public void TC_LOGIN_03(){
        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");

        webDriver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys("John Doe");
        webDriver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys("password salah");

        webDriver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();

        Assert.assertEquals(webDriver.findElement(By.xpath("//*[@id=\"login\"]/div/div/div[1]/p[2]")).getText(), "Login failed! Please ensure the username and password are valid.");

    }

    @Test(priority = 3)
    public void TC_LOGIN_04(){
        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");

        webDriver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys("username salah");
        webDriver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys("password salah");

        webDriver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();

        Assert.assertEquals(webDriver.findElement(By.xpath("//*[@id=\"login\"]/div/div/div[1]/p[2]")).getText(), "Login failed! Please ensure the username and password are valid.");

    }

    @DataProvider(name = "data-login")
    public Object[][] dataLogin(){
        return new Object[][]{{"John Doe","ThisIsNotAPassword","Sucess"},
                {"username palsu","ThisIsNotAPassword","Failed"},
                {"John Doe","password palsu","Failed"},
                {"username palsu","password palsu","Failed"}
        };
    }

    @Test(dataProvider = "data-login")
    public void TC_05(String username, String password, String result){
        webDriver.navigate().to("https://katalon-demo-cura.herokuapp.com/profile.php#login");

        webDriver.findElement(By.xpath("//*[@id=\"txt-username\"]")).sendKeys(username);
        webDriver.findElement(By.xpath("//*[@id=\"txt-password\"]")).sendKeys(password);

        webDriver.findElement(By.xpath("//*[@id=\"btn-login\"]")).click();

        if (result.equals("Sucess")){
            Assert.assertEquals(webDriver.findElement(By.xpath("//*[@id=\"appointment\"]/div/div/div/h2")).getText(), "Make Appointment");
        } else if (result.equals("Failed")) {
            Assert.assertEquals(webDriver.findElement(By.xpath("//*[@id=\"login\"]/div/div/div[1]/p[2]")).getText(), "Login failed! Please ensure the username and password are valid.");
        }

    }



    @AfterClass
    public void closeBrowser() throws InterruptedException {

        Thread.sleep(2000);
        webDriver.quit();
    }



}
