package com.cosmetology.configuration;

import com.cosmetology.filter.AuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	AuthenticationTokenFilter authenticationTokenFilter;
	// 定义白名单URL路径数组
	private static final String URL_WHITELIST[] = {
			"/beautyItems/**",
			"/users/*",
			"/image/**",
			"/test/**"
	} ;
	/**
	 * 配置应用程序的安全规则
	 * 开启跨域资源共享（CORS）功能，并关闭跨站请求伪造（CSRF）攻击防护
	 * 配置登录相关设置
	 * 禁用会话（session）的创建
	 * 配置拦截规则
	 * @param http HttpSecurity对象，用于配置应用程序的安全规则
	 * @throws Exception 配置过程中可能抛出的异常
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors() // 开启CORS功能（跨域）
				.and()
				.csrf().disable() // 关闭CSRF攻击防护
				.formLogin()
				// 配置登录相关设置
				.loginPage("/beautyItems/selectAll")
				// .successHandler() // 自定义登录成功处理器（如果需要）
				// .failureHandler() // 自定义登录失败处理器（如果需要）
				// .and()
				// .logout() // 配置注销设置（如果需要）
				// .logoutSuccessHandler() // 自定义注销成功处理器（如果需要）
				.and()
				//session禁用配置
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 禁用会话的创建（无状态）
				.and()
				.authorizeRequests()
				.antMatchers(URL_WHITELIST).permitAll() // 白名单中的URL路径允许无需身份验证/permitAll放行所有
				.anyRequest().authenticated(); // 其他所有请求需要身份验证
		http.addFilterBefore(authenticationTokenFilter, BasicAuthenticationFilter.class);

//		http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
	}



}
