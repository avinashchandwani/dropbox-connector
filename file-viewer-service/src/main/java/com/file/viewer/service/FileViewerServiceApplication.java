package com.file.viewer.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot Starter Class for REST Service Application
 * 
 * @author Avinash Chandwani
 *
 */
@ComponentScan(basePackageClasses = { com.file.viewer.service.controller.FileViewerServiceController.class,
		com.file.viewer.service.service.FileViewerService.class,
		com.file.viewer.service.config.FileViewerInitialConfig.class })
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class FileViewerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileViewerServiceApplication.class, args);
	}
}