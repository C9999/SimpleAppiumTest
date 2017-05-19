package br.com.cssp;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class FistAppiumTest {

	AppiumDriver driver;

	@BeforeClass
	public void SetUp() throws MalformedURLException{

		File appDir = new File("/Users/carlosaraujo/Documents/workspace/SimpleAppiumTest");
		File app = new File(appDir, "app-debug.apk");

		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		cap.setCapability(MobileCapabilityType.BROWSER_NAME, "Android");
		cap.setCapability(MobileCapabilityType.VERSION, "7.1.1");

		cap.setCapability("plataformName", "Android");
		cap.setCapability("appPackage", "com.example.heitorcolangelo.buscacep");
		cap.setCapability("appActivity", "com.example.heitorcolangelo.buscacep.MainActivity");

		cap.setCapability("app", app.getAbsolutePath());
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);

	}

	@Test 
	public void ConsultaUmCEP() {
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).sendKeys("04578907");
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/search")).click();
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).clear();
	}

	@Test
	public void ConsultaVariosCEPsApagandoAConsultaAnterior(){
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).sendKeys("06773090");
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/search")).click();
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).clear();

		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).sendKeys("04505000");
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/search")).click();
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).clear();

		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).sendKeys("05690000");
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/search")).click();
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).clear();

		driver.navigate().back();
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/history")).click();	
		driver.navigate().back();
	}

	@Test
	public void ConsultaUmCEPValidandoRetorno(){
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).clear();
		String cepConsultado = "05777001";

		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).sendKeys(cepConsultado);
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/search")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		String retornoDaConsulta; 
		retornoDaConsulta = driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/address")).getText();
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).clear();

		String cepRetornado = retornoDaConsulta.substring(0, 8);
		assertEquals(cepConsultado, cepRetornado);
	}

	@Test
	public void AcionaConsultaAnterioresEClicaEmVoltar(){
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/history")).click();
		driver.navigate().back();

	}

	@Test
	public void DigitaCEPConsultaAnterioresVoltarBuscaPorCEP(){
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).sendKeys("05607200");
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/history")).click();

		driver.navigate().back();
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/search")).click();
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).clear();
	}

	@Ignore
	public void DigitaCEPincompleto(){
		String cepConsultado = "1234567";
		String retornoDaConsulta;
		
		driver.resetApp();
		
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).sendKeys(cepConsultado);
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/search")).click();
 
		retornoDaConsulta = driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/address")).getText();
		System.out.println(retornoDaConsulta);
		
		driver.findElement(By.id("com.example.heitorcolangelo.buscacep:id/cep")).clear();
		
		assertNotEquals(cepConsultado, retornoDaConsulta);
	}
}
