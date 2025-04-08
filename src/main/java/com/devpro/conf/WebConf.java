package com.devpro.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class WebConf implements WebMvcConfigurer {
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		return bean;
	}
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/META-INF/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/META-INF/js/");
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/META-INF/images/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/META-INF/fonts/");
		registry.addResourceHandler("/vendor/**").addResourceLocations("classpath:/META-INF/vendor/");
		registry.addResourceHandler("/summernote/**").addResourceLocations("classpath:/META-INF/summernote/");
		
		// Sửa đường dẫn để trỏ tới thư mục upload thực tế của bạn
		registry.addResourceHandler("/file/upload/**")
				.addResourceLocations("file:///D:/ki2nam4/dambaoclpm/dbclpm/upload/");
	}
}