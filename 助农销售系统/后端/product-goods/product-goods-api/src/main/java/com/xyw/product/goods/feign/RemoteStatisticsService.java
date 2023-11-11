package com.xyw.product.goods.feign;

import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.core.constant.ServiceNameConstants;
import com.xyw.product.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteStatisticsService", value = ServiceNameConstants.GOODS_SERVICE)
public interface RemoteStatisticsService {

	@PutMapping("/statisticsApi/updateOrderSum/{BusinessId:\\d+}")
	R updateOrderSumByBusinessId(@PathVariable("BusinessId") Long BusinessId, @RequestHeader(SecurityConstants.FROM) String from);

}
