package com.xyw.product.auth.config;

import com.xyw.product.auth.support.core.FormIdentityLoginConfigurer;
import com.xyw.product.auth.support.core.ProductDaoAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = true)
public class WebSecurityConfiguration {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorizeRequests -> authorizeRequests.antMatchers("/token/*").permitAll()// 开放自定义的部分端点
				.anyRequest().authenticated()).headers().frameOptions().sameOrigin()// 避免iframe同源无法登录
				.and().apply(new FormIdentityLoginConfigurer()); // 表单登录个性化
		// 处理 UsernamePasswordAuthenticationToken
		http.authenticationProvider(new ProductDaoAuthenticationProvider());
		return http.build();
	}

	@Bean
	@Order(0)
	SecurityFilterChain resources(HttpSecurity http) throws Exception {
		http.requestMatchers((matchers) -> matchers.antMatchers("/actuator/**", "/css/**", "/error"))
				.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll()).requestCache().disable()
				.securityContext().disable().sessionManagement().disable();
		return http.build();
	}

}
