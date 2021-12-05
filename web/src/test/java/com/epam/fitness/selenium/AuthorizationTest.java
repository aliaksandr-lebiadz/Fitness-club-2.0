package com.epam.fitness.selenium;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

public class AuthorizationTest extends AbstractSeleniumTest {

	@Test
	public void authorization() {
		// Test name: Authorization
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
		// 6 | verifyText | css=.simple:nth-child(2) | GET MEMBERSHIP
		assertThat(driver.findElement(By.cssSelector(".simple:nth-child(2)")).getText(), is("GET MEMBERSHIP"));
		// 7 | click | css=span |
		driver.findElement(By.cssSelector("span")).click();
	}
}
