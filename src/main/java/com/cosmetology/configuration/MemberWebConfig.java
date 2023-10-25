package com.cosmetology.configuration;

import com.cosmetology.interceptor.LoginUserInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MemberWebConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginUserInterceptor()).addPathPatterns("/**");


	}
}
