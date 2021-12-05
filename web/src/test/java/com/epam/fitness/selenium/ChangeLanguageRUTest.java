package com.epam.fitness.selenium;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ChangeLanguageRUTest extends AbstractSeleniumTest {

	@Test
	public void changeLanguageRU() {
		// Test name: ChangeLanguageRU
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
		// 4 | click | linkText=RU |
		driver.findElement(By.linkText("RU")).click();
		// 5 | verifyText | css=.hint | Еще нет аккаунта? Зарегистрироваться
		assertThat(driver.findElement(By.cssSelector(".hint")).getText(), is("Еще нет аккаунта? Зарегистрироваться"));
		// 6 | verifyText | css=label:nth-child(1) | Электронная почта
		assertThat(driver.findElement(By.cssSelector("label:nth-child(1)")).getText(), is("Электронная почта"));
		// 7 | verifyText | css=label:nth-child(3) | Пароль
		assertThat(driver.findElement(By.cssSelector("label:nth-child(3)")).getText(), is("Пароль"));
	}
}
