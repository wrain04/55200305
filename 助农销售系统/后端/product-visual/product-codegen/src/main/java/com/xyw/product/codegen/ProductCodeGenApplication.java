package com.xyw.product.codegen;

import com.xyw.product.common.datasource.annotation.EnableDynamicDataSource;
import com.xyw.product.common.feign.annotation.EnableProductFeignClients;
import com.xyw.product.common.security.annotation.EnableProductResourceServer;
import com.xyw.product.common.swagger.annotation.EnableProductDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableProductDoc
@EnableDynamicDataSource
@EnableProductFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableProductResourceServer
public class ProductCodeGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCodeGenApplication.class, args);
	}

}
