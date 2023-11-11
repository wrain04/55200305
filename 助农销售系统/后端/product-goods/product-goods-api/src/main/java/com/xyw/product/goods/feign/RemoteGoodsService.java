package com.xyw.product.goods.feign;

import com.xyw.product.common.core.constant.ServiceNameConstants;
import com.xyw.product.common.core.util.R;
import com.xyw.product.goods.vo.GoodsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = "remoteGoodsService", value = ServiceNameConstants.GOODS_SERVICE)
public interface RemoteGoodsService {

	@GetMapping("/goodApi/getByIdList")
	R<List<GoodsVO>> getByIdList(@RequestParam("goodIdList") List<String> goodIdList);

	@GetMapping("/goodApi/{goodId:\\d+}")
	public R<GoodsVO> getById(@PathVariable("goodId") String goodId);

}
