package com.file.viewer.ui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.file.viewer.ui.entity.DropBoxConnection;
import com.file.viewer.ui.entity.ServiceProperties;

@EnableWebMvc
@ComponentScan
@Configuration
@PropertySource(value = "${spring.config.location}")
public class FileViewerWebAppConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		registry.viewResolver(resolver);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Autowired
	private Environment environment;

	@Bean
	public ServiceProperties loadServiceConfiguration() {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.addToProperties("intermediate.folder",
				environment.getRequiredProperty("intermediate.folder"));
		serviceProperties.addToProperties("service.protocol", environment.getRequiredProperty("service.protocol"));
		serviceProperties.addToProperties("service.host", environment.getRequiredProperty("service.host"));
		serviceProperties.addToProperties("service.port", environment.getRequiredProperty("service.port"));
		serviceProperties.addToProperties("service.root", environment.getRequiredProperty("service.root"));
		return serviceProperties;
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver(){
	    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
	    commonsMultipartResolver.setDefaultEncoding("utf-8");
	    commonsMultipartResolver.setMaxUploadSize(50000000);
	    return commonsMultipartResolver;
	}
	@Bean
	@Scope(
			  value = WebApplicationContext.SCOPE_SESSION, 
			  proxyMode = ScopedProxyMode.TARGET_CLASS)
	public DropBoxConnection getDropBoxConnection(){
		return new DropBoxConnection();
	}

}
