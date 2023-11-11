package com.xyw.product.common.datasource;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.xyw.product.common.datasource.config.DataSourceProperties;
import com.xyw.product.common.datasource.config.JdbcDynamicDataSourceProvider;
import com.xyw.product.common.datasource.config.LastParamDsProcessor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {

	@Bean
	public DynamicDataSourceProvider dynamicDataSourceProvider(StringEncryptor stringEncryptor,
			DataSourceProperties properties) {
		return new JdbcDynamicDataSourceProvider(stringEncryptor, properties);
	}

	@Bean
	public DsProcessor dsProcessor() {
		return new LastParamDsProcessor();
	}

}
