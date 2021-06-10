package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BaseMain {
	
	public Properties prop;
	public String BrowserName;
	public WebDriver driver;
	public static Logger log = LogManager.getLogger(BaseMain.class.getClass());
	
	@SuppressWarnings("deprecation")
	public WebDriver InitializeDriver() throws IOException
	{
		//Below line of code is to get the values which is declared in globalData.Properties file.
		prop = new Properties();
		String propFilePath = System.getProperty("user.dir")+"\\src\\main\\java\\resources\\globalData.Properties";
		FileInputStream fis = new FileInputStream(propFilePath);
		prop.load(fis);
		
		//below line will fetch the parameter value from Properties file
		BrowserName = prop.getProperty("Browser");
		
		//below line will fetch the parameter value from Maven command
		//BrowserName = System.getProperty("Browser");
		
		if (BrowserName.contains("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Library\\Chrome V91\\chromedriver.exe");
			ChromeOptions option = new ChromeOptions();
			if (BrowserName.contains("headless"))
			{
				option.addArguments("headless");
			}
			driver = new ChromeDriver(option);		
		}
		else if (BrowserName.equalsIgnoreCase("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\Library\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if (BrowserName.equalsIgnoreCase("IE"))
		{
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\Library\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		else if (BrowserName.equalsIgnoreCase("Edge"))
		{
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+"\\Library\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return driver;
	}

	public String getScreenshotPath(String TestCaseName, WebDriver driver) throws IOException
	{
		log.info(TestCaseName + "is failed and script entered into screenshot method");
		TakesScreenshot ts = (TakesScreenshot) driver;
		File SourceFile = ts.getScreenshotAs(OutputType.FILE);
		String  DestFile = System.getProperty("user.dir")+"\\reports\\"+TestCaseName+".png";
		FileUtils.copyFile(SourceFile, new File (DestFile));
		return DestFile;
	}
}
