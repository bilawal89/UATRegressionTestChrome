package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyProfilePage {
	
	public WebDriver driver;

	public MyProfilePage(WebDriver driver ) 
	{
		// TODO Auto-generated constructor stub
		this.driver=driver;
	}
	By HomePageTextbox=By.id("homePageUrl");
	By EditProfBtn=By.linkText("Edit Profile");
	By ShippingAddressLink=By.linkText("Shipping Addresses");
	By MyProfileLinkLink=By.linkText("My Profile");
	By AccountDropdown=By.xpath("//i[@class='fa fa-caret-down'][1]");
	
	By ShippingAddressLinkFF=By.xpath("(//a[text()='Shipping Addresses'])[3]");
	By SaveBtn=By.xpath("//input[@class='btn btn-primary']");
	By info=By.xpath("(//div[@class='info-section'])[1]");
	
	
	public WebElement getinfo()
	{
		return driver.findElement(info);
	}
	
	public WebElement getSaveBtn()
	{
		return driver.findElement(SaveBtn);
	}
	
	
	public WebElement getHomePageTextbox()
	{
		return driver.findElement(HomePageTextbox);
	}
	
	
	public WebElement getShippingAddressLinkFF()
	{
		return driver.findElement(ShippingAddressLinkFF);
	}
	
	
	public WebElement getAccountDropdown()
	{
		return driver.findElement(AccountDropdown);
	}

public WebElement getEditProfBtn()
{
	return driver.findElement(EditProfBtn);
}

public WebElement getShippingAddressLink()
{
	return driver.findElement(ShippingAddressLink);
}

public WebElement getMyProfileLinkLink()
{
	return driver.findElement(MyProfileLinkLink);
}

}