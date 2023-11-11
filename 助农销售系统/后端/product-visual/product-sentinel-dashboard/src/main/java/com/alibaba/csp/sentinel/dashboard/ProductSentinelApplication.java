package com.alibaba.csp.sentinel.dashboard;

import com.alibaba.csp.sentinel.init.InitExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductSentinelApplication {

	public static void main(String[] args) {
		triggerSentinelInit();
		SpringApplication.run(ProductSentinelApplication.class, args);
	}

	private static void triggerSentinelInit() {
		new Thread(() -> InitExecutor.doInit()).start();
	}

}
