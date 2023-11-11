package com.xyw.product.common.log;

import com.xyw.product.admin.api.feign.RemoteLogService;
import com.xyw.product.common.log.aspect.SysLogAspect;
import com.xyw.product.common.log.event.SysLogListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

	@Bean
	public SysLogListener sysLogListener(RemoteLogService remoteLogService) {
		return new SysLogListener(remoteLogService);
	}

	@Bean
	public SysLogAspect sysLogAspect() {
		return new SysLogAspect();
	}

}
