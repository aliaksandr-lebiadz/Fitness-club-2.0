package com.epam.fitness.selenium;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;

public class ChangeDiscountTest extends AbstractSeleniumTest {

	@Test
	public void changeDiscount() {
		// Test name: ChangeDiscount
		// Step # | name | target | value
		// 1 | open | /fitness/login |
		driver.get("http://localhost:8080/fitness/login");
		// 2 | setWindowSize | 1552x840 |
		driver.manage().window().setSize(new Dimension(1552, 840));
		// 3 | type | id=email | admin@mail.ru
		driver.findElement(By.id("email")).sendKeys("admin@mail.ru");
		// 4 | type | id=password | admin
		driver.findElement(By.id("password")).sendKeys("admin");
		// 5 | sendKeys | id=password | ${KEY_ENTER}
		driver.findElement(By.id("password")).sendKeys(Keys.ENTER);
		// 6 | click | css=.simple:nth-child(2) |
		driver.findElement(By.cssSelector(".simple:nth-child(2)")).click();
		// 7 | click | css=.odd:nth-child(1) > td:nth-child(3) |
		driver.findElement(By.cssSelector(".odd:nth-child(1) > td:nth-child(3)")).click();
		// 8 | click | id=discount-button |
		driver.findElement(By.id("discount-button")).click();
		// 9 | click | id=discount |
		driver.findElement(By.id("discount")).click();
		// 10 | type | id=discount | 100
		driver.findElement(By.id("discount")).sendKeys("100");
		// 11 | click | id=set-discount |
		driver.findElement(By.id("set-discount")).click();
		// 12 | verifyText | css=.odd:nth-child(1) > td:nth-child(4) | 100
		assertThat(driver.findElement(By.cssSelector(".odd:nth-child(1) > td:nth-child(4)")).getText(), is("100"));
		// 13 | click | css=.navigation_link > span |
		driver.findElement(By.cssSelector(".navigation_link > span")).click();
	}
}
