package edu.kh.gowith.common.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.kh.gowith.common.filter.LoginFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter(){
		
		FilterRegistrationBean<LoginFilter> filter = new FilterRegistrationBean<>();
		
		filter.setFilter(new LoginFilter());
		
		String[] filterUrl = {"/myPage/*", "/editBoard/*"};
		
		filter.setUrlPatterns(Arrays.asList(filterUrl));
		
		filter.setName("loginFilter");
		
		filter.setOrder(1);
		
		return filter;
				
		
	}
	
	
	
}
