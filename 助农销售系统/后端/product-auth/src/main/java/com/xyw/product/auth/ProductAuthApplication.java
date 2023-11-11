package com.xyw.product.auth;

import com.xyw.product.common.feign.annotation.EnableProductFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableProductFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ProductAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductAuthApplication.class, args);
	}

}
