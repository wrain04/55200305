package com.xyw.product.admin.api.feign;

import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.core.constant.ServiceNameConstants;
import com.xyw.product.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteParamService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteParamService {

	/**
	 * 通过key 查询参数配置
	 * @param key key
	 * @param from 声明成内部调用，避免MQ 等无法调用
	 * @return
	 */
	@GetMapping("/param/publicValue/{key}")
	R<String> getByKey(@PathVariable("key") String key, @RequestHeader(SecurityConstants.FROM) String from);

}
