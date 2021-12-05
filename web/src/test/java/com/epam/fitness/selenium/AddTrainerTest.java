package com.epam.fitness.selenium;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

public class AddTrainerTest extends AbstractSeleniumTest {

	@Test
	public void addTrainer() {
		// Test name: AddTrainer
		// Step # | name | target | value
		// 1 | open | /fitness/login |
		driver.get("http://localhost:8080/fitness/login");
		// 2 | setWindowSize | 1552x840 |
		driver.manage().window().setSize(new Dimension(1552, 840));
		// 3 | type | id=email | admin@mail.ru
		driver.findElement(By.id("email")).sendKeys("admin@mail.ru");
		// 4 | type | id=password | admin
		driver.findElement(By.id("password")).sendKeys("admin");
		// 5 | click | id=login-button |
		driver.findElement(By.id("login-button")).click();
		// 6 | click | css=.simple:nth-child(2) |
		driver.findElement(By.cssSelector(".simple:nth-child(2)")).click();
		// 7 | click | id=add-trainer-button |
		driver.findElement(By.id("add-trainer-button")).click();
		// 8 | click | id=email |
		driver.findElement(By.id("email")).click();
		// 9 | executeScript | return Math.random().toString(36).substring(0,20) + "@gmail.com" | trainer_email
		vars.put("trainer_email", js.executeScript("return Math.random().toString(36).substring(0,20) + \"@gmail.com\""));
		// 10 | type | id=email | ${trainer_email}
		driver.findElement(By.id("email")).sendKeys(vars.get("trainer_email").toString());
		// 11 | type | id=password | trainer12345
		driver.findElement(By.id("password")).sendKeys("trainer12345");
		// 12 | type | id=first-name | Aliaksandr
		driver.findElement(By.id("first-name")).sendKeys("Aliaksandr");
		// 13 | type | id=second-name | Lebiadz
		driver.findElement(By.id("second-name")).sendKeys("Lebiadz");
		// 14 | click | id=create-trainer |
		driver.findElement(By.id("create-trainer")).click();
		// 15 | click | linkText=Last |
		driver.findElement(By.linkText("Last")).click();
		// 16 | verifyText | css=tr:last-child > td:nth-child(2) | Aliaksandr Lebiadz
		assertThat(driver.findElement(By.cssSelector("tr:last-child > td:nth-child(2)")).getText(), is("Aliaksandr Lebiadz"));
		// 17 | verifyText | css=tr:last-child > td:nth-child(3) | ${trainer_email}
		assertThat(driver.findElement(By.cssSelector("tr:last-child > td:nth-child(3)")).getText(), is(vars.get("trainer_email").toString()));
		// 18 | verifyText | css=tr:last-child > td:nth-child(4) | 0
		assertThat(driver.findElement(By.cssSelector("tr:last-child > td:nth-child(4)")).getText(), is("0"));
		// 19 | click | css=.navigation_link > span |
		driver.findElement(By.cssSelector(".navigation_link > span")).click();
	}
}
