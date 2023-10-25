package com.cosmetology.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cosmetology.entity.Users;
import com.cosmetology.service.UsersService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cosmetology.constant.AuthServerConstant.LOGIN_USER;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {
	@Resource
	StringRedisTemplate stringRedisTemplate;

	@Resource
	UsersService usersService;
	public static ThreadLocal<Users> usersThreadLocal = new ThreadLocal<>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		System.out.println("Request intercepted by AuthenticationTokenFilter. URL: " + request.getRequestURI());
		System.out.println("Request headers: " + request.getHeader("Authorization"));

		String accessToken = request.getHeader("Authorization");
		// 从 Redis 中获取保存的 token
		String userId = stringRedisTemplate.opsForValue().get(LOGIN_USER+accessToken);
		Users user = usersService.getOne(new QueryWrapper<Users>().eq("user_id", userId));
		if (user != null) {
			usersThreadLocal.set(user);
			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPasswordHash(),null);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			//设置为已登录
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}
		filterChain.doFilter(request, response);
	}
}
