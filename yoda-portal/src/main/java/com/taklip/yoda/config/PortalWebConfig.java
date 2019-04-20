package com.taklip.yoda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

@EnableWebMvc
@ComponentScan(basePackages = "com.taklip.yoda.controller", includeFilters = @Filter(Controller.class))
@Configuration
public class PortalWebConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.tiles();
		registry.jsp("/WEB-INF/html/", ".jsp");
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions("/WEB-INF/html/tiles.xml");
		return configurer;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/uploads/**").addResourceLocations("/uploads/")
//			.setCachePeriod(2592000);
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
//		registry.addResourceHandler("/FCKeditor/**").addResourceLocations("/FCKeditor/")
//			.setCachePeriod(2592000);
//		registry.addResourceHandler("/template/basic/**").addResourceLocations("/html/portal/resources/")
//			.setCachePeriod(2592000);
	}
}