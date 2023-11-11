package com.xyw.product.admin.api.feign;

import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.core.constant.ServiceNameConstants;
import com.xyw.product.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(contextId = "remoteDeptService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteDeptService {

	/**
	 * 查收子级id列表
	 * @return 返回子级id列表
	 */
	@GetMapping("/dept/child-id/{deptId}")
	R<List<Long>> listChildDeptId(@PathVariable("deptId") Long deptId,
			@RequestHeader(SecurityConstants.FROM) String from);

}
