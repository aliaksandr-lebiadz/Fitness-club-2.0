package com.epam.fitness.selenium;

import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.*;

public class SendFeedbackTest extends AbstractSeleniumTest {

	@Test
	public void sendFeedback() {
		// Test name: SendFeedback
		// Step # | name | target | value
		// 1 | open | /fitness/login |
		driver.get("http://localhost:8080/fitness/login");
		// 2 | setWindowSize | 1552x840 |
		driver.manage().window().setSize(new Dimension(1552, 840));
		// 3 | type | id=email | user@gmail.com
		driver.findElement(By.id("email")).sendKeys("user@gmail.com");
		// 4 | type | id=password | admin
		driver.findElement(By.id("password")).sendKeys("admin");
		// 5 | click | id=login-button |
		driver.findElement(By.id("login-button")).click();
		// 6 | click | css=.simple:nth-child(3) |
		driver.findElement(By.cssSelector(".simple:nth-child(3)")).click();
		// 7 | click | css=.odd:nth-child(1) > td:nth-child(4) |
		driver.findElement(By.cssSelector(".odd:nth-child(1) > td:nth-child(4)")).click();
		// 8 | click | id=feedback-button |
		driver.findElement(By.id("feedback-button")).click();
		// 9 | click | id=feedback-textarea |
		driver.findElement(By.id("feedback-textarea")).click();
		// 10 | type | id=feedback-textarea | some feedback...
		driver.findElement(By.id("feedback-textarea")).sendKeys("some feedback...");
		// 11 | click | id=send-feedback |
		driver.findElement(By.id("send-feedback")).click();
		// 12 | verifyElementPresent | css=table |
		{
			List<WebElement> elements = driver.findElements(By.cssSelector("table"));
			assert (elements.size() > 0);
		}
		// 13 | click | css=span |
		driver.findElement(By.cssSelector("span")).click();
	}
}
