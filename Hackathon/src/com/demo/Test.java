package com.demo;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {

	public static void main(String[] args) throws Exception {

		List<String> list = Arrays.asList("userId","password","login");
		
		System.setProperty("webdriver.chrome.driver", "D:\\Ziaul\\Downloads\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://ferp-qa1-db2.qa.teams360.net/common");

		Hackathon hackathon=new Hackathon();
		
		for(String key:list ){
			hackathon.executeWebElement(driver, key);
		}
	}

}
