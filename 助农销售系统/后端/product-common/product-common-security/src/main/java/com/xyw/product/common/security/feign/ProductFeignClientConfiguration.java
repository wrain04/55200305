package com.xyw.product.common.security.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

public class ProductFeignClientConfiguration {

	@Bean
	public RequestInterceptor oauthRequestInterceptor(BearerTokenResolver tokenResolver) {
		return new ProductOAuthRequestInterceptor(tokenResolver);
	}

}
