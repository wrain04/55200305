package com.xyw.product.order.feign;

import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.core.constant.ServiceNameConstants;
import com.xyw.product.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteOrdersService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteOrdersService {

	@GetMapping("/orders/getWaitSend")
	R getWaitSend();

	@GetMapping("/orders/getWaitRefundORReturn")
	R getWaitRefundORReturn();

	@GetMapping("/getWaitSendToBusiness")
	R getWaitSendToBusiness();

	@GetMapping("/getWaitRefundORReturnToBusiness")
	R getWaitRefundORReturnToBusiness();

}
