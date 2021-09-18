package com.reddit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//bad configuration for production
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry corsRegistry) {
		// TODO Auto-generated method stub
		   corsRegistry.addMapping("/**")
           .allowedOrigins("http://localhost:4200")
           .allowedMethods("*")
           .maxAge(3600L)
           .allowedHeaders("*")
           .exposedHeaders("Authorization")
           .allowCredentials(true);
	}

	//swagger-ui.html will not be accessible via web browser to make it accessible we will use  this me thod
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
}