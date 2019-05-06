package com.lnragi.tools.bambkins;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesClient {

	public String getPropertyByKey(String key) {
		Properties prop = readPopFile();
		return prop.getProperty(key);
	}

	private Properties readPopFile() {
		Properties prop = null;
		try (InputStream inputStream = new FileInputStream("config.properties")) {
			prop = new Properties();
			prop.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
}
