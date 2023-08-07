package com.pageobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.utils.BrowserUtils;

public class ReUsable extends BrowserUtils {

	public static void myWait(int seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void reClick(Properties prop, String xpath) {
		try {
			driver.findElement(By.xpath(prop.getProperty(xpath))).click();
		} catch (StaleElementReferenceException e) {
			WebElement click = driver.findElement(By.xpath(prop.getProperty(xpath)));
			click.click();

		}
	}

	public static void xpSearchProduct(Properties prop, String xpath, String value) {
		driver.findElement(By.xpath(prop.getProperty(xpath))).sendKeys(value, Keys.ENTER);
	}

	public static void xpSend(Properties prop, String xpath, String value) {
		driver.findElement(By.xpath(prop.getProperty(xpath))).sendKeys(value);
	}

	public static List<WebElement> xpSelectiveProduct(Properties prop, String xpath) {
		return driver.findElements(By.xpath(prop.getProperty(xpath)));
	}

	public static void switchWindow(int tabNo) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabNo));
	}

	public static void idClick(Properties prop, String id) {
		driver.findElement(By.id(prop.getProperty(id))).click();
	}

	public static void xpClick(Properties prop, String xpath) {
		driver.findElement(By.xpath(prop.getProperty(xpath))).click();
	}

	public static void xpClear(Properties prop, String xpath) {
		driver.findElement(By.xpath(prop.getProperty(xpath))).clear();
	}

	public static String xpGetText(Properties prop, String xpath) {
		return driver.findElement(By.xpath(prop.getProperty(xpath))).getText();
	}

	public static void iframe(Properties prop, String loc) {
		WebElement frameElement = driver.findElement(By.xpath(prop.getProperty(loc)));
		driver.switchTo().frame(frameElement);
	}

	public static boolean xpDisplay(Properties prop, String loc) {
		return driver.findElement(By.xpath(prop.getProperty(loc))).isDisplayed();
	}

	public static void userName(Properties prop, String value) {

		for (Object key : prop.keySet()) {
			System.out.println(key + ": " + prop.getProperty(key.toString()));
		}
	}
}
