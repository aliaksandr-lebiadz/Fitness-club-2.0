package com.epam.fitness.selenium;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ChangeLanguageENTest extends AbstractSeleniumTest {

	@Test
	public void changeLanguageEN() {
		// Test name: ChangeLanguageEN
		// Step # | name | target | value
		// 1 | open | /fitness/login |
		driver.get("http://localhost:8080/fitness/login");
		// 2 | setWindowSize | 1552x840 |
		driver.manage().window().setSize(new Dimension(1552, 840));
		// 3 | mouseOver | id=switcher |
		{
			WebElement element = driver.findElement(By.id("switcher"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}
		// 4 | click | linkText=EN |
		driver.findElement(By.linkText("EN")).click();
		// 5 | verifyText | css=.hint | Don't have an account yet? Sign up
		assertThat(driver.findElement(By.cssSelector(".hint")).getText(), is("Don't have an account yet? Sign up"));
		// 6 | verifyText | css=label:nth-child(1) | E-mail
		assertThat(driver.findElement(By.cssSelector("label:nth-child(1)")).getText(), is("E-mail"));
		// 7 | verifyText | css=label:nth-child(3) | Password
		assertThat(driver.findElement(By.cssSelector("label:nth-child(3)")).getText(), is("Password"));
	}
}
