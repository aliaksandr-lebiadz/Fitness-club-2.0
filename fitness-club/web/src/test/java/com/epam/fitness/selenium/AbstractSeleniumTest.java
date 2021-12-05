package com.epam.fitness.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSeleniumTest {

	protected WebDriver driver;
	protected JavascriptExecutor js;
	protected Map<String, Object> vars = new HashMap<>();

	@Before
	public void setUp() throws IOException {
		ChromeDriverService service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File("C:\\Users\\Lenovo\\Desktop\\chromedriver.exe"))
				.usingPort(9515)
				.build();
		service.start();
		driver = new ChromeDriver(service);
		js = (JavascriptExecutor) driver;
	}

	@After
	public void tearDown() {

		driver.quit();
	}

}
