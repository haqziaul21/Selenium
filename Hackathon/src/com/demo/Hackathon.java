package com.demo;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Hackathon {
	private String path = "./files";

	public Hackathon() {
	}

	public Hackathon(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void executeWebElement(WebDriver driver, String key) {
		// key validation
		if (key == null) {
			throw new RuntimeException("key cannot be null.");
		}

		// directory validation
		File files = new File(path);
		if (!files.isDirectory()) {
			throw new RuntimeException("Directory not found.");
		}

		List<String> list = null;
		Scanner scnr = null;
		boolean flag = true;

		Class<By> cls = By.class;
		Method[] methods = cls.getDeclaredMethods();

		for (File file : files.listFiles()) {

			try {
				String propName = file.getName().trim().split("-")[0].toLowerCase();
				scnr = new Scanner(new FileInputStream(file));

				while (scnr.hasNextLine()) {
					list = getFileValue(scnr.nextLine().split(","), file);

					if (key.trim().equals(list.get(0))) {

							flag = false;
							for (Method method : methods) {
								if (method.getName().toLowerCase().startsWith(propName)) {
									if (handleEvent(driver.findElement((By) method.invoke(cls, list.get(1))),list.get(2)))
										System.out.println("Key "+ key+" found in the file "+file.getName());
										return;
								}
							}
						}
					}
				
				if (flag) {
					throw new RuntimeException("Invalid key/No value present for the key " + key);
				}
			} catch (Exception ex) {
				if (ex instanceof NoSuchElementException || ex instanceof ElementNotVisibleException || ex instanceof TimeoutException) {
					System.err.println("Key "+ key+" not found in the file " + file.getName());
					continue;
				}
				ex.printStackTrace();
				break;
			} finally {
				scnr.close();
			}
		}

	}

	private List<String> getFileValue(String[] str, File file) {

		if (str.length < 3) {
			throw new RuntimeException("Invalid key and Values..Please provide 3 comma(,) seperated arguments in file " + file.getName());
		}

		str[0] = str[0].trim();
		str[1] = str[1].trim();
		str[2] = str[2].trim();

		for (int i = 0; i < str.length; i++) {
			if (str[i].isEmpty()) {
				throw new RuntimeException("Argument " + (i + 1) + " cannot be blank in file " + file.getName());
			}
		}

		return Arrays.asList(str[0], str[1], str[2]);
	}

	private boolean handleEvent(WebElement webElement, String val) {
		boolean flag = false;

		String type = webElement.getAttribute("type");

		if (type.equals("text") || type.equals("password")) {
			webElement.clear();
			webElement.sendKeys(val);
			flag = true;
		} else if (type.equals("button")) {
			webElement.click();
			flag = true;
		}

		return flag;

	}

}
