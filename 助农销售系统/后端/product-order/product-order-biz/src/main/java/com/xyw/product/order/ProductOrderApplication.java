package com.xyw.product.order;

import com.xyw.product.common.feign.annotation.EnableProductFeignClients;
import com.xyw.product.common.security.annotation.EnableProductResourceServer;
import com.xyw.product.common.swagger.annotation.EnableProductDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableProductDoc
@EnableProductFeignClients
@EnableProductResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class ProductOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductOrderApplication.class, args);
	}

}