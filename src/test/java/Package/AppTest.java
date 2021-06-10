package Package;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import objectRepository.LandingPageRSAcademy;
import resources.BaseMain;

public class AppTest extends BaseMain{
  
  //creating local driver because if test run in parallel , may cause weird result.
  public WebDriver driver;
  //Initialize the Object for logger, to log something in log file.
  public static Logger log = LogManager.getLogger(BaseMain.class.getClass());
  
  @BeforeTest
  public void InitBrowser() throws IOException
  {
	  driver = InitializeDriver();
	  log.info("SampleTest test execution Started");
	  log.info("Browser Initialized Suucessfully");
	  driver.get(prop.getProperty("URL"));
	  log.info("Naviagted to desired URL successfully");
	  driver.manage().window().maximize();
  }
  @Test
  public void SampleTest() throws IOException {
	  
	  LandingPageRSAcademy LandingPage = new LandingPageRSAcademy(driver);
	  Boolean actualValue = LandingPage.linkRegister().isDisplayed();
	  LandingPage.linkRegister().click();
	  log.info("Register Link has been clicked successfully");
	  System.out.println(actualValue);
	  log.info("status of Register link isDisplayed :"+actualValue);
	  Assert.assertFalse(actualValue); 
  }
  
  @AfterTest
  public void TearDown()
  {
	  driver.close();
	  log.info("Test Completed");
  }
}
