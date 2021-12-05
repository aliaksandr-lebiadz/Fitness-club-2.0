package com.epam.fitness.selenium;

import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.*;

public class AuthorizationWithInvalidPasswordTest extends AbstractSeleniumTest {

	@Test
	public void authorizationWithInvalidPassword() {
		// Test name: AuthorizationWithInvalidPassword
		// Step # | name | target | value
		// 1 | open | /fitness/login |
		driver.get("http://localhost:8080/fitness/login");
		// 2 | setWindowSize | 1552x840 |
		driver.manage().window().setSize(new Dimension(1552, 840));
		// 3 | click | id=email |
		driver.findElement(By.id("email")).click();
		// 4 | type | id=email | user@gmail.com
		driver.findElement(By.id("email")).sendKeys("user@gmail.com");
		// 5 | type | id=password | admin1
		driver.findElement(By.id("password")).sendKeys("admin1");
		// 6 | click | id=login-button |
		driver.findElement(By.id("login-button")).click();
		// 7 | verifyElementPresent | id=login-fail |
		{
			List<WebElement> elements = driver.findElements(By.id("login-fail"));
			assert (elements.size() > 0);
		}
	}
}
