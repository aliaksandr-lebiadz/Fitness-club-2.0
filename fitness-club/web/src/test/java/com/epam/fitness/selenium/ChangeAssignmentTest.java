package com.epam.fitness.selenium;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

public class ChangeAssignmentTest extends AbstractSeleniumTest {

	@Test
	public void changeAssignment() {
		// Test name: ChangeAssignment
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
		// 8 | click | css=tr:nth-child(2) |
		driver.findElement(By.cssSelector("tr:nth-child(2)")).click();
		// 10 | click | id=assignment-button |
		driver.findElement(By.id("assignment-button")).click();
		// 11 | click | css=.odd > td:nth-child(4) |
		driver.findElement(By.cssSelector(".odd > td:nth-child(4)")).click();
		// 12 | click | css=.custom-button:nth-child(8) |
		driver.findElement(By.cssSelector(".custom-button:nth-child(8)")).click();
		// 13 | click | name=date |
		driver.findElement(By.name("date")).click();
		// 14 | type | name=date | 2021-06-04
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("2021-06-04");
		// 15 | click | id=exercise-select-id |
		driver.findElement(By.id("exercise-select-id")).click();
		// 16 | select | id=exercise-select-id | label=pull-ups
		{
			WebElement dropdown = driver.findElement(By.id("exercise-select-id"));
			dropdown.findElement(By.xpath("//option[. = 'pull-ups']")).click();
		}
		// 17 | click | id=exercise-select-id |
		driver.findElement(By.id("exercise-select-id")).click();
		// 18 | click | id=change-assignment-popup-form |
		driver.findElement(By.id("change-assignment-popup-form")).click();
		// 19 | type | id=amount-of-sets | 8
        driver.findElement(By.id("amount-of-sets")).clear();
		driver.findElement(By.id("amount-of-sets")).sendKeys("8");
		// 20 | click | id=change-assignment-popup-form |
		driver.findElement(By.id("change-assignment-popup-form")).click();
		// 21 | type | id=amount-of-reps | 15
        driver.findElement(By.id("amount-of-reps")).clear();
		driver.findElement(By.id("amount-of-reps")).sendKeys("15");
		// 22 | click | id=add-assignment-button |
		driver.findElement(By.id("add-assignment-button")).click();
		// 23 | storeText | css=.even > td:nth-child(2) | Jun 4, 2021
		vars.put("Jun 4, 2021", driver.findElement(By.cssSelector("tr:last-child > td:nth-child(2)")).getText());
		// 24 | verifyText | css=.even > td:nth-child(3) | pull-ups
		assertThat(driver.findElement(By.cssSelector("tr:last-child > td:nth-child(3)")).getText(), is("pull-ups"));
		// 25 | verifyText | css=.even > td:nth-child(4) | 8
		assertThat(driver.findElement(By.cssSelector("tr:last-child > td:nth-child(4)")).getText(), is("8"));
		// 26 | verifyText | css=.even > td:nth-child(5) | 15
		assertThat(driver.findElement(By.cssSelector("tr:last-child > td:nth-child(5)")).getText(), is("15"));
		// 27 | verifyText | css=.even > td:nth-child(6) | Changed
		assertThat(driver.findElement(By.cssSelector("tr:last-child > td:nth-child(6)")).getText(), is("Changed"));
		// 28 | click | css=.fa-sign-out |
		driver.findElement(By.cssSelector(".fa-sign-out")).click();
	}
}
