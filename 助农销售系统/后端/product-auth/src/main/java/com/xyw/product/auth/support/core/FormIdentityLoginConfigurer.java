package com.xyw.product.auth.support.core;

import com.xyw.product.auth.support.handler.FormAuthenticationFailureHandler;
import com.xyw.product.auth.support.handler.SsoLogoutSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public final class FormIdentityLoginConfigurer
		extends AbstractHttpConfigurer<FormIdentityLoginConfigurer, HttpSecurity> {

	@Override
	public void init(HttpSecurity http) throws Exception {
		http.formLogin(formLogin -> {
			formLogin.loginPage("/token/login");
			formLogin.loginProcessingUrl("/token/form");
			formLogin.failureHandler(new FormAuthenticationFailureHandler());

		}).logout() // SSO登出成功处理
				.logoutSuccessHandler(new SsoLogoutSuccessHandler()).deleteCookies("JSESSIONID")
				.invalidateHttpSession(true).and().csrf().disable();
	}

}
