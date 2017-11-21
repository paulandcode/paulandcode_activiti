package com.paulandcode.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * properties载入工具类
 * 
 * @author 黄建峰
 * @date 2017年9月19日 下午3:27:12
 */
public class PropertiesLoader {

	private final Properties properties;

	public PropertiesLoader(String... resourcePaths) {
		properties = loadProperties(resourcePaths);
	}

	/**
	 * 载入多个properties文件
	 * 
	 * @param resourcePaths
	 * @return
	 */
	private Properties loadProperties(String[] resourcePaths) {
		Properties props = new Properties();
		for (String location : resourcePaths) {
			InputStreamReader is = null;
			try {
				is = new InputStreamReader(PropertiesLoader.class.getClassLoader().getResourceAsStream(location),
						"UTF-8");
				props.load(is);
			} catch (Exception e) {

			} finally {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return props;
	}

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public String getConfig(String key) {
		if (properties.containsKey(key)) {
			return properties.getProperty(key);
		}
		return "";
	}

}