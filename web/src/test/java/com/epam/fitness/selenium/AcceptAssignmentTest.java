package com.epam.fitness.selenium;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

public class AcceptAssignmentTest extends AbstractSeleniumTest {

	@Test
	public void acceptAssignment() {
		// Test name: AcceptAssignment
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
		// 7 | click | linkText=3 |
		driver.findElement(By.linkText("3")).click();
		// 8 | click | css=.even:nth-child(2) > td:nth-child(3) |
		driver.findElement(By.cssSelector("tr:nth-child(2)")).click();
		// 9 | click | id=assignment-button |
		driver.findElement(By.id("assignment-button")).click();
		// 10 | click | css=.odd > td:nth-child(4) |
		driver.findElement(By.cssSelector("tr:nth-child(2)")).click();
		// 11 | click | css=.custom-button:nth-child(7) |
		driver.findElement(By.cssSelector(".custom-button:nth-child(7)")).click();
		// 12 | verifyText | css=.odd > td:nth-child(6) | Accepted
		assertThat(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(6)")).getText(), is("Accepted"));
		// 13 | click | css=.navigation_link > span |
		driver.findElement(By.cssSelector(".navigation_link > span")).click();
	}
}
