package com.alibaba.nacos;

import com.alibaba.nacos.config.ConfigConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class ProductNacosApplication {

	public static void main(String[] args) {
		if (initEnv()) {
			SpringApplication.run(ProductNacosApplication.class, args);
		}
	}

	/**
	 * 初始化运行环境
	 */
	private static boolean initEnv() {
		System.setProperty(ConfigConstants.STANDALONE_MODE, "true");
		System.setProperty(ConfigConstants.AUTH_ENABLED, "false");
		System.setProperty(ConfigConstants.LOG_BASEDIR, "logs");
		System.setProperty(ConfigConstants.LOG_ENABLED, "false");
		return true;
	}

}
