package com.file.viewer.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = { com.file.viewer.ui.config.FileViewerWebAppConfig.class }, basePackages = {
		"com.file.viewer.ui.service",
		"com.file.viewer.ui.config", "com.file.viewer.ui.controller" })
public class FileViewerWebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FileViewerWebApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(FileViewerWebApplication.class, args);
	}
}