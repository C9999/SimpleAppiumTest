package br.com.cssp;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class FistAppiumTest {
	//Criando Appium Driver
	AppiumDriver driver;
	
	@BeforeClass
	public void SetUp() throws MalformedURLException{
		
		File appDir = new File("/Users/carlosaraujo/Documents/workspace/SimpleAppiumTest");
        File app = new File(appDir, "app-debug.apk");
        
        DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Android");
		cap.setCapability(MobileCapabilityType.VERSION, "5.1");
		
		cap.setCapability("plataformName", "Android");
		cap.setCapability("appPackage", "com.example.heitorcolangelo.buscacep");
		cap.setCapability("appActivity", "com.example.heitorcolangelo.buscacep.MainActivity");
		
		cap.setCapability("app", app.getAbsolutePath());
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		
	}

	@Test
	public void SimpleTest() {
		Assert.assertNotNull(driver.getContext());
		
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).sendKeys("04578907");
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/search")).click();
		
	}
}
