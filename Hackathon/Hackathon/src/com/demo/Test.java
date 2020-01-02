package com.demo;

import java.io.FileNotFoundException;
import java.util.List;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		PropertyUtil demo = new PropertyUtil();
		List<String> keys = demo.getValueByKey("userId");

		int count = 0;
		String val = "";

		while (count < keys.size()) {
			val = keys.get(count);
			try {
				System.out.println(val);
				if (!val.equals("zia")) throw new RuntimeException();
			} catch (Exception e) {
				count++;
			}
		}
	}

}
