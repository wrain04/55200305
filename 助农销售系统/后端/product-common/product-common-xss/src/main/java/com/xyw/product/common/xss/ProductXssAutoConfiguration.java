package com.xyw.product.common.xss;

import lombok.RequiredArgsConstructor;
import com.xyw.product.common.xss.core.DefaultXssCleaner;
import com.xyw.product.common.xss.core.FormXssClean;
import com.xyw.product.common.xss.core.JacksonXssClean;
import com.xyw.product.common.xss.core.XssCleaner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(com.xyw.product.common.xss.config.ProductXssProperties.class)
@ConditionalOnProperty(prefix = com.xyw.product.common.xss.config.ProductXssProperties.PREFIX, name = "enabled",
		havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ProductXssAutoConfiguration implements WebMvcConfigurer {

	private final com.xyw.product.common.xss.config.ProductXssProperties xssProperties;

	@Bean
	@ConditionalOnMissingBean
	public XssCleaner xssCleaner(com.xyw.product.common.xss.config.ProductXssProperties properties) {
		return new DefaultXssCleaner(properties);
	}

	@Bean
	public FormXssClean formXssClean(com.xyw.product.common.xss.config.ProductXssProperties properties,
			XssCleaner xssCleaner) {
		return new FormXssClean(properties, xssCleaner);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer xssJacksonCustomizer(
			com.xyw.product.common.xss.config.ProductXssProperties properties, XssCleaner xssCleaner) {
		return builder -> builder.deserializerByType(String.class, new JacksonXssClean(properties, xssCleaner));
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> patterns = xssProperties.getPathPatterns();
		if (patterns.isEmpty()) {
			patterns.add("/**");
		}
		com.xyw.product.common.xss.core.XssCleanInterceptor interceptor = new com.xyw.product.common.xss.core.XssCleanInterceptor(
				xssProperties);
		registry.addInterceptor(interceptor).addPathPatterns(patterns)
				.excludePathPatterns(xssProperties.getPathExcludePatterns()).order(Ordered.LOWEST_PRECEDENCE);
	}

}
