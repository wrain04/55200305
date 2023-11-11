package com.xyw.product.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductGatewayApplication.class, args);
	}

}
