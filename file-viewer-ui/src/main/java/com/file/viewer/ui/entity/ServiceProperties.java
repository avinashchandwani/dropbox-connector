package com.file.viewer.ui.entity;

import java.util.HashMap;
import java.util.Map;
/**
 * * A POJO to hold the configuration of the application redily used using
 * Autowired
 * @author Avinash Chandwani
 *
 */
public class ServiceProperties {

	private Map<String, String> properties;

	public ServiceProperties() {
		properties = new HashMap<>();
	}

	public void addToProperties(String key, String value) {
		properties.put(key, value);
	}

	public String getPropertyValue(String key) {
		return properties.get(key);
	}
}