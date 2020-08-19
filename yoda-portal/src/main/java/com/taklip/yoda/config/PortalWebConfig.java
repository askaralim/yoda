package com.taklip.yoda.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author askar
 */
//@EnableWebMvc
//@ComponentScan(basePackages = "com.taklip.yoda.controller", includeFilters = @Filter(Controller.class))
@Configuration
public class PortalWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

//	@Override
//	public void configureViewResolvers(ViewResolverRegistry registry) {
//		registry.tiles();
//		registry.jsp("/WEB-INF/html/", ".jsp");
//	}

//	@Bean
//	public TilesConfigurer tilesConfigurer() {
//		TilesConfigurer configurer = new TilesConfigurer();
//		configurer.setDefinitions("/WEB-INF/html/tiles.xml");
//		return configurer;
//	}
//
//	@Bean
//	public TilesViewResolver tilesViewResolver() {
//		final TilesViewResolver resolver = new TilesViewResolver();
//		resolver.setViewClass(TilesView.class);
//		return resolver;
//	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:///" + System.getProperties().getProperty("user.home") + "/yoda/uploads/");
    }
}