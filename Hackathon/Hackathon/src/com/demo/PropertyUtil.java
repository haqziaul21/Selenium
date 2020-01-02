package com.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PropertyUtil {

	private String path = "./files";

	public PropertyUtil() {
	}

	public PropertyUtil(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getValueByKey(String key) throws FileNotFoundException {
		// key validation
		if (key == null) {
			throw new RuntimeException("key cannot be null.");
		}

		// directory validation
		File file = new File(path);
		if (!file.isDirectory()) {
			throw new RuntimeException("Directory not found.");
		}

		List<String> list = new ArrayList<String>();
		String[] arr = null;
		Scanner scnr = null;
		String seprtr = null;

		for (File files : file.listFiles()) {

			seprtr = ValueSeperator.getSeperator(files.getName().substring(files.getName().lastIndexOf(".") + 1));
			scnr = new Scanner(new FileInputStream(files));

			try {
				while (scnr.hasNextLine()) {
					arr = scnr.nextLine().split(seprtr);
					if (arr.length > 1 && key.trim().equals(arr[0].trim())) {
						if (!arr[1].trim().isEmpty())
							list.add(arr[1].trim());
						break;
					}
				}
			} finally {
				scnr.close();
			}
		}

		if (list.size() == 0) {
			throw new RuntimeException("No value/values present for the key " + key);
		}

		return list;
	}

}

enum ValueSeperator {
	TXT(","), CSV(","), PROPERTIES("= ");

	private String seperator;

	private ValueSeperator(String seperator) {
		this.seperator = seperator;
	}

	public static String getSeperator(String extension) {
		for (ValueSeperator val : values()) {
			if (extension.trim().toUpperCase().equals(val.toString())) {
				return val.seperator;
			}
		}
		return "";

	}

}
