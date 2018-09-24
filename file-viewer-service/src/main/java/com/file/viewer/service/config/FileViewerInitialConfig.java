package com.file.viewer.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.file.viewer.service.entity.ServiceProperties;

@Configuration
@PropertySource(value = "${spring.config.location}")
public class FileViewerInitialConfig {

	@Autowired
	private Environment environment;

	@Bean
	public ServiceProperties loadServiceConfiguration() {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.addToProperties("intermediate.folder",
				environment.getRequiredProperty("intermediate.folder"));
		return serviceProperties;
	}
}