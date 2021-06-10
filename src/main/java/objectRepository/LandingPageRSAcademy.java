package objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPageRSAcademy {
	
	//Initialized local driver to give life to driver object and access the methods and variable in this class
	public WebDriver driver;
	
	//Creating the constructor to give life to local driver object.
	public LandingPageRSAcademy(WebDriver driver)
	{
		this.driver = driver;
	}
	
	private By linkRegister = By.cssSelector("div[class='login-btn'] a[href*='sign_up']");
	
	private By login = By.xpath("//div[@class='login-btn']/a[@class='theme-btn register-btn']");
	
	
	public WebElement linkRegister()
	{
		return driver.findElement(linkRegister);
	}
	
	public WebElement login()
	{
		return driver.findElement(login);
	}

}
