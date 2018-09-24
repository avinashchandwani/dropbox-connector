package com.file.viewer.service.entity;

import java.util.HashMap;
import java.util.Map;

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
